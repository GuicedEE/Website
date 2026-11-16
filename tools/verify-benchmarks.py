"""Check rendered benchmark charts and grids against the published dataset contract.

Requires Python Playwright and Chromium. Without --url, serve the production build
locally and test published, unavailable and invalid dataset responses.
"""
import argparse
import csv
from functools import partial
from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
import json
import os
from pathlib import Path
import threading
from urllib.parse import urlparse
from playwright.sync_api import sync_playwright


class Handler(SimpleHTTPRequestHandler):
    def do_GET(self):
        if not Path(self.translate_path(self.path)).is_file():
            self.path = '/index.html'
        super().do_GET()

    def log_message(self, *args):
        pass


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('benchmarks', type=Path)
    parser.add_argument('--url')
    parser.add_argument('--output', type=Path, default=Path('target/benchmark-verification'))
    args = parser.parse_args()
    args.output.mkdir(parents=True, exist_ok=True)
    root = Path(__file__).resolve().parents[1]
    docs = args.benchmarks / 'docs'
    persistence = json.loads((docs / 'persistence-performance.json').read_text())
    variants = [persistence['variants'][name] for name in ['guicedee', 'quarkus', 'micronaut']]
    expected = [[row['launchToFirstTransactionMs']['median'] / 1000 for row in variants],
                [row['read']['p50Ms'] for row in variants], [row['cycle']['p50Ms'] for row in variants]]
    runtime = json.loads((docs / 'current-performance.json').read_text())
    native = [runtime['groups']['native-standard']['variants'][name]
              for name in ['guicedee-native', 'quarkus-standard-native', 'micronaut-native']]
    builds = list(csv.DictReader((docs / 'build-performance.csv').read_text().splitlines()))
    build_values = [[float(next(row['median_seconds'] for row in builds
                    if row['variant'] == name and row['goal'] == goal)) for name in names]
                    for names, goal in [(['guicedee', 'guicedee-scan', 'quarkus-standard', 'micronaut'], 'compile'),
                                        (['guicedee', 'guicedee-scan', 'quarkus-standard', 'micronaut'], 'package'),
                                        (['guicedee-native', 'quarkus-standard-native', 'micronaut-native'], 'package')]]
    server = None
    if not args.url:
        server = ThreadingHTTPServer(('127.0.0.1', 0), partial(Handler,
            directory=str(root / 'target/webroot/guicedee-website/dist/jwebmp/browser')))
        threading.Thread(target=server.serve_forever, daemon=True).start()
    url = args.url or f'http://127.0.0.1:{server.server_port}/home'
    evidence = []
    try:
        with sync_playwright() as playwright:
            options = dict(headless=True)
            if os.getenv('PLAYWRIGHT_CHROMIUM_EXECUTABLE'):
                options['executable_path'] = os.environ['PLAYWRIGHT_CHROMIUM_EXECUTABLE']
            with playwright.chromium.launch(**options) as browser:
                for mode in (['public'] if args.url else ['published', 'unavailable', 'invalid']):
                    page = browser.new_page(viewport=dict(width=1440, height=1100), reduced_motion='reduce')
                    errors, requests = [], []
                    page.on('pageerror', lambda error: errors.append(str(error)))
                    page.on('response', lambda response: requests.append(dict(url=response.url, status=response.status))
                            if '/GuicedEE/benchmarks/' in response.url else None)
                    if not args.url:
                        def dataset(route):
                            if mode == 'unavailable':
                                route.abort()
                                return
                            file = Path(urlparse(route.request.url).path).name
                            body = (docs / file).read_text(encoding='utf-8')
                            if mode == 'invalid' and file == 'persistence-performance.json':
                                value = json.loads(body)
                                value['variants']['micronaut']['read']['p50Ms'] = -1
                                body = json.dumps(value)
                            elif mode == 'invalid' and file == 'current-performance.json':
                                value = json.loads(body)
                                value['groups']['native-standard']['variants']['micronaut-native']['launchToFirstBusinessMs']['median'] = -1
                                body = json.dumps(value)
                            elif mode == 'invalid' and file == 'build-performance.csv':
                                lines = body.splitlines()
                                columns = lines[0].split(',')
                                for i, line in enumerate(lines[1:], 1):
                                    values = line.split(',')
                                    if values[0] == 'micronaut':
                                        values[columns.index('median_seconds')] = '-1'
                                        lines[i] = ','.join(values)
                                body = '\n'.join(lines)
                            route.fulfill(status=200, body=body,
                                          content_type='text/csv' if file.endswith('.csv') else 'application/json')
                        page.route('https://raw.githubusercontent.com/GuicedEE/benchmarks/master/docs/*', dataset)
                    page.goto(url, wait_until='domcontentloaded', timeout=60000)
                    panel = page.locator('.benchmark-panel').filter(has_text='Real transactions. Real evidence.')
                    panel.wait_for(state='attached', timeout=30000)
                    page.wait_for_function("() => !!customElements.get('wa-page') && !!customElements.get('wa-chart') && !!customElements.get('wa-data-grid')", timeout=30000)
                    source = 'Published' if mode in ['public', 'published'] else 'Snapshot'
                    page.wait_for_function("""([source, date]) => [...document.querySelectorAll('.benchmark-panel')]
                        .some(panel => panel.textContent.includes('Real transactions.') &&
                            (panel.textContent.includes(source + ' · ' + date) || panel.textContent.includes(source + ' ' + date)))""",
                        arg=[source, persistence['measuredAt'][:10]], timeout=15000)
                    page.locator('.showcase-navigation wa-button').filter(has_text='Persistence').click()
                    chart = panel.locator('wa-chart')
                    for index, label in enumerate(['Startup', 'Read', 'CRUD cycle']):
                        panel.locator('.benchmark-modes wa-button').filter(has_text=label).click()
                        page.wait_for_function("""expected => {
                            const panel = [...document.querySelectorAll('.benchmark-panel')].find(p => p.textContent.includes('Real transactions.'));
                            const chart = panel.querySelector('wa-chart');
                            let config = chart?.config;
                            if (typeof config === 'string') config = JSON.parse(config);
                            return JSON.stringify(config?.data?.datasets?.[0]?.data) === JSON.stringify(expected);
                        }""", arg=expected[index], timeout=15000)
                        config = chart.evaluate("e => typeof e.config === 'string' ? JSON.parse(e.config) : e.config")
                        assert config['data']['datasets'][0]['data'] == expected[index]
                        unit = 's' if index == 0 else 'ms'
                        assert config['data']['datasets'][0]['label'] == unit
                        if index == 0:
                            panel.locator('wa-chart canvas').wait_for(state='visible', timeout=15000)
                            page.screenshot(path=str(args.output / f'{mode}-persistence.png'))
                    build_panel = page.locator('.benchmark-panel').filter(has_text='Build. Run. Keep your flow.')
                    build_rows = build_panel.locator('.build-grid').evaluate('e => e.data')
                    runtime_rows = page.locator('.runtime-grid').evaluate('e => e.data')
                    assert len(build_rows) == len(runtime_rows) == 9
                    assert any('Micronaut' in row['artifact'] for row in build_rows)
                    assert any('Micronaut' in row['artifact'] for row in runtime_rows)
                    developer = page.locator('.showcase-navigation wa-button').filter(has_text='Developer')
                    assert developer.inner_text().strip() == 'Developer'
                    developer.click()
                    for index, label in [(0, 'Compile'), (1, 'JVM package'), (2, 'Native build'), (0, 'Compile')]:
                        build_panel.locator('.benchmark-modes wa-button').filter(has_text=label).click()
                        page.wait_for_function("""expected => {
                            const panel = [...document.querySelectorAll('.benchmark-panel')].find(p => p.textContent.includes('Build. Run. Keep your flow.'));
                            let config = panel?.querySelector('wa-chart')?.config;
                            if (typeof config === 'string') config = JSON.parse(config);
                            return JSON.stringify(config?.data?.datasets?.[0]?.data) === JSON.stringify(expected);
                        }""", arg=build_values[index])
                        config = build_panel.locator('wa-chart').evaluate("e => typeof e.config === 'string' ? JSON.parse(e.config) : e.config")
                        assert config['data']['datasets'][0]['data'] == build_values[index], (mode, label, config)
                        labels = (['GuicedEE native', 'Quarkus native', 'Micronaut native'] if index == 2 else
                                  [['GuicedEE SPI', 'JAR · java -m'], ['GuicedEE scanner', 'JAR · java -m'], 'Quarkus fast-JAR', 'Micronaut JVM'])
                        assert config['data']['labels'] == labels, (mode, label, config)
                        if index != 2:
                            column = 'compile' if index == 0 else 'package'
                            assert [row[column] for row in build_rows[:4]] == [f'{value:.2f}' for value in build_values[index]]
                            assert all('JAR (java -m)' in row['artifact'] for row in build_rows[:2])
                        build_panel.locator('wa-chart canvas').wait_for(state='visible')
                        page.screenshot(path=str(args.output / f'{mode}-build-{index}.png'))
                    for label, title, values in [
                        ('Performance', 'Keeping pace with the best.', [v['launchToFirstBusinessMs']['median'] for v in native]),
                        ('Native-Image', 'Your Guice application.', [v['peakMiB']['median'] for v in native])]:
                        page.locator('.showcase-navigation wa-button').filter(has_text=label).click()
                        chart_panel = page.locator('.benchmark-panel').filter(has_text=title)
                        config = chart_panel.locator('wa-chart').evaluate("e => typeof e.config === 'string' ? JSON.parse(e.config) : e.config")
                        assert config['data']['datasets'][0]['data'] == values, (mode, label, config)
                        assert config['data']['labels'] == ['GuicedEE native', 'Quarkus native', 'Micronaut native']
                        table = chart_panel.locator('wa-data-grid').first
                        rows = table.evaluate('e => e.data')
                        unit = 'MiB' if label == 'Native-Image' else 'ms'
                        assert [row['framework'] for row in rows] == config['data']['labels']
                        assert [row['value'] for row in rows] == [f'{value:.2f} {unit}' for value in values]
                        for row in rows:
                            table.get_by_text(row['framework'], exact=True).wait_for(state='visible')
                            table.get_by_text(row['value'], exact=True).wait_for(state='visible')
                    if not args.url and mode == 'published':
                        for width, height in [(1440, 1100), (390, 844)]:
                            page.set_viewport_size(dict(width=width, height=height))
                            page.wait_for_timeout(200)
                            heights = []
                            for i in range(8):
                                page.locator('.showcase-navigation wa-button').nth(i).click()
                                page.wait_for_function('i => document.querySelector(".performance-carousel").activeSlide === i', arg=i)
                                box = page.locator('.performance-carousel').bounding_box()
                                heights.append(box['height'])
                            assert max(heights) - min(heights) < 2, (width, heights)
                            assert page.locator('.showcase-scroller[orientation="vertical"]').count() == 8
                            developer.click()
                            build_panel.locator('wa-chart').evaluate("e => e.scrollIntoView({block: 'center', behavior: 'instant'})")
                            build_panel.locator('wa-chart').screenshot(path=str(args.output / f'compile-chart-{width}.png'))
                            label_bounds = build_panel.locator('wa-chart').evaluate('''e => {
                                const chart = e.chart, axis = chart.scales.y;
                                const labels = axis.ticks.flatMap(tick => tick.label);
                                chart.ctx.save();
                                const font = chart.options.font;
                                chart.ctx.font = `${font.size}px ${font.family}`;
                                const widest = Math.max(...labels.map(label => chart.ctx.measureText(label).width));
                                chart.ctx.restore();
                                return {ticks: axis.ticks.length, widest, available: axis.width - 10};
                            }''')
                            assert label_bounds['ticks'] == 4 and label_bounds['widest'] <= label_bounds['available'], (width, label_bounds)
                            page.locator('.showcase-navigation wa-button').filter(has_text='CDI').click()
                            cdi = page.locator('.cdi-setup')
                            cdi.locator('[part="header"]').first.click()
                            service = cdi.locator('.code-details').filter(has_text='GuicedEE CDI service')
                            if not service.evaluate('e => e.open'):
                                service.locator('[part="header"]').click()
                            service.locator('pre').wait_for(state='visible')
                            excerpt = service.locator('pre').inner_text()
                            assert '@Verticle' not in excerpt and '@Inject' not in excerpt
                            assert '@ConfigProperty(name = "bench.upstream")' in excerpt
                            assert abs(page.locator('.performance-carousel').bounding_box()['height'] - heights[0]) < 2
                            # Collapse details before repeating at another viewport size.
                            cdi.locator('[part="header"]').first.click()
                            page.locator('.showcase-navigation wa-button').filter(has_text='Persistence').click()
                            example = page.locator('.persistence-example')
                            example.scroll_into_view_if_needed()
                            assert 'withStatelessSession' in example.inner_text() and 'withTransaction' in example.inner_text()
                            scrolled = example.locator('xpath=ancestor::wa-scroller').evaluate("e => e.shadowRoot.querySelector('[part=content]').scrollTop")
                            assert scrolled > 0, (width, scrolled)
                            assert abs(page.locator('.performance-carousel').bounding_box()['height'] - heights[0]) < 2
                            page.screenshot(path=str(args.output / f'persistence-example-{width}.png'))
                            assert page.evaluate('document.documentElement.scrollWidth <= innerWidth'), width
                        page.set_viewport_size(dict(width=1440, height=1100))
                    if args.url:
                        page.reload(wait_until='domcontentloaded', timeout=60000)
                        page.wait_for_function("""([expected, date]) => {
                            const panel = [...document.querySelectorAll('.benchmark-panel')].find(p => p.textContent.includes('Real transactions.'));
                            let config = panel?.querySelector('wa-chart')?.config;
                            if (typeof config === 'string') config = JSON.parse(config);
                            return JSON.stringify(config?.data?.datasets?.[0]?.data) === JSON.stringify(expected)
                                && panel.textContent.includes('Published') && panel.textContent.includes(date)
                                && document.querySelector('.runtime-grid')?.data?.length === 9
                                && document.querySelector('.build-grid')?.data?.length === 9;
                        }""", arg=[expected[0], persistence['measuredAt'][:10]], timeout=30000)
                    assert not errors, errors
                    evidence.append(dict(mode=mode, url=page.url, persistenceValues=expected,
                        buildRows=len(build_rows), runtimeRows=len(runtime_rows), source=source,
                        requests=requests, pageErrors=errors, refreshVerified=bool(args.url)))
                    page.close()
    finally:
        if server:
            server.shutdown()
    (args.output / 'verification.json').write_text(json.dumps(evidence, indent=2), encoding='utf-8')
    print(f'Passed {len(evidence)} browser scenarios: three-framework charts, build modes, snapshots, grids and carousel examples')


if __name__ == '__main__':
    main()
