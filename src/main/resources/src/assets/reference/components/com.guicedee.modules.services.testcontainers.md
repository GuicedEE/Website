# com.guicedee.modules.services:testcontainers

A modularized test containers implementation

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.testcontainers`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/testcontainers/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.testcontainers:testcontainers`; scope `compile`; optional.
- `org.testcontainers:testcontainers-postgresql`; scope `compile`; optional.
- `org.testcontainers:testcontainers-mysql`; scope `compile`; optional.
- `org.testcontainers:testcontainers-mariadb`; scope `compile`; optional.
- `org.testcontainers:testcontainers-mssqlserver`; scope `compile`; optional.
- `org.testcontainers:testcontainers-oracle-free`; scope `compile`; optional.
- `org.testcontainers:testcontainers-mongodb`; scope `compile`; optional.
- `org.testcontainers:testcontainers-cassandra`; scope `compile`; optional.
- `org.testcontainers:testcontainers-clickhouse`; scope `compile`; optional.
- `org.testcontainers:testcontainers-cockroachdb`; scope `compile`; optional.
- `org.testcontainers:testcontainers-rabbitmq`; scope `compile`; optional.
- `org.testcontainers:testcontainers-kafka`; scope `compile`; optional.
- `org.testcontainers:testcontainers-elasticsearch`; scope `compile`; optional.
- `org.testcontainers:testcontainers-nginx`; scope `compile`; optional.
- `org.testcontainers:testcontainers-jdbc`; scope `compile`; optional.
- `org.testcontainers:testcontainers-r2dbc`; scope `compile`; optional.
- `org.testcontainers:testcontainers-junit-jupiter`; scope `compile`; optional.
- `com.guicedee.modules.services:jna-platform`; scope `compile`.
- `tools.jackson.core:jackson-core`; scope `compile`.
- `commons-codec:commons-codec`; scope `compile`.
- `org.apache.commons:commons-compress`; scope `compile`.
- `commons-io:commons-io`; scope `compile`.
- `com.github.docker-java:docker-java-api`; scope `compile`; optional.
- `com.github.docker-java:docker-java-transport-zerodep`; scope `compile`; optional.
- `org.rnorth.duct-tape:duct-tape`; scope `compile`; optional.
- `org.slf4j:slf4j-api`; scope `compile`.
- `junit:junit`; scope `compile`.
- `com.google.guava:guava`; scope `compile`.
- `jakarta.annotation:jakarta.annotation-api`; scope `compile`.
- `com.guicedee.modules.services:reactive-streams`; scope `compile`.

## JPMS descriptor

```java
open module org.testcontainers {
    requires java.instrument;
    requires java.management;
    requires java.desktop;

    requires java.sql;

    requires com.sun.jna;

    requires org.slf4j;
    requires java.logging;

    requires org.junit.jupiter.api;
    requires com.fasterxml.jackson.annotation;

    requires static org.apache.commons.compress;
    requires static org.apache.commons.lang3;
    requires static org.apache.commons.io;

    requires junit;
    requires org.reactivestreams;

    exports org.testcontainers.containers;
    exports org.testcontainers.kafka;
    exports org.testcontainers.mssqlserver;
    exports org.testcontainers.containers.wait.strategy;
    exports org.testcontainers.containers.output;
    exports org.testcontainers.containers.startupcheck;
    exports org.testcontainers.junit.jupiter;
    exports org.testcontainers.utility;
    exports org.testcontainers.images;
    exports org.testcontainers.images.builder;

    provides java.sql.Driver with org.testcontainers.jdbc.ContainerDatabaseDriver;

    provides org.testcontainers.containers.JdbcDatabaseContainerProvider with
        org.testcontainers.containers.PostgreSQLContainerProvider,
        org.testcontainers.containers.PostgisContainerProvider,
        org.testcontainers.containers.TimescaleDBContainerProvider,
        org.testcontainers.containers.PgVectorContainerProvider,
        org.testcontainers.containers.MySQLContainerProvider,
        org.testcontainers.containers.MariaDBContainerProvider,
        org.testcontainers.containers.MSSQLServerContainerProvider,
        org.testcontainers.containers.OracleContainerProvider,
        org.testcontainers.containers.CassandraContainerProvider,
        org.testcontainers.containers.ClickHouseContainerProvider,
        org.testcontainers.containers.CockroachContainerProvider;

    provides org.testcontainers.dockerclient.DockerClientProviderStrategy with org.testcontainers.dockerclient.TestcontainersHostPropertyClientProviderStrategy,
        org.testcontainers.dockerclient.EnvironmentAndSystemPropertyClientProviderStrategy,
        org.testcontainers.dockerclient.UnixSocketClientProviderStrategy,
        org.testcontainers.dockerclient.DockerMachineClientProviderStrategy,
        org.testcontainers.dockerclient.NpipeSocketClientProviderStrategy,
        org.testcontainers.dockerclient.RootlessDockerClientProviderStrategy,
        org.testcontainers.dockerclient.DockerDesktopClientProviderStrategy;

    provides org.testcontainers.r2dbc.R2DBCDatabaseContainerProvider with
        org.testcontainers.containers.PostgreSQLR2DBCDatabaseContainerProvider,
        org.testcontainers.containers.MySQLR2DBCDatabaseContainerProvider,
        org.testcontainers.containers.MariaDBR2DBCDatabaseContainerProvider,
        org.testcontainers.containers.MSSQLServerR2DBCDatabaseContainerProvider;

    provides org.testcontainers.shaded.com.fasterxml.jackson.core.JsonFactory with org.testcontainers.shaded.com.fasterxml.jackson.core.JsonFactory;

    provides org.testcontainers.shaded.com.fasterxml.jackson.core.ObjectCodec with org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

    provides io.r2dbc.spi.ConnectionFactoryProvider with org.testcontainers.r2dbc.Hidden$TestcontainersR2DBCConnectionFactoryProvider;

    uses org.testcontainers.containers.JdbcDatabaseContainerProvider;
    uses org.testcontainers.containers.PgVectorContainerProvider;
    uses org.testcontainers.dockerclient.DockerClientProviderStrategy;
    uses org.testcontainers.r2dbc.R2DBCDatabaseContainerProvider;
    uses org.testcontainers.shaded.com.fasterxml.jackson.core.JsonFactory;
    uses org.testcontainers.shaded.com.fasterxml.jackson.core.ObjectCodec;
    uses org.testcontainers.utility.ImageNameSubstitutor;
    uses org.testcontainers.core.CreateContainerCmdModifier;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
