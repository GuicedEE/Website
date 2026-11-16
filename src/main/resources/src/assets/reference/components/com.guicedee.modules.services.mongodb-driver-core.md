# com.guicedee.modules.services:mongodb-driver-core

JPMS modularized shade of org.mongodb:mongodb-driver-core exposing the org.mongodb.driver.core module.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.mongodb.driver.core`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/mongodb-driver-core/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.mongodb:mongodb-driver-core`; scope `compile`; optional.
- `com.guicedee.modules.services:bson`; scope `compile`.
- `com.guicedee.modules.services:bson-record-codec`; scope `compile`.
- `io.netty:netty-buffer`; scope `compile`; optional.
- `io.netty:netty-transport`; scope `compile`; optional.
- `io.netty:netty-handler`; scope `compile`; optional.
- `org.slf4j:slf4j-api`; scope `compile`; optional.

## JPMS descriptor

```java
module org.mongodb.driver.core {
    // bson types (Document, BsonValue, Codec...) appear throughout the exported API.
    requires transitive org.mongodb.bson;
    // Non-optional runtime dependency upstream: the driver registers RecordCodecProvider.
    requires org.mongodb.bson.record.codec;

    requires java.logging;
    requires java.management;
    requires java.naming;
    requires java.security.jgss;
    requires java.sql;

    // Driver DNS discovery invokes ServiceLoader from this named module.
    uses com.mongodb.spi.dns.DnsClientProvider;
    uses com.mongodb.spi.dns.InetAddressResolverProvider;

    // All optional upstream. The Netty transport is only used when explicitly selected
    // (default is NIO) and SLF4J is probed reflectively, so neither may be a hard edge.
    requires static org.slf4j;
    requires static io.netty.common;
    requires static io.netty.buffer;
    requires static io.netty.transport;
    requires static io.netty.handler;

    exports com.mongodb;
    exports com.mongodb.annotations;
    exports com.mongodb.assertions;
    exports com.mongodb.bulk;
    exports com.mongodb.client.cursor;
    exports com.mongodb.client.gridfs.codecs;
    exports com.mongodb.client.gridfs.model;
    exports com.mongodb.client.model;
    exports com.mongodb.client.model.bulk;
    exports com.mongodb.client.model.changestream;
    exports com.mongodb.client.model.densify;
    exports com.mongodb.client.model.fill;
    exports com.mongodb.client.model.geojson;
    exports com.mongodb.client.model.geojson.codecs;
    exports com.mongodb.client.model.mql;
    exports com.mongodb.client.model.search;
    exports com.mongodb.client.model.vault;
    exports com.mongodb.client.result;
    exports com.mongodb.connection;
    exports com.mongodb.event;
    exports com.mongodb.lang;
    exports com.mongodb.management;
    exports com.mongodb.selector;
    exports com.mongodb.session;
    exports com.mongodb.spi.dns;
    exports com.mongodb.observability;
    exports com.mongodb.observability.micrometer;

    // The com.mongodb.internal.* packages are exported on purpose: the reactivestreams
    // driver is a separate module and calls straight into them. Under an automatic module
    // every package was readable; leaving these encapsulated turns into IllegalAccessError
    // at runtime rather than a compile failure.
    exports com.mongodb.internal;
    exports com.mongodb.internal.async;
    exports com.mongodb.internal.async.function;
    exports com.mongodb.internal.authentication;
    exports com.mongodb.internal.binding;
    exports com.mongodb.internal.build;
    exports com.mongodb.internal.bulk;
    exports com.mongodb.internal.capi;
    exports com.mongodb.internal.client.model;
    exports com.mongodb.internal.client;
    exports com.mongodb.internal.client.model.search;
    exports com.mongodb.internal.observability;
    exports com.mongodb.internal.observability.micrometer;
    exports com.mongodb.internal.client.model.bulk;
    exports com.mongodb.internal.client.model.changestream;
    exports com.mongodb.internal.client.vault;
    exports com.mongodb.internal.connection;
    exports com.mongodb.internal.connection.netty;
    exports com.mongodb.internal.connection.tlschannel;
    exports com.mongodb.internal.connection.tlschannel.async;
    exports com.mongodb.internal.connection.tlschannel.impl;
    exports com.mongodb.internal.connection.tlschannel.util;
    exports com.mongodb.internal.diagnostics.logging;
    exports com.mongodb.internal.dns;
    exports com.mongodb.internal.event;
    exports com.mongodb.internal.function;
    exports com.mongodb.internal.graalvm.substitution;
    exports com.mongodb.internal.inject;
    exports com.mongodb.internal.logging;
    exports com.mongodb.internal.operation;
    exports com.mongodb.internal.selector;
    exports com.mongodb.internal.session;
    exports com.mongodb.internal.thread;
    exports com.mongodb.internal.time;
    exports com.mongodb.internal.validator;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
