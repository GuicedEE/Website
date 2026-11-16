# com.guicedee.modules.services:telemetry-core

telemetry-core

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.modules.services.opentelemetry`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/MicroProfile/telemetry-core/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `tools.jackson.core:jackson-core`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `org.slf4j:slf4j-api`; scope `compile`.
- `com.google.guava:guava`; scope `compile`.
- `io.opentelemetry:opentelemetry-api`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-api-incubator`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-context`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-sdk`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-sdk-trace`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-sdk-common`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-sdk-metrics`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-sdk-logs`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-sdk-extension-autoconfigure-spi`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-sdk-extension-autoconfigure`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-sdk-testing`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-exporter-otlp`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-exporter-sender-jdk`; scope `compile`; optional.
- `io.opentelemetry:opentelemetry-exporter-common`; scope `compile`; optional.
- `io.opentelemetry.instrumentation:opentelemetry-log4j-appender-2.17`; scope `compile`; optional.
- `org.apache.logging.log4j:log4j-core`; scope `compile`; optional.
- `io.vertx:vertx-opentelemetry`; scope `compile`; optional.

## JPMS descriptor

```java
module com.guicedee.modules.services.opentelemetry {
    requires org.slf4j;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires java.logging;

    requires io.vertx.core;
    requires com.google.common;

    requires java.net.http;
    requires tools.jackson.core;
    // InstrumentationConfigUtil exposes the Jackson 3 ObjectMapper in its shaded public API.
    requires transitive tools.jackson.databind;


    uses io.opentelemetry.context.ContextStorageProvider;
    uses io.opentelemetry.sdk.common.export.HttpSenderProvider;

    uses io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;
    uses io.opentelemetry.sdk.autoconfigure.spi.ConfigurablePropagatorProvider;
    uses io.opentelemetry.sdk.autoconfigure.spi.ResourceProvider;
    uses io.opentelemetry.sdk.autoconfigure.spi.logs.ConfigurableLogRecordExporterProvider;
    uses io.opentelemetry.sdk.autoconfigure.spi.metrics.ConfigurableMetricExporterProvider;
    uses io.opentelemetry.sdk.autoconfigure.spi.traces.ConfigurableSpanExporterProvider;
    uses io.opentelemetry.sdk.autoconfigure.spi.internal.ComponentProvider;

    provides io.opentelemetry.context.ContextStorageProvider with
            io.vertx.tracing.opentelemetry.VertxContextStorageProvider;
    provides io.vertx.core.spi.VertxServiceProvider with
            io.vertx.tracing.opentelemetry.OpenTelemetryTracingFactory;

    provides io.opentelemetry.api.trace.TracerProvider with io.opentelemetry.sdk.trace.SdkTracerProvider;
    provides io.opentelemetry.api.metrics.MeterProvider with io.opentelemetry.sdk.metrics.SdkMeterProvider;
    provides io.opentelemetry.api.logs.LoggerProvider with io.opentelemetry.sdk.logs.SdkLoggerProvider;

    provides io.opentelemetry.sdk.common.export.HttpSenderProvider with
            io.opentelemetry.exporter.sender.jdk.internal.JdkHttpSenderProvider;

    exports io.opentelemetry.api;
    exports io.opentelemetry.api.baggage;
    exports io.opentelemetry.api.baggage.propagation;
    exports io.opentelemetry.api.common;
    exports io.opentelemetry.api.incubator.config;
    exports io.opentelemetry.api.incubator.logs;
    exports io.opentelemetry.api.incubator.metrics;
    exports io.opentelemetry.api.incubator.propagation;
    exports io.opentelemetry.api.incubator.trace;
    exports io.opentelemetry.api.internal;
    exports io.opentelemetry.api.logs;
    exports io.opentelemetry.api.metrics;
    exports io.opentelemetry.api.trace;
    exports io.opentelemetry.api.trace.propagation;
    exports io.opentelemetry.context;
    exports io.opentelemetry.context.propagation;
    exports io.opentelemetry.exporter.internal;
    // io.opentelemetry.exporter.internal.compression and .http no longer exist in the
    // shaded OpenTelemetry version - exporting absent packages breaks module resolution
    // (InvalidModuleDescriptorException) for every consumer.
    exports io.opentelemetry.exporter.internal.marshal;
    exports io.opentelemetry.exporter.internal.metrics;
    exports io.opentelemetry.exporter.internal.otlp;
    exports io.opentelemetry.exporter.internal.otlp.logs;
    exports io.opentelemetry.exporter.internal.otlp.metrics;
    exports io.opentelemetry.exporter.internal.otlp.traces;
    exports io.opentelemetry.exporter.otlp.trace;
    exports io.opentelemetry.exporter.sender.jdk.internal;
    //exports io.opentelemetry.exporter.sender.okhttp.internal;

    exports io.opentelemetry.instrumentation.api;
    exports io.opentelemetry.instrumentation.api.instrumenter;
    exports io.opentelemetry.instrumentation.api.semconv;
    exports io.opentelemetry.instrumentation.api.semconv.http;
    exports io.opentelemetry.instrumentation.api.semconv.http.internal;
    exports io.opentelemetry.instrumentation.api.semconv.network;
    exports io.opentelemetry.instrumentation.api.semconv.network.internal;
    exports io.opentelemetry.instrumentation.api.semconv.url;
    exports io.opentelemetry.instrumentation.api.semconv.url.internal;
    exports io.opentelemetry.instrumentation.api.semconv.util;
    exports io.opentelemetry.instrumentation.api.util;
    exports io.opentelemetry.instrumentation.log4j.appender.v2_17;
    exports io.opentelemetry.instrumentation.log4j.appender.v2_17.internal;
    exports io.opentelemetry.instrumentation.log4j.contextdata.v2_17;
    exports io.opentelemetry.instrumentation.log4j.contextdata.v2_17.internal;

