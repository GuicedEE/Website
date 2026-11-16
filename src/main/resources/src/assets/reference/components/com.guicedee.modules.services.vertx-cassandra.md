# com.guicedee.modules.services:vertx-cassandra

Vert.x Cassandra

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `io.vertx.cassandra.client`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Vert.x/vertx-cassandra/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `io.vertx:vertx-core`; scope `compile`.
- `io.vertx:vertx-cassandra-client`; scope `compile`; optional.
- `com.datastax.oss:java-driver-core`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.

## JPMS descriptor

```java
module io.vertx.cassandra.client {
    requires transitive io.vertx.core;

    requires io.vertx.core.logging;

    requires transitive java.driver.core;

    exports io.vertx.cassandra;
    exports io.vertx.cassandra.impl;
    exports io.vertx.cassandra.impl.tracing;

    opens io.vertx.cassandra to io.vertx.core;
    opens io.vertx.cassandra.impl to io.vertx.core;
    opens io.vertx.cassandra.impl.tracing to io.vertx.core;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
