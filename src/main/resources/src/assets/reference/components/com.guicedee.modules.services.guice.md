# com.guicedee.modules.services:guice

Guice is a lightweight dependency injection framework for Java 11 and above

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.google.guice`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `jakarta.inject:jakarta.inject-api`; scope `compile`; optional.
- `com.guicedee.modules.services:aopalliance-core`; scope `compile`.
- `com.google.guava:guava`; scope `compile`.
- `jakarta.annotation:jakarta.annotation-api`; scope `compile`.
- `org.ow2.asm:asm`; scope `compile`.
- `com.google.inject:guice`; scope `compile`; optional.
- `com.google.errorprone:error_prone_annotations`; scope `provided`.
- `org.jetbrains:annotations`; scope `provided`.
- `com.google.code.findbugs:findbugs-annotations`; scope `provided`.
- `com.google.code.findbugs:jsr305`; scope `provided`.

## JPMS descriptor

```java
import com.google.inject.gee.*;

module com.google.guice {
    exports com.google.inject;
    exports com.google.inject.util;
    exports com.google.inject.matcher;
    exports com.google.inject.name;
    exports com.google.inject.binder;
    exports com.google.inject.spi;
    exports com.google.inject.multibindings;

    exports com.google.inject.internal;
    exports com.google.inject.internal.util;
    exports com.google.inject.gee;
    exports com.google.inject.internal.aop;

    requires java.logging;

    requires transitive com.google.common;

    requires static jakarta.inject;
    requires static jakarta.annotation;

    requires static jdk.unsupported;
    requires org.objectweb.asm;

    requires transitive aopalliance;

    requires static com.google.errorprone.annotations;
    requires static org.jetbrains.annotations;

    uses InjectionPointProvider;
    uses BindScopeProvider;
    uses ScopeAnnotationProvider;
    uses BindingAnnotationProvider;
    uses NamedAnnotationProvider;
    uses InjectorAnnotationsProvider;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/README.md). Historical version examples retain their original context.

# Google Guice � GuicedEE Modular Fork

[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)
![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Modular](https://img.shields.io/badge/Modular-Level3-green)

A **full source copy** of [Google Guice 7](https://github.com/google/guice) repackaged with a proper **JPMS `module-info.java`** descriptor, modular access fixes for **JDK 25**, and a set of **SPI extension points** (`com.google.inject.gee`) that allow downstream modules to plug in custom annotations for injection, binding, scoping, and naming � without forking Guice internals themselves.

> **Upstream policy:** this module tracks Google Guice releases and carries documented GuicedEE extensions for JPMS, annotation SPIs, and local dependency scoping. Local scoping follows the proposal in google/guice#1949 while it remains a draft upstream.

## Features

- **Full JPMS module** � ships a real `module-info.java` (`module com.google.guice`) with explicit `exports`, `requires`, and `uses` directives
- **JDK 25 compatibility** � access-level and reflection fixes so Guice runs cleanly on the module path without `--add-opens` hacks
- **Jakarta namespace** � uses `jakarta.inject` and `jakarta.annotation` (not `javax.*`)
- **SPI-driven extensibility** � six `ServiceLoader`-based SPIs let you register custom annotations for injection points, scopes, bindings, and naming without touching Guice source
- **Local dependency scopes** � apply a scope to an injected constructor parameter or field without changing the dependency binding for other consumers
- **Multibindings built-in** � `MapBinder`, `Multibinder`, and `OptionalBinder` are included in the same module (no separate `guice-multibindings` artifact needed)
- **Drop-in replacement** � all public Guice APIs remain unchanged; existing `@Inject`, `@Provides`, `@Singleton`, `bind()` EDSL code works as-is

## Installation

```xml
<dependency>
    <groupId>com.guicedee.modules.services</groupId>
    <artifactId>guice</artifactId>
</dependency>
```

Then `requires` it in your `module-info.java`:

```java
module my.app {
    requires com.google.guice;
}
```

## Local scopes on injected dependencies

Scope annotations can now be placed on an injectable constructor parameter or injected field. The
scope wraps the dependency's existing binding for that consumer only. The original binding keeps
its own scope for direct injection and other consumers. Parameters with the same dependency key
and scope annotation in one constructor share a scoped provider; fields with the same key and
scope annotation in one injected type do the same. Other constructors or fields get separate
providers. An existing scoped binding remains scoped underneath the local wrapper.

```java
final class Handler {
    private final RequestState constructorState;

    @Inject
    Handler(@RequestScoped RequestState state) {
        this.constructorState = state;
    }

    @Inject @RequestScoped RequestState fieldState;
}
```

This also works with `@Singleton` and registered custom scopes. Method-injection and
provider-method parameters retain their existing behavior. A local scope is resolved when the
injector builds the consumer, so an unregistered scope fails injector creation.

#### Register `@RequestScoped`

1. Define a runtime scope annotation with the targets you intend to use. `@ScopeAnnotation`
   is built in, so this annotation needs no `ScopeAnnotationProvider`.

```java
@ScopeAnnotation
@Retention(RUNTIME)
@Target({TYPE, METHOD, PARAMETER, FIELD})
public @interface RequestScoped {}
```

2. Provide a Guice `Scope` that caches values by `Key` in the active request context. Its
   `scope(key, unscoped)` provider should return the cached value within one request, create a
   new value in the next request, and throw `OutOfScopeException` when no request is active.
   The request context implementation belongs to the application; `guice-core` does not
   create or propagate request boundaries.

For a synchronous request, a minimal scope implementation is:

```java
public final class MyRequestScope implements Scope {
    public static final MyRequestScope INSTANCE = new MyRequestScope();
    private final ThreadLocal<Map<Key<?>, Object>> current = new ThreadLocal<>();

    public void enter() {
        if (current.get() != null) throw new IllegalStateException("Request already active");
        current.set(new HashMap<>());
    }

    public void exit() {
        current.remove();
    }

    @Override
    public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
        return () -> {
            Map<Key<?>, Object> values = current.get();
            if (values == null) throw new OutOfScopeException("No active request");
            if (values.containsKey(key)) {
                @SuppressWarnings("unchecked") T cached = (T) values.get(key);
                return cached;
            }
            T value = unscoped.get();
            if (!Scopes.isCircularProxy(value)) values.put(key, value);
            return value;
        };
    }
}
```

3. Register the concrete annotation and scope implementation through the existing SPI:

```java
public final class RequestScopeBinding implements BindScopeProvider {
    @Override
    public void bindScope(Binder binder) {
        binder.bindScope(RequestScoped.class, MyRequestScope.INSTANCE);
    }
}
```

```java
module my.scopes {
    requires com.google.guice;
    provides com.google.inject.gee.BindScopeProvider
        with my.scopes.RequestScopeBinding;
}
```

On the classpath, register `my.scopes.RequestScopeBinding` in
`META-INF/services/com.google.inject.gee.BindScopeProvider` instead. Open the scope at request
entry with `MyRequestScope.INSTANCE.enter()` and close it in a `finally` block with
`MyRequestScope.INSTANCE.exit()`. The `ThreadLocal` example is for synchronous requests; use
context-aware storage and propagation for asynchronous processing.

For an annotation marked with a different scope **meta-annotation** (such as
`jakarta.inject.Scope`), register the meta-annotation type through
`ScopeAnnotationProvider` as well. Return the marker type from that SPI, not
`RequestScoped.class`. A concrete annotation still needs a `BindScopeProvider` binding.

## SPI Extension Points

The `com.google.inject.gee` package exposes six SPIs loaded via `java.util.ServiceLoader`. Declare `uses` in the Guice module and `provides` in your own module to extend Guice's annotation handling at runtime.

| SPI | Purpose |
|---|---|
| `InjectionPointProvider` | Register custom annotations that mark injection points (beyond `@Inject`) |
| `InjectorAnnotationsProvider` | Declare additional annotations that Guice should treat as injector annotations |
| `BindScopeProvider` | Programmatically bind custom scope annotations during module configuration |
| `ScopeAnnotationProvider` | Supply scope meta-annotations used to recognise concrete scope annotations (for example, `jakarta.inject.Scope`) |
| `BindingAnnotationProvider` | Supply a list of annotation classes to be recognised as binding annotations (like `@Named`) |
| `NamedAnnotationProvider` | Map custom naming annotations to Guice's `@Named`, enabling alternative naming strategies |

### Example � Custom Injection Annotation

**1. Define your annotation:**

```java
@Retention(RUNTIME)
@Target({FIELD, METHOD, CONSTRUCTOR})
public @interface MyInject {}
```

**2. Implement the SPI:**

```java
public class MyInjectionPointProvider implements InjectionPointProvider {
    @Override
    public Class<? extends Annotation> injectionPoint(AnnotatedElement member) {
        return member.isAnnotationPresent(MyInject.class) ? MyInject.class : null;
    }
}
```

**3. Register in `module-info.java`:**

```java
module my.extensions {
    requires com.google.guice;
    provides com.google.inject.gee.InjectionPointProvider
        with my.extensions.MyInjectionPointProvider;
}
```

Now Guice will inject members annotated with `@MyInject` in addition to `@Inject`.

### Example � Custom Scope Annotation

A scope marked with `@ScopeAnnotation` can target injectable parameters and fields. Bind
its implementation through `BindScopeProvider`:

```java
@Retention(RUNTIME)
@Target({TYPE, METHOD, PARAMETER, FIELD})
@ScopeAnnotation
public @interface LocalSingleton {}

public class LocalSingletonBinding implements BindScopeProvider {
    @Override
    public void bindScope(Binder binder) {
        binder.bindScope(LocalSingleton.class, Scopes.SINGLETON);
    }
}
```

```java
module my.scopes {
    requires com.google.guice;
    provides com.google.inject.gee.BindScopeProvider
        with my.scopes.LocalSingletonBinding;
}
```

For an alternative scope meta-annotation, register the marker type with
`ScopeAnnotationProvider` rather than returning the concrete scope annotation:

```java
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface MyScopeMarker {}

public class MyScopes implements ScopeAnnotationProvider {
    @Override
    public List<Class<? extends Annotation>> getScopeAnnotations() {
        return List.of(MyScopeMarker.class);
    }
}
```

Register `MyScopes` as a `ScopeAnnotationProvider` service and bind each concrete scope
through `BindScopeProvider`.

### Example � Custom Binding Annotation

```java
public class MyBindings implements BindingAnnotationProvider {
    @Override
    public List<Class<? extends Annotation>> getBindingAnnotations() {
        return List.of(MyQualifier.class);
    }
}
```

## Module Graph

```
com.google.guice
  +-- com.google.common                    (Guava)
  +-- aopalliance                          (AOP Alliance)
  +-- jakarta.inject                       (static)
  +-- jakarta.annotation                   (static)
  +-- org.objectweb.asm                    (ASM bytecode)
  +-- java.logging
```

### Exported Packages

| Package | Description |
|---|---|
| `com.google.inject` | Core API � `Injector`, `Module`, `Binder`, `Key`, `TypeLiteral`, `Provider`, `Scope` |
| `com.google.inject.binder` | Binding EDSL � `LinkedBindingBuilder`, `AnnotatedBindingBuilder`, `ScopedBindingBuilder` |
| `com.google.inject.matcher` | Class and method matchers for AOP interceptors |
| `com.google.inject.multibindings` | `MapBinder`, `Multibinder`, `OptionalBinder` |
| `com.google.inject.name` | `@Named` and `Names` utility |
| `com.google.inject.spi` | Elements API � introspection, visitors, `InjectionPoint`, `Dependency` |
| `com.google.inject.util` | `Modules.override()`, `Providers`, `Types` |
| `com.google.inject.gee` | **GuicedEE SPIs** � extension points for custom annotations |
| `com.google.inject.internal` | Internal implementation (exported for framework use) |
| `com.google.inject.internal.aop` | Internal AOP support |
| `com.google.inject.internal.util` | Internal utilities |

## What Changed from Upstream

| Area | Change |
|---|---|
| **Module descriptor** | Added `module-info.java` with full `exports`, `requires`, `uses` |
| **Jakarta namespace** | `javax.inject` ? `jakarta.inject`, `javax.annotation` ? `jakarta.annotation` |
| **Access levels** | Widened select internal access modifiers so packages work under strict JPMS enforcement |
| **`com.google.inject.gee`** | New package with 6 SPI interfaces for pluggable annotation handling |
| **Multibindings** | Bundled into the core module instead of a separate artifact |
| **Guice internals** | `com.google.inject.internal` exported for framework-level consumers |
| **Local dependency scoping** | Constructor parameters and injected fields can apply scope annotations around an existing binding |

## Contributing

Issues and pull requests are welcome � especially for upstream Guice version bumps, additional SPI hooks, and JDK compatibility fixes.

## License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0) � same as upstream Google Guice.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## AbstractModule

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/AbstractModule.java)

### AbstractModule

```java
protected AbstractModule();
@Override public final synchronized void configure(Binder builder);
protected void configure();
protected Binder binder();
protected void bindScope(Class<? extends Annotation> scopeAnnotation, Scope scope);
protected <T> LinkedBindingBuilder<T> bind(Key<T> key);
protected <T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral);
protected <T> AnnotatedBindingBuilder<T> bind(Class<T> clazz);
protected AnnotatedConstantBindingBuilder bindConstant();
protected void install(Module module);
protected void addError(String message, Object... arguments);
protected void addError(Throwable t);
protected void addError(Message message);
protected void requestInjection(Object instance);
protected <T> void requestInjection(TypeLiteral<T> type, T instance);
protected void requestStaticInjection(Class<?>... types);
protected void bindInterceptor(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, MethodInterceptor... interceptors);
protected void requireBinding(Key<?> key);
protected void requireBinding(Class<?> type);
protected <T> Provider<T> getProvider(Key<T> key);
protected <T> Provider<T> getProvider(Class<T> type);
protected void convertToTypes(Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter);
protected Stage currentStage();
protected <T> MembersInjector<T> getMembersInjector(Class<T> type);
protected <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> type);
protected void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener);
protected void bindListener(Matcher<? super Binding<?>> bindingMatcher, ProvisionListener... listener);
```

## AnnotatedBindingBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/binder/AnnotatedBindingBuilder.java)

### AnnotatedBindingBuilder

```java
LinkedBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType);
LinkedBindingBuilder<T> annotatedWith(Annotation annotation);
```

## AnnotatedConstantBindingBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/binder/AnnotatedConstantBindingBuilder.java)

### AnnotatedConstantBindingBuilder

```java
ConstantBindingBuilder annotatedWith(Class<? extends Annotation> annotationType);
ConstantBindingBuilder annotatedWith(Annotation annotation);
```

## AnnotatedElementBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/binder/AnnotatedElementBuilder.java)

### AnnotatedElementBuilder

```java
void annotatedWith(Class<? extends Annotation> annotationType);
void annotatedWith(Annotation annotation);
```

## ConstantBindingBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/binder/ConstantBindingBuilder.java)

### ConstantBindingBuilder

```java
void to(String value);
void to(int value);
void to(long value);
void to(boolean value);
void to(double value);
void to(float value);
void to(short value);
void to(char value);
void to(byte value);
void to(Class<?> value);
<E extends Enum<E>> void to(E value);
```

## LinkedBindingBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/binder/LinkedBindingBuilder.java)

### LinkedBindingBuilder

```java
ScopedBindingBuilder to(Class<? extends T> implementation);
ScopedBindingBuilder to(TypeLiteral<? extends T> implementation);
ScopedBindingBuilder to(Key<? extends T> targetKey);
void toInstance(T instance);
ScopedBindingBuilder toProvider(Provider<? extends T> provider);
ScopedBindingBuilder toProvider(Class<? extends Provider<? extends T>> providerType);
ScopedBindingBuilder toProvider(TypeLiteral<? extends Provider<? extends T>> providerType);
ScopedBindingBuilder toProvider(Key<? extends Provider<? extends T>> providerKey);
<S extends T> ScopedBindingBuilder toConstructor(Constructor<S> constructor);
<S extends T> ScopedBindingBuilder toConstructor(Constructor<S> constructor, TypeLiteral<? extends S> type);
```

## ScopedBindingBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/binder/ScopedBindingBuilder.java)

### ScopedBindingBuilder

```java
void in(Class<? extends Annotation> scopeAnnotation);
void in(Scope scope);
void asEagerSingleton();
```

## Binder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Binder.java)

### Binder

```java
void bindInterceptor(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, MethodInterceptor... interceptors);
void bindScope(Class<? extends Annotation> annotationType, Scope scope);
<T> LinkedBindingBuilder<T> bind(Key<T> key);
<T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral);
<T> AnnotatedBindingBuilder<T> bind(Class<T> type);
AnnotatedConstantBindingBuilder bindConstant();
<T> void requestInjection(TypeLiteral<T> type, T instance);
void requestInjection(Object instance);
void requestStaticInjection(Class<?>... types);
void install(Module module);
Stage currentStage();
void addError(String message, Object... arguments);
void addError(Throwable t);
void addError(Message message);
<T> Provider<T> getProvider(Key<T> key);
<T> Provider<T> getProvider(Dependency<T> dependency);
<T> Provider<T> getProvider(Class<T> type);
<T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral);
<T> MembersInjector<T> getMembersInjector(Class<T> type);
void convertToTypes(Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter);
void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener);
void bindListener(Matcher<? super Binding<?>> bindingMatcher, ProvisionListener... listeners);
Binder withSource(Object source);
Binder skipSources(Class<?>... classesToSkip);
PrivateBinder newPrivateBinder();
void requireExplicitBindings();
void disableCircularProxies();
void requireAtInjectOnConstructors();
void requireExactBindingAnnotations();
void scanModulesForAnnotatedMethods(ModuleAnnotatedMethodScanner scanner);
```

## Binding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Binding.java)

### Binding

```java
Key<T> getKey();
Provider<T> getProvider();
<V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor);
<V> V acceptScopingVisitor(BindingScopingVisitor<V> visitor);
```

## BindingAnnotation

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/BindingAnnotation.java)

