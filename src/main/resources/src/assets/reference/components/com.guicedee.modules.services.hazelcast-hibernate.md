# com.guicedee.modules.services:hazelcast-hibernate

Kubernetes Service Discovery for Hazelcast Discovery SPI

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.hazelcast.hibernate`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JCache/hazelcast-hibernate/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:cache-api`; scope `compile`.
- `com.guicedee.modules.services:hibernate-core`; scope `compile`.
- `com.guicedee.modules.services:hazelcast-all`; scope `compile`.
- `jakarta.activation:jakarta.activation-api`; scope `compile`.
- `com.hazelcast:hazelcast-hibernate53`; scope `compile`; optional.
- `org.checkerframework:checker-qual`; scope `provided`; optional.

## JPMS descriptor

```java
module com.hazelcast.hibernate {
    requires transitive java.logging;
    requires com.hazelcast.all;

    requires transitive cache.api;
    requires java.transaction.xa;
    requires java.management;

    requires transitive java.xml;
    requires static org.hibernate.orm.core;

    requires jdk.unsupported;

    exports com.hazelcast.hibernate to org.hibernate.orm.core;

    provides com.hazelcast.nio.serialization.SerializerHook with com.hazelcast.hibernate.serialization.Hibernate5CacheEntrySerializerHook
            ;
    provides com.hazelcast.internal.serialization.DataSerializerHook with com.hazelcast.hibernate.serialization.HibernateDataSerializerHook
            ;

    opens com.hazelcast.hibernate.serialization;

}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## Dummy

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/hazelcast-hibernate/src/main/java/com/hazelcast/hibernate/Dummy.java)

### Dummy

```java
public Dummy();
```
