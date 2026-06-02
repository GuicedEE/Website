package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.card.WaCard;

/**
 * Cloud integration page — covers service registry, runtime autoconfigure,
 * health monitoring, multi-cloud support, and rest-client integration.
 */
@NgComponent("guicedee-cloud")
@NgRoutable(path = "cloud")
public class CloudPage extends WebsitePage<CloudPage> implements INgComponent<CloudPage>
{
    public CloudPage()
    {
        renderCloud();
    }

    private void renderCloud()
    {
        var layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        // ═══ Hero ═══
        var heroContent = new WaStack<>();
        heroContent.setGap(PageSize.Medium);
        heroContent.add(headingText("h1", "xl", "Cloud & Service Registry"));
        var heroDesc = bodyText(
                "Deploy anywhere — Azure Container Apps, Kubernetes, bare metal — and let GuicedEE auto-discover "
                + "your services, construct URLs, monitor health, and wire REST clients by simple name. "
                + "Zero configuration files, zero manual URL management.", "l");
        heroDesc.setWaColorText("quiet");
        heroContent.add(heroDesc);

        var tags = new WaCluster<>();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("Service Registry", Variant.Brand));
        tags.add(buildTag("Auto-Discovery", Variant.Neutral));
        tags.add(buildTag("Health Monitoring", Variant.Success));
        tags.add(buildTag("Multi-Cloud", Variant.Warning));
        tags.add(buildTag("REST Client", Variant.Danger));
        tags.add(buildTag("OpenAPI Merge", Variant.Brand));
        tags.add(buildTag("GraphQL Gateway", Variant.Neutral));
        tags.add(buildTag("Status Events", Variant.Success));
        tags.add(buildTag("Graceful Shutdown", Variant.Danger));
        tags.add(buildTag("Exponential Backoff", Variant.Warning));
        tags.add(buildTag("Consul", Variant.Success));
        heroContent.add(tags);

        var heroCard = new WaCard<>();
        heroCard.setAppearance(Appearance.Filled);
        heroCard.add(heroContent);
        layout.add(heroCard);

        // ═══ Section: How It Works ═══
        var howItWorksContent = new WaStack<>();
        howItWorksContent.setGap(PageSize.Medium);

        howItWorksContent.add(mermaidDiagram("""
                flowchart TD
                    A["Application Startup"] --> B["RuntimeAutoConfigurePreStartup<br/>MIN+50 — detects platform"]
                    B --> C["ServiceRegistryPreStartup<br/>MIN+150 — scans @RegisteredService"]
                    C --> D["ServiceRegistryPostStartup<br/>MIN+700 — starts health checks"]
                    C --> E["Auto-construct URLs from DNS suffix"]
                    C --> F["Register aliases & external URLs"]
                    C --> G["IServiceRegistryProvider SPIs"]
                    D --> H["Periodic health checks via Vert.x WebClient"]
                    H --> I["Update status: UP / DOWN / DEGRADED"]
                    I --> J["Fire IServiceStatusChangeListener events"]
                    H --> K["Exponential backoff for DOWN services"]
                    L["Application Shutdown"] --> M["ServiceRegistryPreDestroy<br/>marks self as DOWN"]
                    M --> J
                """));

        layout.add(section("Architecture", "How It Works",
                "The service registry automatically detects your cloud platform, registers sibling services, "
                + "and monitors their health — all driven by annotations.",
                howItWorksContent));

        // ═══ Section: Platform Detection ═══
        var platformContent = new WaGrid<>();
        platformContent.setMinColumnSize("18rem");
        platformContent.setGap(PageSize.Medium);

        platformContent.add(featureCard("Azure Container Apps",
                "Auto-detects CONTAINER_APP_ENV_DNS_SUFFIX to construct sibling service URLs. "
                + "Self-registers with region, revision, and replica metadata.",
                "com.guicedee:runtime-autoconfigure"));
        platformContent.add(featureCard("Kubernetes",
                "Register services with explicit cluster URLs like "
                + "http://service.namespace.svc.cluster.local, or use IServiceRegistryProvider "
                + "to discover pods via the Kubernetes API.",
                "@RegisteredService(url = \"...\")"));
        platformContent.add(featureCard("Bare Metal / Custom",
                "Use environment variable placeholders ${MY_URL} or programmatic "
                + "ServiceRegistry.register() for any environment.",
                "Supports any cloud or on-premise"));

