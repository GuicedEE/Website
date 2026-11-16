# com.guicedee:metrics

MicroProfile Metrics for GuicedEE

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.metrics`.
- [Source POM](https://github.com/GuicedEE/Metrics/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `io.vertx:vertx-dropwizard-metrics`; scope `compile`.
- `com.guicedee:vertx`; scope `compile`.
- `com.guicedee:web`; scope `compile`.
- `com.guicedee:client`; scope `compile`.
- `com.guicedee.modules.services:metrics-core`; scope `compile`.
- `io.vertx:vertx-core`; scope `compile`.
- `com.guicedee:inject`; scope `compile`.
- `com.guicedee.modules.services:guice`; scope `compile`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `org.junit.jupiter:junit-jupiter-api`; scope `test`.
- `org.mockito:mockito-core`; scope `test`.
- `org.mockito:mockito-junit-jupiter`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.metrics.Metrics;

module com.guicedee.metrics {
    requires static lombok;

    requires transitive com.guicedee.client;
    requires transitive com.guicedee.guicedinjection;
    requires transitive com.guicedee.vertx;
    requires transitive io.vertx.metrics.dropwizard;
    // Graphite is in the same module as metrics-core in this project's shaded version
    requires transitive com.codahale.metrics;
    requires static com.guicedee.vertx.web;

    requires io.vertx.core;
    requires io.vertx.core.logging;

    exports com.guicedee.metrics;
    exports com.guicedee.metrics.implementations;
    exports com.guicedee.metrics.enumerations;
    exports com.guicedee.metrics.implementations.mp;

    opens com.guicedee.metrics to com.google.guice, tools.jackson.databind, com.guicedee.guicedinjection, com.guicedee.client, guiced.metrics.test, com.google.common;
    opens com.guicedee.metrics.implementations to com.google.guice, tools.jackson.databind, com.guicedee.guicedinjection, com.guicedee.client, guiced.metrics.test, com.google.common;
    opens com.guicedee.metrics.implementations.mp to com.google.guice, tools.jackson.databind, com.guicedee.guicedinjection, com.guicedee.client, guiced.metrics.test, com.google.common;

    provides com.guicedee.client.services.lifecycle.IGuiceModule with com.guicedee.metrics.implementations.MetricsModule;
    provides com.guicedee.client.services.lifecycle.IGuicePreStartup with com.guicedee.metrics.implementations.MetricsPreStartup;
    provides com.guicedee.vertx.spi.VertxConfigurator with com.guicedee.metrics.implementations.MetricsVertxConfigurator;
    provides com.guicedee.vertx.web.spi.VertxRouterConfigurator with com.guicedee.metrics.implementations.PrometheusMetricsConfigurator;

    provides com.guicedee.vertx.web.spi.ManagedHttpListenerProvider with com.guicedee.metrics.implementations.PrometheusMetricsConfigurator;

    uses Metrics;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Metrics/blob/master/README.md). Historical version examples retain their original context.

# GuicedEE Metrics

[![Build](https://github.com/GuicedEE/Metrics/actions/workflows/build.yml/badge.svg)](https://github.com/GuicedEE/Metrics/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.guicedee/metrics)](https://central.sonatype.com/artifact/com.guicedee/metrics)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Guice 7](https://img.shields.io/badge/Guice-7%2B-green)
![Vert.X 5](https://img.shields.io/badge/Vert.x-5%2B-green)

Production-ready **application metrics** for [GuicedEE](https://github.com/GuicedEE) using **Vert.x 5 Dropwizard Metrics** and the **MicroProfile Metrics 5.1 API**.
Annotate your methods with standard `@Counted`, `@Timed`, and custom `@MetricMethod` — interceptors are bound through Guice AOP, metrics are collected into a shared `MetricRegistry`, and a Prometheus-compatible scrape endpoint is exposed on the Vert.x Web `RouterConfig` automatically.

Built on [Vert.x Dropwizard Metrics](https://vertx.io/docs/vertx-dropwizard-metrics/java/) · [MicroProfile Metrics](https://github.com/eclipse/microprofile-metrics) · [Dropwizard Metrics](https://metrics.dropwizard.io/) · [Google Guice](https://github.com/google/guice) · JPMS module `com.guicedee.metrics` · Java 25+

## 📦 Installation

```xml
<dependency>
  <groupId>com.guicedee</groupId>
  <artifactId>metrics</artifactId>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.guicedee:metrics:2.4.1")
