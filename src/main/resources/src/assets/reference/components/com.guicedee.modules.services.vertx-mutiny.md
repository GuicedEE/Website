# com.guicedee.modules.services:vertx-mutiny

Vert.x Mutiny

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `io.vertx.mutiny`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Vert.x/vertx-mutiny/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `io.smallrye.reactive:mutiny-smallrye-context-propagation`; scope `compile`; optional.
- `jakarta.enterprise:jakarta.enterprise.cdi-api`; scope `compile`.
- `io.vertx:vertx-pg-client`; scope `compile`; optional.
- `io.vertx:vertx-mssql-client`; scope `compile`; optional.
- `io.vertx:vertx-mysql-client`; scope `compile`; optional.
- `io.vertx:vertx-oracle-client`; scope `compile`; optional.
- `io.vertx:vertx-db2-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-core`; scope `compile`; optional.
- `io.smallrye.reactive:vertx-mutiny-code-generator`; scope `compile`; optional.
- `io.smallrye.reactive:mutiny`; scope `compile`.
- `io.smallrye.reactive:smallrye-mutiny-vertx-config`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-rabbitmq-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-sql-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-web`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-db2-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-cassandra-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-mssql-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-mysql-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-oracle-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-mongo-client`; scope `compile`; optional.
- `com.guicedee.modules.services:mongodb-driver-reactivestreams`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-pg-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-redis-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-kafka-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-mail-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-web-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-amqp-client`; scope `compile`; optional.
- `io.smallrye.reactive:smallrye-mutiny-vertx-auth-sql-client`; scope `compile`; optional.
- `io.vertx:vertx-codegen-api`; scope `provided`.
- `io.smallrye.common:smallrye-common-annotation`; scope `compile`.
- `io.smallrye.reactive:smallrye-mutiny-vertx-runtime`; scope `compile`; optional.
- `tools.jackson.core:jackson-databind`; scope `compile`.
- `io.vertx:vertx-core`; scope `compile`; optional.
- `io.smallrye.common:smallrye-common-ref`; scope `compile`.

## JPMS descriptor

```java
module io.vertx.mutiny {
    requires transitive io.vertx.core;

    requires io.smallrye.common.ref;
    requires io.smallrye.mutiny;

    exports io.smallrye.context;
    exports io.smallrye.context.impl;
    exports io.smallrye.context.impl.cdi;
    exports io.smallrye.context.impl.wrappers;



    requires jakarta.cdi;

    requires static io.vertx.web;

    requires static io.vertx.codegen.api;
    //requires io.smallrye.mutiny.vertx.core;
    //requires vertx.mutiny.generator;
    //requires io.smallrye.mutiny.vertx.runtime;

    exports io.vertx.mutiny.core;
    exports io.vertx.mutiny.core.http;
    //exports io.vertx.mutiny.core.eventbus;
/*    exports io.vertx.mutiny.core.file;

    exports io.vertx.mutiny.core.net;
    exports io.vertx.mutiny.core.shareddata;
    exports io.vertx.mutiny.core.spi;
    exports io.vertx.mutiny.core.streams;
    exports io.vertx.mutiny.core.workerpool;
    exports io.vertx.mutiny.ext.auth;
    exports io.vertx.mutiny.ext.auth.authentication;
    exports io.vertx.mutiny.ext.auth.authorization;
    exports io.vertx.mutiny.ext.auth.oauth2;
    exports io.vertx.mutiny.ext.auth.shiro;
    exports io.vertx.mutiny.ext.mongo;
    exports io.vertx.mutiny.ext.sql;
    exports io.vertx.mutiny.ext.stomp;*/

    exports io.vertx.mutiny.ext.web;
    exports io.vertx.mutiny.ext.web.client;
    exports io.vertx.mutiny.ext.web.codec;
    exports io.vertx.mutiny.ext.web.handler;
    exports io.vertx.mutiny.ext.web.multipart;
    exports io.vertx.mutiny.ext.web.sstore;