        layout.add(section("Platforms", "Platform Auto-Detection",
                "GuicedEE detects your cloud platform at startup and extracts DNS information "
                + "to auto-construct service URLs.",
                platformContent));

        // ═══ Section: Declaring Services ═══
        var declareContent = new WaStack<>();
        declareContent.setGap(PageSize.Medium);

        declareContent.add(codeBlock("""
                @ServiceRegistryOptions(healthCheckInterval = 30, healthPath = "/health/ready")
                @RegisteredService(name = "jwebmp-website",
                    aliases = {"jwebmp", "jwebswing"},
                    externalUrls = {"https://jwebmp.com", "https://jwebswing.com"},
                    kubernetesUrl = "http://jwebmp-website.default.svc.cluster.local")
                @RegisteredService(name = "payment-service",
                    url = "${PAYMENT_URL}",
                    healthPath = "/status")
                @RegisteredService(name = "hello-service",
                    url = "http://hello-service.myns.svc.cluster.local",
                    healthPath = "/healthz")
                package com.myapp;
                """));

        declareContent.add(bodyText(
                "Each @RegisteredService can have its own healthPath, aliases, external URLs, "
                + "and Kubernetes internal URL. The registry default healthPath is only used when "
                + "a service doesn't specify its own.", "m"));

        layout.add(section("Declaration", "Declaring Services",
                "Register services declaratively on package-info.java with full control over "
                + "URLs, health endpoints, aliases, and multi-domain external URLs.",
                declareContent));

        // ═══ Section: URL Resolution ═══
        var resolutionContent = new WaStack<>();
        resolutionContent.setGap(PageSize.Medium);

        resolutionContent.add(codeBlock("""
                // Internal URL (auto-constructed or explicit)
                String url = ServiceRegistry.url("jwebmp-website");
                // → https://jwebmp-website.whitegrass-2e17714c.ukwest.azurecontainerapps.io

                // Alias resolution
                String url = ServiceRegistry.url("jwebmp");  // same result

                // External/public URL (custom domains)
                String ext = ServiceRegistry.externalUrl("jwebmp-website");
                // → https://jwebmp.com

                // Multiple external URLs
                List<String> exts = ServiceRegistry.externalUrls("jwebmp-website");
                // → [https://jwebmp.com, https://jwebswing.com]

                // Kubernetes internal cluster URL
                String k8s = ServiceRegistry.kubernetesUrl("jwebmp-website");
                // → http://jwebmp-website.default.svc.cluster.local

                // Health-aware resolution (empty if service is DOWN)
                Optional<String> healthy = ServiceRegistry.healthyUrl("jwebmp-website");

                // registry: prefix for programmatic resolution
                String resolved = ServiceRegistry.resolve("registry:jwebmp-website");
                """));

        layout.add(section("Resolution", "URL Resolution",
                "Resolve services by simple name, alias, or registry: prefix. "
                + "Access internal, external, and Kubernetes URLs independently.",
                resolutionContent));

        // ═══ Section: REST Client Integration ═══
        var restClientContent = new WaStack<>();
        restClientContent.setGap(PageSize.Medium);

        restClientContent.add(bodyTextHtml(
                "When " + brandCode("service-registry") + " is on the classpath, the REST client "
                + "automatically resolves service names from the registry. Three URL formats work:",
                "m"));

        restClientContent.add(codeBlock("""
                // 1. Explicit registry: prefix
                @Endpoint(url = "registry:jwebmp-website")
                @Named("jwebmp-api")
                private RestClient<Void, Response> client;

                // 2. Bare service name (auto-detected)
                @Endpoint(url = "jwebmp-website")
                @Named("jwebmp-api")
                private RestClient<Void, Response> client;

                // 3. Full URL (passes through unchanged)
                @Endpoint(url = "https://api.example.com/v1")
                @Named("example-api")
                private RestClient<Void, Response> client;
                """));