```
</details>

## ✨ Features

- **MicroProfile Metrics annotations** — `@Counted` and `@Timed` are intercepted via Guice AOP and bridged to Dropwizard Metrics counters/timers
- **Custom `@MetricMethod`** — annotate any method to auto-increment a named counter; if the method returns a numeric type the counter value is returned
- **Vert.x Dropwizard integration** — `MetricsVertxConfigurator` configures `DropwizardMetricsOptions` on the `VertxBuilder` before Vert.x starts, enabling built-in event bus, HTTP server/client, pool, and datagram metrics
- **Annotation-driven configuration** — `@MetricsOptions` on any class or package configures the registry name, JMX, base name, monitored URIs, Graphite, and Prometheus
- **Environment variable overrides** — every `@MetricsOptions` attribute can be overridden via system properties or environment variables
- **Prometheus scrape endpoint** — `PrometheusMetricsConfigurator` registers a `GET /metrics` handler that renders all Dropwizard metrics in Prometheus exposition format
- **Graphite reporting** — built-in `GraphiteReporter` sends metrics to a Graphite server at a configurable interval
- **JMX exposure** — metrics are optionally published as JMX MBeans
- **Extensible interceptors** — implement the `Metrics` SPI to register custom annotation → interceptor bindings discovered via `ServiceLoader`
- **Type-safe metric enumerations** — `VertxMetrics`, `HttpServerMetrics`, `HttpClientMetrics`, `EventBusMetrics`, `PoolMetrics`, `NetServerMetrics`, `DatagramMetrics` for programmatic access
- **MicroProfile MetricRegistry bridge** — `MPMetricRegistry` wraps Dropwizard's `MetricRegistry` so the standard `org.eclipse.microprofile.metrics.MetricRegistry` is injectable

## 🚀 Quick Start

**Step 1** — Configure metrics in the application's `.env`:

```dotenv
METRICS_ENABLED=true
METRICS_JMX_ENABLED=false
METRICS_PROMETHEUS_ENABLED=true
METRICS_PROMETHEUS_ENDPOINT=/metrics
```

No configuration annotation is required. `@MetricsOptions` remains available for annotation defaults and monitored URI/event-bus patterns:

```java
@MetricsOptions(
    enabled = true,
    jmxEnabled = true,
    baseName = "my-app",
    monitoredHttpServerUris = {
        @Match(value = "/api/.*", type = Match.MatchType.REGEX, alias = "api-calls")
    },
    prometheus = @PrometheusOptions(enabled = true, endpoint = "/metrics")
)
public class MyAppConfig {
}
```

**Step 2** — Annotate methods with MicroProfile Metrics annotations:

```java
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

public class OrderService {

    @Counted(name = "orders-placed", tags = {"env=prod"})
    public void placeOrder(Order order) {
        // ... business logic
    }

    @Timed(name = "order-processing-time")
    public void processOrder(Order order) {
        // ... long-running logic
    }
}
```

**Step 3** — Bootstrap GuicedEE (metrics start automatically):

```java
IGuiceContext.registerModuleForScanning.add("my.app");
IGuiceContext.instance();
// Vert.x metrics are collecting
// GET /metrics returns Prometheus exposition format
```

No JPMS `provides` declaration is needed — the metrics module is registered automatically via its own service providers.

## 📐 Startup Flow

```mermaid
flowchart TD
    n1["IGuiceContext.instance()"]
    n2["IGuicePreStartup hooks"]
    n1 --> n2
    n3["MetricsPreStartup.onStartup()<br/>sortOrder = MIN_VALUE + 40"]
    n2 --> n3
    n4["Scan for @MetricsOptions annotation via ClassGraph"]
    n3 --> n4
    n5["Resolve environment variable overrides"]
    n3 --> n5
    n6["VertxConfigurator SPIs<br/>before Vert.x starts"]
    n1 --> n6
    n7["MetricsVertxConfigurator.builder()"]
    n6 --> n7
    n8["Build DropwizardMetricsOptions from @MetricsOptions"]
    n7 --> n8
    n9["Configure monitored URIs, routes, event bus handlers, client endpoints"]
    n7 --> n9
    n10["Apply to VertxBuilder via VertxOptions.setMetricsOptions()"]
    n7 --> n10
    n11["Guice injector created"]
    n1 --> n11
    n12["MetricsModule.configure()"]
    n11 --> n12
    n13["bind(MetricRegistry.class) from SharedMetricRegistries"]
    n12 --> n13
    n14["bind(MP MetricRegistry.class) via MPMetricRegistryProvider"]
    n12 --> n14
    n15["Bind CountedInterceptor for @Counted"]
    n12 --> n15
    n16["Bind TimedInterceptor for @Timed"]
    n12 --> n16
    n17["Bind MetricMethodInterceptor for @MetricMethod"]
    n12 --> n17
    n18["Load Metrics SPI for custom interceptors"]
    n12 --> n18
    n19["Setup GraphiteReporter<br/>if enabled"]
    n12 --> n19
    n20["IGuicePostStartup hooks"]
    n1 --> n20
    n21["VertxWebServerPostStartup<br/>from web module"]
    n20 --> n21
    n22["PrometheusMetricsConfigurator.builder()<br/>sortOrder = MIN_VALUE + 70"]
    n21 --> n22
    n23["Register GET handler at prometheus endpoint path"]
    n22 --> n23