### BindingAnnotation

```java
```

## ConfigurationException

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/ConfigurationException.java)

### ConfigurationException

```java
public ConfigurationException(Iterable<Message> messages);
public ConfigurationException withPartialValue(Object partialValue);
public Collection<Message> getErrorMessages();
@SuppressWarnings({"unchecked", "TypeParameterUnusedInFormals"}) public <E> E getPartialValue();
@Override public String getMessage();
```

## CreationException

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/CreationException.java)

### CreationException

```java
public CreationException(Collection<Message> messages);
public Collection<Message> getErrorMessages();
@Override public String getMessage();
```

## Exposed

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Exposed.java)

### Exposed

```java
```

## BindingAnnotationProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/gee/BindingAnnotationProvider.java)

### BindingAnnotationProvider

```java
List<Class<? extends Annotation>> getBindingAnnotations();
static List<Class<? extends Annotation>> getAllBindingAnnotations();
```

## BindScopeProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/gee/BindScopeProvider.java)

### BindScopeProvider

```java
void bindScope(Binder binder);
```

## GuicedProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/gee/GuicedProvider.java)

### GuicedProvider

```java
```

## InjectionPoint

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/gee/InjectionPoint.java)

### InjectionPoint

```java
public Member getMember();
public List<Dependency<?>> getDependencies();
public boolean isOptional();
public boolean isToolable();
public TypeLiteral<?> getDeclaringType();
@Override public boolean equals(Object o);
@Override public int hashCode();
@Override public String toString();
public static <T> InjectionPoint forConstructor(Constructor<T> constructor);
public static <T> InjectionPoint forConstructor(Constructor<T> constructor, TypeLiteral<? extends T> type);
public static InjectionPoint forConstructorOf(TypeLiteral<?> type);
public static InjectionPoint forConstructorOf(TypeLiteral<?> type, boolean atInjectRequired);
public static InjectionPoint forConstructorOf(Class<?> type);
public static <T> InjectionPoint forMethod(Method method, TypeLiteral<T> type);
public static Set<InjectionPoint> forStaticMethodsAndFields(TypeLiteral<?> type);
public static Set<InjectionPoint> forStaticMethodsAndFields(Class<?> type);
public static Set<InjectionPoint> forInstanceMethodsAndFields(TypeLiteral<?> type);
public static Set<InjectionPoint> forInstanceMethodsAndFields(Class<?> type);
public static Annotation[] getAnnotations(Field field);
```

## InjectionPointProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/gee/InjectionPointProvider.java)

### InjectionPointProvider

```java
Class<? extends Annotation> injectionPoint(AnnotatedElement member);
```

## InjectorAnnotationsProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/gee/InjectorAnnotationsProvider.java)

### InjectorAnnotationsProvider

```java
boolean isInjectorAnnotation(Class<? extends java.lang.annotation.Annotation> annotationType);
static boolean checkAllInjectorAnnotations(Class<? extends java.lang.annotation.Annotation> annotationType);
```

## NamedAnnotationProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/gee/NamedAnnotationProvider.java)

### NamedAnnotationProvider

```java
Named getNamedAnnotation(Annotation annotationType);
Named getNamedAnnotation(Class<? extends Annotation> annotationType);
```

## ScopeAnnotationProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/gee/ScopeAnnotationProvider.java)

### ScopeAnnotationProvider

```java
List<Class<? extends Annotation>> getScopeAnnotations();
static List<Class<? extends Annotation>> getAllScopeAnnotations();
```

## Guice

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Guice.java)

### Guice

```java
public static Injector createInjector(Module... modules);
public static Injector createInjector(Iterable<? extends Module> modules);
public static Injector createInjector(Stage stage, Module... modules);
public static Injector createInjector(Stage stage, Iterable<? extends Module> modules);
```

## ImplementedBy

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/ImplementedBy.java)

### ImplementedBy

```java
Class<?> value();
```

## Inject

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Inject.java)

### Inject

```java
boolean optional() default false;
```

## Injector

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Injector.java)

### Injector

```java
void injectMembers(Object instance);
<T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral);
<T> MembersInjector<T> getMembersInjector(Class<T> type);
Map<Key<?>, Binding<?>> getBindings();
Map<Key<?>, Binding<?>> getAllBindings();
<T> Binding<T> getBinding(Key<T> key);
<T> Binding<T> getBinding(Class<T> type);
<T> Binding<T> getExistingBinding(Key<T> key);
<T> List<Binding<T>> findBindingsByType(TypeLiteral<T> type);
<T> Provider<T> getProvider(Key<T> key);
<T> Provider<T> getProvider(Class<T> type);
<T> T getInstance(Key<T> key);
<T> T getInstance(Class<T> type);
Injector getParent();
Injector createChildInjector(Iterable<? extends Module> modules);
Injector createChildInjector(Module... modules);
Map<Class<? extends Annotation>, Scope> getScopeBindings();
Set<TypeConverterBinding> getTypeConverterBindings();
List<Element> getElements();
Map<TypeLiteral<?>, List<InjectionPoint>> getAllMembersInjectorInjectionPoints();
```

## AbstractBindingBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/AbstractBindingBuilder.java)

### AbstractBindingBuilder

```java
public static final String IMPLEMENTATION_ALREADY_SET;
public static final String SINGLE_INSTANCE_AND_SCOPE;
public static final String SCOPE_ALREADY_SET;
public static final String BINDING_TO_NULL;
public static final String CONSTANT_VALUE_ALREADY_SET;
public static final String ANNOTATION_ALREADY_SPECIFIED;
public AbstractBindingBuilder(Binder binder, List<Element> elements, Object source, Key<T> key);
protected BindingImpl<T> getBinding();
protected BindingImpl<T> setBinding(BindingImpl<T> binding);
protected BindingImpl<T> annotatedWithInternal(Class<? extends Annotation> annotationType);
protected BindingImpl<T> annotatedWithInternal(Annotation annotation);
public void in(final Class<? extends Annotation> scopeAnnotation);
public void in(final Scope scope);
public void asEagerSingleton();
protected boolean keyTypeIsSet();
protected void checkNotTargetted();
protected void checkNotAnnotated();
protected void checkNotScoped();
```

## Annotations

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/Annotations.java)

### Annotations

```java
public static boolean isMarker(Class<? extends Annotation> annotationType);
public static boolean isAllDefaultMethods(Class<? extends Annotation> annotationType);
@SuppressWarnings("unchecked") public static <T extends Annotation> T generateAnnotation(Class<T> annotationType);
public static boolean isRetainedAtRuntime(Class<? extends Annotation> annotationType);
public static Class<? extends Annotation> findScopeAnnotation(Errors errors, Class<?> implementation);
public static Class<? extends Annotation> findScopeAnnotation(Errors errors, Annotation[] annotations);
public static String memberValueString(String value);
public static String memberValueString(String memberName, Object value);
public static String annotationInstanceClassString(Class<? extends Annotation> ann, boolean includePackage);
public static boolean isScopeAnnotation(Class<? extends Annotation> annotationType);
public static void checkForMisplacedScopeAnnotations(Class<?> type, Object source, Errors errors);
public static Key<?> getKey(TypeLiteral<?> type, Member member, Annotation[] annotations, Errors errors) throws ErrorsException;
public static Annotation findBindingAnnotation(Errors errors, Member member, Annotation[] annotations);
public static boolean isBindingAnnotation(Class<? extends Annotation> annotationType);
public static Annotation canonicalizeIfNamed(Annotation annotation);
public static Class<? extends Annotation> canonicalizeIfNamed(Class<? extends Annotation> annotationType);
public static String nameOf(Key<?> key);
```

## ClassBuilding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/aop/ClassBuilding.java)

### ClassBuilding

```java
public static String signature(Constructor<?> constructor);
public static String signature(Method method);
public static boolean canEnhance(Executable member);
public static BytecodeGen.EnhancerBuilder buildEnhancerBuilder(Class<?> hostClass);
public static BytecodeGen.EnhancerBuilder buildAheadOfTimeEnhancerBuilder(Class<?> hostClass);
public static boolean canFastInvoke(Executable member);
public static Function<String, BiFunction<Object, Object[], Object>> buildFastClass(Class<?> hostClass);
```

## ClassDefining

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/aop/ClassDefining.java)

### ClassDefining

```java
public static Class<?> define(Class<?> hostClass, byte[] bytecode) throws Exception;
public static boolean hasPackageAccess();
public static boolean canLoadProxyByName(Class<?> hostClass);
```

## BindingBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/BindingBuilder.java)

### BindingBuilder

```java
public BindingBuilder(Binder binder, List<Element> elements, Object source, Key<T> key);
@Override public BindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType);
@Override public BindingBuilder<T> annotatedWith(Annotation annotation);
@Override public BindingBuilder<T> to(Class<? extends T> implementation);
@Override public BindingBuilder<T> to(TypeLiteral<? extends T> implementation);
@Override public BindingBuilder<T> to(Key<? extends T> linkedKey);
@Override public void toInstance(T instance);
@Override public BindingBuilder<T> toProvider(Provider<? extends T> provider);
@Override public BindingBuilder<T> toProvider(Class<? extends Provider<? extends T>> providerType);
@Override public BindingBuilder<T> toProvider(TypeLiteral<? extends Provider<? extends T>> providerType);
@Override public BindingBuilder<T> toProvider(Key<? extends Provider<? extends T>> providerKey);
@Override public <S extends T> ScopedBindingBuilder toConstructor(Constructor<S> constructor);
@Override public <S extends T> ScopedBindingBuilder toConstructor(Constructor<S> constructor, TypeLiteral<? extends S> type);
@Override public String toString();
```

## BindingImpl

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/BindingImpl.java)

### BindingImpl

```java
@Override public Key<T> getKey();
@Override public Object getSource();
@Override public Provider<T> getProvider();
public InternalFactory<? extends T> getInternalFactory();
public Scoping getScoping();
public boolean isConstant();
@Override public <V> V acceptVisitor(ElementVisitor<V> visitor);
@Override public <V> V acceptScopingVisitor(BindingScopingVisitor<V> visitor);
protected BindingImpl<T> withScoping(Scoping scoping);
protected BindingImpl<T> withKey(Key<T> key);
@Override public String toString();
public InjectorImpl getInjector();
```

## BytecodeGen

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/BytecodeGen.java)

### BytecodeGen

```java
public static boolean isCircularProxy(Object object);
public static final String ENHANCER_BY_GUICE_MARKER;
public static final String FASTCLASS_BY_GUICE_MARKER;
```

### EnhancerBuilder

```java
Method[] getEnhanceableMethods();
Function<String, BiFunction<Object, Object[], Object>> buildEnhancer(BitSet methodIndices);
```

## ConstantBindingBuilderImpl

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/ConstantBindingBuilderImpl.java)

### ConstantBindingBuilderImpl

```java
@SuppressWarnings("unchecked") public ConstantBindingBuilderImpl(Binder binder, List<Element> elements, Object source);
@Override public ConstantBindingBuilder annotatedWith(Class<? extends Annotation> annotationType);
@Override public ConstantBindingBuilder annotatedWith(Annotation annotation);
@Override public void to(final String value);
@Override public void to(final int value);
@Override public void to(final long value);
@Override public void to(final boolean value);
@Override public void to(final double value);
@Override public void to(final float value);
@Override public void to(final short value);
@Override public void to(final char value);
@Override public void to(final byte value);
@Override public void to(final Class<?> value);
@Override public <E extends Enum<E>> void to(final E value);
@Override public String toString();
```

## DeclaredMembers

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/DeclaredMembers.java)

### DeclaredMembers

```java
public static Field[] getDeclaredFields(Class<?> type);
public static Method[] getDeclaredMethods(Class<?> type);
```

## ErrorId

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/ErrorId.java)

### ErrorId