    exports io.smallrye.mutiny.vertx;
    exports io.smallrye.mutiny.vertx.core;
    exports io.smallrye.mutiny.vertx.impl;

    opens io.smallrye.mutiny.vertx to io.vertx.core;
    opens io.smallrye.mutiny.vertx.core to io.vertx.core;
    opens io.smallrye.mutiny.vertx.impl to io.vertx.core;


/*

    exports io.smallrye.mutiny;
    exports io.smallrye.mutiny.converters;
    exports io.smallrye.mutiny.converters.multi;
    exports io.smallrye.mutiny.converters.uni;
    exports io.smallrye.mutiny.converters;
    exports io.smallrye.mutiny.groups;
    exports io.smallrye.mutiny.helpers;
    exports io.smallrye.mutiny.helpers.spies;
    exports io.smallrye.mutiny.helpers.queues;
    exports io.smallrye.mutiny.helpers.test;
    exports io.smallrye.mutiny.infrastructure;
    exports io.smallrye.mutiny.operators;
    exports io.smallrye.mutiny.operators.multi;
    exports io.smallrye.mutiny.operators.uni;
    exports io.smallrye.mutiny.subscription;
    exports io.smallrye.mutiny.tuples;
    exports io.smallrye.mutiny.unchecked;


    opens io.smallrye.mutiny to io.vertx.core;
    opens io.smallrye.mutiny.converters to io.vertx.core;
    opens io.smallrye.mutiny.converters.multi to io.vertx.core;
    opens io.smallrye.mutiny.converters.uni to io.vertx.core;
    opens io.smallrye.mutiny.converters to io.vertx.core;
    opens io.smallrye.mutiny.groups to io.vertx.core;
    opens io.smallrye.mutiny.helpers to io.vertx.core;
    opens io.smallrye.mutiny.helpers.spies to io.vertx.core;
    opens io.smallrye.mutiny.helpers.queues to io.vertx.core;
    opens io.smallrye.mutiny.helpers.test to io.vertx.core;
    opens io.smallrye.mutiny.infrastructure to io.vertx.core;
    opens io.smallrye.mutiny.operators to io.vertx.core;
    opens io.smallrye.mutiny.operators.multi to io.vertx.core;
    opens io.smallrye.mutiny.operators.uni to io.vertx.core;
    opens io.smallrye.mutiny.subscription to io.vertx.core;
    opens io.smallrye.mutiny.tuples to io.vertx.core;
    opens io.smallrye.mutiny.unchecked to io.vertx.core;
    opens io.smallrye.mutiny.vertx to io.vertx.core;
    opens io.smallrye.mutiny.vertx.core to io.vertx.core;
    opens io.smallrye.mutiny.vertx.impl to io.vertx.core;
*/



}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Vert.x/vertx-mutiny/README.md). Historical version examples retain their original context.

# Vert.x Mutiny — GuicedEE Modular Repackage

