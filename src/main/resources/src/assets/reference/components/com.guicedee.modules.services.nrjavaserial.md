# com.guicedee.modules.services:nrjavaserial

nrjavaserial

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.neuronrobotics.nrjavaserial`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JNI/nrjavaserial/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.neuronrobotics:nrjavaserial`; scope `compile`; optional.
- `org.jspecify:jspecify`; scope `compile`.
- `com.guicedee.modules.services:jna-platform`; scope `compile`.
- `tools.jackson.core:jackson-core`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.

## JPMS descriptor

```java
module com.neuronrobotics.nrjavaserial {
    exports gnu.io;

    requires tools.jackson.core;
    requires transitive org.jspecify;

    requires com.fasterxml.jackson.annotation;

    requires com.sun.jna;

    opens gnu.io to com.google.guice, tools.jackson.databind;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## NRSerialPort

[Java source](https://github.com/GuicedEE/Services/blob/master/JNI/nrjavaserial/src/main/java/gnu/io/NRSerialPort.java)

### NRSerialPort

```java
public NRSerialPort();
public NRSerialPort(String port, int baud);
public boolean connect();
@JsonIgnore public InputStream getInputStream();
@JsonIgnore public OutputStream getOutputStream();
public void setPort(String port);
@SuppressWarnings("unchecked") public J disconnect();
public static Set<String> getAvailableSerialPorts();
public static Set<String> getAllWindowsPorts();
public boolean isConnected();
@SuppressWarnings("unchecked") @org.jspecify.annotations.NonNull public J setConnected(boolean connected);
@SuppressWarnings("unchecked") @org.jspecify.annotations.NonNull public J setBaud(int baud);
public int getBaud();
public int enableRs485(boolean busEnableActiveLow, int delayBusEnableBeforeSendMs, int delayBusEnableAfterSendMs);
public void notifyOnDataAvailable(boolean b);
public void addEventListener(SerialPortEventListener lsnr) throws TooManyListenersException;
public void removeEventListener();
@JsonIgnore public RXTXPort getSerialPortInstance();
```

## NRSerialPortException

[Java source](https://github.com/GuicedEE/Services/blob/master/JNI/nrjavaserial/src/main/java/gnu/io/NRSerialPortException.java)

### NRSerialPortException

```java
public NRSerialPortException();
public NRSerialPortException(String message);
public NRSerialPortException(String message, Throwable cause);
public NRSerialPortException(Throwable cause);
public NRSerialPortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```

## RXTXCommDriver

[Java source](https://github.com/GuicedEE/Services/blob/master/JNI/nrjavaserial/src/main/java/gnu/io/RXTXCommDriver.java)

### RXTXCommDriver

```java
public static String nativeGetVersionWrapper() throws UnsatisfiedLinkError;
public Set<String> getPortIdentifiers();
public void initialize();
public String[] windowsGetSerialPortsFromRegistry() throws Exception;
public CommPort getCommPort(String PortName, int PortType);
public void Report(String arg);
```