        restClientContent.add(bodyText(
                "No hard dependency required — the rest-client uses reflection to check if the "
                + "service registry module is available. If it is, bare service names and registry: "
                + "prefixes are resolved automatically. If not, URLs pass through as-is.", "m"));

        layout.add(section("REST Client", "REST Client Integration",
                "Wire REST clients by service name — the registry handles URL resolution, "
                + "so your code never contains hardcoded URLs.",
                restClientContent));

        // ═══ Section: Health Monitoring ═══
        var healthContent = new WaStack<>();
        healthContent.setGap(PageSize.Medium);

        healthContent.add(codeBlock("""
                @ServiceRegistryOptions(
                    healthCheckInterval = 30,     // seconds (0 = disabled)
                    healthPath = "/health/ready",  // default for services without their own
                    healthCheckTimeout = 5000      // ms
                )

                // Per-service health path override:
                @RegisteredService(name = "legacy-app", healthPath = "/status")
                @RegisteredService(name = "k8s-service", healthPath = "/healthz")
                @RegisteredService(name = "standard-service")  // uses default /health/ready
                """));

        var healthGrid = new WaGrid<>();
        healthGrid.setMinColumnSize("14rem");
        healthGrid.setGap(PageSize.Medium);
        healthGrid.add(featureCard("Per-Service Health Paths",
                "Each service can specify its own health endpoint. The global default is only a fallback.",
                null));
        healthGrid.add(featureCard("Status States",
                "UP, DOWN, DEGRADED, UNKNOWN — tracked per service with last-checked timestamp.",
                null));
        healthGrid.add(featureCard("Health-Aware Resolution",
                "ServiceRegistry.healthyUrl() returns empty if the service is DOWN — "
                + "enabling circuit-breaker patterns.",
                null));
        healthContent.add(healthGrid);

        layout.add(section("Health", "Health Monitoring",
                "Periodic health checks run via Vert.x WebClient with configurable intervals, "
                + "timeouts, and per-service health endpoints.",
                healthContent));

        // ═══ Section: SPI & Extension Points ═══
        var spiContent = new WaStack<>();
        spiContent.setGap(PageSize.Medium);

        spiContent.add(codeBlock("""
                public class AzureCatalogProvider implements IServiceRegistryProvider<AzureCatalogProvider> {
                    @Override
                    public List<ServiceEntry> discover() {
                        // Query Azure Management API for all container apps
                        return List.of(new ServiceEntry("my-api", "https://my-api.azurecontainerapps.io",
                            "/health/ready", ServiceStatus.UNKNOWN, Instant.now(), Map.of()));
                    }

                    @Override
                    public String providerId() { return "azure-catalog"; }
                }
                """));

        spiContent.add(codeBlock("""
                // module-info.java
                provides IServiceRegistryProvider with AzureCatalogProvider;
                """));

        layout.add(section("SPI", "SPI & Extension Points",
                "Plug in custom service catalogs — Consul, Azure API, Kubernetes API, "
                + "or any external registry — via the IServiceRegistryProvider SPI.",
                spiContent));

        // ═══ Section: Consul Integration ═══
        var consulContent = new WaStack<>();
        consulContent.setGap(PageSize.Medium);

        consulContent.add(bodyText(
                "GuicedEE ships a first-class Consul module for service registration, health checking, "
                + "and address resolution. Declare @ConsulOptions on your package-info to connect to "
                + "a Consul agent — services are registered automatically on startup and deregistered on shutdown.", "m"));

        consulContent.add(codeBlock("""
                @ConsulOptions(
                    host = "consul.internal",
                    port = 8500,
                    token = "${CONSUL_TOKEN}",
                    registerService = true,
                    serviceName = "my-api",
                    servicePort = 8080,
                    healthPath = "/health/ready",
                    healthInterval = "10s",
                    deregisterAfter = "1m"
                )
                package com.myapp;
                """));

        consulContent.add(codeBlock("""
                // Consul Service Resolver — client-side load balancing via Consul
                @ServiceResolverOptions(value = "my-resolver", type = "consul")
                package com.myapp;

                // module-info.java
                module my.app {
                    requires com.guicedee.consul;
                    requires com.guicedee.consul.resolver;  // for address resolution
                }
                """));

