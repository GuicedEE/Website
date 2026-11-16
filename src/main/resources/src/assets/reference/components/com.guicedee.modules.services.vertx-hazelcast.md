# com.guicedee.modules.services:vertx-hazelcast

Vert.x Hazelcast

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `io.vertx.clustermanager.hazelcast`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Vert.x/vertx-hazelcast/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `io.vertx:vertx-core`; scope `compile`.
- `io.vertx:vertx-hazelcast`; scope `compile`; optional.
- `com.guicedee.modules.services:hazelcast-all`; scope `compile`.

## JPMS descriptor

```java
module io.vertx.clustermanager.hazelcast {
    requires transitive io.vertx.core;
    requires transitive com.hazelcast.all;

    requires io.vertx.core.logging;
    requires static io.netty.common;

    exports io.vertx.spi.cluster.hazelcast;
    exports io.vertx.spi.cluster.hazelcast.spi;

    uses io.vertx.spi.cluster.hazelcast.spi.HazelcastObjectProvider;

    provides io.vertx.core.spi.VertxServiceProvider with io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

    opens io.vertx.spi.cluster.hazelcast to com.hazelcast.all, io.vertx.core;
    opens io.vertx.spi.cluster.hazelcast.impl to com.hazelcast.all, io.vertx.core;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
