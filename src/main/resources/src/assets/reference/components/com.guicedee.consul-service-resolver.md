# com.guicedee:consul-service-resolver

Consul health/catalog-based AddressResolver for Vert.x service discovery

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.consul.resolver`.
- [Source POM](https://github.com/GuicedEE/ConsulServiceResolver/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.projectlombok:lombok`; scope `compile`.
- `com.guicedee:consul`; scope `compile`.
- `com.guicedee:service-discovery`; scope `compile`.
- `io.vertx:vertx-core`; scope `compile`.
- `io.vertx:vertx-consul-client`; scope `compile`.
- `io.vertx:vertx-service-resolver`; scope `compile`.
- `com.guicedee:inject`; scope `test`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `com.guicedee.modules.services:testcontainers`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.consul.resolver.ConsulServiceResolverProvider;
import com.guicedee.vertx.servicediscovery.IServiceResolverProvider;

module com.guicedee.consul.resolver {

    exports com.guicedee.consul.resolver;

    requires transitive com.guicedee.consul;
    requires transitive com.guicedee.vertx.servicediscovery;
    requires transitive io.vertx.core;
    requires transitive io.vertx.consul.client;
    requires io.vertx.serviceresolver;
    requires static lombok;

    provides IServiceResolverProvider with ConsulServiceResolverProvider;

    opens com.guicedee.consul.resolver to com.google.guice;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/ConsulServiceResolver/blob/master/README.md). Historical version examples retain their original context.

# GuicedEE Consul Service Resolver

