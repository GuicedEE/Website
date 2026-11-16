# com.guicedee:web-services

An integration library for Apache CXF SOAP web services on Vert.x inside the GuicedEE framework

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.webservices`.
- [Source POM](https://github.com/GuicedEE/WebServices/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee:web`; scope `compile`.
- `com.guicedee:client`; scope `compile`.
- `com.guicedee.modules.services:org.apache.cxf`; scope `compile`.
- `org.glassfish.jaxb:jaxb-runtime`; scope `compile`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `org.junit.jupiter:junit-jupiter-api`; scope `test`.
- `com.guicedee:inject`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.client.services.lifecycle.IGuiceConfigurator;
import com.guicedee.client.services.lifecycle.IGuicePostStartup;
import com.guicedee.webservices.implementations.WebServiceRouterConfigurator;
import com.guicedee.webservices.implementations.WebServiceScannerConfig;
import com.guicedee.webservices.services.WebServicePostStartup;

module com.guicedee.webservices {
    requires transitive com.guicedee.vertx.web;
    requires transitive org.apache.cxf;
    requires java.logging;

    requires org.apache.logging.log4j;
    requires static lombok;

    exports com.guicedee.webservices;
    exports com.guicedee.webservices.implementations;
    exports com.guicedee.webservices.services;
    exports com.guicedee.webservices.transport;

    provides IGuiceConfigurator with WebServiceScannerConfig;
    provides IGuicePostStartup with WebServicePostStartup;
    provides com.guicedee.vertx.web.spi.VertxRouterConfigurator with WebServiceRouterConfigurator;

    opens com.guicedee.webservices to com.google.guice;
    opens com.guicedee.webservices.implementations to com.google.guice;
    opens com.guicedee.webservices.services to com.google.guice;
    opens com.guicedee.webservices.transport to com.google.guice, org.apache.cxf;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/WebServices/blob/master/README.md). Historical version examples retain their original context.

# 🧾 GuicedEE Web Services (Apache CXF‑style)

