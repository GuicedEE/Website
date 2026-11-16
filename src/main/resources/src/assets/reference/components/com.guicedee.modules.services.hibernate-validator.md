# com.guicedee.modules.services:hibernate-validator

JPMS Module-Info's for a few of the Jakarta Libraries. These will be removed as time goes by

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.hibernate.validator`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Hibernate/hibernate-validator/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `jakarta.el:jakarta.el-api`; scope `compile`.
- `org.jboss.logging:jboss-logging`; scope `compile`.
- `joda-time:joda-time`; scope `provided`; optional.
- `com.fasterxml:classmate`; scope `compile`.
- `org.hibernate.validator:hibernate-validator`; scope `compile`; optional.
- `org.glassfish.expressly:expressly`; scope `compile`; optional.
- `org.javamoney.moneta:moneta-core`; scope `compile`; optional.
- `org.javamoney.moneta:moneta-convert`; scope `compile`; optional.
- `javax.money:money-api`; scope `compile`; optional.
- `org.openjfx:javafx-base`; scope `provided`; optional.
- `com.thoughtworks.paranamer:paranamer`; scope `compile`; optional.
- `jakarta.inject:jakarta.inject-api`; scope `compile`; optional.
- `jakarta.persistence:jakarta.persistence-api`; scope `compile`; optional.

## JPMS descriptor

```java
module org.hibernate.validator {
    requires transitive jakarta.validation;
    requires org.jboss.logging;
    requires transitive java.xml;
    requires com.fasterxml.classmate;
    requires static jakarta.persistence;
    requires static java.scripting;
    requires transitive jakarta.el;
    requires java.desktop;
    requires java.compiler;

    exports org.hibernate.validator;

    exports org.hibernate.validator.spi.cfg;
    exports org.hibernate.validator.spi.group;
    exports org.hibernate.validator.spi.resourceloading;
    exports org.hibernate.validator.spi.scripting;
    exports org.hibernate.validator.cfg;
    exports org.hibernate.validator.cfg.context;
    exports org.hibernate.validator.cfg.defs;
    exports org.hibernate.validator.cfg.defs.br;
    exports org.hibernate.validator.cfg.defs.pl;
    exports org.hibernate.validator.engine;
    exports org.hibernate.validator.constraints;
    exports org.hibernate.validator.constraints.br;
    exports org.hibernate.validator.constraints.pl;
    exports org.hibernate.validator.constraints.time;
    exports org.hibernate.validator.constraintvalidation;
    exports org.hibernate.validator.constraintvalidators;
    exports org.hibernate.validator.group;
    exports org.hibernate.validator.messageinterpolation;
    exports org.hibernate.validator.parameternameprovider;
    exports org.hibernate.validator.path;
    exports org.hibernate.validator.resourceloading;
    exports org.hibernate.validator.internal.util.logging to org.jboss.logging;

    uses org.hibernate.validator.spi.cfg.ConstraintMappingContributor;
    uses org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
    uses org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
    uses org.hibernate.validator.spi.scripting.ScriptEvaluator;
    uses org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

    uses jakarta.validation.valueextraction.ValueExtractor;
    uses jakarta.validation.ConstraintValidator;

    provides jakarta.validation.spi.ValidationProvider with org.hibernate.validator.HibernateValidator;

}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## HibernateValidatorServiceModule

[Java source](https://github.com/GuicedEE/Services/blob/master/Hibernate/hibernate-validator/src/main/java/com/guicedee/services/hibernate/validator/HibernateValidatorServiceModule.java)

### HibernateValidatorServiceModule

```java
```