```

## 📊 Metric Annotations

### `@Counted`

Increments a monotonic counter each time the method is invoked:

```java
@Counted(name = "api-requests", tags = {"endpoint=users"})
public List<User> getUsers() {
    return userRepository.findAll();
}
```

The annotation can be placed on the **method** or on the **class** (applies to all methods). If `name` is empty, it defaults to `ClassName.methodName`.

### `@Timed`

Measures method execution time with quantile snapshots:

```java
@Timed(name = "db-query-time", tags = {"table=orders"})
public List<Order> queryOrders() {
    return orderRepository.findAll();
}
```

The timer records count, min, max, mean, and percentile distributions (p50, p75, p95, p98, p99, p999).

### `@MetricMethod`

A custom GuicedEE annotation that increments a named counter. If the method returns a numeric type (`long`, `int`, `double`, `float`), the counter value is returned instead of the method's result:

```java
@MetricMethod(name = "items-processed", tags = {"source=api"})
public long processItem(String itemName) {
    // ... logic
    return 0; // replaced with current counter value
}
```

If the method has a `String` parameter, it is used as a dynamic name suffix. If `name` is empty, the string parameter becomes the metric name.

## ⚙️ Configuration

### `@MetricsOptions` annotation

Place `@MetricsOptions` on any class or package to configure the metrics subsystem:

```java
@MetricsOptions(
    enabled = true,
    registryName = "vertx",
    jmxEnabled = true,
    jmxDomain = "vertx",
    baseName = "vertx",
    monitoredHttpServerUris = {
        @Match(value = "/api/.*", type = Match.MatchType.REGEX, alias = "api")
    },
    monitoredEventBusHandlers = {
        @Match(value = "orders.*", type = Match.MatchType.REGEX)
    },
    graphite = @GraphiteOptions(
        enabled = true,
        host = "graphite.local",
        port = 2003,
        prefix = "my-app"
    ),
    prometheus = @PrometheusOptions(
        enabled = true,
        endpoint = "/metrics",
        port = 9090
    )
)
public class MyAppConfig {
}
```

| Attribute | Default | Description |
|---|---|---|
| `enabled` | `true` | Enable or disable metrics collection |
| `registryName` | `"vertx"` | Dropwizard `SharedMetricRegistries` name |
| `jmxEnabled` | `true` | Expose metrics as JMX MBeans |
| `jmxDomain` | `"vertx"` | JMX domain name |
| `baseName` | `"vertx"` | Base prefix for all metric names |
| `monitoredEventBusHandlers` | `{}` | Event bus addresses to monitor |
| `monitoredHttpServerUris` | `{}` | HTTP server URIs to monitor |
| `monitoredHttpServerRoutes` | `{}` | HTTP server routes to monitor |
| `monitoredHttpClientEndpoints` | `{}` | HTTP client endpoints to monitor |
| `graphite` | disabled | Graphite reporter configuration |
| `prometheus` | disabled | Prometheus scrape endpoint configuration |

### `@Match` configuration

Each monitored URI/handler/route uses a `@Match` annotation:

| Attribute | Default | Description |
|---|---|---|
| `value` | *(required)* | Pattern to match (address, URI, etc.) |
| `type` | `EQUALS` | `EQUALS` for exact match, `REGEX` for pattern match |
| `alias` | `""` | Optional alias for the metric name |

### `@GraphiteOptions`

| Attribute | Default | Description |
|---|---|---|
| `enabled` | `false` | Enable Graphite reporting |
| `host` | `"localhost"` | Graphite server hostname |
| `port` | `2003` | Graphite pickle protocol port |
| `prefix` | `""` | Metric name prefix |

### `@PrometheusOptions`

| Attribute | Default | Description |
|---|---|---|
| `enabled` | `false` | Enable Prometheus scrape endpoint |
| `endpoint` | `"/metrics"` | HTTP path for the scrape handler |
| `port` | `-1` | -1 shares the application HTTP listener; 0..65535 creates a dedicated managed listener |

### Environment variable overrides

The settings below work without `@MetricsOptions`, including in SPI-only discovery mode. They use `Environment` to resolve system properties, process environment, `.env.local`, `.env`, then annotation/framework defaults, in that order:

| Variable | Overrides | Example |
|---|---|---|
| `METRICS_ENABLED` | `enabled` | `false` |
| `METRICS_REGISTRY_NAME` | `registryName` | `my-registry` |
| `METRICS_JMX_ENABLED` | `jmxEnabled` | `false` |
| `METRICS_JMX_DOMAIN` | `jmxDomain` | `my-app` |
| `METRICS_BASE_NAME` | `baseName` | `my-app` |
| `METRICS_GRAPHITE_ENABLED` | `graphite.enabled` | `true` |
| `METRICS_GRAPHITE_HOST` | `graphite.host` | `graphite.prod` |
| `METRICS_GRAPHITE_PORT` | `graphite.port` | `2003` |
| `METRICS_GRAPHITE_PREFIX` | `graphite.prefix` | `prod.myapp` |
| `METRICS_PROMETHEUS_ENABLED` | `prometheus.enabled` | `true` |
| `METRICS_PROMETHEUS_ENDPOINT` | `prometheus.endpoint` | `/prom/metrics` |
| `METRICS_PROMETHEUS_PORT` | `prometheus.port` | `-1` |

Environment variables take precedence over annotation values.

## 🔍 Programmatic Metric Access

### Inject the registries

Both Dropwizard and MicroProfile registries are injectable:

```java
import com.codahale.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricRegistry;

