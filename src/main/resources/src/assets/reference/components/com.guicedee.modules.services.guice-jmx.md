# com.guicedee.modules.services:guice-jmx

Guice JMX

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.google.guice.extensions.jmx`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Google/guice-jmx/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:guice`; scope `compile`.
- `com.google.inject.extensions:guice-jmx`; scope `compile`; optional.

## JPMS descriptor

```java
module com.google.guice.extensions.jmx {
    exports com.google.inject.tools.jmx;

    requires com.google.guice;
    requires java.management;

    opens com.google.inject.tools.jmx to com.google.guice;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Google/guice-jmx/README.md). Historical version examples retain their original context.

# guice-jmx
