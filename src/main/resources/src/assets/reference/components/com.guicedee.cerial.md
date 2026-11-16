# com.guicedee:cerial

GuicedEE serial-port utility exposing CRTP-fluent CerialPortConnection APIs on top of jSerialComm.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.cerial`.
- [Source POM](https://github.com/GuicedEE/Cerial/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.guicedee:health`; scope `compile`.
- `com.guicedee.modules.services:telemetry-core`; scope `compile`; optional.
- `com.guicedee:guiced-telemetry`; scope `compile`; optional.
- `com.fazecast:jSerialComm`; scope `compile`.
- `org.apache.logging.log4j:log4j-core`; scope `compile`.
- `com.guicedee:vertx`; scope `compile`.
- `com.guicedee.modules.representations:json-representation`; scope `compile`.
- `org.projectlombok:lombok`; scope `compile`.
- `com.guicedee:client`; scope `compile`.
- `com.guicedee:inject`; scope `compile`.
- `org.apache.commons:commons-lang3`; scope `compile`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `org.junit.jupiter:junit-jupiter-api`; scope `test`.
- `org.mockito:mockito-core`; scope `test`.
- `org.mockito:mockito-junit-jupiter`; scope `test`.
- `commons-io:commons-io`; scope `compile`.

## JPMS descriptor

```java
import com.guicedee.cerial.implementations.CerialPortsBindings;
import com.guicedee.cerial.implementations.CerialPreDestroy;
import com.guicedee.client.services.lifecycle.IGuiceModule;
import com.guicedee.client.services.lifecycle.IGuicePreDestroy;

/**
 * Serial port integration module that exposes core APIs, enumerations, and Guice bindings.
 */
module com.guicedee.cerial {
    requires static lombok;

    requires com.fasterxml.jackson.annotation;
    requires java.logging;

    requires org.apache.logging.log4j;
    requires io.vertx.core;
    requires org.apache.logging.log4j.core;
    requires org.apache.commons.io;
    requires com.guicedee.guicedinjection;

    requires static com.guicedee.health;
    requires static com.guicedee.telemetry;
    requires static com.guicedee.modules.services.opentelemetry;

    requires transitive com.fazecast.jSerialComm;
    requires transitive org.apache.commons.lang3;
    requires transitive com.guicedee.client;
    requires transitive com.guicedee.jsonrepresentation;

    exports com.guicedee.cerial;
    opens com.guicedee.cerial to com.google.guice,tools.jackson.databind,com.guicedee.health;
    opens com.guicedee.cerial.implementations to com.google.guice,tools.jackson.databind,com.guicedee.health;

    exports com.guicedee.cerial.enumerations;
    exports com.guicedee.cerial.implementations;

    provides IGuiceModule with CerialPortsBindings;
    provides IGuicePreDestroy with CerialPreDestroy;

}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Cerial/blob/master/README.md). Historical version examples retain their original context.

# GuicedEE Cerial

[![Build](https://github.com/GuicedEE/Cerial/actions/workflows/build.yml/badge.svg)](https://github.com/GuicedEE/Cerial/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.guicedee/cerial)](https://central.sonatype.com/artifact/com.guicedee/cerial)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Guice 7](https://img.shields.io/badge/Guice-7%2B-green)
![Vert.X 5](https://img.shields.io/badge/Vert.x-5%2B-green)

Lifecycle-aware **serial port connectivity** for [GuicedEE](https://github.com/GuicedEE) applications using **jSerialComm** and **Vert.x 5**.
Inject `@Named` `CerialPortConnection` singletons by port number, configure with CRTP-fluent setters, and let the framework handle connection lifecycle, idle monitoring, automatic reconnect with exponential backoff, MicroProfile Health reporting, and optional OpenTelemetry tracing.

Built on [jSerialComm](https://fazecast.github.io/jSerialComm/) · [Vert.x](https://vertx.io/) · [Google Guice](https://github.com/google/guice) · [MicroProfile Health](https://github.com/eclipse/microprofile-health) · JPMS module `com.guicedee.cerial` · Java 25+

## 📦 Installation

```xml
<dependency>
  <groupId>com.guicedee</groupId>
  <artifactId>cerial</artifactId>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.guicedee:cerial:2.4.1")
