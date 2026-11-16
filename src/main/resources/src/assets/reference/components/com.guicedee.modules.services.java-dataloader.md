# com.guicedee.modules.services:java-dataloader

A JPMS modularized shade of java-dataloader (com.graphql-java:java-dataloader) exposing the org.dataloader module for a clean GuicedEE / jlink pipeline.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.dataloader`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/java-dataloader/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.graphql-java:java-dataloader`; scope `compile`; optional.
- `com.guicedee.modules.services:reactive-streams`; scope `compile`.
- `org.jspecify:jspecify`; scope `compile`.

## JPMS descriptor

```java
module org.dataloader {
    requires transitive org.reactivestreams;

    requires static org.jspecify;

    exports org.dataloader;
    exports org.dataloader.annotations;
    exports org.dataloader.impl;
    exports org.dataloader.instrumentation;
    exports org.dataloader.reactive;
    exports org.dataloader.registries;
    exports org.dataloader.scheduler;
    exports org.dataloader.stats;
    exports org.dataloader.stats.context;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
