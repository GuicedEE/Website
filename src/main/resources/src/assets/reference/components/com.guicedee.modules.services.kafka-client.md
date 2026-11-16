# com.guicedee.modules.services:kafka-client

kafka-client

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.kafka.client`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/kafka-client/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.apache.kafka:kafka-clients`; scope `compile`; optional.
- `org.slf4j:slf4j-api`; scope `compile`.
- `com.github.luben:zstd-jni`; scope `compile`; optional.
- `org.xerial.snappy:snappy-java`; scope `compile`; optional.
- `org.lz4:lz4-java`; scope `compile`; optional.
- `tools.jackson.core:jackson-databind`; scope `compile`.
- `org.bitbucket.b_c:jose4j`; scope `compile`; optional.

## JPMS descriptor

```java
module org.apache.kafka.client {
    exports org.apache.kafka.clients;
    exports org.apache.kafka.clients.admin;
    exports org.apache.kafka.clients.consumer;
    exports org.apache.kafka.clients.producer;
    exports org.apache.kafka.common;
    exports org.apache.kafka.common.acl;
    exports org.apache.kafka.common.config;
    exports org.apache.kafka.common.errors;
    exports org.apache.kafka.common.header;
    exports org.apache.kafka.common.record;
    exports org.apache.kafka.common.header.internals to io.vertx.client.kafka;
    exports org.apache.kafka.common.utils to io.vertx.client.kafka;

    exports org.apache.kafka.server.authorizer;
    exports org.apache.kafka.server.policy;
    exports org.apache.kafka.server.quota;

    exports  org.apache.kafka.common.serialization;

    requires org.slf4j;
    requires java.management;

    provides org.apache.kafka.common.config.provider.ConfigProvider with org.apache.kafka.common.config.provider.FileConfigProvider;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## JavadocGenerator

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/kafka-client/src/main/java/org/apache/kafka/client/JavadocGenerator.java)

### JavadocGenerator

```java
```
