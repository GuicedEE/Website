---
name: guicedee-jwt
description: "MicroProfile JWT Auth bridge for GuicedEE with Vert.x 5: VertxJsonWebToken (Vert.x User → JsonWebToken), @Claim injection without @Inject, MicroProfileJwtContext (CallScope-aware request context), ClaimValueProvider, SPI registration (InjectionPointProvider, NamedAnnotationProvider, BindingAnnotationProvider), type-specific claim bindings (String, Set<String>, Long, Integer, Boolean, Optional<T>), Keycloak/OIDC integration via JWKS, and Guice-managed JsonWebToken. Use when bridging Vert.x JWT auth to MicroProfile JWT, injecting JWT claims, configuring @Claim fields, implementing JWT context propagation, or integrating with Keycloak/OIDC identity providers."
metadata:
  short-description: MicroProfile JWT Auth bridge for GuicedEE with Vert.x 5
---

# GuicedEE MicroProfile JWT

Bridges the MicroProfile JWT Auth specification to Vert.x 5 JWT authentication inside GuicedEE.

## Core Concept

The module converts a Vert.x `User` (authenticated via `JWTAuth`) into an `org.eclipse.microprofile.jwt.JsonWebToken`, making all standard and custom JWT claims available through the MP JWT API. Claims can be injected directly using `@Claim` — no `@Inject` required.

## Architecture

```
Vert.x JWTAuth → User → VertxJsonWebToken (implements JsonWebToken)
                           ↓
                    MicroProfileJwtContext (CallScope + ThreadLocal)
                           ↓
                    MicroProfileJwtModule (Guice bindings)
                           ↓
                    @Claim("sub") String subject  ← injected per-request
```

## Required Flow

1. Add `com.guicedee.microprofile:jwt` dependency.
2. Configure JWT authentication via `@JwtAuthOptions` on `package-info.java` (from the auth module).
3. Use `@Claim` to inject claims into your classes:
   ```java
   @Claim("sub")
   private String subject;

   @Claim("groups")
   private Set<String> roles;

   @Claim("exp")
   private Long expiration;
   ```
4. Access the full token when needed:
   ```java
   @Inject
   private JsonWebToken jwt;
   ```
5. Configure `module-info.java`:
   ```java
   module my.app {
       requires com.guicedee.microprofile.jwt;
       opens my.app to com.google.guice;
   }
   ```

## Key Classes

### `VertxJsonWebToken`
Bridge from Vert.x `User` to MP `JsonWebToken`. Resolves `getName()` via: `upn` → `preferred_username` → `sub`.

### `MicroProfileJwtContext`
Request-scoped holder: primary storage in `CallScopeProperties`, fallback to `ThreadLocal`.

### `MicroProfileJwtModule`
Auto-discovered Guice module. Binds `JsonWebToken` and scans `@Claim` fields via ClassGraph.

## Supported Claim Types

| Type | Absent behavior |
|---|---|
| `String` | `null` |
| `Set<String>` | `Set.of()` |
| `Long` / `long` | `0L` |
| `Integer` / `int` | `0` |
| `Boolean` / `boolean` | `false` |
| `Optional<String>` | `Optional.empty()` |
| `Object` | `null` |

## JPMS Module

```java
module com.guicedee.microprofile.jwt {
    requires transitive com.guicedee.vertx;
    requires transitive com.guicedee.guicedinjection;
    requires transitive io.vertx.auth.jwt;
    requires transitive microprofile.jwt.auth.api;
    requires transitive jakarta.json;
}
```

## Non-Negotiable Constraints

- Module must `requires com.guicedee.microprofile.jwt;`.
- Classes with `@Claim` fields must `opens` their package to `com.google.guice`.
- `@Claim` works **without** `@Inject` (via SPI registration).
- `MicroProfileJwtContext.clear()` **must** be called at end of request to prevent memory leaks.