public class MonitoringService {

    @Inject
    private com.codahale.metrics.MetricRegistry dropwizardRegistry;

    @Inject
    private org.eclipse.microprofile.metrics.MetricRegistry mpRegistry;
}
```

### Vert.x metric enumerations

Use type-safe enums to look up Vert.x metrics by name:

| Enum | Base name | Examples |
|---|---|---|
| `VertxMetrics` | `vertx` | `event-loop-size`, `worker-pool-size` |
| `HttpServerMetrics` | `vertx.http.servers.<host>:<port>` | `requests`, `connections`, `responses-2xx` |
| `HttpClientMetrics` | `vertx.http.clients` | `requests`, `endpoint.%s.ttfb` |
| `EventBusMetrics` | `vertx.eventbus` | `handlers`, `messages.sent`, `messages.pending` |
| `PoolMetrics` | `vertx.pools.<type>.<name>` | `queue-size`, `in-use`, `pool-ratio` |
| `NetServerMetrics` | `vertx.net.servers.<host>:<port>` | `open-netsockets`, `bytes-read` |
| `DatagramMetrics` | `vertx.datagram` | `sockets`, `bytes-written` |

```java
@Inject
com.codahale.metrics.MetricRegistry registry;

public long getActiveConnections() {
    return registry.getCounters()
        .get(HttpServerMetrics.CONNECTIONS.toString())
        .getCount();
}
```

Enums with placeholders provide a `format()` method:

```java
// vertx.eventbus.handlers.orders
String name = EventBusMetrics.HANDLERS_ADDRESS.format("orders");
```

## 🧩 Custom Metric Extensions

Register custom annotations and their interceptors by implementing the `Metrics` SPI:

```java
public class MyCustomMetrics implements Metrics {
    @Override
    public Map<Class<? extends Annotation>, Class<? extends MethodInterceptor>> annotations() {
        return Map.of(MyGauged.class, MyGaugedInterceptor.class);
    }
}
```

Register via JPMS:

```java
module my.app {
    requires com.guicedee.metrics;

    provides com.guicedee.metrics.Metrics
        with my.app.MyCustomMetrics;
}
```

The module declares `uses Metrics;` and discovers implementations via `ServiceLoader`. Each annotation → interceptor pair is bound in Guice AOP during `MetricsModule.configure()`.

## 📋 Prometheus Endpoint

When `@PrometheusOptions(enabled = true)` is set, a `GET` handler is registered at the configured endpoint (default `/metrics`). With `port = -1` (default), the route uses the application listener. A non-negative port creates an isolated listener owned by startup/shutdown; it does not add the route to the public listener. The response is `text/plain; version=0.0.4` in Prometheus exposition format:

```
# TYPE my_app_orders_placed counter
my_app_orders_placed 42
# TYPE my_app_order_processing_time_count summary
my_app_order_processing_time_count 15
# TYPE my_app_order_processing_time summary
my_app_order_processing_time{quantile="0.5"} 0.125
my_app_order_processing_time{quantile="0.95"} 0.350
my_app_order_processing_time{quantile="0.99"} 0.500
```

All metric types are rendered: gauges, counters, histograms (as summaries with quantiles), meters (count + rates), and timers (count + quantiles + rates).

## 📊 Monitoring Stack (Docker Compose)

A `docker-compose.yml` is provided for a local monitoring stack:

```bash
docker-compose up -d
```

This starts:

| Service | Port | Purpose |
|---|---|---|
| **Graphite** | `8080` (Web UI), `2003` (pickle) | Metrics storage and graphing |
| **Grafana** | `3000` | Dashboard visualization (login: `admin`/`admin`) |
| **Prometheus** | `9090` | Metrics scraping and alerting |

### Grafana setup

1. Login to Grafana at `http://localhost:3000`
2. Go to **Connections** → **Data Sources**
3. Add **Graphite** with URL `http://graphite:80`
4. Add **Prometheus** with URL `http://prometheus:9090`
5. Click **Save & Test**

