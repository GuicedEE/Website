# com.guicedee.modules.services:vertx-rabbitmq

Vert.x RabbitMQ

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `io.vertx.rabbitmq`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Vert.x/vertx-rabbitmq/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `io.vertx:vertx-core`; scope `compile`.
- `io.vertx:vertx-rabbitmq-client`; scope `compile`; optional.
- `com.guicedee.modules.services:amqp-client`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.

## JPMS descriptor

```java
module io.vertx.rabbitmq {
    requires transitive io.vertx.core;

    requires io.vertx.core.logging;

    requires com.rabbitmq.client;

    exports io.vertx.rabbitmq;

    opens io.vertx.rabbitmq to io.vertx;
    opens io.vertx.rabbitmq.impl to io.vertx;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
