#!/usr/bin/env python3
"""Check crawler content against the real production nginx configuration.

Requires Docker, a built Angular dist, and Python Playwright/Chromium.
The temporary container binds only to localhost and is removed in finally.
"""
import argparse
import hashlib
import json
import re
import subprocess
import time
import urllib.error
import urllib.parse
import urllib.request
import urllib.robotparser
import xml.etree.ElementTree as ET
from html.parser import HTMLParser
from pathlib import Path

SITE = Path(__file__).resolve().parents[1]
DIST = SITE / 'target/webroot/guicedee-website/dist/jwebmp/browser'
CONF = SITE / 'src/main/nginx/site.conf'


class Head(HTMLParser):
    def __init__(self, html):
        super().__init__()
        self.links, self.meta, self.scripts = [], [], []
        self.feed(html)

    def handle_starttag(self, tag, attrs):
        item = dict(attrs)
        if tag == 'link': self.links.append(item)
        if tag == 'meta': self.meta.append(item)
        if tag == 'script': self.scripts.append(item)


class NoRedirect(urllib.request.HTTPRedirectHandler):
    def redirect_request(self, *args):
        return None


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--output', type=Path, default=SITE / 'target/site-discovery-verification.json')
    parser.add_argument('--image', help='Verify a locally built website image instead of mounting the dist/config')
    args = parser.parse_args()
    assert (DIST / 'llms-full.txt').is_file(), 'Build the website first'
    assert 'nginxConfigFile' in (SITE / 'pom.xml').read_text()
    command = ['docker', 'run', '--detach', '--rm', '--publish', '127.0.0.1::80']
    if not args.image:
        command += ['--mount', f'type=bind,src={DIST},dst=/usr/share/nginx/html,readonly',
                    '--mount', f'type=bind,src={CONF},dst=/etc/nginx/conf.d/default.conf,readonly']
    container = subprocess.check_output(command + [args.image or 'nginx:alpine'], text=True).strip()
    report = {'checks': [], 'nginxSha256': hashlib.sha256(CONF.read_bytes()).hexdigest()}
    try:
        actual_config = subprocess.check_output(['docker', 'exec', container, 'cat', '/etc/nginx/conf.d/default.conf'])
        assert actual_config == CONF.read_bytes(), 'Packaged nginx configuration does not match the website source'
        for _ in range(30):
            try:
                port = subprocess.check_output(['docker', 'port', container, '80/tcp'], text=True).strip().split(':')[-1]
                urllib.request.urlopen(f'http://127.0.0.1:{port}/robots.txt', timeout=2).read()
                break
            except (OSError, subprocess.CalledProcessError): time.sleep(.2)
        base = f'http://127.0.0.1:{port}'
        opener = urllib.request.build_opener(NoRedirect)
        def fetch(path, status=200, headers=None, method='GET'):
            request = urllib.request.Request(base + path, headers=headers or {}, method=method)
            try: response = opener.open(request, timeout=15)
            except urllib.error.HTTPError as error: response = error
            with response:
                body = response.read().decode('utf-8')
                assert response.status == status, (path, response.status, body[:160])
                report['checks'].append({'path': path, 'method': method, 'status': response.status, 'type': response.headers.get('Content-Type'), 'cache': response.headers.get('Cache-Control')})
                return body, response.headers

        robots, headers = fetch('/robots.txt')
        assert headers.get_content_type() == 'text/plain'
        rp = urllib.robotparser.RobotFileParser()
        rp.parse(robots.splitlines())
        assert rp.site_maps() == ['https://guicedee.com/sitemap.xml']
        for user_agent in ('Googlebot', 'Bingbot', 'GPTBot', 'OAI-SearchBot', 'ClaudeBot', '*'):
            for path in ('/home', '/llms.txt', '/modules.md', '/main-example.js'):
                assert rp.can_fetch(user_agent, 'https://guicedee.com' + path), (user_agent, path)
        sitemap, headers = fetch('/sitemap.xml')
        assert headers.get_content_type() == 'application/xml'
        routes = [urllib.parse.urlsplit(node.text).path for node in ET.fromstring(sitemap).findall('{*}url/{*}loc')]
        assert len(routes) == len(set(routes)) == 10
        for route in routes:
            html, headers = fetch(route)
            head = Head(html)
            expected = 'https://guicedee.com' + route
            assert [x['href'] for x in head.links if x.get('rel') == 'canonical'] == [expected], route
            assert any(x.get('rel') == 'alternate' and x.get('href') == expected + '.md' for x in head.links)
            assert any(x.get('name') == 'description' and len(x.get('content', '')) > 60 for x in head.meta)
            assert any(x.get('rel') == 'describedby' and x.get('href') == '/llms.txt' for x in head.links)
            assert any(x.get('type') == 'application/ld+json' for x in head.scripts)
            assert 'no-store' in headers['Cache-Control']
            text, headers = fetch(route + '.md')
            assert headers.get_content_type() == 'text/markdown' and text.startswith('# ')
            assert '{{' not in text
        for path in ('/', '/index.html', '/modules?source=search', '/modules/', '/modules(aside:modules)', '/guides/end-to-end(aside:guides/end-to-end)'):
            html, headers = fetch(path)
            assert len([x for x in Head(html).links if x.get('rel') == 'canonical']) == 1
        for path in ('/does-not-exist-crawler-audit', '/missing.js', '/missing.md', '/reference/missing.md', '/not-real.xml', '/modules(aside:missing)', '/modules/nonsense'):
            fetch(path, 404)
        _, headers = fetch('/modules?x=1', 308, {'Host': 'www.guicedee.com'})
        assert headers['Location'] == 'https://guicedee.com/modules?x=1'
        _, headers = fetch('/modules?x=1', 308, {'X-Forwarded-Proto': 'http'})
        assert headers['Location'] == 'https://guicedee.com/modules?x=1'
        fetch('/modules', headers={'X-Forwarded-Proto': 'https'})
        _, headers = fetch('/favicon.ico', 308)
        assert headers['Location'].endswith('/guicedee-logo.svg')
        text, headers = fetch('/manifest.webmanifest')
        manifest = json.loads(text)
        assert headers.get_content_type() == 'application/manifest+json'
        fetch(manifest['icons'][0]['src'])
        index, headers = fetch('/llms.txt')
        assert index.startswith('# GuicedEE\n\n> ')
        assert headers.get_content_type() == 'text/plain'
        assert 'immutable' not in headers['Cache-Control'] and 'max-age=300' in headers['Cache-Control']
        assert 'describedby' in headers['Link']
        fetch('/llms.txt', method='HEAD')
        full, headers = fetch('/llms-full.txt')
        assert len(full) > 500_000 and '# Website documentation' in full
        paths = sorted(set(urllib.parse.urlsplit(url).path for url in re.findall(r'\]\((https://guicedee.com/[^)]+)\)', index)))
        for path in paths:
            if path in ('/llms-full.txt',): continue
            fetch(path)
        inventory = json.loads(fetch('/reference/inventory.json')[0])
        assert len(inventory['components']) >= 127
        for item in inventory['components']:
            assert item['document'] in index
            if item['packaging'] == 'jar': assert item['jpmsModule'], item
        for artifact, name in [('firebase-admin', 'firebase.admin'), ('commercetools', 'commercetools.sdk.java.api'), ('kotlin', 'com.guicedee.kotlin')]:
            assert any(x['artifactId'] == artifact and x['jpmsModule'] == name for x in inventory['components'])
        _, headers = fetch('/features.css')
        assert 'immutable' not in headers['Cache-Control']
        bundle = next(DIST.glob('main-*.js')).name
        _, headers = fetch('/' + bundle)
        assert 'immutable' in headers['Cache-Control']
        from playwright.sync_api import sync_playwright
        with sync_playwright() as pw:
            browser = pw.chromium.launch()
            page = browser.new_page()
            errors = []
            page.on('pageerror', lambda error: errors.append(str(error)))
            page.goto(base + '/modules', wait_until='networkidle')
            page.wait_for_function("document.title === 'Modules | GuicedEE'")
            assert page.locator('link[rel=canonical]').count() == 1
            assert page.locator('link[rel=canonical]').get_attribute('href') == 'https://guicedee.com/modules'
            page.locator('wa-button[routerlink="/home"]').first.click()
            page.wait_for_function("document.title === 'Home | GuicedEE'")
            assert page.locator('link[rel=canonical]').get_attribute('href') == 'https://guicedee.com/home'
            assert page.locator('link[rel=alternate]').get_attribute('href') == 'https://guicedee.com/home.md'
            assert not errors, errors
            browser.close()
        report['components'] = len(inventory['components'])
        report['image'] = args.image
        report['browserNavigation'] = 'passed'
        args.output.parent.mkdir(parents=True, exist_ok=True)
        args.output.write_text(json.dumps(report, indent=2) + '\n', encoding='utf-8')
        print(f"Passed {len(report['checks'])} HTTP checks, {len(inventory['components'])} components, crawler rules and browser metadata navigation")
    finally:
        subprocess.run(['docker', 'rm', '--force', container], capture_output=True, check=False)


if __name__ == '__main__':
    main()