A `prometheus.yml` is included that scrapes `host.docker.internal:9090/metrics` every 15 seconds.

## 🗺️ Module Graph

```mermaid
flowchart LR
    com_guicedee_metrics["com.guicedee.metrics"]
    com_guicedee_metrics --> com_guicedee_guicedinjection["com.guicedee.guicedinjection<br/>GuicedEE runtime — scanning, Guice, lifecycle"]
    com_guicedee_metrics --> com_guicedee_client["com.guicedee.client<br/>GuicedEE SPI contracts"]
    com_guicedee_metrics --> com_guicedee_vertx["com.guicedee.vertx<br/>Vert.x lifecycle — VertxConfigurator SPI"]
    com_guicedee_metrics --> com_guicedee_vertx_web["com.guicedee.vertx.web<br/>Vert.x Web — Router, optional for Prometheus endpoint"]
    com_guicedee_metrics --> io_vertx_metrics_dropwizard["io.vertx.metrics.dropwizard<br/>Vert.x Dropwizard Metrics integration"]
    com_guicedee_metrics --> com_codahale_metrics["com.codahale.metrics<br/>Dropwizard Metrics core + Graphite reporter"]
    com_guicedee_metrics --> io_vertx_core["io.vertx.core<br/>Vert.x core"]
    com_guicedee_metrics --> com_guicedee_modules_services_metrics["com.guicedee.modules.services.metrics<br/>MicroProfile Metrics API — annotations, SPI"]
```

## 🧩 JPMS

Module name: **`com.guicedee.metrics`**

The module:
- **exports** `com.guicedee.metrics`, `com.guicedee.metrics.implementations`, `com.guicedee.metrics.enumerations`, `com.guicedee.metrics.implementations.mp`
- **provides** `IGuiceModule` with `MetricsModule`
- **provides** `IGuicePreStartup` with `MetricsPreStartup`
- **provides** `VertxConfigurator` with `MetricsVertxConfigurator`
- **provides** `VertxRouterConfigurator` with `PrometheusMetricsConfigurator`
- **uses** `Metrics` (SPI for custom annotation interceptors)

## 🏗️ Key Classes

| Class | Role |
|---|---|
| `MetricsOptions` | Annotation — configures registry, JMX, monitored URIs, Graphite, Prometheus |
| `Match` | Annotation — defines URI/address match patterns with `EQUALS` or `REGEX` type |
| `MetricMethod` | Annotation — marks a method as a counted metric with dynamic naming |
| `Metrics` | SPI — register custom annotation → interceptor bindings |
| `MetricsPreStartup` | `IGuicePreStartup` — scans for `@MetricsOptions` and resolves env overrides |
| `MetricsVertxConfigurator` | `VertxConfigurator` — applies `DropwizardMetricsOptions` to the `VertxBuilder` |
| `MetricsModule` | `IGuiceModule` — binds registries, interceptors, Graphite reporter |
| `CountedInterceptor` | Guice AOP `MethodInterceptor` for `@Counted` |
| `TimedInterceptor` | Guice AOP `MethodInterceptor` for `@Timed` |
| `MetricMethodInterceptor` | Guice AOP `MethodInterceptor` for `@MetricMethod` |
| `PrometheusMetricsConfigurator` | `VertxRouterConfigurator` — mounts the Prometheus scrape endpoint |
| `PrometheusMetricsHandler` | `Handler<RoutingContext>` — renders metrics in Prometheus exposition format |
| `MPMetricRegistryProvider` | Guice `Provider` — creates the MicroProfile `MetricRegistry` bridge |

## 🤝 Contributing

Issues and pull requests are welcome — please add tests for new metric types or interceptors.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## DatagramMetrics

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/enumerations/DatagramMetrics.java)

### DatagramMetrics

```java
public static final DatagramMetrics SOCKETS;
public static final DatagramMetrics EXCEPTIONS;
public static final DatagramMetrics BYTES_WRITTEN;
public static final DatagramMetrics BYTES_READ;
public String format(Object... args);
@Override public String toString();
```

