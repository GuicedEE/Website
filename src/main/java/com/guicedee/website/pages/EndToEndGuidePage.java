package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.annotations.structures.NgField;
import com.jwebmp.core.base.angular.client.annotations.structures.NgMethod;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.card.WaCard;

@NgComponent("guicedee-end-to-end")
@NgRoutable(path = "guides/end-to-end")
@NgField("useGradle = false;")
@NgField("private _customListener: any;")
@NgField("private cdr = inject(ChangeDetectorRef);")
@NgField("private zone = inject(NgZone);")
@NgImportReference(value = "OnDestroy, OnInit, ChangeDetectorRef, inject, NgZone", reference = "@angular/core")
@NgMethod("""
        ngOnInit() {
            const saved = localStorage.getItem('guicedee-build-tool');
            if (saved) { this.useGradle = saved === 'gradle'; }
            this._customListener = (e: any) => {
                this.zone.run(() => {
                    this.useGradle = !!e.detail;
                });
            };
            window.addEventListener('guicedee-build-tool-change', this._customListener);
            window.addEventListener('storage', (e: StorageEvent) => {
                if (e.key === 'guicedee-build-tool') {
                    this.zone.run(() => {
                        this.useGradle = e.newValue === 'gradle';
                    });
                }
            });
        }""")
@NgMethod("""
        ngOnDestroy() {
            if (this._customListener) {
                window.removeEventListener('guicedee-build-tool-change', this._customListener);
            }
        }""")
public class EndToEndGuidePage extends WebsitePage<EndToEndGuidePage> implements INgComponent<EndToEndGuidePage>
{
    public EndToEndGuidePage()
    {
        buildPage();
    }

