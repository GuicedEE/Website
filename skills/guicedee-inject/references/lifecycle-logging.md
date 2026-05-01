# Lifecycle, Logging & JobService

Detailed reference for lifecycle hooks, `@InjectLogger`, `LogUtils`, `Log4JConfigurator`, and `JobService`.

## Lifecycle Hooks — Detail

### `IGuiceConfigurator`

Runs first, before scanning. Receives a `GuiceConfig` to tune scanner behaviour.

```java
public class MyConfig implements IGuiceConfigurator<MyConfig> {
    @Override
    public IGuiceConfig<?> configure(IGuiceConfig<?> config) {
        config.setAnnotationScanning(true);
        return config;
    }
}
```

### `IGuicePreStartup`

Runs after scanning, before injector creation. Grouped by `sortOrder()` — all futures in a group must complete before the next group runs. Returns `List<Future<Boolean>>` (Vert.x futures).

```java
public class DbMigration implements IGuicePreStartup<DbMigration> {
    @Override
    public List<Future<Boolean>> onStartup() {
        return List.of(Future.succeededFuture(true));
    }

    @Override
    public Integer sortOrder() { return 50; }
}
```

### `IGuiceModule`

Standard Guice `AbstractModule` discovered via SPI. The `enabled()` method (default `true`) can be overridden to conditionally skip.

```java
public class AppModule extends AbstractModule implements IGuiceModule<AppModule> {
    @Override
    protected void configure() {
        bind(MyService.class).to(MyServiceImpl.class);
    }
}
```

### `IGuicePostStartup`

Runs after the injector is ready. Same grouping semantics as `IGuicePreStartup`. Returns `List<Uni<Boolean>>` (Mutiny).

```java
public class WarmupCache implements IGuicePostStartup<WarmupCache> {
    @Override
    public List<Uni<Boolean>> postLoad() {
        return List.of(Uni.createFrom().item(true));
    }

    @Override
    public Integer sortOrder() { return 100; }
}
```

### `IGuicePreDestroy`

Cleanup on JVM shutdown. Executed in `sortOrder()` order.

```java
public class ConnectionPoolShutdown implements IGuicePreDestroy<ConnectionPoolShutdown> {
    @Override
    public void onDestroy() {
        pool.close();
    }

    @Override
    public Integer sortOrder() { return Integer.MIN_VALUE + 10; }
}
```

### Registration

Every lifecycle hook must be registered in both:

```java
// module-info.java
provides com.guicedee.client.services.lifecycle.IGuicePreStartup with my.app.DbMigration;
```

And `META-INF/services/com.guicedee.client.services.lifecycle.IGuicePreStartup`:
```
my.app.DbMigration
```

## `@InjectLogger`

Inject a named Log4j2 `Logger` into any Guice-managed field:

```java
public class OrderService {
    @InjectLogger("orders")
    Logger log;
}
```

| Attribute | Default | Purpose |
|---|---|---|
| `value` | declaring class name | Logger name |
| `level` | *(none)* | Optional level hint |
| `rollingFileName` | *(none)* | File name hint for rolling appenders |
| `fileName` | *(none)* | File name hint for file appenders |

Wired automatically by `Log4JTypeListener` and `Log4JMembersInjector`, registered in `ContextBinderGuice`.

## `LogUtils` API

Programmatic Log4j2 setup — no XML configuration files needed:

| Method | What it does |
|---|---|
| `addConsoleLogger()` | Adds a stdout appender with default pattern |
| `addConsoleLogger(Level)` | Adds a stdout appender at the given level |
| `addHighlightedConsoleLogger()` | Adds a stdout appender with ANSI `%highlight` colors |
| `addFileRollingLogger(name, dir)` | Adds a rolling file appender (100 MB / daily rollover) |
| `addMinimalFileRollingLogger(name)` | Same with `logs/` default directory |
| `getSpecificRollingLogger(name, dir, pattern, additive)` | Returns a named `Logger` with its own isolated rolling file |
| `addAppender(appender, level)` | Escape hatch — attach any Log4j2 `Appender` to the root logger |

### Cloud mode

When the `CLOUD` environment variable is set, all layouts auto-switch to compact JSON for log aggregator ingestion.

### Log level environment variables

| Variable | Effect |
|---|---|
| `GUICEDEE_LOG_LEVEL` / `LOG_LEVEL` | Set root log level by name (e.g. `INFO`, `DEBUG`) |
| `DEBUG=true` | Set root level to `DEBUG` |
| `TRACE=true` | Set root level to `TRACE` |

