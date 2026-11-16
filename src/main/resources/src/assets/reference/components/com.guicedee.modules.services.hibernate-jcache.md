# com.guicedee.modules.services:hibernate-jcache

JPMS Module-Info's for a few of the Jakarta Libraries. These will be removed as time goes by

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.hibernate.orm.jcache`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Hibernate/hibernate-jcache/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:hibernate-core`; scope `compile`.
- `com.guicedee.modules.services:cache-api`; scope `compile`.
- `org.hibernate.orm:hibernate-jcache`; scope `compile`; optional.
- `jakarta.persistence:jakarta.persistence-api`; scope `compile`.
- `jakarta.inject:jakarta.inject-api`; scope `compile`.
- `jakarta.validation:jakarta.validation-api`; scope `compile`.
- `jakarta.xml.bind:jakarta.xml.bind-api`; scope `compile`.
- `jakarta.transaction:jakarta.transaction-api`; scope `compile`.

## JPMS descriptor

```java
module org.hibernate.orm.jcache {
    requires transitive org.hibernate.orm.core;

    requires transitive cache.api;

    opens org.hibernate.cache.jcache.internal to org.hibernate.orm.core;

    provides org.hibernate.boot.registry.selector.StrategyRegistrationProvider with org.hibernate.cache.jcache.internal.StrategyRegistrationProviderImpl;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