```
</details>

## ✨ Features

- **Guice-managed port singletons** — `CerialPortsBindings` pre-binds ports 0–999 as `@Named("0")` through `@Named("999")` singletons; inject by port number
- **CRTP-fluent configuration** — chain `setBaudRate()`, `setDataBits()`, `setParity()`, `setStopBits()`, `setFlowControl()` and call `connect()` — all methods return `this`
- **Idle monitoring** — `CerialIdleMonitor` uses a Vert.x periodic timer to detect silent connections and update status to `Silent`
- **Automatic reconnect** — exponential backoff reconnect (1 s → 60 s cap) via Vert.x timers on connection failure or port loss
- **Status lifecycle** — rich `ComPortStatus` enum with 12 states (`Offline`, `Silent`, `Running`, `Missing`, `Failed`, etc.) and grouped `EnumSet` helpers
- **Message-delimited reads** — `DataSerialPortMessageListener` uses jSerialComm's `SerialPortMessageListener` for delimiter-based framing (default: `\n`)
- **Byte-level reads** — `DataSerialPortBytesListener` for raw byte-array reception
- **Per-port logging** — each connection gets its own Log4j2 rolling file logger under `cerial/`
- **Health check** — `CerialHealthCheck` implements `@Liveness`, `@Readiness`, `@Startup` and reports status of all active connections via the [health](https://github.com/GuicedEE/Cerial/blob/health) module
- **Connection registry** — `CerialConnectionRegistry` tracks all active connections for health and lifecycle management
- **OpenTelemetry tracing** — optional `@Trace` spans on `connect()`, `disconnect()`, read, and write with `serial.port`, `serial.bytes_read`, and `serial.bytes_written` metrics (requires `guiced-telemetry`)
- **Cross-platform** — COM ports on Windows (`COM1`), USB serial on Linux (`/dev/ttyUSB0`)
- **JSpecify nullability** — `@NonNull` annotations on all public fluent setters
- **JSON serializable** — `CerialPortConnection` implements `IJsonRepresentation` with Jackson annotations
- **Graceful shutdown** — `IGuicePreDestroy` integration closes all open ports when the context tears down

## 🚀 Quick Start

**Step 1** — Inject a named connection by port number:

```java
@Inject
@Named("1")
private CerialPortConnection connection;
```

**Step 2** — Configure and connect:

```java
connection.setBaudRate(BaudRate.$9600)
          .setDataBits(DataBits.$8)
          .setParity(Parity.None)
          .setStopBits(StopBits.$1)
          .setFlowControl(FlowControl.None)
          .connect();
```

**Step 3** — Send and receive data:

```java
// Write
connection.write("Hello, device!");

// Read (callback on Vert.x worker thread)
connection.setComPortRead((data, port) -> {
    String message = new String(data).trim();
    System.out.println("Received: " + message);
});
```

**Step 4** — Monitor status changes:

```java
connection.onComPortStatusUpdate((conn, status) -> {
    System.out.println("Port " + conn.getComPort() + " → " + status);
});
```

No JPMS `provides` declarations are needed for consuming code — the module is registered automatically via its own `IGuiceModule` provider.

## 📐 Startup Flow

```
IGuiceContext.instance()
 └─ Guice injector created
     └─ CerialPortsBindings.configure()
         └─ Bind @Named("0")..@Named("999") → CerialPortConnectionProvider (Singleton)
 └─ First injection of @Named("N") CerialPortConnection
     └─ CerialPortConnectionProvider.get()
         ├─ new CerialPortConnection(N, BaudRate.$9600)
         ├─ SerialPort.getCommPort("COMN")
         ├─ DataSerialPortMessageListener created
         ├─ CerialIdleMonitor created
         ├─ CerialConnectionRegistry.register(this)
         └─ Register as IGuicePreDestroy
 └─ connection.connect()
     ├─ beforeConnect() → Guice injects members, configure port parameters
     ├─ SerialPort.openPort()
     ├─ afterConnect() → attach data listener, start idle monitor
     ├─ Register shutdown hook
     └─ Status → Silent
