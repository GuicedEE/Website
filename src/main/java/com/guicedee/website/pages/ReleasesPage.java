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
        currentGrid.add(featureCard("Version", "2.2.3", "Current release"));
        currentGrid.add(featureCard("Java baseline", "JDK 25+", "Latest LTS target"));
        currentGrid.add(featureCard("MicroProfile Config", "3.1.1", "Actionable source diagnostics"));
        currentGrid.add(featureCard("Vert.x", "5.1.8", "SmallRye bindings 4.0.2"));
        currentGrid.add(featureCard("Jackson", "3.2.2", "tools.jackson across the board"));
        currentGrid.add(featureCard("Hibernate ORM", "7.4.7.Final", "Reactive 4.5.5.Final + Models 1.3.1"));
        currentGrid.add(featureCard("GraphQL Java", "26.1", "Java DataLoader 6.0.0"));
        currentGrid.add(featureCard("Guice", "7.x", "Latest DI framework"));
        currentContent.add(currentGrid);

        layout.add(buildSection("Current", "v2.2.3",
                "Full dependency refresh with Config maintained one patch ahead",
                true, currentContent));

        // v2.2.3 release
        var v223Content = new WaStack<>();
        v223Content.setGap(PageSize.Medium);

        var v223Grid = new WaGrid<>();
        v223Grid.setMinColumnSize("16rem");
        v223Grid.setGap(PageSize.Medium);

        v223Grid.add(featureCard("Config stays one version ahead",
                "The 2.2.3 platform BOM selects com.guicedee.microprofile:config 2.2.4. This includes the " +
                        "source-aware startup diagnostics introduced in 2.2.1 while preserving a deliberate " +
                        "independent patch lane for configuration fixes.",
                "Dependency management"));

        v223Grid.add(featureCard("Hibernate ORM 7.4.7 + Hibernate Reactive 4.5.5",
                "Hibernate ORM, its bytecode enhancer and annotation processor are aligned on 7.4.7.Final, " +
                        "with Hibernate Reactive 4.5.5.Final, Hibernate Models 1.3.1 and Validator 9.1.3.Final. " +
                        "The BOM tracks stable minor and patch releases, with compatibility checked across the stack.",
                "Dependency upgrade"));

        v223Grid.add(featureCard("Platform dependency refresh",
                "Managed versions now include Jackson 3.2.2, Vert.x 5.1.8, Apache CXF 4.2.3, SmallRye " +
                        "Mutiny 3.3.0, Vert.x bindings 4.0.2, SmallRye Config 3.18.3, Micrometer 1.17.1 " +
                        "and RabbitMQ 5.35.0. OpenTelemetry remains on 1.64.0.",
                "Dependency upgrade"));

        v223Grid.add(featureCard("GraphQL Java 26.1",
                "GraphQL Java moves to 26.1 with Java DataLoader 6.0.0. Jackson 3.2.2 is managed through " +
                        "a single BOM, ahead of the older Jackson baseline in the Vert.x dependency chain.",
                "Dependency upgrade"));

        v223Grid.add(featureCard("Runtime libraries and developer tools",
                "The refresh includes ClassGraph 4.8.195, Byte Buddy 1.18.13, Guava 33.7.1-jre, " +
                        "Protobuf 4.36.1, Lombok 1.18.48, Selenium 4.49.0 and SpotBugs 4.10.4.",
                "Dependency upgrade"));

        v223Grid.add(featureCard("MongoDB modules for jlink",
                "Five new shaded JPMS services cover bson, bson-record-codec, mongodb-driver-core, " +
                        "mongodb-driver-reactivestreams and reactor-core. Vert.x MongoDB applications can now " +
                        "include the full driver chain in custom jlink runtime images. The managed versions " +
                        "are MongoDB 5.11.1 and Reactor Core 3.8.7.",
                "New modules"));

        v223Grid.add(featureCard("Build and module-path fixes",
                "The release corrects shaded module descriptors and source/javadoc inputs across PostgreSQL, " +
                        "CloudEvents, RabbitMQ, Swagger, Hibernate, MongoDB, Hazelcast and OpenTelemetry, and makes BOM import " +
                        "precedence deterministic under Maven 4.",
                "Build"));

        v223Grid.add(featureCard("Consistent shaded dependencies",
                "Config, CXF, Ehcache, SCRAM, XML Security and supporting libraries now follow the BOM " +
                        "inside their shaded service modules.",
                "Dependency alignment"));

        v223Grid.add(featureCard("Security maintenance",
                "Managed updates include Bouncy Castle 1.86, netty-tcnative 2.0.84.Final, jose4j 0.9.7, " +
                        "Log4j2 2.26.1 and SLF4J 2.0.19. Dependency updates use stable minor and patch " +
                        "releases; prereleases receive a separate review.",
                "Security"));

        v223Grid.add(featureCard("Telemetry logging stability",
                "OpenTelemetry internal logs are excluded from the telemetry appender before dispatch, " +
                        "preventing recursive logging. Reconfiguration removes and stops previous appenders.",
                "Runtime fix"));

        v223Grid.add(featureCard("Oracle service-name connections",
                "Oracle JDBC service-name URLs now use the explicit //host:port/service format. " +
                        "SID connections retain their host:port:SID format.",
                "Persistence fix"));

        v223Grid.add(featureCard("IBM MQ 10 Jakarta provider",
                "Connection factories select IBM MQ's Jakarta provider explicitly. Messaging integration " +
                        "checks cover transactional consumption, correlation IDs and multiple messages.",
                "Compatibility fix"));

        v223Grid.add(featureCard("Native reactive SQL Server pools",
                "SQL Server pools consistently use Vert.x MSSQLConnectOptions. Pool lifetimes retain " +
                        "their explicit time unit, avoiding integer overflow during pool creation.",
                "Persistence fix"));

        v223Content.add(v223Grid);
        layout.add(buildSection("v2.2.3", "Dependency refresh and expanded JPMS coverage",
                "A complete GuicedEE release train with refreshed runtime dependencies, five new MongoDB " +
                        "service modules and Config 2.2.4 selected by the BOM.",
                true, v223Content));

        // v2.2.2 release
        var v222Content = new WaStack<>();
        v222Content.setGap(PageSize.Medium);
        v222Content.add(featureCard("Coordinated platform release",
                "GuicedEE runtime and shaded service modules use 2.2.2, with Config 2.2.3 selected by the BOM. " +
                        "Includes expanded JPMS service coverage and module-path packaging fixes.",
                "Release history"));
        layout.add(buildSection("v2.2.2", "Platform and service modules",
                "The previous coordinated GuicedEE release.", false, v222Content));

        // v2.2.1 release
        var v221Content = new WaStack<>();
        v221Content.setGap(PageSize.Medium);

        var v221Grid = new WaGrid<>();
        v221Grid.setMinColumnSize("16rem");
        v221Grid.setGap(PageSize.Medium);

        v221Grid.add(featureCard("Actionable Config startup failures",
                "Malformed Unicode escapes in META-INF/microprofile-config.properties now report the " +
                        "offending resource URL, line, column and source text. Startup no longer stops at SmallRye's " +
                        "context-free Malformed \\uXXXX encoding message.",
                "Bug fix"));

        v221Grid.add(featureCard("Config source validator",
                "The Config module can scan every visible MicroProfile Config properties resource before or " +
                        "outside application startup. It distinguishes valid escaped Windows paths, identifies " +
                        "truncated Unicode escapes and reports UTF-16 byte-order marks with a corrective hint.",
                "Diagnostics"));

        v221Grid.add(featureCard("Scoped patch BOM",
                "guicedee-bom 2.2.1 selects com.guicedee.microprofile:config 2.2.1 while keeping the rest of " +
                        "the GuicedEE runtime and shaded service modules on the compatible 2.2.0 release train.",
                "Dependency management"));

        v221Content.add(v221Grid);
        layout.add(buildSection("v2.2.1", "MicroProfile Config diagnostics",
                "A focused patch that turns opaque properties parsing failures into precise, fixable startup " +
                        "diagnostics and exposes the update through the GuicedEE BOM.",
                true, v221Content));

        // v2.2.0 release
        var v220Content = new WaStack<>();
        v220Content.setGap(PageSize.Medium);

        var v220Grid = new WaGrid<>();
        v220Grid.setMinColumnSize("16rem");
        v220Grid.setGap(PageSize.Medium);

        v220Grid.add(featureCard("Jackson 3 across the board",
                "Migrated the entire platform from Jackson 2 (com.fasterxml.jackson) to Jackson 3 " +
                        "(tools.jackson). The shared DefaultObjectMapper, IJsonRepresentation, REST request/response " +
                        "serialization, event-bus codecs, and all modules now run on Jackson 3.2.1. The stable " +
                        "com.fasterxml.jackson.annotation annotations (2.x, per JSTEP-1) are retained, so " +
                        "@JsonProperty, @JsonInclude, @JsonAutoDetect, @JsonIdentityInfo, and the reference " +
                        "annotations continue to work unchanged. GuicedEE tracks the latest Jackson 3 release " +
                        "rather than the older LTS pinned by vertx-dependencies: standalone-bom imports the " +
                        "Jackson BOM ahead of the Vert.x depchain, so the whole graph resolves from one Jackson.",
                "Migration · Breaking change"));

        v220Grid.add(featureCard("Vert.x JSON uses our Jackson 3 mapper",
                "Vert.x is now explicitly configured to use the GuicedEE Jackson 3 ObjectMapper via the " +
                        "io.vertx.core.spi.JsonFactory SPI. All Vert.x JSON — Json.encode/decode, " +
                        "JsonObject.mapTo/mapFrom, and event-bus payloads — flows through the same mapper, " +
                        "avoiding Vert.x's Jackson 2 fallback codec.",
                "Enhancement"));

        v220Grid.add(featureCard("GraphQL instrumentation fix",
                "GraphQL.newGraphQL now combines the VertxFutureAdapter and JsonObjectAdapter through a single " +
                        "ChainedInstrumentation. Previously the second instrumentation() call silently replaced the first, " +
                        "dropping the Vert.x future adapter — now both instrumentations are applied correctly.",
                "Bug fix"));

        v220Grid.add(featureCard("GraphQL dependency hygiene",
                "GraphQL-Java ships a shaded copy of Guava under graphql.com.google.common. The com.graphqljava " +
                        "shade module now strips that embedded copy and rewrites references back to the canonical " +
                        "com.google.common module. This removes duplicate Guava from the module path, tracks centrally " +
                        "managed Guava CVE fixes, and yields a clean JPMS graph (com.graphqljava requires transitive " +
                        "com.google.common).",
                "Enhancement · Security"));

        v220Grid.add(featureCard("Telemetry — 3 new endpoints",
                "The telemetry module now supports 3 new endpoints, with its underlying dependency " +
                        "upgraded to the latest version.",
                "Enhancement · Dependency upgrade"));

        v220Content.add(v220Grid);
        layout.add(buildSection("v2.2.0", "Jackson 3 migration",
                "Platform-wide migration to Jackson 3 (tools.jackson) with Vert.x JSON routed through the " +
                        "same mapper, plus GraphQL chained-instrumentation and Guava de-shading fixes.",
                true, v220Content));

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
