package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.components.NgIf;
import com.guicedee.website.App;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.plugins.markdown.Markdown;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.copybutton.WaCopyButton;
import com.jwebmp.webawesome.components.details.WaDetails;
import com.jwebmp.webawesome.components.divider.WaDivider;
import com.jwebmp.webawesome.components.tag.WaTag;
import com.jwebmp.webawesome.components.text.WaText;
import com.jwebmp.webawesome.components.SpaceTokenCapable;
import com.jwebmp.webawesome.components.BorderTokenCapable;
import com.jwebmp.webawesome.components.TypographyTokenCapable;
import com.jwebmp.webawesome.tokens.WaSpaceToken;
import org.apache.commons.text.StringEscapeUtils;

@NgComponentReference(App.class)
public abstract class WebsitePage<J extends WebsitePage<J>> extends DivSimple<J> implements INgComponent<J>, SpaceTokenCapable<J>, BorderTokenCapable<J>, TypographyTokenCapable<J>
{
    protected WebsitePage()
    {
        addClass("website-content");
        addStyle("padding", "0 var(--wa-spacing-x-large) var(--wa-spacing-x-large) var(--wa-spacing-x-large)");
        addStyle("max-width", "72rem");
    }

    @SuppressWarnings("unchecked")
    protected J getMain()
    {
        return (J) this;
    }

    @SuppressWarnings("unchecked")
    protected J getAside()
    {
        return (J) this;
    }

    @SuppressWarnings("unchecked")
    public J setPageSize(PageSize pageSize)
    {
        return (J) this;
    }

    protected WaGrid<?> grid(int columns)
    {
        var grid = new WaGrid<>();
        grid.setGap(PageSize.Medium);
        if (columns == 2)
        {
            grid.setMinColumnSize("20rem");
        }
        else if (columns >= 3)
        {
            grid.setMinColumnSize("14rem");
        }
        return grid;
    }

    // ── Text helpers ──────────────────────────────────

    protected String escapeAngular(String text)
    {
        if (text == null)
        {
            return null;
        }
        return StringEscapeUtils.escapeHtml4(text)
                                .replace("@", "&#64;")
                                .replace("{", "&#123;")
                                .replace("}", "&#125;")
                                .replace("[", "&#91;")
                                .replace("]", "&#93;")
                                .replace("(", "&#40;")
                                .replace(")", "&#41;")
                                .replace("*", "&#42;")
                                .replace("_", "&#95;");
    }

    /**
     * Converts a title into a URL-friendly slug for use as an HTML id.
     * E.g. "Injection & Lifecycle" → "injection-lifecycle"
     */
    protected static String slugify(String text)
    {
        if (text == null) return null;
        return text.toLowerCase()
                   .replaceAll("[^a-z0-9]+", "-")
                   .replaceAll("^-+|-+$", "");
    }

    protected WaText<?> headingText(String tag, String size, String text)
    {
        var heading = new WaText<>();
        heading.setTag(tag);
        heading.setWaHeading(size);
        heading.setText(escapeAngular(text));
        return heading;
    }

    protected WaText<?> bodyText(String text, String size)
    {
        var body = new WaText<>();
        body.setTag("p");
        body.setWaBody(size == null || size.isBlank() ? "m" : size);
        body.setText(escapeAngular(text));
        return body;
    }

    protected WaText<?> bodyTextHtml(String html, String size)
    {
        var body = new WaText<>();
        body.setTag("p");
        body.setWaBody(size == null || size.isBlank() ? "m" : size);
        body.setText(html.replace("{", "&#123;").replace("}", "&#125;"));
        return body;
    }

    protected static String brandCode(String text)
    {
        return "<code class=\"wa-body-s\" style=\"color:color-mix(in srgb, var(--wa-color-brand) 70%, var(--wa-color-text));\">" +
               text.replace("{", "&#123;").replace("}", "&#125;") +
               "</code>";
    }

    /**
     * Body text rendered via Markdown so that backtick code spans, bold, italic, and
     * links are preserved.
     */
    protected Markdown<?> richText(String markdownText, String size)
    {
        var md = new Markdown<>(markdownText);
        return md;
    }

    protected WaText<?> captionText(String text)
    {
        var caption = new WaText<>();
        caption.setTag("div");
        caption.setWaCaption("s");
        caption.setWaFontWeight("semibold");
        caption.setText(escapeAngular(text));
        return caption;
    }