[![JDK](https://img.shields.io/badge/JDK-25%2B-0A7?logo=java)](https://openjdk.org/projects/jdk/25/)
[![Build](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

GuicedEE Web Services uses the same annotations and configuration model as Apache CXF. You build SOAP services using standard JAX‑WS annotations (`@WebService`, `@WebMethod`, `@WebParam`, `@WebResult`) and configure endpoints, bindings, interceptors, and WS‑Security in the same way you would with CXF.

This README shows—end‑to‑end—how to create, configure, and expose a SOAP web service in a GuicedEE application.

## ✨ Features
- JAX‑WS (code‑first or WSDL‑first) with Apache CXF conventions
- Familiar CXF configuration: addresses, bindings (SOAP 1.1/1.2), interceptors, logging, MTOM, WS‑Security (WSS4J)
- Guice DI friendly: bind implementations and bootstrap endpoints via SPI/lifecycle
- Works alongside GuicedEE Vert.x Web and REST/OpenAPI modules
- JPMS + ServiceLoader discovery for configurators/providers

## 📦 Install (Maven)
```
<dependency>
  <groupId>com.guicedee</groupId>
  <artifactId>guiced-webservices</artifactId>
</dependency>

<!-- If you generate from WSDL, add CXF codegen plugin in your host app -->
<plugin>
  <groupId>org.apache.cxf</groupId>
  <artifactId>cxf-codegen-plugin</artifactId>
  <version>${cxf.version}</version>
  <executions>
    <execution>
      <id>wsdl2java</id>
      <phase>generate-sources</phase>
      <configuration>
        <wsdlOptions>
          <wsdlOption>
            <wsdl>${project.basedir}/src/main/resources/wsdl/hello.wsdl</wsdl>
            <extraargs>
              <extraarg>-exsh</extraarg>
              <extraarg>true</extraarg>
            </extraargs>
          </wsdlOption>
        </wsdlOptions>
      </configuration>
      <goals>
        <goal>wsdl2java</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

## 🚀 Quick Start (code‑first JAX‑WS)
1) Define the service contract and implementation using JAX‑WS annotations (CXF‑style):
```
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://example.com/hello", name = "HelloService")
public interface HelloService {
  @WebMethod(operationName = "sayHello")
  @WebResult(name = "greeting")
  String sayHello(@WebParam(name = "name") String name);
}

@WebService(endpointInterface = "com.example.ws.HelloService",
            serviceName = "HelloService",
            portName = "HelloPort",
            targetNamespace = "http://example.com/hello")
public class HelloServiceImpl implements HelloService {
  @Override
  public String sayHello(String name) { return "Hello " + name; }
}
```

2) Bind your implementation with Guice and publish the endpoint during startup. You can publish using JAX‑WS `Endpoint` or CXF’s `JaxWsServerFactoryBean`—both are supported configurations in CXF:
```
// A GuicedEE post‑startup hook
public final class WebServicesStartup implements com.guicedee.client.IGuicePostStartup {
  @jakarta.inject.Inject HelloServiceImpl hello;

  @Override
  public void onPostStartup() {
    // Option A: JAX‑WS Endpoint (CXF will wire the transport)
    jakarta.xml.ws.Endpoint.publish("/services/hello", hello);

    // Option B: CXF factory (for advanced settings)
    // org.apache.cxf.jaxws.JaxWsServerFactoryBean factory = new org.apache.cxf.jaxws.JaxWsServerFactoryBean();
    // factory.setServiceBean(hello);
    // factory.setAddress("/services/hello");
    // factory.setBindingId(org.apache.cxf.binding.soap.SoapBindingConstants.SOAP11_BINDING_ID);
    // factory.create();
  }
}
```

3) Start your GuicedEE application; the endpoint will be available at the configured address (see Configuration below for base URL/host configuration).

## 🧰 WSDL‑first (optional)
If you start from WSDL, run CXF’s `wsdl2java` (plugin shown in Install). Implement the generated SEI interface in your class and publish as above. Keep the WSDL under `src/main/resources/wsdl/` and consider exposing it under `?wsdl` via CXF settings.

## ⚙️ Configuration (Apache CXF conventions)
Typical environment keys (align with your global config rules):
```
WS_BASE_ADDRESS=http://0.0.0.0:8080            # base HTTP address
WS_CONTEXT_PATH=/services                      # root path for SOAP services
WS_HELLO_PATH=/hello                           # service‑specific path
WS_SOAP_VERSION=1.1                             # 1.1 or 1.2
WS_LOGGING_ENABLED=true                         # CXF logging interceptors
WS_MATOM_ENABLED=false                          # MTOM attachments
WS_WSDL_LOCATION=classpath:wsdl/hello.wsdl      # optional for WSDL‑first
WS_SECURITY_POLICY=classpath:ws/security.xml    # WS‑Security policy file
```

Interceptor and security examples (mirror CXF patterns):
```
// Example of adding interceptors when using JaxWsServerFactoryBean
org.apache.cxf.jaxws.JaxWsServerFactoryBean factory = new org.apache.cxf.jaxws.JaxWsServerFactoryBean();
factory.setServiceBean(hello);
factory.setAddress(System.getenv().getOrDefault("WS_CONTEXT_PATH", "/services") +
                   System.getenv().getOrDefault("WS_HELLO_PATH", "/hello"));

if (Boolean.parseBoolean(System.getenv().getOrDefault("WS_LOGGING_ENABLED", "true"))) {
  factory.getInInterceptors().add(new org.apache.cxf.interceptor.LoggingInInterceptor());
  factory.getOutInterceptors().add(new org.apache.cxf.interceptor.LoggingOutInterceptor());
}

// WS‑Security (WSS4J) example
// Map<String,Object> props = new HashMap<>();
// props.put("action", "UsernameToken Timestamp");
// props.put("passwordType", "PasswordText");
// props.put("passwordCallbackClass", "com.example.ws.security.ServerPasswordCallback");
// factory.getInInterceptors().add(new org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor(props));
// factory.create();
```

MTOM (attachments):
```
// Enable MTOM on binding
// factory.getProperties().put("mtom-enabled", Boolean.TRUE);
// Or annotate @MTOM on your endpoint implementation
```

SOAP 1.2 binding:
```
// factory.setBindingId(org.apache.cxf.binding.soap.SoapBindingConstants.SOAP12_BINDING_ID);
```

Vertex/HTTP hosting notes:
- If you host HTTP with GuicedEE Vert.x Web, expose CXF endpoints under the same HTTP server. Use consistent context paths so `/services/*` routes through to CXF.
- TLS/HTTPS is configured at the Vert.x Web server level (see GuicedEE Web). CXF inherits the transport from the server container.

## ✅ Testing your service
Manual checks:
```
# Retrieve WSDL
curl -sSf http://localhost:8080/services/hello?wsdl | head -n 20

# SoapUI / Postman
- Import the WSDL and invoke sayHello(name)
```

Generated client (CXF):
```
// Using wsdl2java generated stubs
HelloService client = new HelloService_Service().getHelloPort();
String result = client.sayHello("World");
```

## 🧩 JPMS & SPI
- JPMS‑ready; if you publish SPI/lifecycle hooks, register them via `META-INF/services` and (optionally) `module-info.java` provides entries.
- Common SPI you may implement in this module: GuicedEE post‑startup to bootstrap endpoints; route/config providers.

## 📚 Docs & Rules
- Rules: `RULES.md`
- Guides: `GUIDES.md`
- Architecture: `docs/architecture/README.md`
- CXF Docs (reference): https://cxf.apache.org/docs/

## 🧰 Troubleshooting
- `module not found: tools.jackson.databind`: rebuild/install the corrected `GuicedEE/services/Apache/CXF/apache-cxf` wrapper before building web-services. Older wrapper descriptors declared an unused Jackson requirement. The SOAP integration requires no Jackson or JSON representation dependency; applications needing JSON should opt into that integration separately.
- 404 on `?wsdl`: ensure the binding and address match and that the service is published before requests arrive.
- 415/Content‑Type errors: verify SOAPAction headers and that you’re using SOAP 1.1 vs 1.2 consistently across client/server.
- Interceptor ordering: logging/security interceptors should be added before `create()`.
- MTOM not working: enable MTOM on both server and client; check attachment size limits on the HTTP server.

## 🤝 Contributing
- Issues/PRs welcome. When adding new configuration knobs (security, interceptors, transports), update examples above and cross‑link RULES/GUIDES.

## 📝 License
- Apache 2.0 — see `LICENSE`.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## WebServiceRouterConfigurator

[Java source](https://github.com/GuicedEE/WebServices/blob/master/src/main/java/com/guicedee/webservices/implementations/WebServiceRouterConfigurator.java)

### WebServiceRouterConfigurator

```java
@Override public Router builder(Router router);
@Override public Integer sortOrder();
```

## WebServiceScannerConfig

[Java source](https://github.com/GuicedEE/WebServices/blob/master/src/main/java/com/guicedee/webservices/implementations/WebServiceScannerConfig.java)

### WebServiceScannerConfig

```java
@Override public IGuiceConfig<?> configure(IGuiceConfig<?> config);
```

## WebServicePostStartup

[Java source](https://github.com/GuicedEE/WebServices/blob/master/src/main/java/com/guicedee/webservices/services/WebServicePostStartup.java)

### WebServicePostStartup

```java
@Override public List<Uni<Boolean>> postLoad();
@Override public Integer sortOrder();
```

## VertxBackChannelConduit

[Java source](https://github.com/GuicedEE/WebServices/blob/master/src/main/java/com/guicedee/webservices/transport/VertxBackChannelConduit.java)

### VertxBackChannelConduit

```java
public VertxBackChannelConduit(Message inMessage);
@Override protected java.util.logging.Logger getLogger();
@Override public void prepare(Message message) throws IOException;
@Override public void close(Message message) throws IOException;
```

## VertxDestination

[Java source](https://github.com/GuicedEE/WebServices/blob/master/src/main/java/com/guicedee/webservices/transport/VertxDestination.java)

### VertxDestination

```java
public VertxDestination(Bus bus, EndpointReferenceType ref, EndpointInfo endpointInfo);
@Override protected java.util.logging.Logger getLogger();
@Override protected Conduit getInbuiltBackChannel(Message inMessage);
public void handleRequest(RoutingContext ctx);
public void handleWsdlRequest(RoutingContext ctx);
@Override public void shutdown();
```

## VertxTransportFactory

[Java source](https://github.com/GuicedEE/WebServices/blob/master/src/main/java/com/guicedee/webservices/transport/VertxTransportFactory.java)

### VertxTransportFactory

```java
public static final String TRANSPORT_ID;
public VertxTransportFactory();
@Override public Destination getDestination(EndpointInfo endpointInfo, Bus bus) throws IOException;
@Override public Set<String> getUriPrefixes();
public VertxDestination getDestinationForAddress(String address);
public Map<String, VertxDestination> getDestinations();
```

## WSContext

[Java source](https://github.com/GuicedEE/WebServices/blob/master/src/main/java/com/guicedee/webservices/WSContext.java)

### WSContext

```java
public static String baseWSUrl;
public static void registerEndpoint(Class<?> endpointClass);
public static Set<Class<?>> getRegisteredEndpoints();
public static void setTransportFactory(VertxTransportFactory factory);
public static VertxTransportFactory getTransportFactory();
public static String cleanPath(String path);
```
