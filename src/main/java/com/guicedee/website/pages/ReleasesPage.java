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
        currentGrid.add(featureCard("Version", "2.1.1", "Current release"));
        currentGrid.add(featureCard("Java baseline", "JDK 25+", "Latest LTS target"));
        currentGrid.add(featureCard("Vert.x", "5.1.3", "Latest reactive core"));
        currentGrid.add(featureCard("Jackson", "3.x", "tools.jackson across the board"));
        currentGrid.add(featureCard("Guice", "7.x", "Latest DI framework"));
        currentContent.add(currentGrid);

        layout.add(buildSection("Current", "v2.1.1",
                "Current stable release targeting JDK 25, Vert.x 5.1.3, and Jackson 3",
                true, currentContent));

        // v2.1.1 release
        var v211Content = new WaStack<>();
        v211Content.setGap(PageSize.Medium);

        var v211Grid = new WaGrid<>();
        v211Grid.setMinColumnSize("16rem");
        v211Grid.setGap(PageSize.Medium);

        v211Grid.add(featureCard("Jackson 3 across the board",
                "Migrated the entire platform from Jackson 2 (com.fasterxml.jackson) to Jackson 3 " +
                        "(tools.jackson). The shared DefaultObjectMapper, IJsonRepresentation, REST request/response " +
                        "serialization, event-bus codecs, and all modules now run on Jackson 3.1.x. The stable " +
                        "com.fasterxml.jackson.annotation annotations (2.x, per JSTEP-1) are retained, so " +
                        "@JsonProperty, @JsonInclude, @JsonAutoDetect, @JsonIdentityInfo, and the reference " +
                        "annotations continue to work unchanged.",
                "Migration · Breaking change"));

        v211Grid.add(featureCard("Vert.x JSON uses our Jackson 3 mapper",
                "Vert.x is now explicitly configured to use the GuicedEE Jackson 3 ObjectMapper via the " +
                        "io.vertx.core.spi.JsonFactory SPI. All Vert.x JSON — Json.encode/decode, " +
                        "JsonObject.mapTo/mapFrom, and event-bus payloads — flows through the same mapper, " +
                        "avoiding Vert.x's Jackson 2 fallback codec.",
                "Enhancement"));

        v211Grid.add(featureCard("GraphQL instrumentation fix",
                "GraphQL.newGraphQL now combines the VertxFutureAdapter and JsonObjectAdapter through a single " +
                        "ChainedInstrumentation. Previously the second instrumentation() call silently replaced the first, " +
                        "dropping the Vert.x future adapter — now both instrumentations are applied correctly.",
                "Bug fix"));

        v211Grid.add(featureCard("GraphQL dependency hygiene",
                "GraphQL-Java ships a shaded copy of Guava under graphql.com.google.common. The com.graphqljava " +
                        "shade module now strips that embedded copy and rewrites references back to the canonical " +
                        "com.google.common module. This removes duplicate Guava from the module path, tracks centrally " +
                        "managed Guava CVE fixes, and yields a clean JPMS graph (com.graphqljava requires transitive " +
                        "com.google.common).",
                "Enhancement · Security"));

        v211Grid.add(featureCard("Telemetry — 3 new endpoints",
                "The telemetry module now supports 3 new endpoints, with its underlying dependency " +
                        "upgraded to the latest version.",
                "Enhancement · Dependency upgrade"));

        v211Content.add(v211Grid);
        layout.add(buildSection("v2.1.1", "Jackson 3 migration",
                "Platform-wide migration to Jackson 3 (tools.jackson), with Vert.x JSON routed through the " +
                        "same mapper, plus GraphQL chained-instrumentation and Guava de-shading fixes.",
                true, v211Content));

        // v2.1.0 release
        var v203Content = new WaStack<>();
        v203Content.setGap(PageSize.Medium);

        var v203Grid = new WaGrid<>();
        v203Grid.setMinColumnSize("16rem");
        v203Grid.setGap(PageSize.Medium);

        v203Grid.add(featureCard("Service Discovery",
                "New module: Vert.x Service Resolver integration providing client-side service discovery " +
                        "via Kubernetes endpoints or DNS SRV records. Pluggable IServiceResolverProvider SPI, " +
                        "round-robin load balancing, and environment-driven configuration.",
                "New module"));

        v203Grid.add(featureCard("Runtime Autoconfigure",
                "New module family: cloud runtime detection SPI with providers for Azure Container Apps, " +
                        "AWS ECS/Fargate/Lambda, GCP Cloud Run, DigitalOcean App Platform, and generic Kubernetes. " +
                        "Auto-detects platform, infers service name/port/hostname/region, and fills missing GuicedEE config. " +
                        "Pairs with service-discovery 'auto' resolver type for zero-config multi-cloud resolution.",
                "New module"));

        v203Grid.add(featureCard("Jackson → 2.21.3",
                "Upgraded Jackson core, databind, and all modules to 2.21.3. Patch-level bug fixes.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("JUnit → 6.1.0",
                "Upgraded JUnit Jupiter and Platform to 6.1.0.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("Dropwizard Metrics → 4.2.39",
                "Upgraded all Dropwizard Metrics modules to 4.2.39.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("SmallRye Common → 2.18.1",
                "Upgraded SmallRye Common libraries to 2.18.1.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("Mutiny → 3.2.1",
                "Upgraded SmallRye Mutiny reactive library to 3.2.1.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("Kafka → 4.3.0",
                "Upgraded Apache Kafka clients to 4.3.0.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("Selenium → 4.44.0",
                "Upgraded Selenium WebDriver to 4.44.0.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("Protobuf → 4.35.0",
                "Upgraded Google Protocol Buffers to 4.35.0.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("CloudEvents → 4.1.0",
                "Upgraded CloudEvents API and core to 4.1.0.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("RabbitMQ Client → 5.31.0",
                "Upgraded RabbitMQ AMQP client to 5.31.0.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("Oracle JDBC → 23.26.2.0.0",
                "Upgraded Oracle JDBC driver (ojdbc11) to 23.26.2.0.0.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("IBM MQ → 9.4.5.1",
                "Upgraded IBM MQ client to 9.4.5.1.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("Woodstox → 7.2.0",
                "Upgraded Woodstox XML parser to 7.2.0.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("ASM → 9.10.1",
                "Upgraded ASM bytecode library to 9.10.1.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("JAXB Runtime → 4.0.9",
                "Upgraded GlassFish JAXB core and runtime to 4.0.9.",
                "Dependency upgrade"));

        v203Grid.add(featureCard("Other upgrades",
                "OpenPDF 3.0.5, JavaParser 3.28.1, org.json 20260522, Jaxen 2.0.4, " +
                        "Glassfish Faces 4.1.9, context-propagation 1.2.1, saslprep/stringprep 2.3.",
                "Dependency upgrades"));

        v203Content.add(v203Grid);
        layout.add(buildSection("v2.1.0", "Dependency update release",
                "Batch update of third-party dependencies — all non-major, stable releases keeping the platform current.",
                true, v203Content));

        // v2.0.2 release
        var v202Content = new WaStack<>();
        v202Content.setGap(PageSize.Medium);

        var v202Grid = new WaGrid<>();
        v202Grid.setMinColumnSize("16rem");
        v202Grid.setGap(PageSize.Medium);

        v202Grid.add(featureCard("Log4j2 → 2.26.0",
                "Upgraded Apache Log4j2 to 2.26.0. " +
                        "Resolves CVE-2025-68161 — the Socket Appender in Log4j Core versions 2.0-beta9 through 2.25.2 " +
                        "does not perform TLS hostname verification, allowing man-in-the-middle interception of log traffic.",
                "Security fix · CVE-2025-68161"));

        v202Grid.add(featureCard("checker-qual → 4.1.0",
                "Upgraded Checker Framework qualifier annotations (checker-qual) to 4.1.0. " +
                        "Keeps annotation-processing toolchain current and aligned with Guava and ErrorProne dependencies.",
                "Dependency upgrade"));

        v202Content.add(v202Grid);
        layout.add(buildSection("v2.0.2", "Security patch release",
                "Dependency upgrades addressing CVE-2025-68161 (Log4j2 TLS hostname verification) and checker-qual alignment.",
                true, v202Content));

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
                false, patchContent));

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
