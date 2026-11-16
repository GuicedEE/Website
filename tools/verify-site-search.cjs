/* Run after building the website. Set PLAYWRIGHT_MODULE if Playwright is installed elsewhere. */
const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');
const assert = require('node:assert/strict');
const {chromium} = require(process.env.PLAYWRIGHT_MODULE || 'playwright');
const root = path.resolve(__dirname, '../target/webroot/guicedee-website/dist/jwebmp/browser');
const output = path.resolve(__dirname, '../target');
const server = http.createServer((req, res) => {
    let file = path.join(root, new URL(req.url, 'http://localhost').pathname);
    if (!fs.existsSync(file) || fs.statSync(file).isDirectory()) file = path.join(root, 'index.html');
    res.setHeader('Content-Type', ({'.html':'text/html','.js':'text/javascript','.css':'text/css','.json':'application/json','.svg':'image/svg+xml'})[path.extname(file)] || 'application/octet-stream');
    fs.createReadStream(file).pipe(res);
});
server.listen(0, '127.0.0.1', async () => {
    let browser, page;
    try {
        browser = await chromium.launch({headless:true});
        page = await browser.newPage({viewport:{width:1440,height:1050}, reducedMotion:'reduce'});
        page.setDefaultTimeout(15000);
        const errors = [];
        page.on('pageerror', error => { errors.push(error.message); console.error('Page error:', error.message); });
        await page.goto(`http://127.0.0.1:${server.address().port}/home`);
        await page.waitForFunction(() => !!customElements.get('wa-input') && !!customElements.get('wa-carousel'));
        await page.waitForTimeout(2500);
        const search = page.locator('[slot="menu"] .site-search');
        const input = search.locator('wa-input input');
        const home = page.locator('[slot="menu"] a[routerLink="/home"]');
        assert((await search.boundingBox()).y < (await home.boundingBox()).y, 'Search must be above Home');
        const searchBox = await search.locator('wa-input').boundingBox();
        const selectorBox = await page.locator('.showcase-navigation wa-button').first().boundingBox();
        assert(Math.abs(searchBox.y + searchBox.height / 2 - selectorBox.y - selectorBox.height / 2) < 3,
            `Search and selectors must align: ${JSON.stringify({searchBox, selectorBox})}`);
        await search.locator('.site-search-icon svg').waitFor();
        const homeBefore = await home.boundingBox();
        await input.fill('Kotlin');
        const kotlin = search.locator('[data-search-destination="home#find-your-stack-now-in-kotlin"]');
        await kotlin.waitFor();
        assert((await kotlin.innerText()).includes('Home'));
        assert(Math.abs((await home.boundingBox()).y - homeBefore.y) < 1, 'Results must not push Home down');
        await page.screenshot({path:path.join(output,'site-search-results.png')});
        await page.locator('.showcase-navigation wa-button').first().click();
        await search.locator('.site-search-results').waitFor({state:'hidden'});
        await input.click();
        await kotlin.waitFor();
        await kotlin.click();
        await page.locator('#find-your-stack-now-in-kotlin.site-search-target').waitFor();
        await page.waitForFunction(() => document.querySelector('.performance-carousel').activeSlide === 6);
        assert((await page.url()).includes('#find-your-stack-now-in-kotlin'));
        assert.equal(await page.evaluate(() => document.activeElement.id), 'find-your-stack-now-in-kotlin');
        await page.screenshot({path:path.join(output,'site-search-kotlin.png')});

        await input.fill('firebase');
        await search.locator('[data-search-destination="services#find-firebase-admin"]').click();
        await page.locator('#find-firebase-admin.site-search-target').waitFor();
        assert((await page.url()).includes('services'));
        assert(await page.locator('guicedee-services-aside').count());
        await page.reload();
        await page.locator('#find-firebase-admin.site-search-target').waitFor();

        await input.fill('One env var switches');
        await search.locator('[data-search-destination="home#find-one-env-var-switches-to-production-ready-json"]').click();
        const expanded = page.locator('#explore-platform');
        await page.waitForFunction(() => document.querySelector('#explore-platform')?.open === true);
        await page.locator('#find-one-env-var-switches-to-production-ready-json.site-search-target').waitFor();
        assert(await expanded.isVisible());

        await input.fill('GUICEDEE_STARTUP_TIMEOUT_SECONDS');
        const envResult = search.locator('.site-search-result').first();
        await envResult.getByText(/GUICEDEE_STARTUP_TIMEOUT_SECONDS/).waitFor();
        assert((await envResult.innerText()).includes('GUICEDEE_STARTUP_TIMEOUT_SECONDS'));
        await input.press('ArrowDown');
        await page.keyboard.press('Enter');
        await page.locator('guicedee-environment-variables .site-search-target').waitFor();
        assert((await page.url()).includes('environment-variables'));
        await page.goBack();
        await page.locator('#find-one-env-var-switches-to-production-ready-json.site-search-target').waitFor();

        await input.fill('no-such-guicedee-feature-123456789');
        await search.getByText('No matches. Try a module, feature or annotation.').waitFor();
        assert.equal(await search.locator('.site-search-result').count(), 0);
        await input.fill('<img src=x onerror=alert(1)>');
        assert.equal(await search.locator('img').count(), 0);
        await input.press('Escape');
        assert(await search.locator('.site-search-results').isHidden());
        await input.fill('');
        await page.waitForTimeout(100);
        assert(await search.locator('.site-search-results').isHidden());

        await page.setViewportSize({width:390,height:844});
        await page.locator('wa-page').evaluate(el => { el.navOpen = true; });
        const mobile = page.locator('[slot="navigation"] .site-search');
        await mobile.locator('wa-input input').fill('Kotlin');
        await mobile.locator('[data-search-destination="home#find-your-stack-now-in-kotlin"]').click();
        await page.locator('#find-your-stack-now-in-kotlin.site-search-target').waitFor();
        assert.equal(await page.locator('wa-page').evaluate(el => el.navOpen), false);
        assert(await page.evaluate(() => document.documentElement.scrollWidth <= innerWidth + 1));
        await page.screenshot({path:path.join(output,'site-search-mobile.png')});
        assert.deepEqual(errors, []);
        console.log('PASS: results and snippets, carousel reveal, cross-page/aside navigation, reload/back fragments, collapsed sections, environment variables, keyboard, no matches, escaping, clear/Escape, mobile drawer and width');
    } catch (error) {
        console.error(error);
        if (page) {
            console.log('Location:', page.url());
            console.log('Reveal state:', await page.evaluate(() => ({
                details: [...document.querySelectorAll('wa-details')].map(el => ({id:el.id,open:el.open})),
                highlighted: document.querySelector('.site-search-target')?.id,
                target: document.querySelector('#find-one-env-var-switches-to-production-ready-json')?.outerHTML
            })));
            await page.screenshot({path:path.join(output,'site-search-failure.png')});
        }
        process.exitCode = 1;
    }
    finally { if (browser) await browser.close(); server.close(); }
});
