# com.guicedee.modules.representations:excel-representation

Excel Representation

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.excelrepresentation`.
- [Source POM](https://github.com/GuicedEE/Representations/blob/master/excel/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee.modules.services:apache-poi-ooxml`; scope `compile`.
- `com.guicedee.modules.representations:json-representation`; scope `compile`.
- `org.projectlombok:lombok`; scope `compile`.
- `com.guicedee.modules.services:json`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.

## JPMS descriptor

```java
module com.guicedee.excelrepresentation {
    exports com.guicedee.modules.services.excelrepresentation;

    requires com.guicedee.jsonrepresentation;

    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    requires org.json;
    requires tools.jackson.databind;

    requires static lombok;
    requires java.logging;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Representations/blob/master/excel/README.md). Historical version examples retain their original context.

# ExcelRepresentation
Represents objects as Excel files

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## ExcelReader

[Java source](https://github.com/GuicedEE/Representations/blob/master/excel/src/main/java/com/guicedee/services/excelrepresentation/ExcelReader.java)

### ExcelReader

```java
public ExcelReader(InputStream inputStream, String extension) throws ExcelRenderingException;
public ExcelReader(InputStream inputStream, String extension, int sheet) throws ExcelRenderingException;
public Workbook getWorkbook();
public void writeHeader(List<String> headers);
public Object[][] fetchRows(int sheetNumber, int start, int records);
public int getColCount(int sheetNo);
public int getRowCount(int sheetNo);
public void writeRow(int rowNumber, List<String> headers);
@SuppressWarnings("ConstantConditions") public void writeRow(int rowNumber, Object[] rowData);
public Row getRow(int rowNumber);
public Cell getCell(int rowNumber, int cellNumber);
public byte[] get();
public <T> List<T> getRecords(String sheetName, Class<T> type);
@Override public void close() throws Exception;
```

## ExcelRenderingException

[Java source](https://github.com/GuicedEE/Representations/blob/master/excel/src/main/java/com/guicedee/services/excelrepresentation/ExcelRenderingException.java)

### ExcelRenderingException

```java
public ExcelRenderingException();
public ExcelRenderingException(String message);
public ExcelRenderingException(String message, Throwable cause);
public ExcelRenderingException(Throwable cause);
public ExcelRenderingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```

## IExcelRepresentation

[Java source](https://github.com/GuicedEE/Representations/blob/master/excel/src/main/java/com/guicedee/services/excelrepresentation/IExcelRepresentation.java)

### IExcelRepresentation

```java
default <T> List<T> fromExcel(InputStream stream, Class<T> objectType, String sheetName) throws ExcelRenderingException;
default String toExcel();
```