```

## 🔌 Connection Lifecycle

### Status states

`ComPortStatus` provides 12 states with UI metadata (icon, background/foreground classes):

| Status | Meaning | Group |
|---|---|---|
| `Offline` | Port is closed or disconnected | Exception |
| `Missing` | Port not detected on the system | Exception |
| `Failed` | Connection attempt failed | Exception |
| `InUse` | Port claimed by another process | Exception |
| `GeneralException` | Unhandled error | Exception |
| `Opening` | Port is initializing | Transitional |
| `OperationInProgress` | Long-running operation active | Transitional |
| `FileTransfer` | Bulk data transfer underway | Transitional |
| `Silent` | Connected but idle | Active |
| `Logging` | Connected and logging telemetry | Active |
| `Running` | Connected and actively communicating | Active |
| `Simulation` | Running in simulated mode | Active |

Grouped `EnumSet` helpers: `exceptionOperations`, `pauseOperations`, `portActive`, `portOffline`, `onlineServerStatus`.

### Reconnect with exponential backoff

When a connection fails or is lost, `scheduleReconnect()` uses a Vert.x timer with exponential backoff:

- Starts at `initialReconnectDelaySeconds` (default 1 s)
- Doubles each attempt: 1 s → 2 s → 4 s → 8 s → …
- Capped at `maxReconnectDelaySeconds` (default 60 s)
- Resets on successful reconnect

```java
connection.setInitialReconnectDelaySeconds(2)
          .setMaxReconnectDelaySeconds(30);
```

### Idle monitoring

`CerialIdleMonitor` runs a Vert.x periodic timer (default every 120 s) that checks `lastMessageTime`. If idle beyond the threshold, the status transitions to `Silent`:

```java
// Custom idle detection: check every 30s, idle after 60s
connection.setIdleTimerSeconds(60);
connection.setMonitor(new CerialIdleMonitor(connection, 2, 30, 60));
```

### Error handling

Register an error callback for connection failures:

```java
connection.setComPortError((throwable, conn, status) -> {
    log.error("Port {} error: {} → {}", conn.getComPort(), throwable.getMessage(), status);
});
```

If no error callback is set, the status is updated automatically and reconnect is scheduled.

## ⚙️ Configuration

### Serial port parameters

| Method | Default | Values |
|---|---|---|
| `setBaudRate()` | `BaudRate.$9600` | `$300`, `$600`, `$1200`, `$4800`, `$9600`, `$14400`, `$19200`, `$38400`, `$57600`, `$115200`, `$128000`, `$256000` |
| `setDataBits()` | `DataBits.$8` | `$5`, `$6`, `$7`, `$8` |
| `setParity()` | `Parity.None` | `None`, `Odd`, `Even`, `Mark`, `Space` |
| `setStopBits()` | `StopBits.$1` | `$1`, `$1_5`, `$2` |
| `setFlowControl()` | `FlowControl.None` | `None`, `RtsCtsIn`, `RtsCtsOut`, `XonXoffIn`, `XonXoffOut` |

### Flow type

Set an overall flow type that configures the underlying jSerialComm flags:

```java
connection.setFlow(FlowType.XONXOFF);  // or FlowType.RTSCTS, FlowType.None
```

### Message delimiter

```java
connection.setEndOfMessage(new char[]{'\r', '\n'});
```

### Buffer size

```java
connection.setBufferSize(2048);
```

## 🏥 Health Check

`CerialHealthCheck` is a `@Liveness`, `@Readiness`, `@Startup` MicroProfile Health check that reports the status of all active connections tracked by `CerialConnectionRegistry`:

```json
{
  "status": "UP",
  "checks": [{
    "id": "com.guicedee.cerial.CerialHealthCheck",
    "status": "UP",
    "data": {
      "COM1": "Running",
      "COM3": "Silent"
    }
  }]
}
```

A connection is **DOWN** if its status is `Offline`, `Missing`, `GeneralException`, `Failed`, or `InUse`. If no active connections exist, the check returns **UP** with `"No active connections"`.

## 📡 OpenTelemetry Integration

When `guiced-telemetry` is on the classpath, the module automatically:

- Creates `serial.bytes_written` and `serial.bytes_read` counters via the OpenTelemetry `Meter`
- Traces `connect()` and `disconnect()` calls with `@Trace` and `@SpanAttribute`
- Traces write operations via `CerialWriteTracer` with `serial.port`, `serial.portNumber`, `serial.message_length` span attributes
- Traces read operations via `CerialDataTracer` with `serial.data`, `serial.port`, `serial.message_length` span attributes

All telemetry dependencies are `requires static` — they are completely optional.

## 📝 Logging

Each connection creates a dedicated Log4j2 rolling file logger:

| Port | Logger name | File |
|---|---|---|
| COM1 | `COM1` | `cerial/COM1.log` |
| COM3 | `COM3` | `cerial/COM3.log` |
| (none) | `cerial` | `cerial/cerial.log` |

Log format: `[yyyy-MM-dd HH:mm:ss.SSS] [LEVEL] - [message]`

Messages use emoji prefixes for quick scanning:
- `📤 TX` — data sent
- `📥 RX` — data received
- `🚀` — port opening
- `✅` — success
- `❌` — error
- `⚠️` — disconnect/warning
- `🔄` — reconnect scheduled
- `🔌` — reconnect attempt

## 🗺️ Module Graph

```
com.guicedee.cerial
 ├── com.guicedee.guicedinjection   (GuicedEE runtime — scanning, Guice, lifecycle)
 ├── com.guicedee.client            (GuicedEE SPI contracts — IGuicePreDestroy, IGuiceModule)
 ├── com.guicedee.jsonrepresentation (JSON serialization — IJsonRepresentation)
 ├── com.fazecast.jSerialComm       (jSerialComm — serial port I/O)
 ├── io.vertx.core                  (Vert.x — timers for idle monitor and reconnect)
 ├── org.apache.commons.lang3       (Commons Lang — utility classes)
 ├── org.apache.commons.io          (Commons IO)
 ├── org.apache.logging.log4j       (Log4j2 — per-port rolling loggers)
 ├── com.guicedee.health            (optional — MicroProfile Health integration)
 └── com.guicedee.telemetry         (optional — OpenTelemetry tracing)
