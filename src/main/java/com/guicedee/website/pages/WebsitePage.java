package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.core.base.html.PreFormattedText;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.divider.WaDivider;
import com.jwebmp.webawesome.components.tag.WaTag;
import com.jwebmp.webawesome.components.text.WaText;
import com.jwebmp.webawesomepro.components.page.WaPage;

public abstract class WebsitePage<J extends WebsitePage<J>> extends WaPage<J> implements INgComponent<J>
{
    protected WebsitePage()
    {
        configureLayout();
    }

    private void configureLayout()
    {
        getHeader().add("GuicedEE — Modular Java, Reactive by Default");
        configureMenu();
        configureFooter();
    }

    private void configureMenu()
    {
        var menu = getMenu();
        menu.add(createNavLink("Home", "/home"));
        menu.add(createNavLink("Get Started", "/getting-started"));
        menu.add(createNavLink("End-to-End Guide", "/guides/end-to-end"));
        menu.add(createNavLink("Modules", "/modules"));
        menu.add(createNavLink("Capabilities", "/capabilities"));
        menu.add(createNavLink("Services", "/services"));
        menu.add(createNavLink("App Builder", "/builder"));
        menu.add(createNavLink("Releases", "/releases"));
        menu.add(createNavLink("GitHub", "/github"));
    }

    private void configureFooter()
    {
        var footer = getFooter();
        var footerStack = new WaStack();
        footerStack.setGap(PageSize.Medium);

        var links = new WaCluster<>();
        links.setGap(PageSize.Medium);
        links.add(createFooterLink("GitHub", "https://github.com/GuicedEE"));
        links.add(createFooterLink("Maven Central", "https://central.sonatype.com/GuicedEE/com.guicedee"));
        links.add(createFooterLink("Apache 2.0 License", "https://www.apache.org/licenses/LICENSE-2.0"));
        footerStack.add(links);

        var copyright = new WaText<>();
        copyright.setWaCaption("s");
        copyright.setWaColorText("quiet");
        copyright.setText("© 2025 GuicedEE Contributors · Built with JWebMP & WebAwesome · Java 25+ · Vert.x 5");
        footerStack.add(copyright);

        footer.add(footerStack);
    }

    private Link<?> createNavLink(String label, String route)
    {
        var link = new Link<>();
        link.addClass("wa-page-menu-link");
        link.addAttribute("[routerLink]", "['" + route + "']");
        link.add(label);
        return link;
    }

    private Link<?> createFooterLink(String label, String url)
    {
        var link = new Link<>();
        link.addAttribute("href", url);
        link.addAttribute("target", "_blank");
        link.addAttribute("rel", "noopener noreferrer");
        link.addClass("footer-link");
        link.add(label);
        return link;
    }

    protected WaStack newMainSection()
    {
        var stack = new WaStack();
        stack.setGap(PageSize.Medium);
        getMain().add(stack);
        return stack;
    }

    // ── Shared component helpers used across all pages ──

    protected WaText<?> headingText(String tag, String size, String text)
    {
        var heading = new WaText<>();
        heading.setTag(tag);
        heading.setWaHeading(size);
        heading.setText(text);
        return heading;
    }

    protected WaText<?> bodyText(String text, String size)
    {
        var body = new WaText<>();
        body.setTag("p");
        body.setWaBody(size == null || size.isBlank() ? "m" : size);
        body.setText(text);
        return body;
    }

    protected WaText<?> captionText(String text)
    {
        var caption = new WaText<>();
        caption.setTag("div");
        caption.setWaCaption("s");
        caption.setWaFontWeight("semibold");
        caption.setText(text);
        return caption;
    }

    protected WaTag<?> buildTag(String label, Variant variant)
    {
        WaTag<?> tag = new WaTag<>();
        tag.setText(label);
        tag.setVariant(variant);
        tag.setPill(true);
        return tag;
    }

    protected WaButton<?> buildCta(String label, String route, Variant variant, Appearance appearance)
    {
        WaButton<?> button = new WaButton<>(label, variant);
        if (appearance != null)
        {
            button.setAppearance(appearance);
        }
        button.addAttribute("[routerLink]", "['" + route + "']");
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

        var stack = new WaStack();
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
        return contentCard(title, body, note, Appearance.Outlined, "m", "m");
    }

    protected WaCard<?> buildSection(String eyebrow,
                                     String title,
                                     String subtitle,
                                     boolean alt,
                                     com.jwebmp.core.base.interfaces.IComponentHierarchyBase<?, ?> content)
    {
        var section = new WaCard<>();
        section.setAppearance(alt ? Appearance.Filled : Appearance.Outlined);

        var stack = new WaStack();
        stack.setGap(PageSize.Medium);
        stack.add(sectionHeader(eyebrow, title, subtitle));
        stack.add(content);
        section.add(stack);
        return section;
    }

    protected WaStack sectionHeader(String eyebrow, String title, String subtitle)
    {
        var header = new WaStack();
        header.setGap(PageSize.Small);
        if (eyebrow != null && !eyebrow.isBlank())
        {
            header.add(captionText(eyebrow));
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

    protected DivSimple<?> codeBlock(String code)
    {
        var wrapper = new DivSimple<>();
        wrapper.addClass("code-block");
        var pre = new PreFormattedText<>();
        pre.setText(code);
        wrapper.add(pre);
        return wrapper;
    }

    protected DivSimple<?> codeBlockWithTitle(String title, String code)
    {
        var wrapper = new DivSimple<>();
        wrapper.addClass("code-block-wrapper");

        var label = captionText(title);
        label.addClass("code-block-label");
        wrapper.add(label);

        var pre = new PreFormattedText<>();
        pre.setText(code);
        pre.addClass("code-block");
        wrapper.add(pre);
        return wrapper;
    }

    protected WaDivider<?> divider()
    {
        return new WaDivider<>();
    }
}
