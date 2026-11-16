# com.guicedee.modules.services:apache-poi

SparseBitSet, Math, Codec and Collections still need module-info files. Can't use POI Modularity as it is right now

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.poi.poi`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/POI/apache-poi/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `commons-logging:commons-logging`; scope `compile`.
- `commons-codec:commons-codec`; scope `compile`.
- `com.guicedee.modules.services:commons-math3`; scope `compile`.
- `com.guicedee.modules.services:commons-collections4`; scope `compile`.
- `jakarta.xml.bind:jakarta.xml.bind-api`; scope `compile`.
- `jakarta.validation:jakarta.validation-api`; scope `compile`.
- `org.apache.logging.log4j:log4j-api`; scope `compile`.
- `org.apache.poi:poi`; scope `compile`; optional.

## JPMS descriptor

```java
module org.apache.poi.poi {
    requires org.apache.commons.collections4;
    requires org.apache.commons.codec;
    requires org.apache.commons.io;
    requires org.apache.commons.math3;
    //requires com.zaxxer.sparsebitset;
    requires org.apache.logging.log4j;
    requires java.logging;
    requires java.desktop;
    requires jdk.unsupported;

    exports org.apache.poi;
    exports org.apache.poi.logging;
    exports org.apache.poi.common;
    exports org.apache.poi.common.usermodel;
    exports org.apache.poi.common.usermodel.fonts;
    exports org.apache.poi.ddf;
    exports org.apache.poi.extractor;
    exports org.apache.poi.hpsf;
    exports org.apache.poi.hpsf.extractor;
    exports org.apache.poi.hpsf.wellknown;
    exports org.apache.poi.hssf;
    exports org.apache.poi.hssf.eventmodel;
    exports org.apache.poi.hssf.eventusermodel;
    exports org.apache.poi.hssf.eventusermodel.dummyrecord;
    exports org.apache.poi.hssf.extractor;
    exports org.apache.poi.hssf.model;
    exports org.apache.poi.hssf.record;
    exports org.apache.poi.hssf.record.aggregates;
    exports org.apache.poi.hssf.record.cf;
    exports org.apache.poi.hssf.record.chart;
    exports org.apache.poi.hssf.record.common;
    exports org.apache.poi.hssf.record.cont;
    exports org.apache.poi.hssf.record.crypto;
    exports org.apache.poi.hssf.record.pivottable;
    exports org.apache.poi.hssf.usermodel;
    exports org.apache.poi.hssf.usermodel.helpers;
    exports org.apache.poi.hssf.util;
    exports org.apache.poi.poifs.common;
    exports org.apache.poi.poifs.crypt;
    exports org.apache.poi.poifs.crypt.agile;
    exports org.apache.poi.poifs.crypt.binaryrc4;
    exports org.apache.poi.poifs.crypt.cryptoapi;
    exports org.apache.poi.poifs.crypt.standard;
    exports org.apache.poi.poifs.crypt.xor;
    exports org.apache.poi.poifs.dev;
    exports org.apache.poi.poifs.eventfilesystem;
    exports org.apache.poi.poifs.filesystem;
    exports org.apache.poi.poifs.macros;
    exports org.apache.poi.poifs.nio;
    exports org.apache.poi.poifs.property;
    exports org.apache.poi.poifs.storage;
    exports org.apache.poi.sl.draw;
    exports org.apache.poi.sl.draw.geom;
    exports org.apache.poi.sl.extractor;
    exports org.apache.poi.sl.image;
    exports org.apache.poi.sl.usermodel;
    exports org.apache.poi.ss;
    exports org.apache.poi.ss.extractor;
    exports org.apache.poi.ss.format;
    exports org.apache.poi.ss.formula;
    exports org.apache.poi.ss.formula.atp;
    exports org.apache.poi.ss.formula.constant;
    exports org.apache.poi.ss.formula.eval;
    exports org.apache.poi.ss.formula.eval.forked;
    exports org.apache.poi.ss.formula.function;
    exports org.apache.poi.ss.formula.functions;
    exports org.apache.poi.ss.formula.ptg;
    exports org.apache.poi.ss.formula.udf;
    exports org.apache.poi.ss.usermodel;
    exports org.apache.poi.ss.usermodel.helpers;
    exports org.apache.poi.ss.util;
    exports org.apache.poi.ss.util.cellwalk;
    exports org.apache.poi.util;
    exports org.apache.poi.wp.usermodel;

    uses org.apache.poi.extractor.ExtractorProvider;
    uses org.apache.poi.ss.usermodel.WorkbookProvider;
    uses org.apache.poi.sl.usermodel.SlideShowProvider;
    uses org.apache.poi.sl.draw.ImageRenderer;

    provides org.apache.poi.extractor.ExtractorProvider with
            org.apache.poi.extractor.MainExtractorFactory;
    provides org.apache.poi.ss.usermodel.WorkbookProvider with
            org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
    provides org.apache.poi.sl.draw.ImageRenderer with
            org.apache.poi.sl.draw.BitmapImageRenderer;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