## EventBusMetrics

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/enumerations/EventBusMetrics.java)

### EventBusMetrics

```java
public static final EventBusMetrics HANDLERS;
public static final EventBusMetrics HANDLERS_ADDRESS;
public static final EventBusMetrics MESSAGES_BYTES_READ;
public static final EventBusMetrics MESSAGES_BYTES_WRITTEN;
public static final EventBusMetrics MESSAGES_PENDING;
public static final EventBusMetrics MESSAGES_PENDING_LOCAL;
public static final EventBusMetrics MESSAGES_PENDING_REMOTE;
public static final EventBusMetrics MESSAGES_DISCARDED;
public static final EventBusMetrics MESSAGES_DISCARDED_LOCAL;
public static final EventBusMetrics MESSAGES_DISCARDED_REMOTE;
public static final EventBusMetrics MESSAGES_RECEIVED;
public static final EventBusMetrics MESSAGES_RECEIVED_LOCAL;
public static final EventBusMetrics MESSAGES_RECEIVED_REMOTE;
public static final EventBusMetrics MESSAGES_DELIVERED;
public static final EventBusMetrics MESSAGES_DELIVERED_LOCAL;
public static final EventBusMetrics MESSAGES_DELIVERED_REMOTE;
public static final EventBusMetrics MESSAGES_SENT;
public static final EventBusMetrics MESSAGES_SENT_LOCAL;
public static final EventBusMetrics MESSAGES_SENT_REMOTE;
public static final EventBusMetrics MESSAGES_PUBLISHED;
public static final EventBusMetrics MESSAGES_PUBLISHED_LOCAL;
public static final EventBusMetrics MESSAGES_PUBLISHED_REMOTE;
public static final EventBusMetrics MESSAGES_REPLY_FAILURES;
public String format(Object... args);
@Override public String toString();
```

## HttpClientMetrics

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/enumerations/HttpClientMetrics.java)

### HttpClientMetrics

```java
public static final HttpClientMetrics OPEN_NETSOCKETS;
public static final HttpClientMetrics OPEN_NETSOCKETS_REMOTE;
public static final HttpClientMetrics CONNECTIONS;
public static final HttpClientMetrics EXCEPTIONS;
public static final HttpClientMetrics BYTES_READ;
public static final HttpClientMetrics BYTES_WRITTEN;
public static final HttpClientMetrics REQUESTS;
public static final HttpClientMetrics METHOD_REQUESTS;
public static final HttpClientMetrics METHOD_REQUESTS_PATH;
public static final HttpClientMetrics RESPONSES_1XX;
public static final HttpClientMetrics RESPONSES_2XX;
public static final HttpClientMetrics RESPONSES_3XX;
public static final HttpClientMetrics RESPONSES_4XX;
public static final HttpClientMetrics RESPONSES_5XX;
public static final HttpClientMetrics OPEN_WEBSOCKETS;
public static final HttpClientMetrics OPEN_WEBSOCKETS_REMOTE;
public static final HttpClientMetrics ENDPOINT_OPEN_NETSOCKETS;
public static final HttpClientMetrics ENDPOINT_USAGE;
public static final HttpClientMetrics ENDPOINT_IN_USE;
public static final HttpClientMetrics ENDPOINT_TTFB;
public String format(Object... args);
@Override public String toString();
```

## HttpServerMetrics

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/enumerations/HttpServerMetrics.java)

### HttpServerMetrics

```java
public static final HttpServerMetrics OPEN_NETSOCKETS;
public static final HttpServerMetrics OPEN_NETSOCKETS_REMOTE;
public static final HttpServerMetrics CONNECTIONS;
public static final HttpServerMetrics EXCEPTIONS;
public static final HttpServerMetrics BYTES_READ;
public static final HttpServerMetrics BYTES_WRITTEN;
public static final HttpServerMetrics REQUESTS;
public static final HttpServerMetrics METHOD_REQUESTS;
public static final HttpServerMetrics METHOD_REQUESTS_PATH;
public static final HttpServerMetrics RESPONSES_1XX;
public static final HttpServerMetrics RESPONSES_2XX;
public static final HttpServerMetrics RESPONSES_3XX;
public static final HttpServerMetrics RESPONSES_4XX;
public static final HttpServerMetrics RESPONSES_5XX;
public static final HttpServerMetrics OPEN_WEBSOCKETS;
public static final HttpServerMetrics OPEN_WEBSOCKETS_REMOTE;
public String format(Object... args);
@Override public String toString();
```

## NetServerMetrics

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/enumerations/NetServerMetrics.java)

### NetServerMetrics

