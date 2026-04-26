package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.card.WaCard;

@NgComponent("guicedee-releases")
@NgRoutable(path = "releases")
public class ReleasesPage extends WebsitePage<ReleasesPage> implements INgComponent<ReleasesPage>
{
    public ReleasesPage()
    {
        buildReleasesPage();
    }

    private void buildReleasesPage()
    {

        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        // Header
        var introContent = new WaStack();
        introContent.setGap(PageSize.Medium);
        introContent.add(headingText("h1", "xl", "Releases & Changelog"));
        var desc = bodyText("Track GuicedEE releases, version history, and what's coming next. " +
                "All modules are versioned together through the BOM.", "l");
        desc.setWaColorText("quiet");
        introContent.add(desc);

        var introCard = new WaCard<>();
        introCard.setAppearance(Appearance.Filled);
        introCard.add(introContent);
        layout.add(introCard);

        // Current release
        var currentContent = new WaStack();
        currentContent.setGap(PageSize.Medium);

        var currentGrid = new WaGrid<>();
        currentGrid.setMinColumnSize("14rem");
        currentGrid.setGap(PageSize.Small);
        currentGrid.add(featureCard("Version", "2.0.0-RC10", "In development"));
        currentGrid.add(featureCard("Java baseline", "JDK 25+", "Latest LTS target"));
        currentGrid.add(featureCard("Vert.x", "5.x", "Latest reactive core"));
        currentGrid.add(featureCard("Guice", "7.x", "Latest DI framework"));
        currentContent.add(currentGrid);

        layout.add(buildSection("Current", "v2.0.0-RC10",
                "Active development branch targeting JDK 25 and Vert.x 5.",
                true, currentContent));

        // What's new in 2.0
        var whatsNewContent = new WaStack();
        whatsNewContent.setGap(PageSize.Medium);

        var newGrid = new WaGrid<>();
        newGrid.setMinColumnSize("16rem");
        newGrid.setGap(PageSize.Medium);

        newGrid.add(featureCard("Vert.x 5 migration",
                "Complete migration from Vert.x 4 to Vert.x 5 across all modules. " +
                        "New event loop model, improved WebSocket support, and reactive SQL clients.",
                "Breaking change from 1.x"));

        newGrid.add(featureCard("JDK 25 baseline",
                "All modules compile with --release 25. " +
                        "Takes advantage of sealed classes, pattern matching, and virtual threads.",
                "Minimum JDK 25 required"));

        newGrid.add(featureCard("MicroProfile Fault Tolerance",
                "New module implementing @Retry, @CircuitBreaker, @Timeout, @Bulkhead, @Fallback " +
                        "through Guice AOP interception.",
                "New module"));

        newGrid.add(featureCard("OpenTelemetry integration",
                "New telemetry module with @Trace, @SpanAttribute, OTLP export, " +
                        "and Uni-aware span completion.",
                "New module"));

        newGrid.add(featureCard("MCP Server",
                "Model Context Protocol server for AI agent integration. " +
                        "Tools, resources, prompts, and completion endpoints.",
                "New module"));

        newGrid.add(featureCard("REST Client",
                "Annotation-driven REST client with @Endpoint, typed RestClient<Send, Receive>, " +
                        "and reactive Uni responses.",
                "New module"));

        whatsNewContent.add(newGrid);
        layout.add(buildSection("What's new", "Major changes in 2.0",
                "The 2.0 release is a significant evolution with new modules and platform upgrades.",
                false, whatsNewContent));

        // Roadmap
        var roadmapContent = new WaStack();
        roadmapContent.setGap(PageSize.Medium);

        var roadmapGrid = new WaGrid<>();
        roadmapGrid.setMinColumnSize("16rem");
        roadmapGrid.setGap(PageSize.Medium);

        roadmapGrid.add(featureCard("Workflow engine",
                "Declarative workflow definitions with state machines, " +
                        "activity orchestration, and event-driven transitions.",
                "Planned"));

        roadmapGrid.add(featureCard("gRPC support",
                "Protocol Buffers and gRPC service definitions with Vert.x gRPC client/server.",
                "Under evaluation"));

        roadmapGrid.add(featureCard("Native image",
                "GraalVM native-image support for selected modules " +
                        "with build-time class scanning.",
                "Experimental"));

        roadmapContent.add(roadmapGrid);

        var ctas = new WaCluster<>();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("View on GitHub", "github", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("Get started", "getting-started", Variant.Brand, Appearance.Filled));
        roadmapContent.add(ctas);

        layout.add(buildSection("Roadmap", "What's coming next",
                "Planned features and capabilities for future releases.",
                true, roadmapContent));
    }
}
