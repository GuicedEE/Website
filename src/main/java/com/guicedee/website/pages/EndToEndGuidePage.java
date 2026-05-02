package com.guicedee.website.pages;

import com.guicedee.website.App;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;

import java.util.ArrayList;
import java.util.List;
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

@NgComponent("guicedee-end-to-end")
@NgRoutable(path = "guides/end-to-end")
public class EndToEndGuidePage extends WebsitePage<EndToEndGuidePage> implements INgComponent<EndToEndGuidePage>
{

    public EndToEndGuidePage()
    {
        buildPage();
    }

    private void buildPage()
    {
        var layout = new WaStack<>();
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
        var issuesLink = new com.jwebmp.core.base.html.Link<>("{{currentRepoUrl + '/issues'}}", "_blank");
        var issuesIcon = new WaIcon<>("bug");
        issuesIcon.addStyle("cursor", "pointer");
        issuesIcon.addStyle("font-size", "var(--wa-font-size-l)");
        issuesLink.add(issuesIcon);
        issuesLink.add(new WaTooltip<>(issuesIcon).setText("Log an Issue").setPlacement(TooltipPlacement.Top));
        actions.add(issuesLink);

        headerCluster.add(actions);
        dialogHeader.add(headerCluster);
        dialog.add(dialogHeader);

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

    // ── Intro ─────────────────────────────────────────

    private WaCard<?> buildIntro()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(headingText("h1", "xl", "End-to-End Guide"));

        var intro = bodyTextHtml("Build production-ready reactive microservices and " + brandCode("moduliths") + " from scratch. " +
                "This guide covers REST endpoints with security, CORS, outbound REST clients, " +
                "database persistence with " + brandCode("Hibernate Reactive") + ", " + brandCode("MicroProfile Config") + ", health checks, " +
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
        var content = new WaStack<>();
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
                                    <version>2.0.0</version>
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
                            implementation platform('com.guicedee:guicedee-bom:2.0.0')
                        
                            implementation 'com.guicedee:rest'
                            implementation 'com.guicedee:health'
                            implementation 'com.guicedee.microprofile:config'
                            implementation 'com.guicedee:rest-client'
                            implementation 'com.guicedee:persistence'
                        }"""));

        return buildSection("Project setup", "BOM + only what you need",
                "Five dependencies for full-featured microservices and moduliths. No version numbers required.", true, content);
    }

    // ── Module descriptor ─────────────────────────────

    private WaStack buildModuleDescriptor()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("src/main/java/module-info.java",
                """
                        module my.service {
                            requires transitive com.guicedee.guicedinjection;
                            opens com.example.myservice to com.google.guice;
                        
                            provides com.guicedee.client.services.lifecycle.IGuiceModule
                                with com.example.myservice.AppModule;
                        }"""));

        var note = bodyTextHtml("Explicit " + brandCode("requires") + " for every module you use. " + brandCode("opens") + " for private field and AOP injection. " +
                brandCode("provides") + " to register your Guice module via " + brandCode("ServiceLoader") + ". This is JPMS Level 3 — JLink resolves " +
                "the entire graph.", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("Module descriptor", "JPMS Level 3 — explicit, safe, JLink-ready",
                "Every dependency is declared. No automatic modules, no classpath surprises.", false, content);
    }

    // ── Guice module & bootstrap ──────────────────────

    private WaStack buildGuiceModuleAndBootstrap()
    {
        var content = new WaStack<>();
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
                                IGuiceContext.instance().inject();
                            }
                        }"""));

        return buildSection("Guice module & bootstrap", "Bindings + one-line startup",
                "IGuiceContext.instance().inject() fires the entire lifecycle: scan, inject, start servers, register routes.",
                true, content);
    }

    // ── REST endpoints ────────────────────────────────

    private WaStack buildRestEndpoints()
    {
        var content = new WaStack<>();
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
        features.add(featureCardHtml("Reactive returns", brandCode("Uni&lt;T&gt;") + ", " + brandCode("Future&lt;T&gt;") + ", or plain values. Non-blocking on the event loop.", null));
        features.add(featureCardHtml("Guice-injected", brandCode("@Inject") + " works in every resource. Full DI lifecycle.", null));
        features.add(featureCardHtml("Auto-discovered", brandCode("ClassGraph") + " finds " + brandCode("@Path") + " classes. No registration.", null));
        features.add(featureCard("Jackson serialization", "Request/response bodies (de)serialized automatically.", null));
        content.add(features);

        return buildSection("REST endpoints", "Jakarta REST on Vert.x 5",
                "Standard @Path annotations, Guice injection, reactive returns — all auto-discovered. See the REST module page for full details.",
                false, content);
    }

    // ── Security ──────────────────────────────────────

    private WaStack buildSecuritySection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var desc = bodyTextHtml("Apply standard Jakarta security annotations. Then plug in your " + brandCode("Vert.x") + " authentication " +
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
        grid.add(featureCardHtml("@RolesAllowed", "Class or method level. Checked against the authenticated " + brandCode("Vert.x User") + ".", null));
        grid.add(featureCard("@PermitAll / @DenyAll", "Method-level overrides class-level. Standard Jakarta annotations.", null));
        grid.add(featureCardHtml("HTTPS / TLS", brandCode("HTTPS_ENABLED=true") + ", auto-detected JKS or PKCS#12 keystores.", null));
        grid.add(featureCardHtml("Call-scoped isolation", "Each request gets its own " + brandCode("Guice CallScope") + " with user context.", null));
        content.add(grid);

        return buildSection("Security", "@RolesAllowed + pluggable Vert.x auth",
                "Jakarta annotations with Vert.x handlers — no config files. See the security section in capabilities for more.",
                true, content);
    }

    // ── CORS ──────────────────────────────────────────

    private WaStack buildCorsSection()
    {
        var content = new WaStack<>();
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
        var content = new WaStack<>();
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
        var content = new WaStack<>();
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
        var content = new WaStack<>();
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

        var note = bodyTextHtml("Priority: 1) Environment variables  2) System properties  " +
                "3) microprofile-config.properties. Override at deploy time: ORDER_MAX_ITEMS=500", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("Configuration", "MicroProfile Config — no files required",
                "Typed @ConfigProperty injection. Override everything via env vars.", true, content);
    }

    // ── Persistence ───────────────────────────────────

    private WaStack buildPersistenceSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var desc = bodyTextHtml(brandCode("Hibernate Reactive 7") + " with " + brandCode("Mutiny") + ". Non-blocking database access on the " + brandCode("Vert.x") + " event loop. " +
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
                                    PersistenceUnitDescriptor unit, Properties properties) {
                                return ConnectionBaseInfoFactory.createConnectionBaseInfo("postgresql");
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
        features.add(featureCardHtml("Hibernate Reactive 7", "Fully async with " + brandCode("Mutiny") + " " + brandCode("Uni") + "/" + brandCode("Multi") + ".", null));
        features.add(featureCardHtml("DatabaseModule required", "GuicedEE creates the persistence unit from supplied properties, merged with " + brandCode("persistence.xml") + " if provided.", null));
        features.add(featureCardHtml("Multi-database", "Multiple " + brandCode("DatabaseModule") + " subclasses with " + brandCode("@Named") + " qualifiers.", null));
        features.add(featureCardHtml("ConnectionBaseInfoFactory", "Use " + brandCode("ConnectionBaseInfoFactory.createConnectionBaseInfo(\"postgresql\")") + " — supports postgresql, mysql, sqlserver, oracle, db2.", null));
        features.add(featureCardHtml("Vert.x pool", "Pre-initialized connection pools on the event loop.", null));
        content.add(features);

        return buildSection("Persistence", "Hibernate Reactive 7 with Mutiny",
                "Fully async database access. See the persistence module page for entities, transactions, and multi-db setup.",
                false, content);
    }

    // ── Verticle isolation ────────────────────────────

    private WaStack buildVerticleSection()
    {
        var content = new WaStack<>();
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
        hooks.add(featureCardHtml("VertxHttpServerOptionsConfigurator", "Before server creation — ports, TLS, buffers.", null));
        hooks.add(featureCardHtml("VertxHttpServerConfigurator", "After creation — connection handlers, WebSocket upgrade.", null));
        hooks.add(featureCardHtml("VertxRouterConfigurator", "Add routes and middleware. " + brandCode("sortOrder()") + " for execution order.", null));
        content.add(hooks);

        return buildSection("Verticle isolation", "@Verticle packages with dedicated worker pools",
                "Isolate API groups with separate threading, pool sizes, and sub-routers.", true, content);
    }

    // ── Cloud-aware logging ───────────────────────────

    private WaStack buildCloudLogging()
    {
        var content = new WaStack<>();
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
        grid.add(featureCardHtml("No log4j2.xml required", "Programmatic config in the static initializer. Existing log4j2.xml files are still honoured if present.", null));
        grid.add(featureCard("Stdout/Stderr split", "INFO→stdout, ERROR→stderr. No sidecar.", null));
        grid.add(featureCardHtml("@InjectLogger", "Named loggers via injection. No static fields.", null));
        grid.add(featureCard("Runtime switching", "Switch to JSON at runtime. No restart.", null));
        content.add(grid);

        return buildSection("Cloud-aware logging", "CLOUD=true — that's it",
                "Colorized locally, JSON in the cloud. Standard log4j2.xml and Log4j2 APIs still work — this is a programmatic alternative, not a replacement.", false, content);
    }

    // ── JLink deployment ──────────────────────────────

    private WaStack buildJLinkDeployment()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(mavenGradleCodeBlock("Moditect JLink → Distroless Docker",
                """
                        <!-- pom.xml — moditect-maven-plugin creates the JLink image -->
                        <plugin>
                            <groupId>org.moditect</groupId>
                            <artifactId>moditect-maven-plugin</artifactId>
                            <executions>
                                <execution>
                                    <id>add-module-infos</id>
                                    <phase>none</phase>
                                    <configuration combine.self="override">
                                        <skip>true</skip>
                                    </configuration>
                                </execution>
                                <execution>
                                    <id>create-runtime-image</id>
                                    <phase>package</phase>
                                    <goals>
                                        <goal>create-runtime-image</goal>
                                    </goals>
                                    <configuration>
                                        <modulePath>
                                            <path>${project.build.directory}/libs</path>
                                            <path>${project.build.directory}/${project.build.finalName}.jar</path>
                                        </modulePath>
                                        <modules>
                                            <module>my.service</module>
                                        </modules>
                                        <jarInclusionPolicy>NONE</jarInclusionPolicy>
                                        <launcher>
                                            <name>myservice</name>
                                            <module>my.service/com.example.Main</module>
                                        </launcher>
                                        <noHeaderFiles>true</noHeaderFiles>
                                        <noManPages>true</noManPages>
                                        <stripDebug>true</stripDebug>
                                        <outputDirectory>
                                            ${project.build.directory}/jlink-image
                                        </outputDirectory>
                                    </configuration>
                                </execution>
                            </executions>
                        </plugin>
                        
                        # Dockerfile
                        FROM gcr.io/distroless/base-nossl-debian12
                        COPY target/jlink-image /app/jrt
                        ENV CLOUD=true
                        ENTRYPOINT ["/app/jrt/bin/myservice"]
                        
                        # Build and deploy
                        mvn clean package
                        docker build -t my-service:latest .
                        docker push registry.example.com/my-service:latest""",
                """
                        // build.gradle — moditect Gradle plugin creates the JLink image
                        plugins {
                            id 'org.moditect.gradleplugin' version '1.3.0.Final'
                        }
                        
                        moditect {
                            createRuntimeImage {
                                modulePath = [
                                    file("${buildDir}/libs"),
                                    tasks.jar.archiveFile
                                ]
                                modules = ['my.service']
                                jarInclusionPolicy = 'NONE'
                                launcher {
                                    name = 'myservice'
                                    module = 'my.service/com.example.Main'
                                }
                                noHeaderFiles = true
                                noManPages = true
                                stripDebug = true
                                outputDirectory = file("${buildDir}/jlink-image")
                            }
                        }
                        
                        // Dockerfile
                        // FROM gcr.io/distroless/base-nossl-debian12
                        // COPY build/jlink-image /app/jrt
                        // ENV CLOUD=true
                        // ENTRYPOINT ["/app/jrt/bin/myservice"]
                        
                        // Build and deploy
                        // gradle build
                        // docker build -t my-service:latest .
                        // docker push registry.example.com/my-service:latest"""
        ));

        var jlinkNote = bodyTextHtml("<strong>Tip:</strong> You can also invoke " + brandCode("jlink") + " directly from the command line: " +
                brandCode("jlink --module-path target/libs --add-modules my.service --output target/jlink-image --strip-debug --no-header-files --no-man-pages") +
                ". The moditect plugin simply wraps this into your build lifecycle.", "s");
        jlinkNote.setWaColorText("quiet");
        content.add(jlinkNote);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);
        grid.add(featureCard("~40-60 MB images", "Distroless + JLink. No JDK, no shell.", null));
        grid.add(featureCard("~300 ms cold start", "JRT skips JDK module scanning.", null));
        grid.add(featureCard("JPackage installers", "MSI, DEB, RPM, DMG. No JDK for end users.", null));
        grid.add(featureCard("Module graph verified", "Moditect validates the SPI graph at build time.", null));
        content.add(grid);

        return buildSection("JLink deployment", "Custom runtimes in minimal Docker containers",
                "JPMS Level 3 makes this possible. Build a custom JRE and ship ~40 MB images.",
                true, content);
    }

    // ── Lifecycle & env vars ──────────────────────────

    private WaStack buildLifecycleAndEnvVars()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(mermaidDiagramWithTitle("Startup lifecycle",
                """
                        graph LR
                            subgraph init ["Initialization"]
                                A["IGuiceContext.instance()"] --> B["IGuiceConfigurator"]
                                B --> C["IGuicePreStartup"]
                            end
                            subgraph discovery ["Discovery & Binding"]
                                C --> D["ClassGraph Scan"]
                                D --> E["IGuiceModule"]
                            end
                            subgraph runtime ["Runtime"]
                                E --> F["Injector Creation"]
                                F --> G["IGuicePostStartup"]
                                G --> H["IGuicePreDestroy"]
                            end
                            
                            style init fill:#f9f,stroke:#333,stroke-width:2px
                            style discovery fill:#bbf,stroke:#333,stroke-width:2px
                            style runtime fill:#dfd,stroke:#333,stroke-width:2px"""));

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
        tiers.add(featureCardHtml("1. Annotations", "Sensible defaults in code — " + brandCode("@HealthOptions") + ", " + brandCode("@TelemetryOptions") + ".", null));
        tiers.add(featureCard("2. Environment vars", "Override any annotation value at deploy time.", null));
        tiers.add(featureCardHtml("3. SPI hooks", "Programmatic control — " + brandCode("VertxConfigurator") + ", " + brandCode("RestClientConfigurator") + ".", null));
        content.add(tiers);

        return buildSection("Lifecycle & environment", "Three-tier configuration — annotations → env vars → SPI",
                "Override anything at deploy time without touching code.", false, content);
    }

    // ── Module pages ──────────────────────────────────

    private WaStack buildModulePages()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Each GuicedEE module has its own dedicated page with full API documentation, " +
                "configuration reference, SPI extension points, and code examples.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Small);

        grid.add(guideModuleCard("REST services", brandCode("@Path") + ", " + brandCode("@GET") + ", CORS, security, " + brandCode("ExceptionMapper") + ", " + brandCode("RestInterceptor") + ".", "rest", "Rest/Basic"));
        grid.add(guideModuleCard("REST client", brandCode("@Endpoint") + ", " + brandCode("RestClient&lt;S,R&gt;") + ", auth strategies, path params.", "rest-client", "RestClient/Basic"));
        grid.add(guideModuleCard("Persistence", brandCode("Hibernate Reactive 7") + ", " + brandCode("Mutiny") + ", " + brandCode("DatabaseModule") + ", multi-db.", "persistence", "Persistence/Basic"));
        grid.add(guideModuleCard("Health", brandCode("@Liveness") + ", " + brandCode("@Readiness") + ", " + brandCode("@Startup") + ", env-var paths.", "health", "Health/Basic"));
        grid.add(guideModuleCard("Config", brandCode("@ConfigProperty") + ", source priority, env-var overrides.", "config", "Config/Basic"));
        grid.add(guideModuleCard("Vert.x core", brandCode("EventBus") + ", Verticles, Codecs, deployment.", "vertx", "Vertx/Basic"));
        grid.add(guideModuleCard("Web server", "HTTP/HTTPS, " + brandCode("Router") + ", " + brandCode("BodyHandler") + ", 3 SPI hooks.", "web", "Web/Basic"));
        grid.add(guideModuleCard("WebSockets", "RFC 6455, action routing, group broadcast.", "websockets", "WebSockets/Basic"));
        grid.add(guideModuleCard("RabbitMQ", "Annotation-driven queues, exchanges, consumers.", "rabbitmq", "RabbitMQ/Basic"));
        grid.add(guideModuleCard("Kafka", "Annotation-driven topics, consumers, publishers with Vert.x Kafka Client.", "kafka", "Kafka/Basic"));
        grid.add(guideModuleCard("IBM MQ", "Annotation-driven queues, consumers, publishers with IBM MQ JMS.", "ibmmq", "IBMMQ/Basic"));
        grid.add(guideModuleCard("Telemetry", brandCode("@Trace") + ", OTLP export, Log4j2 correlation.", "telemetry", "Telemetry/Basic"));
        grid.add(guideModuleCard("Metrics", brandCode("@Counted") + ", " + brandCode("@Timed") + ", Prometheus endpoint.", "metrics", "Metrics/Basic"));
        grid.add(guideModuleCard("OpenAPI + Swagger", "Auto-generated spec + browsable UI.", "openapi", "OpenAPI/Basic"));
        grid.add(guideModuleCard("GraphQL", "SPI-driven HTTP + WebSocket + GraphiQL endpoints.", "graphql", "GraphQL/Basic"));
        grid.add(guideModuleCard("Fault Tolerance", brandCode("@Retry") + ", " + brandCode("@CircuitBreaker") + ", " + brandCode("@Timeout") + ", " + brandCode("@Bulkhead") + ".", "fault-tolerance", "FaultTolerance/Basic"));
        grid.add(guideModuleCard("CDI Bridge (Migration)", "Migration aid: " + brandCode("@ApplicationScoped") + ", " + brandCode("@RequestScoped") + ", " + brandCode("BeanManager") + " → " + brandCode("Guice") + ". Not a foundation module.", "cdi", "CDI/Basic"));
        grid.add(guideModuleCard("Inject (core)", brandCode("GuiceContext") + ", " + brandCode("ClassGraph") + ", lifecycle SPI, " + brandCode("JobService") + ".", "inject", "Inject/Basic"));
        content.add(grid);

        var ctas = new WaCluster<>();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("Explore capabilities", "capabilities", Variant.Brand, Appearance.Outlined));
        ctas.add(buildCta("Open the app builder", "builder", Variant.Neutral, Appearance.Outlined));
        ctas.add(browseExamplesButton());
        content.add(ctas);

        return buildSection("Module deep-dives", "Every module has its own page",
                "Full API docs, configuration reference, SPI hooks, and examples for each module.",
                true, content);
    }
    private WaCard<?> guideModuleCard(String title, String bodyHtml, String moduleId, String examplePath)
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

        var bodyCopy = bodyTextHtml(bodyHtml, "m");
        bodyCopy.setWaColorText("quiet");
        stack.add(bodyCopy);

        card.add(stack);

        var footer = new WaStack<>();
        footer.setGap(PageSize.Small);

        var cta = new WaButton<>();
        cta.setText(escapeAngular("View documentation →"));
        cta.setVariant(Variant.Brand);
        cta.setAppearance(Appearance.Outlined);
        cta.addAttribute("(click)", "openReadme('" + moduleId + "', '" + escapeAngular(title) + "')");
        footer.add(cta);
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
                    'hazelcast': 'https://raw.githubusercontent.com/GuicedEE/Hazelcast/refs/heads/master/README.md',
                    'graphql': 'https://raw.githubusercontent.com/GuicedEE/GraphQL/refs/heads/master/README.md'
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

