# com.guicedee.microprofile:jwt

MicroProfile JWT Auth bridge for GuicedEE â€” maps MP JWT claims and JsonWebToken to Vert.x JWT Auth

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.microprofile.jwt`.
- [Source POM](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.projectlombok:lombok`; scope `compile`.
- `com.guicedee:inject`; scope `compile`.
- `io.vertx:vertx-auth-jwt`; scope `compile`.
- `com.guicedee.modules.services:microprofile-jwt-auth-api`; scope `compile`.
- `jakarta.json:jakarta.json-api`; scope `compile`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `org.mockito:mockito-core`; scope `test`.
- `org.mockito:mockito-junit-jupiter`; scope `test`.
- `com.guicedee.modules.services:testcontainers`; scope `test`.
- `io.vertx:vertx-web-client`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.client.services.lifecycle.IGuiceModule;
import com.guicedee.client.services.lifecycle.IGuicePreStartup;
import com.guicedee.microprofile.jwt.implementations.*;

module com.guicedee.microprofile.jwt {
    requires transitive com.guicedee.guicedinjection;
    requires transitive io.vertx.auth.jwt; // optional at runtime — accessed via reflection when present

    requires static lombok;

    exports com.guicedee.microprofile.jwt;
    exports com.guicedee.microprofile.jwt.implementations;

    // MP JWT API re-export
    requires transitive microprofile.jwt.auth.api;
    requires transitive jakarta.json;

    opens com.guicedee.microprofile.jwt to com.google.guice, com.guicedee.client, com.guicedee.guicedinjection, tools.jackson.databind;
    opens com.guicedee.microprofile.jwt.implementations to com.google.guice, com.guicedee.client, com.guicedee.guicedinjection;

    provides IGuiceModule with MicroProfileJwtModule;
    provides IGuicePreStartup with MicroProfileJwtPreStartup;

    // SPI registrations for @Claim as injectable annotation
    provides com.google.inject.gee.InjectionPointProvider with ClaimInjectionPointProvider;
    provides com.google.inject.gee.NamedAnnotationProvider with ClaimNamedAnnotationProvider;
    provides com.google.inject.gee.BindingAnnotationProvider with ClaimBindingAnnotationProvider;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/README.md). Historical version examples retain their original context.

# GuicedEE MicroProfile JWT

