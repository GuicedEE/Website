package com.guicedee.website.pages;

import com.guicedee.website.App;
import com.guicedee.website.catalog.ModuleCatalog;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.annotations.structures.NgField;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.details.WaDetails;
import com.jwebmp.webawesome.components.dialog.WaDialog;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.tooltip.TooltipPlacement;
import com.jwebmp.webawesome.components.tooltip.WaTooltip;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.plugins.markdown.Markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NgComponent("guicedee-capabilities")
@NgRoutable(path = "capabilities")
@NgComponentReference(App.class)
public class CapabilitiesPage extends WebsitePage<CapabilitiesPage> implements INgComponent<CapabilitiesPage>
{
    public CapabilitiesPage()
    {
        packCapabilities();
    }

    private void packCapabilities()
    {
        var layout = new WaStack<>();
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
    }

    private WaCard<?> buildIntro()
    {
        var content = new WaStack<>();
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

    private WaStack buildArchitectureSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(mermaidDiagramWithTitle("GuicedEE Architecture",
                """
                        graph LR
                            subgraph App["Your Application"]
                                APP["Your Business Logic"]
                            end
                            subgraph Protocol["Protocol Layers"]
                                REST["REST (JAX-RS)"]
                                WS["WebSockets"]
                                OPENAPI["OpenAPI"]
                            end
                            subgraph Messaging
                                RABBIT["RabbitMQ"]
                                KAFKA["Kafka"]
                                IBMMQ["IBM MQ"]
                            end
                            subgraph Storage
                                PERSIST["Persistence"]
                            end
                            subgraph Foundation
                                VERTXWEB["Vert.x Web"]
                                VERTXCORE["Vert.x Core"]
                                INJECT["GuicedEE Inject"]
                                BOM["BOM & JPMS"]
                            end
                            
                            APP --> Protocol
                            APP --> Messaging
                            APP --> Storage
                            
                            REST --> VERTXWEB
                            WS --> VERTXWEB
                            OPENAPI --> VERTXWEB
                            
                            RABBIT --> VERTXCORE
                            KAFKA --> VERTXCORE
                            IBMMQ --> INJECT
                            
                            PERSIST --> VERTXCORE
                            
                            VERTXWEB --> VERTXCORE
                            VERTXCORE --> INJECT
                            INJECT --> BOM
                            
                            style Foundation fill:#f9f,stroke:#333
                            style Messaging fill:#bbf,stroke:#333
                            style Protocol fill:#dfd,stroke:#333
                            style Storage fill:#ffd,stroke:#333"""));

        var explain = bodyText("Your application sits at the top. You pick the layers you need. " +
                "The inject layer and BOM are always present — everything else is opt-in.", "m");
        explain.setWaColorText("quiet");
        content.add(explain);

        return buildSection("Architecture", "Layered, composable, modular",
                "Every layer is a JPMS module. Build up your stack — never exclude down.", false, content);
    }

