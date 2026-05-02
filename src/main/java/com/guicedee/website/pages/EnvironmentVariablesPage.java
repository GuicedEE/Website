package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.card.WaCard;

@NgComponent("guicedee-environment-variables")
@NgRoutable(path = "environment-variables")
public class EnvironmentVariablesPage extends WebsitePage<EnvironmentVariablesPage> implements INgComponent<EnvironmentVariablesPage>
{
    public EnvironmentVariablesPage()
    {
        renderContent();
    }

    private void renderContent()
    {
        var layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        // Intro
        var introContent = new WaStack<>();
        introContent.setGap(PageSize.Medium);
        introContent.add(headingText("h1", "xl", "Environment Variables"));
        var intro = bodyText("Every GuicedEE module supports environment variable overrides for deploy-time configuration. "
                + "This page is a comprehensive reference of all available environment variables across the GuicedEE ecosystem.", "l");
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

        layout.add(buildInjectSection());
        layout.add(buildJsonSection());
        layout.add(buildWebSection());
        layout.add(buildVertxSection());
        layout.add(buildVertxEventBusSection());
        layout.add(buildVertxFileSystemSection());
        layout.add(buildVertxAddressResolverSection());
        layout.add(buildRestClientSection());
        layout.add(buildRestCorsSection());
        layout.add(buildAuthSection());
        layout.add(buildHealthSection());
        layout.add(buildMetricsSection());
        layout.add(buildTelemetrySection());
        layout.add(buildRabbitMQSection());
        layout.add(buildKafkaSection());
        layout.add(buildIbmMqSection());
        layout.add(buildPersistenceSection());
        layout.add(buildWebServicesSection());
        layout.add(buildMcpSection());
    }

    // ── Inject ──────────────────────────────────────────

