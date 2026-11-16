# com.guicedee.modules.services:cache-annotations-ri-guice

javax.cache.guice

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `cache.annotations.ri.guice`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:cache-annotations-ri-common`; scope `compile`.
- `com.guicedee.modules.services:guice`; scope `compile`.
- `com.guicedee:inject`; scope `compile`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.

## JPMS descriptor

```java
module cache.annotations.ri.guice {
    requires java.logging;

    requires transitive cache.annotations.ri.common;
    requires transitive com.google.guice;
    requires transitive com.guicedee.client;

    provides com.guicedee.client.services.lifecycle.IGuiceModule with org.jsr107.ri.annotations.guice.module.CacheAnnotationsModule;

    exports org.jsr107.ri.annotations.guice.module;

    opens org.jsr107.ri.annotations.guice.module to com.google.guice;
    opens org.jsr107.ri.annotations.guice to com.google.guice;
    exports org.jsr107.ri.annotations.guice;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## CacheLookupUtil

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/CacheLookupUtil.java)

### CacheLookupUtil

```java
@Inject public CacheLookupUtil(Injector injector, CacheKeyGenerator defaultCacheKeyGenerator, CacheResolverFactory defaultCacheResolverFactory);
@SuppressWarnings({"unchecked"}) @Override protected InternalCacheKeyInvocationContext<? extends Annotation> createCacheKeyInvocationContextImpl(StaticCacheKeyInvocationContext<? extends Annotation> staticCacheKeyInvocationContext, MethodInvocation invocation);
@SuppressWarnings({"unchecked"}) @Override protected InternalCacheInvocationContext<? extends Annotation> createCacheInvocationContextImpl(StaticCacheInvocationContext<? extends Annotation> staticCacheInvocationContext, MethodInvocation invocation);
@Override protected Class<?> getTargetClass(MethodInvocation invocation);
@Override protected Method getMethod(MethodInvocation invocation);
@Override protected <T> T getObjectByType(Class<T> type);
@Override protected CacheKeyGenerator getDefaultCacheKeyGenerator();
@Override protected CacheResolverFactory getDefaultCacheResolverFactory();
```

## CacheMethodInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/CacheMethodInterceptor.java)

### CacheMethodInterceptor

```java
InterceptorType getInterceptorType();
```

## CachePutInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/CachePutInterceptor.java)

### CachePutInterceptor

```java
@Inject public void setCacheContextSource(CacheContextSource<MethodInvocation> cacheContextSource);
@Override public InterceptorType getInterceptorType();
@Override public Object invoke(MethodInvocation invocation) throws Throwable;
@Override protected Object proceed(MethodInvocation invocation) throws Throwable;
```

## CacheRemoveAllInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/CacheRemoveAllInterceptor.java)

### CacheRemoveAllInterceptor

```java
@Inject public void setCacheContextSource(CacheContextSource<MethodInvocation> cacheContextSource);
@Override public InterceptorType getInterceptorType();
@Override public Object invoke(MethodInvocation invocation) throws Throwable;
@Override protected Object proceed(MethodInvocation invocation) throws Throwable;
```

## CacheRemoveEntryInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/CacheRemoveEntryInterceptor.java)

### CacheRemoveEntryInterceptor

```java
@Inject public void setCacheContextSource(CacheContextSource<MethodInvocation> cacheContextSource);
@Override public InterceptorType getInterceptorType();
@Override public Object invoke(MethodInvocation invocation) throws Throwable;
@Override protected Object proceed(MethodInvocation invocation) throws Throwable;
```

## CacheResultInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/CacheResultInterceptor.java)

### CacheResultInterceptor

```java
@Inject public void setCacheContextSource(CacheContextSource<MethodInvocation> cacheContextSource);
@Override public InterceptorType getInterceptorType();
@Override public Object invoke(MethodInvocation invocation) throws Throwable;
@Override protected Object proceed(MethodInvocation invocation) throws Throwable;
@Override protected Class<?> getReturnType(MethodInvocation invocation);
```

## GuiceInternalCacheInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/GuiceInternalCacheInvocationContext.java)

### GuiceInternalCacheInvocationContext

```java
public GuiceInternalCacheInvocationContext(StaticCacheInvocationContext<A> staticCacheInvocationContext, MethodInvocation invocation);
@Override protected Object[] getParameters(MethodInvocation invocation);
@Override protected Method getMethod(MethodInvocation invocation);
@Override protected Object getTarget(MethodInvocation invocation);
```

## GuiceInternalCacheKeyInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/GuiceInternalCacheKeyInvocationContext.java)

### GuiceInternalCacheKeyInvocationContext

```java
public GuiceInternalCacheKeyInvocationContext(StaticCacheKeyInvocationContext<A> staticCacheKeyInvocationContext, MethodInvocation invocation);
@Override protected Object[] getParameters(MethodInvocation invocation);
@Override protected Method getMethod(MethodInvocation invocation);
@Override protected Object getTarget(MethodInvocation invocation);
```

## CacheAnnotationsModule

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-guice/src/main/java/org/jsr107/ri/annotations/guice/module/CacheAnnotationsModule.java)

### CacheAnnotationsModule

```java
@SuppressWarnings("unchecked") @Override protected void configure();
```
