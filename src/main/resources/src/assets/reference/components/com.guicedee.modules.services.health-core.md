# com.guicedee.modules.services:health-core

health

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.modules.services.health`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/MicroProfile/health-core/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.slf4j:slf4j-api`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.
- `org.eclipse.microprofile.health:microprofile-health-api`; scope `compile`; optional.

## JPMS descriptor

```java
module com.guicedee.modules.services.health {

    requires java.logging;

    exports org.eclipse.microprofile.health;
    exports org.eclipse.microprofile.health.spi;

    uses org.eclipse.microprofile.health.spi.HealthCheckResponseProvider;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