        var consulGrid = new WaGrid<>();
        consulGrid.setMinColumnSize("14rem");
        consulGrid.setGap(PageSize.Medium);
        consulGrid.add(featureCard("Auto-Registration",
                "Services are registered with Consul on startup with health check configuration. "
                + "Automatic deregistration on shutdown.",
                null));
        consulGrid.add(featureCard("ACL Token Support",
                "Supports Consul ACL tokens via annotation or environment variable (CONSUL_TOKEN).",
                null));
        consulGrid.add(featureCard("Address Resolution",
                "The consul-service-resolver module provides IServiceResolverProvider SPI — "
                + "queries Consul health API for healthy instances with client-side load balancing.",
                null));
        consulGrid.add(featureCard("Environment Override",
                "All @ConsulOptions values can be overridden via CONSUL_{NAME}_{PROPERTY} or "
                + "CONSUL_{PROPERTY} environment variables.",
                null));
        consulContent.add(consulGrid);

        layout.add(section("Consul", "Consul Integration",
                "First-class HashiCorp Consul support — service registration, health checks, "
                + "and Consul-based service resolution.",
                consulContent));

        // ═══ Section: Configuration Reference ═══
        var configContent = new WaStack<>();
        configContent.setGap(PageSize.Medium);

        var optionsGrid = new WaGrid<>();
        optionsGrid.setMinColumnSize("18rem");
        optionsGrid.setGap(PageSize.Medium);
        optionsGrid.add(featureCard("@ServiceRegistryOptions",
                "healthCheckInterval (30s), healthPath (/health/ready), registerSelf (true), "
                + "healthCheckTimeout (5000ms), useHttps (true), defaultPort (0).",
                "Global registry configuration"));
        optionsGrid.add(featureCard("@RegisteredService",
                "name (required), url, healthPath, aliases[], externalUrls[], externalUrl, kubernetesUrl, "
                + "openApiPath, openApiEnvironments[], graphqlPath, graphqlEnvironments[]. "
                + "All URL fields support ${ENV_VAR} placeholders.",
                "Per-service declaration"));
        optionsGrid.add(featureCard("Environment Variables",
                "CONTAINER_APP_ENV_DNS_SUFFIX (Azure), SERVICE_REGISTRY_DNS_SUFFIX (fallback), "
                + "plus any custom ${VAR} in URL fields.",
                "Runtime overrides"));
        configContent.add(optionsGrid);

        layout.add(section("Configuration", "Configuration Reference",
                "All configuration is annotation-driven with environment variable overrides.",
                configContent));

        // ═══ Section: Installation ═══
        var installContent = new WaStack<>();
        installContent.setGap(PageSize.Medium);

        installContent.add(mavenGradleCodeBlock(
                "Maven / Gradle",
                """
                <dependency>
                  <groupId>com.guicedee</groupId>
                  <artifactId>service-registry</artifactId>
                </dependency>
                """,
                """
                implementation("com.guicedee:service-registry:2.1.0-SNAPSHOT")
                """));

        installContent.add(codeBlock("""
                // module-info.java
                module my.app {
                    requires com.guicedee.service.registry;
                    // Optional: for rest-client integration
                    requires com.guicedee.rest.client;
                }
                """));

        layout.add(section("Installation", "Getting Started",
                "Add the service-registry dependency and declare your services.",
                installContent));

        // ═══ Section: OpenAPI Merge ═══
        var openApiMergeContent = new WaStack<>();
        openApiMergeContent.setGap(PageSize.Medium);

        openApiMergeContent.add(bodyText(
                "Services can advertise their OpenAPI spec via @RegisteredService metadata. "
                + "A gateway service can then discover and merge all OpenAPI specs from healthy services, "
                + "filtered by environment — enabling a unified API documentation portal.", "m"));

        openApiMergeContent.add(codeBlock("""
                @RegisteredService(name = "order-service",
                    openApiPath = "/openapi.json",
                    openApiEnvironments = {"dev", "int", "prod"})
                @RegisteredService(name = "payment-service",
                    openApiPath = "/openapi.json",
                    openApiEnvironments = {"dev", "int"})
                package com.myapp;
                """));