```java
public static final NetServerMetrics OPEN_NETSOCKETS;
public static final NetServerMetrics OPEN_NETSOCKETS_REMOTE;
public static final NetServerMetrics CONNECTIONS;
public static final NetServerMetrics EXCEPTIONS;
public static final NetServerMetrics BYTES_READ;
public static final NetServerMetrics BYTES_WRITTEN;
public String format(Object... args);
@Override public String toString();
```

## PoolMetrics

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/enumerations/PoolMetrics.java)

### PoolMetrics

```java
public static final PoolMetrics QUEUE_DELAY;
public static final PoolMetrics QUEUE_SIZE;
public static final PoolMetrics USAGE;
public static final PoolMetrics IN_USE;
public static final PoolMetrics POOL_RATIO;
public static final PoolMetrics MAX_POOL_SIZE;
@Override public String toString();
```

## VertxMetrics

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/enumerations/VertxMetrics.java)

### VertxMetrics

```java
public static final VertxMetrics EVENT_LOOP_SIZE;
public static final VertxMetrics WORKER_POOL_SIZE;
public static final VertxMetrics CLUSTER_HOST;
public static final VertxMetrics CLUSTER_PORT;
@Override public String toString();
```

## CountedInterceptor

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/CountedInterceptor.java)

### CountedInterceptor

```java
@Override public Object invoke(MethodInvocation invocation) throws Throwable;
```

## MetricMethodInterceptor

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/MetricMethodInterceptor.java)

### MetricMethodInterceptor

```java
@Override public Object invoke(MethodInvocation invocation) throws Throwable;
```

## MetricsModule

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/MetricsModule.java)

### MetricsModule

```java
@Override protected void configure();
```

## MetricsPreStartup

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/MetricsPreStartup.java)

### MetricsPreStartup

```java
@Override public List<Future<Boolean>> onStartup();
@Override public Integer sortOrder();
```

## MetricsVertxConfigurator

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/MetricsVertxConfigurator.java)

### MetricsVertxConfigurator

```java
@Override public VertxBuilder builder(VertxBuilder builder);
@Override public VertxOptions options(VertxOptions runtime);
```

## MPCounter

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/mp/MPCounter.java)

### MPCounter

```java
public MPCounter(com.codahale.metrics.Counter delegate);
@Override public void inc();
@Override public void inc(long n);
@Override public long getCount();
public void dec();
public void dec(long n);
```

## MPGauge

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/mp/MPGauge.java)

### MPGauge

```java
public MPGauge(com.codahale.metrics.Gauge<T> delegate);
@Override public T getValue();
```

## MPHistogram

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/mp/MPHistogram.java)

### MPHistogram

```java
public MPHistogram(com.codahale.metrics.Histogram delegate);
@Override public void update(int value);
@Override public void update(long value);
@Override public long getCount();
@Override public Snapshot getSnapshot();
@Override public long getSum();
```

## MPMetricRegistry

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/mp/MPMetricRegistry.java)

### MPMetricRegistry

```java
public MPMetricRegistry(com.codahale.metrics.MetricRegistry delegate, String scope);
@Override public Counter counter(String name);
@Override public Counter counter(String name, Tag... tags);
@Override public Counter counter(MetricID metricID);
@Override public Counter counter(Metadata metadata);
@Override public Counter counter(Metadata metadata, Tag... tags);
@Override public <T, R extends Number> Gauge<R> gauge(String name, T object, Function<T, R> func, Tag... tags);
@Override public <T, R extends Number> Gauge<R> gauge(MetricID metricID, T object, Function<T, R> func);
@Override public <T, R extends Number> Gauge<R> gauge(Metadata metadata, T object, Function<T, R> func, Tag... tags);
@Override public <T extends Number> Gauge<T> gauge(String name, Supplier<T> supplier, Tag... tags);
@Override public <T extends Number> Gauge<T> gauge(MetricID metricID, Supplier<T> supplier);
@Override public <T extends Number> Gauge<T> gauge(Metadata metadata, Supplier<T> supplier, Tag... tags);
@Override public Histogram histogram(String name);
@Override public Histogram histogram(String name, Tag... tags);
@Override public Histogram histogram(MetricID metricID);
@Override public Histogram histogram(Metadata metadata);
@Override public Histogram histogram(Metadata metadata, Tag... tags);
@Override public Timer timer(String name);
@Override public Timer timer(String name, Tag... tags);
@Override public Timer timer(MetricID metricID);
@Override public Timer timer(Metadata metadata);
@Override public Timer timer(Metadata metadata, Tag... tags);
@Override public Metric getMetric(MetricID metricID);
@Override public <T extends Metric> T getMetric(MetricID metricID, Class<T> clazz);
@Override public Counter getCounter(MetricID metricID);
@Override public Gauge<?> getGauge(MetricID metricID);
@Override public Histogram getHistogram(MetricID metricID);
@Override public Timer getTimer(MetricID metricID);
@Override public Metadata getMetadata(String name);
@Override public boolean remove(String name);
@Override public boolean remove(MetricID metricID);
@Override public void removeMatching(MetricFilter filter);
@Override public SortedSet<String> getNames();
@Override public SortedSet<MetricID> getMetricIDs();
@Override public SortedMap<MetricID, Gauge> getGauges();
@Override public SortedMap<MetricID, Gauge> getGauges(MetricFilter filter);
@Override public SortedMap<MetricID, Counter> getCounters();
@Override public SortedMap<MetricID, Counter> getCounters(MetricFilter filter);
@Override public SortedMap<MetricID, Histogram> getHistograms();
@Override public SortedMap<MetricID, Histogram> getHistograms(MetricFilter filter);
@Override public SortedMap<MetricID, Timer> getTimers();
@Override public SortedMap<MetricID, Timer> getTimers(MetricFilter filter);
@Override public SortedMap<MetricID, Metric> getMetrics(MetricFilter filter);
@Override public <T extends Metric> SortedMap<MetricID, T> getMetrics(Class<T> clazz, MetricFilter filter);
@Override public Map<MetricID, Metric> getMetrics();
@Override public Map<String, Metadata> getMetadata();
@Override public String getScope();
```

