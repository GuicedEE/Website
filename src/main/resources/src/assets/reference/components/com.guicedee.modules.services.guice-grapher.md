# com.guicedee.modules.services:guice-grapher

Guice Grapher

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.google.guice.extensions.grapher`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Google/guice-grapher/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:guice-assistedinject`; scope `compile`.
- `com.google.inject.extensions:guice-grapher`; scope `compile`; optional.

## JPMS descriptor

```java
module com.google.guice.extensions.grapher {
    requires com.google.guice;

    exports com.google.inject.grapher;

    opens com.google.inject.grapher to com.google.guice;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Google/guice-grapher/README.md). Historical version examples retain their original context.

# guice-grapher
