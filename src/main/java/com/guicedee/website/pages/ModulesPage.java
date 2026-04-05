package com.guicedee.website.pages;

import com.guicedee.website.catalog.ModuleCatalog;
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
import com.jwebmp.webawesome.components.page.WaPageContentsAside;

@NgComponent("guicedee-modules")
@NgRoutable(path = "modules")
public class ModulesPage extends WebsitePage<ModulesPage> implements INgComponent<ModulesPage>
{
    public ModulesPage()
    {
        buildPage();
    }

    private void buildPage()
    {
        getMain().setPageSize(PageSize.ExtraLarge);

        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildIntro());
        layout.add(buildCoreModules());
        layout.add(buildWebModules());
        layout.add(buildDataModules());
        layout.add(buildObservabilityModules());
        layout.add(buildIntegrationModules());

        var aside = new WaPageContentsAside<>();
        aside.add(escapeAngular("Overview"));
        aside.add(escapeAngular("Core modules"));
        aside.add(escapeAngular("Web & REST"));
        aside.add(escapeAngular("Data & persistence"));
        aside.add(escapeAngular("Observability"));
        aside.add(escapeAngular("Integration"));
        getAside().add(aside);
    }

    private WaCard<?> buildIntro()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(headingText("h1", "xl", "Module Reference"));

        var intro = bodyText("Every GuicedEE module is a standalone JPMS artifact with its own module-info.java. " +
                "Each page below documents the full API, configuration, SPI extension points, and code examples. " +
                "Import the BOM, add the modules you need, and build up your runtime.", "l");
        intro.setWaColorText("quiet");
        content.add(intro);

        var stats = new WaCluster<>();
        stats.setGap(PageSize.Small);
        stats.add(buildTag(ModuleCatalog.getModules().size() + " modules", Variant.Brand));
        stats.add(buildTag("JPMS Level 3", Variant.Neutral));
        stats.add(buildTag("Zero config", Variant.Warning));
        stats.add(buildTag("BOM-managed versions", Variant.Success));
        content.add(stats);

        var card = new WaCard<>();
        card.setAppearance(Appearance.Filled);
        card.add(content);
        return card;
    }

    private WaCard<?> buildCoreModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("Inject (Core Engine)",
                "GuiceContext, ClassGraph scanning, lifecycle SPI (IGuicePreStartup, IGuiceModule, " +
                        "IGuicePostStartup, IGuicePreDestroy), JobService, Environment utilities, CallScope.",
                "com.guicedee:inject",
                "modules/inject"));

        grid.add(moduleCard("CDI Bridge",
                "@ApplicationScoped, @RequestScoped, @Dependent, BeanManager, Events. " +
                        "CDI annotations mapped to Guice scopes.",
                "com.guicedee:cdi",
                "modules/cdi"));

        grid.add(moduleCard("Client API",
                "IGuiceContext interface, Environment, CallScoper, CallScopeProperties. " +
                        "The lightweight API that all modules depend on.",
                "com.guicedee:client",
                "modules/client"));

        return buildSection("Core modules", "Injection, lifecycle, and scanning",
                "The foundation that every GuicedEE application is built on.", true, grid);
    }

    private WaCard<?> buildWebModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("Vert.x Core",
                "@Verticle annotation, VerticleBuilder, EventBus codecs, deployment options. " +
                        "Threading models, HA, worker pool configuration.",
                "com.guicedee:vertx",
                "modules/vertx"));

        grid.add(moduleCard("Web Server",
                "HTTP/HTTPS auto-start, Router, BodyHandler, 3 SPI hooks " +
                        "(VertxHttpServerOptionsConfigurator, VertxHttpServerConfigurator, VertxRouterConfigurator).",
                "com.guicedee:web",
                "modules/web"));

        grid.add(moduleCard("REST Services",
                "Jakarta REST (JAX-RS) adapter. @Path, @GET, @POST, all parameter annotations, " +
                        "reactive returns, @Cors, @RolesAllowed, ExceptionMapper, RestInterceptor SPI.",
                "com.guicedee:rest",
                "modules/rest"));

        grid.add(moduleCard("REST Client",
                "@Endpoint annotation, typed RestClient<S,R>, path parameters, " +
                        "Bearer/JWT/Basic/ApiKey auth, env-var secrets, RestClientConfigurator SPI.",
                "com.guicedee:rest-client",
                "modules/rest-client"));

        grid.add(moduleCard("WebSockets",
                "RFC 6455 with call-scoped connections, action-based message routing, " +
                        "group join/leave/broadcast, per-message compression (RFC 7692).",
                "com.guicedee:websockets",
                "modules/websockets"));

        return buildSection("Web & REST", "HTTP servers, REST services, and real-time communication",
                "The reactive web stack built on Vert.x 5.", false, grid);
    }

    private WaCard<?> buildDataModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("Persistence",
                "Hibernate Reactive 7 with Mutiny. DatabaseModule, ConnectionBaseInfo, " +
                        "env-var driven config, multi-database support, reactive sessions.",
                "com.guicedee:persistence",
                "modules/persistence"));

        grid.add(moduleCard("Representations",
                "IJsonRepresentation, DefaultObjectMapper, Jackson configuration. " +
                        "Shared JSON serialization used by REST, WebSockets, and EventBus.",
                "com.guicedee:representations",
                "modules/representations"));

        grid.add(moduleCard("Cerial",
                "High-performance serialization with compile-time code generation. " +
                        "Binary and JSON formats with JPMS module support.",
                "com.guicedee:cerial",
                "modules/cerial"));

        return buildSection("Data & persistence", "Database access, serialization, and data formats",
                "Hibernate Reactive, Jackson, and high-performance serialization.", true, grid);
    }

    private WaCard<?> buildObservabilityModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("Health",
                "MicroProfile Health — @Liveness, @Readiness, @Startup. Auto-discovered checks, " +
                        "configurable paths, 2-second timeout, Kubernetes-ready.",
                "com.guicedee:health",
                "modules/health"));

        grid.add(moduleCard("Metrics",
                "MicroProfile Metrics — @Counted, @Timed, @Gauge. " +
                        "Prometheus and Graphite reporting. Automatic endpoint registration.",
                "com.guicedee:metrics",
                "modules/metrics"));

        grid.add(moduleCard("Telemetry",
                "@Trace, @SpanAttribute, @TelemetryOptions. OpenTelemetry with OTLP export " +
                        "to Tempo/Jaeger. Log4j2 appender for trace-correlated logs.",
                "com.guicedee:guiced-telemetry",
                "modules/telemetry"));

        grid.add(moduleCard("Fault Tolerance",
                "MicroProfile Fault Tolerance — @Retry, @CircuitBreaker, @Timeout, " +
                        "@Bulkhead, @Fallback. Guice AOP interception.",
                "com.guicedee:fault-tolerance",
                "modules/fault-tolerance"));

        grid.add(moduleCard("Config",
                "MicroProfile Config with SmallRye — @ConfigProperty, " +
                        "env vars → system props → properties files. Type conversion.",
                "com.guicedee.microprofile:config",
                "modules/config"));

        return buildSection("Observability", "Health, metrics, tracing, and configuration",
                "MicroProfile specifications with zero-config defaults.", false, grid);
    }

    private WaCard<?> buildIntegrationModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("RabbitMQ",
                "Annotation-driven message queues and exchanges. Auto-recovery, " +
                        "Vert.x RabbitMQ client, @RabbitConnectionOptions.",
                "com.guicedee:rabbitmq",
                "modules/rabbitmq"));

        grid.add(moduleCard("OpenAPI",
                "Auto-generated OpenAPI 3.x specification from @Path annotations. " +
                        "Served at /openapi. Runtime schema generation.",
                "com.guicedee:openapi",
                "modules/openapi"));

        grid.add(moduleCard("Swagger UI",
                "Interactive API browser at /swagger/. Auto-configured from " +
                        "the OpenAPI specification.",
                "com.guicedee:guiced-swagger-ui",
                "modules/swagger-ui"));

        grid.add(moduleCard("Web Services",
                "Apache CXF SOAP integration. WSDL-first or code-first " +
                        "web services with Guice injection.",
                "com.guicedee:webservices",
                "modules/webservices"));

        return buildSection("Integration", "Messaging, API documentation, and web services",
                "Connect to the ecosystem with messaging, OpenAPI, and SOAP.", true, grid);
    }

    private WaCard<?> moduleCard(String title, String description, String artifact, String route)
    {
        var card = new WaCard<>();
        card.setAppearance(Appearance.Outlined);

        var stack = new WaStack();
        stack.setGap(PageSize.Small);

        stack.add(headingText("h3", "m", title));

        var body = bodyText(description, "m");
        body.setWaColorText("quiet");
        stack.add(body);

        var artifactText = captionText(artifact);
        artifactText.setWaColorText("quiet");
        stack.add(artifactText);

        var cta = buildCta("View module →", route, Variant.Brand, Appearance.Outlined);
        stack.add(cta);

        card.add(stack);
        return card;
    }
}

