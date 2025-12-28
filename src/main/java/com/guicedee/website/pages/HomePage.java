package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.ListItem;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaFlank;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaSplit;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.tag.WaTag;
import com.jwebmp.webawesome.components.text.WaText;

@NgComponent("guicedee-home")
@NgRoutable(path = "home")
public class HomePage extends WebsitePage<HomePage> implements INgComponent<HomePage>
{
    public HomePage()
    {
        buildLandingPage();
    }

    private void buildLandingPage()
    {
        getMain().setPageSize(PageSize.ExtraLarge);

        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildHero());
        layout.add(buildAudienceSection());
        layout.add(buildFoundationSection());
        layout.add(buildModularBuildSection());
        layout.add(buildControlSection());
        layout.add(buildPluginSection());
        layout.add(buildCommonsSection());
        layout.add(buildDeveloperSection());
        layout.add(buildCallToActionSection());
    }

    private WaCard<?> buildHero()
    {
        var hero = new WaCard<>();
        hero.setAppearance(Appearance.Filled);

        var row = new WaSplit();
        row.row();
        row.setGap(PageSize.Large);
        row.alignItems("center");

        var copy = new WaStack();
        copy.setGap(PageSize.Medium);

        var tags = new WaCluster();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("GuicedEE", Variant.Brand));
        tags.add(buildTag("Reactive on Vert.x 5", Variant.Success));
        tags.add(buildTag("JDK 25", Variant.Neutral));
        tags.add(buildTag("Level 3 Modular", Variant.Neutral));
        copy.add(tags);

        copy.add(headingText("h1", "xl", "Modular enterprise Java, reactive by default."));

        var subtitle = bodyText("GuicedEE targets JDK 25 and Vert.x 5 with modular components designed for a lean Java module path and straightforward enterprise use.", "l");
        subtitle.setWaColorText("quiet");
        copy.add(subtitle);

        var ctas = new WaCluster();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("Build an app", "builder", Variant.Brand, Appearance.Filled));
        ctas.add(buildCta("Explore modules", "capabilities", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("See releases", "releases", Variant.Neutral, Appearance.Outlined));
        copy.add(ctas);

        var followup = bodyText("A modular, reactive stack that keeps runtime size and wiring simple while staying enterprise friendly.", "m");
        followup.setWaColorText("quiet");
        copy.add(followup);

        var stats = new WaGrid<>();
        stats.setMinColumnSize("14rem");
        stats.setGap(PageSize.Small);
        stats.add(heroCard("Vert.x 5 runtime", "Non-blocking throughput with a modern reactive core."));
        stats.add(heroCard("Instant modules", "Repackaged services ready for JLink, JMod, and JPackage."));
        stats.add(heroCard("Lean runtime footprint", "Designed for small container targets (128 MB, 0.5 CPU)."));
        stats.add(heroCard("AI rules repository", "Guidance for AI-assisted development patterns."));

        row.add(copy);
        row.add(stats);
        hero.add(row);
        return hero;
    }

    private WaCard<?> buildFoundationSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);
        grid.add(featureCard("Modular enterprise",
                "Build and deploy complete modular enterprise applications with Guice and Vert.x 5.",
                "Level 3 modular across every artifact in the stack."));
        grid.add(featureCard("Packaging compatibility",
                "Repackaged services stay compatible with JLink, JPackage, and JMod distributables without touching the managed Guice layer.",
                "Stay up to date and module safe."));
        grid.add(featureCard("Reactive by default",
                "Reactive on Vert.x 5 with fully async IO and modern backpressure.",
                "Built for modern runtime constraints."));
        grid.add(featureCard("Instant modules",
                "Grab aligned modules for OpenAPI, Swagger, persistence, and messaging as pre-aligned modules.",
                "Always compatible, always modular."));

        return buildSection("What is this", "Modular Java, straightforward",
                "GuicedEE provides a modular enterprise stack with instant modules and a smaller runtime footprint.",
                false,
                grid);
    }

    private WaCard<?> buildAudienceSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);
        grid.add(featureCard("Developers",
                "Ship features with a reactive Vert.x 5 core, modular service packs, and zero XML configuration.",
                "Build, test, and deploy in one flow."));
        grid.add(featureCard("Architects",
                "Compose clean module graphs, enforce boundaries, and produce smaller runtime images with JLink and JPackage.",
                "Designed for modular governance."));
        grid.add(featureCard("CxOs",
                "Reduce runtime footprint and avoid vendor lock-in while keeping delivery predictable.",
                "Lower operational overhead with predictable agility."));

        return buildSection("Project Focus", "Built for IDEs, developers, engineers, and architects",
                "GuicedEE balances delivery cadence, platform governance, and operational cost in a single modular stack.",
                true,
                grid);
    }

    private WaCard<?> buildModularBuildSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);
        grid.add(featureCard("Deploy the JRE",
                "Build Java modular system artifacts and deploy inside a fully modular environment.",
                "JMod, JLink, and JPackage out of the box."));
        grid.add(featureCard("Micro modules",
                "Each module-info is handcrafted and built for its exact purpose. Add only what you need.",
                "The rest module alone is enough for a microservice stack."));
        grid.add(featureCard("Grouped singular dependencies",
                "If a library is the only one to require a dependency, it is wrapped to encapsulate packages and expose a minimal API.",
                "Smaller module-info and cleaner chains."));
        grid.add(featureCard("Build it up, do not exclude down",
                "Frameworks and capabilities exist only when the module is included.",
                "Compose a runtime that matches your exact workload."));

        return buildSection("Build and deploy", "Ship a complete modular runtime",
                "Your application is only the size you choose. Mix and match capabilities to build the final JLink or JPackage artifact.",
                true,
                grid);
    }

    private WaCard<?> buildControlSection()
    {
        var flank = new WaFlank(true);
        flank.setGap(PageSize.Large);

        var narrative = new WaStack();
        narrative.setGap(PageSize.Small);
        narrative.add(bodyText("A fully programmable system, modular in design. Using Guice, you get a modular DI framework. Configure data sources, REST providers, WebSocket endpoints, RabbitMQ, JMS, and more directly from your application.", "m"));
        narrative.add(bodyText("Micro in execution: runs cleanly on 128 MB / 0.5 CPU pods and starts REST micro-modules in ~300 ms when built as JLink packages on the JRT filesystem.", "m"));
        narrative.add(bodyText("Restart, update, or rewire services on demand with performance tuned for the Java Module System and the reactive Vert.x core.", "m"));

        var list = new WaText<>();
        list.setTag("ul");
        list.setWaListPlain(true);
        list.add(new ListItem<>("Programmatic configuration for every module and service."));
        list.add(new ListItem<>("No XML templates or static configuration files."));
        list.add(new ListItem<>("Easily restart or reconfigure live services."));
        list.add(new ListItem<>("Performance tuned for modular runtimes."));
        list.add(new ListItem<>("Mutiny used throughout the system for async flows."));

        flank.add(list);
        flank.add(narrative);

        return buildSection("Its your program", "Program the platform, not a template",
                "GuicedEE gives you a true modular DI foundation and keeps every configuration point inside your application code.",
                false,
                flank);
    }

    private WaCard<?> buildPluginSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        String[][] plugins = {
                {"Injection (Guice)", "Guice wrapper for controlling modules and server lifecycle.", "Available"},
                {"Persistence", "Hibernate Reactive with Mutiny.SessionFactory injection.", "Available"},
                {"Hazelcast Cache", "Guice JCache wrapper for Hazelcast.", "Available"},
                {"OpenAPI", "Expose OpenAPI endpoints with minimal setup.", "Available"},
                {"Swagger UI", "Add a URL for swagger-ui when needed.", "Available"},
                {"Web Services (CXF)", "SOAP web services with CXF-style configuration, while REST stays on Vert.x 5.", "Available"},
                {"Web Sockets", "Reactive WebSocket controller built for Vert.x 5.", "Available"},
                {"Serial Support (Cerial)", "Serial and device IO integration for modular runtimes.", "Available"},
                {"RabbitMQ Vert.x", "Annotate queues with automatic recovery and clean module wiring.", "Available"},
                {"MicroProfile Config", "Centralized configuration with file-based application support.", "Available"},
                {"MicroProfile Health", "Health checks for services and runtime readiness.", "Available"},
                {"Telemetry", "Traces and metrics instrumentation for production visibility.", "Available"}
        };

        for (String[] plugin : plugins)
        {
            grid.add(pluginCard(plugin[0], plugin[1], plugin[2]));
        }

        return buildSection("Plugin micro-modules", "Drop-in capabilities",
                "Each integration ships as a micro-module that snaps into the runtime without side effects.",
                true,
                grid);
    }

    private WaCard<?> buildCommonsSection()
    {
        var split = new WaSplit();
        split.row();
        split.setGap(PageSize.Medium);

        var categories = new WaCard<>();
        categories.setAppearance(Appearance.Outlined);
        var categoriesStack = new WaStack();
        categoriesStack.setGap(PageSize.Small);
        categoriesStack.add(headingText("h3", "m", "Service families"));
        var list = new WaText<>();
        list.setTag("ul");
        list.setWaListPlain(true);
        list.add(new ListItem<>("Apache (Commons, CXF)"));
        list.add(new ListItem<>("Hibernate Reactive + data drivers"));
        list.add(new ListItem<>("Vert.x runtime services"));
        list.add(new ListItem<>("Messaging, telemetry, and utilities"));
        categoriesStack.add(list);
        var note = captionText("Every service is JPMS-ready and modular level 3.");
        note.setWaColorText("quiet");
        categoriesStack.add(note);
        categories.add(categoriesStack);
        split.add(categories);

        var highlights = new WaCard<>();
        highlights.setAppearance(Appearance.Outlined);
        var highlightStack = new WaStack();
        highlightStack.setGap(PageSize.Small);
        highlightStack.add(headingText("h3", "m", "Visit Services"));
        var highlightBody = bodyText("Dive into the full catalog of services, modules, and integration notes in one dedicated view.", "m");
        highlightBody.setWaColorText("quiet");
        highlightStack.add(highlightBody);
        var highlightCta = new WaCluster();
        highlightCta.setGap(PageSize.Small);
        highlightCta.add(buildCta("Open Services", "services", Variant.Neutral, Appearance.Outlined));
        highlightStack.add(highlightCta);
        highlights.add(highlightStack);
        split.add(highlights);

        return buildSection("Services", "Browse the modular services catalog",
                "The full service inventory lives on the Services page so this page stays focused on the overview.",
                false,
                split);
    }

    private WaCard<?> buildDeveloperSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);
        grid.add(featureCard("AI Rules repository",
                "Open guidance for AI-assisted development keeps patterns consistent and trustworthy.",
                "Use it to drive better outputs from your tools."));
        grid.add(featureCard("Baseline JDK 25",
                "GuicedEE targets JDK 25 by default, aligned with the Vert.x 5 runtime and modern tooling.",
                "Stay on the current OpenJDK cycle."));
        grid.add(featureCard("End-to-end modular Hibernate",
                "Hibernate is fully modular, customized, and runs on non-blocking reactive SQL drivers from Vert.x.",
                "Designed for high throughput data paths."));
        grid.add(featureCard("Community support",
                "Gitter and GitHub provide central channels to chat, improve, update, and track changes.",
                "Support that scales with the community."));
        grid.add(featureCard("Completely testable end to end",
                "Test the entire structure from top to bottom with Jupiter. Services are the only exception.",
                "Unit test across the stack."));
        grid.add(featureCard("Code-first configuration",
                "No XML templates or configuration files required. Build your own configuration structure programmatically.",
                "Configuration lives in code."));

        return buildSection("For the developer", "Open source with practical guardrails",
                "Clear patterns, testing support, and transparent source give teams the controls they need.",
                true,
                grid);
    }

    private WaCard<?> buildCallToActionSection()
    {
        var panel = new WaStack();
        panel.setGap(PageSize.Small);
        var intro = bodyText("Start with the module catalog, assemble services, and deliver a lean, modular runtime in minutes.", "m");
        intro.setWaColorText("quiet");
        panel.add(intro);
        var ctas = new WaCluster();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("Open the app builder", "builder", Variant.Brand, Appearance.Filled));
        ctas.add(buildCta("Browse capabilities", "capabilities", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("Read the release brief", "media", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("View releases", "releases", Variant.Neutral, Appearance.Outlined));
        panel.add(ctas);

        return buildSection("Get started", "Build with GuicedEE today",
                "Pick the modules you need, generate your runtime, and ship a fully reactive enterprise application.",
                false,
                panel);
    }

    private WaTag<?> buildTag(String label, Variant variant)
    {
        WaTag<?> tag = new WaTag<>();
        tag.setText(label);
        tag.setVariant(variant);
        tag.setPill(true);
        return tag;
    }

    private WaButton<?> buildCta(String label, String route, Variant variant, Appearance appearance)
    {
        WaButton<?> button = new WaButton<>(label, variant);
        if (appearance != null)
        {
            button.setAppearance(appearance);
        }
        button.addAttribute("[routerLink]", "['" + route + "']");
        return button;
    }

    private WaCard<?> heroCard(String title, String body)
    {
        return contentCard(title, body, null, Appearance.Outlined, "s", "s");
    }

    private WaCard<?> featureCard(String title, String body, String note)
    {
        return contentCard(title, body, note, Appearance.Outlined, "m", "m");
    }

    private WaCard<?> pluginCard(String title, String body, String status)
    {
        return contentCard(title, body, status, Appearance.Outlined, "m", "m");
    }

    private WaCard<?> contentCard(String title,
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

    private WaCard<?> buildSection(String eyebrow,
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

    private WaStack sectionHeader(String eyebrow, String title, String subtitle)
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

    private WaText<?> headingText(String tag, String size, String text)
    {
        var heading = new WaText<>();
        heading.setTag(tag);
        heading.setWaHeading(size);
        heading.setText(text);
        return heading;
    }

    private WaText<?> bodyText(String text, String size)
    {
        var body = new WaText<>();
        body.setTag("p");
        body.setWaBody(size == null || size.isBlank() ? "m" : size);
        body.setText(text);
        return body;
    }

    private WaText<?> captionText(String text)
    {
        var caption = new WaText<>();
        caption.setTag("div");
        caption.setWaCaption("s");
        caption.setWaFontWeight("semibold");
        caption.setText(text);
        return caption;
    }
}
