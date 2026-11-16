# com.guicedee.modules.services:vertx-kafka

Vert.x Kafka

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `io.vertx.client.kafka`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Vert.x/vertx-kafka/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `io.vertx:vertx-core`; scope `compile`.
- `io.vertx:vertx-kafka-client`; scope `compile`; optional.
- `com.guicedee.modules.services:kafka-client`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.

## JPMS descriptor

```java
module io.vertx.client.kafka {
    requires transitive io.vertx.core;

    requires io.vertx.core.logging;

    requires transitive org.apache.kafka.client;

    exports io.vertx.kafka.admin;
    exports io.vertx.kafka.client.common;
    exports io.vertx.kafka.client.consumer;
    exports io.vertx.kafka.client.producer;
    exports io.vertx.kafka.client.serialization;

    opens io.vertx.kafka.admin to io.vertx.core;
    opens io.vertx.kafka.admin.impl to io.vertx.core;
    opens io.vertx.kafka.client.common to io.vertx.core;
    opens io.vertx.kafka.client.common.impl to io.vertx.core;
    opens io.vertx.kafka.client.common.tracing to io.vertx.core;
    opens io.vertx.kafka.client.consumer to io.vertx.core;
    opens io.vertx.kafka.client.consumer.impl to io.vertx.core;
    opens io.vertx.kafka.client.producer to io.vertx.core;
    opens io.vertx.kafka.client.producer.impl to io.vertx.core;
    opens io.vertx.kafka.client.serialization to io.vertx.core;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