    // ── Coordinate block helper ─────────────────────────

    /**
     * Creates a styled coordinate display block with groupId:artifactId on the first line,
     * an optional version on the second line, and a copy button.
     * Handles formats: "groupId:artifactId", "groupId:artifactId:version"
     */
    protected DivSimple<?> coordinateBlock(String coordinate)
    {
        var wrapper = new DivSimple<>();
        wrapper.addClass("coordinate-block");

        var textArea = new DivSimple<>();
        textArea.addClass("coordinate-text");

        // Split coordinate into parts
        String[] parts = coordinate.split(":");
        String groupArtifact;
        String version = null;
        if (parts.length >= 3)
        {
            groupArtifact = parts[0] + ":" + parts[1];
            version = parts[2];
        }
        else
        {
            groupArtifact = coordinate;
        }

        var line1 = new WaText<>();
        line1.setTag("span");
        line1.setWaCaption("s");
        line1.setWaFontWeight("semibold");
        line1.setText(escapeAngular(groupArtifact));
        line1.addClass("coordinate-ga");
        textArea.add(line1);

        if (version != null && !version.isBlank())
        {
            var line2 = new WaText<>();
            line2.setTag("span");
            line2.setWaCaption("xs");
            line2.setText(escapeAngular(version));
            line2.addClass("coordinate-version");
            textArea.add(line2);
        }

        wrapper.add(textArea);

        var copyBtn = new WaCopyButton();
        copyBtn.setValue(coordinate);
        copyBtn.setCopyLabel("Copy");
        copyBtn.setSuccessLabel("Copied!");
        copyBtn.setErrorLabel("Failed");
        copyBtn.setTooltipPlacement("top");
        copyBtn.addClass("coordinate-copy");
        wrapper.add(copyBtn);

        return wrapper;
    }

    // ── Component helpers ─────────────────────────────

    protected WaTag<?> buildTag(String label, Variant variant)
    {
        WaTag<?> tag = new WaTag<>();
        tag.setText(escapeAngular(label));
        tag.setVariant(variant);
        tag.setPill(true);
        return tag;
    }

    protected WaButton<?> buildCta(String label, String route, Variant variant, Appearance appearance)
    {
        WaButton<?> button = new WaButton<>(escapeAngular(label), variant);
        if (appearance != null)
        {
            button.setAppearance(appearance);
        }
        var absoluteRoute = route.startsWith("/") ? route : "/" + route;
        button.addAttribute("[routerLink]", "['" + absoluteRoute + "']");
        return button;
    }

    protected WaCard<?> contentCard(String title,
                                    String body,
                                    String note,
                                    Appearance appearance,
                                    String headingSize,
                                    String bodySize)
    {
        var card = new WaCard<>();
        card.setAppearance(appearance);

        var stack = new WaStack<>();
        stack.setGap(PageSize.Small);
        stack.add(headingText("h3", headingSize, title));
        var bodyCopy = bodyText(body, bodySize);
        bodyCopy.setWaColorText("quiet");
        stack.add(bodyCopy);
        if (note != null && !note.isBlank())
        {
            var noteText = captionText(note);
            noteText.setWaColorText("quiet");
            stack.add(noteText);
        }
        card.add(stack);
        return card;
    }

    protected WaCard<?> featureCard(String title, String body, String note)
    {
        var card = new WaCard<>();
        card.setAppearance(Appearance.Outlined);
        card.addClass("feature-card");

        var stack = new WaStack<>();
        stack.setGap(PageSize.Small);

        var titleText = headingText("h3", "m", title);
        titleText.addClass("feature-card-title");
        stack.add(titleText);

        var bodyCopy = bodyText(body, "m");
        bodyCopy.addClass("feature-card-body");
        bodyCopy.setWaColorText("quiet");
        stack.add(bodyCopy);
        if (note != null && !note.isBlank())
        {
            var noteText = captionText(note);
            noteText.addClass("feature-card-body");
            noteText.addClass("feature-card-note");
            noteText.setWaColorText("quiet");
            stack.add(noteText);
        }
        card.add(stack);
        return card;
    }

