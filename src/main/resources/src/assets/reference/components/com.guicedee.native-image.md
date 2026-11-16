# com.guicedee:native-image

Optional build-time Guice interception and reflection registration for native images.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.nativeimage`.
- [Source POM](https://github.com/GuicedEE).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:guice`; scope `compile`.
- `org.graalvm.sdk:nativeimage`; scope `provided`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `org.mockito:mockito-core`; scope `test`.

## JPMS descriptor

```java
module com.guicedee.nativeimage {
    requires transitive com.google.guice;
    requires static org.graalvm.nativeimage;
    exports com.guicedee.nativeimage;
    opens com.guicedee.nativeimage to org.graalvm.nativeimage.builder;
    uses com.guicedee.nativeimage.GuiceNativeConfiguration;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE). Historical version examples retain their original context.

# Optional native-image support

`com.guicedee:native-image` supplies the explicitly enabled `com.guicedee.nativeimage.GuiceNativeFeature`.
GuicedEE core has no dependency on this module or GraalVM. JVM applications keep ordinary Guice
construction and interception.

This is a library, so its executable entry point belongs to the consuming application. It uses the
workspace Maven 4 wrapper and the parent/BOM-managed JUnit and Mockito versions. Its metadata classes
are not Guice-injected; their reflective opens target the native image builder. The creator's local
executable-scaffolding checks do not resolve these inherited settings or this library-only role.

The feature prepares Guice enhancer classes during image analysis. It retains ordinary runtime
injector creation, scopes, interceptor instances and service construction. Unenhanced constructors
and methods use Guice's existing reflection fallback. The AOT enhancer factory in the Guice owner
module avoids dynamic lambda linkage and does not initialize the application superclass while
preparing its invokers.

Applications or integration libraries contribute `GuiceNativeConfiguration` through both JPMS
`provides` and `META-INF/services`. A provider calls `reflect(...)` for reflective entry points and
`intercept(type, predicate)` for the union of methods selected by its runtime interceptor bindings.
Multiple selections may be registered when configurations require different method sets. Providers
must contain metadata only: do not start an injector, load credentials, inspect `.env`, or create
network clients during the build. Runtime services remain ordinary Guice objects.

`inspectMemberInjection(...)` can inspect classes used for externally constructed instance bindings.
It retains only proof that a class hierarchy contains no injectable fields or methods, avoiding
repeated runtime reflection on large library objects. No instances, injectors or injection-point
objects enter the image heap. Classes with injectable members and unregistered classes keep Guice's
normal validation, including inherited and generic members and partial error results.

An unregistered interceptor selection fails with a registration error. Interception is never
silently disabled to make compilation succeed. Runtime bytecode generation and arbitrary runtime
classpath discovery are outside this closed-world integration; the initial benchmark uses the
existing application discovery SPI.

When GuicedEE core is present, the native adapter replaces the runtime scanner entry point
with an SPI-mode check. This removes the unreachable scanning implementation from the image
and gives an explicit error if a native application's environment requests scanning. It does
not change JVM discovery, freeze environment settings, or add a core dependency to this library.

The separate [benchmark project](https://github.com/benchmarks/guicedee-native/pom.xml) retains the original
application JAR, its descriptor and its workload. Its SDK reachability metadata is workload-specific;
passing that workload does not imply support for every Firebase product or every GuicedEE integration.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## GuiceNativeConfiguration

[Java source](https://github.com/GuicedEE)

### GuiceNativeConfiguration

```java
void configure(GuiceNativeRegistration registration);
```

## GuiceNativeFeature

[Java source](https://github.com/GuicedEE)

### GuiceNativeFeature

```java
@Override public void beforeAnalysis(BeforeAnalysisAccess access);
```

## GuiceNativeRegistration

[Java source](https://github.com/GuicedEE)

### GuiceNativeRegistration

```java
public void inspectMemberInjection(Class<?>... types);
public void reflect(Class<?>... types);
public void intercept(Class<?> type, Predicate<Method> selected);
```
