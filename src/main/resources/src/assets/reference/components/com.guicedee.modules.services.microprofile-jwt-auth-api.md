# com.guicedee.modules.services:microprofile-jwt-auth-api

A modularized MicroProfile JWT Auth API for JPMS. Wraps org.eclipse.microprofile.jwt:microprofile-jwt-auth-api with a proper module-info.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `microprofile.jwt.auth.api`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/MicroProfile/jwt-auth-api/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.eclipse.microprofile.jwt:microprofile-jwt-auth-api`; scope `compile`; optional.
- `jakarta.json:jakarta.json-api`; scope `compile`; optional.
- `jakarta.inject:jakarta.inject-api`; scope `compile`; optional.

## JPMS descriptor

```java
module microprofile.jwt.auth.api {
    requires transitive jakarta.json;
    requires transitive jakarta.inject;

    exports org.eclipse.microprofile.auth;
    exports org.eclipse.microprofile.jwt;
    exports org.eclipse.microprofile.jwt.config;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
