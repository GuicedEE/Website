package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Size;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.waswitch.WaSwitch;

import java.util.ArrayList;
import java.util.List;

@NgComponent("guicedee-environment-variables")
@NgRoutable(path = "environment-variables")
public class EnvironmentVariablesPage extends WebsitePage<EnvironmentVariablesPage> implements INgComponent<EnvironmentVariablesPage>
{
    private int tableSequence;

    public EnvironmentVariablesPage()
    {
        renderContent();
    }

    @Override
    public List<String> fields()
    {
        var fields = new ArrayList<>(super.fields());
        fields.add("wrappedTables: Record<string, boolean> = {};");
        return fields;
    }

    @Override
    public List<String> methods()
    {
        var methods = new ArrayList<>(super.methods());
        methods.add("""
                setTableWrap(id: string, event: Event) {
                    this.wrappedTables[id] = !!(event.target as HTMLElement & {checked: boolean}).checked;
                }
                """);
        return methods;
    }

    private void renderContent()
    {
        var layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        // Intro
        var introContent = new WaStack<>();
        introContent.setGap(PageSize.Medium);
        introContent.add(headingText("h1", "xl", "Annotations & Environment Variables"));
        var intro = bodyText("Every GuicedEE module supports environment variable overrides for deploy-time configuration. "
                + "The Vert.x configuration tables below are generated from the GuicedEE annotation and runtime lookup inventory for Vert.x 5.2.3.", "l");
        intro.setWaColorText("quiet");
        introContent.add(intro);

        var tags = new WaCluster<>();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("Deploy-time config", Variant.Brand));
        tags.add(buildTag("No code changes", Variant.Neutral));
        tags.add(buildTag("12-factor ready", Variant.Success));
        introContent.add(tags);

        var introCard = new WaCard<>();
        introCard.setAppearance(Appearance.Filled);
        introCard.add(introContent);
        layout.add(introCard);
        layout.add(pageFilter("Filter variables", ".content-section"));

