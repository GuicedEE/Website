package com.guicedee.website.pages;

import com.guicedee.website.App;
import com.guicedee.website.catalog.ModuleCatalog;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.dialog.WaDialog;
import com.jwebmp.plugins.markdown.Markdown;


import java.util.ArrayList;
import java.util.List;

@NgComponent("guicedee-modules")
@NgRoutable(path = "modules")
@NgComponentReference(App.class)
public class ModulesPage extends WebsitePage<ModulesPage> implements INgComponent<ModulesPage>
{
    public ModulesPage()
    {
        buildPage();
    }

    private void buildPage()
    {
        var layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildIntro());
        layout.add(buildCoreModules());
        layout.add(buildWebModules());
        layout.add(buildDataModules());
        layout.add(buildObservabilityModules());
        layout.add(buildIntegrationModules());
        layout.add(buildMigrationModules());

        // Module README dialog
        var dialog = new WaDialog<>("module-readme-dialog");
        dialog.setLabel("Module Documentation");
        dialog.setLightDismiss(true);
        dialog.setScrolling(true);
        dialog.addAttribute("[open]", "readmeDialogOpen");
        dialog.addAttribute("(wa-after-hide)", "readmeDialogOpen = false");
        dialog.addStyle("--width", "min(90vw, 72rem)");

        var dialogHeader = new DivSimple<>();
        dialogHeader.addAttribute("slot", "label");
        dialogHeader.setText("{{readmeModuleTitle}}");
        dialog.add(dialogHeader);

        // Use the Markdown component with [src] binding — ngx-markdown fetches
        // the URL itself and triggers all plugins (Prism, mermaid, clipboard) after load
        var md = new Markdown<>();
        md.setLineNumbers(true);
        md.setEmoji(true);
        md.setMermaid(true);
        md.setClipboard(true);
        md.addAttribute("[src]", "readmeSrc");
        dialog.add(md);

