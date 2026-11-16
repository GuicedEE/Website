# com.guicedee.modules.services:guice-jndi

Guice JNDI

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.google.guice.extensions.jndi`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Google/guice-jndi/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:guice`; scope `compile`.
- `com.google.inject.extensions:guice-jndi`; scope `compile`; optional.

## JPMS descriptor

```java
module com.google.guice.extensions.jndi {
    exports com.google.inject.jndi;

    requires com.google.guice;
    requires jakarta.inject;

    requires java.naming;

    opens com.google.inject.jndi to com.google.guice;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Google/guice-jndi/README.md). Historical version examples retain their original context.

# guice-jndi
