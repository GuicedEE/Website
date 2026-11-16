"""Check rendered benchmark charts and grids against the published dataset contract.

Requires Python Playwright and Chromium. Without --url, serve the production build
locally and test published, unavailable and invalid dataset responses.
"""
import argparse
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
    g, q = (persistence['variants'][name] for name in ['guicedee', 'quarkus'])
    expected = [[row['launchToFirstTransactionMs']['median'] / 1000 for row in [g, q]],
                [row['read']['p50Ms'] for row in [g, q]], [row['cycle']['p50Ms'] for row in [g, q]]]
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
                                value['variants']['guicedee']['read']['p50Ms'] = -1
                                body = json.dumps(value)
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
                    assert not errors, errors
                    evidence.append(dict(mode=mode, url=page.url, persistenceValues=expected,
                        buildRows=len(build_rows), runtimeRows=len(runtime_rows), source=source,
                        requests=requests, pageErrors=errors))
                    page.close()
    finally:
        if server:
            server.shutdown()
    (args.output / 'verification.json').write_text(json.dumps(evidence, indent=2), encoding='utf-8')
    print(f'Passed {len(evidence)} browser scenarios: startup/read/CRUD charts, units, sources and nine-row grids')


if __name__ == '__main__':
    main()