    protected WaCard<?> featureCardHtml(String title, String bodyHtml, String note)
    {
        var card = new WaCard<>();
        card.setAppearance(Appearance.Outlined);
        card.addClass("feature-card");

        var stack = new WaStack<>();
        stack.setGap(PageSize.Small);

        var titleText = headingText("h3", "m", title);
        titleText.addClass("feature-card-title");
        stack.add(titleText);

        var bodyCopy = bodyTextHtml(bodyHtml, "m");
        bodyCopy.addClass("feature-card-body");
        bodyCopy.setWaColorText("quiet");
        stack.add(bodyCopy);
        if (note != null && !note.isBlank())
        {
            var noteText = bodyTextHtml(note, "s");
            noteText.setTag("div");
            noteText.addClass("feature-card-body");
            noteText.addClass("feature-card-note");
            noteText.setWaColorText("quiet");
            stack.add(noteText);
        }
        card.add(stack);
        return card;
    }

    /**
     * Feature card variant where the note is a Maven coordinate displayed
     * with a two-line layout and a copy button.
     */
    protected WaCard<?> featureCardWithCoordinate(String title, String body, String coordinate)
    {
        var card = new WaCard<>();
        card.setAppearance(Appearance.Outlined);
        card.addClass("feature-card");

        var stack = new WaStack<>();
        stack.setGap(PageSize.Small);

        var titleText = headingText("h3", "m", title);
        titleText.addClass("feature-card-title");
        stack.add(titleText);

        var bodyCopy = bodyText(body, "m");
        bodyCopy.addClass("feature-card-body");
        bodyCopy.setWaColorText("quiet");
        stack.add(bodyCopy);
        if (coordinate != null && !coordinate.isBlank())
        {
            var coord = coordinateBlock(coordinate);
            coord.addClass("feature-card-body");
            stack.add(coord);
        }
        card.add(stack);
        return card;
    }

    // ── Section helpers ───────────────────────────────

    protected WaStack section(String eyebrow, String title, String subtitle,
                              com.jwebmp.core.base.interfaces.IComponentHierarchyBase<?, ?> content)
    {
        var section = new WaStack<>();
        section.setGap(PageSize.Medium);
        section.addClass("content-section");

        // Set a slugified ID from the eyebrow (or title) so aside anchor links can scroll here
        String idSource = eyebrow != null && !eyebrow.isBlank() ? eyebrow : title;
        if (idSource != null && !idSource.isBlank())
        {
            section.setID(slugify(idSource));
        }

        var divider = new WaDivider<>();
        divider.addClass("section-divider");
        section.add(divider);
        section.add(sectionHeader(eyebrow, title, subtitle));
        if (content != null)
        {
            section.add(content);
        }
        return section;
    }

    protected WaStack section(String eyebrow, String title, String subtitle)
    {
        return section(eyebrow, title, subtitle, null);
    }

    protected WaStack sectionHeader(String eyebrow, String title, String subtitle)
    {
        var header = new WaStack<>();
        header.setGap(PageSize.Small);
        if (eyebrow != null && !eyebrow.isBlank())
        {
            var eyebrowText = captionText(eyebrow);
            eyebrowText.addClass("hero-eyebrow");
            header.add(eyebrowText);
        }
        if (title != null && !title.isBlank())
        {
            header.add(headingText("h2", "l", title));
        }
        if (subtitle != null && !subtitle.isBlank())
        {
            var subtitleText = bodyText(subtitle, "m");
            subtitleText.setWaColorText("quiet");
            header.add(subtitleText);
        }
        return header;
    }

    // ── Legacy compat — buildSection delegates to section ──

    protected WaStack buildSection(String eyebrow,
                                     String title,
                                     String subtitle,
                                     boolean alt,
                                     com.jwebmp.core.base.interfaces.IComponentHierarchyBase<?, ?> content)
    {
        return section(eyebrow, title, subtitle, content);
    }

    // ── Diagram helpers ──────────────────────────────

    protected DivSimple<?> mermaidDiagram(String mermaidCode)
    {
        var wrapper = new DivSimple<>();
        wrapper.addClass("mermaid-diagram");
        var md = new Markdown<>("```mermaid\n" + mermaidCode + "\n```");
        md.setMermaid(true);
        wrapper.add(md);
        return wrapper;
    }

    protected DivSimple<?> mermaidDiagramWithTitle(String title, String mermaidCode)
    {
        var wrapper = new DivSimple<>();
        wrapper.addClass("mermaid-diagram-wrapper");
        var label = captionText(title);
        label.addClass("mermaid-diagram-label");
        wrapper.add(label);
        wrapper.add(mermaidDiagram(mermaidCode));
        return wrapper;
    }