```java
public static final ErrorId AMBIGUOUS_TYPE_CONVERSION;
public static final ErrorId AOP_DISABLED;
public static final ErrorId AT_INJECT_REQUIRED;
public static final ErrorId AT_TARGET_IS_MISSING_PARAMETER;
public static final ErrorId BINDING_ALREADY_SET;
public static final ErrorId BINDING_TO_GUICE_TYPE;
public static final ErrorId BINDING_TO_PROVIDER;
public static final ErrorId CAN_NOT_PROXY_CLASS;
public static final ErrorId CHILD_BINDING_ALREADY_SET;
public static final ErrorId CIRCULAR_PROXY_DISABLED;
public static final ErrorId CONSTRUCTOR_NOT_DEFINED_BY_TYPE;
public static final ErrorId CONVERSION_TYPE_ERROR;
public static final ErrorId CONVERTER_RETURNED_NULL;
public static final ErrorId DUPLICATE_BINDING_ANNOTATIONS;
public static final ErrorId DUPLICATE_ELEMENT;
public static final ErrorId DUPLICATE_MAP_KEY;
public static final ErrorId DUPLICATE_SCOPES;
public static final ErrorId DUPLICATE_SCOPE_ANNOTATIONS;
public static final ErrorId ERROR_ENHANCING_CLASS;
public static final ErrorId ERROR_INJECTING_CONSTRUCTOR;
public static final ErrorId ERROR_INJECTING_METHOD;
public static final ErrorId ERROR_IN_CUSTOM_PROVIDER;
public static final ErrorId ERROR_IN_USER_CODE;
public static final ErrorId ERROR_IN_USER_INJECTOR;
public static final ErrorId ERROR_NOTIFYING_TYPE_LISTENER;
public static final ErrorId EXPOSED_BUT_NOT_BOUND;
public static final ErrorId INJECT_ABSTRACT_METHOD;
public static final ErrorId INJECT_FINAL_FIELD;
public static final ErrorId INJECT_INNER_CLASS;
public static final ErrorId INJECT_LOCAL_CLASS;
public static final ErrorId INJECT_METHOD_WITH_TYPE_PARAMETER;
public static final ErrorId INJECT_RAW_MEMBERS_INJECTOR;
public static final ErrorId INJECT_RAW_PROVIDER;
public static final ErrorId INJECT_RAW_TYPE_LITERAL;
public static final ErrorId JIT_BINDING_ALREADY_SET;
public static final ErrorId JIT_DISABLED;
public static final ErrorId JIT_DISABLED_IN_PARENT;
public static final ErrorId KEY_NOT_FULLY_SPECIFIED;
public static final ErrorId MISPLACED_BINDING_ANNOTATION;
public static final ErrorId MISSING_CONSTANT_VALUES;
public static final ErrorId MISSING_CONSTRUCTOR;
public static final ErrorId MISSING_IMPLEMENTATION;
public static final ErrorId MISSING_RUNTIME_RETENTION;
public static final ErrorId MISSING_SCOPE_ANNOTATION;
public static final ErrorId NOT_A_SUBTYPE;
public static final ErrorId NULL_ELEMENT_IN_SET;
public static final ErrorId NULL_INJECTED_INTO_NON_NULLABLE;
public static final ErrorId NULL_VALUE_IN_MAP;
public static final ErrorId OPTIONAL_CONSTRUCTOR;
public static final ErrorId RECURSIVE_BINDING;
public static final ErrorId RECURSIVE_IMPLEMENTATION_TYPE;
public static final ErrorId RECURSIVE_PROVIDER_TYPE;
public static final ErrorId REQUEST_INJECTION_WITH_DIFFERENT_TYPES;
public static final ErrorId SCOPE_ANNOTATION_ON_ABSTRACT_TYPE;
public static final ErrorId SCOPE_NOT_FOUND;
public static final ErrorId STATIC_INJECTION_ON_INTERFACE;
public static final ErrorId SUBTYPE_NOT_PROVIDED;
public static final ErrorId TOO_MANY_CONSTRUCTORS;
public static final ErrorId VOID_PROVIDER_METHOD;
public static final ErrorId OTHER;
```

## Errors

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/Errors.java)

### Errors

```java
public Errors();
public Errors(Object source);
public Errors withSource(Object source);
public Errors aopDisabled(InterceptorBinding binding);
public Errors missingImplementation(Key<?> key);
public Errors jitDisabled(Key<?> key);
public Errors jitDisabledInParent(Key<?> key);
public Errors atInjectRequired(TypeLiteral<?> type);
public Errors converterReturnedNull(String stringValue, Object source, TypeLiteral<?> type, TypeConverterBinding typeConverterBinding);
public Errors conversionTypeError(String stringValue, Object source, TypeLiteral<?> type, TypeConverterBinding typeConverterBinding, Object converted);
public Errors conversionError(String stringValue, Object source, TypeLiteral<?> type, TypeConverterBinding typeConverterBinding, RuntimeException cause);
public Errors ambiguousTypeConversion(String stringValue, Object source, TypeLiteral<?> type, TypeConverterBinding a, TypeConverterBinding b);
public Errors bindingToProvider();
public Errors notASubtype(Class<?> implementationType, Class<?> type);
public Errors recursiveImplementationType();
public Errors recursiveProviderType();
public Errors missingRuntimeRetention(Class<? extends Annotation> annotation);
public Errors missingScopeAnnotation(Class<? extends Annotation> annotation);
public Errors optionalConstructor(Constructor<?> constructor);
public Errors cannotBindToGuiceType(String simpleName);
public Errors scopeNotFound(Class<? extends Annotation> scopeAnnotation);
public Errors scopeAnnotationOnAbstractType(Class<? extends Annotation> scopeAnnotation, Class<?> type, Object source);
public Errors misplacedBindingAnnotation(Member member, Annotation bindingAnnotation);
public Errors missingConstructor(TypeLiteral<?> type);
public Errors tooManyConstructors(Class<?> implementation);
public Errors constructorNotDefinedByType(Constructor<?> constructor, TypeLiteral<?> type);
public <K, V> Errors duplicateMapKey(Key<Map<K, V>> mapKey, Multimap<K, Binding<V>> duplicates);
public Errors duplicateScopes(ScopeBinding existing, Class<? extends Annotation> annotationType, Scope scope);
public Errors voidProviderMethod();
public Errors missingConstantValues();
public Errors cannotInjectInnerClass(Class<?> type);
public Errors cannotInjectLocalClass(Class<?> type);
public Errors duplicateBindingAnnotations(Member member, Class<? extends Annotation> a, Class<? extends Annotation> b);
public Errors staticInjectionOnInterface(Class<?> clazz);
public Errors cannotInjectFinalField(Field field);
public Errors atTargetIsMissingParameter(Annotation bindingAnnotation, String parameterName, Class<?> clazz);
public Errors cannotInjectAbstractMethod(Method method);
public Errors cannotInjectMethodWithTypeParameters(Method method);
public Errors duplicateScopeAnnotations(Class<? extends Annotation> a, Class<? extends Annotation> b);
public Errors recursiveBinding(Key<?> key, Key<?> linkedKey);
public Errors bindingAlreadySet(Key<?> key, Object source);
public Errors jitBindingAlreadySet(Key<?> key);
public Errors childBindingAlreadySet(Key<?> key, Set<Object> sources);
public Errors errorCheckingDuplicateBinding(Key<?> key, Object source, Throwable t);
public Errors requestInjectionWithDifferentTypes(Object instance, TypeLiteral<?> type1, Object type1Source, TypeLiteral<?> type2);
public Errors errorNotifyingTypeListener(TypeListenerBinding listener, TypeLiteral<?> type, Throwable cause);
public Errors exposedButNotBound(Key<?> key);
public Errors keyNotFullySpecified(TypeLiteral<?> typeLiteral);
public Errors errorEnhancingClass(Class<?> clazz, Throwable cause);
public static Collection<Message> getMessagesFromThrowable(Throwable throwable);
public Errors errorInUserCode(Throwable cause, String messageFormat, Object... arguments);
public Errors cannotInjectRawProvider();
public Errors cannotInjectRawMembersInjector();
public Errors cannotInjectTypeLiteralOf(Type unsupportedType);
public Errors cannotInjectRawTypeLiteral();
public void throwCreationExceptionIfErrorsExist();
public void throwConfigurationExceptionIfErrorsExist();
public void throwProvisionExceptionIfErrorsExist();
public Errors merge(Collection<Message> messages);
public Errors merge(Errors moreErrors);
public Errors merge(InternalProvisionException ipe);
public void throwIfNewErrors(int expectedSize) throws ErrorsException;
public ErrorsException toException();
public boolean hasErrors();
public Errors addMessage(String messageFormat, Object... arguments);
public Errors addMessage(ErrorId errorId, String messageFormat, Object... arguments);
public Errors addMessage(Message message);
public static String format(String messageFormat, Object... arguments);
public List<Message> getMessages();
public int size();
public static Object convert(Object o);
public static Object convert(Object o, ElementSource source);
public static void formatSource(Formatter formatter, Object source);
```

## ErrorsException

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/ErrorsException.java)

### ErrorsException

```java
public ErrorsException(Errors errors);
public Errors getErrors();
```

## ExposureBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/ExposureBuilder.java)

### ExposureBuilder

```java
public ExposureBuilder(Binder binder, Object source, Key<T> key);
protected void checkNotAnnotated();
@Override public void annotatedWith(Class<? extends Annotation> annotationType);
@Override public void annotatedWith(Annotation annotation);
public Key<?> getKey();
public Object getSource();
@Override public String toString();
```

## FailableCache

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/FailableCache.java)

### FailableCache

```java
protected abstract V create(K key, Errors errors) throws ErrorsException;
public V get(K key, Errors errors) throws ErrorsException;
```

## GenericErrorDetail

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/GenericErrorDetail.java)

### GenericErrorDetail

```java
public GenericErrorDetail(ErrorId errorId, String message, List<Object> sources, Throwable cause);
@Override public void formatDetail(List<ErrorDetail<?>> mergeableErrors, Formatter formatter);
@Override public GenericErrorDetail withSources(List<Object> newSources);
```

## GuiceInternal

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/GuiceInternal.java)

### GuiceInternal

```java
```

## InternalClassesToSkipSources

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/InternalClassesToSkipSources.java)

### InternalClassesToSkipSources

```java
public static ImmutableSet<Class<?>> classesToSkipSources();
```

## InternalFlags

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/InternalFlags.java)

### InternalFlags

```java
public static IncludeStackTraceOption getIncludeStackTraceOption();
public static CustomClassLoadingOption getCustomClassLoadingOption();
public static NullableProvidesOption getNullableProvidesOption();
public static boolean isBytecodeGenEnabled();
public static boolean enableColorizeErrorMessages();
```

### IncludeStackTraceOption

```java
public static final IncludeStackTraceOption OFF;
public static final IncludeStackTraceOption ONLY_FOR_DECLARING_SOURCE;
```

### CustomClassLoadingOption

```java
public static final CustomClassLoadingOption OFF;
public static final CustomClassLoadingOption ANONYMOUS;
public static final CustomClassLoadingOption BRIDGE;
public static final CustomClassLoadingOption CHILD;
```

### NullableProvidesOption

```java
public static final NullableProvidesOption IGNORE;
public static final NullableProvidesOption WARN;
public static final NullableProvidesOption ERROR;
```

### BytecodeGenOption

