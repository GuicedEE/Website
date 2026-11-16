# com.guicedee.modules.services:config-core

config-core

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `io.smallrye.config.core`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/MicroProfile/config-core/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `jakarta.annotation:jakarta.annotation-api`; scope `compile`.
- `com.guicedee:client`; scope `compile`.
- `io.smallrye.config:smallrye-config-core`; scope `compile`; optional.
- `org.jboss.logging:jboss-logging`; scope `compile`.
- `org.eclipse.microprofile.context-propagation:microprofile-context-propagation-api`; scope `compile`; optional.
- `io.smallrye.config:smallrye-config-common`; scope `compile`; optional.
- `org.eclipse.microprofile.config:microprofile-config-api`; scope `compile`; optional.
- `io.smallrye.common:smallrye-common-annotation`; scope `compile`.
- `io.smallrye.common:smallrye-common-expression`; scope `compile`.
- `io.smallrye.common:smallrye-common-function`; scope `compile`.
- `io.smallrye.common:smallrye-common-constraint`; scope `compile`.
- `io.smallrye.common:smallrye-common-classloader`; scope `compile`.

## JPMS descriptor

```java
module io.smallrye.config.core {
    exports io.smallrye.config;
    exports org.eclipse.microprofile.config;
    exports org.eclipse.microprofile.config.spi;
    exports org.eclipse.microprofile.config.inject;

    requires io.smallrye.common.classloader;
    requires com.guicedee.client;
    requires jakarta.annotation;
    requires org.jboss.logging;
    requires io.smallrye.common.constraint;

  exports org.eclipse.microprofile.context;
  exports org.eclipse.microprofile.context.spi;

    exports io.smallrye.config._private to org.jboss.logging;
    opens io.smallrye.config._private to org.jboss.logging;

    uses org.eclipse.microprofile.config.spi.ConfigSource;
    uses org.eclipse.microprofile.config.spi.ConfigSourceProvider;
    uses io.smallrye.config.ConfigSourceFactory;
    uses org.eclipse.microprofile.config.spi.ConfigProviderResolver;
    uses org.eclipse.microprofile.context.spi.ContextManagerProvider;
    uses io.smallrye.config.ConfigSourceInterceptor;
    uses io.smallrye.config.ConfigSourceInterceptorFactory;
    uses org.eclipse.microprofile.config.spi.Converter;
    uses io.smallrye.config.SmallRyeConfigFactory;
    uses io.smallrye.config.SmallRyeConfigBuilderCustomizer;
    uses io.smallrye.config.SecretKeysHandler;
    uses io.smallrye.config.SecretKeysHandlerFactory;
    uses io.smallrye.config.ConfigValidator;

    provides io.smallrye.config.ConfigSourceFactory with io.smallrye.config.PropertiesLocationConfigSourceFactory;
    provides org.eclipse.microprofile.config.spi.ConfigProviderResolver with io.smallrye.config.SmallRyeConfigProviderResolver;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## AbstractLocationConfigSourceLoader

[Java source](https://github.com/GuicedEE/Services/blob/master/MicroProfile/config-core/src/main/java/io/smallrye/config/AbstractLocationConfigSourceLoader.java)

### AbstractLocationConfigSourceLoader

```java
protected boolean failOnMissingFile();
protected abstract String[] getFileExtensions();
protected abstract ConfigSource loadConfigSource(final URL url, final int ordinal) throws IOException;
protected List<ConfigSource> loadConfigSources(final String location, final int ordinal);
protected List<ConfigSource> loadConfigSources(final String location, final int ordinal, final ClassLoader classLoader);
protected List<ConfigSource> loadConfigSources(final String[] locations, final int ordinal);
protected List<ConfigSource> loadConfigSources(final String[] locations, final int ordinal, final ClassLoader classLoader);
protected List<ConfigSource> tryFileSystem(final URI uri, final int ordinal);
protected List<ConfigSource> tryClassPath(final URI uri, final int ordinal, final ClassLoader classLoader);
protected List<ConfigSource> tryJar(final URI uri, final int ordinal);
protected List<ConfigSource> fallbackToUnknownProtocol(final URI uri, final int ordinal, final ClassLoader classLoader);
protected List<ConfigSource> tryHttpResource(final URI uri, final int ordinal);
protected List<ConfigSource> tryJrtResource(final URI uri, final int ordinal);
```
