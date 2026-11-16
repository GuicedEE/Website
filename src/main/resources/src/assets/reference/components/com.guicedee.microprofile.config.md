# com.guicedee.microprofile:config

MicroProfile Config implementation for GuicedEE

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.microprofile.config`.
- [Source POM](https://github.com/GuicedEE/microprofile-config/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.projectlombok:lombok`; scope `compile`.
- `com.guicedee:client`; scope `compile`.
- `com.guicedee:vertx`; scope `compile`.
- `com.guicedee.modules.services:config-core`; scope `compile`.
- `org.junit.jupiter:junit-jupiter-api`; scope `test`.
- `com.guicedee:inject`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.client.services.lifecycle.IGuiceConfigurator;
import com.guicedee.client.services.lifecycle.IGuiceModule;
import com.guicedee.client.services.lifecycle.IGuicePreStartup;
import com.guicedee.microprofile.config.*;
import com.guicedee.microprofile.config.implementations.*;

module com.guicedee.microprofile.config {

    exports com.guicedee.microprofile.config;
    requires transitive io.smallrye.config.core;
    requires static lombok;
    requires transitive com.guicedee.vertx;

    provides IGuicePreStartup with MicroProfileConfigContext;
    provides IGuiceModule with MicroProfileConfigBinder;
    provides com.google.inject.gee.InjectionPointProvider with InjectionPointProvision;
    provides com.google.inject.gee.BindingAnnotationProvider with ConfigPropertyBindingAnnotationProvider;
    provides com.google.inject.gee.NamedAnnotationProvider with ConfigPropertyNamedProvider;
    provides IGuiceConfigurator with ScanConfig;

    opens com.guicedee.microprofile.config.implementations to com.google.guice;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/microprofile-config/blob/master/README.md). Historical version examples retain their original context.

# GuicedEE MicroProfile Config