```java
public static final BytecodeGenOption DISABLED;
public static final BytecodeGenOption ENABLED;
```

### ColorizeOption

```java
public static final ColorizeOption AUTO;
public static final ColorizeOption ON;
public static final ColorizeOption OFF;
```

## InternalInjectorCreator

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/InternalInjectorCreator.java)

### InternalInjectorCreator

```java
public InternalInjectorCreator();
public InternalInjectorCreator stage(Stage stage);
public InternalInjectorCreator parentInjector(InjectorImpl parent);
public InternalInjectorCreator addModules(Iterable<? extends Module> modules);
public Injector build();
```

## InternalProvisionException

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/InternalProvisionException.java)

### InternalProvisionException

```java
public static InternalProvisionException circularDependenciesDisabled(Class<?> expectedType);
public static InternalProvisionException cannotProxyClass(Class<?> expectedType);
public static InternalProvisionException create(ErrorId errorId, String format, Object... arguments);
public static InternalProvisionException errorInUserCode(ErrorId errorId, Throwable cause, String messageFormat, Object... arguments);
public static InternalProvisionException subtypeNotProvided(Class<? extends Provider<?>> providerType, Class<?> type);
public static InternalProvisionException errorInProvider(Throwable cause);
public static InternalProvisionException errorInjectingMethod(Throwable cause);
public static InternalProvisionException errorInjectingConstructor(Throwable cause);
public static InternalProvisionException errorInUserInjector(MembersInjector<?> listener, TypeLiteral<?> type, RuntimeException cause);
public static InternalProvisionException jitDisabled(Key<?> key);
public static InternalProvisionException errorNotifyingInjectionListener(InjectionListener<?> listener, TypeLiteral<?> type, RuntimeException cause);
public ProvisionException toProvisionException();
```

## KotlinSupport

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/KotlinSupport.java)

### KotlinSupport

```java
public static KotlinSupportInterface getInstance();
```

## KotlinSupportInterface

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/KotlinSupportInterface.java)

### KotlinSupportInterface

```java
Annotation[] getAnnotations(Field field);
boolean isNullable(Field field);
Predicate<Integer> getIsParameterKotlinNullablePredicate(Constructor<?> constructor);
Predicate<Integer> getIsParameterKotlinNullablePredicate(Method method);
void checkConstructorParameterAnnotations(Constructor<?> constructor, Errors errors);
boolean isLocalClass(Class<?> clazz);
```

## Messages

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/Messages.java)

### Messages

```java
public static String format(String messageFormat, Object... arguments);
public static String formatMessages(String heading, Collection<Message> errorMessages);
public static Message create(ErrorId errorId, String messageFormat, Object... arguments);
public static Message create(ErrorId errorId, Throwable cause, String messageFormat, Object... arguments);
public static Message create(ErrorId errorId, Throwable cause, List<Object> sources, String messageFormat, Object... arguments);
public static Throwable getOnlyCause(Collection<Message> messages);
public static final String bold(String text);
public static final String redBold(String text);
public static final String underline(String text);
public static final String faint(String text);
```

## MoreTypes

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/MoreTypes.java)

### MoreTypes

```java
public static final Type[] EMPTY_TYPE_ARRAY;
public static <T> Key<T> canonicalizeKey(Key<T> key);
public static <T> TypeLiteral<T> canonicalizeForKey(TypeLiteral<T> typeLiteral);
public static Type canonicalize(Type type);
public static Class<?> getRawType(Type type);
public static boolean equals(Type a, Type b);
public static String typeToString(Type type);
public static Type getGenericSupertype(Type type, Class<?> rawType, Class<?> toResolve);
public static Type resolveTypeVariable(Type type, Class<?> rawType, TypeVariable<?> unknown);
```

### ParameterizedTypeImpl

```java
public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments);
@Override public Type[] getActualTypeArguments();
@Override public Type getRawType();
@Override public Type getOwnerType();
@Override public boolean isFullySpecified();
@Override public boolean equals(Object other);
@Override public int hashCode();
@Override public String toString();
```

### GenericArrayTypeImpl

```java
public GenericArrayTypeImpl(Type componentType);
@Override public Type getGenericComponentType();
@Override public boolean isFullySpecified();
@Override public boolean equals(Object o);
@Override public int hashCode();
@Override public String toString();
```

### WildcardTypeImpl

```java
public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds);
@Override public Type[] getUpperBounds();
@Override public Type[] getLowerBounds();
@Override public boolean isFullySpecified();
@Override public boolean equals(Object other);
@Override public int hashCode();
@Override public String toString();
```

## Nullability

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/Nullability.java)

### Nullability

```java
public static boolean hasNullableAnnotation(Annotation[] annotations);
```

## PrivateElementsImpl

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/PrivateElementsImpl.java)

### PrivateElementsImpl

```java
public PrivateElementsImpl(Object source);
@Override public Object getSource();
@Override public List<Element> getElements();
@Override public Injector getInjector();
public void initInjector(Injector injector);
@Override public Set<Key<?>> getExposedKeys();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
public List<Element> getElementsMutable();
public void addExposureBuilder(ExposureBuilder<?> exposureBuilder);
@Override public void applyTo(Binder binder);
@Override public Object getExposedSource(Key<?> key);
@Override public String toString();
```

## ProviderMethod

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/ProviderMethod.java)

### ProviderMethod

```java
@Override public Key<T> getKey();
@Override public Method getMethod();
public Object getInstance();
@Override public Object getEnclosingInstance();
@Override public Annotation getAnnotation();
public void configure(Binder binder);
@Override protected T doProvision(InternalContext context, Dependency<?> dependency) throws InternalProvisionException;
@Override public Set<Dependency<?>> getDependencies();
@Override @SuppressWarnings("unchecked") public <B, V> V acceptExtensionVisitor(BindingTargetVisitor<B, V> visitor, ProviderInstanceBinding<? extends B> binding);
@Override public String toString();
@Override public boolean equals(Object obj);
@Override public int hashCode();
```

## ProviderMethodsModule

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/ProviderMethodsModule.java)

### ProviderMethodsModule

```java
public static Module forModule(Module module);
public static Module forModule(Object module, ModuleAnnotatedMethodScanner scanner);
public static Module forObject(Object object);
public Class<?> getDelegateModuleClass();
@Override public void configure(Binder binder);
public List<ProviderMethod<?>> getProviderMethods(Binder binder);
@Override public boolean equals(Object o);
@Override public int hashCode();
public boolean isScanningBuiltInProvidesMethods();
public ModuleAnnotatedMethodScanner getScanner();
```

## RealMapBinder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/RealMapBinder.java)

### RealMapBinder

```java
public static <K, V> RealMapBinder<K, V> newMapRealBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType);
public static <K, V> RealMapBinder<K, V> newRealMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType, Annotation annotation);
public static <K, V> RealMapBinder<K, V> newRealMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType, Class<? extends Annotation> annotationType);
public void permitDuplicates();
public LinkedBindingBuilder<V> addBinding(K key);
@SuppressWarnings({"unchecked", "rawtypes"}) @Override public void configure(Binder binder);
@Override public boolean equals(Object o);
@Override public int hashCode();
```

## RealMultibinder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/RealMultibinder.java)

### RealMultibinder

```java
public static <T> RealMultibinder<T> newRealSetBinder(Binder binder, Key<T> key);
@SuppressWarnings({"unchecked", "rawtypes"}) @Override public void configure(Binder binder);
public void permitDuplicates();
public LinkedBindingBuilder<T> addBinding();
@Override public boolean equals(Object o);
@Override public int hashCode();
```

## RealOptionalBinder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/RealOptionalBinder.java)

### RealOptionalBinder

```java
public static <T> RealOptionalBinder<T> newRealOptionalBinder(Binder binder, Key<T> type);
public LinkedBindingBuilder<T> setDefault();
public LinkedBindingBuilder<T> setBinding();
@SuppressWarnings({"unchecked", "rawtypes"}) @Override public void configure(Binder binder);
@Override public boolean equals(Object o);
@Override public int hashCode();
```

## Scoping

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/Scoping.java)

### Scoping

```java
public static final Scoping UNSCOPED;
public static final Scoping SINGLETON_ANNOTATION;
public static final Scoping SINGLETON_INSTANCE;
public static final Scoping EAGER_SINGLETON;
public static Scoping forAnnotation(final Class<? extends Annotation> scopingAnnotation);
public static Scoping forInstance(final Scope scope);
public boolean isExplicitlyScoped();
public boolean isNoScope();
public boolean isEagerSingleton(Stage stage);
public Scope getScopeInstance();
public Class<? extends Annotation> getScopeAnnotation();
@Override public boolean equals(Object obj);
@Override public int hashCode();
public abstract <V> V acceptVisitor(BindingScopingVisitor<V> visitor);
public abstract void applyTo(ScopedBindingBuilder scopedBindingBuilder);
```

## SingletonScope

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/SingletonScope.java)

### SingletonScope

```java
@Override public <T> Provider<T> scope(final Key<T> key, final Provider<T> creator);
@Override public String toString();
```

## UniqueAnnotations

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/UniqueAnnotations.java)

### UniqueAnnotations

```java
public static Annotation create();
```

## Classes

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/util/Classes.java)

### Classes

```java
public static boolean isInnerClass(Class<?> clazz);
public static boolean isConcrete(Class<?> clazz);
public static String toString(Member member);
public static Class<? extends Member> memberType(Member member);
```

## ContinuousStopwatch

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/util/ContinuousStopwatch.java)

### ContinuousStopwatch

```java
public ContinuousStopwatch(Stopwatch stopwatch);
public long reset();
public void resetAndLog(String label);
```

## SourceProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/util/SourceProvider.java)

### SourceProvider

```java
public static final Object UNKNOWN_SOURCE;
public static final SourceProvider DEFAULT_INSTANCE;
public SourceProvider plusSkippedClasses(Class<?>... moreClassesToSkip);
public StackTraceElement getCaller();
public Object getFromClassNames(List<String> moduleClassNames);
```

## StackTraceElements

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/internal/util/StackTraceElements.java)

### StackTraceElements

```java
public static Object forMember(Member member);
public static Object forType(Class<?> implementation);
public static void clearCache();
public static InMemoryStackTraceElement[] convertToInMemoryStackTraceElement(StackTraceElement[] stackTraceElements);
public static StackTraceElement[] convertToStackTraceElement(InMemoryStackTraceElement[] inMemoryStackTraceElements);
```

