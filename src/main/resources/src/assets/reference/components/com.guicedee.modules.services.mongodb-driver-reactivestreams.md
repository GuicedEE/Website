# com.guicedee.modules.services:mongodb-driver-reactivestreams

JPMS modularized shade of org.mongodb:mongodb-driver-reactivestreams exposing the org.mongodb.driver.reactivestreams module. This is the driver io.vertx:vertx-mongo-client depends on, which is how the whole MongoDB chain reaches GuicedEE consumers.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.mongodb.driver.reactivestreams`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/mongodb-driver-reactivestreams/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.mongodb:mongodb-driver-reactivestreams`; scope `compile`; optional.
- `com.guicedee.modules.services:bson`; scope `compile`.
- `com.guicedee.modules.services:mongodb-driver-core`; scope `compile`.
- `com.guicedee.modules.services:reactor-core`; scope `compile`.
- `com.guicedee.modules.services:reactive-streams`; scope `compile`.

## JPMS descriptor

```java
module org.mongodb.driver.reactivestreams {
    // Driver-core and bson types dominate the exported API (MongoClientSettings, Document,
    // codecs), and Publisher is the return type of virtually every method here.
    requires transitive org.mongodb.driver.core;
    requires transitive org.mongodb.bson;
    requires transitive org.reactivestreams;

    // The driver is implemented on Reactor internally (com.mongodb.reactivestreams.client.internal),
    // so this is a hard runtime edge, not an optional one.
    requires reactor.core;

    exports com.mongodb.reactivestreams.client;
    exports com.mongodb.reactivestreams.client.gridfs;
    exports com.mongodb.reactivestreams.client.vault;

    // Exported for the same reason as driver-core's internals: these were freely readable
    // while the jar was an automatic module.
    exports com.mongodb.reactivestreams.client.internal;
    exports com.mongodb.reactivestreams.client.internal.crypt;
    exports com.mongodb.reactivestreams.client.internal.gridfs;
    exports com.mongodb.reactivestreams.client.internal.vault;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