    exports io.opentelemetry.sdk;
    exports io.opentelemetry.sdk.autoconfigure;
    exports io.opentelemetry.sdk.autoconfigure.internal;
    exports io.opentelemetry.sdk.autoconfigure.spi;
    exports io.opentelemetry.sdk.autoconfigure.spi.internal;
    exports io.opentelemetry.sdk.autoconfigure.spi.logs;
    exports io.opentelemetry.sdk.autoconfigure.spi.metrics;
    exports io.opentelemetry.sdk.autoconfigure.spi.traces;
    exports io.opentelemetry.sdk.common;
    exports io.opentelemetry.sdk.common.export;
    exports io.opentelemetry.sdk.common.internal;
    exports io.opentelemetry.sdk.internal;
    exports io.opentelemetry.sdk.logs;
    exports io.opentelemetry.sdk.logs.data;
    // io.opentelemetry.sdk.logs.data.internal is no longer present in the shaded jar.
    exports io.opentelemetry.sdk.logs.export;
    exports io.opentelemetry.sdk.logs.internal;
    exports io.opentelemetry.sdk.metrics;
    exports io.opentelemetry.sdk.metrics.data;
    exports io.opentelemetry.sdk.metrics.export;
    exports io.opentelemetry.sdk.metrics.internal;
    exports io.opentelemetry.sdk.metrics.internal.aggregator;
    exports io.opentelemetry.sdk.metrics.internal.concurrent;
    exports io.opentelemetry.sdk.metrics.internal.data;
    exports io.opentelemetry.sdk.metrics.internal.debug;
    exports io.opentelemetry.sdk.metrics.internal.descriptor;
    exports io.opentelemetry.sdk.metrics.internal.exemplar;
    exports io.opentelemetry.sdk.metrics.internal.export;
    exports io.opentelemetry.sdk.metrics.internal.state;
    exports io.opentelemetry.sdk.metrics.internal.view;
    exports io.opentelemetry.sdk.resources;
    exports io.opentelemetry.sdk.testing.exporter;
    exports io.opentelemetry.sdk.testing.logs;
    // io.opentelemetry.sdk.testing.logs.internal is no longer present in the shaded jar.
    exports io.opentelemetry.sdk.testing.metrics;
    exports io.opentelemetry.sdk.testing.time;
    exports io.opentelemetry.sdk.testing.trace;
    exports io.opentelemetry.sdk.trace;
    exports io.opentelemetry.sdk.trace.data;
    exports io.opentelemetry.sdk.trace.export;
    exports io.opentelemetry.sdk.trace.internal;
    exports io.opentelemetry.sdk.trace.samplers;

    exports io.vertx.tracing.opentelemetry;

    opens io.opentelemetry.api to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.baggage to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.common to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.incubator to tools.jackson.databind, com.guicedee.guicedinjection;
    // io.opentelemetry.api.incubator.common is not present in the shaded jar. `opens` is
    // validated by the module system exactly like `exports`, so opening an absent package
    // breaks resolution just the same.
    opens io.opentelemetry.api.incubator.config to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.incubator.internal to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.incubator.logs to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.incubator.metrics to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.incubator.propagation to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.incubator.trace to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.logs to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.metrics to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.api.trace to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.context to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.instrumentation.log4j.appender.v2_17 to tools.jackson.databind, com.guicedee.guicedinjection,org.apache.logging.log4j.core;
    opens io.opentelemetry.instrumentation.log4j.appender.v2_17.internal to tools.jackson.databind, com.guicedee.guicedinjection,org.apache.logging.log4j.core;

    opens io.opentelemetry.exporter.otlp.trace to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.exporter.otlp.logs to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.exporter.otlp.metrics to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.exporter.otlp.http.trace to tools.jackson.databind, com.guicedee.guicedinjection;
    exports io.opentelemetry.exporter.otlp.http.trace;
    exports io.opentelemetry.exporter.otlp.http.logs;
    opens io.opentelemetry.exporter.otlp.http.logs to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.exporter.otlp.http.metrics to tools.jackson.databind, com.guicedee.guicedinjection;

    opens io.opentelemetry.sdk.autoconfigure to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk.logs to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk.logs.export to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk.metrics to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk.resources to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk.trace to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk.trace.export to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk.trace.data to tools.jackson.databind, com.guicedee.guicedinjection;
    opens io.opentelemetry.sdk.logs.data to tools.jackson.databind, com.guicedee.guicedinjection;




}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/MicroProfile/telemetry-core/README.md). Historical version examples retain their original context.

# Telemetry Core

The shaded OpenTelemetry module uses Jackson 3 (`tools.jackson.core` and
`tools.jackson.databind`). The incubator configuration API's `ObjectMapper`
reference is relocated to Jackson 3; its `convertValue(Object, Class)` call is
verified against the managed Jackson version. Jackson 2 core and databind are
not required. Jackson annotations retain their upstream `com.fasterxml.jackson`
namespace.

OpenTelemetry 1.64.0 writes OTLP JSON with its own buffered encoder. Older
telemetry artifacts whose `JsonSerializer` references Jackson 2 must be replaced
with the rebuilt module; changing only the application's module descriptor does
not update that embedded bytecode.

Run `mvn verify` from this directory. The integration tests load the packaged
shade as a named JPMS module without Jackson 2 core/databind, check its bytecode,
exercise configuration conversion with Jackson 3, and serialize a log record on
the batch processor's worker thread.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## TelemetryServiceModule

[Java source](https://github.com/GuicedEE/Services/blob/master/MicroProfile/telemetry-core/src/main/java/com/guicedee/services/telemetry/TelemetryServiceModule.java)

### TelemetryServiceModule

```java
```