### InMemoryStackTraceElement

```java
@Override public boolean equals(Object obj);
@Override public int hashCode();
@Override public String toString();
```

## Key

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Key.java)

### Key

```java
@SuppressWarnings("unchecked") protected Key(Class<? extends Annotation> annotationType);
@SuppressWarnings("unchecked") protected Key(Annotation annotation);
@SuppressWarnings("unchecked") protected Key();
public final TypeLiteral<T> getTypeLiteral();
public final Class<? extends Annotation> getAnnotationType();
public final Annotation getAnnotation();
@Override public final boolean equals(Object o);
@Override public final int hashCode();
@Override public final String toString();
public static <T> Key<T> get(Class<T> type);
public static <T> Key<T> get(Class<T> type, Class<? extends Annotation> annotationType);
public static <T> Key<T> get(Class<T> type, Annotation annotation);
public static Key<?> get(Type type);
public static Key<?> get(Type type, Class<? extends Annotation> annotationType);
public static Key<?> get(Type type, Annotation annotation);
public static <T> Key<T> get(TypeLiteral<T> typeLiteral);
public static <T> Key<T> get(TypeLiteral<T> typeLiteral, Class<? extends Annotation> annotationType);
public static <T> Key<T> get(TypeLiteral<T> typeLiteral, Annotation annotation);
public <U> Key<U> ofType(Class<U> type);
public Key<?> ofType(Type type);
public <U> Key<U> ofType(TypeLiteral<U> type);
public Key<T> withAnnotation(Class<? extends Annotation> annotationType);
public Key<T> withAnnotation(Annotation annotation);
public boolean hasAttributes();
public Key<T> withoutAttributes();
```

## AbstractMatcher

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/matcher/AbstractMatcher.java)

### AbstractMatcher

```java
protected AbstractMatcher();
@Override public Matcher<T> and(final Matcher<? super T> other);
@Override public Matcher<T> or(Matcher<? super T> other);
```

## Matcher

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/matcher/Matcher.java)

### Matcher

```java
boolean matches(T t);
default Matcher<T> and(Matcher<? super T> other);
default Matcher<T> or(Matcher<? super T> other);
```

## Matchers

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/matcher/Matchers.java)

### Matchers

```java
public static Matcher<Object> any();
public static <T> Matcher<T> not(final Matcher<? super T> p);
public static Matcher<AnnotatedElement> annotatedWith(final Class<? extends Annotation> annotationType);
public static Matcher<AnnotatedElement> annotatedWith(final Annotation annotation);
public static Matcher<Class> subclassesOf(final Class<?> superclass);
public static Matcher<Object> only(Object value);
public static Matcher<Object> identicalTo(final Object value);
public static Matcher<Class> inPackage(final Package targetPackage);
public static Matcher<Class> inSubpackage(final String targetPackageName);
public static Matcher<Method> returns(final Matcher<? super Class<?>> returnType);
```

## MembersInjector

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/MembersInjector.java)

### MembersInjector

```java
void injectMembers(T instance);
```

## Module

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Module.java)

### Module

```java
void configure(Binder binder);
```

## ClassMapKey

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/ClassMapKey.java)

### ClassMapKey

```java
Class<?> value();
```

## MapBinder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/MapBinder.java)

### MapBinder

```java
public static <K, V> MapBinder<K, V> newMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType);
public static <K, V> MapBinder<K, V> newMapBinder(Binder binder, Class<K> keyType, Class<V> valueType);
public static <K, V> MapBinder<K, V> newMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType, Annotation annotation);
public static <K, V> MapBinder<K, V> newMapBinder(Binder binder, Class<K> keyType, Class<V> valueType, Annotation annotation);
public static <K, V> MapBinder<K, V> newMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType, Class<? extends Annotation> annotationType);
public static <K, V> MapBinder<K, V> newMapBinder(Binder binder, Class<K> keyType, Class<V> valueType, Class<? extends Annotation> annotationType);
public MapBinder<K, V> permitDuplicates();
public LinkedBindingBuilder<V> addBinding(K key);
@Override public boolean equals(Object obj);
@Override public int hashCode();
```

## MapBinderBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/MapBinderBinding.java)

### MapBinderBinding

```java
Key<T> getMapKey();
Set<Key<?>> getAlternateMapKeys();
TypeLiteral<?> getKeyTypeLiteral();
TypeLiteral<?> getValueTypeLiteral();
List<Map.Entry<?, Binding<?>>> getEntries();
List<Map.Entry<?, Binding<?>>> getEntries(Iterable<? extends Element> elements);
boolean permitsDuplicates();
boolean containsElement(Element element);
```

## MapKey

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/MapKey.java)

### MapKey

```java
boolean unwrapValue() default true;
```

## Multibinder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/Multibinder.java)

### Multibinder

```java
public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type);
public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type);
public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type, Annotation annotation);
public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type, Annotation annotation);
public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type, Class<? extends Annotation> annotationType);
public static <T> Multibinder<T> newSetBinder(Binder binder, Key<T> key);
public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type, Class<? extends Annotation> annotationType);
public Multibinder<T> permitDuplicates();
public LinkedBindingBuilder<T> addBinding();
@Override public boolean equals(Object obj);
@Override public int hashCode();
```

## MultibinderBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/MultibinderBinding.java)

### MultibinderBinding

```java
Key<T> getSetKey();
Set<Key<?>> getAlternateSetKeys();
TypeLiteral<?> getElementTypeLiteral();
List<Binding<?>> getElements();
boolean permitsDuplicates();
boolean containsElement(Element element);
```

## MultibindingsScanner

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/MultibindingsScanner.java)

### MultibindingsScanner

```java
@Deprecated public static Module asModule();
@Deprecated public static ModuleAnnotatedMethodScanner scanner();
```

## MultibindingsTargetVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/MultibindingsTargetVisitor.java)

### MultibindingsTargetVisitor

```java
V visit(MultibinderBinding<? extends T> multibinding);
V visit(MapBinderBinding<? extends T> mapbinding);
V visit(OptionalBinderBinding<? extends T> optionalbinding);
```

## OptionalBinder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/OptionalBinder.java)

### OptionalBinder

```java
public static <T> OptionalBinder<T> newOptionalBinder(Binder binder, Class<T> type);
public static <T> OptionalBinder<T> newOptionalBinder(Binder binder, TypeLiteral<T> type);
public static <T> OptionalBinder<T> newOptionalBinder(Binder binder, Key<T> type);
public LinkedBindingBuilder<T> setDefault();
public LinkedBindingBuilder<T> setBinding();
@Override public boolean equals(Object obj);
@Override public int hashCode();
```

## OptionalBinderBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/OptionalBinderBinding.java)

### OptionalBinderBinding

```java
Key<T> getKey();
Set<Key<?>> getAlternateKeys();
Binding<?> getDefaultBinding();
Binding<?> getActualBinding();
boolean containsElement(Element element);
```

## ProvidesIntoMap

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/ProvidesIntoMap.java)

### ProvidesIntoMap

```java
```

## ProvidesIntoOptional

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/ProvidesIntoOptional.java)

### ProvidesIntoOptional

```java
Type value();
```

### Type

```java
public static final Type ACTUAL;
public static final Type DEFAULT;
```

## ProvidesIntoSet

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/ProvidesIntoSet.java)

### ProvidesIntoSet

```java
```

## StringMapKey

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/multibindings/StringMapKey.java)

### StringMapKey

```java
String value();
```

## Named

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/name/Named.java)

### Named

```java
String value();
```

## Names

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/name/Names.java)

### Names

```java
public static Named named(String name);
public static void bindProperties(Binder binder, Map<String, String> properties);
public static void bindProperties(Binder binder, Properties properties);
```

## OutOfScopeException

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/OutOfScopeException.java)

### OutOfScopeException

```java
public OutOfScopeException(String message);
public OutOfScopeException(String message, Throwable cause);
public OutOfScopeException(Throwable cause);
```

## PrivateBinder

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/PrivateBinder.java)

### PrivateBinder

```java
void expose(Key<?> key);
AnnotatedElementBuilder expose(Class<?> type);
AnnotatedElementBuilder expose(TypeLiteral<?> type);
@Override PrivateBinder withSource(Object source);
@Override PrivateBinder skipSources(Class<?>... classesToSkip);
```

## PrivateModule

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/PrivateModule.java)

### PrivateModule

```java
@Override public final synchronized void configure(Binder binder);
protected abstract void configure();
protected final <T> void expose(Key<T> key);
protected final AnnotatedElementBuilder expose(Class<?> type);
protected final AnnotatedElementBuilder expose(TypeLiteral<?> type);
protected PrivateBinder binder();
protected final void bindScope(Class<? extends Annotation> scopeAnnotation, Scope scope);
protected final <T> LinkedBindingBuilder<T> bind(Key<T> key);
protected final <T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral);
protected final <T> AnnotatedBindingBuilder<T> bind(Class<T> clazz);
protected final AnnotatedConstantBindingBuilder bindConstant();
protected final void install(Module module);
protected final void addError(String message, Object... arguments);
protected final void addError(Throwable t);
protected final void addError(Message message);
protected final void requestInjection(Object instance);
protected final void requestStaticInjection(Class<?>... types);
protected final void bindInterceptor(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, MethodInterceptor... interceptors);
protected final void requireBinding(Key<?> key);
protected final void requireBinding(Class<?> type);
protected final <T> Provider<T> getProvider(Key<T> key);
protected final <T> Provider<T> getProvider(Class<T> type);
protected final void convertToTypes(Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter);
protected final Stage currentStage();
protected <T> MembersInjector<T> getMembersInjector(Class<T> type);
protected <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> type);
protected void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener);
protected void bindListener(Matcher<? super Binding<?>> bindingMatcher, ProvisionListener... listeners);
```

## ProvidedBy

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/ProvidedBy.java)

### ProvidedBy

```java
Class<? extends Provider<?>> value();
```

## Provider

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Provider.java)

### Provider

```java
T get();
```

## Provides

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Provides.java)

### Provides

```java
```

## ProvisionException

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/ProvisionException.java)

### ProvisionException

```java
public ProvisionException(Iterable<Message> messages);
public ProvisionException(String message, Throwable cause);
public ProvisionException(String message);
public Collection<Message> getErrorMessages();
@Override public String getMessage();
```

