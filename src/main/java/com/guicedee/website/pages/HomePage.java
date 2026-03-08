package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaSplit;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.card.WaCard;

@NgComponent("guicedee-home")
@NgRoutable(path = "home")
public class HomePage extends WebsitePage<HomePage> implements INgComponent<HomePage>
{
    public HomePage()
    {
        buildLandingPage();
    }

    private void buildLandingPage()
    {
        getMain().setPageSize(PageSize.ExtraLarge);

        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildHero());
        layout.add(buildThreeLinesPitch());
        layout.add(buildZeroConfigSection());
        layout.add(buildCodeShowcaseSection());
        layout.add(buildRestServicesSection());
        layout.add(buildSecuritySection());
        layout.add(buildVertxDeploymentSection());
        layout.add(buildMicroProfileSection());
        layout.add(buildFoundationSection());
        layout.add(buildModularBuildSection());
        layout.add(buildJLinkShippingSection());
        layout.add(buildCloudAwareLoggingSection());
        layout.add(buildPluginSection());
        layout.add(buildDeveloperSection());
        layout.add(buildCallToActionSection());
    }

    // ── Hero ──────────────────────────────────────────

    private WaCard<?> buildHero()
    {
        var hero = new WaCard<>();
        hero.setAppearance(Appearance.Filled);

        var row = new WaSplit();
        row.row();
        row.setGap(PageSize.Large);
        row.alignItems("center");

        var copy = new WaStack();
        copy.setGap(PageSize.Medium);

        var tags = new WaCluster();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("GuicedEE", Variant.Brand));
        tags.add(buildTag("Vert.x 5", Variant.Success));
        tags.add(buildTag("Java 25+", Variant.Neutral));
        tags.add(buildTag("JPMS Level 3", Variant.Neutral));
        tags.add(buildTag("Zero Config", Variant.Warning));
        tags.add(buildTag("MicroProfile", Variant.Success));
        copy.add(tags);

        copy.add(headingText("h1", "xl", "Modular enterprise Java, reactive by default."));

        var subtitle = bodyText("Build reactive microservices with Google Guice, Vert.x 5, and MicroProfile — " +
                "all on the Java Module System. Zero XML. Zero boilerplate. Just annotate and go.", "l");
        subtitle.setWaColorText("quiet");
        copy.add(subtitle);

        var ctas = new WaCluster();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("Get started in 5 minutes", "getting-started", Variant.Brand, Appearance.Filled));
        ctas.add(buildCta("End-to-end guide", "guides/end-to-end", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("Browse modules", "modules", Variant.Neutral, Appearance.Outlined));
        copy.add(ctas);

        var stats = new WaGrid<>();
        stats.setMinColumnSize("14rem");
        stats.setGap(PageSize.Small);
        stats.add(heroCard("~300 ms startup", "JLink builds on JRT start REST services in under 300 ms."));
        stats.add(heroCard("128 MB / 0.5 CPU", "Runs comfortably in minimal container targets."));
        stats.add(heroCard("20+ modules", "REST, WebSockets, Persistence, RabbitMQ, Health, Metrics, and more."));
        stats.add(heroCard("Zero config files", "Annotation-driven everything. Override with env vars at deploy time."));

        row.add(copy);
        row.add(stats);
        hero.add(row);
        return hero;
    }

    // ── Three-line pitch ─────────────────────────────

    private WaCard<?> buildThreeLinesPitch()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("1. Add a dependency",
                "Import the BOM, pick the modules you need, and Maven handles the rest. No plugin zoo, no starter generators.",
                "One BOM, zero surprises."));

        grid.add(featureCard("2. Write your code",
                "Use standard annotations — @Path, @Inject, @Liveness, @ConfigProperty. " +
                        "GuicedEE discovers everything via ClassGraph and wires it through Guice.",
                "Standards you already know."));

        grid.add(featureCard("3. Run it",
                "Call IGuiceContext.instance().inject() and your HTTP server, REST routes, WebSocket handlers, " +
                        "and persistence units all light up automatically.",
                "One line to launch."));

        return buildSection("How it works", "Three steps to a reactive microservice",
                "No code generators, no runtime reflection hacks, no XML plumbing.",
                true, grid);
    }

    // ── Zero-configuration section ────────────────────

    private WaCard<?> buildZeroConfigSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var intro = bodyText("Every GuicedEE module follows the same philosophy: sensible defaults out of the box, " +
                "annotations for customization, and environment variables for deployment overrides. " +
                "You'll never touch an XML configuration file (unless you want MicroProfile Config properties files).", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("No persistence.xml?",
                "Configure your database with @EntityManager annotations and environment variables. " +
                        "Or use a minimal persistence.xml with ${ENV_VAR} placeholders — your choice.",
                "Both paths are first-class citizens."));

        grid.add(featureCard("No web.xml ever",
                "The Vert.x HTTP server starts automatically when you add the 'web' module. " +
                        "Port, TLS, body limits — all controlled via environment variables or SPI hooks.",
                "HTTP_PORT=8080, done."));

        grid.add(featureCard("No beans.xml",
                "Guice doesn't need one. GuicedEE discovers your modules, bindings, and lifecycle hooks via JPMS ServiceLoader and ClassGraph.",
                "SPI is the configuration."));

        grid.add(featureCard("No application.yml",
                "Use @ConfigProperty from MicroProfile Config if you want external properties. " +
                        "Otherwise, annotate and override with env vars at deploy time.",
                "Annotations > YAML."));

        content.add(grid);

        content.add(codeBlockWithTitle("Traditional framework vs GuicedEE",
                """
                        // ❌ Traditional framework:
                        //   - application.yml
                        //   - beans.xml
                        //   - web.xml
                        //   - persistence.xml
                        //   - logback.xml
                        //   - 47 starter dependencies
                        //   - A code generator to set it all up
                        //   - A dev console for management
                        
                        // ✅ GuicedEE:
                        IGuiceContext.instance().inject();
                        // That's it. Your REST endpoints, health checks,
                        // persistence units, and WebSocket handlers are live."""));

        return buildSection("Zero-file-based-config philosophy", "No XML. No YAML. No boilerplate.",
                "Configuration lives in your code, not in a file tree. Override anything at deploy time with environment variables. You can of course still use configuration if you desire",
                false, content);
    }

    // ── Code showcase ─────────────────────────────────

    private WaCard<?> buildCodeShowcaseSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Large);

        // REST example
        var restStack = new WaStack();
        restStack.setGap(PageSize.Small);
        restStack.add(headingText("h3", "m", "REST endpoint — standard Jakarta annotations"));
        restStack.add(codeBlock(
                """
                        @Path("/api/products")
                        public class ProductResource {
                        
                            @Inject
                            private ProductService products;
                        
                            @GET
                            @Produces(MediaType.APPLICATION_JSON)
                            public List<Product> list() {
                                return products.findAll();
                            }
                        
                            @POST
                            @Consumes(MediaType.APPLICATION_JSON)
                            @Produces(MediaType.APPLICATION_JSON)
                            public Product create(Product product) {
                                return products.save(product);
                            }
                        }"""));
        var restNote = bodyText("Routes are discovered by ClassGraph at startup and registered on the Vert.x Router. " +
                "No registration code needed — just annotate and ship. Common JAX-RS syntax for simplicity", "s");
        restNote.setWaColorText("quiet");
        restStack.add(restNote);
        content.add(restStack);

        content.add(divider());

        // Health check example
        var healthStack = new WaStack();
        healthStack.setGap(PageSize.Small);
        healthStack.add(headingText("h3", "m", "Health checks — MicroProfile standard"));
        healthStack.add(codeBlock(
                """
                        @Liveness
                        public class DatabaseHealth implements HealthCheck {
                        
                            @Inject
                            private Mutiny.SessionFactory sessionFactory;
                        
                            @Override
                            public HealthCheckResponse call() {
                                return HealthCheckResponse.named("database")
                                    .status(sessionFactory != null)
                                    .build();
                            }
                        }
                        // Exposed at /health/live automatically"""));
        content.add(healthStack);

        content.add(divider());

        // WebSocket example
        var wsStack = new WaStack();
        wsStack.setGap(PageSize.Small);
        wsStack.add(headingText("h3", "m", "WebSockets — action-based routing"));
        wsStack.add(codeBlock(
                """
                        public class ChatReceiver
                            implements IWebSocketMessageReceiver<Void, ChatReceiver> {
                        
                            @Override
                            public Set<String> messageNames() {
                                return Set.of("chat");
                            }
                        
                            @Override
                            public Uni<Void> receiveMessage(
                                    WebSocketMessageReceiver<?> message) {
                                // broadcast to all group members
                                return IGuicedWebSocket.broadcastGroup(
                                    message.getData().toString());
                            }
                        }"""));
        content.add(wsStack);

        content.add(divider());

        // Telemetry example
        var telemetryStack = new WaStack();
        telemetryStack.setGap(PageSize.Small);
        telemetryStack.add(headingText("h3", "m", "Distributed tracing — just add @Trace"));
        telemetryStack.add(codeBlock(
                """
                        @TelemetryOptions(
                            serviceName = "order-service",
                            otlpEndpoint = "http://tempo:4318"
                        )
                        public class OrderService {
                        
                            @Trace
                            public Uni<Order> placeOrder(
                                    @SpanAttribute("customer.id") String customerId) {
                                // This method is automatically traced.
                                // Spans are exported to Tempo/Jaeger via OTLP.
                                return processOrder(customerId);
                            }
                        }"""));
        content.add(telemetryStack);

        return buildSection("See the code", "Real examples, not toy demos",
                "Every snippet below runs in production today. Copy, paste, ship.",
                true, content);
    }

    // ── REST services deep dive ───────────────────────

    private WaCard<?> buildRestServicesSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Large);

        var intro = bodyText("GuicedEE's REST module adapts standard Jakarta REST (JAX-RS) annotations to the Vert.x 5 Router. " +
                "Routes are discovered at startup by ClassGraph, resource instances come from the Guice injector, " +
                "and methods can return plain values, Uni<T>, or Future<T>. CORS, security, and exception mapping all work " +
                "via annotations and SPI — no web.xml, no Application subclass, no registration code.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        // REST features grid
        var features = new WaGrid<>();
        features.setMinColumnSize("14rem");
        features.setGap(PageSize.Small);

        features.add(featureCard("Zero-config routes",
                "@Path, @GET, @POST, @PUT, @DELETE, @PATCH — OperationRegistry scans ClassGraph results " +
                        "and maps them to Vert.x routes at startup. No registration code.",
                null));

        features.add(featureCard("Full parameter binding",
                "@PathParam, @QueryParam, @HeaderParam, @CookieParam, @FormParam, @MatrixParam, @BeanParam — " +
                        "all Jakarta REST parameter annotations are supported.",
                null));

        features.add(featureCard("Reactive returns",
                "Return Uni<T>, Future<T>, or plain values. Reactive types run on the event loop; " +
                        "blocking methods are dispatched to a worker pool automatically.",
                null));

        features.add(featureCard("@Cors annotation",
                "Apply CORS at class or method level. Configures allowed origins, methods, headers, " +
                        "credentials, and max age. Override any value with REST_CORS_* env vars.",
                null));

        features.add(featureCard("RestInterceptor SPI",
                "Hook into request start/end for logging, metrics, or cross-cutting concerns. " +
                        "Implement onStart() and onEnd() — both return Future<Boolean>.",
                null));

        features.add(featureCard("ExceptionMapper",
                "Standard jakarta.ws.rs.ext.ExceptionMapper SPI with cause-chain traversal. " +
                        "Map exceptions to HTTP status codes and error response bodies.",
                null));

        features.add(featureCard("@Verticle worker pools",
                "Resources in @Verticle-annotated packages automatically use their named worker pool. " +
                        "Isolate API groups with different threading and pool sizes.",
                null));

        features.add(featureCard("JAX-RS Response",
                "Return jakarta.ws.rs.core.Response for full control over status codes, " +
                        "headers, and entity bodies. Works alongside reactive returns.",
                null));

        content.add(features);

        // REST endpoint with Guice injection
        content.add(codeBlockWithTitle("Full REST resource with injection, CORS, and reactive returns",
                """
                        @Path("/api/orders")
                        @Cors(allowedOrigins = {"https://app.example.com"},
                              allowedMethods = {"GET", "POST"},
                              allowCredentials = true)
                        public class OrderResource {
                        
                            @Inject
                            private OrderService orderService;
                        
                            @GET
                            @Produces(MediaType.APPLICATION_JSON)
                            public Uni<List<Order>> listOrders(
                                    @QueryParam("status") String status,
                                    @QueryParam("page") @DefaultValue("1") int page) {
                                return orderService.findByStatus(status, page);
                            }
                        
                            @POST
                            @Consumes(MediaType.APPLICATION_JSON)
                            @Produces(MediaType.APPLICATION_JSON)
                            @RolesAllowed("order:write")
                            public Uni<Response> createOrder(OrderRequest request) {
                                return orderService.create(request)
                                    .onItem().transform(order ->
                                        Response.status(201)
                                            .entity(order)
                                            .build());
                            }
                        
                            @GET @Path("/{id}")
                            @Produces(MediaType.APPLICATION_JSON)
                            public Uni<Order> getOrder(@PathParam("id") String id) {
                                return orderService.findById(id);
                            }
                        }"""));

        // REST Client
        content.add(divider());

        var clientIntro = new WaStack();
        clientIntro.setGap(PageSize.Small);
        clientIntro.add(headingText("h3", "m", "REST Client — annotation-driven outbound calls"));
        var clientDesc = bodyText("Need to call external APIs? Declare an @Endpoint on a RestClient<Send, Receive> field, " +
                "add @Named, and inject. URL, method, authentication, timeouts, and connection options are all annotation-driven. " +
                "Every call returns Uni<Receive> for fully reactive composition.", "s");
        clientDesc.setWaColorText("quiet");
        clientIntro.add(clientDesc);
        content.add(clientIntro);

        content.add(codeBlock(
                """
                        public class PaymentService {
                        
                            // Define the endpoint once — URL, auth, and timeouts
                            @Endpoint(url = "https://api.stripe.com/v1/charges",
                                      method = "POST",
                                      security = @EndpointSecurity(
                                          value = SecurityType.Bearer,
                                          token = "${STRIPE_SECRET_KEY}"),
                                      options = @EndpointOptions(
                                          connectTimeout = 5000,
                                          readTimeout = 10000))
                            @Named("stripe-charge")
                            private RestClient<ChargeRequest, ChargeResponse> chargeClient;
                        
                            public Uni<ChargeResponse> charge(ChargeRequest req) {
                                return chargeClient.send(req);
                            }
                        }
                        
                        // Inject the same client anywhere by name — no @Endpoint needed:
                        public class RefundService {
                            @Inject @Named("stripe-charge")
                            private RestClient<ChargeRequest, ChargeResponse> chargeClient;
                        }"""));

        // CORS env vars reference
        content.add(codeBlockWithTitle("CORS environment variables",
                """
                        # Enable/disable CORS (annotation values are defaults)
                        REST_CORS_ENABLED=true
                        REST_CORS_ALLOWED_ORIGINS=https://app.example.com,https://admin.example.com
                        REST_CORS_ALLOWED_METHODS=GET,POST,PUT,DELETE,PATCH,OPTIONS
                        REST_CORS_ALLOWED_HEADERS=Content-Type,Authorization,X-Request-Id
                        REST_CORS_ALLOW_CREDENTIALS=true
                        REST_CORS_MAX_AGE=3600"""));

        return buildSection("REST services", "Jakarta REST on Vert.x 5 — zero registration, full power",
                "Standard @Path annotations, Guice-injected resources, reactive returns, CORS, security, and a typed REST client — all auto-discovered.",
                false, content);
    }

    // ── Security section ──────────────────────────────

    private WaCard<?> buildSecuritySection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Large);

        var intro = bodyText("Security in GuicedEE uses standard Jakarta security annotations with pluggable Vert.x " +
                "authentication and authorization handlers. No security.xml, no realm configuration files — " +
                "annotate your resources and plug in your auth provider via SPI.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        // Security features grid
        var features = new WaGrid<>();
        features.setMinColumnSize("14rem");
        features.setGap(PageSize.Small);

        features.add(featureCard("@RolesAllowed",
                "Standard Jakarta annotation. Apply at class or method level. " +
                        "GuicedEE's SecurityHandler checks roles against the authenticated Vert.x User.",
                null));

        features.add(featureCard("@PermitAll / @DenyAll",
                "Allow or deny all access at class or method level. " +
                        "Method-level annotations override class-level ones.",
                null));

        features.add(featureCard("Pluggable AuthenticationHandler",
                "Call SecurityHandler.setDefaultAuthenticationHandler() with any Vert.x AuthenticationHandler — " +
                        "JWT, OAuth2, Basic, or your own custom handler.",
                null));

        features.add(featureCard("Pluggable AuthorizationProvider",
                "Plug in role/permission resolution via SecurityHandler.setDefaultAuthorizationProvider(). " +
                        "Works with Vert.x's built-in providers or your own.",
                null));

        features.add(featureCard("REST Client @EndpointSecurity",
                "Bearer, JWT, Basic, and ApiKey authentication on outbound REST calls. " +
                        "Credentials support ${ENV_VAR} placeholders — secrets never touch source code.",
                null));

        features.add(featureCard("HTTPS / TLS",
                "JKS and PKCS#12 keystores auto-detected by file extension. " +
                        "Set HTTPS_ENABLED=true, HTTPS_KEYSTORE, and HTTPS_KEYSTORE_PASSWORD.",
                null));

        features.add(featureCard("Call-scoped isolation",
                "Each HTTP request runs in a Guice CallScope with its own CallScopeProperties. " +
                        "User context, request data, and scoped services are isolated per request.",
                null));

        features.add(featureCard("CORS security",
                "@Cors annotation with env var overrides for origins, methods, headers, credentials, and max age. " +
                        "Applied per-class, per-method, or globally via environment variables.",
                null));

        content.add(features);

        // Security code examples
        content.add(codeBlockWithTitle("Securing REST endpoints with Jakarta annotations",
                """
                        @Path("/api/admin")
                        @RolesAllowed({"admin", "super-admin"})
                        public class AdminResource {
                        
                            @GET @Path("/users")
                            @Produces(MediaType.APPLICATION_JSON)
                            public Uni<List<User>> listUsers() {
                                return userService.findAll();  // requires admin role
                            }
                        
                            @DELETE @Path("/users/{id}")
                            @RolesAllowed("super-admin")  // method overrides class
                            public Uni<Void> deleteUser(@PathParam("id") String id) {
                                return userService.delete(id);
                            }
                        
                            @GET @Path("/health")
                            @PermitAll  // no auth needed for this endpoint
                            public String health() { return "OK"; }
                        }"""));

        content.add(codeBlockWithTitle("Plugging in JWT authentication",
                """
                        // In your IGuicePostStartup hook or module:
                        public class SecuritySetup implements IGuicePostStartup<SecuritySetup> {
                        
                            @Override
                            public List<Uni<Boolean>> postLoad() {
                                // Vert.x 5 JWT auth
                                var jwtAuth = JWTAuth.create(vertx, new JWTAuthOptions()
                                    .addPubSecKey(new PubSecKeyOptions()
                                        .setAlgorithm("RS256")
                                        .setBuffer(publicKey)));
                        
                                SecurityHandler.setDefaultAuthenticationHandler(
                                    JWTAuthHandler.create(jwtAuth));
                        
                                return List.of(Uni.createFrom().item(true));
                            }
                        }"""));

        content.add(codeBlockWithTitle("REST Client with secure outbound calls",
                """
                        // Bearer token from environment variable
                        @Endpoint(url = "https://api.example.com/v2/data",
                                  security = @EndpointSecurity(
                                      value = SecurityType.Bearer,
                                      token = "${API_TOKEN}"))
                        @Named("secure-api")
                        private RestClient<Request, Response> secureClient;
                        
                        // Basic auth
                        @Endpoint(url = "https://legacy.example.com/soap",
                                  security = @EndpointSecurity(
                                      value = SecurityType.Basic,
                                      username = "${LEGACY_USER}",
                                      password = "${LEGACY_PASS}"))
                        @Named("legacy-api")
                        private RestClient<Request, Response> legacyClient;
                        
                        // API Key in custom header
                        @Endpoint(url = "https://maps.googleapis.com/maps/api",
                                  security = @EndpointSecurity(
                                      value = SecurityType.ApiKey,
                                      apiKey = "${GOOGLE_API_KEY}",
                                      apiKeyHeader = "X-Goog-Api-Key"))
                        @Named("google-maps")
                        private RestClient<Request, Response> mapsClient;"""));

        content.add(codeBlockWithTitle("HTTPS / TLS configuration",
                """
                        # Enable HTTPS alongside HTTP
                        HTTPS_ENABLED=true
                        HTTPS_PORT=8443
                        HTTPS_KEYSTORE=/etc/ssl/keystore.p12
                        HTTPS_KEYSTORE_PASSWORD=${KEYSTORE_PASSWORD}
                        
                        # Keystore format is auto-detected by extension:
                        #   .jks       → Java KeyStore
                        #   .p12 .pfx  → PKCS#12
                        #   .p8        → PKCS#8
                        
                        # Generate a dev keystore:
                        # keytool -genkey -alias dev -keyalg RSA -keysize 2048 \\
                        #   -validity 365 -keystore keystore.jks -storepass changeit"""));

        return buildSection("Security", "Standard annotations, pluggable authentication, secrets in env vars",
                "Jakarta @RolesAllowed, Vert.x auth handlers, @EndpointSecurity for outbound calls, and HTTPS — all without config files.",
                true, content);
    }

    // ── Vert.x deployment & configuration ─────────────

    private WaCard<?> buildVertxDeploymentSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Large);

        var intro = bodyText("The Vert.x 5 HTTP server, router, and verticle infrastructure are fully managed by GuicedEE. " +
                "Three SPI extension points let you customize every layer. Per-verticle isolation means you can run " +
                "different API groups on separate worker pools with their own threading models.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        // Deployment features grid
        var features = new WaGrid<>();
        features.setMinColumnSize("14rem");
        features.setGap(PageSize.Small);

        features.add(featureCard("Auto-start HTTP/HTTPS",
                "VertxWebServerPostStartup runs as IGuicePostStartup. Creates HTTP and HTTPS servers " +
                        "from environment config. Compression level 9, TCP keepalive, and configurable limits.",
                null));

        features.add(featureCard("VertxHttpServerOptionsConfigurator",
                "SPI to customize HttpServerOptions before servers are created. " +
                        "Set idle timeouts, buffer sizes, ALPN, or any Vert.x option.",
                null));

        features.add(featureCard("VertxHttpServerConfigurator",
                "SPI to configure HttpServer instances after creation. " +
                        "Add connection handlers, WebSocket upgrade logic, or metrics hooks.",
                null));

        features.add(featureCard("VertxRouterConfigurator",
                "SPI to add routes and middleware to the Router. Sorted by sortOrder() — " +
                        "infrastructure runs first, application routes later.",
                null));

        features.add(featureCard("@Verticle annotation",
                "Annotate a package to create an isolated verticle with its own worker pool, " +
                        "threading model, instance count, and HA settings.",
                null));

        features.add(featureCard("Per-verticle sub-routers",
                "VertxRouterConfigurator implementations in @Verticle packages get their own Router, " +
                        "mounted as a sub-router on the main Router.",
                null));

        features.add(featureCard("Capabilities enum",
                "@Verticle can declare capabilities: Rest, RabbitMQ, Web, Telemetry, Persistence, " +
                        "Sockets, OpenAPI, Swagger, and more. Each maps to its module package.",
                null));

        features.add(featureCard("Startup flow control",
                "IGuicePostStartup hooks use sortOrder() for deterministic startup. " +
                        "Web server starts early (MIN_VALUE + 500), your routes register after.",
                null));

        content.add(features);

        // Verticle example
        content.add(codeBlockWithTitle("Isolating APIs with @Verticle worker pools",
                """
                        // package-info.java — isolate the payments API
                        @Verticle(value = "payments-pool",
                                  workerPoolSize = 16,
                                  maxWorkerExecuteTime = 30,
                                  maxWorkerExecuteTimeUnit = TimeUnit.SECONDS,
                                  capabilities = {Capabilities.Rest, Capabilities.Persistence})
                        package com.example.payments;
                        
                        import com.guicedee.vertx.spi.Verticle;
                        import com.guicedee.vertx.spi.Verticle.Capabilities;
                        import java.util.concurrent.TimeUnit;
                        
                        // Any @Path resource in com.example.payments gets its own
                        // worker pool and sub-router — isolated from other API groups."""));

        // SPI example
        content.add(codeBlockWithTitle("Customizing the HTTP server via SPI",
                """
                        // Customize server options before creation
                        public class MyServerOptions
                                implements VertxHttpServerOptionsConfigurator {
                            @Override
                            public HttpServerOptions builder(HttpServerOptions opts) {
                                opts.setIdleTimeout(60);
                                opts.setMaxWebSocketFrameSize(1048576);
                                return opts;
                            }
                        }
                        
                        // Register in module-info.java:
                        provides VertxHttpServerOptionsConfigurator
                            with MyServerOptions;"""));

        // Router SPI
        content.add(codeBlockWithTitle("Adding middleware with VertxRouterConfigurator",
                """
                        public class ApiMiddleware
                                implements VertxRouterConfigurator<ApiMiddleware> {
                        
                            @Override
                            public Router builder(Router router) {
                                // Request logging
                                router.route("/api/*").handler(ctx -> {
                                    log.info("{} {}", ctx.request().method(),
                                                      ctx.request().path());
                                    ctx.next();
                                });
                        
                                // Static files
                                router.route("/static/*")
                                    .handler(StaticHandler.create("webroot"));
                        
                                return router;
                            }
                        
                            @Override
                            public Integer sortOrder() {
                                return 100;  // infrastructure runs early
                            }
                        }"""));

        // Environment variables table
        content.add(codeBlockWithTitle("HTTP/HTTPS server environment variables",
                """
                        # HTTP server
                        HTTP_ENABLED=true
                        HTTP_PORT=8080
                        
                        # HTTPS server
                        HTTPS_ENABLED=true
                        HTTPS_PORT=8443
                        HTTPS_KEYSTORE=/etc/ssl/keystore.p12
                        HTTPS_KEYSTORE_PASSWORD=changeit
                        
                        # Body handler
                        VERTX_MAX_BODY_SIZE=524288000   # 500 MB default
                        
                        # Server defaults (applied before SPI configurators):
                        # Compression: enabled, level 9
                        # TCP keepalive: true
                        # Max header/chunk/form size: 65536 bytes
                        # Max form fields: unlimited (-1)"""));

        return buildSection("Vert.x deployment", "HTTP/HTTPS servers, verticles, and SPI extension points",
                "Three SPI hooks to customize the server, per-verticle isolation for API groups, and environment-driven configuration.",
                false, content);
    }

    // ── MicroProfile section ──────────────────────────

    private WaCard<?> buildMicroProfileSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var intro = bodyText("GuicedEE implements key Eclipse MicroProfile specifications through Guice integration. " +
                "If you know the MicroProfile annotations, you already know GuicedEE.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("MicroProfile Config",
                "Inject configuration with @ConfigProperty. Sources: environment variables (highest priority), " +
                        "system properties, and META-INF/microprofile-config.properties. Profile support included.",
                "SmallRye Config under the hood."));

        grid.add(featureCard("MicroProfile Health",
                "Standard @Liveness, @Readiness, and @Startup health checks. " +
                        "Discovered by ClassGraph and exposed as /health JSON endpoints on the Vert.x Router.",
                "Kubernetes-ready out of the box."));

        grid.add(featureCard("MicroProfile Metrics",
                "@Counted, @Timed, @Gauge — backed by Dropwizard Metrics with a Prometheus scrape endpoint. " +
                        "Add the module and your metrics are live.",
                "Prometheus + Graphite reporting."));

        grid.add(featureCard("MicroProfile Fault Tolerance",
                "@Retry, @CircuitBreaker, @Timeout, @Bulkhead, @Fallback — " +
                        "resilience patterns wired through Guice AOP interception.",
                "Production resilience, zero effort."));

        grid.add(featureCard("MicroProfile OpenAPI",
                "OpenAPI 3.1 spec generation from your JAX-RS annotations. " +
                        "Served at /openapi.json and /openapi.yaml with zero configuration.",
                "Swagger UI auto-mounts at /swagger/."));

        grid.add(featureCard("OpenTelemetry",
                "@Trace and @SpanAttribute for distributed tracing. OTLP export to Tempo, Jaeger, or any collector. " +
                        "Uni-aware — spans complete when the reactive chain resolves.",
                "Correlated logs via Log4j2 appender."));

        content.add(grid);

        content.add(codeBlockWithTitle("MicroProfile Config in action",
                """
                        // META-INF/microprofile-config.properties
                        // messaging.enabled=true
                        // messaging.bootstrap.servers=localhost:9092
                        
                        public class MessagingService {
                        
                            @ConfigProperty(name = "messaging.enabled",
                                            defaultValue = "true")
                            boolean enabled;
                        
                            @ConfigProperty(name = "messaging.bootstrap.servers")
                            String bootstrapServers;
                        }
                        
                        // Override at deploy time:
                        // MESSAGING_ENABLED=false
                        // MESSAGING_BOOTSTRAP_SERVERS=kafka-prod:9092"""));

        return buildSection("MicroProfile native", "Enterprise standards, Guice-powered",
                "Eclipse MicroProfile specifications implemented through SPI discovery and Guice injection.",
                false, content);
    }

    // ── Foundation section ────────────────────────────

    private WaCard<?> buildFoundationSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("Guice-first DI",
                "All lifecycle, routing, persistence, messaging, and telemetry are wired through a single Guice injector. " +
                        "@Inject works everywhere — in REST resources, health checks, WebSocket receivers, even in event bus consumers.",
                "One injector to rule them all."));

        grid.add(featureCard("Vert.x 5 reactive core",
                "Non-blocking HTTP, WebSockets, event bus, SQL clients, and RabbitMQ — all on the Vert.x event loop " +
                        "with Mutiny Uni/Multi support throughout the stack.",
                "Built for modern runtime constraints."));

        grid.add(featureCard("JPMS Level 3",
                "Every module ships a proper module-info.java with explicit exports, requires, provides, and uses. " +
                        "Build JLink and JPackage distributables confidently.",
                "Real modules, not automatic modules."));

        grid.add(featureCard("SPI + ClassGraph",
                "Modules, lifecycle hooks, REST resources, health checks, consumers — everything is discovered automatically at startup. " +
                        "No manual registration, no classpath scanning hacks.",
                "Convention over configuration, done right."));

        return buildSection("Foundations", "What makes GuicedEE different",
                "A cohesive platform where Guice DI, Vert.x reactivity, and JPMS modularity work together — not against each other.",
                true, grid);
    }

    // ── Modular build section ─────────────────────────

    private WaCard<?> buildModularBuildSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("Build it up, never exclude down",
                "Start with inject and add only what you need: rest, persistence, websockets, rabbitmq. " +
                        "Each module is self-contained with its own JPMS descriptor.",
                "Your runtime is only as big as you choose."));

        grid.add(featureCard("JLink / JPackage ready",
                "Every artifact is JPMS Level 3 — build custom JRE images with only the modules your app needs. " +
                        "Deploy as a native installer or minimal Docker image.",
                "From 500 MB JRE to 40 MB custom runtime."));

        grid.add(featureCard("One BOM, all versions aligned",
                "Import guicedee-bom and jwebmp-bom in your dependencyManagement. " +
                        "All 20+ modules are version-aligned and tested together.",
                "No dependency conflicts, ever."));

        grid.add(featureCard("Grouped library wrappers",
                "Third-party libraries (Hibernate, Jackson, Vert.x) are repackaged with proper module-info.java descriptors. " +
                        "They plug into the module system cleanly.",
                "Over 50 JPMS-wrapped service modules."));

        content.add(grid);

        content.add(codeBlockWithTitle("The BOM — one import, everything aligned",
                """
                        <dependencyManagement>
                            <dependencies>
                                <dependency>
                                    <groupId>com.guicedee</groupId>
                                    <artifactId>guicedee-bom</artifactId>
                                    <version>2.0.0-SNAPSHOT</version>
                                    <type>pom</type>
                                    <scope>import</scope>
                                </dependency>
                            </dependencies>
                        </dependencyManagement>
                        
                        <!-- Then just add what you need — no versions required -->
                        <dependencies>
                            <dependency>
                                <groupId>com.guicedee</groupId>
                                <artifactId>rest</artifactId>
                            </dependency>
                            <dependency>
                                <groupId>com.guicedee</groupId>
                                <artifactId>persistence</artifactId>
                            </dependency>
                        </dependencies>"""));

        return buildSection("Modular by design", "Ship only what you need",
                "A composable stack where you build up your runtime — never exclude down from a kitchen-sink starter.",
                false, content);
    }

    // ── JLink / Docker shipping section ───────────────

    private WaCard<?> buildJLinkShippingSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var intro = bodyText("Because every GuicedEE module is JPMS Level 3 with explicit module-info.java descriptors, " +
                "you can use JLink to create a custom Java runtime that contains only the modules your application actually uses. " +
                "The result is a self-contained image — no external JDK required at deploy time — that ships in a minimal Docker container.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("Custom JRE via JLink",
                "Run 'jlink --module-path ... --add-modules my.app --output jrt' to build a trimmed Java runtime. " +
                        "Only the modules your app requires are included — everything else is stripped.",
                "From ~500 MB JDK to ~40 MB custom image."));

        grid.add(featureCard("JPackage installers",
                "Use 'jpackage' to wrap your JLink image into a native installer — " +
                        "MSI on Windows, DEB/RPM on Linux, DMG on macOS. Zero JDK installation required by end users.",
                "Real desktop/server installers."));

        grid.add(featureCard("Minimal Docker containers",
                "Copy the JLink image into a scratch or distroless base. " +
                        "No JDK layer, no package manager, no shell — just your application and its runtime. " +
                        "Cloud Run, Kubernetes, and ECS ship these instantly.",
                "~40-60 MB Docker images."));

        grid.add(featureCard("~300 ms startup on JRT",
                "JLink custom runtimes skip classpath scanning for the JDK modules themselves. " +
                        "Combined with Vert.x's non-blocking boot and ClassGraph's fast scanning, " +
                        "REST services are ready in under 300 ms.",
                "Cold-start friendly for serverless."));

        grid.add(featureCard("CI/CD pipeline integration",
                "Your CI builds the JLink image and pushes it to a container registry. " +
                        "Terraform / Cloud Run / Kubernetes deploys reference the JLink image — never a fat JAR on a generic JDK.",
                "No JARs in production."));

        grid.add(featureCard("50+ JPMS service wrappers",
                "Third-party libraries (Hibernate, Jackson, Vert.x, Netty, Log4j2) ship as proper JPMS modules " +
                        "with 'exports', 'requires', and 'provides'. JLink resolves the entire dependency graph cleanly.",
                "No automatic-module hacks."));

        content.add(grid);

        content.add(codeBlockWithTitle("JLink → Docker in three commands",
                """
                        # 1. Build the JLink custom runtime
                        jlink \\
                          --module-path target/modules \\
                          --add-modules my.service \\
                          --output target/jrt \\
                          --strip-debug \\
                          --no-header-files \\
                          --no-man-pages \\
                          --compress zip-9
                        
                        # 2. Package into a minimal Docker image
                        # Dockerfile:
                        #   FROM gcr.io/distroless/base-nossl-debian12
                        #   COPY target/jrt /app/jrt
                        #   ENTRYPOINT ["/app/jrt/bin/java", "-m", "my.service"]
                        docker build -t my-service:latest .
                        
                        # 3. Push and deploy
                        docker push registry.example.com/my-service:latest
                        # Deploy to Cloud Run, Kubernetes, ECS, etc."""));

        content.add(codeBlockWithTitle("Verify your module graph before shipping",
                """
                        # List resolved modules to verify nothing unexpected leaked in
                        jlink \\
                          --module-path target/modules \\
                          --add-modules my.service \\
                          --suggest-providers
                        
                        # Or check what your app actually uses at runtime
                        java --show-module-resolution -m my.service"""));

        var deployNote = bodyText("Every GuicedEE module declares its SPI 'provides' and 'uses' directives in module-info.java, " +
                "so JLink can resolve the full service graph automatically. No reflection hacks, no runtime classpath surprises. " +
                "What you see in 'module-info.java' is exactly what ships.", "s");
        deployNote.setWaColorText("quiet");
        content.add(deployNote);

        return buildSection("Ship as JLink artifacts", "Custom runtimes in minimal Docker containers",
                "JPMS Level 3 means every module participates in JLink. Build a custom JRE, copy it into a distroless container, and deploy instantly.",
                true, content);
    }

    // ── Cloud-aware logging section ───────────────────

    private WaCard<?> buildCloudAwareLoggingSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var intro = bodyText("GuicedEE ships production-ready logging out of the box. Locally, you get colorized, " +
                "human-readable console output with highlighting. In the cloud, set one environment variable and every " +
                "log line switches to compact JSON — ready for ingestion by CloudWatch, Stackdriver, Loki, Datadog, or any log aggregator.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("CLOUD=true → JSON logs",
                "Set the CLOUD environment variable to 'true' and all console appenders switch to compact JSON layout automatically. " +
                        "No log4j2.xml required, no code changes — it's built into the GuiceContext static initializer.",
                "One env var for production logging."));

        grid.add(featureCard("Highlighted local output",
                "When CLOUD is not set, console output uses ANSI-highlighted patterns with colored log levels, " +
                        "fixed-width logger names, and thread identifiers. Debug and trace are easy to spot.",
                "Developer-friendly by default."));

        grid.add(featureCard("Rolling file appenders",
                "LogUtils.addFileRollingLogger() creates daily/size-based rolling appenders. " +
                        "100 MB per file, 30 files retained, automatic rollover. " +
                        "In cloud mode, file loggers are skipped — only console JSON is emitted.",
                "Zero-config file rotation."));

        grid.add(featureCard("@InjectLogger",
                "Inject named Log4j2 loggers into any Guice-managed class with @InjectLogger(\"name\"). " +
                        "No static Logger fields, no LoggerFactory calls. The Log4JTypeListener wires them automatically.",
                "Clean, testable logging."));

        grid.add(featureCard("Log4JConfigurator SPI",
                "Need custom appenders or filters? Implement Log4JConfigurator and register via ServiceLoader. " +
                        "Your configurator runs during static initialization — before any application code logs.",
                "Full programmatic control."));

        grid.add(featureCard("Dynamic layout switching",
                "Call GuiceContext.setConsoleLayout(ConsoleLayoutOption.JSON) at any time to switch all console appenders " +
                        "between CURRENT, FIXED, HIGHLIGHT, and JSON layouts. No restart required.",
                "Runtime control."));

        grid.add(featureCard("Log level via environment",
                "Set LOG_LEVEL, GUICEDEE_LOG_LEVEL, or guicedee.log.level to control the root logger level. " +
                        "Or use DEBUG=true / TRACE=true for quick toggle. All resolved at startup, overridable at deploy time.",
                "No log4j2.xml needed."));

        grid.add(featureCard("OpenTelemetry log correlation",
                "When the telemetry module is active, an OpenTelemetry Log4j2 appender is auto-registered. " +
                        "Log records are correlated with active trace spans and exported via OTLP alongside your traces.",
                "Logs + traces in one view."));

        content.add(grid);

        content.add(codeBlockWithTitle("Cloud logging in action",
                """
                        # Local development — colorized, human-readable:
                        [2026-03-06 14:23:01.123] [c.e.myservice.OrderService ] [vert.x-eventloop-0 ] [INFO ] - Order 42 placed
                        
                        # Cloud deployment — set one env var:
                        # CLOUD=true
                        
                        # Same log line becomes structured JSON:
                        {"instant":{"epochSecond":1741270981,"nanoOfSecond":123000000},
                         "thread":"vert.x-eventloop-0",
                         "level":"INFO",
                         "loggerName":"com.example.myservice.OrderService",
                         "message":"Order 42 placed",
                         "contextMap":{"traceId":"abc123","spanId":"def456"}}"""));

        content.add(codeBlockWithTitle("Common logging environment variables",
                """
                        # Switch to JSON for cloud log aggregators
                        CLOUD=true
                        
                        # Control the root log level (choose one)
                        LOG_LEVEL=INFO
                        GUICEDEE_LOG_LEVEL=DEBUG
                        
                        # Quick toggles
                        DEBUG=true
                        TRACE=true
                        
                        # Programmatic switch at runtime (no restart)
                        GuiceContext.setConsoleLayout(ConsoleLayoutOption.JSON);
                        GuiceContext.setDefaultLogLevel(Level.DEBUG);"""));

        var note = bodyText("The logging system is configured in the GuiceContext static initializer — before any Guice module loads. " +
                "When CLOUD=true, stdout receives DEBUG/INFO as JSON, stderr receives WARN/ERROR/FATAL as JSON, " +
                "and the rolling file appender is skipped entirely. This means your container only needs to capture stdout/stderr — " +
                "no log volume mounts, no sidecar log shippers.", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("Cloud-aware logging", "One env var switches to production-ready JSON",
                "CLOUD=true — that's it. Every log line becomes structured JSON for your aggregator. Locally, you get colorized output.",
                false, content);
    }

    // ── Plugin section ────────────────────────────────

    private WaCard<?> buildPluginSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        String[][] plugins = {
                {"REST (JAX-RS)", "@Path/@GET/@POST routes auto-registered on Vert.x Router via ClassGraph. Full parameter binding.", "Available"},
                {"WebSockets", "RFC 6455 with call-scoped connections, action-based routing, and group broadcasting.", "Available"},
                {"Persistence", "Hibernate Reactive 7 + Mutiny.SessionFactory. Multi-database, env-var driven.", "Available"},
                {"RabbitMQ", "Annotate connections, exchanges, queues, consumers, and publishers. Auto-recovery included.", "Available"},
                {"Health", "@Liveness, @Readiness, @Startup checks at /health as JSON.", "Available"},
                {"Metrics", "Dropwizard + MicroProfile. @Counted, @Timed, Prometheus endpoint.", "Available"},
                {"Telemetry", "OpenTelemetry tracing with @Trace, OTLP export, Log4j2 correlation.", "Available"},
                {"OpenAPI", "OpenAPI 3.1 spec at /openapi.json + /openapi.yaml from JAX-RS annotations.", "Available"},
                {"Swagger UI", "Browsable UI at /swagger/ with zero code. Just add the dependency.", "Available"},
                {"Config", "@ConfigProperty injection from env vars, system props, and properties files.", "Available"},
                {"Fault Tolerance", "@Retry, @CircuitBreaker, @Timeout, @Bulkhead via Guice AOP.", "Available"},
                {"Web Services", "SOAP/JAX-WS via Apache CXF. @WebService endpoints, WS-Security.", "Available"},
                {"CDI Bridge", "@ApplicationScoped, @RequestScoped, BeanManager — all mapped to Guice.", "Available"},
                {"Serial (Cerial)", "@Named port injection, auto-reconnect, idle monitoring, health reporting.", "Available"},
                {"MCP Server", "Model Context Protocol — tools, resources, prompts for AI agent integration.", "Available"},
        };

        for (String[] plugin : plugins)
        {
            grid.add(pluginCard(plugin[0], plugin[1], plugin[2]));
        }

        return buildSection("Module catalog", "Drop-in capabilities — add a dependency, get a feature",
                "Each integration ships as a micro-module with its own module-info.java. Zero side effects.",
                true, grid);
    }

    // ── Developer section ─────────────────────────────

    private WaCard<?> buildDeveloperSection()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("AI Rules repository",
                "Open guidance for AI-assisted development keeps patterns consistent across teams and LLMs.",
                "Use it to drive better outputs from AI coding tools."));

        grid.add(featureCard("End-to-end testable",
                "Test the entire stack with JUnit 5 + Jupiter. Health checks, REST endpoints, persistence — all verifiable in CI.",
                "In-memory telemetry exporters for tracing tests."));

        grid.add(featureCard("Code-first always",
                "No XML, no YAML, no JSON config. Your configuration is your code. " +
                        "Override at deploy time with environment variables.",
                "Configuration lives where you can test it."));

        grid.add(featureCard("Active community",
                "GitHub Issues and Discussions for support. PRs welcome. " +
                        "All modules are Apache 2.0 licensed.",
                "Open source with practical guardrails."));

        content.add(grid);

        content.add(codeBlockWithTitle("Full JPMS module declaration",
                """
                        module my.app {
                            requires com.guicedee.guicedinjection;
                            requires com.guicedee.vertx.web;
                            requires com.guicedee.rest;
                            requires com.guicedee.persistence;
                            requires com.guicedee.health;
                        
                            // Wire your Guice module + lifecycle hooks via SPI
                            provides com.guicedee.client.services.lifecycle.IGuiceModule
                                with my.app.AppModule;
                            provides com.guicedee.client.services.lifecycle.IGuicePostStartup
                                with my.app.AppStartup;
                        }"""));

        return buildSection("For developers", "Open source with practical guardrails",
                "Clear patterns, comprehensive testing, and transparent code give teams the controls they need.",
                false, content);
    }

    // ── Call to action ────────────────────────────────

    private WaCard<?> buildCallToActionSection()
    {
        var panel = new WaStack();
        panel.setGap(PageSize.Medium);

        panel.add(headingText("h2", "l", "Ready to build something?"));

        var intro = bodyText("Pick the modules you need, generate your project, and ship a fully reactive application. " +
                "The getting started guide will have you running in 5 minutes.", "m");
        intro.setWaColorText("quiet");
        panel.add(intro);

        var ctas = new WaCluster();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("Get started", "getting-started", Variant.Brand, Appearance.Filled));
        ctas.add(buildCta("End-to-end guide", "guides/end-to-end", Variant.Brand, Appearance.Outlined));
        ctas.add(buildCta("Browse modules", "modules", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("View on GitHub", "github", Variant.Neutral, Appearance.Outlined));
        panel.add(ctas);

        var section = new WaCard<>();
        section.setAppearance(Appearance.Filled);
        section.add(panel);
        return section;
    }

    // ── Card helpers ──────────────────────────────────

    private WaCard<?> heroCard(String title, String body)
    {
        return contentCard(title, body, null, Appearance.Outlined, "s", "s");
    }


    private WaCard<?> pluginCard(String title, String body, String status)
    {
        return contentCard(title, body, status, Appearance.Outlined, "m", "m");
    }
}
