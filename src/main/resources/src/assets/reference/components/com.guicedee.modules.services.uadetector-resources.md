# com.guicedee.modules.services:uadetector-resources

This package contains a verified version of the data file from http://user-agent-string.info. During the verification process the data are checked for ability to run against the current implementation. The goal is to provide frequently updated resources for detection of user agents without having to release a new implementation.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `net.sf.uadetector.resources`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-resources/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:uadetector-core`; scope `compile`.
- `jakarta.validation:jakarta.validation-api`; scope `compile`.

## JPMS descriptor

```java
module net.sf.uadetector.resources {
    exports net.sf.uadetector.service;

    requires jakarta.validation;

    requires net.sf.uadetector.core;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## ResourceFinder

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-resources/src/main/java/net/sf/uadetector/resources/ResourceFinder.java)

### ResourceFinder

```java
```

## UADetectorServiceFactory

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-resources/src/main/java/net/sf/uadetector/service/UADetectorServiceFactory.java)

### UADetectorServiceFactory

```java
public static final ResourceModuleXmlDataStore RESOURCE_MODULE;
public static final UserAgentStringParser RESOURCE_MODULE_PARSER;
public static CustomFallbackXmlDataStore customFallbackXmlDataStore;
public static UserAgentStringParser getCachingAndUpdatingParser(URL dataUrl, URL versionUrl);
public static UserAgentStringParser getCachingAndUpdatingParser(URL dataUrl, URL versionUrl, URL fallbackDataURL, URL fallbackVersionURL);
@Deprecated public static UserAgentStringParser getCachingAndUpdatingParser();
public static UserAgentStringParser getOnlineUpdatingParser(URL dataUrl, URL versionUrl);
public static UserAgentStringParser getOnlineUpdatingParser(URL dataUrl, URL versionUrl, URL fallbackDataUrl, URL fallbackVersionUrl);
@Deprecated public static UserAgentStringParser getOnlineUpdatingParser();
public static UserAgentStringParser getResourceModuleParser();
```

### ResourceModuleXmlDataStore

```java
public static final URL UAS_DATA;
public static final URL UAS_VERSION;
public ResourceModuleXmlDataStore();
```

### CustomFallbackXmlDataStore

```java
protected CustomFallbackXmlDataStore(URL dataUrl, URL versionUrl);
```