## RestrictedBindingSource

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/RestrictedBindingSource.java)

### RestrictedBindingSource

```java
String explanation();
Class<? extends Annotation>[] permits();
String exemptModules() default "";
RestrictionLevel restrictionLevel() default RestrictionLevel.ERROR;
```

### Permit

```java
```

### RestrictionLevel

```java
public static final RestrictionLevel WARNING;
public static final RestrictionLevel ERROR;
```

## Scope

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Scope.java)

### Scope

```java
public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped);
@Override String toString();
```

## ScopeAnnotation

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/ScopeAnnotation.java)

### ScopeAnnotation

```java
```

## Scopes

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Scopes.java)

### Scopes

```java
public static final Scope SINGLETON;
public static final Scope NO_SCOPE;
public static boolean isSingleton(Binding<?> binding);
public static boolean isScoped(Binding<?> binding, final Scope scope, final Class<? extends Annotation> scopeAnnotation);
public static boolean isCircularProxy(Object object);
```

## Singleton

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Singleton.java)

### Singleton

```java
```

## BindingScopingVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/BindingScopingVisitor.java)

### BindingScopingVisitor

```java
V visitEagerSingleton();
V visitScope(Scope scope);
V visitScopeAnnotation(Class<? extends Annotation> scopeAnnotation);
V visitNoScoping();
```

## BindingSourceRestriction

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/BindingSourceRestriction.java)

### BindingSourceRestriction

```java
public static Optional<String> getMissingImplementationSuggestion(GuiceInternal guiceInternal, Key<?> key);
public static ImmutableList<Message> check(GuiceInternal guiceInternal, List<Element> elements);
```

## BindingTargetVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/BindingTargetVisitor.java)

### BindingTargetVisitor

```java
V visit(InstanceBinding<? extends T> binding);
V visit(ProviderInstanceBinding<? extends T> binding);
V visit(ProviderKeyBinding<? extends T> binding);
V visit(LinkedKeyBinding<? extends T> binding);
V visit(ExposedBinding<? extends T> binding);
V visit(UntargettedBinding<? extends T> binding);
V visit(ConstructorBinding<? extends T> binding);
V visit(ConvertedConstantBinding<? extends T> binding);
V visit(ProviderBinding<? extends T> binding);
```

## ConstructorBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ConstructorBinding.java)

### ConstructorBinding

```java
com.google.inject.gee.InjectionPoint getConstructor();
Set<InjectionPoint> getInjectableMembers();
Map<Method, List<MethodInterceptor>> getMethodInterceptors();
```

## ConvertedConstantBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ConvertedConstantBinding.java)

### ConvertedConstantBinding

```java
T getValue();
TypeConverterBinding getTypeConverterBinding();
Key<String> getSourceKey();
@Override Set<Dependency<?>> getDependencies();
```

## DefaultBindingScopingVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/DefaultBindingScopingVisitor.java)

### DefaultBindingScopingVisitor

```java
protected V visitOther();
@Override public V visitEagerSingleton();
@Override public V visitScope(Scope scope);
@Override public V visitScopeAnnotation(Class<? extends Annotation> scopeAnnotation);
@Override public V visitNoScoping();
```

## DefaultBindingTargetVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/DefaultBindingTargetVisitor.java)

### DefaultBindingTargetVisitor

```java
protected V visitOther(Binding<? extends T> binding);
@Override public V visit(InstanceBinding<? extends T> instanceBinding);
@Override public V visit(ProviderInstanceBinding<? extends T> providerInstanceBinding);
@Override public V visit(ProviderKeyBinding<? extends T> providerKeyBinding);
@Override public V visit(LinkedKeyBinding<? extends T> linkedKeyBinding);
@Override public V visit(ExposedBinding<? extends T> exposedBinding);
@Override public V visit(UntargettedBinding<? extends T> untargettedBinding);
@Override public V visit(ConstructorBinding<? extends T> constructorBinding);
@Override public V visit(ConvertedConstantBinding<? extends T> convertedConstantBinding);
@Override @SuppressWarnings("unchecked") public V visit(ProviderBinding<? extends T> providerBinding);
```

## DefaultElementVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/DefaultElementVisitor.java)

### DefaultElementVisitor

```java
protected V visitOther(Element element);
@Override public V visit(Message message);
@Override public <T> V visit(Binding<T> binding);
@Override public V visit(InterceptorBinding interceptorBinding);
@Override public V visit(ScopeBinding scopeBinding);
@Override public V visit(TypeConverterBinding typeConverterBinding);
@Override public <T> V visit(ProviderLookup<T> providerLookup);
@Override public V visit(InjectionRequest<?> injectionRequest);
@Override public V visit(StaticInjectionRequest staticInjectionRequest);
@Override public V visit(PrivateElements privateElements);
@Override public <T> V visit(MembersInjectorLookup<T> lookup);
@Override public V visit(TypeListenerBinding binding);
@Override public V visit(ProvisionListenerBinding binding);
@Override public V visit(DisableCircularProxiesOption option);
@Override public V visit(RequireExplicitBindingsOption option);
@Override public V visit(RequireAtInjectOnConstructorsOption option);
@Override public V visit(RequireExactBindingAnnotationsOption option);
@Override public V visit(ModuleAnnotatedMethodScannerBinding binding);
```

## Dependency

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/Dependency.java)

### Dependency

```java
public Dependency(com.google.inject.gee.InjectionPoint injectionPoint, Key<T> key, boolean nullable, int parameterIndex);
public static <T> Dependency<T> get(Key<T> key);
public static Set<Dependency<?>> forInjectionPoints(Set<com.google.inject.gee.InjectionPoint> injectionPoints);
public Key<T> getKey();
public boolean isNullable();
public InjectionPoint getInjectionPoint();
public int getParameterIndex();
@Override public int hashCode();
@Override public boolean equals(Object o);
@Override public String toString();
```

## DisableCircularProxiesOption

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/DisableCircularProxiesOption.java)

### DisableCircularProxiesOption

```java
@Override public Object getSource();
@Override public void applyTo(Binder binder);
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
```

## Element

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/Element.java)

### Element

```java
Object getSource();
<T> T acceptVisitor(ElementVisitor<T> visitor);
void applyTo(Binder binder);
```

## Elements

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/Elements.java)

### Elements

```java
public static List<Element> getElements(Module... modules);
public static List<Element> getElements(Stage stage, Module... modules);
public static List<Element> getElements(Iterable<? extends Module> modules);
public static List<Element> getElements(Stage stage, Iterable<? extends Module> modules);
public static Binder withTrustedSource(GuiceInternal guiceInternal, Binder binder, Object source);
public static Module getModule(final Iterable<? extends Element> elements);
```

## ElementSource

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ElementSource.java)

### ElementSource

```java
public ElementSource getOriginalElementSource();
public Object getDeclaringSource();
public List<String> getModuleClassNames();
@Override public String toString();
```

## ElementVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ElementVisitor.java)

### ElementVisitor

```java
<T> V visit(Binding<T> binding);
V visit(InterceptorBinding binding);
V visit(ScopeBinding binding);
V visit(TypeConverterBinding binding);
V visit(InjectionRequest<?> request);
V visit(StaticInjectionRequest request);
<T> V visit(ProviderLookup<T> lookup);
<T> V visit(MembersInjectorLookup<T> lookup);
V visit(Message message);
V visit(PrivateElements elements);
V visit(TypeListenerBinding binding);
V visit(ProvisionListenerBinding binding);
V visit(RequireExplicitBindingsOption option);
V visit(DisableCircularProxiesOption option);
V visit(RequireAtInjectOnConstructorsOption option);
V visit(RequireExactBindingAnnotationsOption option);
V visit(ModuleAnnotatedMethodScannerBinding binding);
```

## ErrorDetail

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ErrorDetail.java)

### ErrorDetail

```java
protected ErrorDetail(String message, List<Object> sources, Throwable cause);
public boolean isMergeable(ErrorDetail<?> otherError);
public final void format(int index, List<ErrorDetail<?>> mergeableErrors, Formatter formatter);
protected abstract void formatDetail(List<ErrorDetail<?>> mergeableErrors, Formatter formatter);
protected Optional<String> getLearnMoreLink();
protected Optional<String> getErrorIdentifier();
public String getMessage();
public List<Object> getSources();
public Throwable getCause();
@Override public int hashCode();
@Override public boolean equals(Object o);
public abstract SelfT withSources(List<Object> newSources);
```

## ExposedBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ExposedBinding.java)

### ExposedBinding

```java
PrivateElements getPrivateElements();
@Override void applyTo(Binder binder);
```

## HasDependencies

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/HasDependencies.java)

### HasDependencies

```java
Set<Dependency<?>> getDependencies();
```

## InjectionListener

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/InjectionListener.java)

### InjectionListener

```java
void afterInjection(I injectee);
```

## InjectionRequest

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/InjectionRequest.java)

### InjectionRequest

```java
public InjectionRequest(Object source, TypeLiteral<T> type, T instance);
@Override public Object getSource();
public T getInstance();
public TypeLiteral<T> getType();
public Set<com.google.inject.gee.InjectionPoint> getInjectionPoints() throws ConfigurationException;
@Override public <R> R acceptVisitor(ElementVisitor<R> visitor);
@Override public void applyTo(Binder binder);
@Override public boolean equals(Object obj);
@Override public int hashCode();
```

## InstanceBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/InstanceBinding.java)

### InstanceBinding

```java
T getInstance();
Set<InjectionPoint> getInjectionPoints();
```

## InterceptorBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/InterceptorBinding.java)

### InterceptorBinding

```java
@Override public Object getSource();
public Matcher<? super Class<?>> getClassMatcher();
public Matcher<? super Method> getMethodMatcher();
public List<MethodInterceptor> getInterceptors();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
@Override public void applyTo(Binder binder);
```

## LinkedKeyBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/LinkedKeyBinding.java)

### LinkedKeyBinding

```java
Key<? extends T> getLinkedKey();
```

## MembersInjectorLookup

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/MembersInjectorLookup.java)

### MembersInjectorLookup

```java
public MembersInjectorLookup(Object source, TypeLiteral<T> type);
@Override public Object getSource();
public TypeLiteral<T> getType();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
public void initializeDelegate(MembersInjector<T> delegate);
@Override public void applyTo(Binder binder);
public MembersInjector<T> getDelegate();
public Set<com.google.inject.gee.InjectionPoint> getInjectionPoints() throws ConfigurationException;
public MembersInjector<T> getMembersInjector();
@Override public boolean equals(Object obj);
@Override public int hashCode();
```

