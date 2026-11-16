"""Check native scroll chaining and section-link response time in Chromium."""
import argparse
import json
from pathlib import Path
from playwright.sync_api import sync_playwright


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--url', default='http://localhost:4200')
    parser.add_argument('--output', type=Path, default=Path('target/scroll-behavior'))
    args = parser.parse_args()
    args.output.mkdir(parents=True, exist_ok=True)
    base = args.url.rstrip('/')
    evidence = {}
    with sync_playwright() as pw, pw.chromium.launch() as browser:
        page = browser.new_page(viewport={'width':1440,'height':1000})
        page.set_default_timeout(15000)
        errors = []
        page.on('pageerror', lambda error: errors.append(str(error)))
        page.goto(base + '/home', wait_until='domcontentloaded')
        page.wait_for_function("!!customElements.get('wa-carousel') && !!customElements.get('wa-scroller')")
        carousel = page.locator('.performance-carousel')

        def content(index):
            return carousel.locator('wa-scroller').nth(index).locator('div[part="content"]').first

        def point(index):
            box = content(index).bounding_box()
            page.mouse.move(box['x'] + 80, min(box['y'] + box['height'] / 2, 750))

        page.wait_for_function("document.querySelector('wa-scroller').shadowRoot.querySelector('[part=content]').clientHeight > 400")
        assert content(0).evaluate('e => e.scrollHeight <= e.clientHeight + 1')
        point(0)
        page.mouse.wheel(0, 450)
        page.wait_for_function('scrollY > 100')
        evidence['nonOverflowingSlidePageScroll'] = page.evaluate('scrollY')
        page.evaluate('window.scrollTo({top:0,behavior:"instant"})')
        page.locator('.showcase-navigation wa-button').nth(1).click()
        page.wait_for_function("document.querySelector('wa-carousel').activeSlide === 1")
        page.wait_for_function('''() => {
            const c = document.querySelector('wa-carousel');
            return Math.abs(c.querySelectorAll('wa-carousel-item')[1].getBoundingClientRect().x
                - c.shadowRoot.querySelector('[part="scroll-container"]').getBoundingClientRect().x) < 2;
        }''')
        assert content(1).evaluate('e => e.scrollHeight > e.clientHeight + 20')
        point(1)
        page.mouse.wheel(0, 220)
        page.wait_for_function("document.querySelectorAll('wa-scroller')[1].shadowRoot.querySelector('[part=content]').scrollTop > 50")
        assert page.evaluate('scrollY') < 2
        content(1).evaluate('e => e.scrollTo({top:e.scrollHeight,behavior:"instant"})')
        page.wait_for_timeout(150)
        point(1)
        page.mouse.wheel(0, 300)
        page.wait_for_function('scrollY > 100')
        evidence['overflowingSlideBottomPageScroll'] = page.evaluate('scrollY')
        content(1).evaluate('e => e.scrollTo({top:0,behavior:"instant"})')
        point(1)
        previous = page.evaluate('scrollY')
        page.mouse.wheel(0, -200)
        page.wait_for_function('previous => scrollY < previous - 50', arg=previous)
        evidence['overflowingSlideTopPageScroll'] = page.evaluate('scrollY')
        page.evaluate('window.scrollTo({top:0,behavior:"instant"})')
        point(1)
        page.mouse.wheel(1300, 0)
        page.wait_for_function("document.querySelector('wa-carousel').activeSlide > 1")
        evidence['horizontalWheelSlide'] = carousel.evaluate('e => e.activeSlide')

        # Measure from the actual click event, excluding Playwright's actionability wait.
        page.goto(base + '/releases', wait_until='domcontentloaded')
        timings = []
        for anchor in ['v2-4-0', 'v2-6-0', 'v2-4-0']:
            link = page.locator(f'[slot="menu"] a[href="/releases#{anchor}"]')
            link.evaluate('''(e, id) => {
                window.sectionTiming = null;
                e.addEventListener('click', () => {
                    const start = performance.now();
                    document.getElementById(id).addEventListener('focus', () => {
                        window.sectionTiming = performance.now() - start;
                    }, {once:true});
                }, {once:true});
            }''', anchor)
            link.click()
            page.wait_for_function('window.sectionTiming !== null')
            elapsed = page.evaluate('window.sectionTiming')
            assert elapsed < 250, (anchor, elapsed)
            assert page.evaluate('document.activeElement.id') == anchor
            timings.append(elapsed)
        evidence['sectionClickToFocusMs'] = timings
        assert not errors, errors

        mobile = browser.new_context(viewport={'width':390,'height':844}, is_mobile=True, has_touch=True)
        touch_page = mobile.new_page()
        touch_page.goto(base + '/home', wait_until='domcontentloaded')
        touch_page.wait_for_function("!!customElements.get('wa-scroller') && !!customElements.get('wa-carousel')")
        touch_page.locator('.showcase-navigation wa-button').nth(1).click()
        touch_page.wait_for_timeout(500)
        touch_content = touch_page.locator('wa-scroller').nth(1).locator('div[part="content"]').first
        touch_content.evaluate('e => e.scrollTo({top:e.scrollHeight,behavior:"instant"})')
        touch_page.evaluate('window.scrollTo({top:0,behavior:"instant"})')
        touch_page.wait_for_timeout(150)
        box = touch_content.bounding_box()
        client = mobile.new_cdp_session(touch_page)
        x, y = box['x'] + box['width']/2, min(box['y'] + 160, 650)
        client.send('Input.dispatchTouchEvent', {'type':'touchStart', 'touchPoints':[{'x':x,'y':y}]})
        for distance in [40, 80, 120, 160, 200]:
            client.send('Input.dispatchTouchEvent', {'type':'touchMove', 'touchPoints':[{'x':x,'y':y-distance}]})
            touch_page.wait_for_timeout(40)
        client.send('Input.dispatchTouchEvent', {'type':'touchEnd', 'touchPoints':[]})
        touch_page.wait_for_function('scrollY > 50')
        evidence['touchBottomPageScroll'] = touch_page.evaluate('scrollY')
        (args.output / 'verification.json').write_text(json.dumps(evidence, indent=2) + '\n')
        print('PASS: wheel and touch scroll chaining, horizontal carousel scrolling, immediate section links; ' + json.dumps(evidence))


if __name__ == '__main__':
    main()
