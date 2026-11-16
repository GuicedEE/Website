# com.guicedee.modules.services:bson-record-codec

JPMS modularized shade of org.mongodb:bson-record-codec exposing the org.mongodb.bson.record.codec module. This is the artifact jlink reported first: "automatic module cannot be used with jlink: org.mongodb.bson.record.codec".

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.mongodb.bson.record.codec`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/bson-record-codec/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.mongodb:bson-record-codec`; scope `compile`; optional.
- `com.guicedee.modules.services:bson`; scope `compile`.

## JPMS descriptor

```java
module org.mongodb.bson.record.codec {
    // RecordCodecProvider's public API returns org.bson.codecs.Codec, so bson is part of
    // this module's exported surface and must be transitive.
    requires transitive org.mongodb.bson;

    exports org.bson.codecs.record;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
