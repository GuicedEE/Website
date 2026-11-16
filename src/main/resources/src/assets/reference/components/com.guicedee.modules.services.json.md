# com.guicedee.modules.services:json

JSON is a light-weight, language independent, data interchange format. See http://www.JSON.org/ The files in this package implement JSON encoders/decoders in Java. It also includes the capability to convert between JSON and XML, HTTP headers, Cookies, and CDL. This is a reference implementation. There is a large number of JSON packages in Java. Perhaps someday the Java community will standardize on one. Until then, choose carefully. The license includes this restriction: "The software shall be used for good, not evil." If your conscience cannot live with that, then choose a different package.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.json`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/json/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.json:json`; scope `compile`; optional.

## JPMS descriptor

```java
module org.json {
    exports org.json;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Libraries/json/README.md). Historical version examples retain their original context.

This is a helper script to easily package Douglas Crockfords JSON in Java library from sources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## JsonJavadocGenerator

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/json/src/main/java/org/json/JsonJavadocGenerator.java)

### JsonJavadocGenerator

```java
```