## Message

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/Message.java)

### Message

```java
public Message(GuiceInternal internalOnly, ErrorId errorId, ErrorDetail<?> errorDetail);
public Message(ErrorId errorId, List<Object> sources, String message, Throwable cause);
public Message(List<Object> sources, String message, Throwable cause);
public Message(String message, Throwable cause);
public Message(Object source, String message);
public Message(String message);
public ErrorDetail<?> getErrorDetail();
@Override public String getSource();
public List<Object> getSources();
public String getMessage();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
public Throwable getCause();
@Override public String toString();
@Override public int hashCode();
@Override public boolean equals(Object o);
@Override public void applyTo(Binder binder);
public Message withSource(List<Object> newSources);
```

## ModuleAnnotatedMethodScanner

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ModuleAnnotatedMethodScanner.java)

### ModuleAnnotatedMethodScanner

```java
public abstract Set<? extends Class<? extends Annotation>> annotationClasses();
public abstract <T> Key<T> prepareMethod(Binder binder, Annotation annotation, Key<T> key, InjectionPoint injectionPoint);
```

## ModuleAnnotatedMethodScannerBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ModuleAnnotatedMethodScannerBinding.java)

### ModuleAnnotatedMethodScannerBinding

```java
public ModuleAnnotatedMethodScannerBinding(Object source, ModuleAnnotatedMethodScanner scanner);
@Override public Object getSource();
public ModuleAnnotatedMethodScanner getScanner();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
@Override public void applyTo(Binder binder);
@Override public String toString();
```

## PrivateElements

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/PrivateElements.java)

### PrivateElements

```java
List<Element> getElements();
Injector getInjector();
Set<Key<?>> getExposedKeys();
Object getExposedSource(Key<?> key);
```

## ProviderBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProviderBinding.java)

### ProviderBinding

```java
Key<?> getProvidedKey();
```

## ProviderInstanceBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProviderInstanceBinding.java)

### ProviderInstanceBinding

```java
@Deprecated Provider<? extends T> getProviderInstance();
Provider<? extends T> getUserSuppliedProvider();
Set<InjectionPoint> getInjectionPoints();
```

## ProviderKeyBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProviderKeyBinding.java)

### ProviderKeyBinding

```java
Key<? extends Provider<? extends T>> getProviderKey();
```

## ProviderLookup

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProviderLookup.java)

### ProviderLookup

```java
public ProviderLookup(Object source, Key<T> key);
public ProviderLookup(Object source, Dependency<T> dependency);
@Override public Object getSource();
public Key<T> getKey();
public Dependency<T> getDependency();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
public void initializeDelegate(Provider<T> delegate);
@Override public void applyTo(Binder binder);
public Provider<T> getDelegate();
public Provider<T> getProvider();
@Override public String toString();
@Override public boolean equals(Object obj);
@Override public int hashCode();
```

## ProviderWithDependencies

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProviderWithDependencies.java)

### ProviderWithDependencies

```java
```

## ProviderWithExtensionVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProviderWithExtensionVisitor.java)

### ProviderWithExtensionVisitor

```java
<B, V> V acceptExtensionVisitor(BindingTargetVisitor<B, V> visitor, ProviderInstanceBinding<? extends B> binding);
```

## ProvidesMethodBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProvidesMethodBinding.java)

### ProvidesMethodBinding

```java
Method getMethod();
Object getEnclosingInstance();
Key<T> getKey();
Annotation getAnnotation();
```

## ProvidesMethodTargetVisitor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProvidesMethodTargetVisitor.java)

### ProvidesMethodTargetVisitor

```java
V visit(ProvidesMethodBinding<? extends T> providesMethodBinding);
```

## ProvisionListener

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProvisionListener.java)

### ProvisionListener

```java
<T> void onProvision(ProvisionInvocation<T> provision);
```

### ProvisionInvocation

```java
public abstract Binding<T> getBinding();
public abstract T provision();
```

## ProvisionListenerBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ProvisionListenerBinding.java)

### ProvisionListenerBinding

```java
public List<ProvisionListener> getListeners();
public Matcher<? super Binding<?>> getBindingMatcher();
@Override public Object getSource();
@Override public <R> R acceptVisitor(ElementVisitor<R> visitor);
@Override public void applyTo(Binder binder);
```

## RequireAtInjectOnConstructorsOption

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/RequireAtInjectOnConstructorsOption.java)

### RequireAtInjectOnConstructorsOption

```java
@Override public Object getSource();
@Override public void applyTo(Binder binder);
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
```

## RequireExactBindingAnnotationsOption

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/RequireExactBindingAnnotationsOption.java)

### RequireExactBindingAnnotationsOption

```java
@Override public Object getSource();
@Override public void applyTo(Binder binder);
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
```

## RequireExplicitBindingsOption

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/RequireExplicitBindingsOption.java)

### RequireExplicitBindingsOption

```java
@Override public Object getSource();
@Override public void applyTo(Binder binder);
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
```

## ScopeBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/ScopeBinding.java)

### ScopeBinding

```java
@Override public Object getSource();
public Class<? extends Annotation> getAnnotationType();
public Scope getScope();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
@Override public void applyTo(Binder binder);
@Override public String toString();
```

## StaticInjectionRequest

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/StaticInjectionRequest.java)

### StaticInjectionRequest

```java
@Override public Object getSource();
public Class<?> getType();
public Set<com.google.inject.gee.InjectionPoint> getInjectionPoints() throws ConfigurationException;
@Override public void applyTo(Binder binder);
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
@Override public boolean equals(Object obj);
@Override public int hashCode();
```

## Toolable

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/Toolable.java)

### Toolable

```java
```

## TypeConverter

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/TypeConverter.java)

### TypeConverter

```java
Object convert(String value, TypeLiteral<?> toType);
```

## TypeConverterBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/TypeConverterBinding.java)

### TypeConverterBinding

```java
public TypeConverterBinding(Object source, Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter typeConverter);
@Override public Object getSource();
public Matcher<? super TypeLiteral<?>> getTypeMatcher();
public TypeConverter getTypeConverter();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
@Override public void applyTo(Binder binder);
@Override public String toString();
```

## TypeEncounter

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/TypeEncounter.java)

### TypeEncounter

```java
void addError(String message, Object... arguments);
void addError(Throwable t);
void addError(Message message);
<T> Provider<T> getProvider(Key<T> key);
<T> Provider<T> getProvider(Class<T> type);
<T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral);
<T> MembersInjector<T> getMembersInjector(Class<T> type);
void register(MembersInjector<? super I> membersInjector);
void register(InjectionListener<? super I> listener);
void bindInterceptor(Matcher<? super Method> methodMatcher, MethodInterceptor... interceptors);
```

## TypeListener

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/TypeListener.java)

### TypeListener

```java
<I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter);
```

## TypeListenerBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/TypeListenerBinding.java)

### TypeListenerBinding

```java
public TypeListener getListener();
public Matcher<? super TypeLiteral<?>> getTypeMatcher();
@Override public Object getSource();
@Override public <T> T acceptVisitor(ElementVisitor<T> visitor);
@Override public void applyTo(Binder binder);
```

## UntargettedBinding

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/spi/UntargettedBinding.java)

### UntargettedBinding

```java
```

## Stage

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/Stage.java)

### Stage

```java
public static final Stage TOOL;
public static final Stage DEVELOPMENT;
public static final Stage PRODUCTION;
```

## TypeLiteral

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/TypeLiteral.java)

### TypeLiteral

```java
@SuppressWarnings("unchecked") protected TypeLiteral();
public final Class<? super T> getRawType();
public final Type getType();
@Override public final int hashCode();
@Override public final boolean equals(Object o);
@Override public final String toString();
public static TypeLiteral<?> get(Type type);
public static <T> TypeLiteral<T> get(Class<T> type);
public TypeLiteral<?> getSupertype(Class<?> supertype);
public TypeLiteral<?> getFieldType(Field field);
public List<TypeLiteral<?>> getParameterTypes(Member methodOrConstructor);
public List<TypeLiteral<?>> getExceptionTypes(Member methodOrConstructor);
public TypeLiteral<?> getReturnType(Method method);
```

## Enhanced

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/util/Enhanced.java)

### Enhanced

```java
public static boolean isEnhanced(Class<?> clazz);
public static <T> Optional<Class<? super T>> unenhancedClass(Class<T> clazz);
```

## Modules

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/util/Modules.java)

### Modules

```java
public static final Module EMPTY_MODULE;
public static OverriddenModuleBuilder override(Module... modules);
@Deprecated public static OverriddenModuleBuilder override();
public static OverriddenModuleBuilder override(Iterable<? extends Module> modules);
public static Module combine(Module... modules);
@Deprecated public static Module combine(Module module);
@Deprecated public static Module combine();
public static Module combine(Iterable<? extends Module> modules);
public static Module requireExplicitBindingsModule();
public static Module requireAtInjectOnConstructorsModule();
public static Module requireExactBindingAnnotationsModule();
public static Module disableCircularProxiesModule();
```

### OverriddenModuleBuilder

```java
Module with(Module... overrides);
@Deprecated public Module with();
Module with(Iterable<? extends Module> overrides);
```

## Providers

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/util/Providers.java)

### Providers

```java
public static <T> Provider<T> of(final T instance);
@Deprecated public static <T> Provider<T> guicify(Provider<T> provider);
public static <T> Provider<T> guicify(jakarta.inject.Provider<T> provider);
```

## Types

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/guice-core/src/main/java/com/google/inject/util/Types.java)

### Types

```java
public static ParameterizedType newParameterizedType(Type rawType, Type... typeArguments);
public static ParameterizedType newParameterizedTypeWithOwner(Type ownerType, Type rawType, Type... typeArguments);
public static GenericArrayType arrayOf(Type componentType);
public static WildcardType subtypeOf(Type bound);
public static WildcardType supertypeOf(Type bound);
public static ParameterizedType listOf(Type elementType);
public static ParameterizedType collectionOf(Type elementType);
public static ParameterizedType setOf(Type elementType);
public static ParameterizedType mapOf(Type keyType, Type valueType);
public static ParameterizedType providerOf(Type providedType);
public static Type jakartaProviderOf(Type type);
```
