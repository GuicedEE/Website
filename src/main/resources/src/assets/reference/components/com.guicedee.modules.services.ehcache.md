# com.guicedee.modules.services:ehcache

Kubernetes Service Discovery for Hazelcast Discovery SPI

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.ehcache`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JCache/ehcache/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:cache-api`; scope `compile`.
- `org.ehcache:ehcache`; scope `compile`; optional.

## JPMS descriptor

```java
module com.ehcache {

  requires transitive cache.api;
  requires transitive org.slf4j;
  requires java.management;
  requires java.xml;
  requires jakarta.xml.bind;
  requires jdk.unsupported;

  exports org.ehcache.jsr107;
  exports org.ehcache.xml;
  exports org.ehcache.jsr107.config;

  provides javax.cache.spi.CachingProvider with org.ehcache.jsr107.EhcacheCachingProvider;
  provides org.ehcache.core.spi.service.ServiceFactory with org.ehcache.impl.internal.store.copy.CopierStoreProviderFactory,
                                                           org.ehcache.impl.internal.store.shared.SharedStorageProviderFactory,
                                                           org.ehcache.impl.internal.store.shared.store.SharedStoreProviderFactory,
                                                           org.ehcache.impl.internal.store.shared.authoritative.SharedAuthoritativeTierProviderFactory,
                                                           org.ehcache.impl.internal.store.shared.caching.SharedCachingTierProviderFactory,
                                                           org.ehcache.impl.internal.store.shared.caching.lower.SharedLowerCachingTierProviderFactory,
                                                           org.ehcache.impl.internal.store.shared.caching.higher.SharedHigherCachingTierProviderFactory,
                                                           org.ehcache.impl.internal.store.heap.OnHeapStoreProviderFactory,
                                                           org.ehcache.impl.internal.store.offheap.OffHeapStoreProviderFactory,
                                                           org.ehcache.impl.internal.store.disk.OffHeapDiskStoreProviderFactory,
                                                           org.ehcache.impl.internal.store.tiering.TieredStoreProviderFactory,
                                                           org.ehcache.impl.internal.store.tiering.CompoundCachingTierProviderFactory,
                                                           org.ehcache.impl.internal.store.loaderwriter.LoaderWriterStoreProviderFactory,
                                                           org.ehcache.impl.internal.TimeSourceServiceFactory,
                                                           org.ehcache.impl.internal.spi.serialization.DefaultSerializationProviderFactory,
                                                           org.ehcache.impl.internal.spi.loaderwriter.DefaultCacheLoaderWriterProviderFactory,
                                                           org.ehcache.impl.internal.spi.event.DefaultCacheEventListenerProviderFactory,
                                                           org.ehcache.impl.internal.executor.DefaultExecutionServiceFactory,
                                                           org.ehcache.impl.internal.persistence.DefaultLocalPersistenceServiceFactory,
                                                           org.ehcache.impl.internal.persistence.DefaultDiskResourceServiceFactory,
                                                           org.ehcache.impl.internal.loaderwriter.writebehind.WriteBehindProviderFactory,
                                                           org.ehcache.impl.internal.events.CacheEventNotificationListenerServiceProviderFactory,
                                                           org.ehcache.impl.internal.spi.copy.DefaultCopyProviderFactory,
                                                           org.ehcache.impl.internal.sizeof.DefaultSizeOfEngineProviderFactory,
                                                           org.ehcache.impl.internal.spi.resilience.DefaultResilienceStrategyProviderFactory,
                                                           org.ehcache.core.internal.statistics.DefaultStatisticsServiceFactory;

  provides org.ehcache.xml.CacheManagerServiceConfigurationParser with org.ehcache.jsr107.internal.Jsr107ServiceConfigurationParser;
  provides org.ehcache.xml.CacheServiceConfigurationParser with org.ehcache.jsr107.internal.Jsr107CacheConfigurationParser;

  opens org.ehcache.xml.model to jakarta.xml.bind;

  uses org.ehcache.core.spi.service.ServiceFactory;
  uses org.ehcache.xml.CacheManagerServiceConfigurationParser;
  uses org.ehcache.xml.CacheServiceConfigurationParser;
  uses org.ehcache.xml.CacheResourceConfigurationParser;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## EhcacheServiceModule

[Java source](https://github.com/GuicedEE/Services/blob/master/JCache/ehcache/src/main/java/com/guicedee/services/ehcache/EhcacheServiceModule.java)

### EhcacheServiceModule

```java
```
