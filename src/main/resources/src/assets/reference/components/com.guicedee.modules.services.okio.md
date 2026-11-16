# com.guicedee.modules.services:okio

Explicit JPMS wrapper for Okio JVM.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `okio`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/okio/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.squareup.okio:okio-jvm`; scope `compile`; optional.
- `org.jetbrains.kotlin:kotlin-stdlib`; scope `compile`.

## JPMS descriptor

```java
module okio {
    requires transitive kotlin.stdlib;
    exports okio;
    exports okio.internal;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Libraries/okio/README.md). Historical version examples retain their original context.

# Okio JPMS wrapper

Prepared for the upcoming **2.6.0** release. See the [release notes](https://github.com/GuicedEE/Services/blob/RELEASE-2.6.0.md) for validation and publication status.

`com.guicedee.modules.services:okio` retains module name `okio`, exports the
upstream packages and requires external `kotlin.stdlib`. Upstream `okio-jvm`
is automatic; this descriptor allows Moneta -> OkHttp -> Okio to link.
Versions are managed by Versioner and StandaloneBOM. No relocation is applied.
