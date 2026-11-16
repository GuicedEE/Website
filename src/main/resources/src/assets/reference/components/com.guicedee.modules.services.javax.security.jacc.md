# com.guicedee.modules.services:javax.security.jacc

JSR-000115 Java(TM) Authorization Contract for Containers API

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `jakarta.security.jacc.api`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Jakarta/jakarta-security-jacc/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `jakarta.security.jacc:jakarta.security.jacc-api`; scope `compile`; optional.

## JPMS descriptor

```java
module jakarta.security.jacc.api {
    requires jakarta.servlet;
    exports javax.security.jacc;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## JavadocGenerator

[Java source](https://github.com/GuicedEE/Services/blob/master/Jakarta/jakarta-security-jacc/src/main/java/org/jacc/JavadocGenerator.java)

### JavadocGenerator

```java
```
