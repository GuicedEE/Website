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
                "inject",
                "openapi",
                "persistence",
                "rabbitmq",
                "representations",
                "rest",
                "swagger-ui",
                "telemetry",
                "vertx",
                "web",
                "webservices",
                "websockets"
        };

        for (String id : ids)
        {
            String name = toTitleCase(id);
            String description = String.format("GuicedEE module %s exposed on the public site.", name);
            String bootClass = String.format("com.guicedee.%s.Boot", toPascalCase(id));
            String groupId = "com.guicedee";
            String artifactId = id;
            String version = "2.0.0-SNAPSHOT";
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
        String version = "2.0.0-SNAPSHOT";
        String groupId = "com.guicedee.services";

        List<ServiceDefinition> services = new ArrayList<>();

        addServices(services, groupId, version, "services/Apache/Commons", "Apache Commons", new String[]{
                "commons-beanutils",
                "commons-collections",
                "commons-csv",
                "commons-fileupload",
                "commons-math"
        });
        addServices(services, groupId, version, "services/Apache/CXF", "Apache CXF", new String[]{
                "apache-cxf",
                "apache-cxf-rest",
                "apache-cxf-rest-openapi",
                "apache-cxf-rt-security",
                "apache-cxf-rt-transports-http"
        });
        addServices(services, groupId, version, "services/Apache/POI", "Apache POI", new String[]{
                "apache-poi",
                "apache-poi-ooxml"
        });
        addServices(services, groupId, version, "services/Database", "Database Driver", new String[]{
                "msal4j",
                "mssql-jdbc",
                "postgresql"
        });
        addServices(services, groupId, version, "services/Google", "Google", new String[]{
                "aop",
                "guice-core",
                "guice-assistedinject",
                "guice-grapher",
                "guice-jmx",
                "guice-jndi"
        });
        addServices(services, groupId, version, "services/Hibernate", "Hibernate", new String[]{
                "hibernate-c3p0",
                "hibernate-core",
                "hibernate-jcache",
                "hibernate-reactive",
                "hibernate-validator"
        });

        addServices(services, groupId, version, "services/JCache", "JCache", new String[]{
                "cache-annotations-ri-common",
                "cache-annotations-ri-guice",
                "cache-api",
                "ehcache",
                "hazelcast",
                "hazelcast-hibernate"
        });
        addServices(services, groupId, version, "services/JNI", "JNI", new String[]{
                "jna-platform",
                "nrjavaserial"
        });
        addServices(services, groupId, version, "services/Jakarta", "Jakarta", new String[]{
                "jakarta-security-jacc"
        });
        addServices(services, groupId, version, "services/Libraries", "Library", new String[]{
                "bcrypt",
                "cloudevents",
                "ibm-mq",
                "jandex",
                "javassist",
                "json",
                "kafka-client",
                "mapstruct",
                "openpdf",
                "rabbitmq-client",
                "scram",
                "swagger",
                "testcontainers",
                "uadetector-core",
                "uadetector-resources"
        });
        addServices(services, groupId, version, "services/MicroProfile", "MicroProfile", new String[]{
                "config-core",
                "metrics-core"
        });
        addServices(services, groupId, version, "services/Vert.x", "Vert.x", new String[]{
                "vertx-mutiny",
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
                    modulePath
            ));
        }
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
