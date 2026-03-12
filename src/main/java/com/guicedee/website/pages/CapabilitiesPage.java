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
import com.jwebmp.webawesome.components.details.WaDetails;
import com.jwebmp.webawesomepro.components.page.WaPageContentsAside;

@NgComponent("guicedee-capabilities")
@NgRoutable(path = "capabilities")
public class CapabilitiesPage extends WebsitePage<CapabilitiesPage> implements INgComponent<CapabilitiesPage>
{
    public CapabilitiesPage()
    {
        packCapabilities();
    }

    private void packCapabilities()
    {
        getMain().setPageSize(PageSize.ExtraLarge);

        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildIntro());
        layout.add(buildArchitectureSection());
        layout.add(buildInjectionCapability());
        layout.add(buildWebCapability());
        layout.add(buildRestCapability());
        layout.add(buildRestClientCapability());
        layout.add(buildSecurityCapability());
        layout.add(buildVerticleCapability());
        layout.add(buildPersistenceCapability());
        layout.add(buildWebSocketCapability());
        layout.add(buildMessagingCapability());
        layout.add(buildObservabilityCapability());
        layout.add(buildLoggingCapability());
        layout.add(buildConfigCapability());
        layout.add(buildFaultToleranceCapability());
        layout.add(buildJLinkCapability());
        layout.add(buildModuleCatalogSection());

