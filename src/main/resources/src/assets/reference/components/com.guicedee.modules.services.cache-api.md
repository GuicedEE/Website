# com.guicedee.modules.services:cache-api

https://github.com/jsr107/jsr107spec

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `cache.api`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## JPMS descriptor

```java
module cache.api {
    requires java.management;

    exports javax.cache;
    exports javax.cache.annotation;
    exports javax.cache.configuration;
    exports javax.cache.event;
    exports javax.cache.expiry;
    exports javax.cache.integration;
    exports javax.cache.management;
    exports javax.cache.processor;
    exports javax.cache.spi;

    uses javax.cache.spi.CachingProvider;

}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/README.md). Historical version examples retain their original context.

JSR107 (JCache)
===============

About
-----

*JCache* is the Java caching API. It was defined by JSR107. It defines a standard Java Caching API for use by developers and a standard SPI ("Service
 Provider Interface") for use by implementers.


## Releases

* 10 May 2019: 1.1.1 Maintenance Release.
* 16 December 2017: 1.1.0 Maintenance Release.
* 18 March 2014:  1.0.0. Final Release. Unchanged from 1.0.0-RC1.
* 16 December 2013:  1.0.0-RC1. This is the version matching the final specification and is what is up on https://jcp.org/aboutJava/communityprocess/final/jsr107/index.html.
* 21 October 2013:  1.0.0-PFD for the cache-api and 0.11 for other artifacts.Proposed Final Draft
* 26 August 2013:   0.10 Third Public Review Draft
* 25 June 2013:     0.9 Second Public Review Draft
* 25 June 2013:     0.8 Public Review Draft
* 17 April 2013     0.7
* 12 February 2013  0.6
* 13 March 2012:    0.5 Early Draft Submission uses this release.
* December 2011:    0.4
* 12 October 2011:  0.3
* 16 August 2011:   0.2 Initial release

Maven snippet:

    <dependency>
      <groupId>javax.cache</groupId>
      <artifactId>cache-api</artifactId>
      <version>1.0.0</version><type>jar</type>
            </dependency>


Maven Central Releases
----------------------

Releases of jars for binaries, source and javadoc are available on Maven central.

Download the cache-api from <https://oss.sonatype.org/index.html#nexus-search;quick~javax-cache>

or use the following Maven snippet:

    <dependency>
      <groupId>javax.cache</groupId>
      <artifactId>cache-api</artifactId>
      <version>1.1.1</version><type>jar</type>
            </dependency>

Javadoc
-------

The JavaDoc is available as a jar with the releases. You can also find the JavaDoc [online](http://www.javadoc.io/doc/javax.cache/cache-api/1.1.1).

Specification
-------------

The specification is available online as a [Google Doc](https://docs.google.com/document/d/1ijduF_tmHvBaUS7VBBU2ZN8_eEBiFaXXg9OI0_ZxCrA/edit?usp=sharing).

Reference Implementation
------------------------

The reference implementation ("RI") source is available on [GitHub](https://github.com/jsr107/RI).

This implementation is not meant for production use. For that we would refer you
to one of the many high quality open source and commercial implementations of JCache. See the [official list of compatible implementions](https://jcp.org/aboutJava/communityprocess/implementations/jsr107/index.html),
and also the community maintained [JSR107 Test Zoo](https://github.com/cruftex/jsr107-test-zoo/blob/master/report.md).

The RI is there to ensure that the specification and API works as its only purpose.

For example, some things that we leave out:

- tiered storage. A simple on-heap store is used.
- replicated or distributed caching

Why did we do this? Because a much greater engineering effort, which gets put into the open source and commercial caches
which implement this API, is required to accomplish these things.

Having said that, the RI is Apache 2 and is a correct implementation of the spec. It can be used to create new cache
implementations.

Building From Source
--------------------

Building uses Maven in all modules. Maven 3.3.9 - 3.5.4 have been tested.

JCache is compatible with Java 6 to Java 11. We have tested building from Java 8 and Java 11.

See each module's README.md for build instructions.

Please add the following to your settings.xml to enable the CDI RI to be sucked down from JBoss.

    <profiles>
            <profile>
                <id>jboss-public-repository</id>
                <repositories>
                    <repository>
                        <id>jboss-public-repository-group</id>
                        <name>JBoss Public Maven Repository Group</name>
                        <url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
                        <layout>default</layout>
                        <releases>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </releases>
                        <snapshots>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </snapshots>
                    </repository>
                </repositories>
                <pluginRepositories>
                    <pluginRepository>
                        <id>jboss-public-repository-group</id>
                        <name>JBoss Public Maven Repository Group</name>
                        <url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
                        <layout>default</layout>
                        <releases>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </releases>
                        <snapshots>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </snapshots>
                    </pluginRepository>
                </pluginRepositories>
            </profile>
    </profiles>



Testing Implementions of JSR107
-------------------------------

See the [TCK User Guide](https://docs.google.com/document/d/1w3Ugj_oEqjMlhpCkGQOZkd9iPf955ZWHAVdZzEwYYdU/edit?usp=sharing)
for instructions on how to use this TCK.

Mailing list
------------

Please join the mailing list if you're interested in using or developing the software: <http://groups.google.com/group/jsr107>


Issue tracker
-------------

Please log issues to: <https://github.com/jsr107/jsr107spec/issues>


User Questions
--------------

Some of the expert group monitor the tags "jcache" and "jsr107" on [stackoverflow](http://stackoverflow.com).


Contributing
------------

Admission to the Expert Group is closed, but please feel free to post to the mailing list.


License
-------

The API is available under the Apache 2.0 license.

The TCK is available under a restricted Standalone TCK (SATCK) license and must be
licensed from Oracle as is the usual case with JSRs.

The reference implementation is available under an Apache 2.0 license.

For details please read the license in each source code file.

Contributors
------------

This free, open source software was made possible by the JSR107 Expert Group who put many hours of hard work into it.


Copyright
---------

Copyright (c) JSR107 Expert Group

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## CacheDefaults

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheDefaults.java)

### CacheDefaults

```java
String cacheName() default "";
Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;
Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
```

## CacheInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheInvocationContext.java)

### CacheInvocationContext

```java
Object getTarget();
CacheInvocationParameter[] getAllParameters();
<T> T unwrap(java.lang.Class<T> cls);
```

## CacheInvocationParameter

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheInvocationParameter.java)

### CacheInvocationParameter

```java
Class<?> getRawType();
Object getValue();
Set<Annotation> getAnnotations();
int getParameterPosition();
```

## CacheKey

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheKey.java)

### CacheKey

```java
```

## CacheKeyGenerator

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheKeyGenerator.java)

### CacheKeyGenerator

```java
GeneratedCacheKey generateCacheKey(CacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext);
```

## CacheKeyInvocationContext

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheKeyInvocationContext.java)

### CacheKeyInvocationContext

```java
CacheInvocationParameter[] getKeyParameters();
CacheInvocationParameter getValueParameter();
```

## CacheMethodDetails

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheMethodDetails.java)

### CacheMethodDetails

```java
Method getMethod();
Set<Annotation> getAnnotations();
A getCacheAnnotation();
String getCacheName();
```

## CachePut

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CachePut.java)

### CachePut

```java
String cacheName() default "";
boolean afterInvocation() default true;
Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;
Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
Class<? extends Throwable>[] cacheFor() default {};
Class<? extends Throwable>[] noCacheFor() default {};
```

## CacheRemove

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheRemove.java)

### CacheRemove

```java
String cacheName() default "";
boolean afterInvocation() default true;
Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;
Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
Class<? extends Throwable>[] evictFor() default {};
Class<? extends Throwable>[] noEvictFor() default {};
```

## CacheRemoveAll

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheRemoveAll.java)

### CacheRemoveAll

```java
String cacheName() default "";
boolean afterInvocation() default true;
Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;
Class<? extends Throwable>[] evictFor() default {};
Class<? extends Throwable>[] noEvictFor() default {};
```

## CacheResolver

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheResolver.java)

### CacheResolver

```java
<K, V> Cache<K, V> resolveCache(CacheInvocationContext<? extends Annotation> cacheInvocationContext);
```

## CacheResolverFactory

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheResolverFactory.java)

### CacheResolverFactory

```java
CacheResolver getCacheResolver(CacheMethodDetails<? extends Annotation> cacheMethodDetails);
CacheResolver getExceptionCacheResolver(CacheMethodDetails<CacheResult> cacheMethodDetails);
```

## CacheResult

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheResult.java)

### CacheResult

```java
String cacheName() default "";
boolean skipGet() default false;
Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;
Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
String exceptionCacheName() default "";
Class<? extends Throwable>[] cachedExceptions() default {};
Class<? extends Throwable>[] nonCachedExceptions() default {};
```

## CacheValue

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/CacheValue.java)

### CacheValue

```java
```

## GeneratedCacheKey

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/annotation/GeneratedCacheKey.java)

### GeneratedCacheKey

```java
@Override int hashCode();
@Override boolean equals(Object object);
```

## Cache

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/Cache.java)

### Cache

```java
V get(K key);
Map<K, V> getAll(Set<? extends K> keys);
boolean containsKey(K key);
void loadAll(Set<? extends K> keys, boolean replaceExistingValues, CompletionListener completionListener);
void put(K key, V value);
V getAndPut(K key, V value);
void putAll(java.util.Map<? extends K, ? extends V> map);
boolean putIfAbsent(K key, V value);
boolean remove(K key);
boolean remove(K key, V oldValue);
V getAndRemove(K key);
boolean replace(K key, V oldValue, V newValue);
boolean replace(K key, V value);
V getAndReplace(K key, V value);
void removeAll(Set<? extends K> keys);
void removeAll();
void clear();
<C extends Configuration<K, V>> C getConfiguration(Class<C> clazz);
<T> T invoke(K key, EntryProcessor<K, V, T> entryProcessor, Object... arguments) throws EntryProcessorException;
<T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> keys, EntryProcessor<K, V, T> entryProcessor, Object... arguments);
String getName();
CacheManager getCacheManager();
void close();
boolean isClosed();
<T> T unwrap(java.lang.Class<T> clazz);
void registerCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration);
void deregisterCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration);
Iterator<Cache.Entry<K, V>> iterator();
```

### Entry

```java
K getKey();
V getValue();
<T> T unwrap(Class<T> clazz);
```

## CacheException

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/CacheException.java)

### CacheException

```java
public CacheException();
public CacheException(String message);
public CacheException(String message, Throwable cause);
public CacheException(Throwable cause);
```

## CacheManager

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/CacheManager.java)

### CacheManager

```java
CachingProvider getCachingProvider();
URI getURI();
ClassLoader getClassLoader();
Properties getProperties();
<K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String cacheName, C configuration) throws IllegalArgumentException;
<K, V> Cache<K, V> getCache(String cacheName, Class<K> keyType, Class<V> valueType);
<K, V> Cache<K, V> getCache(String cacheName);
Iterable<String> getCacheNames();
void destroyCache(String cacheName);
void enableManagement(String cacheName, boolean enabled);
void enableStatistics(String cacheName, boolean enabled);
void close();
boolean isClosed();
<T> T unwrap(java.lang.Class<T> clazz);
```

## Caching

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/Caching.java)

### Caching

```java
public static final String JAVAX_CACHE_CACHING_PROVIDER;
public static ClassLoader getDefaultClassLoader();
public static void setDefaultClassLoader(ClassLoader classLoader);
public static CachingProvider getCachingProvider();
public static CachingProvider getCachingProvider(ClassLoader classLoader);
public static Iterable<CachingProvider> getCachingProviders();
public static Iterable<CachingProvider> getCachingProviders(ClassLoader classLoader);
public static CachingProvider getCachingProvider(String fullyQualifiedClassName);
public static CachingProvider getCachingProvider(String fullyQualifiedClassName, ClassLoader classLoader);
public static <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyType, Class<V> valueType);
```

## CacheEntryListenerConfiguration

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/configuration/CacheEntryListenerConfiguration.java)

### CacheEntryListenerConfiguration

```java
Factory<CacheEntryListener<? super K, ? super V>> getCacheEntryListenerFactory();
boolean isOldValueRequired();
Factory<CacheEntryEventFilter<? super K, ? super V>> getCacheEntryEventFilterFactory();
boolean isSynchronous();
```

## CompleteConfiguration

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/configuration/CompleteConfiguration.java)

### CompleteConfiguration

```java
boolean isReadThrough();
boolean isWriteThrough();
boolean isStatisticsEnabled();
boolean isManagementEnabled();
Iterable<CacheEntryListenerConfiguration<K, V>> getCacheEntryListenerConfigurations();
Factory<CacheLoader<K, V>> getCacheLoaderFactory();
Factory<CacheWriter<? super K, ? super V>> getCacheWriterFactory();
Factory<ExpiryPolicy> getExpiryPolicyFactory();
```

## Configuration

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/configuration/Configuration.java)

### Configuration

```java
Class<K> getKeyType();
Class<V> getValueType();
boolean isStoreByValue();
```

## Factory

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/configuration/Factory.java)

### Factory

```java
T create();
```

## FactoryBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/configuration/FactoryBuilder.java)

### FactoryBuilder

```java
public static <T> Factory<T> factoryOf(Class<T> clazz);
public static <T> Factory<T> factoryOf(String className);
public static <T extends Serializable> Factory<T> factoryOf(T instance);
```

### ClassFactory

```java
public static final long serialVersionUID;
public ClassFactory(Class<T> clazz);
public ClassFactory(String className);
@Override public T create();
@Override public boolean equals(Object other);
@Override public int hashCode();
```

### SingletonFactory

```java
public static final long serialVersionUID;
public SingletonFactory(T instance);
@Override public T create();
@Override public boolean equals(Object other);
@Override public int hashCode();
```

## MutableCacheEntryListenerConfiguration

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/configuration/MutableCacheEntryListenerConfiguration.java)

### MutableCacheEntryListenerConfiguration

```java
public static final long serialVersionUID;
public MutableCacheEntryListenerConfiguration(CacheEntryListenerConfiguration<K, V> configuration);
public MutableCacheEntryListenerConfiguration(Factory<? extends CacheEntryListener<? super K, ? super V>> listenerFactory, Factory<? extends CacheEntryEventFilter<? super K, ? super V>> filterFactory, boolean isOldValueRequired, boolean isSynchronous);
@Override public Factory<CacheEntryListener<? super K, ? super V>> getCacheEntryListenerFactory();
public MutableCacheEntryListenerConfiguration<K, V> setCacheEntryListenerFactory(Factory<? extends CacheEntryListener<? super K, ? super V>> listenerFactory);
@Override public Factory<CacheEntryEventFilter<? super K, ? super V>> getCacheEntryEventFilterFactory();
public MutableCacheEntryListenerConfiguration<K, V> setCacheEntryEventFilterFactory(Factory<? extends CacheEntryEventFilter<? super K, ? super V>> filterFactory);
@Override public boolean isOldValueRequired();
public MutableCacheEntryListenerConfiguration<K, V> setOldValueRequired(boolean isOldValueRequired);
@Override public boolean isSynchronous();
public MutableCacheEntryListenerConfiguration<K, V> setSynchronous(boolean isSynchronous);
@Override public int hashCode();
@Override public boolean equals(Object object);
```

## MutableConfiguration

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/configuration/MutableConfiguration.java)

### MutableConfiguration

```java
public static final long serialVersionUID;
public MutableConfiguration();
public MutableConfiguration(CompleteConfiguration<K, V> configuration);
@Override public Class<K> getKeyType();
@Override public Class<V> getValueType();
public MutableConfiguration<K, V> setTypes(Class<K> keyType, Class<V> valueType);
@Override public Iterable<CacheEntryListenerConfiguration<K, V>> getCacheEntryListenerConfigurations();
public MutableConfiguration<K, V> addCacheEntryListenerConfiguration(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration);
public MutableConfiguration<K, V> removeCacheEntryListenerConfiguration(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration);
@Override public Factory<CacheLoader<K, V>> getCacheLoaderFactory();
public MutableConfiguration<K, V> setCacheLoaderFactory(Factory<? extends CacheLoader<K, V>> factory);
@Override public Factory<CacheWriter<? super K, ? super V>> getCacheWriterFactory();
public MutableConfiguration<K, V> setCacheWriterFactory(Factory<? extends CacheWriter<? super K, ? super V>> factory);
public Factory<ExpiryPolicy> getExpiryPolicyFactory();
public MutableConfiguration<K, V> setExpiryPolicyFactory(Factory<? extends ExpiryPolicy> factory);
@Override public boolean isReadThrough();
public MutableConfiguration<K, V> setReadThrough(boolean isReadThrough);
@Override public boolean isWriteThrough();
public MutableConfiguration<K, V> setWriteThrough(boolean isWriteThrough);
@Override public boolean isStoreByValue();
public MutableConfiguration<K, V> setStoreByValue(boolean isStoreByValue);
@Override public boolean isStatisticsEnabled();
public MutableConfiguration<K, V> setStatisticsEnabled(boolean enabled);
@Override public boolean isManagementEnabled();
public MutableConfiguration<K, V> setManagementEnabled(boolean enabled);
@Override public int hashCode();
@Override public boolean equals(Object object);
```

## OptionalFeature

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/configuration/OptionalFeature.java)

### OptionalFeature

```java
public static final OptionalFeature STORE_BY_REFERENCE;
```

## CacheEntryCreatedListener

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/CacheEntryCreatedListener.java)

### CacheEntryCreatedListener

```java
void onCreated(Iterable<CacheEntryEvent<? extends K, ? extends V>> events) throws CacheEntryListenerException;
```

## CacheEntryEvent

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/CacheEntryEvent.java)

### CacheEntryEvent

```java
public CacheEntryEvent(Cache<?, ?> source, EventType eventType);
public final EventType getEventType();
public abstract V getOldValue();
@Override public final Cache<?, ?> getSource();
@Override public abstract V getValue();
public abstract boolean isOldValueAvailable();
```

## CacheEntryEventFilter

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/CacheEntryEventFilter.java)

### CacheEntryEventFilter

```java
boolean evaluate(CacheEntryEvent<? extends K, ? extends V> event) throws CacheEntryListenerException;
```

## CacheEntryExpiredListener

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/CacheEntryExpiredListener.java)

### CacheEntryExpiredListener

```java
void onExpired(Iterable<CacheEntryEvent<? extends K, ? extends V>> events) throws CacheEntryListenerException;
```

## CacheEntryListener

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/CacheEntryListener.java)

### CacheEntryListener

```java
```

## CacheEntryListenerException

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/CacheEntryListenerException.java)

### CacheEntryListenerException

```java
public CacheEntryListenerException();
public CacheEntryListenerException(String message);
public CacheEntryListenerException(String message, Throwable cause);
public CacheEntryListenerException(Throwable cause);
```

## CacheEntryRemovedListener

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/CacheEntryRemovedListener.java)

### CacheEntryRemovedListener

```java
void onRemoved(Iterable<CacheEntryEvent<? extends K, ? extends V>> events) throws CacheEntryListenerException;
```

## CacheEntryUpdatedListener

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/CacheEntryUpdatedListener.java)

### CacheEntryUpdatedListener

```java
void onUpdated(Iterable<CacheEntryEvent<? extends K, ? extends V>> events) throws CacheEntryListenerException;
```

## EventType

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/event/EventType.java)

### EventType

```java
public static final EventType CREATED;
public static final EventType UPDATED;
public static final EventType REMOVED;
public static final EventType EXPIRED;
```

## AccessedExpiryPolicy

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/expiry/AccessedExpiryPolicy.java)

### AccessedExpiryPolicy

```java
public static final long serialVersionUID;
public AccessedExpiryPolicy(Duration expiryDuration);
public static Factory<ExpiryPolicy> factoryOf(Duration duration);
@Override public Duration getExpiryForCreation();
@Override public Duration getExpiryForAccess();
@Override public Duration getExpiryForUpdate();
@Override public int hashCode();
@Override public boolean equals(Object object);
```

## CreatedExpiryPolicy

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/expiry/CreatedExpiryPolicy.java)

### CreatedExpiryPolicy

```java
public static final long serialVersionUID;
public CreatedExpiryPolicy(Duration expiryDuration);
public static Factory<ExpiryPolicy> factoryOf(Duration duration);
@Override public Duration getExpiryForCreation();
@Override public Duration getExpiryForAccess();
@Override public Duration getExpiryForUpdate();
@Override public int hashCode();
@Override public boolean equals(Object object);
```

## Duration

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/expiry/Duration.java)

### Duration

```java
public static final long serialVersionUID;
public static final Duration ETERNAL;
public static final Duration ONE_DAY;
public static final Duration ONE_HOUR;
public static final Duration THIRTY_MINUTES;
public static final Duration TWENTY_MINUTES;
public static final Duration TEN_MINUTES;
public static final Duration FIVE_MINUTES;
public static final Duration ONE_MINUTE;
public static final Duration ZERO;
public Duration();
public Duration(TimeUnit timeUnit, long durationAmount);
public Duration(long startTime, long endTime);
public TimeUnit getTimeUnit();
public long getDurationAmount();
public boolean isEternal();
public boolean isZero();
public long getAdjustedTime(long time);
@Override public boolean equals(Object other);
@Override public int hashCode();
```

## EternalExpiryPolicy

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/expiry/EternalExpiryPolicy.java)

### EternalExpiryPolicy

```java
public static final long serialVersionUID;
public static Factory<ExpiryPolicy> factoryOf();
@Override public Duration getExpiryForCreation();
@Override public Duration getExpiryForAccess();
@Override public Duration getExpiryForUpdate();
@Override public int hashCode();
@Override public boolean equals(Object other);
```

## ExpiryPolicy

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/expiry/ExpiryPolicy.java)

### ExpiryPolicy

```java
Duration getExpiryForCreation();
Duration getExpiryForAccess();
Duration getExpiryForUpdate();
```

## ModifiedExpiryPolicy

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/expiry/ModifiedExpiryPolicy.java)

### ModifiedExpiryPolicy

```java
public static final long serialVersionUID;
public ModifiedExpiryPolicy(Duration expiryDuration);
public static Factory<ExpiryPolicy> factoryOf(Duration duration);
@Override public Duration getExpiryForCreation();
@Override public Duration getExpiryForAccess();
@Override public Duration getExpiryForUpdate();
@Override public int hashCode();
@Override public boolean equals(Object object);
```

## TouchedExpiryPolicy

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/expiry/TouchedExpiryPolicy.java)

### TouchedExpiryPolicy

```java
public static final long serialVersionUID;
public TouchedExpiryPolicy(Duration expiryDuration);
public static Factory<ExpiryPolicy> factoryOf(Duration duration);
@Override public Duration getExpiryForCreation();
@Override public Duration getExpiryForAccess();
@Override public Duration getExpiryForUpdate();
@Override public int hashCode();
@Override public boolean equals(Object object);
```

## CacheLoader

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/integration/CacheLoader.java)

### CacheLoader

```java
V load(K key) throws CacheLoaderException;
Map<K, V> loadAll(Iterable<? extends K> keys) throws CacheLoaderException;
```

## CacheLoaderException

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/integration/CacheLoaderException.java)

### CacheLoaderException

```java
public CacheLoaderException();
public CacheLoaderException(String message);
public CacheLoaderException(String message, Throwable cause);
public CacheLoaderException(Throwable cause);
```

## CacheWriter

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/integration/CacheWriter.java)

### CacheWriter

```java
void write(Cache.Entry<? extends K, ? extends V> entry) throws CacheWriterException;
void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> entries) throws CacheWriterException;
void delete(Object key) throws CacheWriterException;
void deleteAll(Collection<?> keys) throws CacheWriterException;
```

## CacheWriterException

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/integration/CacheWriterException.java)

### CacheWriterException

```java
public CacheWriterException();
public CacheWriterException(String message);
public CacheWriterException(String message, Throwable cause);
public CacheWriterException(Throwable cause);
```

## CompletionListener

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/integration/CompletionListener.java)

### CompletionListener

```java
void onCompletion();
void onException(Exception e);
```

## CompletionListenerFuture

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/integration/CompletionListenerFuture.java)

### CompletionListenerFuture

```java
public CompletionListenerFuture();
@Override public void onCompletion() throws IllegalStateException;
@Override public void onException(Exception e) throws IllegalStateException;
@Override public boolean cancel(boolean b);
@Override public boolean isCancelled();
@Override public boolean isDone();
@Override public Void get() throws InterruptedException, ExecutionException;
@Override public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
```

## CacheMXBean

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/management/CacheMXBean.java)

### CacheMXBean

```java
String getKeyType();
String getValueType();
boolean isReadThrough();
boolean isWriteThrough();
boolean isStoreByValue();
boolean isStatisticsEnabled();
boolean isManagementEnabled();
```

## CacheStatisticsMXBean

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/management/CacheStatisticsMXBean.java)

### CacheStatisticsMXBean

```java
void clear();
long getCacheHits();
float getCacheHitPercentage();
long getCacheMisses();
float getCacheMissPercentage();
long getCacheGets();
long getCachePuts();
long getCacheRemovals();
long getCacheEvictions();
float getAverageGetTime();
float getAveragePutTime();
float getAverageRemoveTime();
```

## EntryProcessor

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/processor/EntryProcessor.java)

### EntryProcessor

```java
T process(MutableEntry<K, V> entry, Object... arguments) throws EntryProcessorException;
```

## EntryProcessorException

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/processor/EntryProcessorException.java)

### EntryProcessorException

```java
public EntryProcessorException();
public EntryProcessorException(String message);
public EntryProcessorException(String message, Throwable cause);
public EntryProcessorException(Throwable cause);
```

## EntryProcessorResult

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/processor/EntryProcessorResult.java)

### EntryProcessorResult

```java
T get() throws EntryProcessorException;
```

## MutableEntry

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/processor/MutableEntry.java)

### MutableEntry

```java
boolean exists();
void remove();
V getValue();
void setValue(V value);
```

## CachingProvider

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/cache-api/src/main/java/javax/cache/spi/CachingProvider.java)

### CachingProvider

```java
CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties);
ClassLoader getDefaultClassLoader();
URI getDefaultURI();
Properties getDefaultProperties();
CacheManager getCacheManager(URI uri, ClassLoader classLoader);
CacheManager getCacheManager();
void close();
void close(ClassLoader classLoader);
void close(URI uri, ClassLoader classLoader);
boolean isSupported(OptionalFeature optionalFeature);
```
