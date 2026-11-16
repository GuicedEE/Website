# Generated project SDK operation validation — 15 November 2026

This extends the [generated starter build and startup check](native-starter-validation.md)
with actual Firebase Admin and Commercetools SDK calls. A copy of the downloaded
Maven project has both SDKs and the native starter selected. The test adds the
benchmark's unchanged `Workload.java` and `WorkloadTrace.java`, a three-cycle assertion
runner, and the benchmark's workload-specific native compatibility setup.

**Passed on both JVM and native.** Each run completed one OAuth exchange, three
Commercetools REST requests, three GraphQL requests and three Firestore write/read
cycles. All upstream requests returned HTTP 200, all independently retrieved
Firestore documents matched, and both applications exited with code zero.

## Operations and assertions

| Operation | Fixture | Assertion |
| --- | --- | --- |
| Commercetools OAuth client credentials | Benchmark `tools/upstream.py` | Valid Basic authentication and grant; HTTP 200 |
| Commercetools project REST request | Same upstream fixture | Project name `Fixture shop`; three HTTP 200 responses |
| Commercetools GraphQL inventory query | Same upstream fixture | SKU `fixture`, available quantity 40, order quantity 2 and total 250; three HTTP 200 responses |
| Firebase Admin Firestore write/read | Official Firestore emulator, project `demo-devsuite` | Three unique documents, each read back through the SDK with SKU `fixture` and total 250 |
| Independent persistence check | Firestore emulator REST API | Python reads all three documents and verifies their stored fields |

The application starts through the generated GuicedEE boot sequence, runs all three
cycles, closes the SDK workload, destroys the Guice context and exits. The harness
requires exit code zero, three success markers, successful recorded upstream requests
and independent Firestore persistence checks. Each probe starts fresh fixtures and
uses unique document IDs. It stops its fixtures and container after the probe.

These are real SDK protocol calls to the same local fixtures used by the benchmarks.
Commercetools uses a local OAuth/REST/GraphQL stub; Firestore uses the official emulator.
No production tenant, credentials or vendor-hosted endpoint is involved. This checks
functional correctness, not performance, cloud TLS/authentication or every SDK API.

## Native metadata scope

The local Firebase 2.6.0 JAR differs from the saved benchmark trace's input, so the
harness first traces the passing JVM control with Native Image's agent. It verifies
the SDK JAR hashes before preparing metadata for native compilation.

The test reuses the benchmark's metadata relocation tool, module access/resource
helper, `AbsentOptionalBackends` and `FirebaseJdkPem` compatibility classes, and
DNS/headless provider exclusions. Relocation rules come from the actual SDK JARs.
Traces are collected for this generated application; the saved benchmark traces and
benchmark checkout are not modified. Generated Guice JVM helper names are filtered,
and the benchmark's modular resource/reflection preparation is applied.

This additional setup belongs to the SDK workload test. The downloaded starter's
baseline metadata remains focused on GuicedEE startup; selecting these SDKs alone
does not supply metadata for arbitrary Firebase or Commercetools operations.

## Reproduce locally

Run from the DevSuite root on Windows with Java 25, Maven, Python, Node, Docker Desktop
and the adjacent `benchmarks` checkout. Install the benchmark's pinned Firebase CLI
under `benchmarks/fixtures/firebase/node_modules` and cache its Firestore emulator.
This run used Firebase CLI 15.36.0 and Firestore emulator 1.22.0. Port 18088 must be free.

Download the website's Maven `com.example:my-service` project with Firebase Admin,
Commercetools SDK and Native image starter selected. Extract it under
`GuicedEE/website/target/native-generated/my-service`. The local 2.6.0 artifacts must
be available to Maven. The harness creates a separate project and refuses to overwrite
existing preparation or trace directories.

```powershell
python GuicedEE/website/tools/verify_generated_native_sdks.py prepare
./mvnw.cmd -B -ntp -f GuicedEE/website/target/native-sdk-validation/project/pom.xml package dependency:copy-dependencies '-DskipTests' '-DincludeScope=runtime' '-DoutputDirectory=target/native-libs'
python GuicedEE/website/tools/verify_generated_native_sdks.py probe --trace
python GuicedEE/website/tools/verify_generated_native_sdks.py metadata
./mvnw.cmd -B -ntp -f GuicedEE/website/target/native-sdk-validation/project/pom.xml package '-DskipTests'
$sdkProject = (Resolve-Path GuicedEE/website/target/native-sdk-validation/project).Path
docker run --rm --name guicedee-generated-sdk-build --memory=10g --cpus=4 --mount "type=bind,source=$sdkProject,target=/work" --workdir /work --entrypoint sh devsuite-bench-native-builder:local build-sdk-native.sh
if ($LASTEXITCODE -ne 0) { throw 'Native SDK build failed' }
python GuicedEE/website/tools/verify_generated_native_sdks.py probe --native
if ($LASTEXITCODE -ne 0) { throw 'Native SDK probe failed' }
```

The existing benchmark builder image is Mandrel 25.0.5.0-Final / Java 25.0.5,
Linux x86-64, image ID
`sha256:c5e4bb9793e8f9f763b2567c761fa48454a82b30cef4c94185d07f47cdd415f5`.
The native build uses four CPUs, 10 GiB container memory and a 6 GiB builder heap.
Probes use two CPUs and 2 GiB, with host-local fixture access through Docker Desktop.

## Evidence

| Recorded run | Result |
| --- | --- |
| JVM control and fresh agent trace, `jvm-trace-20261115-213929` | All six harness checks passed |
| Native executable, `native-20261115-214524` | All six harness checks passed |
| Native Image compilation | Succeeded in 2m 29s; elapsed time is diagnostic, not a benchmark comparison |
| Native executable size | 140,037,224 bytes |

Native executable SHA-256:
`9ac9c28b4fb70b74029b46875cbde94e961b0e4709ebcaef4ce04e5898e4339e`.

Exact SDK JAR SHA-256 values used by both probes:

- `firebase-admin-2.6.0.jar`: `65b58f8774fbde8ebb4be0f23a6fde9a9f137433946599a1fa84db150c4750be`
- `commercetools-2.6.0.jar`: `d70a9311745879fde60c35bfdb358e5189530e0080c08f36c60d0182c63c774d`

The harness writes source hashes to `target/native-sdk-validation/inputs.json`, SDK
and trace hashes to `metadata-inputs.json`, and timestamped probe directories with
`application.log`, `firestore.log` and `result.json`. The latter records all assertions,
upstream requests, stored documents, SDK hashes and, for native runs, executable hash.
The generated test application and executable stay under the ignored `target` tree.

Workspace logs are `logs/generated-sdk-jvm-trace.log`,
`logs/generated-sdk-native-image.log` and `logs/generated-sdk-native-probe.log`.
Build-time upstream reflection warnings, runtime native-access warnings and the
relocated SDK's missing SLF4J provider warning remain; all operation assertions passed.

No publication or deployment is part of this validation.