        openApiMergeContent.add(codeBlock("""
                // Programmatic access to all OpenAPI endpoints for current environment
                Map<String, String> specs = ServiceRegistry.allOpenApiUrls("dev");
                // → {order-service=https://order-service.../openapi.json,
                //    payment-service=https://payment-service.../openapi.json}
                """));

        layout.add(section("OpenAPI", "OpenAPI Spec Merge",
                "Discover and aggregate OpenAPI specs from all registered services, "
                + "filtered by environment (dev, int, prod).",
                openApiMergeContent));

        // ═══ Section: GraphQL Gateway ═══
        var graphqlGatewayContent = new WaStack<>();
        graphqlGatewayContent.setGap(PageSize.Medium);

        graphqlGatewayContent.add(bodyText(
                "The GraphQL module includes an optional gateway that stitches remote GraphQL schemas "
                + "from services registered in the service registry. When enabled, it introspects each "
                + "service's GraphQL endpoint, merges their schemas into a single unified graph, "
                + "and proxies queries to the originating service — all filtered by environment.", "m"));

        graphqlGatewayContent.add(codeBlock("""
                // Declare a service with a GraphQL endpoint
                @RegisteredService(name = "order-service",
                    graphqlPath = "/graphql",
                    graphqlEnvironments = {"dev", "int", "prod"})
                @RegisteredService(name = "inventory-service",
                    graphqlPath = "/graphql",
                    graphqlEnvironments = {"dev", "int"})
                package com.myapp;
                """));

        graphqlGatewayContent.add(codeBlock("""
                # Enable the gateway on the aggregator service (env vars or config)
                GRAPHQL_GATEWAY_ENABLED=true
                GRAPHQL_GATEWAY_ENVIRONMENT=dev
                GRAPHQL_GATEWAY_TIMEOUT_MS=10000
                """));

        var gatewayGrid = new WaGrid<>();
        gatewayGrid.setMinColumnSize("14rem");
        gatewayGrid.setGap(PageSize.Medium);
        gatewayGrid.add(featureCard("Schema Stitching",
                "Introspects remote services via standard GraphQL introspection, "
                + "converts to SDL, and merges into a unified schema.",
                null));
        gatewayGrid.add(featureCard("Proxy Data Fetchers",
                "Queries for remote types are automatically proxied to the originating "
                + "service's GraphQL endpoint via Vert.x WebClient.",
                null));
        gatewayGrid.add(featureCard("Environment Filtering",
                "Only merge schemas from services allowed in the current environment "
                + "(dev, int, prod) — controlled via graphqlEnvironments metadata.",
                null));
        gatewayGrid.add(featureCard("Health-Aware",
                "Unhealthy services are automatically excluded from the merged schema. "
                + "The gateway only stitches schemas from UP or DEGRADED services.",
                null));
        graphqlGatewayContent.add(gatewayGrid);

        graphqlGatewayContent.add(bodyText(
                "The gateway is optional — it activates only when service-registry and vertx-web-client "
                + "are on the classpath and GRAPHQL_GATEWAY_ENABLED=true. Your local schema providers "
                + "always take priority over remote schemas.", "m"));

        graphqlGatewayContent.add(mermaidDiagram("""
                flowchart LR
                    A["Gateway Service"] -->|introspects| B["order-service /graphql"]
                    A -->|introspects| C["inventory-service /graphql"]
                    A -->|merges schemas| D["Unified GraphQL Endpoint /graphql"]
                    E["Client"] -->|query| D
                    D -->|proxy| B
                    D -->|proxy| C
                """));

        layout.add(section("GraphQL Gateway", "GraphQL Schema Stitching",
                "Merge GraphQL schemas from multiple microservices into a unified endpoint, "
                + "with environment filtering and health-aware resolution.",
                graphqlGatewayContent));

        // ═══ Section: Status Change Events ═══
        var statusEventsContent = new WaStack<>();
        statusEventsContent.setGap(PageSize.Medium);

        statusEventsContent.add(bodyText(
                "The service registry fires events whenever a service's health status changes. "
                + "Implement IServiceStatusChangeListener to react to status transitions — "
                + "invalidate caches, trigger circuit-breakers, refresh schemas, or send alerts.", "m"));

