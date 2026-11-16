# Generated native starter validation — 15 November 2026

The website-generated Maven project with **Firebase Admin**, **Commercetools SDK** and
**Native image starter (SPI)** selected compiled to a Linux executable and completed
GuicedEE/Vert.x startup. No application code was added to the downloaded starter.

A subsequent [SDK operation validation](native-sdk-validation.md) added the benchmark
workload and its native compatibility setup to a separate copy. Both JVM and native
passed Commercetools OAuth/REST/GraphQL and Firestore write/read checks against the
benchmark fixtures. The table below records the original startup-only check.

## Scope and result

| Check | Result |
| --- | --- |
| Website ZIP generation | Passed, including both SDK coordinates and JPMS requirements |
| Java 25 compilation | Passed |
| Native argument preparation | Passed, with the application JAR and all 75 dependency JARs |
| Native Image compilation | Passed on Mandrel 25.0.5.0-Final, Linux x86-64 |
| Native startup | Passed: injector initialized and global Vert.x worker pool deployed |
| Bounded runtime check | Passed: readiness log observed, then deliberate timeout exit 124 |
| Live Firebase/Commercetools operations | Not exercised; runtime networking was disabled |

The generated starter contains no SDK calls or credentials. These results establish
that selecting both SDKs produces a buildable, bootable native starter, not that all
SDK products or application workloads are native-compatible. Native Image can remove
unused code; the module path includes both SDKs but this is not an SDK API coverage test.

## Fix found by execution

The initial native compilation succeeded, but startup failed when Guice looked up
`Integer.parseInt(String)` reflectively. Both website and IntelliJ generators now emit:

- Reflection registration for Guice's seven primitive converters.
- Baseline call-scope, global-properties and startup injection targets.
- A member-injection check for the existing Vert.x instance.
- Reachability metadata for the core context provider lambda.

Application-specific classes remain in the generated `NativeConfiguration` and
`ApplicationDiscovery` providers. The fix does not copy the benchmark application's
SDK traces or silently remove either SDK. A new ZIP was downloaded after the generator
fix, compiled, and used for the successful native build and startup check.

## Reproduction

The project used `com.example:my-service:1.0.0-SNAPSHOT`, module `my.service`, and main
class `com.example.myservice.Boot`. GuicedEE dependencies were version 2.6.0 from the
local Maven repository; this validation does not claim those artifacts are published.

Follow the generated `NATIVE.md`:

```sh
mvn package dependency:copy-dependencies -DincludeScope=runtime -DoutputDirectory=target/native-libs
java NativeBuild.java target/my-service-1.0.0-SNAPSHOT.jar target/native-libs my.service/com.example.myservice.Boot
native-image -J-Xmx6g @target/native-image.args
```

Native compilation ran in the existing local `devsuite-bench-native-builder:local`
container, limited to 4 CPUs and 10 GiB, with a 6 GiB builder heap. Its image ID was
`sha256:c5e4bb9793e8f9f763b2567c761fa48454a82b30cef4c94185d07f47cdd415f5`.
Run `NativeBuild.java` inside the build environment so paths and separators match it.
The runtime smoke check used 1 CPU, 500 MiB, `--network none`, and a read-only executable
mount. It observed successful startup before deliberately stopping the long-running
application with `timeout`.

The build still reports upstream Firebase reflection-entry warnings and a deprecated
proxy-metadata warning. Runtime reports Netty's native-access warning on JDK 25.
They did not prevent this startup check; SDK operation coverage remains separate.

## Local evidence

- Generated sources: `target/native-generated/my-service/`
- Executable: `target/native-generated/my-service/target/native/application`
- Executable size: 132,566,120 bytes
- SHA-256: `4fe81df890b2a50f43d30be366ad5cbd27731eed6e3641c5d3a4da5cd38cd5c6`
- Workspace logs: `logs/generated-native-image-fixed.log`,
  `logs/generated-native-startup-verified.log`, `logs/generated-native-starter-fixed.log`
- Browser checks: `logs/website-native-bootstrap-browser.log`
- IntelliJ generator tests: `logs/plugin-native-bootstrap-tests.log`

No publication or deployment was performed.
