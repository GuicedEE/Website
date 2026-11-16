# com.guicedee:swagger-ui

Installs a Swagger-UI endpoint into GuicedEE

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.swaggerui`.
- [Source POM](https://github.com/GuicedEE/SwaggerUI/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee:web`; scope `compile`.
- `com.guicedee:inject`; scope `test`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `org.junit.jupiter:junit-jupiter-api`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.servlets.swaggerui.SwaggerUIRegistration;

open module com.guicedee.swaggerui {
    requires com.guicedee.vertx.web;
    requires static lombok;

    provides com.guicedee.vertx.web.spi.VertxRouterConfigurator with SwaggerUIRegistration;

    provides com.guicedee.client.services.config.IGuiceScanModuleExclusions with com.guicedee.servlets.swaggerui.SwaggerUIExclusion;

    provides com.guicedee.client.services.config.IGuiceScanJarExclusions with com.guicedee.servlets.swaggerui.SwaggerUIExclusion;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/SwaggerUI/blob/master/README.md). Historical version examples retain their original context.

# GuicedEE Swagger UI

[![Build](https://github.com/GuicedEE/SwaggerUI/actions/workflows/build.yaml/badge.svg)](https://github.com/GuicedEE/SwaggerUI/actions/workflows/build.yaml)
[![Maven Central](https://img.shields.io/maven-central/v/com.guicedee/guiced-swagger-ui)](https://central.sonatype.com/artifact/com.guicedee/guiced-swagger-ui)
[![Snapshots](https://img.shields.io/badge/Snapshots-GitHub%20Packages-orange)](https://github.com/orgs/GuicedEE/packages)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Guice 7](https://img.shields.io/badge/Guice-7%2B-green)
![Vert.x 5](https://img.shields.io/badge/Vert.x-5%2B-green)

**Browsersable Swagger UI** for the [GuicedEE](https://github.com/GuicedEE) / Vert.x stack.
Add the dependency and a fully functional Swagger UI is mounted at `/swagger/` automatically  it reads from your `/openapi.json` endpoint out of the box, with zero code required.

Built on [Swagger UI](https://swagger.io/tools/swagger-ui/)   [Vert.x](https://vertx.io/)   [Google Guice](https://github.com/google/guice)   JPMS module `com.guicedee.swaggerui`   Java 25+

## Installation

```xml
<dependency>
  <groupId>com.guicedee</groupId>
  <artifactId>swagger-ui</artifactId>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.guicedee:guiced-swagger-ui:2.4.1")
```
</details>

> **OpenAPI spec generation**  pair this module with the `openapi` module for automatic spec generation:
>
> ```xml
> <dependency>
>   <groupId>com.guicedee</groupId>
>   <artifactId>openapi</artifactId>
> </dependency>
> ```

## Features

- **Zero-configuration UI mounting**  `SwaggerUIRegistration` registers a Vert.x `StaticHandler` on the router at startup; Swagger UI is live immediately
- **Pre-bundled assets**  all Swagger UI static resources (HTML, JS, CSS, favicons) are included in the JAR under `swagger/`
- **Points at `/openapi.json` by default**  the bundled `swagger-initializer.js` is pre-configured to load the spec from `/openapi.json`
- **Fully configurable**  route path, resource root, caching, directory listing, and index page are all controllable via system properties or environment variables
- **Can be disabled**  set `guicedee.swaggerui.enabled=false` to skip registration entirely (e.g. in production)
- **JPMS + ServiceLoader friendly**  fully modular with `module-info.java` and `META-INF/services` descriptors

## Quick Start

**Step 1**  Add both dependencies (see [Installation](#installation)).

**Step 2**  Bootstrap GuicedEE:

```java
IGuiceContext.instance().inject();
```

**Step 3**  Open your browser:

```
http://localhost:8080/swagger/
```

That's it. The Swagger UI loads and reads from `/openapi.json` automatically. If the `openapi` module is on the classpath your Jakarta REST resources appear in the UI immediately.

## Architecture

```
Startup
  IGuiCeContext.instance()
    +-- %% SwaggerUIRegistration    (Vert.x RouterConfigurator, order MAX_VALUE-100)
        +-- %% Reads configuration from system properties / environment
            +-- Creates StaticHandler for classpath "swagger" directory
                +-- Mounts route on Router at /swagger/* (configurable)
```

### Request lifecycle

```
HTTP GET /swagger/index.html
  +-- !Vert.x Router
      !StaticHandler
        +-- Serves index.html from classpath:swagger/
        +-- Browser loads swagger-ui-bundle.js, CSS, etc.
        +-- swagger-initializer.js fetches(/openapi.json)
        +-- Swagger UI renders the API documentation
```

## Bundled assets

The following Swagger UI files are bundled in the JAR at `src/main/resources/swagger/`:

| File | Purpose |
|---|---|
| `index.html` | Entry point HTML page |
| `swagger-initializer.js` | Configures `SwaggerUIBundle` (points at `/openapi.json`) |
| `swagger-ui-bundle.js` | Core Swagger UI JavaScript |
| `swagger-ui-standalone-preset.js` | Standalone layout preset |
| `swagger-ui.css` / `index.css` | Stylesheets |
| `favicon-16x16.png` / `favicon-32x32.png` | Favicons |
| `oauth2-redirect.html` | OAauth 2 redirect handler |

Classpath assets are streamed in bounded chunks using Vert.x worker reads and asynchronous HTTP
writes. The handler waits for each write before reading the next chunk and closes the resource
after completion or failure; it does not allocate response buffers the size of an entire bundle.

Native metadata includes the default page's runtime assets, favicons and OAuth redirect scripts.
The JAR still contains all distribution files. Native images omit source maps and alternate ES
bundles by default; a custom UI can opt them back in with application reachability metadata:

```json
{"resources":[{"module":"com.guicedee.swaggerui","glob":"swagger/**"}]}
```

For a custom resource root or index page, register those resources in the consuming native
application's metadata as well. Runtime route, resource-root and cache configuration still apply.

## Configuration

All configuration is driven by system properties with automatic environment variable fallbacks (via `Environment.getSystemPropertyOrEnvironment`).

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## SwaggerUIExclusion

[Java source](https://github.com/GuicedEE/SwaggerUI/blob/master/src/main/java/com/guicedee/servlets/swaggerui/SwaggerUIExclusion.java)

### SwaggerUIExclusion

```java
@Override public Set<String> excludeModules();
@Override public Set<String> excludeJars();
```

## SwaggerUIRegistration

[Java source](https://github.com/GuicedEE/SwaggerUI/blob/master/src/main/java/com/guicedee/servlets/swaggerui/SwaggerUIRegistration.java)

### SwaggerUIRegistration

```java
@Override public Integer sortOrder();
public static final String PROPERTY_ENABLED;
public static final String PROPERTY_ROUTE;
public static final String PROPERTY_RESOURCE_ROOT;
public static final String PROPERTY_FILES_READ_ONLY;
public static final String PROPERTY_CACHE_ENABLED;
public static final String PROPERTY_CACHE_MAX_AGE_SECONDS;
public static final String PROPERTY_DIRECTORY_LISTING;
public static final String PROPERTY_INDEX_PAGE;
public static final boolean DEFAULT_ENABLED;
public static final String DEFAULT_ROUTE;
public static final String DEFAULT_RESOURCE_ROOT;
public static final boolean DEFAULT_FILES_READ_ONLY;
public static final boolean DEFAULT_CACHE_ENABLED;
public static final long DEFAULT_CACHE_MAX_AGE_SECONDS;
public static final boolean DEFAULT_DIRECTORY_LISTING;
public static final String DEFAULT_INDEX_PAGE;
@Override public Router builder(Router router);
```
