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

        var layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        // Header
        var introContent = new WaStack<>();
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
        var currentContent = new WaStack<>();
        currentContent.setGap(PageSize.Medium);

        var currentGrid = new WaGrid<>();
        currentGrid.setMinColumnSize("14rem");
        currentGrid.setGap(PageSize.Small);
        currentGrid.add(featureCard("Version", "2.0.2", "Current release"));
        currentGrid.add(featureCard("Java baseline", "JDK 25+", "Latest LTS target"));
        currentGrid.add(featureCard("Vert.x", "5.x", "Latest reactive core"));
        currentGrid.add(featureCard("Guice", "7.x", "Latest DI framework"));
        currentContent.add(currentGrid);

        layout.add(buildSection("Current", "v2.0.1",
                "Current stable release targeting JDK 25 and Vert.x 5.",
                true, currentContent));

        // v2.0.1 release
        var patchContent = new WaStack<>();
        patchContent.setGap(PageSize.Medium);

        var patchGrid = new WaGrid<>();
        patchGrid.setMinColumnSize("16rem");
        patchGrid.setGap(PageSize.Medium);

        patchGrid.add(featureCard("MongoDB support",
                "Native MongoDB integration via Vert.x MongoClient. " +
                        "Extend MongoModule, configure MongoConnectionInfo with connection string or host/port/auth, " +
                        "and inject @Named MongoClient instances. Environment variable driven configuration.",
                "New feature"));

        patchGrid.add(featureCard("Cassandra support",
                "Native Cassandra integration via Vert.x CassandraClient. " +
                        "Extend CassandraModule, configure CassandraConnectionInfo with contact points, keyspace, and auth, " +
                        "and inject @Named CassandraClient instances. Environment variable driven configuration.",
                "New feature"));

        patchGrid.add(featureCard("IntelliJ plugin templates",
                "New file templates for MongoDB Module and Cassandra Module in the IntelliJ plugin, " +
                        "plus Application Builder integration for scaffolding MongoDB and Cassandra projects.",
                "Enhancement"));

        patchGrid.add(featureCard("Testcontainers fix",
                "Fixed org.reactivestreams package split conflict in the Testcontainers shade jar, " +
                        "enabling Testcontainers-based integration tests for MongoDB and Cassandra.",
                "Bug fix"));

        patchGrid.add(featureCard("HTTP Proxy support",
                "Vert.x HttpProxy reverse proxy integration. Extend ProxyModule, configure ProxyConnectionInfo " +
                        "with proxy/origin host and port, interceptors, caching, and WebSocket support. " +
                        "Inject @Named HttpProxy instances.",
                "New feature"));

        patchGrid.add(featureCard("Redis support",
                "Comprehensive Vert.x Redis integration with @RedisOptions annotation-driven config, " +
                        "all 4 modes (Standalone, Sentinel, Cluster, Replication), connection pooling, TLS/SSL, " +
                        "RESP2/RESP3 protocol selection, ${ENV_VAR:default} placeholders, pub/sub, " +
                        "and @Named multi-connection injection. Zero-code setup via annotation or full programmatic control.",
                "New feature"));

        patchGrid.add(featureCard("ConnectionBaseInfoFactory",
                "New factory API for creating database-specific ConnectionBaseInfo implementations. " +
                        "Use ConnectionBaseInfoFactory.createConnectionBaseInfo(\"postgresql\") instead of " +
                        "instantiating DB-specific classes directly. Supports postgresql, mysql, sqlserver, oracle, and db2.",
                "Enhancement"));

        patchGrid.add(featureCard("Updated persistence examples",
                "All persistence examples (Basic, DB2, MySQL, MSSQL, Oracle) updated to use " +
                        "ConnectionBaseInfoFactory instead of DB-specific ConnectionBaseInfo subclasses. " +
                        "IntelliJ plugin persistence template updated with DATABASE_TYPE variable.",
                "Enhancement"));

        patchContent.add(patchGrid);
        layout.add(buildSection("v2.0.1", "Patch release",
                "MongoDB, Cassandra, HTTP Proxy, Redis support, ConnectionBaseInfoFactory, IntelliJ plugin enhancements, and Testcontainers fixes.",
                true, patchContent));

        // What's new in 2.0
        var whatsNewContent = new WaStack<>();
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


        newGrid.add(featureCard("REST Client",
                "Annotation-driven REST client with @Endpoint, typed RestClient<Send, Receive>, " +
                        "and reactive Uni responses.",
                "New module"));

        whatsNewContent.add(newGrid);
        layout.add(buildSection("What's new", "Major changes in 2.0",
                "The 2.0 release is a significant evolution with new modules and platform upgrades.",
                false, whatsNewContent));

        // Roadmap
        var roadmapContent = new WaStack<>();
        roadmapContent.setGap(PageSize.Medium);

        var roadmapGrid = new WaGrid<>();
        roadmapGrid.setMinColumnSize("16rem");
        roadmapGrid.setGap(PageSize.Medium);

        roadmapGrid.add(featureCard("Expanded DB support",
                "CockroachDB and ClickHouse service modules with dedicated connection info and module patterns.",
                "Planned"));

        roadmapGrid.add(featureCard("Enhanced CallScope",
                "Richer call-scope propagation across async boundaries — automatic context " +
                        "carry through Uni chains, event bus messages, and worker verticles.",
                "In progress"));

        roadmapGrid.add(featureCard("AI sanity checks",
                "Automated AI-driven analysis of module configurations, dependency graphs, " +
                        "and module-info declarations to catch misconfigurations before startup.",
                "Planned"));

        roadmapGrid.add(featureCard("Version maintenance automation",
                "AI-assisted dependency upgrades, CVE scanning, and compatibility verification " +
                        "across the entire BOM with automated PR generation.",
                "Planned"));

        roadmapContent.add(roadmapGrid);

        var ctas = new WaCluster<>();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("View on GitHub", "github", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("Get started", "getting-started", Variant.Brand, Appearance.Outlined));
        roadmapContent.add(ctas);

        layout.add(buildSection("Roadmap", "What's coming next",
                "Planned features and capabilities for future releases.",
                true, roadmapContent));
    }
}
