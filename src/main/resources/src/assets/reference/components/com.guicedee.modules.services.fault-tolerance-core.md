# com.guicedee.modules.services:fault-tolerance-core

fault-tolerance

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.modules.services.faulttolerance`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/MicroProfile/fault-tolerance-core/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.eclipse.microprofile.fault-tolerance:microprofile-fault-tolerance-api`; scope `compile`; optional.

## JPMS descriptor

```java
module com.guicedee.modules.services.faulttolerance {

    exports org.eclipse.microprofile.faulttolerance;
    exports org.eclipse.microprofile.faulttolerance.exceptions;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