[![Build](https://github.com/GuicedEE/microprofile-jwt/actions/workflows/build.yml/badge.svg)](https://github.com/GuicedEE/microprofile-jwt/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.guicedee.microprofile/jwt)](https://central.sonatype.com/artifact/com.guicedee.microprofile/jwt)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Guice 7](https://img.shields.io/badge/Guice-7%2B-green)
![Vert.X 5](https://img.shields.io/badge/Vert.x-5%2B-green)

MicroProfile JWT Auth bridge for [GuicedEE](https://github.com/GuicedEE) — maps the MP JWT `JsonWebToken` interface and `@Claim` injection to the Vert.x JWT Auth provider used by GuicedEE.

Built on [MicroProfile JWT](https://github.com/eclipse/microprofile-jwt-auth) · [Vert.x Auth JWT](https://vertx.io/docs/vertx-auth-jwt/java/) · [Google Guice](https://github.com/google/guice) · JPMS module `com.guicedee.microprofile.jwt` · Java 25+

## 📦 Installation

```xml
<dependency>
  <groupId>com.guicedee.microprofile</groupId>
  <artifactId>jwt</artifactId>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.guicedee.microprofile:jwt:2.4.1")
```
</details>

## ✨ Features

- **`JsonWebToken` injection** — backed by Vert.x `User` principal, resolved per-request from the GuicedEE `CallScope`
- **`@Claim` injection without `@Inject`** — `@Claim("sub")` fields are auto-injected via `InjectionPointProvider` and `NamedAnnotationProvider` SPIs, mirroring the `@ConfigProperty` pattern
- **Type-safe claim bindings** — `String`, `Set<String>`, `Long`, `Integer`, `Boolean`, and `Optional<T>` wrappers supported per claim
- **Seamless bridge** — works with `@JwtAuthOptions` from the `com.guicedee.vertx` auth module
- **CallScope-aware context** — JWT context stored in GuicedEE `CallScopeProperties` for correct behavior across Mutiny/reactive thread switches; falls back to `ThreadLocal` outside call scopes
- **Optional auth dependency** — auth provider classes are accessed via reflection; the module works without the JWT auth provider on the module path
- **JPMS-ready** — named module with proper exports, provides, and opens directives

## 🚀 Quick Start

**Step 1** — Configure JWT auth on your `package-info.java` (see [Vert.x Auth docs](https://github.com/GuicedEE/Guiced-Vert.x)):

```java
@JwtAuthOptions(
    keystorePath = "keystore.jceks",
    keystoreType = "jceks",
    keystorePassword = "${JWT_KEYSTORE_PASSWORD}",
    algorithm = "RS256",
    authorizationType = JwtAuthorizationType.MICROPROFILE
)
package com.example.auth;
```

**Step 2** — Inject MP JWT types (no `@Inject` needed on `@Claim` fields):

```java
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;

public class UserService {

    @Inject
    private JsonWebToken jwt;

    @Claim("sub")
    private String subject;

    @Claim("groups")
    private Set<String> groups;

    @Claim("exp")
    private long expiration;

    public String currentUser() {
        return subject;
    }
}
```

**Step 3** — Register via JPMS:

```java
module com.example {
    requires com.guicedee.microprofile.jwt;
    opens com.example to com.google.guice;
}
```

## 📐 Architecture

```mermaid
flowchart TD
    A["IGuiceContext.instance()"] --> B["IGuicePreStartup hooks"]
    B --> B1["MicroProfileJwtPreStartup<br/>validates JWTAuth provider via reflection"]
    A --> C["IGuiceModule hooks"]
    C --> C1["MicroProfileJwtModule"]
    C1 --> C1a["bind JsonWebToken via MicroProfileJwtContext"]
    C1 --> C1b["scan @Claim fields via ClassGraph"]
    C1 --> C1c["bind per type: String, Set, Long, Integer, Boolean, Object"]
    A --> D["SPI Registrations"]
    D --> D1["ClaimInjectionPointProvider<br/>registers @Claim as injection point"]
    D --> D2["ClaimNamedAnnotationProvider<br/>converts @Claim to @Named"]
    D --> D3["ClaimBindingAnnotationProvider<br/>registers @Qualifier as binding annotation"]
```

### Bridge Model

```mermaid
flowchart LR
    subgraph mp["MicroProfile JWT API"]
        jwt["JsonWebToken"]
        claim["@Claim('sub')"]
        claims["Claims enum"]
    end
    subgraph vx["Vert.x Auth"]
        principal["User.principal()"]
        attrs["User.attributes()"]
        jwtauth["JWTAuth"]
    end
    principal -->|"bridges via"| bridge["VertxJsonWebToken"]
    attrs --> bridge
    bridge --> jwt
    jwtauth -->|"configured by"| provider["JwtAuthenticationProvider<br/>from com.guicedee.vertx"]
```

## 🔌 Supported Injection Types

`MicroProfileJwtModule` scans all classes with `@Claim`-annotated fields and creates Guice bindings for each:

| Field type | Binding | Example claim |
|---|---|---|
| `String` | Direct value from JWT claim | `sub`, `iss`, `jti` |
| `Set<String>` | JSON array → `Set<String>` | `groups`, `aud` |
| `long` / `Long` | Numeric claim | `exp`, `iat` |
| `int` / `Integer` | Numeric claim | custom claims |
| `boolean` / `Boolean` | Boolean claim | custom claims |
| `Optional<String>` | Wrapped optional | any claim |
| `Object` | Raw claim value (fallback) | any claim |

## ⚙️ Request Context

The JWT context is managed per-request via `MicroProfileJwtContext`:

```java
// In a route handler or filter:
MicroProfileJwtPreStartup.setCurrentUser(routingContext.user());
try {
    // handle request — @Claim fields resolve from this context
} finally {
    MicroProfileJwtContext.clear();
}
```

### Context storage strategy

| Environment | Storage | Thread-safe across switches |
|---|---|---|
| Inside `CallScope` (HTTP handlers) | `CallScopeProperties.properties` map | ✅ Yes |
| Outside `CallScope` (tests, CLI) | `ThreadLocal` fallback | ✅ Yes (same thread) |

## 🔧 SPI Extension Points

All SPIs are discovered via `ServiceLoader`. Register implementations with JPMS `provides...with` or `META-INF/services`.

| SPI | Implementation | Purpose |
|---|---|---|
| `IGuicePreStartup` | `MicroProfileJwtPreStartup` | Validates JWT auth provider availability at startup |
| `IGuiceModule` | `MicroProfileJwtModule` | Scans `@Claim` fields, binds `JsonWebToken` and claims to Guice |
| `InjectionPointProvider` | `ClaimInjectionPointProvider` | Registers `@Claim` as a Guice injection point (no `@Inject` needed) |
| `NamedAnnotationProvider` | `ClaimNamedAnnotationProvider` | Converts `@Claim("x")` → `@Named("x")` for binding keys |
| `BindingAnnotationProvider` | `ClaimBindingAnnotationProvider` | Registers `@jakarta.inject.Qualifier` as a binding annotation |

## 🗺️ Module Graph

```mermaid
flowchart LR
    jwt["com.guicedee.microprofile.jwt"] --> vertx["com.guicedee.vertx<br/>Vert.x lifecycle, CallScope, auth SPIs"]
    jwt --> injection["com.guicedee.guicedinjection<br/>GuicedEE runtime — classpath scanning"]
    jwt --> client["com.guicedee.client<br/>SPI contracts — IGuiceContext, CallScopeProperties"]
    jwt --> authjwt["io.vertx.auth.jwt<br/>Vert.x JWT Auth — optional, via reflection"]
    jwt --> mpjwt["microprofile.jwt.auth.api<br/>MicroProfile JWT API — JsonWebToken, @Claim, Claims"]
    jwt --> jakjson["jakarta.json<br/>Jakarta JSON API — required by MP JWT Claims enum"]
    jwt --> classgraph["io.github.classgraph<br/>annotation scanning"]
    jwt --> guice["com.google.guice<br/>Guice DI"]
```

## 🏗️ Key Classes

| Class | Package | Role |
|---|---|---|
| `VertxJsonWebToken` | `jwt` | `JsonWebToken` implementation backed by Vert.x `User` principal |
| `MicroProfileJwtContext` | `jwt` | CallScope-aware holder for the current request's `JsonWebToken` |
| `ClaimLiteral` | `jwt` | Runtime `@Claim` annotation implementation for Guice binding keys |
| `ClaimValueProvider` | `jwt` | Guice `Provider<T>` that resolves claims from the current JWT |
| `MicroProfileJwtModule` | `implementations` | `IGuiceModule` — scans `@Claim` fields and creates per-type bindings |
| `MicroProfileJwtPreStartup` | `implementations` | `IGuicePreStartup` — validates JWT auth provider via reflection |
| `ClaimInjectionPointProvider` | `implementations` | Registers `@Claim` as a Guice injection point |
| `ClaimNamedAnnotationProvider` | `implementations` | Converts `@Claim("x")` → `@Named("x")` |
| `ClaimBindingAnnotationProvider` | `implementations` | Registers `@Qualifier` as a binding annotation |

## 🧩 JPMS

Module name: **`com.guicedee.microprofile.jwt`**

The module:
- **exports** `com.guicedee.microprofile.jwt`, `com.guicedee.microprofile.jwt.implementations`
- **requires transitive** `com.guicedee.vertx`, `com.guicedee.guicedinjection`, `io.vertx.auth.jwt`, `microprofile.jwt.auth.api`, `jakarta.json`
- **provides** `IGuiceModule` with `MicroProfileJwtModule`
- **provides** `IGuicePreStartup` with `MicroProfileJwtPreStartup`
- **provides** `InjectionPointProvider` with `ClaimInjectionPointProvider`
- **provides** `NamedAnnotationProvider` with `ClaimNamedAnnotationProvider`
- **provides** `BindingAnnotationProvider` with `ClaimBindingAnnotationProvider`
- **opens** `com.guicedee.microprofile.jwt` and `com.guicedee.microprofile.jwt.implementations` to `com.google.guice`

## 🧪 Testing

```java
import com.guicedee.microprofile.jwt.MicroProfileJwtContext;
import com.guicedee.microprofile.jwt.VertxJsonWebToken;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtBridgeTest {

    @Test
    void claimsAreResolvedFromVertxUser() {
        User user = User.create(new JsonObject()
            .put("sub", "user-123")
            .put("iss", "https://auth.example.com")
            .put("groups", new io.vertx.core.json.JsonArray().add("admin")));

        VertxJsonWebToken jwt = new VertxJsonWebToken(user);

        assertEquals("user-123", jwt.getSubject());
        assertEquals("https://auth.example.com", jwt.getIssuer());
        assertTrue(jwt.getGroups().contains("admin"));
    }

    @Test
    void contextSetAndClear() {
        User user = User.create(new JsonObject().put("sub", "test"));
        MicroProfileJwtContext.setCurrent(new VertxJsonWebToken(user));

        assertNotNull(MicroProfileJwtContext.getCurrent());
        assertEquals("test", MicroProfileJwtContext.getCurrent().getSubject());

        MicroProfileJwtContext.clear();
        assertNull(MicroProfileJwtContext.getCurrent());
    }
}
```

## 🤝 Contributing

Issues and pull requests are welcome — please add tests for new claim types, bridge behaviors, or context strategies.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## ClaimLiteral

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/ClaimLiteral.java)

### ClaimLiteral

```java
public ClaimLiteral(String value);
@Override public Class<? extends Annotation> annotationType();
@Override public int hashCode();
@Override public boolean equals(Object o);
```

## ClaimValueProvider

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/ClaimValueProvider.java)

### ClaimValueProvider

```java
public ClaimValueProvider(Provider<JsonWebToken> jwtProvider, String claimName);
@Override @SuppressWarnings("unchecked") public T get();
```

## ClaimBindingAnnotationProvider

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/implementations/ClaimBindingAnnotationProvider.java)

### ClaimBindingAnnotationProvider

```java
@Override public List<Class<? extends Annotation>> getBindingAnnotations();
```

## ClaimInjectionPointProvider

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/implementations/ClaimInjectionPointProvider.java)

### ClaimInjectionPointProvider

```java
@Override public Class<? extends Annotation> injectionPoint(AnnotatedElement member);
```

## ClaimNamedAnnotationProvider

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/implementations/ClaimNamedAnnotationProvider.java)

### ClaimNamedAnnotationProvider

```java
@Override public Named getNamedAnnotation(Annotation annotation);
@Override public Named getNamedAnnotation(Class<? extends Annotation> annotationType);
```

## MicroProfileJwtModule

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/implementations/MicroProfileJwtModule.java)

### MicroProfileJwtModule

```java
@Override protected void configure();
@Override public Integer sortOrder();
```

## MicroProfileJwtPreStartup

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/implementations/MicroProfileJwtPreStartup.java)

### MicroProfileJwtPreStartup

```java
@Override public List<Future<Boolean>> onStartup();
public static void setCurrentUser(User user);
@Override public Integer sortOrder();
```

## MicroProfileJwtContext

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/MicroProfileJwtContext.java)

### MicroProfileJwtContext

```java
public static void setCurrent(JsonWebToken token);
public static JsonWebToken getCurrent();
public static void clear();
```

## VertxJsonWebToken

[Java source](https://github.com/GuicedEE/microprofile-jwt.git
branch = master/blob/master/src/main/java/com/guicedee/microprofile/jwt/VertxJsonWebToken.java)

### VertxJsonWebToken

```java
public VertxJsonWebToken(User vertxUser);
@Override public String getName();
@Override public Set<String> getClaimNames();
@Override @SuppressWarnings("unchecked") public <T> T getClaim(String claimName);
@Override public String getRawToken();
@Override public String getIssuer();
@Override public Set<String> getAudience();
@Override public String getSubject();
@Override public String getTokenID();
@Override public long getExpirationTime();
@Override public long getIssuedAtTime();
@Override public Set<String> getGroups();
```
