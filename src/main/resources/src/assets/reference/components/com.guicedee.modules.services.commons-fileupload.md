# com.guicedee.modules.services:commons-fileupload

The Apache Commons FileUpload component provides a simple yet flexible means of adding support for multipart file upload functionality to servlets and web applications.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.commons.fileupload`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/Commons/commons-fileupload/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `jakarta.servlet:jakarta.servlet-api`; scope `compile`.
- `commons-io:commons-io`; scope `compile`.
- `portlet-api:portlet-api`; scope `compile`; optional.
- `commons-fileupload:commons-fileupload`; scope `compile`; optional.

## JPMS descriptor

```java
module org.apache.commons.fileupload {
    exports org.apache.commons.fileupload;
    exports org.apache.commons.fileupload.disk;
    exports org.apache.commons.fileupload.servlet;

    requires transitive jakarta.servlet;

    requires transitive org.apache.commons.io;

}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
