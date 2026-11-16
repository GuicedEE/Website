"""Check all page trees, section links, filters and legacy URLs in Chromium."""
import argparse
import json
from pathlib import Path
from playwright.sync_api import sync_playwright

COUNTS = {'home': 4, 'releases': 0, 'builder': 0, 'getting-started': 8,
          'guides/end-to-end': 15, 'capabilities': 22, 'modules': 6,
          'services': 15, 'cloud': 9, 'environment-variables': 27}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--url', default='http://localhost:4200')
    parser.add_argument('--output', type=Path, default=Path('target/page-navigation'))
    args = parser.parse_args()
    args.output.mkdir(parents=True, exist_ok=True)
    base = args.url.rstrip('/')
    evidence, errors = [], []
    with sync_playwright() as pw, pw.chromium.launch() as browser:
        page = browser.new_page(viewport={'width': 1440, 'height': 1100}, reduced_motion='reduce')
        page.set_default_timeout(15000)
        page.on('pageerror', lambda error: errors.append(str(error)))

        def node(route, slot='menu'):
            return page.locator(f'[slot="{slot}"] .page-navigation[data-navigation-route="{route}"]')

        def expanded(route):
            expected = [route] if COUNTS.get(route, 0) else []
            page.wait_for_function('''expected => ['menu', 'navigation'].every(slot => {
                const nodes = [...document.querySelectorAll(`[slot="${slot}"] .page-navigation`)];
                return nodes.length === 10 && JSON.stringify(nodes.filter(e => e.expanded && e.querySelector('.navigation-section'))
                    .map(e => e.dataset.navigationRoute)) === JSON.stringify(expected);
            })''', arg=expected)

        def active(route):
            expanded(route)
            page.wait_for_function('''route => ['menu','navigation'].every(slot => {
                const links = [...document.querySelectorAll(`[slot="${slot}"] .page-navigation > a[aria-current="page"]`)];
                return links.length === 1 && links[0].getAttribute('routerlink') === '/' + route;
            })''', arg=route)
            assert page.locator('wa-page > [slot="aside"], .page-aside').count() == 0
            assert '(aside:' not in page.url
            page.wait_for_function('document.documentElement.scrollWidth <= innerWidth + 1')

        def destination(route, anchor):
            page.wait_for_function('id => document.activeElement?.id === id', arg=anchor)
            active(route)
            assert page.url.endswith('#' + anchor), page.url
            target = page.locator(f'wa-page > main [id="{anchor}"]')
            assert target.is_visible(), (route, anchor)
            box = target.bounding_box()
            header_bottom = page.locator('wa-page > [slot="header"]').bounding_box()
            minimum = header_bottom['y'] + header_bottom['height'] if header_bottom else 0
            assert box['y'] >= minimum - 2 and box['y'] < page.viewport_size['height'] - 30, (route, anchor, box, minimum)
            if anchor == 'explore-platform': assert target.evaluate('e => e.open')

        # Every old aside link exists exactly once in its page, with matching menus.
        for route, count in COUNTS.items():
            page.goto(base + '/' + route, wait_until='domcontentloaded')
            active(route)
            links = node(route).locator(':scope > .navigation-section > a')
            assert links.count() == count
            hrefs = links.evaluate_all('es => es.map(e => e.getAttribute("href"))')
            assert node(route, 'navigation').locator(':scope > .navigation-section > a').evaluate_all('es => es.map(e => e.getAttribute("href"))') == hrefs
            for href in hrefs:
                anchor = href.split('#')[1]
                assert page.locator(f'wa-page > main [id="{anchor}"]').count() == 1, (route, anchor)
            if count:
                for index in sorted({0, count // 2, count - 1}):
                    anchor = hrefs[index].split('#')[1]
                    links.nth(index).click()
                    destination(route, anchor)
            evidence.append({'route': route, 'sectionLinks': count})
            print('Checked', route, count, flush=True)

        # Opening a different branch collapses its sibling without changing pages.
        page.goto(base + '/home', wait_until='domcontentloaded')
        active('home')
        for route in ['modules', 'services', 'cloud', 'getting-started', 'home']:
            node(route).locator('[part="expand-button"]').first.click()
            expanded(route)
            assert page.url == base + '/home'
        node('home').locator('[part="expand-button"]').first.click()
        expanded('')
        node('home').locator(':scope > a').click()
        active('home')
        node('services').locator('[part="expand-button"]').first.click()
        expanded('services')
        node('services').locator('a[href="/services#hibernate"]').press('Enter')
        destination('services', 'hibernate')
        page.reload(wait_until='domcontentloaded')
        destination('services', 'hibernate')
        node('modules').locator(':scope > a').click()
        active('modules')
        page.go_back()
        destination('services', 'hibernate')
        page.evaluate('window.scrollTo(0,0)')
        node('services').locator('a[href="/services#hibernate"]').click()
        page.wait_for_function("document.querySelector('#hibernate').getBoundingClientRect().top < 250")

        print('Checked exclusive expansion, keyboard and history', flush=True)

        # The removed sidebar filters remain usable and section links clear filtering.
        for route, query, anchor in [('modules', 'RabbitMQ', 'core-modules'),
                                     ('services', 'Firebase', 'hibernate'),
                                     ('environment-variables', 'GUICEDEE_LOG_LEVEL', 'metrics')]:
            page.goto(base + '/' + route, wait_until='domcontentloaded')
            active(route)
            field = page.locator('wa-page > main .page-filter input')
            field.fill(query)
            page.locator('[data-page-filter-hidden]').first.wait_for(state='attached')
            field.fill('')
            page.wait_for_function("document.querySelectorAll('[data-page-filter-hidden]').length === 0")
            field.fill(query)
            page.locator('[data-page-filter-hidden]').first.wait_for(state='attached')
            node(route).locator(f'a[href="/{route}#{anchor}"]').click()
            destination(route, anchor)
            assert field.input_value() == ''
            assert page.locator('[data-page-filter-hidden]').count() == 0

        print('Checked all three page filters', flush=True)

        # Old named-outlet bookmarks normalize to the same page and section.
        for route in ['home', 'getting-started', 'guides/end-to-end', 'capabilities', 'modules', 'services', 'cloud', 'environment-variables']:
            page.goto(base + '/' + route + f'(aside:{route})?from=bookmark', wait_until='domcontentloaded')
            active(route)
            assert '?from=bookmark' in page.url

        print('Checked legacy bookmarks', flush=True)

        for width, height in [(1440,1100), (1024,1000), (390,844)]:
            page.set_viewport_size({'width':width,'height':height})
            page.goto(base + '/services', wait_until='domcontentloaded')
            active('services')
            mobile = page.locator('wa-page').get_attribute('view') == 'mobile'
            slot = 'navigation' if mobile else 'menu'
            if mobile:
                page.locator('[slot="navigation-toggle"] wa-button').click()
                page.wait_for_timeout(400)
            page.screenshot(path=str(args.output / f'services-{width}.png'))
            node('cloud', slot).locator('[part="expand-button"]').first.click()
            expanded('cloud')
            node('cloud', slot).locator('a[href="/cloud#configuration"]').click()
            destination('cloud', 'configuration')
            assert not page.locator('wa-page').evaluate('e => e.navOpen')
            if mobile:
                page.locator('[slot="navigation-toggle"] wa-button').click()
                page.wait_for_timeout(400)
            node('environment-variables', slot).locator(':scope > a').click()
            active('environment-variables')
            if mobile:
                page.locator('[slot="navigation-toggle"] wa-button').click()
                page.wait_for_timeout(400)
            node('environment-variables', slot).locator('a[href="/environment-variables#mcp"]').click()
            destination('environment-variables', 'mcp')
            evidence.append({'width':width, 'slot':slot, 'singleOpenBranch':True})
            print('Checked viewport', width, flush=True)
        assert errors == [], errors
        (args.output / 'verification.json').write_text(json.dumps(evidence, indent=2) + '\n')
        print('PASS: 10 pages, 106 section links, exclusive expansion, direct/repeat/search-compatible navigation, filters, legacy URLs, keyboard, reload/back and mobile drawer')


if __name__ == '__main__':
    main()
