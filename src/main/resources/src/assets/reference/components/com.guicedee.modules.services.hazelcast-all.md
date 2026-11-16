# com.guicedee.modules.services:hazelcast-all

JPMS Hazelcast with shared Jackson 3 and no bundled Jackson 2 runtime

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.hazelcast.all`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JCache/hazelcast/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:cache-api`; scope `compile`.
- `jakarta.activation:jakarta.activation-api`; scope `compile`.
- `jakarta.annotation:jakarta.annotation-api`; scope `compile`.
- `com.guicedee.modules.services:hibernate-core`; scope `compile`; optional.
- `com.hazelcast:hazelcast`; scope `compile`; optional.
- `org.apache.logging.log4j:log4j-api`; scope `provided`; optional.
- `org.checkerframework:checker-qual`; scope `provided`; optional.
- `org.jline:jline-terminal`; scope `compile`; optional.
- `org.jline:jline-reader`; scope `compile`; optional.
- `io.dropwizard.metrics:metrics-core`; scope `compile`; optional.
- `com.github.spotbugs:spotbugs-annotations`; scope `provided`; optional.
- `org.slf4j:slf4j-api`; scope `provided`; optional.
- `org.codehaus.groovy:groovy-all`; scope `provided`; optional.
- `jakarta.jms:jakarta.jms-api`; scope `provided`; optional.
- `com.fasterxml.jackson.core:jackson-annotations`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.
- `ch.randelshofer:fastdoubleparser`; scope `compile`; optional.
- `net.openhft:affinity`; scope `compile`; optional.
- `org.osgi:org.osgi.core`; scope `provided`; optional.
- `tools.jackson.jr:jackson-jr-objects`; scope `compile`; optional.
- `tools.jackson.jr:jackson-jr-annotation-support`; scope `compile`; optional.
- `org.junit.jupiter:junit-jupiter`; scope `test`.

## JPMS descriptor

