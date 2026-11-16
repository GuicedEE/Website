# com.guicedee.modules.services:cache-annotations-ri-common

javax.cache.commons

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `cache.annotations.ri.common`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:cache-api`; scope `compile`.
- `io.smallrye.reactive:mutiny`; scope `compile`.

## JPMS descriptor

```java
module cache.annotations.ri.common {
    requires transitive cache.api;
    requires transitive io.smallrye.mutiny;

    requires transitive java.logging;
    exports org.jsr107.ri.annotations;

}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## AbstractCacheInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractCacheInterceptor.java)

### AbstractCacheInterceptor

```java
protected abstract Object proceed(I invocation) throws Throwable;
protected Class<?> getReturnType(I invocation);
```

## AbstractCacheLookupUtil

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractCacheLookupUtil.java)

### AbstractCacheLookupUtil

```java
public AbstractCacheLookupUtil();
@Override public InternalCacheKeyInvocationContext<? extends Annotation> getCacheKeyInvocationContext(I invocation);
protected abstract InternalCacheKeyInvocationContext<? extends Annotation> createCacheKeyInvocationContextImpl(StaticCacheKeyInvocationContext<? extends Annotation> staticCacheKeyInvocationContext, I invocation);
@Override public InternalCacheInvocationContext<? extends Annotation> getCacheInvocationContext(I invocation);
protected abstract InternalCacheInvocationContext<? extends Annotation> createCacheInvocationContextImpl(StaticCacheInvocationContext<? extends Annotation> staticCacheInvocationContext, I invocation);
protected <T extends Annotation> T getAnnotation(Class<T> annotationClass, Method method, Class<? extends Object> targetClass);
@Override public StaticCacheInvocationContext<? extends Annotation> getMethodDetails(Method method, Class<? extends Object> targetClass);
protected abstract Class<?> getTargetClass(I invocation);
protected abstract Method getMethod(I invocation);
protected <A extends Annotation> CacheMethodDetails<A> createCacheMethodDetails(A cacheAnnotation, CacheDefaults cacheDefaultsAnnotation, String methodCacheName, Method method, Class<? extends Object> targetClass);
protected CacheResultMethodDetails createCacheResultMethodDetails(CacheResult cacheResultAnnotation, CacheDefaults cacheDefaultsAnnotation, Method method, Class<? extends Object> targetClass);
protected CachePutMethodDetails createCachePutMethodDetails(CachePut cachePutAnnotation, CacheDefaults cacheDefaultsAnnotation, Method method, Class<? extends Object> targetClass);
protected CacheRemoveEntryMethodDetails createCacheRemoveEntryMethodDetails(CacheRemove cacheRemoveAnnotation, CacheDefaults cacheDefaultsAnnotation, Method method, Class<? extends Object> targetClass);
protected CacheRemoveAllMethodDetails createCacheRemoveAllMethodDetails(CacheRemoveAll cacheRemoveAllAnnotation, CacheDefaults cacheDefaultsAnnotation, Method method, Class<? extends Object> targetClass);
protected Set<Annotation> getMethodAnnotations(Method method);
protected ParameterDetails getParameterDetails(final Method method, final boolean cacheValueAllowed);
protected abstract <T> T getObjectByType(Class<T> type);
protected abstract CacheKeyGenerator getDefaultCacheKeyGenerator();
protected abstract CacheResolverFactory getDefaultCacheResolverFactory();
protected CacheKeyGenerator getCacheKeyGenerator(Class<? extends CacheKeyGenerator> cacheKeyGeneratorType, CacheDefaults cacheDefaultsAnnotation);
protected CacheResolverFactory getCacheResolverFactory(Class<? extends CacheResolverFactory> cacheResolverFactoryType, CacheDefaults cacheDefaultsAnnotation);
protected String resolveCacheName(String methodCacheName, CacheDefaults cacheDefaultsAnnotation, Method method, Class<? extends Object> targetClass);
```

## AbstractCachePutInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractCachePutInterceptor.java)

### AbstractCachePutInterceptor

```java
public Object cachePut(CacheContextSource<I> cacheContextSource, I invocation) throws Throwable;
protected void cacheValue(final InternalCacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext, final CachePutMethodDetails methodDetails, final Object value);
```

## AbstractCacheRemoveAllInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractCacheRemoveAllInterceptor.java)

### AbstractCacheRemoveAllInterceptor

```java
public final Object cacheRemoveAll(CacheContextSource<I> cacheContextSource, I invocation) throws Throwable;
protected void removeAll(final InternalCacheInvocationContext<? extends Annotation> cacheInvocationContext, final StaticCacheInvocationContext<CacheRemoveAll> methodDetails);
@SuppressWarnings("unchecked") protected <T extends StaticCacheInvocationContext<?>> T getCacheInvocationContext(final InternalCacheInvocationContext<? extends Annotation> cacheInvocationContext, final InterceptorType interceptorType);
```

## AbstractCacheRemoveEntryInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractCacheRemoveEntryInterceptor.java)

### AbstractCacheRemoveEntryInterceptor

```java
public final Object cacheRemoveEntry(CacheContextSource<I> cacheContextSource, I invocation) throws Throwable;
```

## AbstractCacheResultInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractCacheResultInterceptor.java)

### AbstractCacheResultInterceptor

```java
public final Object cacheResult(CacheContextSource<I> cacheContextSource, I invocation) throws Throwable;
protected void checkForCachedException(final Cache<Object, Throwable> exceptionCache, final GeneratedCacheKey cacheKey) throws Throwable;
protected Throwable getCachedException(final Cache<Object, Throwable> exceptionCache, final GeneratedCacheKey cacheKey);
protected void cacheException(final Cache<Object, Throwable> exceptionCache, final GeneratedCacheKey cacheKey, final CacheResult cacheResultAnnotation, Throwable t);
protected Cache<Object, Throwable> getExceptionCache(final InternalCacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext, final CacheResultMethodDetails methodDetails);
protected Uni<?> cacheUniResult(final Uni<?> uni, final Cache<Object, Object> cache, final GeneratedCacheKey cacheKey, final Cache<Object, Throwable> exceptionCache, final CacheResult cacheResultAnnotation);
protected Object createResult(final Object result, final I invocation);
protected Object createFailure(final Throwable throwable, final I invocation) throws Throwable;
```

## AbstractInternalCacheInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractInternalCacheInvocationContext.java)

### AbstractInternalCacheInvocationContext

```java
public AbstractInternalCacheInvocationContext(StaticCacheInvocationContext<A> staticCacheInvocationContext, I invocation);
protected abstract Object[] getParameters(I invocation);
protected abstract Method getMethod(I invocation);
protected abstract Object getTarget(I invocation);
@Override public StaticCacheInvocationContext<A> getStaticCacheInvocationContext();
@Override public Set<Annotation> getAnnotations();
@Override public A getCacheAnnotation();
@Override public String getCacheName();
@Override public Method getMethod();
@Override public Object getTarget();
@Override public CacheInvocationParameter[] getAllParameters();
@Override public <T> T unwrap(Class<T> cls);
```

## AbstractInternalCacheKeyInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractInternalCacheKeyInvocationContext.java)

### AbstractInternalCacheKeyInvocationContext

```java
public AbstractInternalCacheKeyInvocationContext(StaticCacheKeyInvocationContext<A> staticCacheKeyInvocationContext, I invocation);
@Override public StaticCacheKeyInvocationContext<A> getStaticCacheKeyInvocationContext();
@Override protected Object[] getParameters(I invocation);
@Override protected Method getMethod(I invocation);
@Override protected Object getTarget(I invocation);
@Override public CacheInvocationParameter[] getKeyParameters();
@Override public CacheInvocationParameter getValueParameter();
```

## AbstractKeyedCacheInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractKeyedCacheInterceptor.java)

### AbstractKeyedCacheInterceptor

```java
@SuppressWarnings("unchecked") protected T getStaticCacheKeyInvocationContext(final InternalCacheKeyInvocationContext<?> cacheInvocationContext, final InterceptorType interceptorType);
```

## AbstractStaticCacheInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/AbstractStaticCacheInvocationContext.java)

### AbstractStaticCacheInvocationContext

```java
public AbstractStaticCacheInvocationContext(CacheMethodDetails<A> cacheMethodDetails, CacheResolver cacheResolver, List<CacheParameterDetails> allParameters);
@Override public List<CacheParameterDetails> getAllParameters();
@Override public CacheResolver getCacheResolver();
@Override public Method getMethod();
@Override public Set<Annotation> getAnnotations();
@Override public A getCacheAnnotation();
@Override public String getCacheName();
```

## CacheContextSource

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/CacheContextSource.java)

### CacheContextSource

```java
InternalCacheKeyInvocationContext<? extends Annotation> getCacheKeyInvocationContext(I invocation);
InternalCacheInvocationContext<? extends Annotation> getCacheInvocationContext(I invocation);
StaticCacheInvocationContext<? extends Annotation> getMethodDetails(Method method, Class<? extends Object> targetClass);
```

## CacheInvocationParameterImpl

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/CacheInvocationParameterImpl.java)

### CacheInvocationParameterImpl

```java
public CacheInvocationParameterImpl(CacheParameterDetails cacheParameterDetails, Object value);
@Override public Class<?> getRawType();
@Override public Object getValue();
@Override public Set<Annotation> getAnnotations();
@Override public int getParameterPosition();
```

## CacheMethodDetailsImpl

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/CacheMethodDetailsImpl.java)

### CacheMethodDetailsImpl

```java
public CacheMethodDetailsImpl(Method targetMethod, Set<Annotation> methodAnotations, A cacheAnnotation, String cacheName);
@Override public Method getMethod();
@Override public Set<Annotation> getAnnotations();
@Override public A getCacheAnnotation();
@Override public String getCacheName();
```

## CacheParameterDetails

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/CacheParameterDetails.java)

### CacheParameterDetails

```java
public CacheParameterDetails(Class<?> rawType, Set<Annotation> annotations, int parameterPosition);
public Class<?> getRawType();
public Set<Annotation> getAnnotations();
public int getParameterPosition();
```

## CachePutMethodDetails

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/CachePutMethodDetails.java)

### CachePutMethodDetails

```java
public CachePutMethodDetails(CacheMethodDetails<CachePut> cacheMethodDetails, CacheResolver cacheResolver, CacheKeyGenerator cacheKeyGenerator, List<CacheParameterDetails> allParameters, List<CacheParameterDetails> keyParameters, CacheParameterDetails cacheValueParameter);
@Override public InterceptorType getInterceptorType();
public CacheParameterDetails getCacheValueParameter();
```

## CacheRemoveAllMethodDetails

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/CacheRemoveAllMethodDetails.java)

### CacheRemoveAllMethodDetails

```java
public CacheRemoveAllMethodDetails(CacheMethodDetails<CacheRemoveAll> cacheMethodDetails, CacheResolver cacheResolver, List<CacheParameterDetails> allParameters);
@Override public InterceptorType getInterceptorType();
```

## CacheRemoveEntryMethodDetails

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/CacheRemoveEntryMethodDetails.java)

### CacheRemoveEntryMethodDetails

```java
public CacheRemoveEntryMethodDetails(CacheMethodDetails<CacheRemove> cacheMethodDetails, CacheResolver cacheResolver, CacheKeyGenerator cacheKeyGenerator, List<CacheParameterDetails> allParameters, List<CacheParameterDetails> keyParameters);
@Override public InterceptorType getInterceptorType();
```

