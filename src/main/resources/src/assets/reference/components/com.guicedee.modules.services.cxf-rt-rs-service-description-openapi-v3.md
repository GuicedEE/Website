# com.guicedee.modules.services:cxf-rt-rs-service-description-openapi-v3

Apache CXF JAX-RS Service Description OpenAPI v3

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.cxf.rest.openapi`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/CXF/apache-cxf-rest-openapi/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.apache.cxf:cxf-rt-rs-service-description-openapi-v3`; scope `compile`; optional.
- `org.apache.cxf:cxf-rt-rs-service-description-common-openapi`; scope `compile`; optional.
- `org.apache.cxf:cxf-rt-rs-service-description-swagger-ui`; scope `compile`; optional.
- `com.guicedee.modules.services:swagger`; scope `compile`.
- `com.guicedee.modules.services:org.apache.cxf`; scope `compile`.
- `com.guicedee.modules.services:cxf-rt-frontend-jaxrs`; scope `compile`.
- `org.apache.cxf:cxf-rt-rs-json-basic`; scope `provided`; optional.

## JPMS descriptor

```java
module org.apache.cxf.rest.openapi {
    requires org.apache.cxf.rest;
    requires org.apache.cxf;

    requires transitive com.guicedee.modules.services.openapi;

    exports org.apache.cxf.jaxrs.openapi;
    exports org.apache.cxf.jaxrs.common.openapi;
    exports org.apache.cxf.jaxrs.swagger.ui;
    exports org.apache.cxf.jaxrs.openapi.parse;

    provides io.swagger.v3.rest.ext.OpenAPIExtension with org.apache.cxf.jaxrs.openapi.JaxRs2Extension;

}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