[![Build](https://github.com/GuicedEE/GuicedConsulServiceResolver/actions/workflows/build.yml/badge.svg)](https://github.com/GuicedEE/GuicedConsulServiceResolver/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.guicedee/consul-service-resolver)](https://github.com/GuicedEE/GuicedConsulServiceResolver)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Guice 7](https://img.shields.io/badge/Guice-7%2B-green)
![Vert.X 5](https://img.shields.io/badge/Vert.x-5%2B-green)

Bridges **Consul health/catalog lookups** into the GuicedEE service-discovery module via the `IServiceResolverProvider` SPI. Provides a Consul-backed `AddressResolver` for Vert.x HTTP clients with automatic healthy-instance load balancing.

Built on [Vert.x 5](https://vertx.io/) · [Vert.x Consul Client](https://vertx.io/docs/vertx-consul-client/java/) · [GuicedEE Consul](https://github.com/GuicedEE/ConsulServiceResolver/blob/consul) · [GuicedEE Service Discovery](https://github.com/GuicedEE/ConsulServiceResolver/blob/service-discovery) · JPMS module `com.guicedee.consul.resolver` · Java 25+

## 📦 Installation

```xml
<dependency>
  <groupId>com.guicedee</groupId>
  <artifactId>consul-service-resolver</artifactId>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.guicedee:consul-service-resolver:2.4.1")
```
</details>

## ✨ Features

- **Consul-backed service resolution** — queries `healthServiceNodes(serviceName, passingOnly=true)` for healthy instances
- **SPI integration** — implements `IServiceResolverProvider` for seamless plug-in to service-discovery
- **Shared Consul configuration** — reuses `@ConsulOptions` from the core consul module
- **Per-resolver overrides** — configure consul connection per-resolver via `@ServiceResolverOptions` attributes
- **Environment variable overrides** — `SERVICE_RESOLVER_{NAME}_CONSUL_HOST` etc.
- **Load-balanced HTTP clients** — Vert.x HTTP/Web clients use resolved instances automatically

## 🚀 Quick Start

**Step 1** — Declare a Consul-based resolver on your client package:

```java
@ServiceResolverOptions(value = "wallet-api", type = "consul")
package com.myapp.wallet.client;

import com.guicedee.vertx.servicediscovery.ServiceResolverOptions;
```

**Step 2** — Use with Vert.x HTTP Client or `@Endpoint` / `RestClient`:

```java
HttpClient client = vertx.httpClientBuilder()
    .withAddressResolver(resolver)
    .build();

ServiceAddress serviceAddress = ServiceAddress.of("wallet-api");
client.request(new RequestOptions()
    .setMethod(HttpMethod.GET)
    .setURI("/api/wallets")
    .setServer(serviceAddress));
```

The resolver queries Consul for healthy instances of `wallet-api` and load-balances requests across them.

## 📐 Architecture

```mermaid
flowchart TD
    n1["@ServiceResolverOptions(type = 'consul')"]
    n2["Service Discovery Module<br/>scans annotation"]
    n1 --> n2
    n3["IServiceResolverProvider SPI<br/>delegates to ConsulServiceResolverProvider"]
    n2 --> n3
    n4["ConsulAddressResolver<br/>queries Consul health API"]
    n3 --> n4
    n5["Vert.x HTTP Client<br/>uses resolved addresses"]
    n4 --> n5
```

### How It Works

1. `@ServiceResolverOptions(type = "consul")` is scanned by the service-discovery module
2. The SPI `IServiceResolverProvider` delegates to `ConsulServiceResolverProvider`
3. A `ConsulAddressResolver` is created using Consul connection details from either:
   - The `@ServiceResolverOptions` consul fields (`consulHost`, `consulPort`, etc.)
   - Or the `@ConsulOptions` annotation discovered by the consul core module
4. The resolver queries `healthServiceNodes(serviceName, passingOnly=true)` for healthy instances
5. Vert.x HTTP/Web clients use these instances for load-balanced requests

## ⚙️ Configuration

Consul connection can be configured via:

| Method | Example |
|---|---|
| `@ServiceResolverOptions` attributes | `@ServiceResolverOptions(consulHost = "consul.internal", consulPort = 8500)` |
| Matching `@ConsulOptions` annotation | Preferred for shared config across modules |
| Environment variables | `SERVICE_RESOLVER_WALLET_API_CONSUL_HOST=consul.internal` |

### Environment Variable Overrides

| Pattern | Example |
|---|---|
| `SERVICE_RESOLVER_{NAME}_CONSUL_HOST` | `SERVICE_RESOLVER_WALLET_API_CONSUL_HOST=consul.internal` |
| `SERVICE_RESOLVER_{NAME}_CONSUL_PORT` | `SERVICE_RESOLVER_WALLET_API_CONSUL_PORT=8500` |
| `SERVICE_RESOLVER_{NAME}_CONSUL_TOKEN` | `SERVICE_RESOLVER_WALLET_API_CONSUL_TOKEN=my-token` |

## 🗺️ Module Graph

```mermaid
flowchart LR
    com_guicedee_cr["com.guicedee.consul.resolver"]
    com_guicedee_cr --> com_guicedee_consul["com.guicedee.consul<br/>Consul client & registration"]
    com_guicedee_cr --> com_guicedee_sd["com.guicedee.vertx.servicediscovery<br/>Service discovery framework"]
    com_guicedee_cr --> io_vertx_core["io.vertx.core<br/>Vert.x Core"]
    com_guicedee_cr --> io_vertx_consul["io.vertx.consul.client<br/>Vert.x Consul Client"]
    com_guicedee_cr --> io_vertx_sr["io.vertx.serviceresolver<br/>Vert.x Service Resolver"]
```

## 🧩 JPMS

Module name: **`com.guicedee.consul.resolver`**

The module:
- **exports** `com.guicedee.consul.resolver`
- **provides** `IServiceResolverProvider` with `ConsulServiceResolverProvider`

```java
module my.app {
    requires com.guicedee.consul.resolver;
}
```

## 🏗️ Key Classes

| Class | Role |
|---|---|
| `ConsulServiceResolverProvider` | `IServiceResolverProvider` implementation for Consul |
| `ConsulAddressResolver` | Vert.x `AddressResolver` that queries Consul health API |
| `ConsulEndpointResolver` | Resolves endpoints from Consul catalog/health responses |

## 🤝 Contributing

Issues and pull requests are welcome — please add tests for new resolver features.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## ConsulAddressResolver

[Java source](https://github.com/GuicedEE/ConsulServiceResolver/blob/master/src/main/java/com/guicedee/consul/resolver/ConsulAddressResolver.java)

### ConsulAddressResolver

```java
public ConsulAddressResolver(ConsulClient consulClient, boolean passingOnly);
@Override public EndpointResolver<ServiceAddress, ?, ?, ?> endpointResolver(Vertx vertx);
```

## ConsulEndpointResolver

[Java source](https://github.com/GuicedEE/ConsulServiceResolver/blob/master/src/main/java/com/guicedee/consul/resolver/ConsulEndpointResolver.java)

### ConsulEndpointResolver

```java
public ConsulEndpointResolver(Vertx vertx, ConsulClient client, boolean passingOnly);
@Override public ServiceAddress tryCast(Address address);
@Override public SocketAddress addressOf(SocketAddress server);
@Override public Future<ServiceEntryList> resolve(ServiceAddress address, EndpointBuilder<B, SocketAddress> builder);
@Override public B endpoint(ServiceEntryList state);
@Override public boolean isValid(ServiceEntryList state);
@Override public void dispose(ServiceEntryList state);
@Override public void close();
```

## ConsulServiceResolverProvider

[Java source](https://github.com/GuicedEE/ConsulServiceResolver/blob/master/src/main/java/com/guicedee/consul/resolver/ConsulServiceResolverProvider.java)

### ConsulServiceResolverProvider

```java
@Override public String type();
@Override public AddressResolver<ServiceAddress> create(String name, ServiceResolverOptions options);
@Override public Integer sortOrder();
```
