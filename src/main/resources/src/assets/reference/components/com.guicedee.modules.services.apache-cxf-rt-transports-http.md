# com.guicedee.modules.services:apache-cxf-rt-transports-http

Apache CXF Runtime HTTP Transport

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.cxf.rt.transports.http`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/CXF/apache-cxf-rt-transports-http/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.apache.cxf:cxf-rt-transports-http`; scope `compile`; optional.
- `org.apache.neethi:neethi`; scope `compile`; optional.
- `jakarta.servlet:jakarta.servlet-api`; scope `compile`.
- `jakarta.xml.bind:jakarta.xml.bind-api`; scope `compile`.
- `jakarta.activation:jakarta.activation-api`; scope `compile`.
- `com.guicedee.modules.services:org.apache.cxf`; scope `compile`.
- `org.slf4j:slf4j-api`; scope `compile`.
- `jakarta.xml.ws:jakarta.xml.ws-api`; scope `compile`.
- `org.apache.cxf:cxf-rt-wsdl`; scope `provided`; optional.
- `org.apache.cxf:cxf-rt-ws-policy`; scope `provided`; optional.
- `org.springframework:spring-beans`; scope `compile`; optional.
- `org.springframework:spring-core`; scope `compile`; optional.
- `org.springframework:spring-context`; scope `compile`; optional.
- `org.springframework:spring-web`; scope `compile`; optional.

## JPMS descriptor

```java
module org.apache.cxf.rt.transports.http {
    requires org.apache.cxf;

    requires java.desktop;

    requires jakarta.servlet;
    requires jakarta.xml.bind;

    //exports org.apache.cxf.transport.commons_text;
    exports org.apache.cxf.transport.http;
    exports org.apache.cxf.transport.http.auth;
    exports org.apache.cxf.transport.http.policy;
    exports org.apache.cxf.transport.http.policy.impl;

    exports org.apache.cxf.transport.https;
    exports org.apache.cxf.transport.https.httpclient;

    exports org.apache.cxf.transport.servlet;
    opens org.apache.cxf.transport.servlet;
    exports org.apache.cxf.transport.servlet.servicelist;

    exports org.apache.cxf.transports.http.configuration;

}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