        // Sidebar
        var aside = new WaPageContentsAside<>();
        aside.add(escapeAngular("Overview"));
        aside.add(escapeAngular("Architecture"));
        aside.add(escapeAngular("Injection & Lifecycle"));
        aside.add(escapeAngular("HTTP Server"));
        aside.add(escapeAngular("REST (JAX-RS)"));
        aside.add(escapeAngular("REST Client"));
        aside.add(escapeAngular("Security"));
        aside.add(escapeAngular("Verticles"));
        aside.add(escapeAngular("Persistence"));
        aside.add(escapeAngular("WebSockets"));
        aside.add(escapeAngular("Messaging"));
        aside.add(escapeAngular("Observability"));
        aside.add(escapeAngular("Logging"));
        aside.add(escapeAngular("Configuration"));
        aside.add(escapeAngular("Fault Tolerance"));
        aside.add(escapeAngular("JLink & Deployment"));
        aside.add(escapeAngular("Full catalog"));
        getAside().add(aside);
    }

    private WaCard<?> buildIntro()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(headingText("h1", "xl", "Platform Capabilities"));
        var intro = bodyText("A deep dive into every capability GuicedEE provides. Each section includes " +
                "code examples, configuration options, and the module coordinates you need to get started.", "l");
        intro.setWaColorText("quiet");
        content.add(intro);

        var tags = new WaCluster<>();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("20+ modules", Variant.Brand));
        tags.add(buildTag("Zero config", Variant.Warning));
        tags.add(buildTag("MicroProfile", Variant.Success));
        tags.add(buildTag("Vert.x 5", Variant.Success));
        content.add(tags);

        var card = new WaCard<>();
        card.setAppearance(Appearance.Filled);
        card.add(content);
        return card;
    }

    private WaCard<?> buildArchitectureSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlock(
                "┌─────────────────────────────────────────────────────────────────────┐\n" +
                "│                         Your Application                            │\n" +
                "├──────────┬──────────┬──────────┬──────────┬──────────┬──────────────┤\n" +
                "│   REST   │WebSockets│Persistence│ RabbitMQ │Telemetry │  OpenAPI     │\n" +
                "│  (JAX-RS)│ (RFC6455)│(Hibernate)│  (AMQP)  │  (OTLP)  │ (Swagger)   │\n" +
                "├──────────┴──────────┴──────────┴──────────┴──────────┴──────────────┤\n" +
                "│                    Vert.x Web · HTTP/HTTPS · Router                 │\n" +
                "├─────────────────────────────────────────────────────────────────────┤\n" +
                "│              Guiced Vert.x · EventBus · Verticles · Codecs          │\n" +
                "├──────────┬──────────┬──────────┬──────────┬─────────────────────────┤\n" +
                "│  Health  │ Metrics  │  Config  │   CDI    │    Fault Tolerance      │\n" +
                "├──────────┴──────────┴──────────┴──────────┴─────────────────────────┤\n" +
                "│        GuicedEE Inject · Guice Injector · ClassGraph · SPI          │\n" +
                "├─────────────────────────────────────────────────────────────────────┤\n" +
                "│             BOM · Parent POM · Services (JPMS wrappers)             │\n" +
                "└─────────────────────────────────────────────────────────────────────┘"));

        var explain = bodyText("Your application sits at the top. You pick the layers you need. " +
                "The inject layer and BOM are always present — everything else is opt-in.", "m");
        explain.setWaColorText("quiet");
        content.add(explain);

        return buildSection("Architecture", "Layered, composable, modular",
                "Every layer is a JPMS module. Build up your stack — never exclude down.", false, content);
    }

    private WaCard<?> buildInjectionCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("ClassGraph scanning", "Discovers annotated classes, SPI providers, and modules at startup. Fast and deterministic.", null));
        grid.add(featureCard("Guice injector", "Full Google Guice 7 injector with AOP, multibindings, and assisted inject support.", null));
        grid.add(featureCard("SPI lifecycle", "IGuiceConfigurator → IGuicePreStartup → IGuiceModule → IGuicePostStartup → IGuicePreDestroy.", null));
        grid.add(featureCard("@CallScope", "Request-scoped injection for REST requests, WebSocket connections, and event bus messages.", null));
        grid.add(featureCard("@InjectLogger", "Named Log4j2 logger injection — no static fields, no boilerplate.", null));
        grid.add(featureCard("@Verticle", "Scope components to dedicated Vert.x verticles for isolated execution.", null));
        content.add(grid);

        content.add(codeBlockWithTitle("Example: Custom lifecycle hook",
                "public class AppStartup\n" +
                "    implements IGuicePostStartup<AppStartup> {\n\n" +
                "    @Inject\n" +
                "    private SomeService service;\n\n" +
                "    @Override\n" +
                "    public void postLoad() {\n" +
                "        service.warmUpCaches();\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public Integer sortOrder() {\n" +
                "        return 100; // lower = earlier\n" +
                "    }\n" +
                "}"));

        return buildSection("Injection & Lifecycle", "The foundation of everything",
                "com.guicedee:inject — ClassGraph scanning, Guice DI, and deterministic lifecycle hooks.",
                true, content);
    }

    private WaCard<?> buildWebCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("Auto-start HTTP", "HTTP server starts via IGuicePostStartup. Default port 8080.", null));
        grid.add(featureCard("HTTPS/TLS", "JKS and PKCS#12 keystores auto-detected by file extension.", null));
        grid.add(featureCard("Body handling", "BodyHandler pre-configured with uploads, form merging, size limits.", null));
        grid.add(featureCard("Per-verticle routers", "Isolated sub-routers for @Verticle-annotated packages.", null));
        grid.add(featureCard("3 SPI extension points", "VertxHttpServerOptionsConfigurator, VertxHttpServerConfigurator, VertxRouterConfigurator.", null));
        grid.add(featureCard("Environment-driven", "HTTP_PORT, HTTPS_PORT, HTTP_ENABLED, HTTPS_ENABLED, and more.", null));
        content.add(grid);

        content.add(codeBlockWithTitle("Custom route via SPI",
                "public class MyRoutes\n" +
                "    implements VertxRouterConfigurator<MyRoutes> {\n\n" +
                "    @Override\n" +
                "    public Router builder(Router router) {\n" +
                "        router.get(\"/ping\").handler(ctx ->\n" +
                "            ctx.response().end(\"pong\"));\n" +
                "        return router;\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public Integer sortOrder() { return 500; }\n" +
                "}"));

        return buildSection("HTTP Server", "Reactive by default",
                "com.guicedee:web — Vert.x 5 HTTP/HTTPS server with Router and BodyHandler.", false, content);
    }

    private WaCard<?> buildRestCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("Full-featured REST resource",
                "@Path(\"/api/orders\")\n" +
                "@Produces(MediaType.APPLICATION_JSON)\n" +
                "public class OrderResource {\n\n" +
                "    @Inject private OrderService orders;\n\n" +
                "    @GET\n" +
                "    public List<Order> list(\n" +
                "            @QueryParam(\"status\") String status,\n" +
                "            @QueryParam(\"limit\") @DefaultValue(\"50\") int limit) {\n" +
                "        return orders.find(status, limit);\n" +
                "    }\n\n" +
                "    @GET @Path(\"/{id}\")\n" +
                "    public Order get(@PathParam(\"id\") Long id) {\n" +
                "        return orders.findById(id);\n" +
                "    }\n\n" +
                "    @POST @Consumes(MediaType.APPLICATION_JSON)\n" +
                "    public Order create(Order order) {\n" +
                "        return orders.save(order);\n" +
                "    }\n\n" +
                "    @PUT @Path(\"/{id}\")\n" +
                "    @Consumes(MediaType.APPLICATION_JSON)\n" +
                "    public Order update(@PathParam(\"id\") Long id,\n" +
                "                        Order order) {\n" +
                "        return orders.update(id, order);\n" +
                "    }\n\n" +
                "    @DELETE @Path(\"/{id}\")\n" +
                "    public void delete(@PathParam(\"id\") Long id) {\n" +
                "        orders.delete(id);\n" +
                "    }\n" +
                "}"));

        var note = bodyText("All HTTP methods, path parameters, query parameters, header parameters, " +
                "cookie parameters, form parameters, matrix parameters, and bean parameters are supported. " +
                "Jackson serialization is automatic.", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("REST (JAX-RS)", "Standard annotations, zero registration",
                "com.guicedee:rest — Jakarta REST adapter for Vert.x 5.", true, content);
    }

    private WaCard<?> buildRestClientCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("@Endpoint annotation",
                "Declare URL, HTTP method, protocol, security, and timeouts on a RestClient field. " +
                        "One annotation defines everything the client needs.",
                null));

        grid.add(featureCard("Typed RestClient<S,R>",
                "Fully generic — Jackson (de)serialization preserves nested generics. " +
                        "send() returns Uni<R>, publish() returns Uni<Void>.",
                null));

        grid.add(featureCard("Path parameters",
                "{paramName} placeholders with fluent .pathParam() builder. " +
                        "URL-encoded per RFC 3986. Unresolved placeholders throw at call time.",
                null));

        grid.add(featureCard("Multiple auth strategies",
                "Bearer, JWT, Basic, ApiKey — all via @EndpointSecurity. " +
                        "Credentials support ${ENV_VAR} placeholders.",
                null));

        grid.add(featureCard("Package-level @Endpoint",
                "Declare shared base URL and options on package-info.java. " +
                        "Field-level URLs starting with / append to the base.",
                null));

        grid.add(featureCard("RestClientConfigurator SPI",
                "Hook into WebClient construction for custom SSL, proxy, or " +
                        "Vert.x WebClientOptions.",
                null));

        content.add(grid);

        content.add(codeBlockWithTitle("REST Client — outbound calls with annotation-driven config",
                "@Endpoint(url = \"https://api.example.com/users/{userId}\",\n" +
                "          method = \"GET\",\n" +
                "          security = @EndpointSecurity(\n" +
                "              value = SecurityType.Bearer,\n" +
                "              token = \"${API_TOKEN}\"),\n" +
                "          options = @EndpointOptions(\n" +
                "              connectTimeout = 5000, readTimeout = 10000))\n" +
                "@Named(\"get-user\")\n" +
                "private RestClient<Void, UserResponse> userClient;\n\n" +
                "// Call with path parameters:\n" +
                "Uni<UserResponse> user = userClient\n" +
                "    .pathParam(\"userId\", \"123\")\n" +
                "    .send();"));

        return buildSection("REST Client", "Typed, reactive, annotation-driven",
                "com.guicedee:rest-client — outbound HTTP calls with @Endpoint, @EndpointSecurity, and Uni<R>.",
                false, content);
    }

    private WaCard<?> buildSecurityCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("@RolesAllowed",
                "Standard Jakarta annotation at class or method level. " +
                        "SecurityHandler checks roles against the authenticated Vert.x User.",
                null));

        grid.add(featureCard("@PermitAll / @DenyAll",
                "Allow or deny all access. Method-level overrides class-level. " +
                        "Works with the Vert.x authentication pipeline.",
                null));

        grid.add(featureCard("Pluggable AuthenticationHandler",
                "Set SecurityHandler.setDefaultAuthenticationHandler() with any Vert.x handler — " +
                        "JWT, OAuth2, Basic, or custom. No config files.",
                null));

        grid.add(featureCard("Pluggable AuthorizationProvider",
                "SecurityHandler.setDefaultAuthorizationProvider() for role/permission resolution. " +
                        "Works with Vert.x built-in providers.",
                null));

        grid.add(featureCard("HTTPS / TLS",
                "JKS and PKCS#12 keystores auto-detected by extension. " +
                        "HTTPS_ENABLED, HTTPS_PORT, HTTPS_KEYSTORE env vars.",
                null));

        grid.add(featureCard("@EndpointSecurity",
                "Bearer, JWT, Basic, ApiKey on outbound REST client calls. " +
                        "Secrets via ${ENV_VAR} — never in source code.",
                null));

        grid.add(featureCard("Call-scoped isolation",
                "Each request runs in a Guice CallScope. " +
                        "User context, request data, and services are isolated per request.",
                null));

        grid.add(featureCard("@Cors",
                "Annotation-driven CORS at class/method level. " +
                        "Override with REST_CORS_* env vars at deploy time.",
                null));

        content.add(grid);

        content.add(codeBlockWithTitle("Jakarta security annotations on REST endpoints",
                "@Path(\"/api/admin\")\n" +
                "@RolesAllowed({\"admin\", \"super-admin\"})\n" +
                "public class AdminResource {\n\n" +
                "    @GET @Path(\"/users\")\n" +
                "    public Uni<List<User>> listUsers() {\n" +
                "        return userService.findAll();\n" +
                "    }\n\n" +
                "    @DELETE @Path(\"/users/{id}\")\n" +
                "    @RolesAllowed(\"super-admin\")  // overrides class\n" +
                "    public Uni<Void> deleteUser(@PathParam(\"id\") String id) {\n" +
                "        return userService.delete(id);\n" +
                "    }\n\n" +
                "    @GET @Path(\"/health\")\n" +
                "    @PermitAll  // no auth needed\n" +
                "    public String health() { return \"OK\"; }\n" +
                "}"));

        return buildSection("Security", "Standard annotations, pluggable auth",
                "Jakarta @RolesAllowed + Vert.x auth handlers + @EndpointSecurity for outbound calls.",
                true, content);
    }

    private WaCard<?> buildVerticleCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("@Verticle annotation",
                "Annotate a package to create an isolated verticle with its own worker pool, " +
                        "threading model, instance count, and HA settings.",
                null));

        grid.add(featureCard("Per-verticle sub-routers",
                "VertxRouterConfigurators in @Verticle packages get their own Router, " +
                        "mounted as sub-routers on the main Router.",
                null));

        grid.add(featureCard("Capabilities enum",
                "Rest, RabbitMQ, Web, Telemetry, Persistence, Sockets, OpenAPI, Swagger, " +
                        "WebServices, Cerial — each maps to its module package.",
                null));

        grid.add(featureCard("Worker pool sizing",
                "workerPoolSize, maxWorkerExecuteTime, and maxWorkerExecuteTimeUnit " +
                        "per verticle. EVENT_LOOP vs WORKER threading model.",
                null));

        grid.add(featureCard("VertxRouterConfigurator SPI",
                "Add routes and middleware. sortOrder() controls execution order — " +
                        "infrastructure first, application routes later.",
                null));

        grid.add(featureCard("3 server SPI hooks",
                "VertxHttpServerOptionsConfigurator (before creation), " +
                        "VertxHttpServerConfigurator (after creation), VertxRouterConfigurator (routes).",
                null));

        content.add(grid);

        content.add(codeBlockWithTitle("Isolating APIs with @Verticle",
                "// package-info.java\n" +
                "@Verticle(value = \"payments-pool\",\n" +
                "          workerPoolSize = 16,\n" +
                "          maxWorkerExecuteTime = 30,\n" +
                "          maxWorkerExecuteTimeUnit = TimeUnit.SECONDS,\n" +
                "          capabilities = {Capabilities.Rest,\n" +
                "                          Capabilities.Persistence})\n" +
                "package com.example.payments;\n\n" +
                "// Any @Path resource in com.example.payments gets\n" +
                "// its own worker pool and sub-router — isolated\n" +
                "// from other API groups."));

        return buildSection("Verticles & Deployment", "Isolated worker pools, SPI-driven config",
                "@Verticle packages with per-pool threading, 3 SPI hooks, and environment-driven server config.",
                false, content);
    }

    private WaCard<?> buildPersistenceCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("Hibernate Reactive 7", "Fully async persistence with Mutiny.SessionFactory.", null));
        grid.add(featureCard("Multi-database", "PostgreSQL, MySQL, SQL Server, Oracle, and DB2.", null));
        grid.add(featureCard("Env var resolution", "${DB_URL}, ${DB_USER}, ${DB_PASSWORD} in persistence.xml.", null));
        grid.add(featureCard("Multiple units", "Multiple DatabaseModule subclasses with @Named qualifiers.", null));
        grid.add(featureCard("Vert.x connection pool", "Pre-initialized shared pools on the event loop.", null));
        grid.add(featureCard("Lifecycle managed", "PersistService.start()/stop() integrated with lifecycle hooks.", null));
        content.add(grid);

        content.add(codeBlockWithTitle("Reactive query with Mutiny",
                "@Inject\n" +
                "private Mutiny.SessionFactory sf;\n\n" +
                "public Uni<List<Product>> findByCategory(String cat) {\n" +
                "    return sf.withSession(session ->\n" +
                "        session.createQuery(\n" +
                "            \"FROM Product WHERE category = :cat\",\n" +
                "            Product.class)\n" +
                "        .setParameter(\"cat\", cat)\n" +
                "        .getResultList());\n" +
                "}"));

        return buildSection("Persistence", "Reactive JPA with zero boilerplate",
                "com.guicedee:persistence — Hibernate Reactive on Vert.x SQL Client.", false, content);
    }

    private WaCard<?> buildWebSocketCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("Call-scoped", "Each connection gets its own Guice @CallScope.", null));
        grid.add(featureCard("Action routing", "JSON messages dispatched to receivers by action name.", null));
        grid.add(featureCard("Group broadcast", "Join/leave named groups, broadcast to all members.", null));
        grid.add(featureCard("Per-message compression", "RFC 7692 enabled by default.", null));
        grid.add(featureCard("SPI lifecycle hooks", "Intercept group add/remove/publish operations.", null));
        grid.add(featureCard("Reactive", "receiveMessage() returns Uni<Void> for non-blocking flow.", null));
        content.add(grid);

        return buildSection("WebSockets", "Real-time communication",
                "com.guicedee:websockets — RFC 6455 with action-based routing and group management.", true, content);
    }

    private WaCard<?> buildMessagingCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("RabbitMQ — annotation-driven messaging",
                "@RabbitConnectionOptions(\n" +
                "    host = \"localhost\", port = 5672,\n" +
                "    user = \"guest\", password = \"guest\"\n" +
                ")\n" +
                "public class OrderEvents {\n\n" +
                "    @RabbitExchange(\"orders\")\n" +
                "    @RabbitQueue(\"order.created\")\n" +
                "    public void onOrderCreated(Order order) {\n" +
                "        // Process the order event\n" +
                "    }\n" +
                "}"));

        var features = new WaGrid<>();
        features.setMinColumnSize("14rem");
        features.setGap(PageSize.Small);
        features.add(featureCard("Annotation-driven", "Connections, exchanges, queues, consumers all via annotations.", null));
        features.add(featureCard("Auto-recovery", "Connection and channel recovery built in.", null));
        features.add(featureCard("Vert.x native", "Uses Vert.x RabbitMQ client for non-blocking IO.", null));
        features.add(featureCard("Env overrides", "Override host, port, credentials via environment variables.", null));
        content.add(features);

        return buildSection("Messaging", "Event-driven architecture",
                "com.guicedee:rabbitmq — RabbitMQ integration with Vert.x.", false, content);
    }

    private WaCard<?> buildObservabilityCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("Health — /health",
                "@Liveness, @Readiness, @Startup checks. Configurable paths. 2-second timeout per check. " +
                        "Kubernetes probe-ready.",
                "com.guicedee:health"));

        grid.add(featureCard("Metrics — Prometheus",
                "@Counted, @Timed, @Gauge annotations. Dropwizard Metrics backend. " +
                        "Prometheus scrape endpoint + Graphite reporting.",
                "com.guicedee:metrics"));

        grid.add(featureCard("Telemetry — OTLP",
                "@Trace and @SpanAttribute for distributed tracing. OTLP export to Tempo, Jaeger. " +
                        "Uni-aware span completion. Log4j2 correlation.",
                "com.guicedee:guiced-telemetry"));

        grid.add(featureCard("OpenAPI — /openapi.json",
                "OpenAPI 3.1 spec generated from JAX-RS annotations. " +
                        "Swagger UI at /swagger/ with zero code.",
                "com.guicedee:openapi + com.guicedee:guiced-swagger-ui"));

        content.add(grid);

        return buildSection("Observability", "See everything, measure everything",
                "Health, Metrics, Telemetry, and OpenAPI — all discoverable and auto-registered.", true, content);
    }

    private WaCard<?> buildLoggingCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("CLOUD=true → JSON",
                "Set CLOUD=true and all console appenders switch to compact JSON layout. " +
                        "No log4j2.xml, no code changes — configured in the GuiceContext static initializer.",
                null));

        grid.add(featureCard("Highlighted local dev",
                "ANSI-highlighted patterns with colored log levels, fixed-width logger names, and thread IDs. " +
                        "When CLOUD is not set, this is the default.",
                null));

        grid.add(featureCard("Stdout/Stderr split",
                "DEBUG and INFO go to stdout. WARN, ERROR, FATAL go to stderr. " +
                        "Containers capture them separately — no sidecar log shippers needed.",
                null));

        grid.add(featureCard("Rolling file appenders",
                "LogUtils.addFileRollingLogger() creates daily/size-based rolling appenders. " +
                        "100 MB per file, 30 files retained. Skipped entirely in cloud mode.",
                null));

        grid.add(featureCard("@InjectLogger",
                "Inject named Log4j2 loggers with @InjectLogger(\"name\"). " +
                        "No static fields, no LoggerFactory boilerplate. Wired by Log4JTypeListener.",
                null));

        grid.add(featureCard("Log4JConfigurator SPI",
                "Implement Log4JConfigurator and register via ServiceLoader. " +
                        "Runs during static init — before any application code logs.",
                null));

        grid.add(featureCard("Runtime layout switch",
                "GuiceContext.setConsoleLayout(ConsoleLayoutOption.JSON) switches all appenders " +
                        "between CURRENT, FIXED, HIGHLIGHT, and JSON at runtime. No restart.",
                null));

        grid.add(featureCard("Log level via env vars",
                "LOG_LEVEL, GUICEDEE_LOG_LEVEL, guicedee.log.level, DEBUG=true, TRACE=true. " +
                        "All resolved at startup, overridable via env vars at deploy time.",
                null));

        grid.add(featureCard("OpenTelemetry correlation",
                "When the telemetry module is present, log records are correlated with active trace spans " +
                        "and exported via OTLP alongside traces. Logs + traces in one view.",
                null));

        content.add(grid);

        content.add(codeBlockWithTitle("Cloud vs local logging — one env var",
                """
                        # Local: colorized, human-readable
                        [2026-03-06 14:23:01.123] [c.e.myservice.OrderService ] [vert.x-eventloop-0 ] [INFO ] - Order placed
                        
                        # Cloud: CLOUD=true → structured JSON
                        {"instant":{"epochSecond":1741270981,"nanoOfSecond":123000000},
                         "thread":"vert.x-eventloop-0","level":"INFO",
                         "loggerName":"com.example.myservice.OrderService",
                         "message":"Order placed"}"""));

        return buildSection("Logging", "Production-ready from day one",
                "CLOUD=true switches to JSON. Locally you get ANSI highlighting. No log4j2.xml needed.",
                false, content);
    }

    private WaCard<?> buildConfigCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("Three-tier configuration",
                "// Tier 1: Annotation defaults (in code)\n" +
                "@TelemetryOptions(\n" +
                "    serviceName = \"my-service\",\n" +
                "    otlpEndpoint = \"http://localhost:4318\"\n" +
                ")\n\n" +
                "// Tier 2: Environment override (no code change)\n" +
                "// TELEMETRY_SERVICE_NAME=production-service\n" +
                "// TELEMETRY_OTLP_ENDPOINT=http://tempo:4318\n\n" +
                "// Tier 3: SPI hook (programmatic control)\n" +
                "public class MyTelemetryConfig\n" +
                "    implements GuiceTelemetryRegistration {\n" +
                "    // Full programmatic customization\n" +
                "}"));

        var note = bodyText("This three-tier pattern applies to every GuicedEE module: " +
                "HTTP server, health, metrics, telemetry, persistence, RabbitMQ, and more. " +
                "You only need MicroProfile Config properties files if you want " +
                "@ConfigProperty injection — everything else works with annotations and env vars.", "m");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("Configuration", "Annotations → Env vars → SPI hooks",
                "com.guicedee.microprofile:config — MicroProfile Config via SmallRye.", false, content);
    }

    private WaCard<?> buildFaultToleranceCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("Resilience patterns via annotations",
                "@Retry(maxRetries = 3, delay = 200)\n" +
                "@CircuitBreaker(requestVolumeThreshold = 20,\n" +
                "    failureRatio = 0.5, delay = 5000)\n" +
                "@Timeout(value = 2000)\n" +
                "public Uni<PaymentResult> processPayment(\n" +
                "        PaymentRequest request) {\n" +
                "    return paymentGateway.charge(request);\n" +
                "}\n\n" +
                "@Fallback(fallbackMethod = \"cachedPrice\")\n" +
                "public Uni<Price> getPrice(String productId) {\n" +
                "    return priceService.lookup(productId);\n" +
                "}\n\n" +
                "public Uni<Price> cachedPrice(String productId) {\n" +
                "    return Uni.createFrom().item(priceCache.get(productId));\n" +
                "}"));

        var grid = new WaGrid<>();
        grid.setMinColumnSize("12rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("@Retry", "Automatic retry with configurable max retries and delay.", null));
        grid.add(featureCard("@CircuitBreaker", "Prevent cascading failures with circuit breaker pattern.", null));
        grid.add(featureCard("@Timeout", "Set maximum execution time for methods.", null));
        grid.add(featureCard("@Bulkhead", "Limit concurrent executions to protect resources.", null));
        grid.add(featureCard("@Fallback", "Provide alternative behavior when primary logic fails.", null));
        content.add(grid);

        return buildSection("Fault Tolerance", "Production resilience, zero effort",
                "com.guicedee:fault-tolerance — MicroProfile Fault Tolerance via Guice AOP.", true, content);
    }

    private WaCard<?> buildJLinkCapability()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("JLink custom runtimes",
                "Every module is JPMS Level 3. Run 'jlink --add-modules my.app' to build a trimmed " +
                        "JRE with only the modules you need. ~40-60 MB vs ~500 MB full JDK.",
                null));

        grid.add(featureCard("JPackage native installers",
                "MSI on Windows, DEB/RPM on Linux, DMG on macOS. Wrap your JLink image into " +
                        "a native installer — zero JDK required by end users.",
                null));

        grid.add(featureCard("Distroless Docker",
                "Copy the JLink image into a scratch or distroless base. No JDK layer, " +
                        "no package manager, no shell. Cloud Run, Kubernetes, ECS ready.",
                null));

        grid.add(featureCard("~300 ms cold start",
                "JRT skips classpath scanning for JDK modules. Combined with Vert.x non-blocking boot " +
                        "and ClassGraph, REST services are ready in under 300 ms.",
                null));

        grid.add(featureCard("50+ JPMS service wrappers",
                "Hibernate, Jackson, Vert.x, Netty, Log4j2 — all ship as proper JPMS modules. " +
                        "JLink resolves the entire dependency graph cleanly.",
                null));

        grid.add(featureCard("CI/CD integration",
                "Build JLink images in CI, push to container registry. " +
                        "Terraform/Cloud Run/K8s reference JLink images — never fat JARs on generic JDKs.",
                null));

        content.add(grid);

        content.add(codeBlockWithTitle("JLink → Distroless Docker → Deploy",
                """
                        # Build custom runtime
                        jlink --module-path target/modules \\
                          --add-modules my.service \\
                          --output target/jrt \\
                          --strip-debug --compress zip-9
                        
                        # Dockerfile
                        FROM gcr.io/distroless/base-nossl-debian12
                        COPY target/jrt /app/jrt
                        ENV CLOUD=true
                        ENTRYPOINT ["/app/jrt/bin/java", "-m", "my.service"]
                        
                        # Build, push, deploy
                        docker build -t my-service:latest .
                        docker push registry.example.com/my-service:latest"""));

        var note = bodyText("Every module declares 'provides' and 'uses' in module-info.java, " +
                "so JLink resolves the full SPI graph. No reflection hacks, no runtime classpath surprises. " +
                "What you declare is exactly what ships.", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("JLink & Deployment", "Custom runtimes in minimal containers",
                "JPMS Level 3 enables JLink, JPackage, and distroless Docker — ship your app as a ~40 MB image.",
                false, content);
    }

    private WaCard<?> buildModuleCatalogSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Every GuicedEE module in the catalog, with coordinates and descriptions.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("18rem");
        grid.setGap(PageSize.Small);

        ModuleCatalog.getModules().forEach(module ->
        {
            var details = new WaDetails<>();
            details.setSummary(module.getName() + " — " + module.getGroupId() + ":" + module.getArtifactId());
            details.add("Description: " + module.getDescription());
            details.add("Version: " + module.getVersion());
            details.add("Boot class: " + module.getBootClass());
            details.add("Docs: " + module.getReadmePath());
            grid.add(details);
        });

        content.add(grid);

        return buildSection("Full module catalog", "All " + ModuleCatalog.getModules().size() + " modules at a glance",
                "Expandable entries with coordinates, boot classes, and documentation links.",
                false, content);
    }
}
