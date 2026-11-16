# com.guicedee:mail-client

Provides annotation-driven SMTP mail client integration for GuicedEE using Vert.x Mail Client

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.mailclient`.
- [Source POM](https://github.com/GuicedEE/MailClient.git
branch = master/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.projectlombok:lombok`; scope `compile`.
- `com.guicedee:client`; scope `compile`.
- `com.guicedee.modules.representations:json-representation`; scope `compile`.
- `com.guicedee:inject`; scope `test`.
- `com.guicedee:vertx`; scope `compile`.
- `io.vertx:vertx-mail-client`; scope `compile`.
- `org.junit.jupiter:junit-jupiter-api`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.client.services.lifecycle.IGuiceModule;
import com.guicedee.client.services.lifecycle.IGuicePostStartup;
import com.guicedee.client.services.lifecycle.IGuicePreDestroy;
import com.guicedee.client.services.lifecycle.IGuicePreStartup;
import com.guicedee.mailclient.implementations.*;

module com.guicedee.mailclient {
    requires com.guicedee.jsonrepresentation;
    exports com.guicedee.mailclient;
    exports com.guicedee.mailclient.implementations;

    requires transitive io.vertx.mail.client;
    requires com.guicedee.vertx;
    requires com.guicedee.client;
    requires static lombok;

    requires io.github.classgraph;
    requires org.apache.commons.lang3;

    provides IGuicePostStartup with MailClientPostStartup;
    provides IGuiceModule with MailClientModule;
    provides IGuicePreStartup with MailClientPreStartup;
    provides IGuicePreDestroy with MailClientPreDestroy;

    opens com.guicedee.mailclient to com.google.guice, tools.jackson.databind;
    opens com.guicedee.mailclient.implementations to tools.jackson.databind, com.google.guice;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/MailClient.git
branch = master/blob/master/README.md). Historical version examples retain their original context.

# GuicedEE Mail Client