[![Build](https://github.com/GuicedEE/microprofile-config/actions/workflows/build.yml/badge.svg)](https://github.com/GuicedEE/Guiced-MicroProfileConfig/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.guicedee.microprofile/config)](https://central.sonatype.com/artifact/com.guicedee.microprofile/config)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Guice 7](https://img.shields.io/badge/Guice-7%2B-green)
![Vert.X 5](https://img.shields.io/badge/Vert.x-5%2B-green)

MicroProfile Config implementation for [GuicedEE](https://github.com/GuicedEE) applications using **SmallRye Config** and **Google Guice**.
Inject configuration values with `@ConfigProperty`, resolve from environment variables, system properties, and `META-INF/microprofile-config.properties` — all wired automatically through SPI discovery and Guice bindings.

Built on [SmallRye Config](https://smallrye.io/smallrye-config/) · [MicroProfile Config](https://github.com/eclipse/microprofile-config) · [Google Guice](https://github.com/google/guice) · [Vert.x](https://vertx.io/) · JPMS module `com.guicedee.microprofile.config` · Java 25+

## 📦 Installation

Configuration is an opt-in integration. `com.guicedee:inject` does not depend on this module, `config-core`, or the MicroProfile Config API. Add the dependency below only to applications that need MicroProfile Config; its configurator, startup task and Guice bindings are discovered through the existing client lifecycle SPIs. Providers are registered for both JPMS and `META-INF/services` launches.

```xml
<dependency>
  <groupId>com.guicedee.microprofile</groupId>
  <artifactId>config</artifactId>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.guicedee.microprofile:config:2.5.1")
```
</details>

For JPMS applications, also declare `requires com.guicedee.microprofile.config;`.

### SPI-only application discovery

With `GUICEDEE_DISCOVERY_MODE=spi`, register classes containing `@ConfigProperty` fields through the client-owned `IGuiceDiscovery` SPI:

```java
public final class ApplicationDiscovery implements IGuiceDiscovery<ApplicationDiscovery> {
    public Collection<Class<?>> classes() { return List.of(ApplicationSettings.class); }
}
```

Register the provider in `module-info.java` (`provides com.guicedee.client.services.IGuiceDiscovery with my.app.ApplicationDiscovery;`) and `META-INF/services/com.guicedee.client.services.IGuiceDiscovery`. Open the injected classes' packages to `com.google.guice` as usual.

Config field discovery then uses registered classes; default property resources are loaded directly through the application classloader, without creating ClassGraph. Profile files remain paired with their own base resource, including resources in JARs. Custom `ConfigSource` and `Converter` implementations retain their standard MicroProfile service registrations. The default `scan` mode continues to support annotation discovery and ClassGraph-backed resource lookup.

## ✨ Features

- **CDI-style injection** — `@ConfigProperty(name = "key")` with automatic type conversion for `String`, `Boolean`, `Integer`, `Long`, `Double`, `Float`, and `Optional<T>` wrappers
- **Standards-compliant** — implements `org.eclipse.microprofile.config` APIs and annotations via SmallRye Config
- **Deterministic source ordering** — environment variables (highest), system properties, and classpath `META-INF/microprofile-config.properties` (lowest)
- **Custom converters** — register `Converter<T>` implementations via `ServiceLoader` for application-specific types
- **Profile support** — profile-specific properties (e.g. `%dev.key=value`) resolved by SmallRye Config
- **Guice-native** — `SmallRyeConfig` is bound as a singleton via `SmallRyeConfigProvider`; `@ConfigProperty` fields are scanned and bound at startup
- **Vert.x-aware initialization** — config is built on a Vert.x worker thread to avoid blocking the event loop
- **JPMS-ready** — named module with proper exports, provides, and opens directives

## 🚀 Quick Start

**Step 1** — Add a `microprofile-config.properties` file:

```properties
# src/main/resources/META-INF/microprofile-config.properties
messaging.enabled=true
messaging.bootstrap.servers=localhost:9092
liveness.port=8081
```

**Step 2** — Inject configuration values:

```java
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class MessagingService {

    @ConfigProperty(name = "messaging.enabled", defaultValue = "true")
    boolean enabled;

    @ConfigProperty(name = "messaging.bootstrap.servers")
    String bootstrapServers;

    public void start() {
        if (enabled) {
            // use bootstrapServers
        }
    }
}
```

**Step 3** — Register via JPMS:

```java
module my.app {
    requires com.guicedee.microprofile.config;
}
```

The `MicroProfileConfigContext` initializes SmallRye Config automatically during `IGuicePreStartup`, and `MicroProfileConfigBinder` scans for all `@ConfigProperty` fields and binds them into Guice.

## 📐 Architecture

```mermaid
flowchart TD
    n1["IGuiceContext.instance()"]
    n2["IGuiceConfigurator hooks"]
    n1 --> n2
    n3["ScanConfig<br/>enables field, annotation, classpath scanning"]
    n2 --> n3
    n4["IGuicePreStartup hooks"]
    n1 --> n4
    n5["MicroProfileConfigContext<br/>builds SmallRyeConfig on Vert.x worker thread"]
    n4 --> n5
    n6["addDefaultSources()<br/>env vars, system props, microprofile-config.properties"]
    n5 --> n6
    n7["addDiscoveredSources()<br/>ServiceLoader-discovered ConfigSource SPIs"]
    n5 --> n7
    n8["addDiscoveredConverters()<br/>ServiceLoader-discovered Converter<T> SPIs"]
    n5 --> n8
    n9["addDiscoveredInterceptors()<br/>ConfigSourceInterceptor SPIs"]
    n5 --> n9
    n10["IGuiceModule hooks"]
    n1 --> n10
    n11["MicroProfileConfigBinder<br/>Guice AbstractModule"]
    n10 --> n11
    n12["bind SmallRyeConfig via SmallRyeConfigProvider<br/>singleton"]
    n11 --> n12
    n13["scan @ConfigProperty fields via ClassGraph"]
    n11 --> n13
    n14["bind each field type: String, Boolean, Integer, Long, Double, Float, Optional<T>"]
    n11 --> n14
    n15["InjectionPointProvider"]
    n1 --> n15
    n16["InjectionPointProvision<br/>registers @ConfigProperty as a Guice injection point"]
    n15 --> n16
```

## ⚙️ Configuration Sources and Resolution

MicroProfile Config defines a composite of sources with numeric ordinals. Higher ordinal wins when keys overlap:

| Source | Ordinal | Example |
|---|---|---|
| Environment variables | 300 | `MESSAGING_ENABLED=true` |
| System properties | 200 | `-Dmessaging.enabled=true` |
| `META-INF/microprofile-config.properties` | 100 | `messaging.enabled=true` |

### Key mapping

Environment variable names follow MicroProfile Config rules:
- Dots (`.`) and hyphens (`-`) are replaced with underscores (`_`)
- Names are uppercased
- e.g. `messaging.bootstrap.servers` → `MESSAGING_BOOTSTRAP_SERVERS`

### Profiles

SmallRye Config supports profile-specific properties using the `%profile.` prefix:

```properties
# META-INF/microprofile-config.properties
db.url=jdbc:postgresql://prod-host:5432/mydb

# Profile-specific override
%dev.db.url=jdbc:postgresql://localhost:5432/mydb
```

Activate a profile with `mp.config.profile=dev` (system property or environment variable).

## 🔄 Programmatic Access

Inject the `SmallRyeConfig` instance directly for programmatic lookups:

```java
import jakarta.inject.Inject;
import io.smallrye.config.SmallRyeConfig;

public class HealthProbe {

    @Inject
    SmallRyeConfig config;

    public int livenessPort() {
        return config.getOptionalValue("liveness.port", Integer.class).orElse(8081);
    }
}
```

Or use the standard MicroProfile `Config` interface:

```java
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

Config config = ConfigProvider.getConfig();
String value = config.getValue("messaging.enabled", String.class);
```

## 🔌 Supported Injection Types

`MicroProfileConfigBinder` scans all classes with `@ConfigProperty`-annotated fields and creates Guice bindings for each:

| Field type | Binding |
|---|---|
| `String` | Direct value from config |
| `boolean` / `Boolean` | Parsed via `Boolean.parseBoolean()` |
| `int` / `Integer` | Parsed via `Integer.parseInt()` |
| `long` / `Long` | Parsed via `Long.parseLong()` |
| `double` / `Double` | Parsed via `Double.parseDouble()` |
| `float` / `Float` | Parsed via `Float.parseFloat()` |
| `Optional<String>` | Wrapped optional |
| `Optional<Boolean>` | Wrapped optional |
| `Optional<Integer>` | Wrapped optional |
| `Optional<Long>` | Wrapped optional |
| `Optional<Double>` | Wrapped optional |
| `Optional<Float>` | Wrapped optional |

Default values are supported via `@ConfigProperty(defaultValue = "...")`.

## 🧩 Custom Converters

Register custom `Converter<T>` implementations via `ServiceLoader` for application-specific types:

```java
import org.eclipse.microprofile.config.spi.Converter;
import java.time.Duration;

public class DurationConverter implements Converter<Duration> {

    @Override
    public Duration convert(String value) {
        if (value.endsWith("ms")) return Duration.ofMillis(Long.parseLong(value.replace("ms", "")));
        if (value.endsWith("s")) return Duration.ofSeconds(Long.parseLong(value.replace("s", "")));
        throw new IllegalArgumentException("Unsupported duration format: " + value);
    }
}
```

Register via `META-INF/services/org.eclipse.microprofile.config.spi.Converter`:

```
com.example.DurationConverter
```

And then with JPMS:

```java
module my.app {
    provides org.eclipse.microprofile.config.spi.Converter
        with com.example.DurationConverter;
}
```

## 🔧 SPI Extension Points

All SPIs are discovered via `ServiceLoader`. Register implementations with JPMS `provides...with` or `META-INF/services`.

| SPI | Purpose |
|---|---|
| `IGuicePreStartup` | `MicroProfileConfigContext` — builds the `SmallRyeConfig` instance |
| `IGuiceModule` | `MicroProfileConfigBinder` — scans `@ConfigProperty` fields and binds to Guice |
| `IGuiceConfigurator` | `ScanConfig` — enables classpath, annotation, and field scanning |
| `InjectionPointProvider` | `InjectionPointProvision` — registers `@ConfigProperty` as an injection point |
| `ConfigSource` (MicroProfile) | Add custom config sources with custom ordinals |
| `Converter<T>` (MicroProfile) | Register custom type converters |
| `ConfigSourceInterceptor` (SmallRye) | Intercept and transform config values |

## 🗺️ Module Graph

```mermaid
flowchart LR
    com_guicedee_microprofile_config["com.guicedee.microprofile.config"]
    com_guicedee_microprofile_config --> io_smallrye_config_core["io.smallrye.config.core<br/>SmallRye Config — MicroProfile Config implementation"]
    com_guicedee_microprofile_config --> com_guicedee_vertx["com.guicedee.vertx<br/>Vert.x lifecycle — worker thread initialization"]
    com_guicedee_microprofile_config --> com_guicedee_client["com.guicedee.client<br/>GuicedEE client — SPI contracts, IGuiceContext"]
    com_guicedee_microprofile_config --> com_google_guice["com.google.guice<br/>Guice DI — injection bindings"]
```

## 🏗️ Key Classes

| Class | Package | Role |
|---|---|---|
| `MicroProfileConfigContext` | `config` | `IGuicePreStartup` — builds `SmallRyeConfig` on a Vert.x worker thread at startup |
| `MicroProfileConfigBinder` | `implementations` | `IGuiceModule` — scans `@ConfigProperty` fields and creates Guice bindings per type |
| `SmallRyeConfigProvider` | `implementations` | Guice `Provider<SmallRyeConfig>` — returns the shared config instance |
| `InjectionPointProvision` | `implementations` | `InjectionPointProvider` — registers `@ConfigProperty` for Guice injection point processing |
| `ScanConfig` | `implementations` | `IGuiceConfigurator` — enables classpath, annotation, and field scanning |

## 🧩 JPMS

Module name: **`com.guicedee.microprofile.config`**

The module:
- **exports** `com.guicedee.microprofile.config`
- **requires transitive** `io.smallrye.config.core`, `com.guicedee.vertx`
- **provides** `IGuicePreStartup` with `MicroProfileConfigContext`
- **provides** `IGuiceModule` with `MicroProfileConfigBinder`
- **provides** `InjectionPointProvider` with `InjectionPointProvision`
- **provides** `IGuiceConfigurator` with `ScanConfig`
- **opens** `com.guicedee.microprofile.config.implementations` to `com.google.guice`

In non-JPMS environments, `META-INF/services` discovery still works.

## 🧪 Testing

```java
import com.guicedee.client.IGuiceContext;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void configPropertyFieldsAreInjected() {
        Injector injector = IGuiceContext.getContext().inject();
        MyConfigBean bean = injector.getInstance(MyConfigBean.class);
        assertNotNull(bean.getServerHost());
    }
}
```

Provide test configuration in `src/test/resources/META-INF/microprofile-config.properties`:

```properties
server.host=localhost
server.port=8080
```

## 🤝 Contributing

Issues and pull requests are welcome — please add tests for new type converters, config sources, or binding changes.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## ConfigPropertiesValidator

[Java source](https://github.com/GuicedEE/microprofile-config/blob/master/src/main/java/com/guicedee/microprofile/config/ConfigPropertiesValidator.java)

### ConfigPropertiesValidator

```java
public static final String DEFAULT_RESOURCE;
public static List<String> validateDefaultSources();
public static List<String> validate(String resourceName);
public static Set<URL> locate(String resourceName);
public static Optional<String> describeProblem(URL url);
public static void main(String[] args);
```

## ConfigPropertyBindingAnnotationProvider

[Java source](https://github.com/GuicedEE/microprofile-config/blob/master/src/main/java/com/guicedee/microprofile/config/implementations/ConfigPropertyBindingAnnotationProvider.java)

### ConfigPropertyBindingAnnotationProvider

```java
@Override public List<Class<? extends Annotation>> getBindingAnnotations();
```

## ConfigPropertyNamedProvider

[Java source](https://github.com/GuicedEE/microprofile-config/blob/master/src/main/java/com/guicedee/microprofile/config/implementations/ConfigPropertyNamedProvider.java)

### ConfigPropertyNamedProvider

```java
@Override public Named getNamedAnnotation(Annotation annotation);
@Override public Named getNamedAnnotation(Class<? extends Annotation> annotationType);
```

## InjectionPointProvision

[Java source](https://github.com/GuicedEE/microprofile-config/blob/master/src/main/java/com/guicedee/microprofile/config/implementations/InjectionPointProvision.java)

### InjectionPointProvision

```java
@Override public Class<? extends Annotation> injectionPoint(AnnotatedElement member);
```

## MicroProfileConfigBinder

[Java source](https://github.com/GuicedEE/microprofile-config/blob/master/src/main/java/com/guicedee/microprofile/config/implementations/MicroProfileConfigBinder.java)

### MicroProfileConfigBinder

```java
@Override protected void configure();
```

## ScanConfig

[Java source](https://github.com/GuicedEE/microprofile-config/blob/master/src/main/java/com/guicedee/microprofile/config/implementations/ScanConfig.java)

### ScanConfig

```java
@Override public IGuiceConfig<?> configure(IGuiceConfig<?> config);
```

## SmallRyeConfigProvider

[Java source](https://github.com/GuicedEE/microprofile-config/blob/master/src/main/java/com/guicedee/microprofile/config/implementations/SmallRyeConfigProvider.java)

### SmallRyeConfigProvider

```java
@Override public SmallRyeConfig get();
```

## MicroProfileConfigContext

[Java source](https://github.com/GuicedEE/microprofile-config/blob/master/src/main/java/com/guicedee/microprofile/config/MicroProfileConfigContext.java)

### MicroProfileConfigContext

```java
@Override public List<Future<Boolean>> onStartup();
```