```

## 🧩 JPMS

Module name: **`com.guicedee.cerial`**

The module:
- **exports** `com.guicedee.cerial`, `com.guicedee.cerial.enumerations`, `com.guicedee.cerial.implementations`
- **provides** `IGuiceModule` with `CerialPortsBindings`
- **requires static** `com.guicedee.health` (optional health check integration)
- **requires static** `com.guicedee.telemetry` (optional OpenTelemetry tracing)

## 🏗️ Key Classes

| Class | Role |
|---|---|
| `CerialPortConnection<J>` | Core CRTP-fluent connection — configure, connect, write, receive, lifecycle |
| `CerialPortsBindings` | `IGuiceModule` — binds `@Named("0")`..`@Named("999")` to `CerialPortConnectionProvider` singletons |
| `CerialPortConnectionProvider` | Guice `Provider` — creates `CerialPortConnection` with default baud rate |
| `CerialConnectionRegistry` | Thread-safe registry of all active connections |
| `CerialIdleMonitor` | Vert.x periodic timer that detects idle connections |
| `CerialHealthCheck` | `@Liveness` + `@Readiness` + `@Startup` health check for all active connections |
| `CerialDataReceived` | Functional interface (`BiConsumer<byte[], CerialPortConnection>`) for read callbacks |
| `DataSerialPortMessageListener` | jSerialComm `SerialPortMessageListener` — delimiter-based message framing |
| `DataSerialPortBytesListener` | jSerialComm `SerialPortDataListener` — raw byte-array reception |
| `ComPortEvents` | SPI contract for read callbacks shared by message and byte listeners |
| `CerialDataTracer` | OpenTelemetry `@Trace` wrapper for read operations |
| `CerialWriteTracer` | OpenTelemetry `@Trace` wrapper for write operations |
| `SerialPortException` | `RuntimeException` for serial port errors |

### Enumerations

| Enum | Values |
|---|---|
| `BaudRate` | `$300` – `$256000` (12 rates) |
| `DataBits` | `$5`, `$6`, `$7`, `$8` |
| `Parity` | `None`, `Odd`, `Even`, `Mark`, `Space` |
| `StopBits` | `$1`, `$1_5`, `$2` |
| `FlowControl` | `None`, `RtsCtsIn`, `RtsCtsOut`, `XonXoffIn`, `XonXoffOut` |
| `FlowType` | `None`, `XONXOFF`, `RTSCTS` |
| `ComPortStatus` | 12 states with UI metadata (icon, CSS classes) |
| `ComPortType` | `Device`, etc. |

## 🤝 Contributing

Issues and pull requests are welcome — please include the serial port, OS, jSerialComm version, and log excerpt. Follow CRTP (no builders) and JSpecify nullness conventions.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## CerialConnectionRegistry

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/CerialConnectionRegistry.java)

### CerialConnectionRegistry

```java
public CerialConnectionRegistry();
public static void register(CerialPortConnection<?> connection);
public static void unregister(CerialPortConnection<?> connection);
public static Set<CerialPortConnection<?>> getActiveConnections();
```

## CerialDataReceived

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/CerialDataReceived.java)

### CerialDataReceived

```java
```

## CerialHealthCheck

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/CerialHealthCheck.java)

### CerialHealthCheck

```java
public CerialHealthCheck();
@Override public HealthCheckResponse call();
```

## CerialIdleMonitor

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/CerialIdleMonitor.java)

### CerialIdleMonitor

```java
public CerialIdleMonitor(CerialPortConnection<?> connection);
public CerialIdleMonitor(CerialPortConnection<?> connection, int initialDelay, int period, int seconds);
public void begin();
public void end();
```

## CerialPortConnection

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/CerialPortConnection.java)

### CerialPortConnection

```java
public static NumberFormat portNumberFormat;
public static void addBytesWritten(long bytes, String portName);
public static void addBytesRead(long bytes, String portName);
@org.jspecify.annotations.NonNull public J setConnectionPort(SerialPort connectionPort);
public J reset();
public CerialPortConnection(int comPort, BaudRate baudRate, int seconds);
@org.jspecify.annotations.NonNull public J setComPort(int comPort);
public org.apache.logging.log4j.core.Logger getLog();
public CerialPortConnection(int comPort, BaudRate baudRate);
public CerialIdleMonitor getMonitor();
@Trace @SpanAttribute("connection_status") public synchronized J connect();
@Trace @SpanAttribute("connection_status") public synchronized J disconnect();
protected synchronized J scheduleReconnect(String reason);
public J beforeConnect();
public J afterConnect();
@org.jspecify.annotations.NonNull public J setXOnXOff();
@org.jspecify.annotations.NonNull public J setRts();
@org.jspecify.annotations.NonNull public J setDsr();
public J onConnectError(Throwable e, ComPortStatus status);
public J onComPortStatusUpdate(BiConsumer<CerialPortConnection<?>, ComPortStatus> comPortStatusUpdate);
@org.jspecify.annotations.NonNull public J setComPortStatus(ComPortStatus comPortStatus, boolean... update);
protected J registerShutdownHook();
protected J afterShutdown();
protected J beforeShutdown();
protected J configure(SerialPort instance);
@JsonProperty public String getComPortName();
public void write(String message, boolean... checkForEndOfCharacter);
@Override public void onDestroy();
@org.jspecify.annotations.NonNull public J setComPort(Integer comPort);
@org.jspecify.annotations.NonNull public J setBaudRate(BaudRate baudRate);
@org.jspecify.annotations.NonNull public J setComPortStatus(ComPortStatus comPortStatus);
@org.jspecify.annotations.NonNull public J setComPortType(ComPortType comPortType);
@org.jspecify.annotations.NonNull public J setDataBits(DataBits dataBits);
@org.jspecify.annotations.NonNull public J setFlowControl(FlowControl flowControl);
@org.jspecify.annotations.NonNull public J setParity(Parity parity);
@org.jspecify.annotations.NonNull public J setStopBits(StopBits stopBits);
@org.jspecify.annotations.NonNull public J setBufferSize(Integer bufferSize);
@org.jspecify.annotations.NonNull public J setWriter(OutputStream writer);
@org.jspecify.annotations.NonNull public J setComPortStatusUpdate(BiConsumer<CerialPortConnection<?>, ComPortStatus> comPortStatusUpdate);
@org.jspecify.annotations.NonNull public J setComPortRead(BiConsumer<byte[], com.fazecast.jSerialComm.SerialPort> comPortRead);
@org.jspecify.annotations.NonNull public J setComPortError(TriConsumer<Throwable, CerialPortConnection<?>, ComPortStatus> comPortError);
@org.jspecify.annotations.NonNull public J setMonitor(CerialIdleMonitor monitor);
@org.jspecify.annotations.NonNull public J setLastMessageTime(LocalDateTime lastMessageTime);
```

## BaudRate

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/enumerations/BaudRate.java)

### BaudRate

```java
public static final BaudRate $300;
public static final BaudRate $600;
public static final BaudRate $1200;
public static final BaudRate $4800;
public static final BaudRate $9600;
public static final BaudRate $14400;
public static final BaudRate $19200;
public static final BaudRate $38400;
public static final BaudRate $57600;
public static final BaudRate $115200;
public static final BaudRate $128000;
public static final BaudRate $256000;
@Override public String toString();
public int toInt();
@JsonCreator public static BaudRate from(String s);
```

## ComPortStatus

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/enumerations/ComPortStatus.java)

### ComPortStatus

```java
public static final ComPortStatus GeneralException;
public static final ComPortStatus Missing;
public static final ComPortStatus Failed;
public static final ComPortStatus InUse;
public static final ComPortStatus Offline;
public static final ComPortStatus OperationInProgress;
public static final ComPortStatus FileTransfer;
public static final ComPortStatus Simulation;
public static final ComPortStatus Opening;
public static final ComPortStatus Silent;
public static final ComPortStatus Logging;
public static final ComPortStatus Running;
public static final EnumSet<ComPortStatus> exceptionOperations;
public static final EnumSet<ComPortStatus> pauseOperations;
public static final EnumSet<ComPortStatus> portActive;
public static final EnumSet<ComPortStatus> portOffline;
public static final EnumSet<ComPortStatus> onlineServerStatus;
public ComPortStatus setIcon(String icon);
public ComPortStatus setBackgroundClass(String backgroundClass);
public ComPortStatus setForegroundClass(String foregroundClass);
```

## ComPortType

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/enumerations/ComPortType.java)

### ComPortType

```java
public static final ComPortType Scanner;
public static final ComPortType Device;
public static final ComPortType Server;
public static final ComPortType PrinterPPLA;
public static final ComPortType PrinterPPLB;
public static final ComPortType PrinterPPLZ;
public static final ComPortType ScannerSim;
public static final ComPortType DeviceSim;
public static final EnumSet<ComPortType> deviceServerSim;
public static final EnumSet<ComPortType> deviceServer;
public static final EnumSet<ComPortType> deviceSim;
public static final EnumSet<ComPortType> scanners;
public static final EnumSet<ComPortType> scannerSim;
public static final EnumSet<ComPortType> scannersAndSim;
public static final EnumSet<ComPortType> printers;
```

## DataBits

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/enumerations/DataBits.java)

### DataBits

```java
public static final DataBits $5;
public static final DataBits $6;
public static final DataBits $7;
public static final DataBits $8;
public int toInt();
@Override public String toString();
@JsonCreator public static DataBits fromString(String s);
```

## FlowControl

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/enumerations/FlowControl.java)

### FlowControl

```java
public static final FlowControl None;
public static final FlowControl RTSCTS_IN;
public static final FlowControl RTSCTS_OUT;
public static final FlowControl XONXOFF_IN;
public static final FlowControl XONXOFF_OUT;
public int toInt();
```

## FlowType

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/enumerations/FlowType.java)

### FlowType

```java
public static final FlowType None;
public static final FlowType RTSCTS;
public static final FlowType XONXOFF;
```

## Parity

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/enumerations/Parity.java)

### Parity

```java
public static final Parity None;
public static final Parity Odd;
public static final Parity Even;
public static final Parity Mark;
public static final Parity Space;
public int toInt();
@JsonCreator public static Parity from(String value);
```

## StopBits

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/enumerations/StopBits.java)

### StopBits

```java
public static final StopBits $1;
public static final StopBits $2;
public static final StopBits $1_5;
public int toInt();
@JsonCreator public static StopBits from(String name);
```

## CerialDataTracer

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/implementations/CerialDataTracer.java)

### CerialDataTracer

```java
public CerialDataTracer();
public static java.lang.invoke.MethodHandles.Lookup getModuleLookup();
@Trace("Serial Receive") public void onDataReceived(@SpanAttribute("serial.data")
byte[] newData, CerialPortConnection<?> connection, BiConsumer<byte[], SerialPort> comPortRead);
```

## CerialPortConnectionProvider

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/implementations/CerialPortConnectionProvider.java)

### CerialPortConnectionProvider

```java
public CerialPortConnectionProvider(int comPortNumber);
@Override public CerialPortConnection get();
```

## CerialPortsBindings

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/implementations/CerialPortsBindings.java)

### CerialPortsBindings

```java
public CerialPortsBindings();
@Override protected void configure();
```

## CerialPreDestroy

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/implementations/CerialPreDestroy.java)

### CerialPreDestroy

```java
@Override public void onDestroy();
@Override public Integer sortOrder();
```

## CerialWriteTracer

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/implementations/CerialWriteTracer.java)

### CerialWriteTracer

```java
public CerialWriteTracer();
public static java.lang.invoke.MethodHandles.Lookup getModuleLookup();
@Trace("Serial Write") public void onWrite(@SpanAttribute("message")
String message, @SpanAttribute("serial.port")
String portName, @SpanAttribute("serial.portNumber")
Integer portNumber);
```

## ComPortEvents

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/implementations/ComPortEvents.java)

### ComPortEvents

```java
java.util.function.BiConsumer<byte[], com.fazecast.jSerialComm.SerialPort> getComPortRead();
SerialPortDataListener setComPortRead(java.util.function.BiConsumer<byte[], com.fazecast.jSerialComm.SerialPort> comPortRead);
```

## DataSerialPortBytesListener

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/implementations/DataSerialPortBytesListener.java)

### DataSerialPortBytesListener

```java
public DataSerialPortBytesListener(char[] delimiter, SerialPort comPort, CerialPortConnection<?> connection);
@Override public int getListeningEvents();
public byte[] remove(byte[] array, byte toRemove);
@Override public void serialEvent(SerialPortEvent event);
public void processReceivedBytes(byte[] newData);
@Override public void catchException(Exception e);
```

### Mode

```java
public static final Mode Delimeter;
public static final Mode Pattern;
public static final Mode Length;
public static final Mode All;
```

## DataSerialPortMessageListener

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/implementations/DataSerialPortMessageListener.java)

### DataSerialPortMessageListener

```java
public DataSerialPortMessageListener(char[] delimiter, SerialPort comPort, CerialPortConnection<?> connection);
@Override public byte[] getMessageDelimiter();
@Override public boolean delimiterIndicatesEndOfMessage();
@Override public int getListeningEvents();
public static byte[] remove(byte[] array, byte toRemove);
@Override public void serialEvent(SerialPortEvent event);
public void processReceivedBytes(byte[] newData);
```

## SerialPortException

[Java source](https://github.com/GuicedEE/Cerial/blob/master/src/main/java/com/guicedee/cerial/SerialPortException.java)

### SerialPortException

```java
public SerialPortException();
public SerialPortException(String message);
public SerialPortException(String message, Throwable cause);
public SerialPortException(Throwable cause);
public SerialPortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```
