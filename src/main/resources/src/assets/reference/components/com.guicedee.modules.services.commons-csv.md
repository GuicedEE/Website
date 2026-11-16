# com.guicedee.modules.services:commons-csv

The Apache Commons CSV library provides a simple interface for reading and writing CSV files of various types.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.commons.csv`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/Commons/commons-csv/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.apache.commons:commons-csv`; scope `compile`; optional.

## JPMS descriptor

```java
module org.apache.commons.csv {
    exports org.apache.commons.csv;


    requires static java.sql;
    requires static org.apache.commons.io;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
