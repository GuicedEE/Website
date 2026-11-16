package com.guicedee.website;

import com.guicedee.website.pages.*;
import com.jwebmp.core.base.ComponentHierarchyBase;
import com.jwebmp.plugins.markdown.Markdown;
import com.jwebmp.core.base.angular.components.NgIf;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Builds search data from the same Java component trees as the displayed pages. */
final class SiteSearchIndex {
    private record Page(String route, String title, Supplier<WebsitePage<?>> create) {}
    private static final List<Page> PAGES = List.of(
            new Page("home", "Home", HomePage::new),
            new Page("releases", "Releases", ReleasesPage::new),
            new Page("builder", "App Builder", AppBuilderPage::new),
            new Page("getting-started", "Getting Started", GettingStartedPage::new),
            new Page("guides/end-to-end", "End-to-End Guide", EndToEndGuidePage::new),
            new Page("capabilities", "Capabilities", CapabilitiesPage::new),
            new Page("modules", "Modules", ModulesPage::new),
            new Page("services", "Services", ServicesPage::new),
            new Page("cloud", "Cloud", CloudPage::new),
            new Page("environment-variables", "Environment Variables", EnvironmentVariablesPage::new));

    private static final class Entry {
        final String anchor, title;
        final StringBuilder text = new StringBuilder();
        Entry(String anchor, String title) { this.anchor = anchor; this.title = title; }
    }

    static String javascript() {
        List<String> json = new ArrayList<>();
        for (Page page : PAGES) {
            List<Entry> entries = new ArrayList<>();
            collect(page.create.get(), entries);
            for (Entry entry : entries) {
                json.add("{route:" + quote(page.route) + ",page:" + quote(page.title)
                        + ",anchor:" + quote(entry.anchor) + ",title:" + quote(entry.title)
                        + ",text:" + quote(entry.text.toString().strip()) + "}");
            }
        }
        return "[" + String.join(",", json) + "]";
    }

    private static void collect(ComponentHierarchyBase<?, ?, ?, ?, ?> node, List<Entry> entries) {
        // Remote README dialogs are not page content, and their contents are not shipped locally.
        if (List.of("wa-dialog", "wa-tooltip", "wa-popover").contains(node.getTag())) return;
        // Search must not change builder selections or the Maven/Gradle preference to reveal a hit.
        if (node instanceof NgIf<?>) return;
        if (node.getClasses().contains("hero-eyebrow")) return;
        String anchor = node.getAttributes().get("data-search-anchor");
        if (anchor != null) entries.add(new Entry(anchor, plain(node.getText())));
        if (!entries.isEmpty()) {
            String text = node instanceof Markdown<?> markdown ? markdown.getMarkdownContent() : node.getText();
            String clean = plain(text);
            if (!clean.isBlank()) entries.getLast().text.append(clean).append(' ');
        }
        for (Object child : node.getChildren()) {
            if (child instanceof ComponentHierarchyBase<?, ?, ?, ?, ?> component) collect(component, entries);
        }
    }

    private static String plain(String text) {
        if (text == null) return "";
        return StringEscapeUtils.unescapeHtml4(text.replaceAll("<[^>]*>", " "))
                .replaceAll("\\{\\{.*?}}", " ").replaceAll("\\s+", " ").strip();
    }

    private static String quote(String text) {
        return "\"" + StringEscapeUtils.escapeJson(text).replace("<", "\\u003c") + "\"";
    }
}