    // ── Code block helpers ────────────────────────────

    protected DivSimple<?> codeBlock(String code)
    {
        return codeBlock(code, "java");
    }

    protected DivSimple<?> codeBlock(String code, String language)
    {
        var md = new Markdown<>("```" + language + "\n" + code + "\n```");
        md.addClass("code-block");
        md.addClass("wa-body-s");
        return md;
    }

    protected DivSimple<?> codeBlockWithTitle(String title, String code)
    {
        return codeBlockWithTitle(title, code, "java");
    }

    protected DivSimple<?> codeBlockWithTitle(String title, String code, String language)
    {
        var details = new WaDetails<>();
        details.setSummary(title);
        details.addClass("code-details");

        var md = new Markdown<>("```" + language + "\n" + code + "\n```");
        md.addClass("code-block");
        md.addClass("wa-body-s");
        details.add(md);
        return details;
    }

    protected WaDivider<?> divider()
    {
        return new WaDivider<>();
    }

    // ── Maven / Gradle dual code block helpers ────────

    /**
     * Creates a pair of code blocks — one for Maven (XML) and one for Gradle (Groovy) —
     * toggled by the useGradle field. Both share the same title.
     */
    protected DivSimple<?> mavenGradleCodeBlock(String title, String mavenCode, String gradleCode)
    {
        var details = new WaDetails<>();
        details.setSummary(title);
        details.addClass("code-details");

        var mavenMd = new Markdown<>("```xml\n" + mavenCode + "\n```");
        mavenMd.addClass("code-block");
        mavenMd.addClass("wa-body-s");
        var mavenIf = new NgIf("!app.useGradle()");
        mavenIf.add(mavenMd);
        details.add(mavenIf);

        var gradleMd = new Markdown<>("```groovy\n" + gradleCode + "\n```");
        gradleMd.addClass("code-block");
        gradleMd.addClass("wa-body-s");
        var gradleIf = new NgIf("app.useGradle()");
        gradleIf.add(gradleMd);
        details.add(gradleIf);

        return details;
    }

    /**
     * Creates a pair of code blocks without a title — one for Maven (XML) and one for Gradle (Groovy).
     */
    protected DivSimple<?> mavenGradleCodeBlock(String mavenCode, String gradleCode)
    {
        var wrapper = new DivSimple<>();

        var mavenMd = new Markdown<>("```xml\n" + mavenCode + "\n```");
        mavenMd.addClass("code-block");
        mavenMd.addClass("wa-body-s");
        var mavenIf = new NgIf("!app.useGradle()");
        mavenIf.add(mavenMd);
        wrapper.add(mavenIf);

        var gradleMd = new Markdown<>("```groovy\n" + gradleCode + "\n```");
        gradleMd.addClass("code-block");
        gradleMd.addClass("wa-body-s");
        var gradleIf = new NgIf("app.useGradle()");
        gradleIf.add(gradleMd);
        wrapper.add(gradleIf);

        return wrapper;
    }

    // ── Shared CTA section ──────────────────────────────

    /**
     * Builds the standard "Ready to Build?" call-to-action section
     * with radial gradient background, centered layout, and consistent copy.
     */
    protected WaStack buildCallToAction()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(bodyText(
                "Stop wrestling with configuration files and boilerplate. Build reactive microservices "
                + "with the annotations and patterns you already know.",
                "l"));

        var ctas = new WaCluster<>();
        ctas.setGap(PageSize.Small);
        ctas.addClass("hero-ctas");
        ctas.add(buildCta("Get Started", "/getting-started", Variant.Brand, null));
        ctas.add(buildCta("Capabilities", "/capabilities", Variant.Neutral, Appearance.Outlined));

        var githubCta = new WaButton<>(escapeAngular("View on GitHub"), Variant.Neutral);
        githubCta.setAppearance(Appearance.Outlined);
        githubCta.setAsLink("https://github.com/GuicedEE/", "_blank", null);
        ctas.add(githubCta);
        content.add(ctas);

        var section = buildSection(null,
                "Ready to Build?",
                "From zero to production microservice — with zero XML.",
                false, content);
        section.addClass("cta-section");
        return section;
    }
}
