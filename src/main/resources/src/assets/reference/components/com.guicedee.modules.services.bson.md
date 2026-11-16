# com.guicedee.modules.services:bson

JPMS modularized shade of org.mongodb:bson exposing the org.mongodb.bson module. Upstream ships only an Automatic-Module-Name, which jlink refuses: "automatic module cannot be used with jlink". The whole MongoDB driver chain (bson -> bson-record-codec -> mongodb-driver-core -> mongodb-driver-reactivestreams) arrives transitively through io.vertx:vertx-mongo-client, so each link needs a real module descriptor for a clean runtime image.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.mongodb.bson`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/bson/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.mongodb:bson`; scope `compile`; optional.
- `org.slf4j:slf4j-api`; scope `compile`; optional.

## JPMS descriptor

```java
module org.mongodb.bson {
    // Optional upstream: org.bson.diagnostics.Loggers probes for SLF4J reflectively and
    // falls back to java.util.logging, so this must not become a hard runtime edge.
    requires static org.slf4j;
    requires java.logging;

    exports org.bson;
    exports org.bson.annotations;
    exports org.bson.assertions;
    exports org.bson.codecs;
    exports org.bson.codecs.configuration;
    exports org.bson.codecs.jsr310;
    exports org.bson.codecs.pojo;
    exports org.bson.codecs.pojo.annotations;
    exports org.bson.conversions;
    exports org.bson.diagnostics;
    // org.bson.internal / .internal.vector are exported deliberately: mongodb-driver-core
    // reaches into them. As an automatic module every package was readable; once named,
    // anything left unexported becomes an IllegalAccessError at runtime.
    exports org.bson.internal;
    exports org.bson.internal.vector;
    exports org.bson.io;
    exports org.bson.json;
    exports org.bson.types;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
