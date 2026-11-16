# com.guicedee.modules.services:commons-beanutils

Apache Commons BeanUtils provides an easy-to-use but flexible wrapper around reflection and introspection.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.commons.beanutils`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/Commons/commons-beanutils/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:commons-collections4`; scope `compile`.
- `commons-logging:commons-logging`; scope `compile`.
- `commons-beanutils:commons-beanutils`; scope `compile`; optional.

## JPMS descriptor

```java
module org.apache.commons.beanutils {
    exports org.apache.commons.beanutils;

    requires java.xml;
    requires java.desktop;
    requires java.sql;

    requires org.apache.commons.logging;
    requires org.apache.commons.collections4;

    exports org.apache.commons.beanutils.converters;
    exports org.apache.commons.beanutils.expression;
    exports org.apache.commons.beanutils.locale;
    exports org.apache.commons.beanutils.locale.converters;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