## MPMetricRegistryProvider

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/mp/MPMetricRegistryProvider.java)

### MPMetricRegistryProvider

```java
@Override public MetricRegistry get();
```

## MPSnapshot

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/mp/MPSnapshot.java)

### MPSnapshot

```java
public MPSnapshot(com.codahale.metrics.Snapshot delegate);
@Override public long size();
@Override public double getMax();
@Override public double getMean();
@Override public PercentileValue[] percentileValues();
@Override public HistogramBucket[] bucketValues();
@Override public void dump(OutputStream output);
```

## MPTimer

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/mp/MPTimer.java)

### MPTimer

```java
public MPTimer(com.codahale.metrics.Timer delegate);
@Override public void update(Duration duration);
@Override public <T> T time(Callable<T> event) throws Exception;
@Override public void time(Runnable event);
@Override public Context time();
@Override public Duration getElapsedTime();
@Override public long getCount();
@Override public Snapshot getSnapshot();
```

### MPContext

```java
public MPContext(com.codahale.metrics.Timer.Context delegate);
@Override public long stop();
@Override public void close();
```

## PrometheusMetricsConfigurator

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/PrometheusMetricsConfigurator.java)

### PrometheusMetricsConfigurator

```java
@Override public Router builder(Router builder);
@Override public java.util.List<com.guicedee.vertx.web.spi.ManagedHttpListener> listeners();
@Override public Integer sortOrder();
```

## PrometheusMetricsHandler

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/PrometheusMetricsHandler.java)

### PrometheusMetricsHandler

```java
@Override public void handle(RoutingContext event);
```

## TimedInterceptor

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/implementations/TimedInterceptor.java)

### TimedInterceptor

```java
@Override public Object invoke(MethodInvocation invocation) throws Throwable;
```

## Match

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/Match.java)

### Match

```java
String value();
MatchType type() default MatchType.EQUALS;
String alias() default "";
```

### MatchType

```java
public static final MatchType EQUALS;
public static final MatchType REGEX;
```

## MetricMethod

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/MetricMethod.java)

### MetricMethod

```java
String name() default "";
String[] tags() default {};
```

## Metrics

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/Metrics.java)

### Metrics

```java
Map<Class<? extends Annotation>, Class<? extends MethodInterceptor>> annotations();
```

## MetricsOptions

[Java source](https://github.com/GuicedEE/Metrics/blob/master/src/main/java/com/guicedee/metrics/MetricsOptions.java)

### MetricsOptions

```java
boolean enabled() default true;
String registryName() default "vertx";
boolean jmxEnabled() default true;
String jmxDomain() default "vertx";
String baseName() default "vertx";
Match[] monitoredEventBusHandlers() default {};
Match[] monitoredHttpServerUris() default {};
Match[] monitoredHttpServerRoutes() default {};
Match[] monitoredHttpClientEndpoints() default {};
GraphiteOptions graphite() default @GraphiteOptions(enabled = false);
PrometheusOptions prometheus() default @PrometheusOptions(enabled = false);
```

### GraphiteOptions

```java
boolean enabled() default false;
String host() default "localhost";
int port() default 2003;
String prefix() default "";
```

### PrometheusOptions

```java
boolean enabled() default false;
String endpoint() default "/metrics";
int port() default -1;
```