[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Vert.x 5](https://img.shields.io/badge/Vert.x-5%2B-green)
![Modular](https://img.shields.io/badge/Modular-Level3-green)

A **modular repackage** of the [SmallRye Mutiny Vert.x bindings](https://github.com/smallrye/smallrye-mutiny-vertx-bindings) with a proper **JPMS `module-info.java`** descriptor (`module io.vertx.mutiny`), Vert.x 5 compatibility fixes, and SmallRye Context Propagation shaded in so the entire reactive Vert.x stack works cleanly on the module path.

This module shades the SmallRye Mutiny Vert.x core, web, SQL client, and other bindings into a single module and provides patched `Vertx` and `HttpServer` wrappers updated for the Vert.x 5 API (Future-based instead of callback-based).

## Reactor compilation

The `compile` phase runs Java compilation, JAR creation, shading, and Moditect in
that order. This supplies the named `io.vertx.mutiny` module to reactor consumers
such as GuicedEE Cerial before they compile. The wrapper's `target/classes`
directory alone does not contain the complete module.

JAR creation is forced on each build so shading always starts from the compiled
wrapper classes. `package` and `install` retain the same modular artifact.

## ✨ Features

- **Full JPMS module** — ships `module-info.java` (`module io.vertx.mutiny`) with explicit `exports`, `requires`, and `opens` directives
- **Vert.x 5 compatible** — `Vertx` and `HttpServer` wrapper classes updated to use Vert.x 5 Future-based APIs
- **Mutiny-first API** — every async method returns `Uni<T>` with `*AndAwait()` (blocking) and `*AndForget()` (fire-and-forget) variants
- **Context Propagation** — SmallRye Context Propagation (`io.smallrye.context`) shaded in for seamless CDI/reactive context flow
- **All-in-one shade** — smallrye-mutiny-vertx-core, vertx-web, SQL clients (PostgreSQL, MySQL, MSSQL, Oracle, DB2, Cassandra, MongoDB), Redis, Kafka, RabbitMQ, AMQP, Mail, and Web Client bindings included
- **Consumer-friendly handlers** — handler methods accept `java.util.function.Consumer` in addition to Vert.x `Handler`

## 📦 Installation

```xml
<dependency>
    <groupId>com.guicedee.modules.services</groupId>
    <artifactId>vertx-mutiny</artifactId>
</dependency>
```

Then `requires` it in your `module-info.java`:

```java
module my.app {
    requires io.vertx.mutiny;
}
```

## 🚀 Quick Start

### Creating a Vert.x instance

```java
import io.vertx.mutiny.core.Vertx;

Vertx vertx = Vertx.vertx();
```

### Starting an HTTP server

```java
import io.vertx.mutiny.core.http.HttpServer;

HttpServer server = vertx.createHttpServer();

server.requestHandler(req -> {
    req.response().end("Hello from Mutiny!");
});

server.listenAndAwait(8080, "0.0.0.0");
```

### Async with Uni

```java
server.listen(8080, "0.0.0.0")
    .subscribe().with(
        s -> System.out.println("Listening on port " + s.actualPort()),
        err -> System.err.println("Failed: " + err.getMessage())
    );
```

### Execute blocking code

```java
Uni<String> result = vertx.executeBlocking(() -> {
    // long-running computation
    return "computed value";
});
```

## 📐 API Pattern

Every async operation follows a consistent triple-method pattern:

| Method | Behaviour |
|---|---|
| `operation()` | Returns `Uni<T>` — subscribe to trigger |
| `operationAndAwait()` | Blocks until complete, returns result |
| `operationAndForget()` | Subscribes and discards the outcome |

For example:
- `listen(port)` → `Uni<HttpServer>`
- `listenAndAwait(port)` → `HttpServer` (blocking)
- `listenAndForget(port)` → `void`

## 🗺️ Module Graph

```
io.vertx.mutiny
 ├── io.vertx.core              (Vert.x 5 core)
 ├── io.vertx.web               (Vert.x Web, static)
 ├── io.smallrye.mutiny          (Mutiny reactive types)
 ├── io.smallrye.common.ref      (SmallRye common)
 ├── jakarta.cdi                 (CDI annotations)
 └── io.vertx.codegen.api        (codegen, static)
```

### Exported Packages

| Package | Description |
|---|---|
| `io.vertx.mutiny.core` | Mutiny wrapper for `Vertx` — the main entry point |
| `io.vertx.mutiny.core.http` | Mutiny wrappers for `HttpServer`, `HttpClient`, WebSocket |
| `io.vertx.mutiny.ext.web` | Mutiny wrappers for Vert.x Web `RouterConfig`, `Route` |
| `io.vertx.mutiny.ext.web.client` | Mutiny wrapper for `WebClient` |
| `io.vertx.mutiny.ext.web.codec` | Body codecs for web client |
| `io.vertx.mutiny.ext.web.handler` | Route handlers |
| `io.vertx.mutiny.ext.web.multipart` | Multipart form support |
| `io.vertx.mutiny.ext.web.sstore` | Session stores |
| `io.smallrye.mutiny.vertx` | SmallRye Mutiny–Vert.x integration internals |
| `io.smallrye.mutiny.vertx.core` | Core Mutiny–Vert.x helpers |
| `io.smallrye.mutiny.vertx.codegen` | Codegen support types |
| `io.smallrye.mutiny.vertx.impl` | Implementation helpers |
| `io.smallrye.context` | SmallRye Context Propagation |

## 🔧 What Changed from Upstream

| Area | Change |
|---|---|
| **Module descriptor** | Added `module-info.java` with full `exports`, `requires`, `opens` |
| **Vert.x 5 migration** | `Vertx.java` and `HttpServer.java` rewritten for Future-based Vert.x 5 API |
| **Shading** | All SmallRye Mutiny Vert.x bindings + Context Propagation shaded into one module |
| **Deprecated removal** | Removed methods dropped in Vert.x 5 (e.g. `nettyEventLoopGroup()`, `requestStream()`) |
| **Consumer handlers** | All handler methods offer `Consumer<T>` overloads alongside `Handler<T>` |

## 🔌 Shaded Artifacts

The following SmallRye Reactive artifacts are shaded into this module:

- `smallrye-mutiny-vertx-core` — Mutiny bindings for Vert.x Core
- `smallrye-mutiny-vertx-web` — Mutiny bindings for Vert.x Web
- `smallrye-mutiny-vertx-web-client` — WebClient bindings
- `smallrye-mutiny-vertx-sql-client` — Generic SQL client bindings
- `smallrye-mutiny-vertx-pg-client` — PostgreSQL
- `smallrye-mutiny-vertx-mysql-client` — MySQL
- `smallrye-mutiny-vertx-mssql-client` — Microsoft SQL Server
- `smallrye-mutiny-vertx-oracle-client` — Oracle
- `smallrye-mutiny-vertx-db2-client` — DB2
- `smallrye-mutiny-vertx-mongo-client` — MongoDB
- `smallrye-mutiny-vertx-cassandra-client` — Cassandra
- `smallrye-mutiny-vertx-redis-client` — Redis
- `smallrye-mutiny-vertx-kafka-client` — Kafka
- `smallrye-mutiny-vertx-rabbitmq-client` — RabbitMQ
- `smallrye-mutiny-vertx-amqp-client` — AMQP
- `smallrye-mutiny-vertx-mail-client` — Mail
- `smallrye-mutiny-vertx-config` — Config
- `smallrye-mutiny-vertx-auth-sql-client` — Auth SQL
- `smallrye-mutiny-vertx-runtime` — Runtime helpers
- `mutiny-smallrye-context-propagation` — Context propagation
- `smallrye-context-propagation` — SmallRye Context Propagation core

## 🤝 Contributing

Issues and pull requests are welcome — especially for upstream version bumps, additional Vert.x 5 API patches, and new SQL client bindings.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## HttpServer

[Java source](https://github.com/GuicedEE/Services/blob/master/Vert.x/vertx-mutiny/src/main/java/io/vertx/mutiny/core/http/HttpServer.java)

### HttpServer

```java
public static final io.smallrye.mutiny.vertx.TypeArg<HttpServer> __TYPE_ARG;
public HttpServer(io.vertx.core.http.HttpServer delegate);
public HttpServer(Object delegate);
@Override public io.vertx.core.http.HttpServer getDelegate();
@Override public String toString();
@Override public boolean equals(Object o);
@Override public int hashCode();
public boolean isMetricsEnabled();
public io.vertx.mutiny.core.http.HttpServer requestHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.HttpServerRequest> handler);
public io.vertx.mutiny.core.http.HttpServer invalidRequestHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.HttpServerRequest> handler);
public io.vertx.mutiny.core.http.HttpServer connectionHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.HttpConnection> handler);
public io.vertx.mutiny.core.http.HttpServer exceptionHandler(java.util.function.Consumer<java.lang.Throwable> handler);
public io.vertx.mutiny.core.http.HttpServer webSocketHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.ServerWebSocket> handler);
public io.vertx.mutiny.core.http.HttpServer webSocketHandshakeHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.ServerWebSocketHandshake> handler);
@CheckReturnValue public io.smallrye.mutiny.Uni<Boolean> updateSSLOptions(io.vertx.core.net.ServerSSLOptions options);
public Boolean updateSSLOptionsAndAwait(io.vertx.core.net.ServerSSLOptions options);
public void updateSSLOptionsAndForget(io.vertx.core.net.ServerSSLOptions options);
@CheckReturnValue public io.smallrye.mutiny.Uni<Boolean> updateSSLOptions(io.vertx.core.net.ServerSSLOptions options, boolean force);
public Boolean updateSSLOptionsAndAwait(io.vertx.core.net.ServerSSLOptions options, boolean force);
public void updateSSLOptionsAndForget(io.vertx.core.net.ServerSSLOptions options, boolean force);
public void updateTrafficShapingOptions(io.vertx.core.net.TrafficShapingOptions options);
@CheckReturnValue public io.smallrye.mutiny.Uni<io.vertx.mutiny.core.http.HttpServer> listen(int port, String host);
public io.vertx.mutiny.core.http.HttpServer listenAndAwait(int port, String host);
@Fluent public io.vertx.mutiny.core.http.HttpServer listenAndForget(int port, String host);
@CheckReturnValue public io.smallrye.mutiny.Uni<io.vertx.mutiny.core.http.HttpServer> listen(io.vertx.core.net.SocketAddress address);
public io.vertx.mutiny.core.http.HttpServer listenAndAwait(io.vertx.core.net.SocketAddress address);
@Fluent public io.vertx.mutiny.core.http.HttpServer listenAndForget(io.vertx.core.net.SocketAddress address);
@CheckReturnValue public io.smallrye.mutiny.Uni<io.vertx.mutiny.core.http.HttpServer> listen(int port);
public io.vertx.mutiny.core.http.HttpServer listenAndAwait(int port);
@Fluent public io.vertx.mutiny.core.http.HttpServer listenAndForget(int port);
@CheckReturnValue public io.smallrye.mutiny.Uni<io.vertx.mutiny.core.http.HttpServer> listen();
public io.vertx.mutiny.core.http.HttpServer listenAndAwait();
@Fluent public io.vertx.mutiny.core.http.HttpServer listenAndForget();
@CheckReturnValue public io.smallrye.mutiny.Uni<Void> close();
public Void closeAndAwait();
public void closeAndForget();
public int actualPort();
public static HttpServer newInstance(io.vertx.core.http.HttpServer arg);
```

## Vertx

[Java source](https://github.com/GuicedEE/Services/blob/master/Vert.x/vertx-mutiny/src/main/java/io/vertx/mutiny/core/Vertx.java)

### Vertx

```java
public static final io.smallrye.mutiny.vertx.TypeArg<Vertx> __TYPE_ARG;
public Vertx(io.vertx.core.Vertx delegate);
public Vertx(Object delegate);
@Override public io.vertx.core.Vertx getDelegate();
@Override public String toString();
@Override public boolean equals(Object o);
@Override public int hashCode();
public boolean isMetricsEnabled();
public static io.vertx.mutiny.core.VertxBuilder builder();
public static io.vertx.mutiny.core.Vertx vertx();
public static io.vertx.mutiny.core.Vertx vertx(io.vertx.core.VertxOptions options);
@CheckReturnValue public static io.smallrye.mutiny.Uni<io.vertx.mutiny.core.Vertx> clusteredVertx(io.vertx.core.VertxOptions options);
public static io.vertx.mutiny.core.Vertx clusteredVertxAndAwait(io.vertx.core.VertxOptions options);
public static void clusteredVertxAndForget(io.vertx.core.VertxOptions options);
public static io.vertx.mutiny.core.Context currentContext();
public io.vertx.mutiny.core.Context getOrCreateContext();
public io.vertx.mutiny.core.net.NetServer createNetServer(io.vertx.core.net.NetServerOptions options);
public io.vertx.mutiny.core.net.NetServer createNetServer();
public io.vertx.mutiny.core.net.NetClient createNetClient(io.vertx.core.net.NetClientOptions options);
public io.vertx.mutiny.core.net.NetClient createNetClient();
public io.vertx.mutiny.core.http.HttpServer createHttpServer(io.vertx.core.http.HttpServerOptions options);
public io.vertx.mutiny.core.http.HttpServer createHttpServer();
public io.vertx.mutiny.core.http.WebSocketClient createWebSocketClient();
public io.vertx.mutiny.core.http.WebSocketClient createWebSocketClient(io.vertx.core.http.WebSocketClientOptions options);
public io.vertx.mutiny.core.http.HttpClientBuilder httpClientBuilder();
public io.vertx.mutiny.core.http.HttpClient createHttpClient(io.vertx.core.http.HttpClientOptions clientOptions, io.vertx.core.http.PoolOptions poolOptions);
public io.vertx.mutiny.core.http.HttpClient createHttpClient(io.vertx.core.http.HttpClientOptions clientOptions);
public io.vertx.mutiny.core.http.HttpClient createHttpClient(io.vertx.core.http.PoolOptions poolOptions);
public io.vertx.mutiny.core.http.HttpClient createHttpClient();
public io.vertx.mutiny.core.datagram.DatagramSocket createDatagramSocket(io.vertx.core.datagram.DatagramSocketOptions options);
public io.vertx.mutiny.core.datagram.DatagramSocket createDatagramSocket();
public io.vertx.mutiny.core.file.FileSystem fileSystem();
public io.vertx.mutiny.core.eventbus.EventBus eventBus();
public io.vertx.mutiny.core.dns.DnsClient createDnsClient(int port, String host);
public io.vertx.mutiny.core.dns.DnsClient createDnsClient();
public io.vertx.mutiny.core.dns.DnsClient createDnsClient(io.vertx.core.dns.DnsClientOptions options);
public io.vertx.mutiny.core.shareddata.SharedData sharedData();
public io.vertx.mutiny.core.Timer timer(long delay);
public io.vertx.mutiny.core.Timer timer(long delay, java.util.concurrent.TimeUnit unit);
public long setTimer(long delay, java.util.function.Consumer<Long> handler);
public long setPeriodic(long delay, java.util.function.Consumer<Long> handler);
public long setPeriodic(long initialDelay, long delay, java.util.function.Consumer<Long> handler);
public boolean cancelTimer(long id);
public void runOnContext(java.lang.Runnable action);
@CheckReturnValue public io.smallrye.mutiny.Uni<Void> close();
public Void closeAndAwait();
public void closeAndForget();
@CheckReturnValue public io.smallrye.mutiny.Uni<String> deployVerticle(String name);
public String deployVerticleAndAwait(String name);
public void deployVerticleAndForget(String name);
@CheckReturnValue public io.smallrye.mutiny.Uni<String> deployVerticle(String name, io.vertx.core.DeploymentOptions options);
public String deployVerticleAndAwait(String name, io.vertx.core.DeploymentOptions options);
public void deployVerticleAndForget(String name, io.vertx.core.DeploymentOptions options);
@CheckReturnValue public io.smallrye.mutiny.Uni<Void> undeploy(String deploymentID);
public Void undeployAndAwait(String deploymentID);
public void undeployAndForget(String deploymentID);
public Set<String> deploymentIDs();
public boolean isClustered();
@CheckReturnValue public <T> io.smallrye.mutiny.Uni<T> executeBlocking(io.smallrye.mutiny.Uni<T> blockingCodeHandler, boolean ordered);
public <T> T executeBlockingAndAwait(io.smallrye.mutiny.Uni<T> blockingCodeHandler, boolean ordered);
public <T> void executeBlockingAndForget(io.smallrye.mutiny.Uni<T> blockingCodeHandler, boolean ordered);
@CheckReturnValue public <T> io.smallrye.mutiny.Uni<T> executeBlocking(io.smallrye.mutiny.Uni<T> blockingCodeHandler);
public <T> T executeBlockingAndAwait(io.smallrye.mutiny.Uni<T> blockingCodeHandler);
public <T> void executeBlockingAndForget(io.smallrye.mutiny.Uni<T> blockingCodeHandler);
public io.vertx.mutiny.core.WorkerExecutor createSharedWorkerExecutor(String name);
public io.vertx.mutiny.core.WorkerExecutor createSharedWorkerExecutor(String name, int poolSize);
public io.vertx.mutiny.core.WorkerExecutor createSharedWorkerExecutor(String name, int poolSize, long maxExecuteTime);
public io.vertx.mutiny.core.WorkerExecutor createSharedWorkerExecutor(String name, int poolSize, long maxExecuteTime, java.util.concurrent.TimeUnit maxExecuteTimeUnit);
public boolean isNativeTransportEnabled();
public java.lang.Throwable unavailableNativeTransportCause();
public io.vertx.mutiny.core.Vertx exceptionHandler(java.util.function.Consumer<java.lang.Throwable> handler);
@CheckReturnValue public io.smallrye.mutiny.Uni<String> deployVerticle(io.vertx.core.Verticle verticle);
public String deployVerticleAndAwait(io.vertx.core.Verticle verticle);
public void deployVerticleAndForget(io.vertx.core.Verticle verticle);
@CheckReturnValue public io.smallrye.mutiny.Uni<String> deployVerticle(io.vertx.core.Verticle verticle, io.vertx.core.DeploymentOptions options);
public String deployVerticleAndAwait(io.vertx.core.Verticle verticle, io.vertx.core.DeploymentOptions options);
public void deployVerticleAndForget(io.vertx.core.Verticle verticle, io.vertx.core.DeploymentOptions options);
@CheckReturnValue public io.smallrye.mutiny.Uni<String> deployVerticle(java.util.function.Supplier<io.vertx.core.Verticle> verticleSupplier, io.vertx.core.DeploymentOptions options);
public String deployVerticleAndAwait(java.util.function.Supplier<io.vertx.core.Verticle> verticleSupplier, io.vertx.core.DeploymentOptions options);
public void deployVerticleAndForget(java.util.function.Supplier<io.vertx.core.Verticle> verticleSupplier, io.vertx.core.DeploymentOptions options);
public void registerVerticleFactory(io.vertx.core.spi.VerticleFactory factory);
public void unregisterVerticleFactory(io.vertx.core.spi.VerticleFactory factory);
public Set<io.vertx.core.spi.VerticleFactory> verticleFactories();
@CheckReturnValue public <T> io.smallrye.mutiny.Uni<T> executeBlocking(java.util.concurrent.Callable<T> blockingCodeHandler);
public <T> T executeBlockingAndAwait(java.util.concurrent.Callable<T> blockingCodeHandler);
public <T> void executeBlockingAndForget(java.util.concurrent.Callable<T> blockingCodeHandler);
@CheckReturnValue public <T> io.smallrye.mutiny.Uni<T> executeBlocking(java.util.concurrent.Callable<T> blockingCodeHandler, boolean ordered);
public <T> T executeBlockingAndAwait(java.util.concurrent.Callable<T> blockingCodeHandler, boolean ordered);
public <T> void executeBlockingAndForget(java.util.concurrent.Callable<T> blockingCodeHandler, boolean ordered);
public static Vertx newInstance(io.vertx.core.Vertx arg);
```