```java
open module com.hazelcast.all {
    exports com.hazelcast.cache;
    requires transitive java.logging;
    requires tools.jackson.core;
    requires tools.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires transitive cache.api;

    requires java.transaction.xa;
    requires java.sql;
    requires java.management;

    requires static  jdk.unsupported;

    requires static org.apache.logging.log4j.core;
    requires static org.sl4fj;

    requires transitive java.xml;
    requires static org.hibernate.orm.core;
    requires static com.sun.jna;

    uses com.hazelcast.client.impl.ClientExtension;


    uses com.hazelcast.client.impl.protocol.MessageTaskFactoryProvider;
    provides com.hazelcast.client.impl.protocol.MessageTaskFactoryProvider with com.hazelcast.jet.impl.client.protocol.task.JetMessageTaskFactoryProvider;

    uses com.hazelcast.dataconnection.DataConnectionRegistration;
    provides com.hazelcast.dataconnection.DataConnectionRegistration with com.hazelcast.dataconnection.impl.JdbcDataConnectionRegistration,
                                            com.hazelcast.dataconnection.impl.HazelcastDataConnectionRegistration;

    provides com.hazelcast.client.impl.ClientExtension with com.hazelcast.client.impl.clientside.DefaultClientExtension;


    uses com.hazelcast.internal.serialization.DataSerializerHook;
    provides  com.hazelcast.internal.serialization.DataSerializerHook  with com.hazelcast.internal.cluster.impl.ClusterDataSerializerHook,
              com.hazelcast.spi.impl.SpiDataSerializerHook,
                com.hazelcast.internal.partition.impl.PartitionDataSerializerHook,
                com.hazelcast.map.impl.MapDataSerializerHook,
                com.hazelcast.collection.impl.queue.QueueDataSerializerHook,
                com.hazelcast.multimap.impl.MultiMapDataSerializerHook,
                com.hazelcast.collection.impl.collection.CollectionDataSerializerHook,
                com.hazelcast.topic.impl.TopicDataSerializerHook,
                com.hazelcast.executor.impl.ExecutorDataSerializerHook,
                com.hazelcast.durableexecutor.impl.DurableExecutorDataSerializerHook,
                com.hazelcast.internal.locksupport.LockDataSerializerHook,
                com.hazelcast.internal.longregister.LongRegisterDataSerializerHook,
                com.hazelcast.transaction.impl.TransactionDataSerializerHook,
                com.hazelcast.replicatedmap.impl.operation.ReplicatedMapDataSerializerHook,
                com.hazelcast.cache.impl.CacheDataSerializerHook,
                com.hazelcast.ringbuffer.impl.RingbufferDataSerializerHook,
                com.hazelcast.wan.impl.WanDataSerializerHook,
                com.hazelcast.query.impl.predicates.PredicateDataSerializerHook,
                com.hazelcast.cardinality.impl.CardinalityEstimatorDataSerializerHook,
                com.hazelcast.client.impl.ClientDataSerializerHook,
                com.hazelcast.internal.management.ManagementDataSerializerHook,
                com.hazelcast.internal.ascii.TextProtocolsDataSerializerHook,
                com.hazelcast.scheduledexecutor.impl.ScheduledExecutorDataSerializerHook,
                com.hazelcast.internal.usercodedeployment.impl.UserCodeDeploymentSerializerHook,
                com.hazelcast.aggregation.impl.AggregatorDataSerializerHook,
                com.hazelcast.projection.impl.ProjectionDataSerializerHook,
                com.hazelcast.internal.config.ConfigDataSerializerHook,
                com.hazelcast.internal.journal.EventJournalDataSerializerHook,
                com.hazelcast.flakeidgen.impl.FlakeIdGeneratorDataSerializerHook,
                com.hazelcast.spi.impl.merge.SplitBrainDataSerializerHook,
                com.hazelcast.internal.crdt.CRDTDataSerializerHook,
            //  com.hazelcast.cp.event.impl.CpEventDataSerializerHook,
                com.hazelcast.cp.internal.raft.impl.RaftDataSerializerHook,
                com.hazelcast.cp.internal.RaftServiceDataSerializerHook,
                com.hazelcast.cp.internal.session.RaftSessionServiceDataSerializerHook,
                com.hazelcast.cp.internal.datastructures.atomiclong.AtomicLongDataSerializerHook,
                com.hazelcast.cp.internal.datastructures.atomicref.AtomicRefDataSerializerHook,
                com.hazelcast.cp.internal.datastructures.lock.LockDataSerializerHook,
                com.hazelcast.cp.internal.datastructures.semaphore.SemaphoreDataSerializerHook,
                com.hazelcast.cp.internal.datastructures.RaftDataServiceDataSerializerHook,
                com.hazelcast.cp.internal.datastructures.countdownlatch.CountDownLatchDataSerializerHook,
                com.hazelcast.internal.metrics.managementcenter.MetricsDataSerializerHook,
                com.hazelcast.sql.impl.SqlDataSerializerHook,
                com.hazelcast.json.internal.JsonDataSerializerHook,
                com.hazelcast.internal.util.collection.UtilCollectionSerializerHook,
                com.hazelcast.jet.core.JetDataSerializerHook,
                com.hazelcast.jet.core.metrics.MetricsDataSerializerHook,
                com.hazelcast.jet.impl.execution.init.JetInitDataSerializerHook,
                com.hazelcast.jet.impl.metrics.JetMetricsDataSerializerHook,
                com.hazelcast.jet.impl.observer.JetObserverDataSerializerHook,
                com.hazelcast.jet.impl.aggregate.AggregateDataSerializerHook,
                com.hazelcast.jet.config.JetConfigDataSerializerHook,
                com.hazelcast.internal.serialization.impl.compact.schema.SchemaDataSerializerHook,
                com.hazelcast.jet.impl.util.FunctionsSerializerHook
    ;


    uses com.hazelcast.instance.impl.NodeExtension;
    provides com.hazelcast.instance.impl.NodeExtension with com.hazelcast.instance.impl.DefaultNodeExtension;

    uses com.hazelcast.internal.serialization.PortableHook;

    uses com.hazelcast.internal.serialization.SerializerHook;
    provides com.hazelcast.internal.serialization.SerializerHook with com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.LongAccHook,
                com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.DoubleAccHook,
                com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.MutableReferenceHook,
                com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.LinTrendAccHook,
                com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.LongLongAccHook,
                com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.LongDoubleAccHook,
                com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.PickAnyAccHook,
                com.hazelcast.jet.core.CoreSerializerHooks.WatermarkHook,
                com.hazelcast.jet.core.CoreSerializerHooks.JetEventHook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.WindowResultHook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.KeyedWindowResultHook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.Tuple2Hook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.Tuple3Hook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.Tuple4Hook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.Tuple5Hook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.TagHook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.ItemsByTagHook,
                com.hazelcast.jet.datamodel.DataModelSerializerHooks.TimestampedItemHook,
                com.hazelcast.jet.impl.execution.ExecutionSerializerHooks.SnapshotBarrierHook,
                com.hazelcast.jet.impl.execution.ExecutionSerializerHooks.BroadcastEntryHook,
                com.hazelcast.jet.impl.execution.ExecutionSerializerHooks.BroadcastKeyHook,
                com.hazelcast.jet.impl.execution.ExecutionSerializerHooks.DoneItemHook,
                com.hazelcast.jet.impl.execution.init.CustomClassLoadedObject.Hook,
                com.hazelcast.jet.impl.metrics.JetMetricsDataSerializerHook,
                com.hazelcast.jet.impl.observer.JetObserverDataSerializerHook,
                com.hazelcast.jet.json.JsonSerializerHooks.DeferredMapHook
                    ;





    uses com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
    provides com.hazelcast.spi.discovery.DiscoveryStrategyFactory with com.hazelcast.spi.discovery.multicast.MulticastDiscoveryStrategyFactory,
            com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory,
                com.hazelcast.gcp.GcpDiscoveryStrategyFactory,
                com.hazelcast.aws.AwsDiscoveryStrategyFactory,
                com.hazelcast.azure.AzureDiscoveryStrategyFactory
    ;


    uses com.hazelcast.spi.impl.servicemanager.ServiceDescriptorProvider;
    provides com.hazelcast.spi.impl.servicemanager.ServiceDescriptorProvider with com.hazelcast.cp.internal.RaftServiceDescriptorProvider,
            com.hazelcast.cp.internal.datastructures.RaftDataServiceDescriptorProvider,
            com.hazelcast.internal.longregister.LongRegisterServiceDescriptorProvider;

    provides javax.cache.spi.CachingProvider with com.hazelcast.cache.HazelcastCachingProvider;

    //opens com.hazelcast.nio to com.hazelcast.hibernate;

    exports com.hazelcast.nio;
    exports com.hazelcast.client.config;
    exports com.hazelcast.client.cache.impl;

    exports com.hazelcast.client;
    exports com.hazelcast.collection;

    exports com.hazelcast.config;
    exports com.hazelcast.config.matcher;
    exports com.hazelcast.config.properties;
    exports com.hazelcast.console;
    exports com.hazelcast.core;
    exports com.hazelcast.topic;
    exports com.hazelcast.core.server;
    exports com.hazelcast.cluster;
    exports com.hazelcast.map;

    exports com.hazelcast.internal.serialization;
    exports com.hazelcast.internal.serialization.impl;
    exports com.hazelcast.nio.serialization;
    exports com.hazelcast.logging to com.hazelcast.hibernate;
    exports com.hazelcast.internal.util to com.hazelcast.hibernate;

    exports com.hazelcast.jet.config;

    exports com.hazelcast.instance;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/JCache/hazelcast/README.md). Historical version examples retain their original context.

# Hazelcast and Jackson 3

`hazelcast-all` keeps Hazelcast 5.7's public API and JPMS module name, `com.hazelcast.all`.
Its JSON paths use the application's BOM-managed Jackson 3 core/databind (currently 3.2.3).
The lightweight Jackson Jr objects and annotation-support helpers are shaded privately at
that same managed version. Jackson's shared `com.fasterxml.jackson.annotation` artifact is
still required by Jackson 3; it is not a Jackson 2 core/databind runtime.

The upstream Hazelcast JAR embeds Jackson 2.21.2 and Jackson 3.1.2. The wrapper removes
those implementations, their service descriptors, and their Maven version metadata.
Hazelcast's Jackson 3 references are relocated to the shared `tools.jackson` modules.
The inspected upstream Hazelcast bytecode has no Jackson 2 callers outside Jackson itself.
Do not replace this with metadata-only exclusions or a blanket Jackson 2-to-3 relocation:
the APIs are not generally binary compatible.

`mvn -B -ntp verify -Dmaven.javadoc.skip=true -Dgpg.skip=true` in this directory exercises
the packaged named module, with no upstream Hazelcast JAR and no Jackson 2 runtime on its
module path. The integration tests check classes, bytecode, embedded version metadata,
Jet JSON round-trips, and JSON-value replication between two cluster members.
Run the GuicedEE Hazelcast consumer tests after installing the rebuilt wrapper locally.

## Cloud logging

GuicedEE Client and Inject use Log4j JSON Template Layout with its `JsonLayout.json`
compatibility template. This removes the legacy JsonLayout dependency on Jackson 2.
Output is compact newline-delimited JSON; exceptions follow the template's flat stack-trace
representation. The website backend and its jlink module no longer request Jackson 2 databind.
The template layout is a named JPMS module, included through the client's module descriptor.

## Remaining migration inventory (2026-10-16)

These changes remove the two embedded Jackson copies responsible for the reported Hazelcast
scan findings. A fresh platform build and image scan are still required to confirm closure.
Existing published 2.4.0 artifacts and deployed images are not changed by a local rebuild;
applications must adopt the coordinated 2.4.1 release and rebuild their images.

A full DevSuite reactor runtime dependency audit also found Jackson 2 paths outside Hazelcast:

- Azure Identity / Azure Core, including the optional ActivityMaster Core dependency.
- Cassandra drivers exposed by the Vert.x Cassandra wrapper and optional Mutiny/persistence paths.
- The optional CXF REST OpenAPI Swagger integration.
- The optional CloudEvents Jackson adapter.
- Testcontainers, whose private shaded Jackson 2 implementation is test tooling; its Cassandra
  adapter also resolves Jackson 2. Application usages inspected declare Testcontainers as test scope.

Those adapters require API-compatible migrations and their own runtime tests. They have not
been made Jackson 3 compatible by exclusions. Therefore this change does not certify every
optional DevSuite service or the test tooling as Jackson 2 free. See the reproducible reactor
inventory in `logs/jackson2-runtime-dependencies.log` in the DevSuite workspace.