    private void buildPage()
    {
        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildIntro());
        layout.add(buildProjectSetup());
        layout.add(buildModuleDescriptor());
        layout.add(buildGuiceModuleAndBootstrap());
        layout.add(buildRestEndpoints());
        layout.add(buildSecuritySection());
        layout.add(buildCorsSection());
        layout.add(buildRestClientSection());
        layout.add(buildHealthChecks());
        layout.add(buildConfiguration());
        layout.add(buildPersistenceSection());
        layout.add(buildVerticleSection());
        layout.add(buildCloudLogging());
        layout.add(buildJLinkDeployment());
        layout.add(buildLifecycleAndEnvVars());
        layout.add(buildModulePages());
    }

    // ── Intro ─────────────────────────────────────────

    private WaCard<?> buildIntro()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(headingText("h1", "xl", "End-to-End Microservice Guide"));

        var intro = bodyText("Build a production-ready reactive microservice from scratch. " +
                "This guide covers REST endpoints with security, CORS, outbound REST clients, " +
                "database persistence with Hibernate Reactive, MicroProfile Config, health checks, " +
                "cloud-aware JSON logging, verticle isolation, and JLink deployment into distroless Docker containers.", "l");
        intro.setWaColorText("quiet");
        content.add(intro);

        var tags = new WaCluster<>();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("Real-world", Variant.Brand));
        tags.add(buildTag("Production-ready", Variant.Success));
        tags.add(buildTag("Full stack", Variant.Neutral));
        tags.add(buildTag("JLink + Docker", Variant.Warning));
        content.add(tags);

        var card = new WaCard<>();
        card.setAppearance(Appearance.Filled);
        card.add(content);
        return card;
    }

    // ── Project setup ─────────────────────────────────

    private WaStack buildProjectSetup()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Import the GuicedEE BOM and add the modules you need. " +
                "Each module is self-contained with its own JPMS descriptor — you build up, never exclude down.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        content.add(mavenGradleCodeBlock("BOM + dependencies",
                """
                        <dependencyManagement>
                            <dependencies>
                                <dependency>
                                    <groupId>com.guicedee</groupId>
                                    <artifactId>guicedee-bom</artifactId>
                                    <version>2.0.0-RC4</version>
                                    <type>pom</type>
                                    <scope>import</scope>
                                </dependency>
                            </dependencies>
                        </dependencyManagement>
                        
                        <dependencies>
                            <dependency>
                                <groupId>com.guicedee</groupId>
                                <artifactId>rest</artifactId>
                            </dependency>
                            <dependency>
                                <groupId>com.guicedee</groupId>
                                <artifactId>health</artifactId>
                            </dependency>
                            <dependency>
                                <groupId>com.guicedee.microprofile</groupId>
                                <artifactId>config</artifactId>
                            </dependency>
                            <dependency>
                                <groupId>com.guicedee</groupId>
                                <artifactId>rest-client</artifactId>
                            </dependency>
                            <dependency>
                                <groupId>com.guicedee</groupId>
                                <artifactId>persistence</artifactId>
                            </dependency>
                        </dependencies>""",
                """
                        dependencies {
                            implementation platform('com.guicedee:guicedee-bom:2.0.0-RC4')
                        
                            implementation 'com.guicedee:rest'
                            implementation 'com.guicedee:health'
                            implementation 'com.guicedee.microprofile:config'
                            implementation 'com.guicedee:rest-client'
                            implementation 'com.guicedee:persistence'
                        }"""));

        return buildSection("Project setup", "BOM + only what you need",
                "Five dependencies for a full-featured microservice. No version numbers required.", true, content);
    }

    // ── Module descriptor ─────────────────────────────

    private WaStack buildModuleDescriptor()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("src/main/java/module-info.java",
                """
                        module my.service {
                            requires com.guicedee.guicedinjection;
                            requires com.guicedee.vertx.web;
                            requires com.guicedee.rest;
                            requires com.guicedee.rest.client;
                            requires com.guicedee.health;
                            requires com.guicedee.microprofile.config;
                            requires com.guicedee.guicedpersistence;
                        
                            requires jakarta.ws.rs;
                            requires jakarta.inject;
                        
                            opens com.example.myservice to
                                com.google.guice,
                                com.guicedee.guicedinjection,
                                io.github.classgraph;
                        
                            provides com.guicedee.client.services.lifecycle.IGuiceModule
                                with com.example.myservice.AppModule;
                        }"""));

        var note = bodyText("Explicit 'requires' for every module you use. 'opens' for Guice injection. " +
                "'provides' to register your Guice module via ServiceLoader. This is JPMS Level 3 — JLink resolves " +
                "the entire graph.", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("Module descriptor", "JPMS Level 3 — explicit, safe, JLink-ready",
                "Every dependency is declared. No automatic modules, no classpath surprises.", false, content);
    }

    // ── Guice module & bootstrap ──────────────────────

    private WaStack buildGuiceModuleAndBootstrap()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("AppModule.java",
                """
                        public class AppModule extends AbstractModule
                                implements IGuiceModule<AppModule> {
                            @Override
                            protected void configure() {
                                bind(OrderService.class).to(DefaultOrderService.class);
                                bind(UserService.class).to(DefaultUserService.class);
                            }
                        }"""));

        content.add(codeBlockWithTitle("Boot.java",
                """
                        public class Boot {
                            public static void main(String[] args) {
                                IGuiceContext.registerModuleForScanning
                                    .add("com.example.myservice");
                                IGuiceContext.instance();
                            }
                        }"""));

        return buildSection("Guice module & bootstrap", "Bindings + one-line startup",
                "IGuiceContext.instance() fires the entire lifecycle: scan, inject, start servers, register routes.",
                true, content);
    }

    // ── REST endpoints ────────────────────────────────

    private WaStack buildRestEndpoints()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("OrderResource.java — full REST resource",
                """
                        @Path("/api/orders")
                        @Produces(MediaType.APPLICATION_JSON)
                        public class OrderResource {
                        
                            @Inject private OrderService orders;
                        
                            @GET
                            public Uni<List<Order>> list(
                                    @QueryParam("status") String status,
                                    @QueryParam("page") @DefaultValue("1") int page) {
                                return orders.findByStatus(status, page);
                            }
                        
                            @POST @Consumes(MediaType.APPLICATION_JSON)
                            @RolesAllowed("order:write")
                            public Uni<Response> create(OrderRequest request) {
                                return orders.create(request)
                                    .onItem().transform(order ->
                                        Response.status(201).entity(order).build());
                            }
                        
                            @GET @Path("/{id}")
                            public Uni<Order> get(@PathParam("id") String id) {
                                return orders.findById(id);
                            }
                        
                            @DELETE @Path("/{id}")
                            @RolesAllowed("admin")
                            public Uni<Void> delete(@PathParam("id") String id) {
                                return orders.delete(id);
                            }
                        }"""));

        var features = new WaGrid<>();
        features.setMinColumnSize("12rem");
        features.setGap(PageSize.Small);
        features.add(featureCard("Reactive returns", "Uni<T>, Future<T>, or plain values. Non-blocking on the event loop.", null));
        features.add(featureCard("Guice-injected", "@Inject works in every resource. Full DI lifecycle.", null));
        features.add(featureCard("Auto-discovered", "ClassGraph finds @Path classes. No registration.", null));
        features.add(featureCard("Jackson serialization", "Request/response bodies (de)serialized automatically.", null));
        content.add(features);

        return buildSection("REST endpoints", "Jakarta REST on Vert.x 5",
                "Standard @Path annotations, Guice injection, reactive returns — all auto-discovered. See the REST module page for full details.",
                false, content);
    }

    // ── Security ──────────────────────────────────────

    private WaStack buildSecuritySection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Apply standard Jakarta security annotations. Then plug in your Vert.x authentication " +
                "handler — JWT, OAuth2, Basic, or custom. No security.xml, no realm config files.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        content.add(codeBlockWithTitle("Plug in JWT authentication",
                """
                        public class SecuritySetup implements IGuicePostStartup<SecuritySetup> {
                            @Override
                            public List<Uni<Boolean>> postLoad() {
                                var jwtAuth = JWTAuth.create(vertx, new JWTAuthOptions()
                                    .addPubSecKey(new PubSecKeyOptions()
                                        .setAlgorithm("RS256")
                                        .setBuffer(publicKey)));
                        
                                SecurityHandler.setDefaultAuthenticationHandler(
                                    JWTAuthHandler.create(jwtAuth));
                        
                                return List.of(Uni.createFrom().item(true));
                            }
                        }"""));

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("@RolesAllowed", "Class or method level. Checked against the authenticated Vert.x User.", null));
        grid.add(featureCard("@PermitAll / @DenyAll", "Method-level overrides class-level. Standard Jakarta annotations.", null));
        grid.add(featureCard("HTTPS / TLS", "HTTPS_ENABLED=true, auto-detected JKS or PKCS#12 keystores.", null));
        grid.add(featureCard("Call-scoped isolation", "Each request gets its own Guice CallScope with user context.", null));
        content.add(grid);

        return buildSection("Security", "@RolesAllowed + pluggable Vert.x auth",
                "Jakarta annotations with Vert.x handlers — no config files. See the security section in capabilities for more.",
                true, content);
    }

    // ── CORS ──────────────────────────────────────────

    private WaStack buildCorsSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("@Cors annotation on a resource",
                """
                        @Path("/api/data")
                        @Cors(allowedOrigins = {"https://app.example.com"},
                              allowedMethods = {"GET", "POST", "PUT"},
                              allowCredentials = true,
                              maxAgeSeconds = 3600)
                        public class DataResource { /* ... */ }"""));

        content.add(codeBlockWithTitle("Or via environment variables",
                """
                        REST_CORS_ENABLED=true
                        REST_CORS_ALLOWED_ORIGINS=https://app.example.com
                        REST_CORS_ALLOWED_METHODS=GET,POST,PUT,DELETE
                        REST_CORS_ALLOW_CREDENTIALS=true"""));

        return buildSection("CORS", "Annotation or env-var driven",
                "Apply @Cors at class/method level. Override any value with REST_CORS_* env vars at deploy time.",
                false, content);
    }

    // ── REST client ───────────────────────────────────

    private WaStack buildRestClientSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("Declare a typed REST client",
                """
                        public class PaymentService {
                        
                            @Endpoint(url = "https://api.stripe.com/v1/charges",
                                      method = "POST",
                                      security = @EndpointSecurity(
                                          value = SecurityType.Bearer,
                                          token = "${STRIPE_SECRET_KEY}"),
                                      options = @EndpointOptions(
                                          connectTimeout = 5000, readTimeout = 10000))
                            @Named("stripe-charge")
                            private RestClient<ChargeRequest, ChargeResponse> chargeClient;
                        
                            public Uni<ChargeResponse> charge(ChargeRequest req) {
                                return chargeClient.send(req);
                            }
                        }
                        
                        // Inject by name anywhere else — no @Endpoint needed:
                        @Inject @Named("stripe-charge")
                        private RestClient<ChargeRequest, ChargeResponse> client;"""));

        var grid = new WaGrid<>();
        grid.setMinColumnSize("12rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("Bearer / JWT", "Token from ${ENV_VAR}", null));
        grid.add(featureCard("Basic auth", "Username + password from env vars", null));
        grid.add(featureCard("API Key", "Custom header with key", null));
        grid.add(featureCard("Path params", "{userId} placeholders, URL-encoded", null));
        content.add(grid);

        return buildSection("REST client", "Typed, reactive, annotation-driven outbound calls",
                "See the REST client module page for full configuration, error handling, and SPI hooks.",
                true, content);
    }

    // ── Health checks ─────────────────────────────────

    private WaStack buildHealthChecks()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("AppHealth.java — MicroProfile Health",
                """
                        @Liveness
                        public class AppHealth implements HealthCheck {
                            @Inject private OrderService orders;
                        
                            @Override
                            public HealthCheckResponse call() {
                                return HealthCheckResponse
                                    .named("order-service")
                                    .status(orders != null)
                                    .withData("version", "1.0.0")
                                    .build();
                            }
                        }"""));

        var endpoints = new WaGrid<>();
        endpoints.setMinColumnSize("14rem");
        endpoints.setGap(PageSize.Small);
        endpoints.add(featureCard("/health", "All checks aggregated", null));
        endpoints.add(featureCard("/health/live", "@Liveness — is it alive?", null));
        endpoints.add(featureCard("/health/ready", "@Readiness — accepting traffic?", null));
        endpoints.add(featureCard("/health/started", "@Startup — finished init?", null));
        content.add(endpoints);

        return buildSection("Health checks", "MicroProfile Health — Kubernetes-ready",
                "Auto-discovered, auto-registered. Paths configurable via env vars.", false, content);
    }

    // ── Configuration ─────────────────────────────────

    private WaStack buildConfiguration()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("Inject config values",
                """
                        public class DefaultOrderService implements OrderService {
                        
                            @ConfigProperty(name = "order.max-items",
                                            defaultValue = "100")
                            int maxItems;
                        
                            @ConfigProperty(name = "order.default-currency",
                                            defaultValue = "USD")
                            String currency;
                        }"""));

        var note = bodyText("Priority: 1) Environment variables  2) System properties  " +
                "3) microprofile-config.properties. Override at deploy time: ORDER_MAX_ITEMS=500", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("Configuration", "MicroProfile Config — no files required",
                "Typed @ConfigProperty injection. Override everything via env vars.", true, content);
    }

    // ── Persistence ───────────────────────────────────

    private WaStack buildPersistenceSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Hibernate Reactive 7 with Mutiny. Non-blocking database access on the Vert.x event loop. " +
                "Multi-database support with env-var driven connection details.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        content.add(codeBlockWithTitle("DatabaseModule — wire your data source",
                """
                        public class MyDatabaseModule
                                extends DatabaseModule<MyDatabaseModule> {
                            @Override
                            protected String getPersistenceUnitName() {
                                return "mydb";
                            }
                        
                            @Override
                            protected ConnectionBaseInfo getConnectionBaseInfo(
                                    Properties properties) {
                                return new PostgreSQLConnectionInfo();
                            }
                        }"""));

        content.add(codeBlockWithTitle("Reactive queries with Mutiny",
                """
                        @Inject
                        private Mutiny.SessionFactory sessionFactory;
                        
                        public Uni<Order> findById(String id) {
                            return sessionFactory.withSession(session ->
                                session.find(Order.class, id));
                        }
                        
                        public Uni<List<Order>> findByStatus(String status, int page) {
                            return sessionFactory.withSession(session ->
                                session.createQuery(
                                    "FROM Order WHERE status = :status", Order.class)
                                    .setParameter("status", status)
                                    .setFirstResult((page - 1) * 20)
                                    .setMaxResults(20)
                                    .getResultList());
                        }
                        
                        public Uni<Order> save(Order order) {
                            return sessionFactory.withTransaction(session ->
                                session.persist(order).replaceWith(order));
                        }"""));

        var features = new WaGrid<>();
        features.setMinColumnSize("14rem");
        features.setGap(PageSize.Small);
        features.add(featureCard("Hibernate Reactive 7", "Fully async with Mutiny Uni/Multi.", null));
        features.add(featureCard("Env-var driven", "DB_URL, DB_USER, DB_PASSWORD — no persistence.xml required.", null));
        features.add(featureCard("Multi-database", "Multiple DatabaseModule subclasses with @Named qualifiers.", null));
        features.add(featureCard("Vert.x pool", "Pre-initialized connection pools on the event loop.", null));
        content.add(features);

        return buildSection("Persistence", "Hibernate Reactive 7 with Mutiny",
                "Fully async database access. See the persistence module page for entities, transactions, and multi-db setup.",
                false, content);
    }

    // ── Verticle isolation ────────────────────────────

    private WaStack buildVerticleSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("package-info.java — isolate the payments API",
                """
                        @Verticle(value = "payments-pool",
                                  workerPoolSize = 16,
                                  maxWorkerExecuteTime = 30,
                                  maxWorkerExecuteTimeUnit = TimeUnit.SECONDS,
                                  capabilities = {Capabilities.Rest,
                                                  Capabilities.Persistence})
                        package com.example.payments;"""));

        content.add(codeBlockWithTitle("Server customization via SPI",
                """
                        public class MyServerOptions
                                implements VertxHttpServerOptionsConfigurator {
                            @Override
                            public HttpServerOptions builder(HttpServerOptions opts) {
                                opts.setIdleTimeout(60);
                                return opts;
                            }
                        }"""));

        var hooks = new WaGrid<>();
        hooks.setMinColumnSize("14rem");
        hooks.setGap(PageSize.Small);
        hooks.add(featureCard("VertxHttpServerOptionsConfigurator", "Before server creation — ports, TLS, buffers.", null));
        hooks.add(featureCard("VertxHttpServerConfigurator", "After creation — connection handlers, WebSocket upgrade.", null));
        hooks.add(featureCard("VertxRouterConfigurator", "Add routes and middleware. sortOrder() for execution order.", null));
        content.add(hooks);

        return buildSection("Verticle isolation", "@Verticle packages with dedicated worker pools",
                "Isolate API groups with separate threading, pool sizes, and sub-routers.", true, content);
    }

    // ── Cloud-aware logging ───────────────────────────

    private WaStack buildCloudLogging()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("One env var switches to JSON",
                """
                        # Local: colorized, human-readable
                        [2026-03-06 14:23:01] [c.e.OrderService] [INFO ] - Order 42 placed
                        
                        # Cloud: CLOUD=true → structured JSON
                        {"level":"INFO","loggerName":"c.e.OrderService",
                         "message":"Order 42 placed","traceId":"abc123"}"""));

        content.add(codeBlockWithTitle("Logging environment variables",
                """
                        CLOUD=true              # JSON logging
                        LOG_LEVEL=INFO           # Root log level
                        DEBUG=true               # Quick toggle"""));

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("No log4j2.xml", "Programmatic config in the static initializer.", null));
        grid.add(featureCard("Stdout/Stderr split", "INFO→stdout, ERROR→stderr. No sidecar.", null));
        grid.add(featureCard("@InjectLogger", "Named loggers via injection. No static fields.", null));
        grid.add(featureCard("Runtime switching", "Switch to JSON at runtime. No restart.", null));
        content.add(grid);

        return buildSection("Cloud-aware logging", "CLOUD=true — that's it",
                "Colorized locally, JSON in the cloud. No XML config files.", false, content);
    }

    // ── JLink deployment ──────────────────────────────

    private WaStack buildJLinkDeployment()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("JLink → Distroless Docker",
                """
                        # Build custom runtime (~40 MB)
                        jlink --module-path target/modules \\
                          --add-modules my.service \\
                          --output target/jrt \\
                          --strip-debug --compress zip-9
                        
                        # Dockerfile
                        FROM gcr.io/distroless/base-nossl-debian12
                        COPY target/jrt /app/jrt
                        ENV CLOUD=true
                        ENTRYPOINT ["/app/jrt/bin/java", "-m", "my.service"]
                        
                        # Build and deploy
                        docker build -t my-service:latest .
                        docker push registry.example.com/my-service:latest"""));

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("~40-60 MB images", "Distroless + JLink. No JDK, no shell.", null));
        grid.add(featureCard("~300 ms cold start", "JRT skips JDK module scanning.", null));
        grid.add(featureCard("JPackage installers", "MSI, DEB, RPM, DMG. No JDK for end users.", null));
        grid.add(featureCard("Module graph verified", "--suggest-providers validates the SPI graph.", null));
        content.add(grid);

        return buildSection("JLink deployment", "Custom runtimes in minimal Docker containers",
                "JPMS Level 3 makes this possible. Build a custom JRE and ship ~40 MB images.",
                true, content);
    }

    // ── Lifecycle & env vars ──────────────────────────

    private WaStack buildLifecycleAndEnvVars()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(mermaidDiagramWithTitle("Startup lifecycle",
                """
                        graph TD
                            A["IGuiceContext.instance()"] --> B["IGuiceConfigurator — Configure ClassGraph scan"]
                            B --> C["IGuicePreStartup — Pre-scan hooks"]
                            C --> D["ClassGraph Scan — Discover classes & SPI"]
                            D --> E["IGuiceModule — Guice module binding"]
                            E --> F["Injector Creation — Build Guice injector"]
                            F --> G["IGuicePostStartup — Start servers, routes, consumers"]
                            G --> H["IGuicePreDestroy — Graceful shutdown"]"""));

        content.add(codeBlockWithTitle("Common environment variables",
                """
                        # HTTP server
                        HTTP_ENABLED=true       HTTP_PORT=8080
                        HTTPS_ENABLED=false     HTTPS_PORT=8443
                        HTTPS_KEYSTORE=/path    HTTPS_KEYSTORE_PASSWORD=changeit
                        VERTX_MAX_BODY_SIZE=524288000
                        
                        # Logging
                        CLOUD=true    LOG_LEVEL=INFO    DEBUG=true
                        
                        # CORS
                        REST_CORS_ENABLED=true
                        REST_CORS_ALLOWED_ORIGINS=https://app.example.com
                        
                        # Telemetry
                        TELEMETRY_SERVICE_NAME=my-service
                        TELEMETRY_OTLP_ENDPOINT=http://tempo:4318
                        
                        # Database
                        DB_URL=jdbc:postgresql://db:5432/mydb
                        DB_USER=postgres    DB_PASSWORD=secret"""));

        var tiers = new WaGrid<>();
        tiers.setMinColumnSize("14rem");
        tiers.setGap(PageSize.Small);
        tiers.add(featureCard("1. Annotations", "Sensible defaults in code — @HealthOptions, @TelemetryOptions.", null));
        tiers.add(featureCard("2. Environment vars", "Override any annotation value at deploy time.", null));
        tiers.add(featureCard("3. SPI hooks", "Programmatic control — VertxConfigurator, RestClientConfigurator.", null));
        content.add(tiers);

        return buildSection("Lifecycle & environment", "Three-tier configuration — annotations → env vars → SPI",
                "Override anything at deploy time without touching code.", false, content);
    }

    // ── Module pages ──────────────────────────────────

    private WaStack buildModulePages()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Each GuicedEE module has its own dedicated page with full API documentation, " +
                "configuration reference, SPI extension points, and code examples.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("REST services", "@Path, @GET, CORS, security, ExceptionMapper, RestInterceptor.", "/modules/rest"));
        grid.add(featureCard("REST client", "@Endpoint, RestClient<S,R>, auth strategies, path params.", "/modules/rest-client"));
        grid.add(featureCard("Persistence", "Hibernate Reactive 7, Mutiny, DatabaseModule, multi-db.", "/modules/persistence"));
        grid.add(featureCard("Health", "@Liveness, @Readiness, @Startup, env-var paths.", "/modules/health"));
        grid.add(featureCard("Config", "@ConfigProperty, source priority, env-var overrides.", "/modules/config"));
        grid.add(featureCard("Vert.x core", "EventBus, Verticles, Codecs, deployment.", "/modules/vertx"));
        grid.add(featureCard("Web server", "HTTP/HTTPS, Router, BodyHandler, 3 SPI hooks.", "/modules/web"));
        grid.add(featureCard("WebSockets", "RFC 6455, action routing, group broadcast.", "/modules/websockets"));
        grid.add(featureCard("RabbitMQ", "Annotation-driven queues, exchanges, consumers.", "/modules/rabbitmq"));
        grid.add(featureCard("Telemetry", "@Trace, OTLP export, Log4j2 correlation.", "/modules/telemetry"));
        grid.add(featureCard("Metrics", "@Counted, @Timed, Prometheus endpoint.", "/modules/metrics"));
        grid.add(featureCard("OpenAPI + Swagger", "Auto-generated spec + browsable UI.", "/modules/openapi"));
        grid.add(featureCard("Fault Tolerance", "@Retry, @CircuitBreaker, @Timeout, @Bulkhead.", "/modules/fault-tolerance"));
        grid.add(featureCard("CDI Bridge (Migration)", "Migration aid: @ApplicationScoped, @RequestScoped, BeanManager → Guice. Not a foundation module.", "/modules/cdi"));
        grid.add(featureCard("Inject (core)", "GuiceContext, ClassGraph, lifecycle SPI, JobService.", "/modules/inject"));
        content.add(grid);

        var ctas = new WaCluster<>();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("Explore capabilities", "capabilities", Variant.Brand, Appearance.Filled));
        ctas.add(buildCta("Open the app builder", "builder", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("View on GitHub", "github", Variant.Neutral, Appearance.Outlined));
        content.add(ctas);

        return buildSection("Module deep-dives", "Every module has its own page",
                "Full API docs, configuration reference, SPI hooks, and examples for each module.",
                true, content);
    }
}