    private WaStack buildInjectionCapability()
    {
        var content = new WaStack<>();
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
                """
                        public class AppStartup
                            implements IGuicePostStartup<AppStartup> {
                        
                            @Inject
                            private SomeService service;
                        
                            @Override
                            public void postLoad() {
                                service.warmUpCaches();
                            }
                        
                            @Override
                            public Integer sortOrder() {
                                return 100; // lower = earlier
                            }
                        }"""));

        return buildSection("Injection & Lifecycle", "The foundation of everything",
                "com.guicedee:inject — ClassGraph scanning, Guice DI, and deterministic lifecycle hooks.",
                true, content);
    }

    private WaStack buildWebCapability()
    {
        var content = new WaStack<>();
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
                """
                        public class MyRoutes
                            implements VertxRouterConfigurator<MyRoutes> {
                        
                            @Override
                            public Router builder(Router router) {
                                router.get("/ping").handler(ctx ->
                                    ctx.response().end("pong"));
                                return router;
                            }
                        
                            @Override
                            public Integer sortOrder() { return 500; }
                        }"""));

        return buildSection("HTTP Server", "Reactive by nature",
                "com.guicedee:web — Vert.x 5 HTTP/HTTPS server with Router and BodyHandler.", false, content);
    }

    private WaStack buildRestCapability()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("Full-featured REST resource",
                """
                        @Path("/api/orders")
                        @Produces(MediaType.APPLICATION_JSON)
                        public class OrderResource {
                        
                            @Inject private OrderService orders;
                        
                            @GET
                            public List<Order> list(
                                    @QueryParam("status") String status,
                                    @QueryParam("limit") @DefaultValue("50") int limit) {
                                return orders.find(status, limit);
                            }
                        
                            @GET @Path("/{id}")
                            public Order get(@PathParam("id") Long id) {
                                return orders.findById(id);
                            }
                        
                            @POST @Consumes(MediaType.APPLICATION_JSON)
                            public Order create(Order order) {
                                return orders.save(order);
                            }
                        
                            @PUT @Path("/{id}")
                            @Consumes(MediaType.APPLICATION_JSON)
                            public Order update(@PathParam("id") Long id,
                                                Order order) {
                                return orders.update(id, order);
                            }
                        
                            @DELETE @Path("/{id}")
                            public void delete(@PathParam("id") Long id) {
                                orders.delete(id);
                            }
                        }"""));

        var note = bodyText("All HTTP methods, path parameters, query parameters, header parameters, " +
                "cookie parameters, form parameters, matrix parameters, and bean parameters are supported. " +
                "Jackson serialization is automatic.", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("REST (JAX-RS)", "Standard annotations, zero registration",
                "com.guicedee:rest — Jakarta REST adapter for Vert.x 5.", true, content);
    }

    private WaStack buildRestClientCapability()
    {
        var content = new WaStack<>();
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
                """
                        @Endpoint(url = "https://api.example.com/users/{userId}",
                                  method = "GET",
                                  security = @EndpointSecurity(
                                      value = SecurityType.Bearer,
                                      token = "${API_TOKEN}"),
                                  options = @EndpointOptions(
                                      connectTimeout = 5000, readTimeout = 10000))
                        @Named("get-user")
                        private RestClient<Void, UserResponse> userClient;
                        
                        // Call with path parameters:
                        Uni<UserResponse> user = userClient
                            .pathParam("userId", "123")
                            .send();"""));

        return buildSection("REST Client", "Typed, reactive, annotation-driven",
                "com.guicedee:rest-client — outbound HTTP calls with @Endpoint, @EndpointSecurity, and Uni<R>.",
                false, content);
    }

    private WaStack buildSecurityCapability()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("@AuthOptions",
                "Core annotation for ChainAuth (ANY/ALL), KeyStore, PEM keys, PRNG, and leeway. " +
                        "The foundation that all auth providers chain into.",
                null));

        grid.add(featureCard("@OAuth2Options",
                "OAuth2 and OpenID Connect with well-known providers (Google, Keycloak, Azure AD, GitHub, etc.). " +
                        "Authorization Code, Password, Client Credentials, and JWT flows.",
                null));

        grid.add(featureCard("@JwtAuthOptions",
                "JWT authentication with KeyStore, PEM, or JWK keys. HS256/RS256/ES256. " +
                        "Token generation, validation, issuer/audience checks, and JWTAuthorization or MicroProfileAuthorization.",
                null));

        grid.add(featureCard("@AbacOptions",
                "Attribute-Based Access Control with JSON policies. Match user attributes (eq, ne, has) " +
                        "and grant permissions. Custom Function<User, Boolean> matchers via IAbacPolicyProvider SPI.",
                null));

        grid.add(featureCard("@OtpAuthOptions",
                "One-Time Passwords — TOTP (Google Authenticator) and HOTP (counter-based). " +
                        "IOtpAuthenticatorService SPI for storage callbacks. QR code URI generation.",
                null));

        grid.add(featureCard("@PropertyFileAuthOptions",
                "Apache Shiro-format properties file with users, passwords, roles, and permissions. " +
                        "Both authentication and authorization from a single file.",
                null));

        grid.add(featureCard("@LdapAuthOptions",
                "LDAP authentication with configurable query template (uid={0},ou=users,dc=...), " +
                        "authentication mechanism, and referral behavior.",
                null));

        grid.add(featureCard("@HtpasswdAuthOptions",
                "Apache htpasswd file authentication. Plain text and hashed passwords. " +
                        "File loaded once at startup — authentication only, no authorization.",
                null));

        grid.add(featureCard("@HtdigestAuthOptions",
                "Apache htdigest file for HTTP Digest authentication. " +
                        "File loaded once at startup — authentication only.",
                null));

        grid.add(featureCard("IGuicedAuthenticationProvider SPI",
                "Register custom authentication providers via ServiceLoader. " +
                        "JWT, OAuth2, Basic, UsernamePassword — implement and register in module-info.java.",
                null));

        grid.add(featureCard("IGuicedAuthorizationProvider SPI",
                "Register authorization providers via ServiceLoader. " +
                        "Role-based, permission-based, wildcard, and logical combinations (And/Or/Not).",
                null));

        grid.add(featureCard("@RolesAllowed / @PermitAll / @DenyAll",
                "Standard Jakarta annotations at class or method level. " +
                        "SecurityHandler checks roles against the authenticated Vert.x User.",
                null));

        content.add(grid);

        content.add(codeBlockWithTitle("JWT authentication on package-info.java",
                """
                        @JwtAuthOptions(
                            keystorePath = "keystore.jceks",
                            keystoreType = "jceks",
                            keystorePassword = "${JWT_KEYSTORE_PASSWORD}",
                            algorithm = "RS256",
                            issuer = "my-corp.com",
                            audience = {"my-service"},
                            permissionsClaimKey = "realm_access/roles",
                            authorizationType = JwtAuthorizationType.JWT
                        )
                        package com.example.auth;"""));

        content.add(codeBlockWithTitle("OAuth2 with OIDC Discovery",
                """
                        @OAuth2Options(
                            flow = OAuth2Options.FlowType.AUTH_CODE,
                            clientId = "${OAUTH2_CLIENT_ID}",
                            clientSecret = "${OAUTH2_CLIENT_SECRET}",
                            discoveryUrl = "${OAUTH2_DISCOVERY_URL}",
                            callbackPath = "/callback"
                        )
                        package com.example.auth;"""));

        content.add(codeBlockWithTitle("ABAC policy-based authorization",
                """
                        @AbacOptions(
                            policyFiles = {"policies/admin.json", "policies/readonly.json"}
                        )
                        package com.example.auth;
                        
                        // admin.json:
                        // {"name":"Admin DELETE","attributes":{"/principal/role":{"eq":"admin"}},
                        //  "authorizations":[{"type":"wildcard","permission":"admin:*"}]}"""));

        content.add(codeBlockWithTitle("TOTP (Google Authenticator) setup",
                """
                        @OtpAuthOptions(type = OtpType.TOTP, passwordLength = 6, period = 30)
                        package com.example.auth;
                        
                        // Implement IOtpAuthenticatorService for storage:
                        // provides IOtpAuthenticatorService with MyOtpService;
                        
                        @Inject private TotpAuth totpAuth;
                        OtpKey key = OtpKeyGenerator.create().generate();
                        String qrUri = totpAuth.generateUri(key, "MyApp", "user@eg.com");"""));

        content.add(codeBlockWithTitle("Jakarta security annotations on REST endpoints",
                """
                        @Path("/api/admin")
                        @RolesAllowed({"admin", "super-admin"})
                        public class AdminResource {
                        
                            @GET @Path("/users")
                            public Uni<List<User>> listUsers() {
                                return userService.findAll();
                            }
                        
                            @DELETE @Path("/users/{id}")
                            @RolesAllowed("super-admin")  // overrides class
                            public Uni<Void> deleteUser(@PathParam("id") String id) {
                                return userService.delete(id);
                            }
                        
                            @GET @Path("/health")
                            @PermitAll  // no auth needed
                            public String health() { return "OK"; }
                        }"""));

        return buildSection("Security & Authentication", "8 auth providers — all annotation-driven",
                "OAuth2, JWT, ABAC, OTP, Property File, LDAP, htpasswd, htdigest — " +
                        "opt-in via annotations, override via env vars, inject via Guice.",
                true, content);
    }

    private WaStack buildVerticleCapability()
    {
        var content = new WaStack<>();
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
                "Rest, RabbitMQ, Kafka, IBM MQ, Web, Telemetry, Persistence, Sockets, OpenAPI, Swagger, " +
                        "WebServices, Cerial (serial ports) — each maps to its module package.",
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
                """
                        // package-info.java
                        @Verticle(value = "payments-pool",
                                  workerPoolSize = 16,
                                  maxWorkerExecuteTime = 30,
                                  maxWorkerExecuteTimeUnit = TimeUnit.SECONDS,
                                  capabilities = {Capabilities.Rest,
                                                  Capabilities.Persistence})
                        package com.example.payments;
                        
                        // Any @Path resource in com.example.payments gets
                        // its own worker pool and sub-router — isolated
                        // from other API groups."""));

        return buildSection("Verticles & Deployment", "Isolated worker pools, SPI-driven config",
                "@Verticle packages with per-pool threading, 3 SPI hooks, and environment-driven server config.",
                false, content);
    }

    private WaStack buildPersistenceCapability()
    {
        var content = new WaStack<>();
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
                """
                        @Inject
                        private Mutiny.SessionFactory sf;
                        
                        public Uni<List<Product>> findByCategory(String cat) {
                            return sf.withSession(session ->
                                session.createQuery(
                                    "FROM Product WHERE category = :cat",
                                    Product.class)
                                .setParameter("cat", cat)
                                .getResultList());
                        }"""));

        return buildSection("Persistence", "Reactive JPA with zero boilerplate",
                "com.guicedee:persistence — Hibernate Reactive on Vert.x SQL Client.", false, content);
    }

    private WaStack buildWebSocketCapability()
    {
        var content = new WaStack<>();
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

    private WaStack buildMessagingCapability()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Large);

        // ── RabbitMQ ──
        var rabbitSection = new WaStack<>();
        rabbitSection.setGap(PageSize.Medium);
        rabbitSection.add(headingText("h3", "m", "RabbitMQ"));

        rabbitSection.add(codeBlockWithTitle("RabbitMQ — annotation-driven messaging",
                """
                        @RabbitConnectionOptions(
                            host = "localhost", port = 5672,
                            user = "guest", password = "guest"
                        )
                        public class OrderEvents {
                        
                            @RabbitExchange("orders")
                            @RabbitQueue("order.created")
                            public void onOrderCreated(Order order) {
                                // Process the order event
                            }
                        }"""));

        var rabbitFeatures = new WaGrid<>();
        rabbitFeatures.setMinColumnSize("14rem");
        rabbitFeatures.setGap(PageSize.Small);
        rabbitFeatures.add(featureCard("Annotation-driven", "Connections, exchanges, queues, consumers all via annotations.", null));
        rabbitFeatures.add(featureCard("Auto-recovery", "Connection and channel recovery built in.", null));
        rabbitFeatures.add(featureCard("Vert.x native", "Uses Vert.x RabbitMQ client for non-blocking IO.", null));
        rabbitFeatures.add(featureCard("Env overrides", "Override host, port, credentials via environment variables.", null));
        rabbitSection.add(rabbitFeatures);
        content.add(rabbitSection);

        // ── Kafka ──
        var kafkaSection = new WaStack<>();
        kafkaSection.setGap(PageSize.Medium);
        kafkaSection.add(headingText("h3", "m", "Apache Kafka"));

        var kafkaIntro = bodyTextHtml("Annotation-driven " + brandCode("Apache Kafka") + " integration built on the " + brandCode("Vert.x Kafka Client") + ". " +
                "Declare connections, topics, consumers, and publishers with annotations — everything is discovered at startup via ClassGraph, wired through Guice, and managed by Vert.x.", "m");
        kafkaIntro.setWaColorText("quiet");
        kafkaSection.add(kafkaIntro);

        kafkaSection.add(codeBlockWithTitle("Kafka — define a connection, consumer, and publisher",
                """
                        // package-info.java — declare connection and topic
                        @KafkaConnectionOptions(
                            value = "my-connection",
                            bootstrapServers = "localhost:9092",
                            groupId = "my-group"
                        )
                        @KafkaTopicCreate(value = "order-events",
                            partitions = 3, replicationFactor = 1)
                        package com.example.messaging;
                        
                        // Consumer
                        @KafkaTopicDefinition("order-events")
                        public class OrderConsumer
                                implements KafkaTopicConsumer<String, String> {
                            @Override
                            public void consume(KafkaConsumerRecord<String, String> record) {
                                System.out.println("Received: " + record.value());
                            }
                        }
                        
                        // Publisher injection
                        @Inject @Named("order-events")
                        private KafkaTopicPublisher orderPublisher;"""));

        var kafkaFeatures = new WaGrid<>();
        kafkaFeatures.setMinColumnSize("14rem");
        kafkaFeatures.setGap(PageSize.Small);
        kafkaFeatures.add(featureCard("Annotation-driven", "@KafkaConnectionOptions, @KafkaTopicDefinition, @KafkaTopicCreate — declare the entire topology.", null));
        kafkaFeatures.add(featureCard("Auto-discovery", "KafkaPreStartup scans the classpath via ClassGraph to find all annotated declarations.", null));
        kafkaFeatures.add(featureCard("Guice-managed", "Consumers bound as singletons. KafkaTopicPublisher instances injectable by @Named(\"topic-name\").", null));
        kafkaFeatures.add(featureCard("Admin client", "Topics created at startup via KafkaAdminClient using @KafkaTopicCreate (repeatable).", null));
        kafkaFeatures.add(featureCard("Worker threads", "@KafkaTopicOptions(worker = true) offloads message processing to the Vert.x worker pool.", null));
        kafkaFeatures.add(featureCard("Call-scoped handling", "Each message is processed within a GuicedEE CallScoper transaction boundary.", null));
        kafkaFeatures.add(featureCard("Env overrides", "KAFKA_{NAME}_{PROPERTY} for name-specific, KAFKA_{PROPERTY} for global fallback.", null));
        kafkaFeatures.add(featureCard("Graceful shutdown", "All consumers, producers, and admin clients are closed on application shutdown.", null));
        kafkaSection.add(kafkaFeatures);
        content.add(kafkaSection);

        // ── IBM MQ ──
        var ibmmqSection = new WaStack<>();
        ibmmqSection.setGap(PageSize.Medium);
        ibmmqSection.add(headingText("h3", "m", "IBM MQ"));

        var ibmmqIntro = bodyTextHtml("Annotation-driven " + brandCode("IBM MQ") + " integration using the IBM MQ JMS client. " +
                "Define connections, queues, consumers, and publishers with annotations — auto-discovered and wired through Guice.", "m");
        ibmmqIntro.setWaColorText("quiet");
        ibmmqSection.add(ibmmqIntro);

        ibmmqSection.add(codeBlockWithTitle("IBM MQ — connection, consumer, and publisher",
                """
                        // package-info.java — declare connection
                        @IBMMQConnectionOptions(
                            value = "my-connection",
                            host = "localhost",
                            port = 1414,
                            queueManager = "QM1",
                            channel = "DEV.APP.SVRCONN"
                        )
                        package com.example.messaging;
                        
                        // Consumer
                        @IBMMQQueueDefinition("DEV.QUEUE.1")
                        public class OrderConsumer implements IBMMQConsumer {
                            @Override
                            public void consume(Message message) {
                                // process message
                            }
                        }
                        
                        // Publisher injection
                        @Inject @Named("DEV.QUEUE.1")
                        private IBMMQPublisher publisher;"""));

        var ibmmqFeatures = new WaGrid<>();
        ibmmqFeatures.setMinColumnSize("14rem");
        ibmmqFeatures.setGap(PageSize.Small);
        ibmmqFeatures.add(featureCard("Annotation-driven", "@IBMMQConnectionOptions and @IBMMQQueueDefinition — declare connections and queues.", null));
        ibmmqFeatures.add(featureCard("Auto-discovery", "Classpath scanning via ClassGraph finds all annotated connection and queue declarations.", null));
        ibmmqFeatures.add(featureCard("Guice-managed", "Consumers and publishers are Guice-managed singletons, injectable by @Named.", null));
        ibmmqFeatures.add(featureCard("Env overrides", "IBMMQ_{NAME}_{PROPERTY} for name-specific, IBMMQ_{PROPERTY} for global fallback.", null));
        ibmmqSection.add(ibmmqFeatures);
        content.add(ibmmqSection);

        return buildSection("Messaging", "Event-driven architecture — RabbitMQ, Kafka, and IBM MQ",
                "com.guicedee:rabbitmq · com.guicedee:kafka · com.guicedee:ibmmq — three messaging integrations, all annotation-driven.", false, content);
    }

    private WaStack buildObservabilityCapability()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("18rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCardWithCoordinate("Health — /health",
                "@Liveness, @Readiness, @Startup checks. Configurable paths. 2-second timeout per check. " +
                        "Kubernetes probe-ready.",
                "com.guicedee:health"));

        grid.add(featureCardWithCoordinate("Metrics — Prometheus",
                "@Counted, @Timed, @Gauge annotations. Dropwizard Metrics backend. " +
                        "Prometheus scrape endpoint + Graphite reporting.",
                "com.guicedee:metrics"));

        grid.add(featureCardWithCoordinate("Telemetry — OTLP",
                "@Trace and @SpanAttribute for distributed tracing. OTLP export to Tempo, Jaeger. " +
                        "Uni-aware span completion. Log4j2 correlation.",
                "com.guicedee:guiced-telemetry"));

        grid.add(featureCardWithCoordinate("OpenAPI — /openapi.json",
                "OpenAPI 3.1 spec generated from JAX-RS annotations. " +
                        "Swagger UI at /swagger/ with zero code.",
                "com.guicedee:openapi"));

        content.add(grid);

        return buildSection("Observability", "See everything, measure everything",
                "Health, Metrics, Telemetry, and OpenAPI — all discoverable and auto-registered.", true, content);
    }

    private WaStack buildLoggingCapability()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("CLOUD=true → JSON",
                "Set CLOUD=true and all console appenders switch to compact JSON layout. " +
                        "No log4j2.xml required — but if you already have one, it still works. " +
                        "This is a programmatic alternative configured in the GuiceContext static initializer.",
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
                "CLOUD=true switches to JSON. Locally you get ANSI highlighting. Standard log4j2.xml and Log4j2 APIs still work — this is simply a more programmatic approach.",
                false, content);
    }

    private WaStack buildConfigCapability()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(mermaidDiagramWithTitle("Three-tier configuration model",
                """
                        graph LR
                            A["1. Annotation defaults<br/>(in code)"] --> B["2. Environment variables<br/>(deploy-time override)"]
                            B --> C["3. SPI hooks<br/>(programmatic control)"]
                            style A fill:#4a9eff,color:#fff
                            style B fill:#f5a623,color:#fff
                            style C fill:#7ed321,color:#fff"""));

        content.add(codeBlockWithTitle("Example: Telemetry configuration tiers",
                """
                        // Tier 1: Annotation defaults (in code)
                        @TelemetryOptions(
                            serviceName = "my-service",
                            otlpEndpoint = "http://localhost:4318"
                        )
                        
                        // Tier 2: Environment override (no code change)
                        // TELEMETRY_SERVICE_NAME=production-service
                        // TELEMETRY_OTLP_ENDPOINT=http://tempo:4318
                        
                        // Tier 3: SPI hook (programmatic control)
                        public class MyTelemetryConfig
                            implements GuiceTelemetryRegistration {
                            // Full programmatic customization
                        }"""));

        var note = bodyText("This three-tier pattern applies to every GuicedEE module: " +
                "HTTP server, health, metrics, telemetry, persistence, RabbitMQ, Kafka, IBM MQ, and more. " +
                "You only need MicroProfile Config properties files if you want " +
                "@ConfigProperty injection — everything else works with annotations and env vars.", "m");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("Configuration", "Annotations → Env vars → SPI hooks",
                "com.guicedee.microprofile:config — MicroProfile Config via SmallRye.", false, content);
    }

    private WaStack buildFaultToleranceCapability()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("Resilience patterns via annotations",
                """
                        @Retry(maxRetries = 3, delay = 200)
                        @CircuitBreaker(requestVolumeThreshold = 20,
                            failureRatio = 0.5, delay = 5000)
                        @Timeout(value = 2000)
                        public Uni<PaymentResult> processPayment(
                                PaymentRequest request) {
                            return paymentGateway.charge(request);
                        }
                        
                        @Fallback(fallbackMethod = "cachedPrice")
                        public Uni<Price> getPrice(String productId) {
                            return priceService.lookup(productId);
                        }
                        
                        public Uni<Price> cachedPrice(String productId) {
                            return Uni.createFrom().item(priceCache.get(productId));
                        }"""));

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

    private WaStack buildJLinkCapability()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCard("JLink custom runtimes",
                "Every module is JPMS Level 3. Use the moditect-maven-plugin to build a trimmed " +
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

        content.add(mavenGradleCodeBlock("Moditect JLink plugin configuration",
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
                        </plugin>""",
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
                        }"""
        ));

        content.add(codeBlockWithTitle("Dockerfile — distroless container",
                """
                        FROM gcr.io/distroless/base-nossl-debian12
                        COPY target/jlink-image /app/jrt
                        ENV CLOUD=true
                        ENTRYPOINT ["/app/jrt/bin/myservice"]"""));

        content.add(codeBlockWithTitle("Build, push, deploy",
                """
                        # Maven
                        mvn clean package
                        
                        # — or Gradle —
                        gradle build
                        
                        # Then containerize and ship
                        docker build -t my-service:latest .
                        docker push registry.example.com/my-service:latest"""));

        var jlinkNote = bodyTextHtml("<strong>Tip:</strong> You can also run " + brandCode("jlink") + " directly: " +
                brandCode("jlink --module-path target/libs --add-modules my.service --output target/jlink-image --strip-debug --no-header-files --no-man-pages") +
                ". The moditect plugin simply wraps this into your build lifecycle.", "s");
        jlinkNote.setWaColorText("quiet");
        content.add(jlinkNote);

        var note = bodyText("Every module declares 'provides' and 'uses' in module-info.java, " +
                "so JLink resolves the full SPI graph. No reflection hacks, no runtime classpath surprises. " +
                "What you declare is exactly what ships.", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("JLink & Deployment", "Custom runtimes in minimal containers",
                "JPMS Level 3 enables JLink, JPackage, and distroless Docker — ship your app as a ~40 MB image.",
                false, content);
    }

    private static final Map<String, String> EXAMPLE_PATHS = Map.ofEntries(
            Map.entry("inject", "Inject/Basic"),
            Map.entry("vertx", "Vertx/Basic"),
            Map.entry("web", "Web/Basic"),
            Map.entry("rest", "Rest/Basic"),
            Map.entry("rest-client", "RestClient/Basic"),
            Map.entry("websockets", "WebSockets/Basic"),
            Map.entry("persistence", "Persistence/Basic"),
            Map.entry("rabbitmq", "RabbitMQ/Basic"),
            Map.entry("kafka", "Kafka/Basic"),
            Map.entry("ibmmq", "IBMMQ/Basic"),
            Map.entry("openapi", "OpenAPI/Server"),
            Map.entry("swagger-ui", "OpenAPI/SwaggerUI"),
            Map.entry("webservices", "WebServices/Basic"),
            Map.entry("cerial", "Cerial/Basic"),
            Map.entry("cdi", "CDI/Basic"),
            Map.entry("config", "Config/Basic"),
            Map.entry("telemetry", "Telemetry/Basic"),
            Map.entry("jwt", "JWT/Basic"),
            Map.entry("health", "Health/Basic"),
            Map.entry("metrics", "Metrics/Basic"),
            Map.entry("hazelcast", "Hazelcast/Client"),
            Map.entry("mailclient", "MailClient"),
            Map.entry("fault-tolerance", "FaultTolerance/Basic")
    );

    private WaStack buildModuleCatalogSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Every GuicedEE module in the catalog, with coordinates and descriptions.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("24rem");
        grid.setGap(PageSize.Medium);

        ModuleCatalog.getModules().forEach(module ->
        {
            var details = new WaDetails<>();
            details.setSummary(module.getName());

            var detailContent = new WaStack<>();
            detailContent.setGap(PageSize.Small);

            var description = bodyText(module.getDescription(), "s");
            description.setWaColorText("quiet");
            detailContent.add(description);

            detailContent.add(coordinateBlock(module.getGroupId() + ":" + module.getArtifactId() + ":" + module.getVersion()));

            // Action links row
            var actions = new WaCluster<>();
            actions.setGap(PageSize.ExtraSmall);

            // View Documentation button — opens readme dialog
            var docsBtn = new WaButton<>(escapeAngular("View Docs"), Variant.Brand);
            docsBtn.setAppearance(Appearance.Outlined);
            docsBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
            docsBtn.addAttribute("(click)", "openReadme('" + module.getId() + "', '" + escapeAngular(module.getName()) + "')");
            actions.add(docsBtn);

            // Example link with tooltip
            var examplePath = EXAMPLE_PATHS.get(module.getId());
            if (examplePath != null)
            {
                actions.add(exampleHeaderIcon(examplePath));
            }

            detailContent.add(actions);

            details.add(detailContent);
            grid.add(details);
        });

        content.add(grid);

        // Module README dialog
        buildReadmeDialog();

        return buildSection("Full module catalog", "All " + ModuleCatalog.getModules().size() + " modules at a glance",
                "Expandable entries with coordinates, documentation, and example links.",
                false, content);
    }

    private void buildReadmeDialog()
    {
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

        var headerActions = new WaCluster<>();
        headerActions.setGap(PageSize.ExtraSmall);

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
        headerActions.add(repoBtn);
        headerActions.add(new WaTooltip<>(repoBtn).setText("View Repository").setPlacement(TooltipPlacement.Top));

        headerCluster.add(headerActions);
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
                    'jwt': 'https://raw.githubusercontent.com/GuicedEE/JWT/refs/heads/master/README.md'
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
