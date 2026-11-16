# com.guicedee.modules.services:apache-cxf-rt-security

Apache CXF Runtime Security functionality

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.cxf.rt.security`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/CXF/apache-cxf-rt-security/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.apache.cxf:cxf-rt-security`; scope `compile`; optional.
- `com.guicedee.modules.services:org.apache.cxf`; scope `compile`.
- `org.slf4j:slf4j-api`; scope `compile`.
- `commons-logging:commons-logging`; scope `compile`.

## JPMS descriptor

```java
module org.apache.cxf.rt.security {
    requires org.apache.cxf;

    exports org.apache.cxf.rt.security;
    exports org.apache.cxf.rt.security.claims;
    exports org.apache.cxf.rt.security.claims.interceptor;


    exports org.apache.cxf.rt.security.crypto;
    exports org.apache.cxf.rt.security.rs;
    exports org.apache.cxf.rt.security.utils;

}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
