# com.guicedee.modules.services:cxf-rt-frontend-jaxrs

Apache CXF Runtime JAX-RS Frontend

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.cxf.rest`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/CXF/apache-cxf-rest/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.apache.cxf:cxf-rt-frontend-jaxrs`; scope `compile`; optional.
- `com.guicedee.modules.services:org.apache.cxf`; scope `compile`.
- `com.guicedee.modules.services:apache-cxf-rt-transports-http`; scope `compile`.
- `com.guicedee.modules.services:apache-cxf-rt-security`; scope `compile`.
- `org.apache.cxf:cxf-rt-rs-http-sci`; scope `compile`.
- `org.slf4j:slf4j-api`; scope `compile`.
- `org.apache.commons:commons-lang3`; scope `compile`.
- `jakarta.xml.ws:jakarta.xml.ws-api`; scope `compile`.
- `jakarta.xml.bind:jakarta.xml.bind-api`; scope `compile`.
- `jakarta.validation:jakarta.validation-api`; scope `compile`.
- `jakarta.annotation:jakarta.annotation-api`; scope `compile`.
- `com.fasterxml.woodstox:woodstox-core`; scope `compile`.
- `org.apache.ws.xmlschema:xmlschema-core`; scope `compile`; optional.
- `jakarta.ws.rs:jakarta.ws.rs-api`; scope `compile`.
- `tools.jackson.jakarta.rs:jackson-jakarta-rs-json-provider`; scope `compile`.
- `org.springframework:spring-beans`; scope `provided`; optional.
- `org.springframework:spring-core`; scope `provided`; optional.
- `org.springframework:spring-context`; scope `provided`; optional.
- `org.springframework:spring-web`; scope `provided`; optional.
- `org.springframework:spring-webmvc`; scope `provided`; optional.

## JPMS descriptor

```java
module org.apache.cxf.rest {
    requires org.apache.cxf;

    requires org.apache.cxf.rt.transports.http;
    requires org.apache.cxf.rt.security;

    requires tools.jackson.jakarta.rs.json;

    exports org.apache.cxf.jaxrs;
    opens org.apache.cxf.jaxrs;

    exports org.apache.cxf.jaxrs.interceptor;
    opens org.apache.cxf.jaxrs.interceptor;
    exports org.apache.cxf.jaxrs.lifecycle;
    exports org.apache.cxf.jaxrs.model;
    exports org.apache.cxf.jaxrs.nio;
    exports org.apache.cxf.jaxrs.provider;
    opens org.apache.cxf.jaxrs.provider;
    exports org.apache.cxf.jaxrs.security;
    exports org.apache.cxf.jaxrs.servlet;
    exports org.apache.cxf.jaxrs.validation;
    exports org.apache.cxf.jaxrs.utils;
    opens org.apache.cxf.jaxrs.utils;

    exports org.apache.cxf.jaxrs.servlet.sci;

    uses org.apache.cxf.jaxrs.ext.ContextResolver;

    provides jakarta.ws.rs.ext.RuntimeDelegate with org.apache.cxf.jaxrs.impl.RuntimeDelegateImpl;
    provides jakarta.servlet.ServletContainerInitializer with org.apache.cxf.jaxrs.servlet.sci.JaxrsServletContainerInitializer;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
