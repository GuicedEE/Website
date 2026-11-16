package com.guicedee.website;

import com.jwebmp.core.base.angular.client.AppUtils;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

/** Publishes crawler resources from the same component trees used by the UI. */
final class SiteDiscovery {
    private SiteDiscovery() {}

    static void writeResources() {
        var sitemap = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
        var full = new StringBuilder(resource("/src/assets/llms-full.txt"));
        full.append("\n\n---\n\n# Website documentation\n\nCharts are interactive; benchmark numbers and methodology are available at https://github.com/GuicedEE/benchmarks.\n\n");
        for (var page : SiteSearchIndex.PAGES) {
            String markdown = SiteSearchIndex.markdown(page);
            write(page.route() + ".md", markdown);
            full.append("\n\n---\n\n").append(markdown);
            sitemap.append("  <url><loc>https://guicedee.com/").append(page.route()).append("</loc></url>\n");
        }
        sitemap.append("</urlset>\n");
        write("sitemap.xml", sitemap.toString());
        write("llms-full.txt", full.toString());
    }

    private static String resource(String path) {
        try (var input = SiteDiscovery.class.getResourceAsStream(path)) {
            if (input == null) throw new IllegalStateException("Missing crawler reference: " + path);
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException error) { throw new IllegalStateException(error); }
    }

    private static void write(String name, String text) {
        AppUtils.saveAsset(WebsiteApplication.class, new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), "src/assets/" + name);
    }

    static String metadataMethod() {
        String titles = SiteSearchIndex.PAGES.stream().map(page -> "'" + page.route() + "': '" + page.title() + "'").collect(java.util.stream.Collectors.joining(","));
        return """
                private updateSiteMetadata(url: string): void {
                    const tree = this.router.parseUrl(url);
                    const path = (tree.root.children['primary']?.segments || []).map(s => s.path).join('/') || 'home';
                    const titles: Record<string, string> = {TITLES};
                    const known = Object.hasOwn(titles, path);
                    const canonical = 'https://guicedee.com/' + (known ? path : 'home');
                    const title = known ? titles[path] + ' | GuicedEE' : 'Page not found | GuicedEE';
                    this.document.title = title;
                    const link = (rel: string, href: string, type?: string) => {
                        let element = this.document.head.querySelector<HTMLLinkElement>('link[rel="' + rel + '"]');
                        if (!element) { element = this.document.createElement('link'); element.rel = rel; this.document.head.appendChild(element); }
                        element.href = href;
                        if (type) element.type = type;
                    };
                    link('canonical', canonical);
                    link('alternate', canonical + '.md', 'text/markdown');
                    const setMeta = (key: string, value: string, attribute = 'name') => {
                        let element = this.document.head.querySelector<HTMLMetaElement>('meta[' + attribute + '="' + key + '"]');
                        if (!element) { element = this.document.createElement('meta'); element.setAttribute(attribute, key); this.document.head.appendChild(element); }
                        element.content = value;
                    };
                    setMeta('robots', known ? 'index, follow' : 'noindex, follow');
                    setMeta('og:url', canonical, 'property');
                    setMeta('og:title', title, 'property');
                    setMeta('twitter:title', title);
                }
                """.replace("TITLES", titles);
    }
}