[![Build](https://github.com/GuicedEE/GuicedMailClient/actions/workflows/build.yml/badge.svg)](https://github.com/GuicedEE/GuicedMailClient/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.guicedee/mail-client)](https://central.sonatype.com/artifact/com.guicedee/mail-client)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Guice 7](https://img.shields.io/badge/Guice-7%2B-green)
![Vert.X 5](https://img.shields.io/badge/Vert.x-5%2B-green)

**Annotation-driven SMTP mail client** for the [GuicedEE](https://github.com/GuicedEE) / Vert.x stack.
Declare SMTP connections with annotations — everything is discovered at startup via ClassGraph, wired through Guice, and managed by the Vert.x Mail Client with connection pooling.

Built on [Vert.x Mail Client](https://vertx.io/docs/vertx-mail-client/java/) · [Google Guice](https://github.com/google/guice) · JPMS module `com.guicedee.mailclient` · Java 25+

## 📦 Installation

```xml
<dependency>
  <groupId>com.guicedee</groupId>
  <artifactId>mail-client</artifactId>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.guicedee:mail-client:2.4.1")
```
</details>

## ✨ Features

- **Annotation-driven setup** — `@MailConnectionOptions` declares the entire SMTP configuration
- **Auto-discovery** — `MailClientPreStartup` scans the classpath via ClassGraph to find all annotated connection declarations
- **Guice-managed mail service** — `MailService` instances are injectable by `@Named("connection-name")` with convenience send methods
- **Connection pooling** — shared Vert.x `MailClient` instances with configurable pool size
- **StartTLS & SSL** — full TLS configuration with `DISABLED`, `OPTIONAL`, or `REQUIRED` modes
- **DKIM signing** — enable DomainKeys Identified Mail signature signing
- **Default from address** — configurable per-connection default sender
- **Attachments** — full `MailMessage` API for attachments, inline images, CC/BCC, HTML, and multipart
- **Environment variable overrides** — every annotation attribute can be overridden via system properties or environment variables at runtime
- **Graceful shutdown** — all mail clients are closed on application shutdown

## 🚀 Quick Start

**Step 1** — Define a connection. Place `@MailConnectionOptions` on a class or `package-info.java`:

```java
@MailConnectionOptions(
        value = "notifications",
        hostname = "smtp.example.com",
        port = 587,
        startTls = MailConnectionOptions.StartTLSMode.REQUIRED,
        login = MailConnectionOptions.LoginMode.REQUIRED,
        username = "user@example.com",
        password = "secret",
        defaultFrom = "noreply@example.com"
)
package com.example.mail;
```

Or on a class:

```java
@MailConnectionOptions(
    value = "notifications",
    hostname = "smtp.example.com",
    port = 587,
    startTls = MailConnectionOptions.StartTLSMode.REQUIRED,
    login = MailConnectionOptions.LoginMode.REQUIRED,
    username = "user@example.com",
    password = "secret",
    defaultFrom = "noreply@example.com"
)
public class MailConfig {
}
```

**Step 2** — Inject and send:

```java
public class NotificationService {

    @Inject
    @Named("notifications")
    private MailService mailService;

    public void sendWelcome(String to) {
        mailService.sendHtml(to, "Welcome!", "<h1>Welcome aboard!</h1>");
    }

    public void sendReport(String to) {
        mailService.sendText(to, "Daily Report", "Here is your daily report...");
    }
}
```

**Step 3** — Configure `module-info.java`:

```java
module my.app {
    requires com.guicedee.mailclient;
    opens my.app.mail to com.google.guice, com.guicedee.mailclient;
}
```

**Step 4** — Bootstrap GuicedEE:

```java
IGuiceContext.instance().inject();
```

That's it. `MailClientPreStartup` discovers the annotations, `MailClientModule` creates the Guice bindings with shared pooled mail clients, and your `MailService` is ready to send.

## 📐 Architecture

```mermaid
flowchart TD
    n1["Startup"]
    n2["IGuiceContext.instance()"]
    n1 --> n2
    n3["MailClientPreStartup<br/>IGuicePreStartup — annotation scanning"]
    n2 --> n3
    n4["Discovers @MailConnectionOptions<br/>classes and package-info.java"]
    n3 --> n4
    n5["Wraps with environment variable resolution"]
    n3 --> n5
    n6["MailClientModule<br/>IGuiceModule — Guice bindings"]
    n2 --> n6
    n7["Creates shared MailClient per connection<br/>pooled"]
    n6 --> n7
    n8["Binds MailClient as @Named('connection-name')"]
    n6 --> n8
    n9["Binds MailService as @Named('connection-name') singleton"]
    n6 --> n9
    n10["MailClientPostStartup<br/>IGuicePostStartup — initialization logging"]
    n2 --> n10
    n11["MailClientPreDestroy<br/>IGuicePreDestroy — shutdown"]
    n2 --> n11
    n12["Closes all mail clients"]
    n11 --> n12
```

### Send lifecycle

```
Send:
  MailService.sendHtml(to, subject, html)
   → MailMessage (auto-fills from if defaultFrom set)
   → MailClient.sendMail(message)
   → Future<MailResult> (messageId, recipients)
```

## 🔧 Annotations

### `@MailConnectionOptions`

Placed on a class or `package-info.java` to declare an SMTP connection:

| Attribute | Default | Purpose |
|---|---|---|
| `value` | `"default"` | Connection name (used for `@Named` binding) |
| `hostname` | `"localhost"` | SMTP server hostname |
| `port` | `25` | SMTP server port |
| `startTls` | `OPTIONAL` | StartTLS mode: `DISABLED`, `OPTIONAL`, `REQUIRED` |
| `login` | `NONE` | Login mode: `DISABLED`, `NONE`, `REQUIRED` |
| `username` | `""` | SMTP authentication username |
| `password` | `""` | SMTP authentication password |
| `ssl` | `false` | Use SSL on connect (port 465) |
| `trustAll` | `false` | Trust all server certificates |
| `ehloHostname` | `""` | EHLO hostname for message-id generation |
| `authMethods` | `""` | Space-separated allowed auth methods |
| `keepAlive` | `true` | Enable connection pooling |
| `maxPoolSize` | `10` | Maximum pool connections |
| `allowRcptErrors` | `false` | Continue if a recipient is rejected |
| `disableEsmtp` | `false` | Disable ESMTP commands |
| `userAgent` | `""` | Mail user agent name |
| `enableDkim` | `false` | Enable DKIM signature signing |
| `pipelining` | `true` | Enable SMTP pipelining |
| `maxMailsPerConnection` | `0` | Max emails per connection (0 = unlimited) |
| `keepAliveTimeout` | `300` | Keep-alive timeout in seconds |
| `defaultFrom` | `""` | Default sender address |

## 📤 Sending Emails

### Simple methods

```java
@Inject
@Named("notifications")
private MailService mailService;

// Plain text (uses default from)
mailService.sendText("user@example.com", "Subject", "Body text");

// Plain text (explicit from)
mailService.sendText("from@example.com", "user@example.com", "Subject", "Body text");

// HTML (uses default from)
mailService.sendHtml("user@example.com", "Subject", "<h1>Hello</h1>");

// HTML (explicit from)
mailService.sendHtml("from@example.com", "user@example.com", "Subject", "<h1>Hello</h1>");

// Multipart (text + HTML)
mailService.sendMultipart("from@example.com", List.of("a@ex.com", "b@ex.com"),
    "Subject", "Plain text fallback", "<h1>HTML body</h1>");
```

### Full MailMessage API

For attachments, inline images, CC/BCC, custom headers:

```java
MailMessage message = new MailMessage()
    .setTo("user@example.com")
    .setCc("cc@example.com")
    .setBcc("bcc@example.com")
    .setSubject("Report with attachment")
    .setText("See attached report.")
    .setHtml("<p>See attached <b>report</b>.</p>")
    .setAttachment(List.of(
        MailAttachment.create()
            .setContentType("application/pdf")
            .setName("report.pdf")
            .setData(Buffer.buffer(pdfBytes))
    ));

mailService.send(message);  // auto-fills from if defaultFrom is set
```

### MailService methods

| Method | Return | Description |
|---|---|---|
| `send(MailMessage)` | `Future<MailResult>` | Send pre-built message (auto-fills from) |
| `sendText(to, subject, text)` | `Future<MailResult>` | Plain text using default from |
| `sendText(from, to, subject, text)` | `Future<MailResult>` | Plain text with explicit from |
| `sendHtml(to, subject, html)` | `Future<MailResult>` | HTML using default from |
| `sendHtml(from, to, subject, html)` | `Future<MailResult>` | HTML with explicit from |
| `sendMultipart(from, to, subject, text, html)` | `Future<MailResult>` | Multipart (text + HTML) |
| `getMailClient()` | `MailClient` | Access underlying Vert.x client |
| `getConnectionName()` | `String` | Logical connection name |
| `getDefaultFrom()` | `String` | Default from address |

## ⚙️ Environment Variable Overrides

All annotation attributes can be overridden at runtime via system properties or environment variables. Overrides are **scoped by name** — the lookup tries a name-specific key first, then falls back to a global key:

1. `MAIL_{NORMALIZED_NAME}_{PROPERTY}` — name-specific override
2. `MAIL_{PROPERTY}` — global fallback
3. Annotation default

The name is normalized to uppercase with hyphens and dots replaced by underscores. For example, a connection named `order-notifications` checking the `HOSTNAME` property would resolve:
1. `MAIL_ORDER_NOTIFICATIONS_HOSTNAME` (name-specific)
2. `MAIL_HOSTNAME` (global fallback)
3. The `hostname` attribute from the `@MailConnectionOptions` annotation

### Connection overrides (`@MailConnectionOptions`)

| Property | Variable pattern |
|---|---|
| `value()` | `MAIL_{name}_CONNECTION_NAME` / `MAIL_CONNECTION_NAME` |
| `hostname()` | `MAIL_{name}_HOSTNAME` / `MAIL_HOSTNAME` |
| `port()` | `MAIL_{name}_PORT` / `MAIL_PORT` |
| `startTls()` | `MAIL_{name}_START_TLS` / `MAIL_START_TLS` |
| `login()` | `MAIL_{name}_LOGIN` / `MAIL_LOGIN` |
| `username()` | `MAIL_{name}_USERNAME` / `MAIL_USERNAME` |
| `password()` | `MAIL_{name}_PASSWORD` / `MAIL_PASSWORD` |
| `ssl()` | `MAIL_{name}_SSL` / `MAIL_SSL` |
| `trustAll()` | `MAIL_{name}_TRUST_ALL` / `MAIL_TRUST_ALL` |
| `ehloHostname()` | `MAIL_{name}_EHLO_HOSTNAME` / `MAIL_EHLO_HOSTNAME` |
| `authMethods()` | `MAIL_{name}_AUTH_METHODS` / `MAIL_AUTH_METHODS` |
| `keepAlive()` | `MAIL_{name}_KEEP_ALIVE` / `MAIL_KEEP_ALIVE` |
| `maxPoolSize()` | `MAIL_{name}_MAX_POOL_SIZE` / `MAIL_MAX_POOL_SIZE` |
| `allowRcptErrors()` | `MAIL_{name}_ALLOW_RCPT_ERRORS` / `MAIL_ALLOW_RCPT_ERRORS` |
| `disableEsmtp()` | `MAIL_{name}_DISABLE_ESMTP` / `MAIL_DISABLE_ESMTP` |
| `userAgent()` | `MAIL_{name}_USER_AGENT` / `MAIL_USER_AGENT` |
| `enableDkim()` | `MAIL_{name}_ENABLE_DKIM` / `MAIL_ENABLE_DKIM` |
| `pipelining()` | `MAIL_{name}_PIPELINING` / `MAIL_PIPELINING` |
| `maxMailsPerConnection()` | `MAIL_{name}_MAX_MAILS_PER_CONNECTION` / `MAIL_MAX_MAILS_PER_CONNECTION` |
| `keepAliveTimeout()` | `MAIL_{name}_KEEP_ALIVE_TIMEOUT` / `MAIL_KEEP_ALIVE_TIMEOUT` |
| `defaultFrom()` | `MAIL_{name}_DEFAULT_FROM` / `MAIL_DEFAULT_FROM` |

## 💉 Dependency Injection

### Named bindings

| Type | Named by | Example |
|---|---|---|
| `MailService` | Connection name | `@Named("notifications") MailService mailService` |
| `MailClient` | Connection name | `@Named("notifications") MailClient client` |

### Injecting the raw Vert.x MailClient

```java
@Inject
@Named("notifications")
private MailClient mailClient;
```

## 🗺️ Module Graph

```mermaid
flowchart LR
    com_guicedee_mailclient["com.guicedee.mailclient"]
    com_guicedee_mailclient --> io_vertx_mail_client["io.vertx.mail.client<br/>Vert.x Mail Client"]
    com_guicedee_mailclient --> com_guicedee_vertx["com.guicedee.vertx<br/>Vert.x lifecycle"]
    com_guicedee_mailclient --> com_guicedee_client["com.guicedee.client<br/>GuicedEE SPI contracts"]
    com_guicedee_mailclient --> io_github_classgraph["io.github.classgraph<br/>annotation scanning"]
    com_guicedee_mailclient --> org_apache_commons_lang3["org.apache.commons.lang3<br/>StringUtils"]
```

## 🧩 JPMS

Module name: **`com.guicedee.mailclient`**

The module:
- **exports** `com.guicedee.mailclient`, `com.guicedee.mailclient.implementations`
- **provides** `IGuicePreStartup` with `MailClientPreStartup`
- **provides** `IGuiceModule` with `MailClientModule`
- **provides** `IGuicePostStartup` with `MailClientPostStartup`
- **provides** `IGuicePreDestroy` with `MailClientPreDestroy`
- **opens** `com.guicedee.mailclient`, `com.guicedee.mailclient.implementations` to `com.google.guice` and `com.fasterxml.jackson.databind`

## 🏗️ Key Classes

| Class | Package | Role |
|---|---|---|
| `MailConnectionOptions` | `mailclient` | Annotation declaring an SMTP connection with all configuration options |
| `MailService` | `mailclient` | High-level mail service with convenience send methods (text, HTML, multipart) |
| `MailClientPreStartup` | `implementations` | `IGuicePreStartup` — scans for all `@MailConnectionOptions` and registers metadata |
| `MailClientModule` | `implementations` | `IGuiceModule` — creates shared `MailClient` instances and binds `MailService` |
| `MailClientPostStartup` | `implementations` | `IGuicePostStartup` — initialization logging |
| `MailClientPreDestroy` | `implementations` | `IGuicePreDestroy` — closes all mail clients on shutdown |

## 🧪 Testing

The test suite validates annotation scanning, Guice binding, and `MailService` injection without requiring a real SMTP server:

```java
@BeforeAll
static void init() {
    IGuiceContext.registerModule("com.guicedee.mailclient.test");
    IGuiceContext.instance().inject();
}

@Test
void testMailServiceBound() {
    var service = IGuiceContext.get(Key.get(MailService.class, Names.named("test-smtp")));
    assertNotNull(service);
    assertEquals("test-smtp", service.getConnectionName());
    assertEquals("test@example.com", service.getDefaultFrom());
}
```

Run tests:

```bash
mvn test
```

## 🤝 Contributing

Issues and pull requests are welcome — please add tests for new connection options or mail service features.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## MailClientModule

[Java source](https://github.com/GuicedEE/MailClient.git
branch = master/blob/master/src/main/java/com/guicedee/mailclient/implementations/MailClientModule.java)

### MailClientModule

```java
@Override protected void configure();
public static MailConfig toMailConfig(MailConnectionOptions options);
```

## MailClientPostStartup

[Java source](https://github.com/GuicedEE/MailClient.git
branch = master/blob/master/src/main/java/com/guicedee/mailclient/implementations/MailClientPostStartup.java)

### MailClientPostStartup

```java
@Override public List<Uni<Boolean>> postLoad();
@Override public Integer sortOrder();
```

## MailClientPreDestroy

[Java source](https://github.com/GuicedEE/MailClient.git
branch = master/blob/master/src/main/java/com/guicedee/mailclient/implementations/MailClientPreDestroy.java)

### MailClientPreDestroy

```java
@Override public void onDestroy();
@Override public Integer sortOrder();
```

## MailClientPreStartup

[Java source](https://github.com/GuicedEE/MailClient.git
branch = master/blob/master/src/main/java/com/guicedee/mailclient/implementations/MailClientPreStartup.java)

### MailClientPreStartup

```java
@Override public List<Future<Boolean>> onStartup();
@Override public Integer sortOrder();
```

## MailConnectionOptions

[Java source](https://github.com/GuicedEE/MailClient.git
branch = master/blob/master/src/main/java/com/guicedee/mailclient/MailConnectionOptions.java)

### MailConnectionOptions

```java
String value() default "default";
String hostname() default "localhost";
int port() default 25;
StartTLSMode startTls() default StartTLSMode.OPTIONAL;
LoginMode login() default LoginMode.NONE;
String username() default "";
String password() default "";
boolean ssl() default false;
boolean trustAll() default false;
String ehloHostname() default "";
String authMethods() default "";
boolean keepAlive() default true;
int maxPoolSize() default 10;
boolean allowRcptErrors() default false;
boolean disableEsmtp() default false;
String userAgent() default "";
boolean enableDkim() default false;
boolean pipelining() default true;
long maxMailsPerConnection() default 0;
int keepAliveTimeout() default 300;
String defaultFrom() default "";
```

### StartTLSMode

```java
public static final StartTLSMode DISABLED;
public static final StartTLSMode OPTIONAL;
public static final StartTLSMode REQUIRED;
```

### LoginMode

```java
public static final LoginMode DISABLED;
public static final LoginMode NONE;
public static final LoginMode REQUIRED;
```

## MailService

[Java source](https://github.com/GuicedEE/MailClient.git
branch = master/blob/master/src/main/java/com/guicedee/mailclient/MailService.java)

### MailService

```java
public MailService(MailClient mailClient, String connectionName, String defaultFrom);
public Future<MailResult> send(MailMessage message);
public Future<MailResult> sendText(String from, String to, String subject, String text);
public Future<MailResult> sendText(String to, String subject, String text);
public Future<MailResult> sendHtml(String from, String to, String subject, String html);
public Future<MailResult> sendHtml(String to, String subject, String html);
public Future<MailResult> sendMultipart(String from, List<String> to, String subject, String text, String html);
public MailClient getMailClient();
public String getConnectionName();
public String getDefaultFrom();
```
