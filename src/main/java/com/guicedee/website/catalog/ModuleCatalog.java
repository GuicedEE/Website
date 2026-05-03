package com.guicedee.website.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ModuleCatalog
{
    private static final List<ModuleEntry> MODULES = Collections.unmodifiableList(buildStaticModules());
    private static final List<ServiceDefinition> SERVICES = Collections.unmodifiableList(buildStaticServices());

    private ModuleCatalog()
    {
    }

    public static List<ModuleEntry> getModules()
    {
        return MODULES;
    }

    public static List<ServiceDefinition> getServices()
    {
        return SERVICES;
    }

    // Static list of GuicedEE modules; avoids IO-based discovery as per requirements
    private static List<ModuleEntry> buildStaticModules()
    {
        List<ModuleEntry> modules = new ArrayList<>();
        // Known module identifiers under GuicedEE directory
        String[] ids = new String[]{
                "cdi",
                "cerial",
                "client",
                "config",
                "fault-tolerance",
                "graphql",
                "hazelcast",
                "health",
                "ibmmq",
                "inject",
                "jwt",
                "kafka",
                "mailclient",
                "metrics",
                "openapi",
                "persistence",
                "rabbitmq",
                "representations",
                "rest",
                "rest-client",
                "swagger-ui",
                "telemetry",
                "vertx",
                "web",
                "webservices",
                "websockets"
        };

        for (String id : ids)
        {
            String name = displayName(id);
            String description = switch (id)
            {
                case "cerial" -> "Modular serial port communications in Java. @Named port injection, auto-reconnect, idle monitoring, and health reporting.";
                case "fault-tolerance" -> "MicroProfile Fault Tolerance for GuicedEE. @Retry, @Timeout, @CircuitBreaker, @Fallback, and @Bulkhead annotations with Guice AOP interceptors.";
                case "graphql" -> "SPI-driven GraphQL integration for GuicedEE with Vert.x Web GraphQL. HTTP + WebSocket endpoints, GraphiQL IDE, DataLoader support, VertxFutureAdapter and JsonObjectAdapter auto-configured.";
                case "hazelcast" -> "Hazelcast distributed caching and clustering integration for GuicedEE. JCache provider, distributed data structures, and cluster management.";
                case "health" -> "MicroProfile Health integration for GuicedEE with Vert.x 5. @Liveness, @Readiness, @Startup health checks with automatic discovery and JSON endpoints.";
                case "ibmmq" -> "Annotation-driven IBM MQ integration for GuicedEE. @IBMMQConnectionOptions, @IBMMQQueueDefinition, IBMMQConsumer/Publisher with IBM MQ JMS client.";
                case "jwt" -> "MicroProfile JWT Auth bridge for Vert.x 5. Maps Vert.x User to JsonWebToken, @Claim injection without @Inject, CallScope-aware context, Keycloak/OIDC support.";
                case "kafka" -> "Annotation-driven Kafka consumer and producer integration for GuicedEE. @KafkaConnectionOptions, @KafkaTopicDefinition, KafkaTopicConsumer/Publisher with Vert.x Kafka client.";
                case "mailclient" -> "Email client integration for GuicedEE with Vert.x 5. SMTP mail sending with Guice-managed configuration and templates.";
                case "metrics" -> "MicroProfile Metrics for GuicedEE with Vert.x 5 Dropwizard Metrics. @Counted, @Timed annotations, Prometheus scrape endpoint, and Graphite reporting.";
                case "rest-client" -> "Annotation-driven REST client for GuicedEE using Vert.x 5 WebClient. @Endpoint declarations, RestClient injection, and authentication strategies.";
                case "persistence" -> "Reactive JPA persistence with Hibernate Reactive 7 and Vert.x 5 SQL clients, plus MongoDB document storage, Cassandra wide-column storage, and Redis caching via Vert.x clients. Supports PostgreSQL, MySQL, SQL Server, Oracle, DB2, MongoDB, Cassandra, and Redis.";
                default -> String.format("GuicedEE module %s exposed on the public site.", name);
            };
            String bootClass = String.format("com.guicedee.%s.Boot", toPascalCase(id));
            String groupId = switch (id)
            {
                case "jwt", "config" -> "com.guicedee.microprofile";
                default -> "com.guicedee";
            };
            String artifactId = id;
            String version = "2.0.1";
            String readmePath = "GuicedEE/" + id + "/README.md";
            String rulesPath = "GuicedEE/" + id + "/rules";
            modules.add(new ModuleEntry(id, name, description, bootClass, groupId, artifactId, version, readmePath, rulesPath));
        }

        return modules.stream()
                .sorted((a, b) -> a.getId().compareToIgnoreCase(b.getId()))
                .collect(Collectors.toList());
    }

    // Static list of GuicedEE services; avoids IO-based discovery as per requirements
    private static List<ServiceDefinition> buildStaticServices()
    {
        String version = "2.0.1";
        String groupId = "com.guicedee.modules.services";

        List<ServiceDefinition> services = new ArrayList<>();

        addServices(services, groupId, version, "Apache/Commons", "Apache Commons", new String[]{
                "commons-beanutils",
                "commons-collections",
                "commons-csv",
                "commons-fileupload",
                "commons-math"
        });
        addServices(services, groupId, version, "Apache/CXF", "Apache CXF (Web Services)", new String[]{
                "apache-cxf",
                "apache-cxf-rt-security",
                "apache-cxf-rt-transports-http"
        });
        addServices(services, groupId, version, "Apache/POI", "Apache POI", new String[]{
                "apache-poi",
                "apache-poi-ooxml"
        });
        addServices(services, groupId, version, "Database", "Database Driver", new String[]{
                "msal4j",
                "mssql-jdbc",
                "postgresql",
                "vertx-redis-client"
        });
        addServices(services, groupId, version, "Google", "Google", new String[]{
                "aop",
                "guice-core",
                "guice-assistedinject",
                "guice-grapher",
                "guice-jmx",
                "guice-jndi"
        });
        addServices(services, groupId, version, "Hibernate", "Hibernate", new String[]{
                "hibernate-core",
                "hibernate-jcache",
                "hibernate-reactive",
                "hibernate-validator"
        });

        addServices(services, groupId, version, "JCache", "JCache", new String[]{
                "cache-annotations-ri-common",
                "cache-annotations-ri-guice",
                "cache-api",
                "ehcache",
                "hazelcast",
                "hazelcast-hibernate"
        });
        addServices(services, groupId, version, "JNI", "JNI", new String[]{
                "jna-platform",
                "nrjavaserial"
        });
        addServices(services, groupId, version, "Jakarta", "Jakarta", new String[]{
                "jakarta-security-jacc"
        });
        addServices(services, groupId, version, "Libraries", "Library", new String[]{
                "bcrypt",
                "cloudevents",
                "ibm-mq",
                "jandex",
                "javassist",
                "json",
                "kafka-client",
                "mapstruct",
                "microprofile-jwt-auth-api",
                "openpdf",
                "rabbitmq-client",
                "scram",
                "swagger",
                "testcontainers",
                "uadetector-core",
                "uadetector-resources"
        });
        addServices(services, groupId, version, "MicroProfile", "MicroProfile", new String[]{
                "config-core",
                "jwt-auth-api",
                "metrics-core"
        });
        addServices(services, groupId, version, "Vert.x", "Vert.x", new String[]{
                "vertx-cassandra",
                "vertx-grpc-server",
                "vertx-grpc-client",
                "vertx-http-proxy",
                "vertx-mutiny",
                "vertx-kafka",
                "vertx-pg-client",
                "vertx-rabbitmq"
        });
        addServices(services, groupId, version, "services", "Misc", new String[]{
                "untitled"
        });

        services.sort((a, b) -> a.getId().compareToIgnoreCase(b.getId()));
        return services;
    }

    private static void addServices(List<ServiceDefinition> target,
                                    String groupId,
                                    String version,
                                    String modulePath,
                                    String family,
                                    String[] artifactIds)
    {
        for (String artifactId : artifactIds)
        {
            target.add(new ServiceDefinition(
                    artifactId,
                    groupId,
                    artifactId,
                    version,
                    family + " integration service: " + artifactId,
                    modulePath,
                    modulePath + "/" + artifactId
            ));
        }
    }

    private static String displayName(String id)
    {
        return switch (id)
        {
            case "cdi" -> "CDI Bridge";
            case "cerial" -> "Cerial (Serial Ports)";
            case "client" -> "Client API";
            case "config" -> "MicroProfile Config";
            case "fault-tolerance" -> "Fault Tolerance";
            case "graphql" -> "GraphQL";
            case "hazelcast" -> "Hazelcast";
            case "health" -> "MicroProfile Health";
            case "ibmmq" -> "IBM MQ";
            case "inject" -> "Inject (Core Engine)";
            case "jwt" -> "JWT Authentication";
            case "kafka" -> "Apache Kafka";
            case "mailclient" -> "Mail Client";
            case "metrics" -> "MicroProfile Metrics";
            case "openapi" -> "OpenAPI";
            case "persistence" -> "Persistence (SQL + MongoDB + Cassandra)";
            case "rabbitmq" -> "RabbitMQ";
            case "representations" -> "Representations";
            case "rest" -> "REST Services";
            case "rest-client" -> "REST Client";
            case "swagger-ui" -> "Swagger UI";
            case "telemetry" -> "Telemetry";
            case "vertx" -> "Vert.x Core + HTTP Proxy";
            case "web" -> "Web Server";
            case "webservices" -> "Web Services (SOAP)";
            case "websockets" -> "WebSockets";
            default -> toTitleCase(id);
        };
    }

    private static String toTitleCase(String value)
    {
        return splitOnDelimiters(value)
                .stream()
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    private static String toPascalCase(String value)
    {
        return splitOnDelimiters(value)
                .stream()
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(""));
    }

    private static List<String> splitOnDelimiters(String value)
    {
        return List.of(value.split("[-_\\s]+"));
    }
}
