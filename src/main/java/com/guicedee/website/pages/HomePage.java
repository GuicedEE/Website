package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.tooltip.TooltipPlacement;
import com.jwebmp.webawesome.components.tooltip.WaTooltip;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.dialog.WaDialog;
import com.jwebmp.webawesome.components.text.WaText;
import com.jwebmp.plugins.markdown.Markdown;

import java.util.ArrayList;
import java.util.List;

@NgComponent("guicedee-home")
@NgRoutable(path = "home", isDefault = true)
public class HomePage extends WebsitePage<HomePage> implements INgComponent<HomePage>
{
    public HomePage()
    {
        removeClass("website-content");
        buildLandingPage();
    }

    @Override
    public List<String> afterViewInit()
    {
        var a = new ArrayList<>(super.afterViewInit());
        a.add("""
                const script = document.createElement('script');
                script.src = 'https://plugins.jetbrains.com/assets/scripts/mp-widget.js';
                script.onload = () => {
                    (window as any).MarketplaceWidget?.setupMarketplaceWidget('card', 31112, '#jetbrains-plugin-widget');
                };
                document.body.appendChild(script);""");
        return a;
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
                    'rest': 'https://raw.githubusercontent.com/GuicedEE/RestServices/refs/heads/master/README.md',
                    'websockets': 'https://raw.githubusercontent.com/GuicedEE/GuicedVertxSockets/refs/heads/master/README.md',
                    'persistence': 'https://raw.githubusercontent.com/GuicedEE/GuicedVertxPersistence/refs/heads/master/README.md',
                    'rabbitmq': 'https://raw.githubusercontent.com/GuicedEE/GuicedRabbit/refs/heads/master/README.md',
                    'kafka': 'https://raw.githubusercontent.com/GuicedEE/GuicedKafka/refs/heads/master/README.md',
                    'ibmmq': 'https://raw.githubusercontent.com/GuicedEE/GuicedIBMMQ/refs/heads/master/README.md',
                    'health': 'https://raw.githubusercontent.com/GuicedEE/Health/refs/heads/master/README.md',
                    'metrics': 'https://raw.githubusercontent.com/GuicedEE/Metrics/refs/heads/master/README.md',
                    'telemetry': 'https://raw.githubusercontent.com/GuicedEE/GuicedTelemetry/refs/heads/master/README.md',
                    'openapi': 'https://raw.githubusercontent.com/GuicedEE/OpenAPI/refs/heads/master/README.md',
                    'swagger-ui': 'https://raw.githubusercontent.com/GuicedEE/SwaggerUI/refs/heads/master/README.md',
                    'config': 'https://raw.githubusercontent.com/GuicedEE/microprofile-config/refs/heads/master/README.md',
                    'fault-tolerance': 'https://raw.githubusercontent.com/GuicedEE/FaultTolerance/refs/heads/master/README.md',
                    'webservices': 'https://raw.githubusercontent.com/GedMarc/Guiced-WebServices/refs/heads/master/README.md',
                    'cdi': 'https://raw.githubusercontent.com/GuicedEE/GuicedCDI/refs/heads/master/README.md',
                    'cerial': 'https://raw.githubusercontent.com/GedMarc/GuicedCerial/refs/heads/master/README.md',
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
                    this.readmeDialogOpen = true;
                    
                    const match = this.readmeSrc.match(/https:\\/\\/raw\\.githubusercontent\\.com\\/([^\\/]+)\\/([^\\/]+)/);
                    if (match) {
                        this.currentRepoUrl = `https://github.com/${match[1]}/${match[2]}`;
                    } else {
                        this.currentRepoUrl = 'https://github.com/GuicedEE';
                    }
                }
                """);
        return m;
    }

    private void buildLandingPage()
    {
        // page size handled by WebsitePage base class

        var layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildHero());
        layout.add(buildThreeLinesPitch());
        layout.add(buildFoundationSection());
        layout.add(buildModularBuildSection());
        layout.add(buildPluginSection());
        layout.add(buildZeroConfigSection());
        layout.add(buildCodeShowcaseSection());
        layout.add(buildRestServicesSection());
        layout.add(buildSecuritySection());
        layout.add(buildVertxDeploymentSection());
        layout.add(buildMicroProfileSection());
        layout.add(buildJLinkShippingSection());
        layout.add(buildCloudAwareLoggingSection());
        layout.add(buildDeveloperSection());
        layout.add(buildCallToActionSection());

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

    // ── Hero ──────────────────────────────────────────

    private WaStack buildHero()
    {
        var hero = new WaStack<>();
        hero.setGap(PageSize.Large);
        hero.setID("hero");
        hero.addClass("hero-banner");

        // Eyebrow
        var eyebrow = captionText("MODULAR ENTERPRISE JAVA");
        eyebrow.addClass("hero-eyebrow");
        hero.add(eyebrow);

        // Main heading
        var heading = headingText("h1", "xl", "E2E Modular enterprise Java, reactive by nature.");
        heading.addClass("hero-heading");
        hero.add(heading);

        // Subtitle
        var subtitle = bodyTextHtml("Build reactive microservices and " + brandCode("moduliths") + " with " + brandCode("Google Guice") + ", " + brandCode("Vert.x 5") + " — " +
                "all on the Java Module System. Zero XML. Zero boilerplate. Just annotate and go.", "l");
        subtitle.setWaColorText("quiet");
        subtitle.addClass("hero-subtitle");
        hero.add(subtitle);

        var tags = new WaCluster();
        tags.setGap(PageSize.Small);
        tags.addClass("hero-tags");
        tags.add(buildTag("GuicedEE", Variant.Brand));
        tags.add(buildTag("Vert.x 5", Variant.Success));
        tags.add(buildTag("Java 25+", Variant.Neutral));
        tags.add(buildTag("JPMS Level 3", Variant.Neutral));
        tags.add(buildTag("Zero Config", Variant.Warning));
        tags.add(buildTag("MicroProfile", Variant.Success));
        hero.add(tags);

        var ctas = new WaCluster();
        ctas.setGap(PageSize.Small);
        ctas.addClass("hero-ctas");
        ctas.add(buildCta("Get Started", "/getting-started", Variant.Neutral, Appearance.Outlined));

        var pluginBtn = new WaButton<>(escapeAngular("IntelliJ Plugin"), Variant.Neutral);
        pluginBtn.setAppearance(Appearance.Outlined);
        pluginBtn.setAsLink("https://plugins.jetbrains.com/plugin/31112-guiced", "_blank", null);
        ctas.add(pluginBtn);

        ctas.add(buildCta("End-to-end Guide", "/guides/end-to-end", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("Browse Modules", "/modules", Variant.Neutral, Appearance.Outlined));
        hero.add(ctas);

        return hero;
    }

    // ── Three-line pitch ─────────────────────────────

    private WaStack buildThreeLinesPitch()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("1. Add a dependency",
                "Import the BOM, pick the modules you need, and your build tool handles the rest. Maven, Gradle, or any Java compiler — no plugin zoo, no starter generators.",
                "One BOM, zero surprises."));

        grid.add(featureCardHtml("2. Write your code",
                "Use standard annotations — " + brandCode("@Path") + ", " + brandCode("@Inject") + ", " + brandCode("@Liveness") + ", " + brandCode("@ConfigProperty") + ". " +
                        "GuicedEE discovers everything via " + brandCode("ClassGraph") + " and wires it through " + brandCode("Guice") + ".",
                "Standards you already know."));

        grid.add(featureCardHtml("3. Run it",
                "Call one line and your HTTP server, REST routes, WebSocket handlers, " +
                        "and persistence units all light up automatically.",
                "One line to launch."));

        return buildSection("How it works", "Three steps to modular applications",
                "No code generators, no runtime reflection hacks, no XML plumbing.",
                true, grid);
    }

    // ── Zero-configuration section ────────────────────

    private WaStack buildZeroConfigSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var intro = bodyText("Every GuicedEE module follows the same philosophy: sensible defaults out of the box, " +
                "annotations for customization, and environment variables for deployment overrides. " +
                "You'll never touch an XML configuration file (unless you want MicroProfile Config properties files).", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCardHtml("No persistence.xml?",
                "Configure your database with a " + brandCode("DatabaseModule") + ". " +
                        "GuicedEE creates the persistence unit from supplied properties, merged with " + brandCode("persistence.xml") + " if provided.",
                "Properties + persistence.xml = merged configuration."));

        grid.add(featureCardHtml("No web.xml ever",
                "The " + brandCode("Vert.x") + " HTTP server starts automatically when you add the 'web' module. " +
                        "Port, TLS, body limits — all controlled via environment variables or SPI hooks.",
                "HTTP_PORT=8080, done."));

        grid.add(featureCardHtml("No beans.xml",
                brandCode("Guice") + " doesn't need one. GuicedEE discovers your modules, bindings, and lifecycle hooks via JPMS " + brandCode("ServiceLoader") + " and " + brandCode("ClassGraph") + ". " +
                        "Since " + brandCode("Guice") + " supports JIT (Just-In-Time) binding, you can often skip explicit registration altogether.",
                "SPI is the configuration."));

        grid.add(featureCardHtml("No application.yml",
                "Keep it simple and cozy—no more jumping between " + brandCode("application.yml") + " and " + brandCode("cloud-config.yml") + ". " +
                        "Use " + brandCode("@ConfigProperty") + " from MicroProfile Config for external properties, and override anything with env vars at deploy time.",
                "Unified configuration, zero duplication."));

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

    private WaStack buildCodeShowcaseSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Large);

        // REST example
        var restStack = new WaStack<>();
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
        var restNote = bodyTextHtml("Routes are discovered by " + brandCode("ClassGraph") + " at startup and registered on the " + brandCode("Vert.x Router") + ". " +
                "No registration code needed — just annotate and ship. Common JAX-RS syntax for simplicity", "s");
        restNote.setWaColorText("quiet");
        restStack.add(restNote);
        content.add(restStack);

        content.add(divider());

        // Health check example
        var healthStack = new WaStack<>();
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
        var wsStack = new WaStack<>();
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
        var telemetryStack = new WaStack<>();
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

    private WaStack buildRestServicesSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Large);

        var intro = bodyTextHtml("GuicedEE's REST module adapts standard Jakarta REST (JAX-RS) annotations to the " + brandCode("Vert.x 5 Router") + ". " +
                "Routes are discovered at startup by " + brandCode("ClassGraph") + ", resource instances come from the " + brandCode("Guice") + " injector, " +
                "and methods can return plain values, " + brandCode("Uni&lt;T&gt;") + ", or " + brandCode("Future&lt;T&gt;") + ". CORS, security, and exception mapping all work " +
                "via annotations and SPI — no " + brandCode("web.xml") + ", no " + brandCode("Application") + " subclass, no registration code.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        // REST features grid
        var features = new WaGrid<>();
        features.setMinColumnSize("14rem");
        features.setGap(PageSize.Small);

        features.add(featureCardHtml("Zero-config routes",
                brandCode("@Path") + ", " + brandCode("@GET") + ", " + brandCode("@POST") + ", " + brandCode("@PUT") + ", " + brandCode("@DELETE") + ", " + brandCode("@PATCH") + " — " + brandCode("OperationRegistry") + " scans " + brandCode("ClassGraph") + " results " +
                        "and maps them to " + brandCode("Vert.x") + " routes at startup. No registration code.",
                null));

        features.add(featureCardHtml("Full parameter binding",
                brandCode("@PathParam") + ", " + brandCode("@QueryParam") + ", " + brandCode("@HeaderParam") + ", " + brandCode("@CookieParam") + ", " + brandCode("@FormParam") + ", " + brandCode("@MatrixParam") + ", " + brandCode("@BeanParam") + " — " +
                        "all Jakarta REST parameter annotations are supported.",
                null));

        features.add(featureCardHtml("Reactive returns",
                "Return " + brandCode("Uni&lt;T&gt;") + ", " + brandCode("Future&lt;T&gt;") + ", or plain values. Reactive types run on the event loop; " +
                        "blocking methods are dispatched to a worker pool automatically.",
                null));

        features.add(featureCardHtml("@Cors annotation",
                "Apply CORS at class or method level. Configures allowed origins, methods, headers, " +
                        "credentials, and max age. Override any value with " + brandCode("REST_CORS_*") + " env vars.",
                null));

        features.add(featureCardHtml("RestInterceptor SPI",
                "Hook into request start/end for logging, metrics, or cross-cutting concerns. " +
                        "Implement " + brandCode("onStart()") + " and " + brandCode("onEnd()") + " — both return " + brandCode("Future&lt;Boolean&gt;") + ".",
                null));

        features.add(featureCardHtml("ExceptionMapper",
                "Standard " + brandCode("ExceptionMapper") + " SPI with cause-chain traversal. " +
                        "Map exceptions to HTTP status codes and error response bodies.",
                null));

        features.add(featureCardHtml("@Verticle worker pools",
                "Resources in " + brandCode("@Verticle") + "-annotated packages automatically use their named worker pool. " +
                        "Isolate API groups with different threading and pool sizes.",
                null));

        features.add(featureCardHtml("JAX-RS Response",
                "Return " + brandCode("Response") + " for full control over status codes, " +
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

        var clientIntro = new WaStack<>();
        clientIntro.setGap(PageSize.Small);
        clientIntro.add(headingText("h3", "m", "REST Client — annotation-driven outbound calls"));
        var clientDesc = bodyTextHtml("Need to call external APIs? Declare an " + brandCode("@Endpoint") + " on a " + brandCode("RestClient&lt;Send, Receive&gt;") + " field, " +
                "add " + brandCode("@Named") + ", and inject. URL, method, authentication, timeouts, and connection options are all annotation-driven. " +
                "Every call returns " + brandCode("Uni&lt;Receive&gt;") + " for fully reactive composition.", "s");
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

    private WaStack buildSecuritySection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Large);

        var intro = bodyTextHtml("Security in GuicedEE uses standard Jakarta security annotations with pluggable " + brandCode("Vert.x") + " " +
                "authentication and authorization handlers. No " + brandCode("security.xml") + ", no realm configuration files — " +
                "annotate your resources and plug in your auth provider via SPI.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        // Security features grid
        var features = new WaGrid<>();
        features.setMinColumnSize("14rem");
        features.setGap(PageSize.Small);

        features.add(featureCardHtml("@RolesAllowed",
                "Standard Jakarta annotation. Apply at class or method level. " +
                        "GuicedEE's " + brandCode("SecurityHandler") + " checks roles against the authenticated " + brandCode("Vert.x User") + ".",
                null));

        features.add(featureCard("@PermitAll / @DenyAll",
                "Allow or deny all access at class or method level. " +
                        "Method-level annotations override class-level ones.",
                null));

        features.add(featureCardHtml("Pluggable AuthenticationHandler",
                "Call " + brandCode("SecurityHandler.setDefaultAuthenticationHandler()") + " with any " + brandCode("Vert.x AuthenticationHandler") + " — " +
                        "JWT, OAuth2, Basic, or your own custom handler.",
                null));

        features.add(featureCardHtml("Pluggable AuthorizationProvider",
                "Plug in role/permission resolution via " + brandCode("SecurityHandler.setDefaultAuthorizationProvider()") + ". " +
                        "Works with " + brandCode("Vert.x") + "'s built-in providers or your own.",
                null));

        features.add(featureCardHtml("REST Client @EndpointSecurity",
                "Bearer, JWT, Basic, and ApiKey authentication on outbound REST calls. " +
                        "Credentials support " + brandCode("${ENV_VAR}") + " placeholders — secrets never touch source code.",
                null));

        features.add(featureCardHtml("HTTPS / TLS",
                "JKS and PKCS#12 keystores auto-detected by file extension. " +
                        "Set " + brandCode("HTTPS_ENABLED=true") + ", " + brandCode("HTTPS_KEYSTORE") + ", and " + brandCode("HTTPS_KEYSTORE_PASSWORD") + ".",
                null));

        features.add(featureCardHtml("Call-scoped isolation",
                "Each HTTP request runs in a " + brandCode("Guice CallScope") + " with its own " + brandCode("CallScopeProperties") + ". " +
                        "User context, request data, and scoped services are isolated per request.",
                null));

        features.add(featureCardHtml("CORS security",
                brandCode("@Cors") + " annotation with env var overrides for origins, methods, headers, credentials, and max age. " +
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

    private WaStack buildVertxDeploymentSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Large);

        var intro = bodyTextHtml("The " + brandCode("Vert.x 5") + " HTTP server, router, and verticle infrastructure are fully managed by GuicedEE. " +
                "Three SPI extension points let you customize every layer. Per-verticle isolation means you can run " +
                "different API groups on separate worker pools with their own threading models.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        // Deployment features grid
        var features = new WaGrid<>();
        features.setMinColumnSize("14rem");
        features.setGap(PageSize.Small);

        features.add(featureCardHtml("Auto-start HTTP/HTTPS",
                brandCode("VertxWebServerPostStartup") + " runs as " + brandCode("IGuicePostStartup") + ". Creates HTTP and HTTPS servers " +
                        "from environment config. Compression level 9, TCP keepalive, and configurable limits.",
                null));

        features.add(featureCardHtml("VertxHttpServerOptionsConfigurator",
                "SPI to customize " + brandCode("HttpServerOptions") + " before servers are created. " +
                        "Set idle timeouts, buffer sizes, ALPN, or any " + brandCode("Vert.x") + " option.",
                null));

        features.add(featureCardHtml("VertxHttpServerConfigurator",
                "SPI to configure " + brandCode("HttpServer") + " instances after creation. " +
                        "Add connection handlers, WebSocket upgrade logic, or metrics hooks.",
                null));

        features.add(featureCardHtml("VertxRouterConfigurator",
                "SPI to add routes and middleware to the " + brandCode("Router") + ". Sorted by " + brandCode("sortOrder()") + " — " +
                        "infrastructure runs first, application routes later.",
                null));

        features.add(featureCardHtml("@Verticle annotation",
                "Annotate a package to create an isolated verticle with its own worker pool, " +
                        "threading model, instance count, and HA settings.",
                null));

        features.add(featureCardHtml("Per-verticle sub-routers",
                brandCode("VertxRouterConfigurator") + " implementations in " + brandCode("@Verticle") + " packages get their own " + brandCode("Router") + ", " +
                        "mounted as a sub-router on the main " + brandCode("Router") + ".",
                null));

        features.add(featureCard("Capabilities enum",
                "@Verticle can declare capabilities: Rest, RabbitMQ, Kafka, IBM MQ, Web, Telemetry, Persistence, " +
                        "Sockets, OpenAPI, Swagger, and more. Each maps to its module package.",
                null));

        features.add(featureCardHtml("Startup flow control",
                brandCode("IGuicePostStartup") + " hooks use " + brandCode("sortOrder()") + " for deterministic startup. " +
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

    private WaStack buildMicroProfileSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var intro = bodyTextHtml("Eclipse MicroProfile provides an additive support mechanism, implemented through " + brandCode("Guice") + " integration. " +
                "If you know the MicroProfile annotations, you already know GuicedEE.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCardHtml("MicroProfile Config",
                "Inject configuration with " + brandCode("@ConfigProperty") + ". Sources: environment variables (highest priority), " +
                        "system properties, and " + brandCode("META-INF/microprofile-config.properties") + ". Profile support included.",
                "SmallRye Config under the hood."));

        grid.add(featureCardHtml("MicroProfile Health",
                "Standard " + brandCode("@Liveness") + ", " + brandCode("@Readiness") + ", and " + brandCode("@Startup") + " health checks. " +
                        "Discovered by " + brandCode("ClassGraph") + " and exposed as " + brandCode("/health") + " JSON endpoints on the " + brandCode("Vert.x Router") + ".",
                "Kubernetes-ready out of the box."));

        grid.add(featureCardHtml("MicroProfile Metrics",
                brandCode("@Counted") + ", " + brandCode("@Timed") + ", " + brandCode("@Gauge") + " — backed by Dropwizard Metrics with a Prometheus scrape endpoint. " +
                        "Add the module and your metrics are live.",
                "Prometheus + Graphite reporting."));

        grid.add(featureCardHtml("MicroProfile Fault Tolerance",
                brandCode("@Retry") + ", " + brandCode("@CircuitBreaker") + ", " + brandCode("@Timeout") + ", " + brandCode("@Bulkhead") + ", " + brandCode("@Fallback") + " — " +
                        "resilience patterns wired through " + brandCode("Guice") + " AOP interception.",
                "Production resilience, zero effort."));

        grid.add(featureCardHtml("MicroProfile OpenAPI",
                "OpenAPI 3.1 spec generation from your JAX-RS annotations. " +
                        "Served at " + brandCode("/openapi.json") + " and " + brandCode("/openapi.yaml") + " with zero configuration.",
                "Swagger UI auto-mounts at /swagger/."));

        grid.add(featureCardHtml("OpenTelemetry",
                brandCode("@Trace") + " and " + brandCode("@SpanAttribute") + " for distributed tracing. OTLP export to Tempo, Jaeger, or any collector. " +
                        brandCode("Uni") + "-aware — spans complete when the reactive chain resolves.",
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

    private WaStack buildFoundationSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCardHtml("Guice-first DI",
                "All lifecycle, routing, persistence, messaging, and telemetry are wired through a single " + brandCode("Guice") + " injector. " +
                        brandCode("@Inject") + " works everywhere — in REST resources, health checks, WebSocket receivers, even in event bus consumers.",
                "One injector to rule them all."));

        grid.add(featureCardHtml("Vert.x 5 reactive core",
                "Non-blocking HTTP, WebSockets, event bus, SQL clients, RabbitMQ, Kafka, and IBM MQ — all on the " + brandCode("Vert.x") + " event loop " +
                        "with " + brandCode("Mutiny") + " " + brandCode("Uni") + "/" + brandCode("Multi") + " support throughout the stack.",
                "Built for modern runtime constraints."));

        grid.add(featureCardHtml("JPMS Level 3",
                "Every module ships a proper " + brandCode("module-info.java") + " with explicit " + brandCode("exports") + ", " + brandCode("requires") + ", " + brandCode("provides") + ", and " + brandCode("uses") + ". " +
                        "Build JLink and JPackage distributables confidently.",
                "Real modules, not automatic modules."));

        grid.add(featureCardHtml("SPI + ClassGraph",
                "Modules, lifecycle hooks, REST resources, health checks, consumers — everything is discovered automatically at startup. " +
                        "No manual registration, no classpath scanning hacks.",
                "Convention over configuration, done right."));

        grid.add(featureCardHtml("Pure environment variable config",
                "Every runtime setting — ports, pool sizes, credentials, feature flags — resolves from " + brandCode("environment variables") + " or system properties. " +
                        "No " + brandCode("application.yml") + ", no " + brandCode("bootstrap.properties") + ", no secret files to manage. " +
                        "12-factor by default, cloud-native from day one.",
                "Your deployment IS your configuration."));

        grid.add(featureCardHtml("Config files are optional",
                "Need " + brandCode("microprofile-config.properties") + "? It works. Prefer " + brandCode("persistence.xml") + "? Also works. " +
                        "But you never <em>need</em> them — every value can be set via annotations, code, or env vars. " +
                        "Config files are an escape hatch, not a requirement.",
                "Zero files to deploy if you choose."));

        grid.add(featureCardHtml("One language, end to end",
                "Your REST endpoints, persistence, messaging, health checks, security, configuration, and deployment logic — all in " + brandCode("Java") + ". " +
                        "No YAML to debug, no XML to lint, no JSON schemas to validate. " +
                        "Your IDE catches every error at compile time.",
                "Type-safe from editor to production."));

        return buildSection("Foundations", "What makes GuicedEE different",
                "A cohesive platform where Guice DI, Vert.x reactivity, and JPMS modularity work together — not against each other.",
                true, grid);
    }

    // ── Modular build section ─────────────────────────

    private WaStack buildModularBuildSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCardHtml("Build it up, never exclude down",
                "Start with " + brandCode("inject") + " and add only what you need: " + brandCode("rest") + ", " + brandCode("persistence") + ", " + brandCode("websockets") + ", " + brandCode("rabbitmq") + ", " + brandCode("kafka") + ", " + brandCode("ibmmq") + ". " +
                        "Each module is self-contained with its own JPMS descriptor.",
                "Your runtime is only as big as you choose."));

        grid.add(featureCard("JLink / JPackage ready",
                "Every artifact is JPMS Level 3 — build custom JRE images with only the modules your app needs. " +
                        "Deploy as a native installer or minimal Docker image.",
                "From 500 MB JRE to 40 MB custom runtime."));

        grid.add(featureCardHtml("One BOM, all versions aligned",
                "Import " + brandCode("guicedee-bom") + " in your " + brandCode("dependencyManagement") + ". " +
                        "All 20+ modules are version-aligned and tested together.",
                "No dependency conflicts, ever."));

        grid.add(featureCardHtml("Grouped library wrappers",
                "Third-party libraries (Hibernate, Jackson, Vert.x) are repackaged with proper " + brandCode("module-info.java") + " descriptors. " +
                        "They plug into the module system cleanly.",
                "Over 50 JPMS-wrapped service modules."));

        content.add(grid);

        content.add(mavenGradleCodeBlock("The BOM — one import, everything aligned",
                """
                        <dependencyManagement>
                            <dependencies>
                                <dependency>
                                    <groupId>com.guicedee</groupId>
                                    <artifactId>guicedee-bom</artifactId>
                                    <version>2.0.1</version>
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
                        </dependencies>""",
                """
                        // Import the BOM — all versions aligned
                        dependencies {
                            implementation platform('com.guicedee:guicedee-bom:2.0.1')
                        
                            // Then just add what you need — no versions required
                            implementation 'com.guicedee:rest'
                            implementation 'com.guicedee:persistence'
                        }"""));

        return buildSection("Modular by design", "Ship only what you need",
                "A composable stack where you build up your runtime — never exclude down from a kitchen-sink starter.",
                false, content);
    }

    // ── JLink / Docker shipping section ───────────────

    private WaStack buildJLinkShippingSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var intro = bodyTextHtml("Because every GuicedEE module is JPMS Level 3 with explicit " + brandCode("module-info.java") + " descriptors, " +
                "you can use the " + brandCode("moditect-maven-plugin") + " to create a custom Java runtime that contains only the modules your application actually uses. " +
                "The result is a self-contained image — no external JDK required at deploy time — that ships in a minimal Docker container.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCardHtml("Native JREs",
                "Use the " + brandCode("moditect-maven-plugin") + " with " + brandCode("create-runtime-image") + " to build a trimmed Java runtime. " +
                        "Only the modules your app requires are included — everything else is stripped.",
                "From ~500 MB JDK to ~40 MB custom image."));

        grid.add(featureCardHtml("JPackage installers",
                "Use " + brandCode("jpackage") + " to wrap your JLink image into a native installer — " +
                        "MSI on Windows, DEB/RPM on Linux, DMG on macOS. Zero JDK installation required by end users.",
                "Real desktop/server installers."));

        grid.add(featureCard("Minimal Docker containers",
                "Copy the JLink image into a scratch or distroless base. " +
                        "No JDK layer, no package manager, no shell — just your application and its runtime. " +
                        "Cloud Run, Kubernetes, and ECS ship these instantly.",
                "~40-60 MB Docker images."));

        grid.add(featureCardHtml("Incredible startup time on JRT",
                "JLink custom runtimes utilize the module path, and the JRT filesystem. " +
                        "Combined with " + brandCode("Vert.x") + "'s non-blocking boot and " + brandCode("ClassGraph") + "'s fast scanning, " +
                        "REST services are ready in amazing speed.",
                "Cold-start friendly for serverless."));

        grid.add(featureCard("CI/CD pipeline integration",
                "Your CI builds the JLink image and pushes it to a container registry. " +
                        "Terraform / Cloud Run / Kubernetes deploys reference the JLink image — never a fat JAR on a generic JDK (But you can if you want to...).",
                "No JARs in production."));

        grid.add(featureCardHtml("50+ JPMS service wrappers",
                "Third-party libraries (Hibernate, Jackson, Vert.x, Netty, Log4j2) ship as proper JPMS modules " +
                        "with " + brandCode("exports") + ", " + brandCode("requires") + ", and " + brandCode("provides") + ". JLink resolves the entire dependency graph cleanly.",
                "No automatic-module hacks."));

        content.add(grid);

        content.add(mavenGradleCodeBlock("Moditect JLink → Docker with mvn/gradle package",
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
                        
                        # 1. Build the JLink custom runtime
                        mvn clean package
                        
                        # 2. Package into a minimal Docker image
                        # Dockerfile:
                        #   FROM gcr.io/distroless/base-nossl-debian12
                        #   COPY target/jlink-image /app/jrt
                        #   ENTRYPOINT ["/app/jrt/bin/myservice"]
                        docker build -t my-service:latest .
                        
                        # 3. Push and deploy
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
                        
                        // 1. Build the JLink custom runtime
                        // gradle build
                        
                        // 2. Package into a minimal Docker image
                        // Dockerfile:
                        //   FROM gcr.io/distroless/base-nossl-debian12
                        //   COPY build/jlink-image /app/jrt
                        //   ENTRYPOINT ["/app/jrt/bin/myservice"]
                        // docker build -t my-service:latest .
                        
                        // 3. Push and deploy
                        // docker push registry.example.com/my-service:latest"""));

        content.add(codeBlockWithTitle("Verify your module graph before shipping",
                """
                        # Check what your app actually uses at runtime
                        java --show-module-resolution -m my.service
                        
                        # Or validate providers with jlink directly
                        jlink \\
                          --module-path target/libs \\
                          --add-modules my.service \\
                          --suggest-providers"""));

        var jlinkNote = bodyTextHtml("<strong>Tip:</strong> You can also invoke " + brandCode("jlink") + " directly from the command line to build the runtime image: " +
                brandCode("jlink --module-path target/libs --add-modules my.service --output target/jlink-image --strip-debug --no-header-files --no-man-pages") +
                ". The moditect plugin wraps this into your Maven build lifecycle so it runs automatically during " + brandCode("mvn package") + ".", "s");
        jlinkNote.setWaColorText("quiet");
        content.add(jlinkNote);

        var deployNote = bodyTextHtml("Every GuicedEE module declares its SPI " + brandCode("provides") + " and " + brandCode("uses") + " directives in " + brandCode("module-info.java") + ", " +
                "so JLink can resolve the full service graph automatically. No reflection hacks, no runtime classpath surprises. " +
                "What you see in " + brandCode("module-info.java") + " is exactly what ships.", "s");
        deployNote.setWaColorText("quiet");
        content.add(deployNote);

        return buildSection("Ship as JLink artifacts", "Custom runtimes in minimal Docker containers",
                "JPMS Level 3 means every module participates in JLink. Build a custom JRE, copy it into a distroless container, and deploy instantly.",
                true, content);
    }

    // ── Cloud-aware logging section ───────────────────

    private WaStack buildCloudAwareLoggingSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var intro = bodyTextHtml("GuicedEE ships production-ready logging out of the box. Locally, you get colorized, " +
                "human-readable console output with highlighting. In the cloud, set one environment variable and every " +
                "log line switches to compact JSON — ready for ingestion by CloudWatch, Stackdriver, Loki, Datadog, or any log aggregator. " +
                "This is a programmatic alternative to XML configuration — existing " + brandCode("log4j2.xml") + " and " + brandCode("log4j2.properties") + " files, " +
                "standard Log4j2 API calls, and Lombok's " + brandCode("@Log4j2") + " annotation all continue to work exactly as expected. " +
                "GuicedEE simply gives you a code-first option that integrates with environment variables and the DI lifecycle.", "m");
        intro.setWaColorText("quiet");
        content.add(intro);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("15rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCardHtml("CLOUD=true → JSON logs",
                "Set the " + brandCode("CLOUD") + " environment variable to 'true' and all console appenders switch to compact JSON layout automatically. " +
                        "No " + brandCode("log4j2.xml") + " required, no code changes — it's built into the " + brandCode("GuiceContext") + " static initializer.",
                "One env var for production logging."));

        grid.add(featureCard("Highlighted local output",
                "When CLOUD is not set, console output uses ANSI-highlighted patterns with colored log levels, " +
                        "fixed-width logger names, and thread identifiers. Debug and trace are easy to spot.",
                "Developer-friendly by default."));

        grid.add(featureCardHtml("Rolling file appenders",
                brandCode("LogUtils.addFileRollingLogger()") + " creates daily/size-based rolling appenders. " +
                        "100 MB per file, 30 files retained, automatic rollover. " +
                        "In cloud mode, file loggers are skipped — only console JSON is emitted.",
                "Zero-config file rotation."));

        grid.add(featureCardHtml("@InjectLogger",
                "Inject named Log4j2 loggers into any Guice-managed class with " + brandCode("@InjectLogger(\"name\")") + ". " +
                        "No static Logger fields, no LoggerFactory calls. The " + brandCode("Log4JTypeListener") + " wires them automatically.",
                "Clean, testable logging."));

        grid.add(featureCardHtml("Log4JConfigurator SPI",
                "Need custom appenders or filters? Implement " + brandCode("Log4JConfigurator") + " and register via " + brandCode("ServiceLoader") + ". " +
                        "Your configurator runs during static initialization — before any application code logs.",
                "Full programmatic control."));

        grid.add(featureCardHtml("Dynamic layout switching",
                "Call " + brandCode("GuiceContext.setConsoleLayout(ConsoleLayoutOption.JSON)") + " at any time to switch all console appenders " +
                        "between CURRENT, FIXED, HIGHLIGHT, and JSON layouts. No restart required.",
                "Runtime control."));

        grid.add(featureCardHtml("Log level via environment",
                "Set " + brandCode("LOG_LEVEL") + ", " + brandCode("GUICEDEE_LOG_LEVEL") + ", or " + brandCode("guicedee.log.level") + " to control the root logger level. " +
                        "Or use " + brandCode("DEBUG=true") + " / " + brandCode("TRACE=true") + " for quick toggle. All resolved at startup, overridable at deploy time.",
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

        var note = bodyTextHtml("The logging system is configured in the " + brandCode("GuiceContext") + " static initializer — before any " + brandCode("Guice") + " module loads. " +
                "When " + brandCode("CLOUD=true") + ", stdout receives DEBUG/INFO as JSON, stderr receives WARN/ERROR/FATAL as JSON, " +
                "and the rolling file appender is skipped entirely. This means your container only needs to capture stdout/stderr — " +
                "no log volume mounts, no sidecar log shippers. " +
                "If you already have a " + brandCode("log4j2.xml") + " or " + brandCode("log4j2.properties") + " on your classpath, Log4j2 will honour it as usual — " +
                "the programmatic configuration only applies when no existing config file is detected.", "s");
        note.setWaColorText("quiet");
        content.add(note);

        return buildSection("Cloud-aware logging", "One env var switches to production-ready JSON",
                "CLOUD=true — that's it. Every log line becomes structured JSON for your aggregator. Locally, you get colorized output.",
                false, content);
    }

    // ── Plugin section ────────────────────────────────

    private WaStack buildPluginSection()
    {
        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(pluginCard("REST (JAX-RS)",
                brandCode("@Path") + "/" + brandCode("@GET") + "/" + brandCode("@POST") + " routes auto-registered on Vert.x Router via " + brandCode("ClassGraph") + ". Full parameter binding.", "rest", "Rest/Basic"));
        grid.add(pluginCard("WebSockets",
                "RFC 6455 with call-scoped connections, action-based routing, and group broadcasting.", "websockets", "WebSockets/Basic"));
        grid.add(pluginCard("Persistence",
                brandCode("Hibernate Reactive 7") + " + " + brandCode("Mutiny.SessionFactory") + ". Multi-database, env-var driven.", "persistence", "Persistence/Basic"));
        grid.add(pluginCard("RabbitMQ",
                "Annotate connections, exchanges, queues, consumers, and publishers. Auto-recovery included.", "rabbitmq", "RabbitMQ/Basic"));
        grid.add(pluginCard("Kafka",
                "Annotation-driven Apache Kafka with Vert.x Kafka Client. " + brandCode("@KafkaConnectionOptions") + ", " + brandCode("@KafkaTopicDefinition") + ", consumers, and publishers.", "kafka", "Kafka/Basic"));
        grid.add(pluginCard("IBM MQ",
                "Annotation-driven IBM MQ via JMS client. " + brandCode("@IBMMQConnectionOptions") + ", " + brandCode("@IBMMQQueueDefinition") + ", consumers, and publishers.", "ibmmq", "IBMMQ/Basic"));
        grid.add(pluginCard("Health",
                brandCode("@Liveness") + ", " + brandCode("@Readiness") + ", " + brandCode("@Startup") + " checks at " + brandCode("/health") + " as JSON.", "health", "Health/Basic"));
        grid.add(pluginCard("Metrics",
                "Dropwizard + MicroProfile. " + brandCode("@Counted") + ", " + brandCode("@Timed") + ", Prometheus endpoint.", "metrics", "Metrics/Basic"));
        grid.add(pluginCard("Telemetry",
                "OpenTelemetry tracing with " + brandCode("@Trace") + ", OTLP export, Log4j2 correlation.", "telemetry", "Telemetry/Basic"));
        grid.add(pluginCard("OpenAPI",
                "OpenAPI 3.1 spec at " + brandCode("/openapi.json") + " + " + brandCode("/openapi.yaml") + " from JAX-RS annotations.", "openapi", "OpenAPI/Basic"));
        grid.add(pluginCard("GraphQL",
                "SPI-driven schema at " + brandCode("/graphql") + " with HTTP + WebSocket + optional " + brandCode("GraphiQL") + " IDE.", "graphql", "GraphQL/Basic"));
        grid.add(pluginCard("Swagger UI",
                "Browsable UI at " + brandCode("/swagger/") + " with zero code. Just add the dependency.", "swagger-ui", "OpenAPI/Basic"));
        grid.add(pluginCard("Config",
                brandCode("@ConfigProperty") + " injection from env vars, system props, and properties files.", "config", "Config/Basic"));
        grid.add(pluginCard("Fault Tolerance",
                brandCode("@Retry") + ", " + brandCode("@CircuitBreaker") + ", " + brandCode("@Timeout") + ", " + brandCode("@Bulkhead") + " via Guice AOP.", "fault-tolerance", "FaultTolerance/Basic"));
        grid.add(pluginCard("Web Services",
                "SOAP/JAX-WS via Apache CXF. " + brandCode("@WebService") + " endpoints, WS-Security.", "webservices", "WebServices/Basic"));
        grid.add(pluginCard("CDI Bridge",
                brandCode("@ApplicationScoped") + ", " + brandCode("@RequestScoped") + ", " + brandCode("BeanManager") + " — all mapped to Guice.", "cdi", "CDI/Basic"));
        grid.add(pluginCard("Serial (Cerial)",
                brandCode("@Named") + " port injection, auto-reconnect, idle monitoring, health reporting.", "cerial", "Cerial/Basic"));

        var content = new WaStack<>();
        content.setGap(PageSize.Large);

        // JetBrains Marketplace plugin widget
        var widgetWrapper = new DivSimple<>();
        widgetWrapper.setID("jetbrains-plugin-widget");
        widgetWrapper.addStyle("display:flex;justify-content:center");
        content.add(widgetWrapper);

        content.add(grid);

        return buildSection("Module catalog", "Drop-in capabilities — add a dependency, get a feature",
                "Each integration ships as a micro-module with its own module-info.java. Zero side effects.",
                true, content);
    }

    // ── Developer section ─────────────────────────────

    private WaStack buildDeveloperSection()
    {
        var content = new WaStack<>();
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

    private WaStack buildCallToActionSection()
    {
        var cta = buildCallToAction();
        // Add browse examples to the CTA cluster
        var examplesBtn = browseExamplesButton();
        // Find the hero-ctas cluster and add to it
        cta.add(examplesBtn);
        return cta;
    }

    // ── Card helpers ──────────────────────────────────


    private WaCard<?> pluginCard(String title, String htmlBody, String moduleId, String examplePath)
    {
        var card = featureCardHtml(title, htmlBody, null);

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

        // Remove the title from the body stack since it's now in the header
        if (card.getChildren().get(0) instanceof WaStack stack)
        {
            stack.getChildren().removeIf(c -> c instanceof WaText<?> text && title.equals(text.getText()));
        }

        var buttons = new com.jwebmp.webawesome.components.WaCluster<>();
        buttons.setGap(PageSize.ExtraSmall);

        var cta = new WaButton<>();
        cta.setText(escapeAngular("View module →"));
        cta.setVariant(Variant.Brand);
        cta.setAppearance(Appearance.Outlined);
        cta.addAttribute("(click)", "openReadme('" + moduleId + "', '" + escapeAngular(title) + "')");
        buttons.add(cta);

        var footer = new DivSimple<>();
        footer.add(buttons);
        card.withFooter(footer);

        return card;
    }
}

