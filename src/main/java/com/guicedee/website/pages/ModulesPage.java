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
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.tooltip.TooltipPlacement;
import com.jwebmp.webawesome.components.tooltip.WaTooltip;

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
        var headerCluster = new WaCluster<>();
        headerCluster.setSplit();
        headerCluster.setGap(PageSize.Small);
        var titleSpan = new DivSimple<>();
        titleSpan.setText("{{readmeModuleTitle}}");
        headerCluster.add(titleSpan);

        var actions = new WaCluster<>();
        actions.setGap(PageSize.ExtraSmall);

        // GitHub repo link
        var repoIcon = new WaIcon<>("github");
        repoIcon.addAttribute("family", "brands");
        repoIcon.addStyle("font-size", "var(--wa-font-size-l)");
        var repoBtn = new WaButton<>();
        repoBtn.setVariant(Variant.Brand);
        repoBtn.setAppearance(Appearance.Plain);
        repoBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        repoBtn.addAttribute("href", "{{currentRepoUrl}}");
        repoBtn.addAttribute("target", "_blank");
        repoBtn.add(repoIcon);
        actions.add(repoBtn);
        actions.add(new WaTooltip<>(repoBtn).setText("View Repository").setPlacement(TooltipPlacement.Top));

        // Star repo link
        var starIcon = new WaIcon<>("star");
        starIcon.addStyle("font-size", "var(--wa-font-size-l)");
        var starBtn = new WaButton<>();
        starBtn.setVariant(Variant.Brand);
        starBtn.setAppearance(Appearance.Plain);
        starBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        starBtn.addAttribute("href", "{{currentRepoUrl}}");
        starBtn.addAttribute("target", "_blank");
        starBtn.add(starIcon);
        actions.add(starBtn);
        actions.add(new WaTooltip<>(starBtn).setText("Star Repository").setPlacement(TooltipPlacement.Top));

        // Issues link
        var issuesIcon = new WaIcon<>("bug");
        issuesIcon.addStyle("font-size", "var(--wa-font-size-l)");
        var issuesBtn = new WaButton<>();
        issuesBtn.setVariant(Variant.Brand);
        issuesBtn.setAppearance(Appearance.Plain);
        issuesBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        issuesBtn.addAttribute("href", "{{currentRepoUrl + '/issues'}}");
        issuesBtn.addAttribute("target", "_blank");
        issuesBtn.add(issuesIcon);
        actions.add(issuesBtn);
        actions.add(new WaTooltip<>(issuesBtn).setText("Log an Issue").setPlacement(TooltipPlacement.Top));

        headerCluster.add(actions);
        dialogHeader.add(headerCluster);
        dialog.add(dialogHeader);

        // Use the Markdown component with [src] binding — ngx-markdown fetches
        // the URL itself and triggers all plugins (Prism, mermaid, clipboard) after load
        var md = new Markdown<>();
        md.setLineNumbers(true);
        md.setEmoji(true);
        md.setMermaid(true);
        md.setClipboard(true);
        md.addAttribute("[src]", "readmeSrc");
        md.addClass("wa-body-s");
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

        // Single combined Inject + Client API card
        var card = new WaCard<>();
        card.setAppearance(Appearance.Outlined);

        var header = new DivSimple<>();
        var headerCluster = new WaCluster<>();
        headerCluster.setGap(PageSize.Small);
        headerCluster.setSplit();
        headerCluster.add(headingText("h3", "m", "Inject (Core Engine)"));
        headerCluster.add(exampleHeaderIcon("Inject/Basic"));
        header.add(headerCluster);
        card.withHeader(header);

        var stack = new WaStack<>();
        stack.setGap(PageSize.Small);

        var body = bodyText("GuiceContext, ClassGraph scanning, lifecycle SPI (IGuicePreStartup, IGuiceModule, " +
                "IGuicePostStartup, IGuicePreDestroy), JobService, Environment utilities, CallScope.", "m");
        body.setWaColorText("quiet");
        stack.add(body);

        var clientBody = bodyText("Client API — IGuiceContext interface, Environment, CallScoper, CallScopeProperties. " +
                "The lightweight API that all modules depend on.", "m");
        clientBody.setWaColorText("quiet");
        stack.add(clientBody);

        card.add(stack);

        var footer = new WaStack<>();
        footer.setGap(PageSize.Small);

        var coords = new WaCluster<>();
        coords.setGap(PageSize.Small);
        coords.add(coordinateBlock("com.guicedee:inject"));
        coords.add(coordinateBlock("com.guicedee:client"));
        footer.add(coords);

        var buttons = new com.jwebmp.webawesome.components.WaCluster<>();
        buttons.setGap(PageSize.ExtraSmall);

        var injectCta = new WaButton<>();
        injectCta.setText(escapeAngular("View Inject →"));
        injectCta.setVariant(Variant.Brand);
        injectCta.setAppearance(Appearance.Outlined);
        injectCta.addAttribute("(click)", "openReadme('inject', 'Inject &#40;Core Engine&#41;')");
        buttons.add(injectCta);

        var clientCta = new WaButton<>();
        clientCta.setText(escapeAngular("View Client API →"));
        clientCta.setVariant(Variant.Neutral);
        clientCta.setAppearance(Appearance.Outlined);
        clientCta.addAttribute("(click)", "openReadme('client', 'Client API')");
        buttons.add(clientCta);

        footer.add(buttons);
        card.withFooter(footer);

        grid.add(card);

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
                "vertx", "Vertx/Basic"));

        grid.add(moduleCard("Web Server",
                "HTTP/HTTPS auto-start, Router, BodyHandler, 3 SPI hooks " +
                        "(VertxHttpServerOptionsConfigurator, VertxHttpServerConfigurator, VertxRouterConfigurator).",
                "com.guicedee:web",
                "web", "Web/Basic"));

        grid.add(moduleCard("HTTP Proxy",
                "Vert.x 5 reverse proxy. ProxyModule abstract class, ProxyConnectionInfo config, " +
                        "interceptors via Guice, response caching, WebSocket forwarding, @Named multi-proxy.",
                "com.guicedee:vertx + io.vertx:vertx-http-proxy",
                "vertx", "Vertx/HttpProxy"));

        grid.add(moduleCard("REST Services",
                "Jakarta REST (JAX-RS) adapter. @Path, @GET, @POST, all parameter annotations, " +
                        "reactive returns, @Cors, @RolesAllowed, ExceptionMapper, RestInterceptor SPI.",
                "com.guicedee:rest",
                "rest", "Rest/Basic"));

        grid.add(moduleCard("REST Client",
                "@Endpoint annotation, typed RestClient<S,R>, path parameters, " +
                        "Bearer/JWT/Basic/ApiKey auth, env-var secrets, RestClientConfigurator SPI.",
                "com.guicedee:rest-client",
                "rest-client", "RestClient/Basic"));

        grid.add(moduleCard("WebSockets",
                "RFC 6455 with call-scoped connections, action-based message routing, " +
                        "group join/leave/broadcast, per-message compression (RFC 7692).",
                "com.guicedee:websockets",
                "websockets", "WebSockets/Basic"));

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
                "persistence", "Persistence/Basic"));

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
                "health", "Health/Basic"));

        grid.add(moduleCard("Metrics",
                "MicroProfile Metrics — @Counted, @Timed, @Gauge. " +
                        "Prometheus and Graphite reporting. Automatic endpoint registration.",
                "com.guicedee:metrics",
                "metrics", "Metrics/Basic"));

        grid.add(moduleCard("Telemetry",
                "@Trace, @SpanAttribute, @TelemetryOptions. OpenTelemetry with OTLP export " +
                        "to Tempo/Jaeger. Log4j2 appender for trace-correlated logs.",
                "com.guicedee:guiced-telemetry",
                "telemetry", "Telemetry/Basic"));

        grid.add(moduleCard("Fault Tolerance",
                "MicroProfile Fault Tolerance — @Retry, @CircuitBreaker, @Timeout, " +
                        "@Bulkhead, @Fallback. Guice AOP interception.",
                "com.guicedee:fault-tolerance",
                "fault-tolerance", "FaultTolerance/Basic"));

        grid.add(moduleCard("Config",
                "MicroProfile Config with SmallRye — @ConfigProperty, " +
                        "env vars → system props → properties files. Type conversion.",
                "com.guicedee.microprofile:config",
                "config", "Config/Basic"));

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
                "rabbitmq", "RabbitMQ/Basic"));

        grid.add(moduleCard("Kafka",
                "Annotation-driven Apache Kafka integration. @KafkaConnectionOptions, " +
                        "@KafkaTopicDefinition, consumers, publishers, admin client, " +
                        "worker threads, and call-scoped message handling via Vert.x Kafka Client.",
                "com.guicedee:kafka",
                "kafka", "Kafka/Basic"));

        grid.add(moduleCard("IBM MQ",
                "Annotation-driven IBM MQ integration via JMS client. " +
                        "@IBMMQConnectionOptions, @IBMMQQueueDefinition, consumers, " +
                        "publishers, and environment variable overrides.",
                "com.guicedee:ibmmq",
                "ibmmq", "IBMMQ/Basic"));

        grid.add(moduleCard("OpenAPI",
                "Auto-generated OpenAPI 3.x specification from @Path annotations. " +
                        "Served at /openapi. Runtime schema generation.",
                "com.guicedee:openapi",
                "openapi", "OpenAPI/Basic"));

        grid.add(moduleCard("Swagger UI",
                "Interactive API browser at /swagger/. Auto-configured from " +
                        "the OpenAPI specification.",
                "com.guicedee:guiced-swagger-ui",
                "swagger-ui", "OpenAPI/Basic"));

        grid.add(moduleCard("Web Services",
                "Apache CXF SOAP integration. WSDL-first or code-first " +
                        "web services with Guice injection.",
                "com.guicedee:webservices",
                "webservices", "WebServices/Basic"));

        grid.add(moduleCard("Cerial",
                "Modular serial port communications in Java. @Named port injection, " +
                        "auto-reconnect, idle monitoring, and health reporting.",
                "com.guicedee:cerial",
                "cerial", "Cerial/Basic"));

        grid.add(moduleCard("Mail Client",
                "Annotation-driven SMTP mail client with @MailConnectionOptions, " +
                        "MailService, and session pooling.",
                "com.guicedee:mail-client",
                "mail-client", "MailClient/Basic"));

        grid.add(moduleCard("Hazelcast",
                "Clustered Vert.x support with Hazelcast. Shared maps, queues, " +
                        "and distributed events.",
                "com.guicedee:hazelcast",
                "hazelcast", "Hazelcast/Basic"));

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
                "cdi", "CDI/Basic"));

        return buildSection("Migration & Compatibility", "Bridges for migrating from other DI frameworks",
                "These modules are not part of the GuicedEE foundation. They exist to assist projects " +
                        "migrating from Jakarta CDI, Spring, or other DI frameworks to the native Guice-first model. " +
                        "New projects should use Guice annotations and APIs directly.", false, grid);
    }

    private WaCard<?> moduleCard(String title, String description, String artifact, String moduleId)
    {
        return moduleCard(title, description, artifact, moduleId, null);
    }

    private WaCard<?> moduleCard(String title, String description, String artifact, String moduleId, String examplePath)
    {
        var card = new WaCard<>();
        card.setAppearance(Appearance.Outlined);

        var header = new DivSimple<>();
        var headerCluster = new WaCluster<>();
        headerCluster.setGap(PageSize.Small);
        headerCluster.setSplit();
        headerCluster.add(headingText("h3", "m", title));
        if (examplePath != null)
        {
            headerCluster.add(exampleHeaderIcon(examplePath));
        }
        header.add(headerCluster);
        card.withHeader(header);

        var stack = new WaStack<>();
        stack.setGap(PageSize.Small);

        var body = bodyText(description, "m");
        body.setWaColorText("quiet");
        stack.add(body);

        card.add(stack);

        var footer = new WaStack<>();
        footer.setGap(PageSize.Small);

        footer.add(coordinateBlock(artifact));

        var buttons = new com.jwebmp.webawesome.components.WaCluster<>();
        buttons.setGap(PageSize.ExtraSmall);

        var cta = new WaButton<>();
        cta.setText(escapeAngular("View module →"));
        cta.setVariant(Variant.Brand);
        cta.setAppearance(Appearance.Outlined);
        cta.addAttribute("(click)", "openReadme('" + moduleId + "', '" + escapeAngular(title) + "')");
        buttons.add(cta);

        footer.add(buttons);
        card.withFooter(footer);

        return card;
    }

    @Override
    public List<String> fields()
    {
        var f = new ArrayList<>(super.fields());
        f.add("readmeDialogOpen = false;");
        f.add("readmeModuleTitle = '';");
        f.add("readmeSrc = '';");
        f.add("currentRepoUrl = '';");
        f.add("""
                readmeUrls: Record<string, string> = {
                    'inject': 'https://raw.githubusercontent.com/GuicedEE/GuicedInjection/refs/heads/master/README.md',
                    'client': 'https://raw.githubusercontent.com/GuicedEE/Client/refs/heads/master/README.md',
                    'vertx': 'https://raw.githubusercontent.com/GuicedEE/Guiced-Vert.x/refs/heads/master/README.md',
                    'web': 'https://raw.githubusercontent.com/GuicedEE/GuicedVertxWeb/refs/heads/master/README.md',
                    'rest': 'https://raw.githubusercontent.com/GuicedEE/RestServices/refs/heads/master/README.md',
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
                    'webservices': 'https://raw.githubusercontent.com/GedMarc/Guiced-WebServices/refs/heads/master/README.md',
                    'cerial': 'https://raw.githubusercontent.com/GedMarc/GuicedCerial/refs/heads/master/README.md',
                    'cdi': 'https://raw.githubusercontent.com/GuicedEE/GuicedCDI/refs/heads/master/README.md',
                    'mail-client': 'https://raw.githubusercontent.com/GuicedEE/MailClient/refs/heads/master/README.md',
                    'hazelcast': 'https://raw.githubusercontent.com/GuicedEE/Hazelcast/refs/heads/master/README.md'
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
                    const raw = this.readmeUrls[moduleId] || '';
                    const match = raw.match(/raw\\.githubusercontent\\.com\\/([^\\/]+\\/[^\\/]+)/);
                    this.currentRepoUrl = match ? 'https://github.com/' + match[1] : '';
                    this.readmeDialogOpen = true;
                }
                """);
        return m;
    }
}

