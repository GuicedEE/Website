# com.guicedee.modules.services:reactor-core

JPMS modularized shade of io.projectreactor:reactor-core exposing the reactor.core module. Reactor ships no module descriptor at all (not even an Automatic-Module-Name - the name reactor.core is derived from the file name), so jlink rejects it. It is a non-optional runtime dependency of org.mongodb:mongodb-driver-reactivestreams, which io.vertx:vertx-mongo-client depends on, so it cannot be avoided while the Mongo client is in use.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `reactor.core`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/reactor-core/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `io.projectreactor:reactor-core`; scope `compile`; optional.
- `com.guicedee.modules.services:reactive-streams`; scope `compile`.

## JPMS descriptor

```java
module reactor.core {
    // Publisher/Subscriber appear all over Flux/Mono's exported API.
    requires transitive org.reactivestreams;

    // reactor.util.Loggers falls back to java.util.logging when no SLF4J binding is present.
    // As an automatic module this read every module implicitly; named modules do not.
    requires java.logging;
    requires static org.slf4j;

    exports reactor.adapter;
    exports reactor.core;
    exports reactor.core.observability;
    exports reactor.core.publisher;
    exports reactor.core.scheduler;
    exports reactor.util;
    exports reactor.util.annotation;
    exports reactor.util.concurrent;
    exports reactor.util.context;
    exports reactor.util.function;
    exports reactor.util.retry;
    exports reactor.util.repeat;

    // NOTE: the jar ships META-INF/services for io.micrometer.context.ContextAccessor and
    // reactor.blockhound.integration.BlockHoundIntegration. Neither interface is on the
    // module path here (both are optional integrations), so no `provides` clauses are
    // declared - a `provides` naming an unresolvable service type would not compile.
    // The service files remain inside the jar for classpath use.
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
