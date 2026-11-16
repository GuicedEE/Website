# com.guicedee.modules.services:zstd-jni

Zstd JNI with a corrected JPMS descriptor; native names and binaries are preserved.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.github.luben.zstd_jni`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/zstd-jni/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.github.luben:zstd-jni`; scope `compile`; optional.

## JPMS descriptor

```java
module com.github.luben.zstd_jni {
    exports com.github.luben.zstd;
    exports com.github.luben.zstd.util;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Libraries/zstd-jni/README.md). Historical version examples retain their original context.

# Zstd JNI modular wrapper

Prepared for the upcoming **2.6.0** release. See the [release notes](https://github.com/GuicedEE/Services/blob/RELEASE-2.6.0.md) for validation and publication status.

Preserves module name `com.github.luben.zstd_jni`, package names and native library
resources. Versioner/StandaloneBOM manages upstream `com.github.luben:zstd-jni`.
The 1.5.7-20 descriptor incorrectly exports a META-INF versioned package and fails
jlink. This wrapper replaces only the descriptor and packaging metadata, retaining
multi-release behavior and byte-identical native resources. Consumers use
`com.guicedee.modules.services:zstd-jni` through the GuicedEE BOM.