Programmatic: `GuiceContext.setDefaultLogLevel(Level.INFO)` or `GuiceContext.setDefaultLogLevel("WARN")`.

### Console layout options

Switch layout dynamically: `GuiceContext.setConsoleLayout(ConsoleLayoutOption.HIGHLIGHT)`.

| Option | Description |
|---|---|
| `CURRENT` | Default pattern layout |
| `FIXED` | Fixed-width columns (logger, thread, level) |
| `HIGHLIGHT` | ANSI highlighted level + colored logger/thread |
| `JSON` | Compact JSON (for log aggregators) |

## `Log4JConfigurator` SPI

Customize Log4j2 `Configuration` at startup (after default appenders are set up):

```java
public class MyLog4JConfig implements Log4JConfigurator {
    @Override
    public void configure(Configuration config) {
        // Add custom appenders, filters, etc.
    }
}
```

Register under `com.guicedee.client.services.lifecycle.Log4JConfigurator`.

## `JobService` API

Vert.x-backed job pools. Singleton at `JobService.INSTANCE`. Auto-shuts-down via `IGuicePreDestroy`.

All tasks execute on the Vert.x worker pool via `executeBlocking()`. Periodic tasks use `Vertx.setPeriodic()`.
Cron tasks use chained `Vertx.setTimer()` with a built-in `CronExpression` parser.

### One-off job pools

```java
JobService jobs = JobService.INSTANCE;
jobs.registerJob("import", 100);              // register pool with max queue 100
jobs.addJob("import", () -> processFile(f));   // submit work (returns Future<Void>)
Future<String> result = jobs.addTask("import", () -> compute()); // callable variant
jobs.removeJob("import");                      // cancel timers and remove
```

### Delayed one-off

```java
jobs.addDelayedJob("cleanup", () -> purge(), 5, TimeUnit.MINUTES);
```

### Scheduled polling

```java
jobs.registerPollingJob("heartbeat", () -> ping(), 0, 30, TimeUnit.SECONDS);
jobs.addPollingJob("metrics", () -> collect(), 1, 60, TimeUnit.SECONDS);
jobs.removePollingJob("heartbeat");
```

### Cron scheduling

Standard 5-field UNIX cron expressions. Supports values, ranges, steps, lists, wildcards, named days (MON-SUN), named months (JAN-DEC).

```java
jobs.addCronJob("nightly-report", "0 2 * * *", () -> generateReport());
jobs.addCronJob("weekday-sync", "0 9 * * MON-FRI", () -> syncData());
jobs.addCronJob("quarterly", "0 0 1 1,4,7,10 *", () -> quarterlyJob());
```

Day matching follows standard UNIX cron OR semantics: when both day-of-month and day-of-week are restricted, the job fires if *either* matches.

### Pool introspection

```java
jobs.getJobPools();                     // Set<String> of all pool names
jobs.getPollingPools();                 // Set<String> of polling pool names
jobs.getCronPools();                    // Set<String> of cron pool names
jobs.isRegistered("import");            // boolean
jobs.getActiveTaskCount("import");      // int - currently running tasks
jobs.getMaxQueueCount("import");        // int - configured max
jobs.getCronExpression("nightly-report"); // Optional<CronExpression>
```

### Configuration

| Static setter | Default | Purpose |
|---|---|---|
| `JobService.setDefaultWaitTime(long)` | `120` | Default wait time (backward compat) |
| `JobService.setDefaultWaitUnit(TimeUnit)` | `SECONDS` | Default wait time unit (backward compat) |
| `jobs.setMaxQueueCount(name, count)` | `20` | Max concurrent tasks per pool |

## Core Bindings (auto-registered by `ContextBinderGuice`)

| Type | Provider |
|---|---|
| `GuiceConfig` | Current config from `GuiceContext.instance().getConfig()` |
| `GlobalProperties` | Eager singleton |
| `ScanResult` | Current scan result (singleton) |
| `Log4JTypeListener` | Binds `@InjectLogger` fields automatically |

## Module Graph

```
com.guicedee.guicedinjection
 ├── com.guicedee.client              (SPI contracts)
 ├── com.guicedee.vertx               (Vert.x integration)
 ├── io.smallrye.config.core          (MicroProfile Config)
 ├── org.apache.commons.lang3
 ├── org.apache.logging.log4j.core    (Log4j2 runtime)
 ├── org.apache.logging.log4j.jul     (JUL bridge)
 └── lombok                           (static, compile-only)
```

