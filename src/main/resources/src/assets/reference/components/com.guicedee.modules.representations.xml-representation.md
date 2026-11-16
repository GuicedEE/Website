# com.guicedee.modules.representations:xml-representation

XML Representation

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.xmlrepresentation`.
- [Source POM](https://github.com/GuicedEE/Representations/blob/master/xml/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee:client`; scope `compile`.
- `jakarta.xml.bind:jakarta.xml.bind-api`; scope `compile`.
- `org.glassfish.jaxb:jaxb-runtime`; scope `compile`.
- `jakarta.validation:jakarta.validation-api`; scope `compile`.
- `org.projectlombok:lombok`; scope `compile`.

## JPMS descriptor

```java
module com.guicedee.xmlrepresentation {
    exports com.guicedee.modules.services.xmlrepresentation;

    requires transitive org.glassfish.jaxb.runtime;

    requires transitive com.guicedee.client;

    requires static lombok;
    requires java.logging;
    requires java.xml;
    requires jakarta.xml.bind;
    requires jakarta.validation;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Representations/blob/master/xml/README.md). Historical version examples retain their original context.

# XMLRepresentation
Represents objects as XML using JAXB

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## IXmlRepresentation

[Java source](https://github.com/GuicedEE/Representations/blob/master/xml/src/main/java/com/guicedee/services/xmlrepresentation/IXmlRepresentation.java)

### IXmlRepresentation

```java
@SuppressWarnings("unchecked") default J fromXml(String xml, Class<J> type);
@SuppressWarnings("unchecked") default String toXml();
default boolean isResolveExternalEntities();
default boolean isSupportDTD();
default boolean isFormattedOutput();
default boolean isFragment();
```

## XmlRenderException

[Java source](https://github.com/GuicedEE/Representations/blob/master/xml/src/main/java/com/guicedee/services/xmlrepresentation/XmlRenderException.java)

### XmlRenderException

```java
public XmlRenderException();
public XmlRenderException(String message);
public XmlRenderException(String message, Throwable cause);
public XmlRenderException(Throwable cause);
public XmlRenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```