    private WaStack<?> buildInjectSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Logging & Runtime",
                new String[][]{
                        {"GUICEDEE_LOG_LEVEL", "Log level for GuicedEE internals (also: guicedee.log.level)", "INFO"},
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
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "HTTP / HTTPS Server",
                new String[][]{
                        {"HTTP_ENABLED", "Enable the HTTP server", "true"},
                        {"HTTP_PORT", "HTTP listen port", "8080"},
                        {"HTTP_HOST", "HTTP listen host / bind address", "0.0.0.0"},
                        {"HTTP_COMPRESSION_ENABLED", "Enable response compression (gzip/deflate)", "true"},
                        {"HTTP_COMPRESSION_LEVEL", "Compression level (1–9)", "9"},
                        {"HTTP_DECOMPRESSION_ENABLED", "Enable request decompression", "false"},
                        {"HTTP_TCP_KEEP_ALIVE", "Enable TCP keep-alive", "true"},
                        {"HTTP_IDLE_TIMEOUT", "Connection idle timeout in seconds (0 = disabled)", "0"},
                        {"HTTP_MAX_HEADER_SIZE", "Maximum HTTP header size (bytes)", "65536"},
                        {"HTTP_MAX_CHUNK_SIZE", "Maximum HTTP chunk size (bytes)", "65536"},
                        {"HTTP_MAX_INITIAL_LINE_LENGTH", "Maximum HTTP initial line length (bytes)", "65536"},
                        {"HTTP_MAX_FORM_ATTRIBUTE_SIZE", "Maximum form attribute size (bytes)", "65536"},
                        {"HTTP_MAX_FORM_FIELDS", "Maximum number of form fields (-1 = unlimited)", "-1"},
                        {"VERTX_MAX_BODY_SIZE", "Maximum request body size (bytes)", "524288000"},
                        {"HTTP_UPLOADS_DIRECTORY", "Directory for temporary file uploads", "uploads"},
                        {"HTTP_DELETE_UPLOADS_ON_END", "Delete uploaded files after request completes", "true"},
                        {"HTTP_HANDLE_FILE_UPLOADS", "Enable multipart file upload handling", "true"},
                        {"HTTPS_ENABLED", "Enable the HTTPS server", "false"},
                        {"HTTPS_PORT", "HTTPS listen port", "443"},
                        {"HTTPS_KEYSTORE", "Path to JKS or PKCS#12 keystore", "—"},
                        {"HTTPS_KEYSTORE_TYPE", "Keystore type override (JKS, PKCS12, PFX). Auto-detected from file extension if not set.", "auto"},
                        {"HTTPS_KEYSTORE_PASSWORD", "Keystore password", "—"},
                }
        ));

        return section("HTTP Server", "GuicedEE Web",
                "Reactive HTTP/HTTPS server bootstrap with Vert.x.", content);
    }

    // ── Vert.x ──────────────────────────────────────────

    private WaStack<?> buildVertxSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Vert.x Runtime (@VertX)",
                new String[][]{
                        {"VERTX_EVENT_LOOP_POOL_SIZE", "Event loop thread pool size", "auto"},
                        {"VERTX_WORKER_POOL_SIZE", "Worker thread pool size", "auto"},
                        {"VERTX_BLOCKED_THREAD_CHECK_INTERVAL", "Blocked thread check interval", "auto"},
                        {"VERTX_BLOCKED_THREAD_CHECK_INTERVAL_TIME_UNIT", "Time unit for blocked thread check", "MILLISECONDS"},
                        {"VERTX_MAX_EVENT_LOOP_EXECUTE_TIME", "Max event loop execute time before warning", "auto"},
                        {"VERTX_MAX_EVENT_LOOP_EXECUTE_TIME_UNIT", "Time unit for max event loop execute time", "NANOSECONDS"},
                        {"VERTX_MAX_WORKER_EXECUTE_TIME", "Max worker execute time before warning", "auto"},
                        {"VERTX_MAX_WORKER_EXECUTE_TIME_UNIT", "Time unit for max worker execute time", "NANOSECONDS"},
                        {"VERTX_INTERNAL_BLOCKING_POOL_SIZE", "Internal blocking pool size", "auto"},
                        {"VERTX_HA_ENABLED", "Enable high availability", "false"},
                        {"VERTX_QUORUM_SIZE", "HA quorum size", "1"},
                        {"VERTX_WARNING_EXCEPTION_TIME", "Warning exception time threshold", "auto"},
                        {"VERTX_WARNING_EXCEPTION_TIME_UNIT", "Time unit for warning exception time", "NANOSECONDS"},
                        {"VERTX_PREFER_NATIVE_TRANSPORT", "Prefer native transport (epoll/kqueue)", "false"},
                        {"VERTX_USE_DAEMON_THREADS", "Use daemon threads", "false"},
                        {"VERTX_DISABLE_TCCL", "Disable thread context class loader", "false"},
                        {"VERTX_METRICS_ENABLED", "Enable Vert.x internal metrics", "false"},
                }
        ));

        content.add(envTable(
                "Throttling",
                new String[][]{
                        {"VERTX_PUBLISH_THROTTLE_MS", "Global throttle period (0 = disabled)", "50"},
                        {"VERTX_PUBLISH_THROTTLE_MS_<ADDR>", "Per-address throttle override", "—"},
                        {"VERTX_PUBLISH_QUEUE_WARN", "Backlog warning threshold", "1000"},
                        {"VERTX_PUBLISH_QUEUE_WARN_<ADDR>", "Per-address warning threshold", "—"},
                }
        ));

        content.add(envTable(
                "Consumer Options",
                new String[][]{
                        {"VERTX_EVENT_ADDRESS_<ADDR>", "Override the resolved address", "—"},
                        {"VERTX_EVENT_LOCAL_ONLY", "Force local-only consumers", "false"},
                        {"VERTX_EVENT_CONSUMER_COUNT", "Default consumer count", "—"},
                        {"VERTX_EVENT_WORKER", "Default worker mode", "false"},
                        {"VERTX_EVENT_WORKER_POOL", "Worker pool name", "—"},
                        {"VERTX_EVENT_WORKER_POOL_SIZE", "Worker pool size", "—"},
                        {"VERTX_EVENT_INSTANCES", "Verticle instances per address", "—"},
                        {"VERTX_EVENT_TIMEOUT_MS", "Consumer timeout (ms)", "—"},
                        {"VERTX_EVENT_BATCH_WINDOW_MS", "Batch window (ms)", "—"},
                        {"VERTX_EVENT_BATCH_MAX", "Max batch size", "—"},
                        {"VERTX_EVENT_MAX_BUFFERED_MESSAGES", "Backpressure buffer limit", "—"},
                        {"VERTX_EVENT_RESUME_AT_MESSAGES", "Resume threshold", "—"},
                }
        ));

        return section("Vert.x Runtime & Consumers", "GuicedEE Vert.x",
                "Vert.x runtime configuration, event bus consumers, and publisher throttling.", content);
    }

    // ── Vert.x EventBus ─────────────────────────────────

    private WaStack<?> buildVertxEventBusSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "EventBus Options (@EventBusOptions)",
                new String[][]{
                        {"VERTX_EVENTBUS_CLUSTER_PUBLIC_HOST", "Cluster public host for event bus", "—"},
                        {"VERTX_EVENTBUS_CLUSTER_PUBLIC_PORT", "Cluster public port for event bus", "0"},
                        {"VERTX_EVENTBUS_CLUSTER_PING_INTERVAL", "Cluster ping interval (ms)", "auto"},
                        {"VERTX_EVENTBUS_CLUSTER_PING_REPLY_INTERVAL", "Cluster ping reply interval (ms)", "auto"},
                        {"VERTX_EVENTBUS_HOST", "Event bus listen host", "—"},
                        {"VERTX_EVENTBUS_PORT", "Event bus listen port", "0"},
                        {"VERTX_EVENTBUS_ACCEPT_BACKLOG", "Accept backlog queue size", "-1"},
                        {"VERTX_EVENTBUS_RECONNECT_ATTEMPTS", "Reconnect attempts on failure", "0"},
                        {"VERTX_EVENTBUS_RECONNECT_INTERVAL", "Reconnect interval (ms)", "1000"},
                        {"VERTX_EVENTBUS_CONNECT_TIMEOUT", "Connect timeout (ms)", "60000"},
                        {"VERTX_EVENTBUS_TRUST_ALL", "Trust all certificates on event bus", "false"},
                        {"VERTX_EVENTBUS_CLIENT_AUTH", "Client auth mode (NONE, REQUEST, REQUIRED)", "NONE"},
                }
        ));

        return section("EventBus", "Vert.x EventBus Options",
                "Clustered event bus networking and reconnection configuration.", content);
    }

    // ── Vert.x FileSystem ───────────────────────────────

    private WaStack<?> buildVertxFileSystemSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "FileSystem Options (@FileSystemOptions)",
                new String[][]{
                        {"VERTX_FILESYSTEM_CLASSPATH_RESOLVING", "Enable classpath resource resolving", "true"},
                        {"VERTX_FILESYSTEM_FILE_CACHING", "Enable file caching", "true"},
                        {"VERTX_FILESYSTEM_FILE_CACHE_DIR", "Directory for file cache", "—"},
                }
        ));

        return section("FileSystem", "Vert.x FileSystem Options",
                "Classpath resolving and file caching configuration.", content);
    }

    // ── Vert.x Address Resolver ─────────────────────────

    private WaStack<?> buildVertxAddressResolverSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Address Resolver Options (@AddressResolverOptions)",
                new String[][]{
                        {"VERTX_ADDR_RESOLVER_HOSTS_PATH", "Custom hosts file path", "—"},
                        {"VERTX_ADDR_RESOLVER_HOSTS_REFRESH_PERIOD", "Hosts file refresh period (ms)", "—"},
                        {"VERTX_ADDR_RESOLVER_SERVERS", "Comma-separated DNS server addresses", "—"},
                        {"VERTX_ADDR_RESOLVER_ROTATE_SERVERS", "Rotate DNS servers on failure", "false"},
                        {"VERTX_ADDR_RESOLVER_CACHE_MIN_TTL", "Minimum DNS cache TTL (seconds)", "0"},
                        {"VERTX_ADDR_RESOLVER_CACHE_MAX_TTL", "Maximum DNS cache TTL (seconds)", "auto"},
                        {"VERTX_ADDR_RESOLVER_CACHE_NEGATIVE_TTL", "Negative DNS cache TTL (seconds)", "0"},
                        {"VERTX_ADDR_RESOLVER_QUERY_TIMEOUT", "DNS query timeout (ms)", "5000"},
                        {"VERTX_ADDR_RESOLVER_MAX_QUERIES", "Max DNS queries per lookup", "4"},
                        {"VERTX_ADDR_RESOLVER_RD_FLAG", "Enable recursion desired flag", "true"},
                        {"VERTX_ADDR_RESOLVER_SEARCH_DOMAINS", "Comma-separated DNS search domains", "—"},
                        {"VERTX_ADDR_RESOLVER_NDOTS", "ndots option for DNS search", "auto"},
                        {"VERTX_ADDR_RESOLVER_OPT_RESOURCE_ENABLED", "Enable OPT resource record", "false"},
                        {"VERTX_ADDR_RESOLVER_ROUND_ROBIN", "Round-robin DNS address selection", "false"},
                }
        ));

        return section("Address Resolver", "Vert.x Address Resolver",
                "DNS resolution and caching configuration.", content);
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
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "JWT Provider",
                new String[][]{
                        {"VERTX_AUTH_JWT_KEYSTORE_PATH", "Path to JWT keystore", "—"},
                        {"VERTX_AUTH_JWT_KEYSTORE_PASSWORD", "JWT keystore password", "—"},
                        {"VERTX_AUTH_JWT_ALGORITHM", "JWT signing algorithm", "—"},
                        {"VERTX_AUTH_JWT_ISSUER", "JWT issuer claim", "—"},
                        {"VERTX_AUTH_JWT_AUDIENCE", "JWT audience claim", "—"},
                        {"VERTX_AUTH_JWT_EXPIRES_IN_SECONDS", "Token expiration (seconds)", "—"},
                        {"VERTX_AUTH_JWT_PERMISSIONS_CLAIM_KEY", "Permissions claim JSON path", "—"},
                }
        ));

        content.add(envTable(
                "OAuth2 Provider",
                new String[][]{
                        {"VERTX_AUTH_OAUTH2_CLIENT_ID", "OAuth2 client ID", "—"},
                        {"VERTX_AUTH_OAUTH2_CLIENT_SECRET", "OAuth2 client secret", "—"},
                        {"VERTX_AUTH_OAUTH2_SITE", "OAuth2 site / issuer URL", "—"},
                        {"VERTX_AUTH_OAUTH2_DISCOVERY_URL", "OIDC discovery URL", "—"},
                }
        ));

        content.add(envTable(
                "ABAC Provider",
                new String[][]{
                        {"VERTX_AUTH_ABAC_POLICY_FILES", "Comma-separated ABAC policy file paths", "—"},
                }
        ));

        content.add(envTable(
                "OTP Provider",
                new String[][]{
                        {"VERTX_AUTH_OTP_TYPE", "OTP type (TOTP or HOTP)", "—"},
                        {"VERTX_AUTH_OTP_PASSWORD_LENGTH", "OTP password length", "6"},
                        {"VERTX_AUTH_OTP_PERIOD", "TOTP period (seconds)", "30"},
                        {"VERTX_AUTH_OTP_LOOK_AHEAD_WINDOW", "OTP look-ahead window", "—"},
                        {"VERTX_AUTH_OTP_COUNTER", "HOTP counter", "—"},
                }
        ));

        content.add(envTable(
                "Property File Provider",
                new String[][]{
                        {"VERTX_AUTH_PROPERTIES_PATH", "Path to Shiro properties file", "—"},
                }
        ));

        content.add(envTable(
                "LDAP Provider",
                new String[][]{
                        {"VERTX_AUTH_LDAP_URL", "LDAP server URL", "—"},
                        {"VERTX_AUTH_LDAP_AUTHENTICATION_QUERY", "Authentication query template", "—"},
                        {"VERTX_AUTH_LDAP_AUTHENTICATION_MECHANISM", "LDAP auth mechanism", "simple"},
                        {"VERTX_AUTH_LDAP_REFERRAL", "LDAP referral policy", "—"},
                }
        ));

        content.add(envTable(
                "htpasswd Provider",
                new String[][]{
                        {"VERTX_AUTH_HTPASSWD_PATH", "Path to .htpasswd file", "—"},
                        {"VERTX_AUTH_HTPASSWD_PLAIN_TEXT_ENABLED", "Allow plain-text passwords", "false"},
                }
        ));

        content.add(envTable(
                "htdigest Provider",
                new String[][]{
                        {"VERTX_AUTH_HTDIGEST_PATH", "Path to .htdigest file", "—"},
                }
        ));

        return section("Authentication & Authorization", "GuicedEE Auth",
                "Annotation-driven auth providers with env var overrides.", content);
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
                }
        ));

        return section("Health Checks", "GuicedEE Health",
                "MicroProfile Health integration with Vert.x.", content);
    }

    // ── Metrics ─────────────────────────────────────────

    private WaStack<?> buildMetricsSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "Core Metrics",
                new String[][]{
                        {"METRICS_ENABLED", "Enable metrics collection", "false"},
                        {"METRICS_REGISTRY_NAME", "Dropwizard registry name", "—"},
                        {"METRICS_JMX_ENABLED", "Expose metrics as JMX MBeans", "false"},
                        {"METRICS_JMX_DOMAIN", "JMX domain name", "—"},
                        {"METRICS_BASE_NAME", "Metric name prefix", "—"},
                }
        ));

        content.add(envTable(
                "Graphite Reporter",
                new String[][]{
                        {"METRICS_GRAPHITE_ENABLED", "Enable Graphite reporting", "false"},
                        {"METRICS_GRAPHITE_HOST", "Graphite host", "—"},
                        {"METRICS_GRAPHITE_PORT", "Graphite port", "2003"},
                        {"METRICS_GRAPHITE_PREFIX", "Metric prefix for Graphite", "—"},
                }
        ));

        content.add(envTable(
                "Prometheus Endpoint",
                new String[][]{
                        {"METRICS_PROMETHEUS_ENABLED", "Enable Prometheus scrape endpoint", "false"},
                        {"METRICS_PROMETHEUS_ENDPOINT", "Prometheus endpoint path", "/metrics"},
                        {"METRICS_PROMETHEUS_PORT", "Prometheus endpoint port", "—"},
                }
        ));

        return section("Metrics", "GuicedEE Metrics",
                "Application metrics with Dropwizard and Prometheus.", content);
    }

    // ── Telemetry ───────────────────────────────────────

    private WaStack<?> buildTelemetrySection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        content.add(envTable(
                "OpenTelemetry",
                new String[][]{
                        {"TELEMETRY_ENABLED", "Enable distributed tracing", "false"},
                        {"TELEMETRY_SERVICE_NAME", "OpenTelemetry service name", "—"},
                        {"TELEMETRY_OTLP_ENDPOINT", "OTLP HTTP exporter endpoint", "—"},
                        {"TELEMETRY_USE_IN_MEMORY", "Use in-memory exporters (testing)", "false"},
                        {"TELEMETRY_CONFIGURE_LOGS", "Enable Log4j2 OpenTelemetry appender", "false"},
                        {"TELEMETRY_SERVICE_VERSION", "Service version resource attribute", "1.0.0"},
                        {"TELEMETRY_DEPLOYMENT_ENVIRONMENT", "Deployment environment attribute", "production"},
                        {"TELEMETRY_MAX_BATCH_SIZE", "Batch span processor max batch size", "512"},
                        {"TELEMETRY_MAX_LOG_BATCH_SIZE", "Max log record batch size", "512"},
                        {"TELEMETRY_LOG_SIGNALS", "Log telemetry signals", "false"},
                        {"OTEL_EXPORTER_OTLP_ENDPOINT", "Standard OTLP endpoint override (OpenTelemetry spec)", "—"},
                        {"HOSTNAME", "Hostname used as host.name resource attribute", "localhost"},
                }
        ));

        return section("Telemetry", "GuicedEE Telemetry",
                "OpenTelemetry distributed tracing with OTLP export.", content);
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
     * Builds an HTML table of environment variables with name, purpose, and default columns.
     */
    private DivSimple<?> envTable(String title, String[][] rows)
    {
        var wrapper = new WaStack<>();
        wrapper.setGap(PageSize.Small);

        if (title != null && !title.isBlank())
        {
            wrapper.add(headingText("h4", "s", title));
        }

        var tableDiv = new DivSimple<>();
        tableDiv.addStyle("overflow-x", "auto");

        var sb = new StringBuilder();
        sb.append("<table style=\"width:100%;border-collapse:collapse;\">");
        sb.append("<thead><tr>");
        sb.append("<th style=\"text-align:left;padding:var(--wa-space-2xs) var(--wa-space-xs);border-bottom:var(--wa-border-width-s) solid var(--wa-color-surface-border);white-space:nowrap;\">Variable</th>");
        sb.append("<th style=\"text-align:left;padding:var(--wa-space-2xs) var(--wa-space-xs);border-bottom:var(--wa-border-width-s) solid var(--wa-color-surface-border);\">Purpose</th>");
        sb.append("<th style=\"text-align:left;padding:var(--wa-space-2xs) var(--wa-space-xs);border-bottom:var(--wa-border-width-s) solid var(--wa-color-surface-border);white-space:nowrap;\">Default</th>");
        sb.append("</tr></thead><tbody>");

        for (String[] row : rows)
        {
            sb.append("<tr>");
            sb.append("<td style=\"padding:var(--wa-space-2xs) var(--wa-space-xs);border-bottom:var(--wa-border-width-s) solid color-mix(in srgb, var(--wa-color-surface-border) 50%, transparent);\">");
            sb.append("<code class=\"wa-body-s\" style=\"color:color-mix(in srgb, var(--wa-color-brand) 70%, var(--wa-color-text));white-space:nowrap;\">")
              .append(escapeAngular(row[0])).append("</code></td>");
            sb.append("<td class=\"wa-body-s\" style=\"padding:var(--wa-space-2xs) var(--wa-space-xs);border-bottom:var(--wa-border-width-s) solid color-mix(in srgb, var(--wa-color-surface-border) 50%, transparent);\">")
              .append(escapeAngular(row[1])).append("</td>");
            sb.append("<td style=\"padding:var(--wa-space-2xs) var(--wa-space-xs);border-bottom:var(--wa-border-width-s) solid color-mix(in srgb, var(--wa-color-surface-border) 50%, transparent);\">");
            sb.append("<code class=\"wa-body-s\">").append(escapeAngular(row[2])).append("</code></td>");
            sb.append("</tr>");
        }

        sb.append("</tbody></table>");
        tableDiv.setText(sb.toString());

        wrapper.add(tableDiv);
        return wrapper;
    }
}


