"""Verify homepage section navigation, responsive layout and aside transitions."""
import argparse
import json
from pathlib import Path
from playwright.sync_api import sync_playwright


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--url', default='http://localhost:4200')
    parser.add_argument('--output', type=Path, default=Path('target/home-navigation'))
    args = parser.parse_args()
    args.output.mkdir(parents=True, exist_ok=True)
    base = args.url.rstrip('/')
    anchors = ['highlights', 'how-it-works', 'intellij-plugin', 'explore-platform']
    evidence, errors = [], []
    with sync_playwright() as playwright, playwright.chromium.launch() as browser:
        page = browser.new_page(viewport={'width': 1440, 'height': 1100}, reduced_motion='reduce')
        page.on('pageerror', lambda error: errors.append(str(error)))

        def home_layout():
            page.wait_for_function("document.querySelector('wa-page')?.classList.contains('no-aside')")
            assert page.locator('wa-page > [slot="aside"]').is_hidden()
            assert page.locator('guicedee-home-aside').count() == 0
            page.wait_for_function('document.documentElement.scrollWidth <= innerWidth + 1')

        def destination(anchor):
            page.wait_for_function('id => document.activeElement?.id === id', arg=anchor)
            assert page.url.endswith('#' + anchor), page.url
            top = page.locator('#' + anchor).bounding_box()['y']
            assert 40 <= top <= 180, (anchor, top)
            if anchor == 'explore-platform':
                assert page.locator('#explore-platform').evaluate('e => e.open')
            home_layout()

        for width, height in [(1440, 1100), (1024, 1000), (390, 844)]:
            page.set_viewport_size({'width': width, 'height': height})
            page.goto(base + '/home', wait_until='networkidle')
            page.wait_for_function("!!customElements.get('wa-tree-item') && !!customElements.get('wa-page')")
            home_layout()
            mobile = page.locator('wa-page').get_attribute('view') == 'mobile'
            slot = 'navigation' if mobile else 'menu'
            home = page.locator(f'[slot="{slot}"] .home-navigation')
            if mobile:
                page.locator('wa-page').evaluate('e => { e.navOpen = true; }')
                page.wait_for_timeout(400)
            assert home.evaluate('e => e.expanded')
            assert home.locator(':scope > .home-section').count() == 4
            assert home.locator(':scope > .home-section > a').evaluate_all('es => es.map(e => e.getAttribute("href"))') == ['/home#' + anchor for anchor in anchors]
            first = home.locator('.home-section > a').first.bounding_box()
            parent = home.locator(':scope > a').bounding_box()
            assert first['x'] > parent['x'], (width, first, parent)
            page.screenshot(path=str(args.output / f'home-{width}.png'))
            for anchor in anchors:
                if mobile:
                    page.locator('wa-page').evaluate('e => { e.navOpen = true; }')
                home.locator(f'a[href="/home#{anchor}"]').click()
                destination(anchor)
                assert not page.locator('wa-page').evaluate('e => e.navOpen')
            evidence.append({'width': width, 'slot': slot, 'anchors': anchors})

        page.set_viewport_size({'width': 1440, 'height': 1100})
        page.goto(base + '/services', wait_until='networkidle')
        page.locator('guicedee-services-aside').wait_for(state='visible')
        assert not page.locator('wa-page').evaluate('e => e.classList.contains("no-aside")')
        services_width = page.locator('wa-page > main').bounding_box()['width']
        link = page.locator('[slot="menu"] a[href="/home#how-it-works"]')
        link.focus()
        link.press('Enter')
        destination('how-it-works')
        home_width = page.locator('wa-page > main').bounding_box()['width']
        assert home_width > services_width + 150, (home_width, services_width)
        page.reload(wait_until='networkidle')
        destination('how-it-works')
        page.locator('[slot="menu"] a[href="/home#intellij-plugin"]').click()
        destination('intellij-plugin')
        page.go_back()
        destination('how-it-works')
        page.evaluate('window.scrollTo(0, 0)')
        link.click()
        page.wait_for_function("document.querySelector('#how-it-works').getBoundingClientRect().top >= 40 && document.querySelector('#how-it-works').getBoundingClientRect().top <= 180")

        page.goto(base + '/home(aside:home)?from=bookmark#explore-platform', wait_until='networkidle')
        destination('explore-platform')
        assert '(aside:' not in page.url and '?from=bookmark' in page.url
        page.locator('[slot="menu"] a[routerlink="/services"]').click()
        page.locator('guicedee-services-aside').wait_for(state='visible')
        assert not page.locator('wa-page').evaluate('e => e.classList.contains("no-aside")')
        assert errors == [], errors
        evidence.append({'homeWidth': home_width, 'servicesWidth': services_width, 'errors': errors})
        (args.output / 'verification.json').write_text(json.dumps(evidence, indent=2) + '\n')
        print('PASS: four Home sub-items, desktop/mobile anchors, expanded disclosure, keyboard, reload/back, legacy bookmarks and aside transitions')


if __name__ == '__main__':
    main()
