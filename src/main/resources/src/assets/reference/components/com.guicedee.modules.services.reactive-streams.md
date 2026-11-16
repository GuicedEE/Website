# com.guicedee.modules.services:reactive-streams

A JPMS modularized shade of Reactive Streams (org.reactivestreams:reactive-streams) exposing the org.reactivestreams module for a clean GuicedEE / jlink pipeline. The upstream artifact ships only an Automatic-Module-Name which cannot be used with jlink; this shade adds an explicit module-info.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.reactivestreams`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/reactive-streams/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.reactivestreams:reactive-streams`; scope `compile`; optional.

## JPMS descriptor

```java
module org.reactivestreams {
    exports org.reactivestreams;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
