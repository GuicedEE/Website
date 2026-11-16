# com.guicedee.modules.services:jna-platform-bridge

Provides the JPMS name required by Azure Identity while using GuicedEE's shaded JNA implementation.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.sun.jna.platform`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JNI/jna-platform-bridge/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:jna-platform`; scope `compile`.

## JPMS descriptor

```java
/**
 * Azure Identity requires this JPMS name as well as com.sun.jna. GuicedEE's
 * shaded JNA artifact contains both sets of packages in com.sun.jna; this
 * empty bridge makes the second name resolvable without adding raw JNA jars.
 */
module com.sun.jna.platform {
    requires transitive com.sun.jna;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