        layout.add(configurationOrderSection());
        layout.add(moduleAnnotationTextSection());
        layout.add(buildInjectSection());
        layout.add(buildJsonSection());
        layout.add(buildWebSection());
        layout.add(buildVertxSection());
        layout.add(buildVertxEventBusSection());
        layout.add(configurationSection("Verticle Deployment", "Deployment options apply to default and annotated verticles. Named worker limits require a worker pool name. Capability prefixes select startup hooks."));
        layout.add(configurationSection("Redis", "RedisOptions discovers connection definitions. Register a concrete RedisModule to create clients. Database -1 preserves the URI; explicit database selection applies to all endpoints. Connection keys are global overrides across repeated definitions."));
        layout.add(configurationSection("gRPC", "GrpcOptions discovers connection definitions. Register a concrete GrpcModule and service providers to create listeners. Message size, gRPC-Web and transcoding controls configure the actual server options. Connection keys are global overrides across repeated definitions."));
        layout.add(buildHazelcastSection());
        layout.add(buildVertxFileSystemSection());
        layout.add(buildVertxAddressResolverSection());
        layout.add(buildRestClientSection());
        layout.add(buildRestCorsSection());
        layout.add(buildAuthSection());
        layout.add(buildHealthSection());
        layout.add(buildMetricsSection());
        layout.add(buildTelemetrySection());
        layout.add(configurationSection("Programmatic Options", "Complete declared setter inventory for the six reviewed Vert.x 5.2.3 core option classes. Entries without an annotation remain programmatic; they have no implied environment key. Nested option objects are available through VertxConfigurator.options. Inherited and integration-specific APIs are outside this declared-method inventory."));
        layout.add(buildRabbitMQSection());
        layout.add(buildGraphQLSection());
        layout.add(buildKafkaSection());
        layout.add(buildIbmMqSection());
        layout.add(buildPersistenceSection());
        layout.add(buildWebServicesSection());
        layout.add(buildServiceDiscoverySection());
        layout.add(buildServiceRegistrySection());
        layout.add(buildConsulSection());
        layout.add(buildMcpSection());
    }

    private WaStack<?> configurationOrderSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);
        content.add(bodyText("For each key: JVM system property > OS environment > .env.local > .env > registered application defaults > option annotation or runtime default. Resolution does not copy values into JVM properties, so defaults from one lookup cannot mask another declaration.", "m"));
        content.add(bodyText("Dot-env lookup starts in the working directory, walks up at most five parents independently for each file, then falls back to a file resource at the classpath root. An empty nearest file stops the search. OS and dot-env lookup accept the exact name, then its uppercase form with dots/hyphens replaced by underscores. Explicit empty strings remain values; options documented as empty/inherit retain their native default.", "m"));
        content.add(bodyText("GuiceApplication.run(Main.class) registers @GuiceOptions and repeatable @EnvironmentProperty defaults before bootstrap. Class defaults override package defaults. Both ${NAME:default} and ${NAME:-default} placeholders are supported, including nested values. Missing placeholders without a fallback remain unresolved.", "m"));
        content.add(bodyText("Text annotation entries also accept bare property names: @HttpServerOptions(host = \"APP_BIND_HOST\") first checks HTTP_HOST across its normal sources, then looks up APP_BIND_HOST in JVM properties, OS environment, .env.local, .env and application defaults. If neither exists, the annotation text remains literal. Each string-array entry resolves separately. Found values can contain placeholders, but are not followed recursively as bare names; explicit external overrides and placeholder results remain values.", "m"));
        content.add(bodyText("Module option annotations work on classes and packages. SPI mode requires their declaring class or package in IGuiceDiscovery; scanner mode requires the application module/package to be scanned. Singleton configurations reject duplicates. VertxConfigurator.options and HTTP server configurators run after declarative options and can deliberately override them.", "m"));
        content.add(bodyText("The tables separate annotation defaults from behavior without an annotation. GuicedEE orchestration controls and intentional defaults are distinguished from raw Vert.x 5.2.3 options. Reserved event metadata has no built-in runtime effect. Complex options, callbacks, classloaders and transport objects remain programmatic SPI settings.", "m"));
        return section("Configuration Search Order", "Vert.x 5.2.3 configuration contract", "Annotations, sources, activation and extension points", content);
    }

    private WaStack<?> moduleAnnotationTextSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);
        content.add(bodyText("The shared text lookup applies across GuicedEE configuration annotations: normal scoped key, normal global key, then the annotation text as a property name, then its literal value. Each key uses the source order above. Scope selectors use the declared name; registries store the resolved name. Arrays resolve entry by entry, and explicit overrides are not interpreted again as bare names.", "m"));
        content.add(bodyText("The inventory covers 266 text members in 69 owned annotations across 31 runtime modules. EnvironmentProperty.name identifies a literal configuration key. InjectLogger level/file hints have no built-in consumer; its logger name resolves normally. Standard and third-party annotations such as Named qualifiers and Jakarta REST paths retain their defined semantics. Cloud metadata defaults do not create or reconfigure cloud resources.", "m"));
        content.add(envTable("Module annotation text coverage", AnnotationTextCatalog.rows(), "Module", "Text members", "Resolution / scope"));
        return section("Annotation Text Across Modules", "Shared property lookup", "Bare names, placeholders, arrays and scope", content);
    }

    private WaStack<?> configurationSection(String group, String description)
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);
        content.add(envTable("Annotation and environment contract", VertxConfigurationCatalog.rows(group),
                "Annotation / attribute", "Environment key", "Type / unit", "Annotation default", "Without annotation", "Application / status"));
        return section(group, "Vert.x 5.2.3", description, content);
    }

    // ── Inject ──────────────────────────────────────────

    private WaStack<?> buildInjectSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Logging & Runtime",
                new String[][]{
                        {"GUICEDEE_LOG_LEVEL", "@GuiceOptions.logLevel; log level for GuicedEE internals", "INFO"},
                        {"GUICEDEE_DISCOVERY_MODE", "@GuiceOptions.discoveryMode; scan or spi. Register the application before bootstrap", "scan"},
                        {"GUICEDEE_STARTUP_TIMEOUT_SECONDS", "Startup wait timeout; GuiceApplication.run supplies 300 seconds, lower-level context startup uses 60", "300 / 60"},
                        {"LOG_LEVEL", "Global application log level", "INFO"},
                        {"DEBUG", "Enable debug logging (set to true; also: guicedee.debug)", "false"},
                        {"TRACE", "Enable trace logging (set to true; also: guicedee.trace)", "false"},
                        {"CLOUD", "Switch log layout to compact JSON for log aggregators", "false"},
                }
        ));

        return section("Injection & Runtime", "GuicedEE Inject",
                "Core bootstrap, classpath scanning, and logging configuration.", content);
    }

    // ── JSON ────────────────────────────────────────────

    private WaStack<?> buildJsonSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "JSON Serialization",
                new String[][]{
                        {"JSON_MAX_STRING_LENGTH", "Maximum JSON string length for Jackson ObjectMapper (bytes)", "262144000"},
                }
        ));

        return section("JSON", "GuicedEE JSON Representations",
                "Jackson ObjectMapper configuration via environment variables.", content);
    }

    // ── Web ─────────────────────────────────────────────

    private WaStack<?> buildWebSection()
    {
        return configurationSection("HTTP Server", "GuicedEE HTTP defaults are intentionally larger than upstream request limits. HTTPS ALPN and cleartext H2C have independent controls; keystores remain environment settings. Complex TLS, network, HTTP/2 and WebSocket options use VertxHttpServerOptionsConfigurator.");
    }

    // ── Vert.x ──────────────────────────────────────────

    private WaStack<?> buildVertxSection()
    {
        return configurationSection("Vert.x Runtime & Consumers", "Core configuration works with environment values alone. Class/package option declarations are singletons. Event-specific keys precede global keys; normalize addresses to uppercase and replace dots and hyphens with underscores. Ordering, batching and resume fields are reserved metadata.");
    }

    // ── Vert.x EventBus ─────────────────────────────────

    private WaStack<?> buildVertxEventBusSection()
    {
        return configurationSection("EventBus", "These configure event-bus networking. A cluster manager must be enabled separately for clustering. TLS certificates, trust options and additional network controls use VertxConfigurator.options.");
    }

    // ── Vert.x FileSystem ───────────────────────────────

    private WaStack<?> buildHazelcastSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);
        content.add(envTable("Activation and membership", new String[][]{
                {"VERTX_CLUSTER_ENABLED", "Explicit true/false override; absent uses intentional server configuration, not dependency presence", "intentional config"},
                {"HAZELCAST_CLIENT_ENABLED", "Independent client gate; absent starts only an intentionally configured client", "intentional config"},
                {"HAZELCAST_CLUSTER_NAME", "Server annotation cluster name override; isolate application/environment", "annotation / guicedee-local"},
                {"HAZELCAST_START_LOCAL", "Request an externally owned embedded member; independent of event-bus cluster activation", "annotation / false"},
                {"HAZELCAST_PORT", "Membership listen port", "5701"},
                {"HAZELCAST_PORT_AUTO_INCREMENT", "Allow an alternate membership port; use false for explicit node examples", "true"},
                {"HAZELCAST_PUBLIC_ADDRESS", "Membership endpoint advertised to peers (host:port)", "annotation / empty"},
                {"HAZELCAST_INTERFACES", "Comma-separated bind interfaces", "annotation / empty"},
                {"HAZELCAST_INTERFACES_ENABLED", "Apply membership interface filtering", "false"},
                {"HAZELCAST_JOIN_TYPE", "Server annotation discovery: TCP, MULTICAST, KUBERNETES or NONE", "annotation"},
                {"HAZELCAST_TCP_MEMBERS", "Comma-separated membership endpoints for TCP join", "annotation / empty"}
        }));
        content.add(bodyText("Server network overrides require @HazelcastServerOptions; use a server configuration SPI " +
                "for programmatic configuration. No incidental dependency starts a member or client. An explicit cache-only " +
                "startLocal=true, clustered=false member can still run with VERTX_CLUSTER_ENABLED=false. Use VERTX_EVENTBUS_HOST/PORT " +
                "and VERTX_EVENTBUS_CLUSTER_PUBLIC_HOST/PORT separately for event-bus transport. No HAZELCAST_CLUSTERED override exists.", "m"));
        return section("Hazelcast Clustering", "Intentional activation and member endpoints",
                "Membership discovery and event-bus networking have separate configuration and lifecycle ownership.", content);
    }

    private WaStack<?> buildVertxFileSystemSection()
    {
        return configurationSection("FileSystem", "An empty cache path inherits Vert.x settings. The exact-path flag controls whether Vert.x appends a generated suffix.");
    }

    // ── Vert.x Address Resolver ─────────────────────────

    private WaStack<?> buildVertxAddressResolverSection()
    {
        return configurationSection("Address Resolver", "DNS servers and search domains are comma-separated. The hosts refresh value and unit are applied together; GuicedEE uses milliseconds by default. Empty lists and paths inherit system defaults.");
    }

    // ── REST Client ─────────────────────────────────────

    private WaStack<?> buildRestClientSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(bodyText("Variables are scoped by endpoint name. Replace <name> with the @Named value (uppercased, hyphens/dots replaced with underscores).", "s"));

        content.add(envTable(
                "Authentication Type",
                new String[][]{
                        {"REST_CLIENT_AUTH_TYPE_<name>", "Override auth type (None, Bearer, JWT, Basic, ApiKey, OAuth2, MutualTLS)", "None"},
                }
        ));

        content.add(envTable(
                "Bearer / JWT Security",
                new String[][]{
                        {"REST_CLIENT_TOKEN_<name>", "Override Bearer/JWT token", "—"},
                }
        ));

        content.add(envTable(
                "Basic Auth Security",
                new String[][]{
                        {"REST_CLIENT_USERNAME_<name>", "Override Basic username", "—"},
                        {"REST_CLIENT_PASSWORD_<name>", "Override Basic password", "—"},
                }
        ));

        content.add(envTable(
                "API Key Security",
                new String[][]{
                        {"REST_CLIENT_API_KEY_<name>", "Override API key value", "—"},
                        {"REST_CLIENT_API_KEY_HEADER_<name>", "Override API key header name", "X-API-Key"},
                }
        ));

        content.add(envTable(
                "OAuth2 / OIDC Security",
                new String[][]{
                        {"REST_CLIENT_OAUTH2_TOKEN_URL_<name>", "OAuth2 token endpoint URL", "—"},
                        {"REST_CLIENT_OAUTH2_CLIENT_ID_<name>", "OAuth2 client ID", "—"},
                        {"REST_CLIENT_OAUTH2_CLIENT_SECRET_<name>", "OAuth2 client secret", "—"},
                        {"REST_CLIENT_OAUTH2_SCOPES_<name>", "OAuth2 scopes (space-separated)", "—"},
                        {"REST_CLIENT_OAUTH2_GRANT_TYPE_<name>", "OAuth2 grant type", "client_credentials"},
                }
        ));

        content.add(envTable(
                "Mutual TLS (mTLS) Security",
                new String[][]{
                        {"REST_CLIENT_CLIENT_CERT_PATH_<name>", "Client certificate keystore path", "—"},
                        {"REST_CLIENT_CLIENT_CERT_PASSWORD_<name>", "Client certificate keystore password", "—"},
                        {"REST_CLIENT_CLIENT_CERT_TYPE_<name>", "Client certificate keystore type (JKS, PKCS12)", "PKCS12"},
                }
        ));

        content.add(envTable(
                "Connection Overrides",
                new String[][]{
                        {"REST_CLIENT_URL_<name>", "Override endpoint URL", "—"},
                        {"REST_CLIENT_METHOD_<name>", "Override HTTP method", "—"},
                        {"REST_CLIENT_PROTOCOL_<name>", "Override protocol (HTTP / HTTP2)", "—"},
                        {"REST_CLIENT_CONTENT_TYPE_<name>", "Override default content type", "application/json"},
                        {"REST_CLIENT_ACCEPT_<name>", "Override default Accept header", "application/json"},
                        {"REST_CLIENT_CONNECT_TIMEOUT_<name>", "Override connect timeout (ms)", "0"},
                        {"REST_CLIENT_IDLE_TIMEOUT_<name>", "Override idle timeout (ms)", "0"},
                        {"REST_CLIENT_READ_TIMEOUT_<name>", "Override read/response timeout (ms)", "0"},
                        {"REST_CLIENT_TRUST_ALL_<name>", "Override trust-all flag (dev only)", "false"},
                        {"REST_CLIENT_VERIFY_HOST_<name>", "Override hostname verification", "true"},
                        {"REST_CLIENT_SSL_<name>", "Override SSL flag", "false"},
                        {"REST_CLIENT_MAX_POOL_SIZE_<name>", "Override connection pool size", "0"},
                        {"REST_CLIENT_KEEP_ALIVE_<name>", "Override keep-alive flag", "true"},
                        {"REST_CLIENT_DECOMPRESSION_<name>", "Override response decompression", "true"},
                        {"REST_CLIENT_FOLLOW_REDIRECTS_<name>", "Override follow-redirects flag", "true"},
                        {"REST_CLIENT_MAX_REDIRECTS_<name>", "Override max redirect hops", "0"},
                        {"REST_CLIENT_USER_AGENT_<name>", "Override User-Agent header", "GuicedEE-RestClient/2.0"},
                        {"REST_CLIENT_PIPELINING_<name>", "Enable HTTP pipelining", "false"},
                        {"REST_CLIENT_MAX_WAIT_QUEUE_SIZE_<name>", "Max queued requests when pool is full", "0"},
                        {"REST_CLIENT_MAX_WS_FRAME_SIZE_<name>", "Max WebSocket frame size (bytes)", "0"},
                }
        ));

        content.add(envTable(
                "Resilience",
                new String[][]{
                        {"REST_CLIENT_RETRY_ATTEMPTS_<name>", "Number of retry attempts on failure (0 = none)", "0"},
                        {"REST_CLIENT_RETRY_DELAY_<name>", "Delay between retries (ms)", "1000"},
                }
        ));

        return section("REST Client", "GuicedEE REST Client",
                "Annotation-driven REST client with Vert.x WebClient.", content);
    }

    // ── REST CORS ───────────────────────────────────────

    private WaStack<?> buildRestCorsSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "CORS Configuration",
                new String[][]{
                        {"REST_CORS_ENABLED", "Enable CORS handler on REST endpoints", "false"},
                        {"REST_CORS_ALLOWED_ORIGINS", "Comma-separated allowed origins", "*"},
                        {"REST_CORS_ALLOWED_METHODS", "Comma-separated allowed HTTP methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS,HEAD"},
                        {"REST_CORS_ALLOWED_HEADERS", "Comma-separated allowed request headers", "Content-Type,Authorization,..."},
                        {"REST_CORS_ALLOW_CREDENTIALS", "Allow credentials in CORS requests", "true"},
                        {"REST_CORS_MAX_AGE", "Preflight cache max age (seconds)", "3600"},
                }
        ));

        return section("REST CORS", "GuicedEE REST — CORS",
                "Cross-Origin Resource Sharing configuration for Jakarta REST endpoints.", content);
    }

    // ── Auth ────────────────────────────────────────────

    private WaStack<?> buildAuthSection()
    {
        return configurationSection("Authentication & Authorization", "Providers require their class/package annotation and optional Vert.x auth dependency. Duplicate global configurations fail explicitly. Provider leeway inherits the global value only when set to -1; zero overrides it. OAuth request scopes and redirect URI apply only when omitted by the caller.");
    }

    // ── Health ───────────────────────────────────────────

    private WaStack<?> buildHealthSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Health Endpoints",
                new String[][]{
                        {"HEALTH_ENABLED", "Enable health endpoints", "true"},
                        {"HEALTH_PATH", "Root health endpoint path", "/health"},
                        {"HEALTH_LIVENESS_PATH", "Liveness probe path", "/health/live"},
                        {"HEALTH_READINESS_PATH", "Readiness probe path", "/health/ready"},
                        {"HEALTH_STARTUP_PATH", "Startup probe path", "/health/started"},
                        {"HEALTH_DOWNSTREAM_ENABLED", "Enable downstream service health aggregation in readiness checks", "true"},
                }
        ));

        return section("Health Checks", "GuicedEE Health",
                "MicroProfile Health integration with Vert.x — includes downstream health aggregation.", content);
    }

    // ── Metrics ─────────────────────────────────────────

    private WaStack<?> buildMetricsSection()
    {
        return configurationSection("Metrics", "Environment-only configuration is supported. Prometheus port -1 shares the application listener; a non-negative port creates an isolated lifecycle-owned listener. Exporters require their enabled flags.");
    }

    // ── Telemetry ───────────────────────────────────────

    private WaStack<?> buildTelemetrySection()
    {
        return configurationSection("Telemetry", "Environment-only configuration is supported. TelemetryOptions takes priority over the Telemetry alias. OTEL_EXPORTER_OTLP signal keys precede TELEMETRY signal keys, then the shared endpoint. Full signal endpoints are used verbatim.");
    }

    // ── RabbitMQ ────────────────────────────────────────

    private WaStack<?> buildRabbitMQSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(bodyText("Variables are scoped by name. Replace {name} with the connection/exchange/queue name "
                + "(uppercased, hyphens/dots replaced with underscores). Falls back to global key without name prefix.", "s"));

        content.add(envTable(
                "Connection (@RabbitConnectionOptions)",
                new String[][]{
                        {"RABBITMQ_{name}_HOST / RABBITMQ_HOST", "RabbitMQ host", "localhost"},
                        {"RABBITMQ_{name}_PORT / RABBITMQ_PORT", "RabbitMQ port", "5672"},
                        {"RABBITMQ_{name}_USER / RABBITMQ_USER", "Username", "guest"},
                        {"RABBITMQ_{name}_PASSWORD / RABBITMQ_PASSWORD", "Password", "guest"},
                        {"RABBITMQ_{name}_URI / RABBITMQ_URI", "Full AMQP URI", "—"},
                        {"RABBITMQ_{name}_VIRTUAL_HOST / RABBITMQ_VIRTUAL_HOST", "Virtual host", "/"},
                        {"RABBITMQ_{name}_ADDRESSES / RABBITMQ_ADDRESSES", "Comma-separated host:port pairs", "—"},
                        {"RABBITMQ_{name}_CONNECTION_TIMEOUT", "Connection timeout (ms)", "—"},
                        {"RABBITMQ_{name}_REQUESTED_HEARTBEAT", "Requested heartbeat (seconds)", "—"},
                        {"RABBITMQ_{name}_HANDSHAKE_TIMEOUT", "Handshake timeout (ms)", "—"},
                        {"RABBITMQ_{name}_REQUESTED_CHANNEL_MAX", "Max channels per connection", "—"},
                        {"RABBITMQ_{name}_NETWORK_RECOVERY_INTERVAL", "Network recovery interval (ms)", "—"},
                        {"RABBITMQ_{name}_AUTOMATIC_RECOVERY_ENABLED", "Enable automatic recovery", "—"},
                        {"RABBITMQ_{name}_RECONNECT_ATTEMPTS", "Max reconnect attempts", "—"},
                        {"RABBITMQ_{name}_RECONNECT_INTERVAL", "Reconnect interval (ms)", "—"},
                        {"RABBITMQ_{name}_USE_NIO", "Use NIO transport", "—"},
                        {"RABBITMQ_{name}_CONFIRM_PUBLISHES", "Enable publisher confirms", "—"},
                }
        ));

        content.add(envTable(
                "Exchange (@QueueExchange)",
                new String[][]{
                        {"RABBITMQ_{name}_EXCHANGE_TYPE", "Exchange type (Direct, Fanout, Topic, Headers)", "—"},
                        {"RABBITMQ_{name}_EXCHANGE_DURABLE", "Exchange durability", "—"},
                        {"RABBITMQ_{name}_EXCHANGE_AUTO_DELETE", "Auto-delete exchange", "—"},
                        {"RABBITMQ_{name}_EXCHANGE_CREATE_DEAD_LETTER", "Create dead-letter exchange", "—"},
                }
        ));

        content.add(envTable(
                "Queue Options (@QueueOptions)",
                new String[][]{
                        {"RABBITMQ_{name}_QUEUE_PRIORITY", "Queue priority", "—"},
                        {"RABBITMQ_{name}_QUEUE_FETCH_COUNT", "Prefetch count", "—"},
                        {"RABBITMQ_{name}_QUEUE_DURABLE", "Queue durability", "—"},
                        {"RABBITMQ_{name}_QUEUE_AUTO_ACK", "Auto-acknowledge messages", "—"},
                        {"RABBITMQ_{name}_QUEUE_TTL", "Message TTL (ms)", "—"},
                        {"RABBITMQ_{name}_QUEUE_SINGLE_CONSUMER", "Single consumer mode", "—"},
                        {"RABBITMQ_{name}_QUEUE_CONSUMER_EXCLUSIVE", "Exclusive consumer", "—"},
                        {"RABBITMQ_{name}_QUEUE_TRANSACTED", "Transacted channel", "—"},
                        {"RABBITMQ_{name}_QUEUE_CONSUMER_COUNT", "Consumer count", "—"},
                        {"RABBITMQ_{name}_QUEUE_DEDICATED_CHANNEL", "Dedicated channel per consumer", "—"},
                        {"RABBITMQ_{name}_QUEUE_DEDICATED_CONNECTION", "Dedicated connection per consumer", "—"},
                }
        ));

        content.add(envTable(
                "Module-Level",
                new String[][]{
                        {"RABBIT_MQ_PORT", "Global port override at module level", "5672"},
                }
        ));

        return section("RabbitMQ", "GuicedEE RabbitMQ",
                "Annotation-driven RabbitMQ messaging with scoped env var overrides.", content);
    }

    // ── GraphQL ─────────────────────────────────────────

    private WaStack<?> buildGraphQLSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "GraphQL Configuration",
                new String[][]{
                        {"GRAPHQL_HTTP_PATH", "HTTP endpoint path", "/graphql"},
                        {"GRAPHQL_WS_ENABLED", "Enable GraphQL over WebSocket", "true"},
                        {"GRAPHQL_WS_PATH", "WebSocket endpoint path", "/graphql"},
                        {"GRAPHIQL_ENABLED", "Enable GraphiQL IDE", "false"},
                        {"GRAPHIQL_PATH", "GraphiQL mount path", "/graphiql"},
                        {"GRAPHQL_BATCHING_ENABLED", "Enable query batching", "false"},
                        {"GRAPHQL_UPLOADS_ENABLED", "Enable multipart file uploads", "false"},
                }
        ));

        content.add(envTable(
                "GraphQL Gateway (Schema Stitching)",
                new String[][]{
                        {"GRAPHQL_GATEWAY_ENABLED", "Enable remote schema stitching from service registry", "false"},
                        {"GRAPHQL_GATEWAY_ENVIRONMENT", "Only merge services allowed in this environment (e.g. dev, int, prod)", "(empty = all)"},
                        {"GRAPHQL_GATEWAY_TIMEOUT_MS", "Introspection timeout per remote service in milliseconds", "10000"},
                }
        ));

        return section("graphql", "GraphQL",
                "GraphQL integration with Vert.x Web GraphQL.", content);
    }

    // ── Kafka ───────────────────────────────────────────

    private WaStack<?> buildKafkaSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(bodyText("Variables are scoped by name. Replace {name} with the connection or topic name "
                + "(uppercased, hyphens/dots replaced with underscores). Falls back to global KAFKA_ prefix.", "s"));

        content.add(envTable(
                "Connection (@KafkaConnectionOptions)",
                new String[][]{
                        {"KAFKA_{name}_BOOTSTRAP_SERVERS / KAFKA_BOOTSTRAP_SERVERS", "Kafka bootstrap servers", "localhost:9092"},
                        {"KAFKA_{name}_GROUP_ID / KAFKA_GROUP_ID", "Consumer group ID", "—"},
                        {"KAFKA_{name}_CLIENT_ID / KAFKA_CLIENT_ID", "Client ID", "—"},
                        {"KAFKA_{name}_KEY_DESERIALIZER / KAFKA_KEY_DESERIALIZER", "Key deserializer class", "StringDeserializer"},
                        {"KAFKA_{name}_VALUE_DESERIALIZER / KAFKA_VALUE_DESERIALIZER", "Value deserializer class", "StringDeserializer"},
                        {"KAFKA_{name}_KEY_SERIALIZER / KAFKA_KEY_SERIALIZER", "Key serializer class", "StringSerializer"},
                        {"KAFKA_{name}_VALUE_SERIALIZER / KAFKA_VALUE_SERIALIZER", "Value serializer class", "StringSerializer"},
                        {"KAFKA_{name}_AUTO_OFFSET_RESET / KAFKA_AUTO_OFFSET_RESET", "Auto offset reset policy", "latest"},
                        {"KAFKA_{name}_ENABLE_AUTO_COMMIT / KAFKA_ENABLE_AUTO_COMMIT", "Enable auto commit", "true"},
                        {"KAFKA_{name}_AUTO_COMMIT_INTERVAL_MS / KAFKA_AUTO_COMMIT_INTERVAL_MS", "Auto commit interval (ms)", "5000"},
                        {"KAFKA_{name}_ACKS / KAFKA_ACKS", "Producer acknowledgment mode", "all"},
                        {"KAFKA_{name}_RETRIES / KAFKA_RETRIES", "Producer retry count", "0"},
                        {"KAFKA_{name}_LINGER_MS / KAFKA_LINGER_MS", "Producer linger time (ms)", "0"},
                        {"KAFKA_{name}_BATCH_SIZE / KAFKA_BATCH_SIZE", "Producer batch size (bytes)", "16384"},
                        {"KAFKA_{name}_BUFFER_MEMORY / KAFKA_BUFFER_MEMORY", "Producer buffer memory (bytes)", "33554432"},
                        {"KAFKA_{name}_REQUEST_TIMEOUT_MS / KAFKA_REQUEST_TIMEOUT_MS", "Request timeout (ms)", "30000"},
                        {"KAFKA_{name}_SESSION_TIMEOUT_MS / KAFKA_SESSION_TIMEOUT_MS", "Session timeout (ms)", "10000"},
                        {"KAFKA_{name}_MAX_POLL_RECORDS / KAFKA_MAX_POLL_RECORDS", "Max poll records per fetch", "500"},
                }
        ));

        content.add(envTable(
                "Topic Options",
                new String[][]{
                        {"KAFKA_{name}_TOPIC_NAME", "Override topic name", "—"},
                        {"KAFKA_{name}_TOPIC_AUTO_COMMIT", "Auto-commit for topic consumer", "—"},
                        {"KAFKA_{name}_TOPIC_WORKER", "Use worker thread for consumer", "false"},
                        {"KAFKA_{name}_TOPIC_CONSUMER_COUNT", "Number of consumers for topic", "1"},
                        {"KAFKA_{name}_TOPIC_PARTITION", "Partition assignment", "-1"},
                        {"KAFKA_{name}_TOPIC_MAX_POLL_INTERVAL_MS", "Max poll interval (ms)", "—"},
                        {"KAFKA_{name}_TOPIC_PAUSE_ON_START", "Pause consumer on startup", "false"},
                }
        ));

        content.add(envTable(
                "Topic Creation (@KafkaTopicCreate)",
                new String[][]{
                        {"KAFKA_{name}_TOPIC_CREATE_NAME", "Override topic name for creation", "—"},
                        {"KAFKA_{name}_TOPIC_CREATE_PARTITIONS", "Number of partitions", "1"},
                        {"KAFKA_{name}_TOPIC_CREATE_REPLICATION_FACTOR", "Replication factor", "1"},
                        {"KAFKA_{name}_TOPIC_CREATE_IGNORE_IF_EXISTS", "Ignore errors if topic already exists", "true"},
                }
        ));

        return section("Kafka", "GuicedEE Kafka",
                "Annotation-driven Kafka messaging with scoped env var overrides.", content);
    }

    // ── IBM MQ ──────────────────────────────────────────

    private WaStack<?> buildIbmMqSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(bodyText("Variables are scoped by name. Replace {name} with the connection or queue name "
                + "(uppercased, hyphens/dots replaced with underscores). Falls back to global IBMMQ_ prefix.", "s"));

        content.add(envTable(
                "Connection (@IBMMQConnectionOptions)",
                new String[][]{
                        {"IBMMQ_{name}_HOST / IBMMQ_HOST", "IBM MQ host", "localhost"},
                        {"IBMMQ_{name}_PORT / IBMMQ_PORT", "IBM MQ listener port", "1414"},
                        {"IBMMQ_{name}_QUEUE_MANAGER / IBMMQ_QUEUE_MANAGER", "Queue manager name", "—"},
                        {"IBMMQ_{name}_CHANNEL / IBMMQ_CHANNEL", "Server connection channel", "—"},
                        {"IBMMQ_{name}_USER / IBMMQ_USER", "Username", "—"},
                        {"IBMMQ_{name}_PASSWORD / IBMMQ_PASSWORD", "Password", "—"},
                        {"IBMMQ_{name}_APPLICATION_NAME / IBMMQ_APPLICATION_NAME", "Application name", "—"},
                        {"IBMMQ_{name}_TRANSPORT_TYPE / IBMMQ_TRANSPORT_TYPE", "Transport type", "MQC.TRANSPORT_MQSERIES_CLIENT"},
                        {"IBMMQ_{name}_SSL_ENABLED / IBMMQ_SSL_ENABLED", "Enable SSL", "false"},
                        {"IBMMQ_{name}_SSL_CIPHER_SUITE / IBMMQ_SSL_CIPHER_SUITE", "SSL cipher suite", "—"},
                        {"IBMMQ_{name}_SSL_PEER_NAME / IBMMQ_SSL_PEER_NAME", "SSL peer name", "—"},
                        {"IBMMQ_{name}_CONNECTION_TIMEOUT / IBMMQ_CONNECTION_TIMEOUT", "Connection timeout (ms)", "—"},
                        {"IBMMQ_{name}_CCSID / IBMMQ_CCSID", "Coded character set ID", "—"},
                        {"IBMMQ_{name}_RECONNECT_ATTEMPTS / IBMMQ_RECONNECT_ATTEMPTS", "Max reconnect attempts", "—"},
                        {"IBMMQ_{name}_RECONNECT_INTERVAL_MS / IBMMQ_RECONNECT_INTERVAL_MS", "Reconnect interval (ms)", "—"},
                        {"IBMMQ_{name}_CLIENT_ID / IBMMQ_CLIENT_ID", "Client ID", "—"},
                }
        ));

        content.add(envTable(
                "Queue Options",
                new String[][]{
                        {"IBMMQ_{name}_QUEUE_NAME", "Override queue name", "—"},
                        {"IBMMQ_{name}_QUEUE_CONNECTION", "Override queue connection name", "—"},
                        {"IBMMQ_{name}_QUEUE_AUTO_ACK", "Auto-acknowledge messages", "—"},
                        {"IBMMQ_{name}_QUEUE_WORKER", "Use worker thread for consumer", "false"},
                        {"IBMMQ_{name}_QUEUE_CONSUMER_COUNT", "Number of consumers", "1"},
                        {"IBMMQ_{name}_QUEUE_TRANSACTED", "Transacted session", "false"},
                        {"IBMMQ_{name}_QUEUE_AUTOBIND", "Auto-bind queue", "—"},
                        {"IBMMQ_{name}_QUEUE_RECEIVE_TIMEOUT_MS", "Receive timeout (ms)", "—"},
                        {"IBMMQ_{name}_QUEUE_TOPIC", "Treat as topic subscription", "false"},
                        {"IBMMQ_{name}_QUEUE_DURABLE_SUBSCRIPTION", "Durable subscription name", "—"},
                        {"IBMMQ_{name}_QUEUE_MESSAGE_SELECTOR", "JMS message selector", "—"},
                        {"IBMMQ_{name}_QUEUE_PERSISTENCE", "Message persistence level", "—"},
                        {"IBMMQ_{name}_QUEUE_PRIORITY", "Message priority", "—"},
                        {"IBMMQ_{name}_QUEUE_TIME_TO_LIVE", "Message time-to-live (ms)", "—"},
                }
        ));

        return section("IBM MQ", "GuicedEE IBM MQ",
                "Annotation-driven IBM MQ messaging with scoped env var overrides.", content);
    }

    // ── Persistence ─────────────────────────────────────

    private WaStack<?> buildPersistenceSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(bodyText("Persistence uses ${VAR_NAME} or ${VAR_NAME:default} placeholders directly in persistence.xml properties. "
                + "Any environment variable can be referenced. Common variables used in persistence.xml:", "s"));

        content.add(envTable(
                "persistence.xml Placeholders",
                new String[][]{
                        {"DB_URL", "JDBC connection URL", "—"},
                        {"DB_USER", "Database username", "—"},
                        {"DB_PASSWORD", "Database password", "—"},
                }
        ));

        return section("Persistence", "GuicedEE Persistence",
                "Reactive JPA persistence with Hibernate Reactive. Uses ${VAR} placeholders in persistence.xml.", content);
    }

    // ── Web Services ────────────────────────────────────

    private WaStack<?> buildWebServicesSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(bodyText("Web Services uses standard environment variables via System.getenv() following Apache CXF conventions.", "s"));

        content.add(envTable(
                "SOAP / CXF Configuration",
                new String[][]{
                        {"WS_BASE_ADDRESS", "Base HTTP address for SOAP services", "http://0.0.0.0:8080"},
                        {"WS_CONTEXT_PATH", "Root path for SOAP services", "/services"},
                        {"WS_SOAP_VERSION", "SOAP version (1.1 or 1.2)", "1.1"},
                        {"WS_LOGGING_ENABLED", "Enable CXF logging interceptors", "true"},
                        {"WS_MATOM_ENABLED", "Enable MTOM attachments", "false"},
                        {"WS_WSDL_LOCATION", "WSDL file location (for WSDL-first)", "—"},
                        {"WS_SECURITY_POLICY", "WS-Security policy file path", "—"},
                }
        ));

        return section("Web Services", "GuicedEE Web Services",
                "SOAP web services configuration using Apache CXF conventions.", content);
    }

    // ── Service Discovery ─────────────────────────────────────────────

    private WaStack<?> buildServiceDiscoverySection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Service Resolver",
                new String[][]{
                        {"SERVICE_RESOLVER_TYPE", "Resolver type: kubernetes, kube, srv, dns, or empty to disable", "(disabled)"},
                        {"KUBERNETES_SERVICE_HOST", "Kubernetes API host", "from pod env"},
                        {"KUBERNETES_SERVICE_PORT", "Kubernetes API port", "443"},
                        {"KUBERNETES_NAMESPACE", "Kubernetes namespace", "from pod env"},
                        {"SRV_DNS_SERVER", "DNS server for SRV resolution", "(system default)"},
                        {"SRV_DNS_PORT", "DNS server port", "53"},
                }
        ));

        return section("Service Discovery", "GuicedEE Service Discovery",
                "Vert.x Service Resolver integration with Kubernetes and DNS SRV backends.", content);
    }

    // ── Service Registry ───────────────────────────────────

    private WaStack<?> buildServiceRegistrySection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Service Registry",
                new String[][]{
                        {"CONTAINER_APP_ENV_DNS_SUFFIX", "Azure Container Apps DNS suffix for auto-URL construction", "(from Azure runtime)"},
                        {"SERVICE_REGISTRY_DNS_SUFFIX", "Fallback DNS suffix when no cloud provider is detected", "(empty)"},
                        {"SERVICE_REGISTRY_HEALTH_PATH", "Default service health path; overrides ServiceRegistryOptions.healthPath", "/health/ready"},
                        {"HEALTH_DOWNSTREAM_ENABLED", "Enable downstream service health aggregation in readiness checks", "true"},
                }
        ));

        return section("Service Registry", "GuicedEE Service Registry",
                "Named service registry with health-aware resolution, status change events, "
                + "exponential backoff, and graceful shutdown.", content);
    }

    // ── Consul ─────────────────────────────────────────────

    private WaStack<?> buildConsulSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Consul Connection",
                new String[][]{
                        {"CONSUL_HOST", "Consul agent host (global fallback)", "localhost"},
                        {"CONSUL_PORT", "Consul agent HTTP port (global fallback)", "8500"},
                        {"CONSUL_SSL", "Use SSL for Consul connection (global fallback)", "false"},
                        {"CONSUL_TOKEN", "Consul ACL token (global fallback)", "(empty)"},
                        {"CONSUL_DATACENTER", "Consul datacenter (global fallback)", "(empty)"},
                        {"CONSUL_{NAME}_HOST", "Named Consul host (overrides global)", "localhost"},
                        {"CONSUL_{NAME}_PORT", "Named Consul port (overrides global)", "8500"},
                        {"CONSUL_{NAME}_SSL", "Named Consul SSL (overrides global)", "false"},
                        {"CONSUL_{NAME}_TOKEN", "Named Consul ACL token (overrides global)", "(empty)"},
                        {"CONSUL_{NAME}_DATACENTER", "Named Consul datacenter (overrides global)", "(empty)"},
                }
        ));

        content.add(envTable(
                "Consul Service Resolver",
                new String[][]{
                        {"CONSUL_RESOLVER_HOST", "Consul host for service resolver", "(from @ConsulOptions or localhost)"},
                        {"CONSUL_RESOLVER_PORT", "Consul port for service resolver", "(from @ConsulOptions or 8500)"},
                        {"CONSUL_RESOLVER_PASSING_ONLY", "Only resolve healthy/passing instances", "true"},
                }
        ));

        return section("Consul", "GuicedEE Consul",
                "HashiCorp Consul integration — service registration, health checks, "
                + "KV config, and Consul-based address resolution.", content);
    }

    // ── MCP ─────────────────────────────────────────────

    private WaStack<?> buildMcpSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "MCP Server Configuration",
                new String[][]{
                        {"MCP_PROTOCOL_REVISION", "MCP protocol revision target", "2025-11-25"},
                        {"MCP_TRANSPORT_MODE", "Transport mode (http, stdio, dual)", "http"},
                        {"MCP_STATEFUL_SESSIONS", "Enable stateful sessions", "true"},
                        {"MCP_HTTP_HOST", "HTTP listen host", "0.0.0.0"},
                        {"MCP_HTTP_PORT", "HTTP listen port", "8080"},
                        {"MCP_MAX_REQUEST_BYTES", "Maximum request body size (bytes)", "1048576"},
                        {"MCP_RATE_LIMIT_PER_MINUTE", "Rate limit per minute per client", "120"},
                        {"MCP_SESSION_TTL_SECONDS", "Session time-to-live (seconds)", "900"},
                        {"MCP_REQUEST_TIMEOUT_MS", "Request timeout (ms)", "10000"},
                        {"MCP_SSE_HEARTBEAT_SECONDS", "SSE heartbeat interval (seconds)", "15"},
                        {"MCP_HTTP_ALLOWED_HOSTS", "Comma-separated allowed hosts", "localhost,127.0.0.1"},
                        {"MCP_HTTP_ALLOWED_ORIGINS", "Comma-separated allowed CORS origins", "—"},
                        {"MCP_CURSOR_SECRET", "Secret for cursor signing", "dev-cursor-secret-change-me"},
                        {"MCP_MAX_LIST_LIMIT", "Maximum items per list response", "50"},
                }
        ));

        return section("MCP", "GuicedEE MCP Server",
                "Model Context Protocol server configuration for AI tool integrations.", content);
    }

    // ── Table helper ────────────────────────────────────

    /**
     * Keeps reference content in semantic HTML, with compact rows and optional wrapping.
     */
    private DivSimple<?> envTable(String title, String[][] rows, String... headings)
    {
        var wrapper = new WaStack<>();
        wrapper.setGap(PageSize.Small);
        wrapper.addClass("environment-table");
        String tableId = "environment-table-" + ++tableSequence;
        String label = title == null || title.isBlank() ? "Configuration variables" : title;
        wrapper.addAttribute("[class.table-wrapped]", "wrappedTables['" + tableId + "']");
        var toolbar = new WaCluster<>();
        toolbar.addClass("environment-table-toolbar");
        if (title != null && !title.isBlank()) toolbar.add(headingText("h4", "s", title));
        var wrap = new WaSwitch<>();
        wrap.setSize(Size.Small);
        wrap.setText("Wrap text");
        wrap.addAttribute("aria-label", "Wrap text in " + label);
        wrap.addAttribute("aria-controls", tableId);
        wrap.addAttribute("title", "Show complete cell values on multiple lines");
        wrap.addAttribute("[checked]", "!!wrappedTables['" + tableId + "']");
        wrap.addAttribute("(wa-change)", "setTableWrap('" + tableId + "', $event)");
        toolbar.add(wrap);
        wrapper.add(toolbar);
        if (headings.length == 0) headings = new String[]{"Variable", "Purpose", "Default"};
        var tableDiv = new DivSimple<>();
        tableDiv.addClass("environment-table-scroll");
        tableDiv.addAttribute("id", tableId);
        tableDiv.addAttribute("role", "region");
        tableDiv.addAttribute("aria-label", label);
        tableDiv.addAttribute("tabindex", "0");
        var html = new StringBuilder("<table aria-label=\"").append(escapeAngular(label)).append("\"");
        if (headings.length == 6) html.append(" class=\"environment-contract-table\"");
        html.append(">");
        if (headings.length == 6) {
            html.append("<colgroup>");
            for (int width : new int[]{22, 25, 8, 11, 11, 23}) html.append("<col style=\"width:").append(width).append("%\">");
            html.append("</colgroup>");
        }
        html.append("<thead><tr>");
        for (String heading : headings) html.append("<th scope=\"col\">").append(escapeAngular(heading)).append("</th>");
        html.append("</tr></thead><tbody>");
        for (String[] row : rows) {
            html.append("<tr>");
            for (String value : row) html.append("<td><span title=\"").append(escapeAngular(value)).append("\">")
                    .append(escapeAngular(value)).append("</span></td>");
            html.append("</tr>");
        }
        tableDiv.setText(html.append("</tbody></table>").toString());
        wrapper.add(tableDiv);
        return wrapper;
    }
}
