# com.guicedee.modules.services:guice-assistedinject

Guice Assisted Inject

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.google.guice.extensions.assistedinject`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Google/guice-assistedinject/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:guice`; scope `compile`.
- `com.google.inject.extensions:guice-assistedinject`; scope `compile`; optional.

## JPMS descriptor

```java
module com.google.guice.extensions.assistedinject {
    requires com.google.guice;

    exports com.google.inject.assistedinject;
    opens com.google.inject.assistedinject to com.google.guice;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Google/guice-assistedinject/README.md). Historical version examples retain their original context.

# guice-assistedinject
