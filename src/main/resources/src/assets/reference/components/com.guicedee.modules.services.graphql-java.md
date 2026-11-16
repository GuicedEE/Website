# com.guicedee.modules.services:graphql-java

A JPMS modularized shade of GraphQL-Java (com.graphql-java:graphql-java) exposing the com.graphqljava module for a clean GuicedEE / jlink pipeline. ANTLR remains relocated internally by GraphQL-Java under the graphql.org.antlr.* namespace. The stale Guava copy that GraphQL-Java bundles under graphql.com.google.common.* is stripped out of this shade and its references are rewritten back to com.google.common.* so the module reuses the canonical com.google.guava:guava (module com.google.common) already shipped by GuicedEE. This avoids duplicating Guava and removes the security risk of an out-of-date, embedded copy.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.graphqljava`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/graphql-java/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.graphql-java:graphql-java`; scope `compile`; optional.
- `com.guicedee.modules.services:java-dataloader`; scope `compile`.
- `com.google.guava:guava`; scope `compile`.
- `com.guicedee.modules.services:reactive-streams`; scope `compile`.
- `org.jspecify:jspecify`; scope `compile`.

## JPMS descriptor

```java
module com.graphqljava {
    requires transitive org.dataloader;
    requires transitive org.reactivestreams;

    requires static org.jspecify;

    // Canonical Guava (com.google.guava:guava). GraphQL-Java's internal references to its
    // embedded copy (graphql.com.google.common.*) are rewritten back to com.google.common.*
    // by the shade, so the module now reads the real Guava module instead of a stale bundled one.
    // transitive: GraphQL-Java exposes a few Guava types on its public API surface.
    requires transitive com.google.common;

    // Required by the relocated ANTLR runtime still bundled under graphql.org.antlr.*
    // (it uses java.util.logging.Logger). As an automatic module graphql-java implicitly read
    // every module; as a named module this must be declared explicitly.
    requires java.logging;

    exports graphql;
    exports graphql.analysis;
    exports graphql.analysis.values;
    exports graphql.collect;
    exports graphql.execution;
    exports graphql.execution.conditional;
    exports graphql.execution.directives;
    exports graphql.execution.incremental;
    exports graphql.execution.instrumentation;
    exports graphql.execution.instrumentation.dataloader;
    exports graphql.execution.instrumentation.fieldvalidation;
    exports graphql.execution.instrumentation.parameters;
    exports graphql.execution.instrumentation.tracing;
    exports graphql.execution.preparsed;
    exports graphql.execution.preparsed.persisted;
    exports graphql.execution.reactive;
    exports graphql.execution.values;
    exports graphql.execution.values.legacycoercing;
    exports graphql.extensions;
    exports graphql.i18n;
    exports graphql.incremental;
    exports graphql.introspection;
    exports graphql.language;
    exports graphql.normalized;
    exports graphql.normalized.incremental;
    exports graphql.normalized.nf;
    exports graphql.parser;
    exports graphql.parser.antlr;
    exports graphql.parser.exceptions;
    exports graphql.relay;
    exports graphql.scalar;
    exports graphql.schema;
    exports graphql.schema.diff;
    exports graphql.schema.diff.reporting;
    exports graphql.schema.diffing;
    exports graphql.schema.diffing.ana;
    exports graphql.schema.fetching;
    exports graphql.schema.idl;
    exports graphql.schema.idl.errors;
    exports graphql.schema.impl;
    exports graphql.schema.transform;
    exports graphql.schema.usage;
    exports graphql.schema.validation;
    exports graphql.schema.visibility;
    exports graphql.schema.visitor;
    exports graphql.util;
    exports graphql.util.querygenerator;
    exports graphql.validation;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