        statusEventsContent.add(codeBlock("""
                public class MyStatusListener implements IServiceStatusChangeListener<MyStatusListener> {
                    @Override
                    public void onStatusChange(String serviceName, ServiceStatus previousStatus,
                                               ServiceStatus newStatus, ServiceEntry entry) {
                        if (newStatus == ServiceStatus.DOWN) {
                            log.warn("🚨 {} went DOWN!", serviceName);
                            // Invalidate cached connections, trigger alerts, etc.
                        }
                        if (previousStatus == ServiceStatus.DOWN && newStatus == ServiceStatus.UP) {
                            log.info("✅ {} recovered!", serviceName);
                            // Refresh merged schemas, re-enable routing, etc.
                        }
                    }
                }
                """));

        statusEventsContent.add(codeBlock("""
                // Register via module-info.java (ServiceLoader discovery)
                provides IServiceStatusChangeListener with MyStatusListener;

                // Or register programmatically at runtime
                ServiceRegistry.addStatusChangeListener(myListener);
                ServiceRegistry.removeStatusChangeListener(myListener);
                """));

        var statusEventsGrid = new WaGrid<>();
        statusEventsGrid.setMinColumnSize("14rem");
        statusEventsGrid.setGap(PageSize.Medium);
        statusEventsGrid.add(featureCard("Deduplication",
                "Events only fire when status actually changes — same-status updates are suppressed.",
                null));
        statusEventsGrid.add(featureCard("Fault Isolation",
                "One failing listener never prevents other listeners from receiving events.",
                null));
        statusEventsGrid.add(featureCard("Dual Registration",
                "Register listeners via ServiceLoader (module-info) or programmatically at runtime.",
                null));
        statusEventsGrid.add(featureCard("Full Context",
                "Listeners receive the service name, previous status, new status, and the full ServiceEntry.",
                null));
        statusEventsContent.add(statusEventsGrid);

        layout.add(section("Status Events", "Status Change Notifications",
                "React to service health transitions in real-time with the IServiceStatusChangeListener SPI.",
                statusEventsContent));

        // ═══ Section: Graceful Shutdown ═══
        var shutdownContent = new WaStack<>();
        shutdownContent.setGap(PageSize.Medium);

        shutdownContent.add(bodyText(
                "During rolling deployments or application shutdown, the registry automatically marks "
                + "this instance as DOWN and fires status change events — ensuring other services "
                + "see the shutdown immediately rather than waiting for the next health poll to timeout.", "m"));

        shutdownContent.add(codeBlock("""
                // Automatic — happens via IGuicePreDestroy during IGuiceContext.destroy()
                // No code needed! The ServiceRegistryPreDestroy hook:
                //   1. Marks all "self" services as DOWN
                //   2. Fires IServiceStatusChangeListener events
                //   3. Cancels the periodic health check timer
                //   4. Closes the Vert.x WebClient
                """));

        shutdownContent.add(mermaidDiagram("""
                sequenceDiagram
                    participant App as Application
                    participant Reg as ServiceRegistry
                    participant Listeners as Status Listeners
                    participant Timer as Health Timer

                    App->>Reg: IGuiceContext.destroy()
                    Reg->>Reg: Mark self as DOWN
                    Reg->>Listeners: onStatusChange(self, UP→DOWN)
                    Reg->>Timer: Cancel periodic timer
                    Reg->>Reg: Close WebClient
                """));

        layout.add(section("Shutdown", "Graceful Shutdown",
                "Zero-config graceful shutdown — your service is marked DOWN immediately on shutdown, "
                + "not after the next health check cycle.",
                shutdownContent));

        // ═══ Section: Exponential Backoff ═══
        var backoffContent = new WaStack<>();
        backoffContent.setGap(PageSize.Medium);

        backoffContent.add(bodyText(
                "When a service goes DOWN, the registry doesn't hammer it with health checks every cycle. "
                + "Instead, it applies exponential backoff — progressively increasing the delay between "
                + "retries to reduce load on struggling services while still detecting recovery.", "m"));