        add(dialog);
    }

    private WaCard<?> buildIntro()
    {
        var content = new WaStack<>();
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

    private WaStack buildCoreModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("Inject (Core Engine)",
                "GuiceContext, ClassGraph scanning, lifecycle SPI (IGuicePreStartup, IGuiceModule, " +
                        "IGuicePostStartup, IGuicePreDestroy), JobService, Environment utilities, CallScope.",
                "com.guicedee:inject",
                "inject"));


        grid.add(moduleCard("Client API",
                "IGuiceContext interface, Environment, CallScoper, CallScopeProperties. " +
                        "The lightweight API that all modules depend on.",
                "com.guicedee:client",
                "client"));

        return buildSection("Core modules", "Injection, lifecycle, and scanning",
                "The foundation that every GuicedEE application is built on.", true, grid);
    }

    private WaStack buildWebModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("Vert.x Core",
                "@Verticle annotation, VerticleBuilder, EventBus codecs, deployment options. " +
                        "Threading models, HA, worker pool configuration.",
                "com.guicedee:vertx",
                "vertx"));

        grid.add(moduleCard("Web Server",
                "HTTP/HTTPS auto-start, Router, BodyHandler, 3 SPI hooks " +
                        "(VertxHttpServerOptionsConfigurator, VertxHttpServerConfigurator, VertxRouterConfigurator).",
                "com.guicedee:web",
                "web"));

        grid.add(moduleCard("REST Services",
                "Jakarta REST (JAX-RS) adapter. @Path, @GET, @POST, all parameter annotations, " +
                        "reactive returns, @Cors, @RolesAllowed, ExceptionMapper, RestInterceptor SPI.",
                "com.guicedee:rest",
                "rest"));

        grid.add(moduleCard("REST Client",
                "@Endpoint annotation, typed RestClient<S,R>, path parameters, " +
                        "Bearer/JWT/Basic/ApiKey auth, env-var secrets, RestClientConfigurator SPI.",
                "com.guicedee:rest-client",
                "rest-client"));

        grid.add(moduleCard("WebSockets",
                "RFC 6455 with call-scoped connections, action-based message routing, " +
                        "group join/leave/broadcast, per-message compression (RFC 7692).",
                "com.guicedee:websockets",
                "websockets"));

        return buildSection("Web & REST", "HTTP servers, REST services, and real-time communication",
                "The reactive web stack built on Vert.x 5.", false, grid);
    }

    private WaStack buildDataModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("Persistence",
                "Hibernate Reactive 7 with Mutiny. DatabaseModule, ConnectionBaseInfo, " +
                        "env-var driven config, multi-database support, reactive sessions.",
                "com.guicedee:persistence",
                "persistence"));

        grid.add(moduleCard("Representations",
                "IJsonRepresentation, DefaultObjectMapper, Jackson configuration. " +
                        "Shared JSON serialization used by REST, WebSockets, and EventBus.",
                "com.guicedee:representations",
                "representations"));

        return buildSection("Data & persistence", "Database access, serialization, and data formats",
                "Hibernate Reactive, Jackson, and shared JSON representations.", true, grid);
    }

    private WaStack buildObservabilityModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("Health",
                "MicroProfile Health — @Liveness, @Readiness, @Startup. Auto-discovered checks, " +
                        "configurable paths, 2-second timeout, Kubernetes-ready.",
                "com.guicedee:health",
                "health"));

        grid.add(moduleCard("Metrics",
                "MicroProfile Metrics — @Counted, @Timed, @Gauge. " +
                        "Prometheus and Graphite reporting. Automatic endpoint registration.",
                "com.guicedee:metrics",
                "metrics"));

        grid.add(moduleCard("Telemetry",
                "@Trace, @SpanAttribute, @TelemetryOptions. OpenTelemetry with OTLP export " +
                        "to Tempo/Jaeger. Log4j2 appender for trace-correlated logs.",
                "com.guicedee:guiced-telemetry",
                "telemetry"));

        grid.add(moduleCard("Fault Tolerance",
                "MicroProfile Fault Tolerance — @Retry, @CircuitBreaker, @Timeout, " +
                        "@Bulkhead, @Fallback. Guice AOP interception.",
                "com.guicedee:fault-tolerance",
                "fault-tolerance"));

        grid.add(moduleCard("Config",
                "MicroProfile Config with SmallRye — @ConfigProperty, " +
                        "env vars → system props → properties files. Type conversion.",
                "com.guicedee.microprofile:config",
                "config"));

        return buildSection("Observability", "Health, metrics, tracing, and configuration",
                "MicroProfile specifications with zero-config defaults.", false, grid);
    }

    private WaStack buildIntegrationModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("RabbitMQ",
                "Annotation-driven message queues and exchanges. Auto-recovery, " +
                        "Vert.x RabbitMQ client, @RabbitConnectionOptions.",
                "com.guicedee:rabbitmq",
                "rabbitmq"));

        grid.add(moduleCard("Kafka",
                "Annotation-driven Apache Kafka integration. @KafkaConnectionOptions, " +
                        "@KafkaTopicDefinition, consumers, publishers, admin client, " +
                        "worker threads, and call-scoped message handling via Vert.x Kafka Client.",
                "com.guicedee:kafka",
                "kafka"));

        grid.add(moduleCard("IBM MQ",
                "Annotation-driven IBM MQ integration via JMS client. " +
                        "@IBMMQConnectionOptions, @IBMMQQueueDefinition, consumers, " +
                        "publishers, and environment variable overrides.",
                "com.guicedee:ibmmq",
                "ibmmq"));

        grid.add(moduleCard("OpenAPI",
                "Auto-generated OpenAPI 3.x specification from @Path annotations. " +
                        "Served at /openapi. Runtime schema generation.",
                "com.guicedee:openapi",
                "openapi"));

        grid.add(moduleCard("Swagger UI",
                "Interactive API browser at /swagger/. Auto-configured from " +
                        "the OpenAPI specification.",
                "com.guicedee:guiced-swagger-ui",
                "swagger-ui"));

        grid.add(moduleCard("Web Services",
                "Apache CXF SOAP integration. WSDL-first or code-first " +
                        "web services with Guice injection.",
                "com.guicedee:webservices",
                "webservices"));

        grid.add(moduleCard("Cerial",
                "Modular serial port communications in Java. @Named port injection, " +
                        "auto-reconnect, idle monitoring, and health reporting.",
                "com.guicedee:cerial",
                "cerial"));

        return buildSection("Integration", "Messaging, API documentation, serial ports, and web services",
                "Connect to the ecosystem with messaging, OpenAPI, serial ports, and SOAP.", true, grid);
    }

    private WaStack buildMigrationModules()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(moduleCard("CDI Bridge (Migration)",
                "Jakarta CDI compatibility layer for projects migrating from CDI-based frameworks. " +
                        "Maps @ApplicationScoped, @RequestScoped, @Dependent, and BeanManager to Guice equivalents. " +
                        "Not a foundation component — new projects should use Guice annotations directly.",
                "com.guicedee:cdi",
                "cdi"));

        return buildSection("Migration & Compatibility", "Bridges for migrating from other DI frameworks",
                "These modules are not part of the GuicedEE foundation. They exist to assist projects " +
                        "migrating from Jakarta CDI, Spring, or other DI frameworks to the native Guice-first model. " +
                        "New projects should use Guice annotations and APIs directly.", false, grid);
    }

    private WaCard<?> moduleCard(String title, String description, String artifact, String moduleId)
    {
        var card = new WaCard<>();
        card.setAppearance(Appearance.Outlined);

        var stack = new WaStack<>();
        stack.setGap(PageSize.Small);

        stack.add(headingText("h3", "m", title));

        var body = bodyText(description, "m");
        body.setWaColorText("quiet");
        stack.add(body);

        stack.add(coordinateBlock(artifact));

        var cta = new WaButton<>();
        cta.setText(escapeAngular("View module →"));
        cta.setVariant(Variant.Brand);
        cta.setAppearance(Appearance.Outlined);
        cta.addAttribute("(click)", "openReadme('" + moduleId + "', '" + escapeAngular(title) + "')");
        stack.add(cta);

        card.add(stack);
        return card;
    }

    @Override
    public List<String> fields()
    {
        var f = new ArrayList<>(super.fields());
        f.add("readmeDialogOpen = false;");
        f.add("readmeModuleTitle = '';");
        f.add("readmeSrc = '';");
        f.add("""
                readmeUrls: Record<string, string> = {
                    'inject': 'https://raw.githubusercontent.com/GuicedEE/GuicedInjection/refs/heads/master/README.md',
                    'client': 'https://raw.githubusercontent.com/GuicedEE/Client/refs/heads/master/README.md',
                    'vertx': 'https://raw.githubusercontent.com/GuicedEE/Guiced-Vert.x/refs/heads/master/README.md',
                    'web': 'https://raw.githubusercontent.com/GuicedEE/GuicedVertxWeb/refs/heads/master/README.md',
                    'rest': 'https://raw.githubusercontent.com/GuicedEE/GuicedRestServices/refs/heads/master/README.md',
                    'rest-client': 'https://raw.githubusercontent.com/GuicedEE/Rest-Client/refs/heads/master/README.md',
                    'websockets': 'https://raw.githubusercontent.com/GuicedEE/GuicedVertxSockets/refs/heads/master/README.md',
                    'persistence': 'https://raw.githubusercontent.com/GuicedEE/GuicedVertxPersistence/refs/heads/master/README.md',
                    'representations': 'https://raw.githubusercontent.com/GuicedEE/Representations/refs/heads/master/README.md',
                    'health': 'https://raw.githubusercontent.com/GuicedEE/Health/refs/heads/master/README.md',
                    'metrics': 'https://raw.githubusercontent.com/GuicedEE/Metrics/refs/heads/master/README.md',
                    'telemetry': 'https://raw.githubusercontent.com/GuicedEE/GuicedTelemetry/refs/heads/master/README.md',
                    'fault-tolerance': 'https://raw.githubusercontent.com/GuicedEE/FaultTolerance/refs/heads/master/README.md',
                    'config': 'https://raw.githubusercontent.com/GuicedEE/microprofile-config/refs/heads/master/README.md',
                    'rabbitmq': 'https://raw.githubusercontent.com/GuicedEE/GuicedRabbit/refs/heads/master/README.md',
                    'kafka': 'https://raw.githubusercontent.com/GuicedEE/GuicedKafka/refs/heads/master/README.md',
                    'ibmmq': 'https://raw.githubusercontent.com/GuicedEE/GuicedIBMMQ/refs/heads/master/README.md',
                    'openapi': 'https://raw.githubusercontent.com/GuicedEE/OpenAPI/refs/heads/master/README.md',
                    'swagger-ui': 'https://raw.githubusercontent.com/GuicedEE/SwaggerUI/refs/heads/master/README.md',
                    'webservices': 'https://raw.githubusercontent.com/GuicedEE/GuicedWebServices/refs/heads/master/README.md',
                    'cerial': 'https://raw.githubusercontent.com/GuicedEE/Cerial/refs/heads/master/README.md',
                    'cdi': 'https://raw.githubusercontent.com/GuicedEE/GuicedCDI/refs/heads/master/README.md'
                };
                """);
        return f;
    }

    @Override
    public List<String> methods()
    {
        var m = new ArrayList<>(super.methods());
        m.add("""
                openReadme(moduleId: string, title: string) {
                    this.readmeModuleTitle = title;
                    this.readmeSrc = this.readmeUrls[moduleId] || '';
                    this.readmeDialogOpen = true;
                }
                """);
        return m;
    }
}
