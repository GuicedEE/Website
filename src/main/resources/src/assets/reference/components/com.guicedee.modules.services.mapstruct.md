# com.guicedee.modules.services:mapstruct

mapstruct

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.mapstruct`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/mapstruct/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.mapstruct:mapstruct`; scope `compile`; optional.

## JPMS descriptor

```java
module org.mapstruct {
    exports org.mapstruct;
    exports org.mapstruct.util;
    exports org.mapstruct.factory;
    exports org.mapstruct.control;

    requires transitive java.compiler;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## MapStructServiceModule

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/mapstruct/src/main/java/com/guicedee/services/mapstruct/MapStructServiceModule.java)

### MapStructServiceModule

```java
```
