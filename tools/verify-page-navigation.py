"""Check all page trees, section links, filters and legacy URLs in Chromium."""
import argparse
import json
from pathlib import Path
from playwright.sync_api import sync_playwright

COUNTS = {'home': 17, 'releases': 15, 'builder': 0, 'getting-started': 8,
          'guides/end-to-end': 15, 'capabilities': 23, 'modules': 6,
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
            links = node(route).locator('.navigation-section > a')
            assert links.count() == count
            hrefs = links.evaluate_all('es => es.map(e => e.getAttribute("href"))')
            assert node(route, 'navigation').locator('.navigation-section > a').evaluate_all('es => es.map(e => e.getAttribute("href"))') == hrefs
            for href in hrefs:
                anchor = href.split('#')[1]
                assert page.locator(f'wa-page > main [id="{anchor}"]').count() == 1, (route, anchor)
            if count:
                for index in sorted({0, count // 2, count - 1}):
                    anchor = hrefs[index].split('#')[1]
                    link = links.nth(index)
                    group = link.locator('xpath=ancestor::wa-tree-item[contains(@class, "navigation-group")]')
                    if group.count() and not group.evaluate('e => e.expanded'):
                        group.locator(':scope > .navigation-group-label').click()
                    link.click()
                    destination(route, anchor)
            evidence.append({'route': route, 'sectionLinks': count})
            print('Checked', route, count, flush=True)

        # Home's nested destinations open the disclosure before scrolling/focusing.
        def explore(slot='menu'):
            return node('home', slot).locator('[data-navigation-group="home:explore-platform"]')

        def expanded_explore():
            expanded('home')
            page.wait_for_function('''() => ['menu','navigation'].every(slot => {
                const groups = [...document.querySelectorAll(`[slot="${slot}"] .navigation-group`)];
                const opened = groups.filter(e => e.expanded);
                return opened.length === 1 && opened[0].dataset.navigationGroup === 'home:explore-platform';
            })''')

        page.goto(base + '/home', wait_until='domcontentloaded')
        active('home')
        assert not explore().evaluate('e => e.expanded')
        explore().locator(':scope > .navigation-group-label').click()
        expanded_explore()
        assert page.url == base + '/home'
        links = explore().locator('.navigation-section > a')
        assert links.count() == 14
        for index in range(links.count()):
            anchor = links.nth(index).get_attribute('href').split('#')[1]
            links.nth(index).click()
            destination('home', anchor)
            expanded_explore()
            assert page.locator('#explore-platform').evaluate('e => e.open')
        page.goto(base + '/home#rest-services', wait_until='domcontentloaded')
        destination('home', 'rest-services')
        expanded_explore()
        page.reload(wait_until='domcontentloaded')
        destination('home', 'rest-services')
        expanded_explore()
        page.locator('#explore-platform').evaluate('e => e.open = false')
        explore().locator('a[href="/home#rest-services"]').click()
        destination('home', 'rest-services')
        assert page.locator('#explore-platform').evaluate('e => e.open')
        search = page.locator('[slot="menu"] .site-search')
        search.locator('wa-input input').fill('One env var switches')
        result = search.locator('[data-search-destination^="home#"]').first
        result.wait_for(state='visible')
        search_anchor = result.get_attribute('data-search-destination').split('#')[1]
        result.click()
        page.wait_for_function('id => document.activeElement.id === id', arg=search_anchor)
        expanded_explore()
        page.go_back()
        destination('home', 'rest-services')
        expanded_explore()
        print('Checked all Explore links, disclosure, direct/repeat links, reload, search and history', flush=True)

        # Capability groups are exclusive, including keyboard and search navigation.
        page.goto(base + '/capabilities', wait_until='domcontentloaded')
        active('capabilities')
        def group(key, slot='menu'):
            return node('capabilities', slot).locator(f'[data-navigation-group="capabilities:{key}"]')

        def expanded_group(key):
            expected = ['capabilities:' + key] if key else []
            page.wait_for_function('''expected => ['menu','navigation'].every(slot => JSON.stringify(
                [...document.querySelectorAll(`[slot="${slot}"] .navigation-group`)].filter(e => e.expanded)
                .map(e => e.dataset.navigationGroup)) === JSON.stringify(expected))''', arg=expected)
            expanded('capabilities')

        assert node('capabilities').locator(':scope > .navigation-group').count() == 5
        assert node('capabilities').locator('.navigation-group-label').all_text_contents() == [
            'Core & configuration', 'Web & security', 'Data & messaging', 'Cloud & deployment', 'Observability & resilience']
        expanded_group('')
        for key in ['core', 'web', 'data', 'cloud', 'operations']:
            group(key).locator(':scope > .navigation-group-label').click()
            expanded_group(key)
        group('operations').focus()
        group('operations').press('Enter')
        expanded_group('')
        group('operations').press(' ')
        expanded_group('operations')
        group('web').locator('[part="expand-button"]').first.click()
        expanded_group('web')
        group('web').locator('a[href="/capabilities#http-server"]').click()
        destination('capabilities', 'http-server')
        expanded_group('web')
        page.goto(base + '/capabilities#modular-sdks', wait_until='domcontentloaded')
        destination('capabilities', 'modular-sdks')
        expanded_group('data')
        page.reload(wait_until='domcontentloaded')
        destination('capabilities', 'modular-sdks')
        expanded_group('data')
        search = page.locator('[slot="menu"] .site-search')
        search.locator('wa-input input').fill('Production resilience')
        result = search.locator('[data-search-destination^="capabilities#"]').first
        result.wait_for(state='visible')
        search_anchor = result.get_attribute('data-search-destination').split('#')[1]
        result.click()
        page.wait_for_function('id => document.activeElement.id === id', arg=search_anchor)
        expanded_group('operations')
        page.go_back()
        destination('capabilities', 'modular-sdks')
        expanded_group('data')
        page.goto(base + '/releases', wait_until='domcontentloaded')
        active('releases')
        assert node('releases').locator('.navigation-section > a').all_text_contents() == [
            'v2.6.0', 'v2.5.1', 'v2.5.0', 'v2.4.2', 'v2.4.1', 'v2.4.0', 'v2.3.0',
            'v2.2.3', 'v2.2.2', 'v2.2.1', 'v2.2.0', 'v2.1.0', 'v2.0.2', 'v2.0.1', 'v2.0']
        print('Checked release versions, nested groups, keyboard, deep links and search expansion', flush=True)

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
            page.goto(base + '/home#rest-services', wait_until='domcontentloaded')
            destination('home', 'rest-services')
            expanded_explore()
            if mobile:
                page.locator('[slot="navigation-toggle"] wa-button').click()
                page.wait_for_timeout(400)
            page.screenshot(path=str(args.output / f'explore-{width}.png'))
            explore(slot).locator('a[href="/home#for-developers"]').click()
            destination('home', 'for-developers')
            assert not page.locator('wa-page').evaluate('e => e.navOpen')
            page.goto(base + '/capabilities#modular-sdks', wait_until='domcontentloaded')
            destination('capabilities', 'modular-sdks')
            expanded_group('data')
            if mobile:
                page.locator('[slot="navigation-toggle"] wa-button').click()
                page.wait_for_timeout(400)
            page.screenshot(path=str(args.output / f'capabilities-{width}.png'))
            group('cloud', slot).locator(':scope > .navigation-group-label').click()
            expanded_group('cloud')
            group('cloud', slot).locator('a[href="/capabilities#webassembly"]').click()
            destination('capabilities', 'webassembly')
            assert not page.locator('wa-page').evaluate('e => e.navOpen')
            if mobile:
                page.locator('[slot="navigation-toggle"] wa-button').click()
                page.wait_for_timeout(400)
            node('releases', slot).locator(':scope > a').click()
            active('releases')
            page.wait_for_timeout(400)
            if mobile:
                page.locator('[slot="navigation-toggle"] wa-button').click()
                page.wait_for_timeout(400)
            page.screenshot(path=str(args.output / f'releases-{width}.png'))
            node('releases', slot).locator('a[href="/releases#v2-4-0"]').click()
            destination('releases', 'v2-4-0')
            evidence.append({'width':width, 'slot':slot, 'singleOpenBranch':True})
            print('Checked viewport', width, flush=True)
        assert errors == [], errors
        (args.output / 'verification.json').write_text(json.dumps(evidence, indent=2) + '\n')
        print('PASS: 10 pages, 135 section links, exclusive expansion, direct/repeat/search-compatible navigation, filters, legacy URLs, keyboard, reload/back and mobile drawer')


if __name__ == '__main__':
    main()
