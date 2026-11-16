const http = require('node:http'), fs = require('node:fs'), path = require('node:path'), assert = require('node:assert/strict');
const {chromium} = require(process.env.PLAYWRIGHT_MODULE || 'playwright');
const JSZip = require('../target/webroot/guicedee-website/node_modules/jszip');
const root = path.resolve(__dirname, '../target/webroot/guicedee-website/dist/jwebmp/browser');
const server = http.createServer((req, res) => {
  let file = path.join(root, new URL(req.url, 'http://localhost').pathname);
  if (!fs.existsSync(file) || fs.statSync(file).isDirectory()) file = path.join(root, 'index.html');
  res.setHeader('Content-Type', ({'.html':'text/html','.js':'text/javascript','.css':'text/css','.json':'application/json','.svg':'image/svg+xml'})[path.extname(file)] || 'application/octet-stream');
  fs.createReadStream(file).pipe(res);
});
server.listen(0, '127.0.0.1', async () => {
  let browser;
  try {
    browser = await chromium.launch({headless:true});
    const page = await browser.newPage({acceptDownloads:true});
    const errors = [];
    page.on('pageerror', error => errors.push(error.message));
    await page.goto(`http://127.0.0.1:${server.address().port}/builder`);
    await page.waitForTimeout(1500);
    await page.locator('wa-select[label="Language"]').click();
    await page.getByRole('option', {name:'Kotlin',exact:true}).click();
    await page.waitForFunction(() => document.querySelector('wa-select[label="Language"]').value === 'kotlin');
    for (const nativeSdk of [false, true]) {
    if (nativeSdk) {
      await page.locator('.builder-module-tree > wa-tree-item').filter({hasText:'Cloud SDKs'}).evaluate(el => { el.expanded = true; });
      await page.getByText('Firebase Admin', {exact:true}).click();
      await page.getByText('Commercetools SDK', {exact:true}).click();
      await page.locator('wa-select[label="Runtime target"]').click();
      await page.getByRole('option', {name:'Native image starter (SPI)'}).click();
    }
    for (const gradle of [false, true]) {
      await page.locator('wa-switch[name="useGradle"]').evaluate((el, value) => {
        el.checked=value; el.dispatchEvent(new CustomEvent('wa-change', {bubbles:true}));
      }, gradle);
      const download = page.waitForEvent('download');
      await page.getByText('Download ZIP', {exact:true}).click();
      const zip = await JSZip.loadAsync(fs.readFileSync(await (await download).path()));
      assert(zip.file('my-service/src/main/kotlin/com/example/myservice/Boot.kt'), Object.keys(zip.files).join(', '));
      assert(zip.file('my-service/src/test/kotlin/com/example/myservice/test/BootTest.kt'));
      assert(!zip.file('my-service/src/main/java/com/example/myservice/Boot.java'));
      const build = await zip.file('my-service/' + (gradle ? 'build.gradle.kts' : 'pom.xml')).async('string');
      assert(build.includes('2.4.21'));
      if (nativeSdk) {
        assert(zip.file('my-service/NativeBuild.java'));
        assert(build.includes('firebase-admin') && build.includes('commercetools'));
        assert((await zip.file('my-service/src/main/kotlin/com/example/myservice/Boot.kt').async('string')).includes('GUICEDEE_DISCOVERY_MODE'));
      }
      const out = path.resolve(__dirname, '../target/kotlin-generated', (gradle ? 'gradle' : 'maven') + (nativeSdk ? '-native-sdk' : ''));
      for (const [name,file] of Object.entries(zip.files)) {
        if (file.dir) continue;
        const dest = path.join(out,name);
        fs.mkdirSync(path.dirname(dest), {recursive:true});
        fs.writeFileSync(dest, await file.async('nodebuffer'));
      }
    }
    }
    assert.deepEqual(errors, []);
    console.log('PASS: Kotlin selection and downloaded Maven/Gradle sources, descriptors and tests');
  } catch(error) {console.error(error);process.exitCode=1;}
  finally {if(browser) await browser.close();server.close();}
});