## CacheResultMethodDetails

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/CacheResultMethodDetails.java)

### CacheResultMethodDetails

```java
public CacheResultMethodDetails(CacheMethodDetails<CacheResult> cacheMethodDetails, CacheResolver cacheResolver, CacheResolver exceptionCacheResolver, CacheKeyGenerator cacheKeyGenerator, List<CacheParameterDetails> allParameters, List<CacheParameterDetails> keyParameters);
@Override public CacheResolver getExceptionCacheResolver();
@Override public InterceptorType getInterceptorType();
```

## ClassFilter

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/ClassFilter.java)

### ClassFilter

```java
public static <T> boolean isIncluded(T candidate, Class<? extends T>[] included, Class<? extends T>[] excluded, boolean includeBothEmpty);
public static <T> boolean matches(T candidate, Class<? extends T>[] classes);
```

## DefaultCacheKeyGenerator

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/DefaultCacheKeyGenerator.java)

### DefaultCacheKeyGenerator

```java
@Override public GeneratedCacheKey generateCacheKey(CacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext);
```

## DefaultCacheResolver

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/DefaultCacheResolver.java)

### DefaultCacheResolver

```java
public DefaultCacheResolver(Cache<?, ?> cache);
@Override @SuppressWarnings("unchecked") public <K, V> Cache<K, V> resolveCache(CacheInvocationContext<? extends Annotation> cacheInvocationContext);
```

## DefaultCacheResolverFactory

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/DefaultCacheResolverFactory.java)

### DefaultCacheResolverFactory

```java
public DefaultCacheResolverFactory(CacheManager cacheManager);
public DefaultCacheResolverFactory();
@Override public CacheResolver getCacheResolver(CacheMethodDetails<? extends Annotation> cacheMethodDetails);
@Override public CacheResolver getExceptionCacheResolver(CacheMethodDetails<CacheResult> cacheMethodDetails);
```

## DefaultGeneratedCacheKey

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/DefaultGeneratedCacheKey.java)

### DefaultGeneratedCacheKey

```java
public DefaultGeneratedCacheKey(Object[] parameters);
@Override public int hashCode();
@Override public boolean equals(Object obj);
```

## InterceptorType

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/InterceptorType.java)

### InterceptorType

```java
public static final InterceptorType CACHE_RESULT;
public static final InterceptorType CACHE_PUT;
public static final InterceptorType CACHE_REMOVE_ENTRY;
public static final InterceptorType CACHE_REMOVE_ALL;
```

## InternalCacheInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/InternalCacheInvocationContext.java)

### InternalCacheInvocationContext

```java
StaticCacheInvocationContext<A> getStaticCacheInvocationContext();
```

## InternalCacheKeyInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/InternalCacheKeyInvocationContext.java)

### InternalCacheKeyInvocationContext

```java
StaticCacheKeyInvocationContext<A> getStaticCacheKeyInvocationContext();
```

## StaticCacheInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/StaticCacheInvocationContext.java)

### StaticCacheInvocationContext

```java
InterceptorType getInterceptorType();
List<CacheParameterDetails> getAllParameters();
CacheResolver getCacheResolver();
```

## StaticCacheKeyInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/StaticCacheKeyInvocationContext.java)

### StaticCacheKeyInvocationContext

```java
public StaticCacheKeyInvocationContext(CacheMethodDetails<A> cacheMethodDetails, CacheResolver cacheResolver, CacheKeyGenerator cacheKeyGenerator, List<CacheParameterDetails> allParameters, List<CacheParameterDetails> keyParameters);
public CacheKeyGenerator getCacheKeyGenerator();
public List<CacheParameterDetails> getKeyParameters();
```

## StaticCacheResultInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-annotations-ri-common/src/main/java/org/jsr107/ri/annotations/StaticCacheResultInvocationContext.java)

### StaticCacheResultInvocationContext

```java
CacheResolver getExceptionCacheResolver();
```