        backoffContent.add(codeBlock("""
                // Backoff behavior (base interval = 30s):
                // Failure #1: check every 2 × 30s = 60s
                // Failure #2: check every 4 × 30s = 120s
                // Failure #3: check every 8 × 30s = 240s
                // Failure #4: check every 16 × 30s = 480s
                // Failure #5+: check every 32 × 30s = 960s (max)
                //
                // Recovers instantly: as soon as a check succeeds, backoff resets to 0.
                """));

        var backoffGrid = new WaGrid<>();
        backoffGrid.setMinColumnSize("14rem");
        backoffGrid.setGap(PageSize.Medium);
        backoffGrid.add(featureCard("Reduced Load",
                "DOWN services are checked less frequently, preventing resource waste on unreachable endpoints.",
                null));
        backoffGrid.add(featureCard("Instant Recovery",
                "The moment a service responds successfully, backoff resets and normal polling resumes.",
                null));
        backoffGrid.add(featureCard("Bounded Maximum",
                "Backoff caps at 32× the base interval — ensuring eventual re-check even for long outages.",
                null));
        backoffGrid.add(featureCard("Per-Service Tracking",
                "Each service has independent backoff state — one flaky service doesn't affect others.",
                null));
        backoffContent.add(backoffGrid);

        layout.add(section("Backoff", "Exponential Backoff",
                "Intelligent health check backoff reduces polling pressure on DOWN services "
                + "while maintaining fast recovery detection.",
                backoffContent));

        // ═══ Section: Downstream Health Aggregation ═══
        var downstreamContent = new WaStack<>();
        downstreamContent.setGap(PageSize.Medium);

        downstreamContent.add(bodyText(
                "The health module includes a built-in @Readiness check that aggregates health status "
                + "from all services in the registry. Your gateway's readiness endpoint automatically "
                + "reflects whether downstream services are reachable — perfect for Kubernetes "
                + "readiness probes and load balancer health checks.", "m"));

        downstreamContent.add(codeBlock("""
                // Automatic when both modules are on the classpath:
                //   requires com.guicedee.health;
                //   requires com.guicedee.service.registry;
                //
                // The DownstreamHealthCheck is discovered and registered automatically.
                // Control via environment variable:
                //   HEALTH_DOWNSTREAM_ENABLED=true (default)
                //   HEALTH_DOWNSTREAM_ENABLED=false (to disable)
                """));

        downstreamContent.add(codeBlock("""
                // Health endpoint response when downstream check is active:
                // GET /health/ready
                {
                    "status": "UP",
                    "checks": [
                        {
                            "name": "downstream-services",
                            "status": "UP",
                            "data": {
                                "order-service": "UP",
                                "payment-service": "UP",
                                "inventory-service": "DEGRADED",
                                "total": "3",
                                "up": "3",
                                "down": "0"
                            }
                        }
                    ]
                }
                """));

        var downstreamGrid = new WaGrid<>();
        downstreamGrid.setMinColumnSize("14rem");
        downstreamGrid.setGap(PageSize.Medium);
        downstreamGrid.add(featureCard("Readiness Only",
                "Downstream health is a readiness concern — prevents traffic routing to a gateway "
                + "when backends are down. Never liveness (don't restart pods for downstream issues).",
                null));
        downstreamGrid.add(featureCard("Optional Activation",
                "Uses requires static — only activates when service-registry is on the classpath. "
                + "No hard dependency required.",
                null));
        downstreamGrid.add(featureCard("Per-Service Detail",
                "Reports each downstream service's status individually in the health response data, "
                + "plus aggregate counts.",
                null));
        downstreamGrid.add(featureCard("DEGRADED = Healthy",
                "Services with DEGRADED status are counted as healthy — only fully DOWN services "
                + "trigger a failing readiness check.",
                null));
        downstreamContent.add(downstreamGrid);

        layout.add(section("Downstream Health", "Downstream Health Aggregation",
                "Automatically aggregate downstream service health into your readiness endpoint — "
                + "enabling intelligent traffic routing in Kubernetes and load balancers.",
                downstreamContent));

        // ═══ CTA ═══
        layout.add(buildCallToAction());
    }
}

