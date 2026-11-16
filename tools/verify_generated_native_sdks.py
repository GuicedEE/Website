"""Exercise a copy of the website-generated native starter against benchmark fixtures.

No production credentials or vendor endpoints. Run prepare, build with Maven,
probe --trace, metadata, rebuild with Maven, build the native executable, then
probe --native. See docs/native-sdk-validation.md for prerequisites and commands.
"""
import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import socket
import subprocess
import sys
import threading
import time
import urllib.request
import uuid
import zipfile

SITE = Path(__file__).resolve().parents[1]
WORKSPACE = SITE.parents[1]
BENCH = WORKSPACE / 'benchmarks'
OUT = SITE / 'target/native-sdk-validation'
PROJECT = OUT / 'project'
IMAGE = 'devsuite-bench-native-builder:local'
sys.path.insert(0, str(BENCH / 'tools'))
from upstream import Upstream


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def prepare():
    baseline = SITE / 'target/native-generated/my-service'
    if PROJECT.exists():
        raise RuntimeError('Choose a fresh output directory; refusing to overwrite a previous validation project')
    PROJECT.mkdir(parents=True)
    for name in ['pom.xml', 'NativeBuild.java', 'NATIVE.md']:
        shutil.copy2(baseline / name, PROJECT / name)
    shutil.copytree(baseline / 'src', PROJECT / 'src')
    sources = {}
    for name in ['Workload.java', 'WorkloadTrace.java']:
        source = BENCH / 'common/src/main/java/bench/workload' / name
        target = PROJECT / 'src/main/java/bench/workload' / name
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(source, target)
        sources[str(source.relative_to(WORKSPACE))] = sha(source)
    for name in ['AbsentOptionalBackends.java', 'FirebaseJdkPem.java']:
        source = BENCH / 'quarkus-native/src/main/java/bench/nativecompat' / name
        target = PROJECT / 'src/main/java/bench/nativecompat' / name
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(source, target)
        sources[str(source.relative_to(WORKSPACE))] = sha(source)
    descriptor = PROJECT / 'src/main/java/module-info.java'
    descriptor.write_text(descriptor.read_text().replace('module my.service {', '''module my.service {
    requires java.net.http;
    requires static org.graalvm.nativeimage;
    opens bench.nativecompat to org.graalvm.nativeimage.builder;
'''))
    pkg = PROJECT / 'src/main/java/com/example/myservice'
    (pkg / 'SdkSmoke.java').write_text('''package com.example.myservice;

import bench.workload.Workload;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class SdkSmoke {
    public static void run() throws Exception {
        String emulator = System.getenv("FIRESTORE_EMULATOR_HOST");
        if (emulator == null || !emulator.startsWith("host.docker.internal:"))
            throw new IllegalStateException("This check requires the local Firestore emulator");
        String prefix = System.getenv("SDK_SMOKE_ID");
        try (var workload = new Workload(System.getenv("BENCH_UPSTREAM"))) {
            for (int i = 0; i < 3; i++) {
                Map<String,Object> quote = workload.quote(new Workload.Order("fixture", 2))
                        .toCompletableFuture().get(30, TimeUnit.SECONDS);
                if (!"fixture".equals(quote.get("sku")) || !"Fixture shop".equals(quote.get("project"))
                        || ((Number)quote.get("quantity")).intValue() != 2
                        || ((Number)quote.get("available")).intValue() != 40
                        || ((Number)quote.get("total")).intValue() != 250)
                    throw new AssertionError("Unexpected Commercetools response: " + quote);
                String id = prefix + "-" + i;
                Map<String,Object> stored = workload.firebase(id).toCompletableFuture().get(30, TimeUnit.SECONDS);
                if (!id.equals(stored.get("id")) || !"fixture".equals(stored.get("sku"))
                        || ((Number)stored.get("total")).intValue() != 250)
                    throw new AssertionError("Unexpected Firestore response: " + stored);
                System.out.println("SDK_SMOKE_PASS " + id);
            }
        }
    }
}
''')
    boot = pkg / 'Boot.java'
    boot.write_text(boot.read_text().replace('});', '''});
        int exit = 0;
        try { SdkSmoke.run(); }
        catch (Throwable failure) { failure.printStackTrace(); exit = 1; }
        finally { IGuiceContext.instance().destroy(); }
        System.exit(exit);'''))
    (OUT / 'inputs.json').write_text(json.dumps({'baseline': str(baseline), 'benchmarkSources': sources}, indent=2))
    print('Prepared generated-project SDK workload:', PROJECT, flush=True)


def stop(process):
    if process is None or process.poll() is not None:
        return
    if os.name == 'nt':
        subprocess.run(['taskkill', '/PID', str(process.pid), '/T', '/F'], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
    else:
        process.terminate()
    process.wait(timeout=20)


def metadata():
    """Apply the benchmark's metadata preparation to these exact traced SDKs."""
    import prepare_native_metadata
    results = sorted(OUT.glob('jvm-trace-*/result.json'))
    if not results or not json.loads(results[-1].read_text())['passed']:
        raise RuntimeError('A passing fresh JVM trace is required')
    traced = json.loads(results[-1].read_text())
    library = PROJECT / 'target/native-libs'
    staging = OUT / 'metadata-staging'
    if staging.exists():
        raise RuntimeError('Metadata staging must be fresh')
    repository = staging / 'repository'
    sdk_types = set()
    for entry in traced['sdkJars']:
        jar = library / entry['name']
        if sha(jar) != entry['sha256']:
            raise RuntimeError('SDK changed after tracing: ' + str(jar))
        artifact = jar.name.removesuffix('-2.6.0.jar')
        dest = repository / 'com/guicedee/modules/services' / artifact / '2.6.0' / jar.name
        dest.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(jar, dest)
        with zipfile.ZipFile(jar) as archive:
            sdk_types.update(n[:-6].replace('/', '.') for n in archive.namelist() if n.endswith('.class'))
    trace = json.loads((PROJECT / 'target/sdk-trace/reachability-metadata.json').read_text())
    trace['reflection'] = [e for e in trace['reflection'] if not any(
            marker in str(e.get('type')) for marker in ['$$EnhancerByGuice$$', '$$FastClassByGuice$$'])]
    for entry in trace['reflection']:
        name = entry.get('type')
        if isinstance(name, str) and name.startswith(('com.guicedee.', 'com.google.inject.', 'com.example.')):
            entry['allDeclaredConstructors'] = True
    base = staging / 'quarkus-native/src/main/resources/META-INF/native-image/dev.guicedee.benchmarks'
    sdk = {'reflection': [e for e in trace['reflection'] if isinstance(e.get('type'), str) and e['type'].removesuffix('[]') in sdk_types],
           'resources': trace.get('resources', [])}
    framework = {'reflection': [e for e in trace['reflection'] if e not in sdk['reflection']], 'resources': trace.get('resources', [])}
    for name, data in [('sdk-trace', sdk), ('guicedee-trace', framework)]:
        (base / name).mkdir(parents=True)
        (base / name / 'reachability-metadata.json').write_text(json.dumps(data, indent=2))
    # Redirect only output paths; the benchmark checkout and its traces stay untouched.
    prepare_native_metadata.ROOT = staging
    prepare_native_metadata.prepare(repository, '2.6.0')
    target = PROJECT / 'src/main/resources/META-INF/native-image/dev.guicedee.benchmarks'
    shutil.copytree(base, target)
    for path in target.glob('*/sdk-services/reflect-config.json'):
        # GuicedEE retains the Native Image ServiceLoader feature.
        path.unlink()
    compat = target / 'compatibility'
    compat.mkdir()
    shutil.copy2(BENCH / 'quarkus-native/src/main/resources/META-INF/native-image/dev.guicedee.benchmarks/compatibility/native-image.properties', compat)
    shutil.copy2(BENCH / 'guicedee-native/NativeModuleAccess.java', PROJECT / 'NativeModuleAccess.java')
    shutil.copy2(BENCH / 'guicedee-native/native-excluded-providers.txt', PROJECT / 'native-excluded-providers.txt')
    resources = sorted({e['glob'] for e in trace.get('resources', []) if 'glob' in e})
    (PROJECT / 'native-resources.txt').write_text('\n'.join(resources) + '\n')
    (PROJECT / 'build-sdk-native.sh').write_text('''#!/bin/sh
set -eu
java NativeBuild.java target/my-service-1.0.0-SNAPSHOT.jar target/native-libs my.service/com.example.myservice.Boot
java --class-path 'target/native-libs/*' NativeModuleAccess.java target/native-libs target/native-module-access.args native-resources.txt target/modular-resources native-excluded-providers.txt target/my-service-1.0.0-SNAPSHOT.jar false
EXCLUDED_PROVIDERS=$(sed '/^#/d; /^$/d' native-excluded-providers.txt | paste -sd ',' -)
native-image -J-Xmx6g -J-Djava.awt.headless=true @target/native-image.args \\
  -H:+UnlockExperimentalVMOptions @target/native-module-access.args \\
  -H:ConfigurationFileDirectories=target/modular-resources \\
  --add-reads=firebase.admin=org.graalvm.nativeimage \\
  --add-reads=commercetools.sdk.java.api=org.graalvm.nativeimage \\
  "-H:ServiceLoaderFeatureExcludeServiceProviders=$EXCLUDED_PROVIDERS" \\
  -H:ServiceLoaderFeatureExcludeServices=javax.sound.midi.spi.MidiDeviceProvider,javax.sound.midi.spi.MidiFileReader,javax.sound.midi.spi.MidiFileWriter,javax.sound.midi.spi.SoundbankReader,javax.sound.sampled.spi.AudioFileReader,javax.sound.sampled.spi.AudioFileWriter,javax.sound.sampled.spi.FormatConversionProvider,javax.sound.sampled.spi.MixerProvider \\
  '-H:ExcludeResources=.*META-INF/native/.*(windows|osx|aarch_64).*' \\
  --exclude-config '.*firebase-admin.*' '/META-INF/native-image/.*' \\
  --exclude-config '.*commercetools.*' '/META-INF/native-image/.*' \\
  --exclude-config '.*my-service.*' '/META-INF/native-image/.*/(sdk-trace|guicedee-trace)/.*' \\
  --exclude-config '.*my-service.*' '/META-INF/native-image/.*/firebase-admin/com.google.(cloud.firestore.v1|cloud.monitoring.v3|storage.v2)/reflect-config.json'
''', newline='\n')
    (OUT / 'metadata-inputs.json').write_text(json.dumps({'sdkJars': traced['sdkJars'],
            'traceSha256': sha(PROJECT / 'target/sdk-trace/reachability-metadata.json'),
            'scope': 'Fresh generated-application trace; benchmark relocation, modular resources and DNS/headless native recipe'}, indent=2))
    print('Prepared fresh workload metadata and benchmark native recipe:', PROJECT / 'build-sdk-native.sh', flush=True)


def probe(native=False, trace=False):
    label = 'native' if native else 'jvm-trace' if trace else 'jvm'
    evidence = OUT / (label + '-' + time.strftime('%Y%m%d-%H%M%S'))
    evidence.mkdir(parents=True)
    emulator = None
    upstream = Upstream('0.0.0.0')
    thread = threading.Thread(target=upstream.serve_forever, daemon=True)
    thread.start()
    project_id = 'demo-devsuite'
    prefix = 'generated-' + uuid.uuid4().hex
    code = None
    try:
        with socket.socket() as sock:
            if sock.connect_ex(('127.0.0.1', 18088)) == 0:
                raise RuntimeError('Firestore port 18088 is occupied; refusing to reuse an unknown service')
        config = json.loads((BENCH / 'fixtures/firebase/firebase.json').read_text())
        config['emulators']['firestore']['host'] = '0.0.0.0'
        config['firestore']['rules'] = str(BENCH / 'fixtures/firebase/firestore.rules')
        (evidence / 'firebase.json').write_text(json.dumps(config))
        with (evidence / 'firestore.log').open('w') as firestore_log:
            emulator = subprocess.Popen([shutil.which('node'), str(BENCH / 'fixtures/firebase/node_modules/firebase-tools/lib/bin/firebase.js'),
                    'emulators:start', '--only', 'firestore', '--project', project_id, '--non-interactive',
                    '--config', str(evidence / 'firebase.json')], cwd=evidence, stdout=firestore_log,
                    stderr=subprocess.STDOUT, creationflags=subprocess.CREATE_NO_WINDOW if os.name == 'nt' else 0)
            deadline = time.monotonic() + 120
            while time.monotonic() < deadline:
                if emulator.poll() is not None:
                    raise RuntimeError('Firestore emulator exited; see ' + str(evidence / 'firestore.log'))
                with socket.socket() as sock:
                    if sock.connect_ex(('127.0.0.1', 18088)) == 0:
                        break
                time.sleep(.2)
            else:
                raise TimeoutError('Firestore emulator did not start')
            name = 'guicedee-generated-sdk-' + label
            command = ['docker', 'run', '--rm', '--name', name, '--memory=2g', '--cpus=2',
                    '--mount', f'type=bind,source={PROJECT},target=/work', '--workdir', '/work',
                    '-e', f'BENCH_UPSTREAM=http://host.docker.internal:{upstream.server_port}',
                    '-e', 'FIRESTORE_EMULATOR_HOST=host.docker.internal:18088',
                    '-e', 'GCLOUD_PROJECT=' + project_id, '-e', 'SDK_SMOKE_ID=' + prefix,
                    '--entrypoint', '/work/target/native/application' if native else 'java', IMAGE]
            if not native:
                if trace:
                    trace_dir = PROJECT / 'target/sdk-trace'
                    if trace_dir.exists():
                        raise RuntimeError('Trace output must be fresh: ' + str(trace_dir))
                    command.append('-agentlib:native-image-agent=config-output-dir=target/sdk-trace')
                command += ['--module-path', 'target/native-libs:target/my-service-1.0.0-SNAPSHOT.jar', '-m', 'my.service/com.example.myservice.Boot']
            with (evidence / 'application.log').open('w') as log:
                try:
                    code = subprocess.run(command, stdout=log, stderr=subprocess.STDOUT, timeout=180).returncode
                finally:
                    subprocess.run(['docker', 'rm', '-f', name], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
            text = (evidence / 'application.log').read_text(encoding='utf-8', errors='replace')
            checks = {'process': code == 0, 'threeSdkCycles': text.count('SDK_SMOKE_PASS ') == 3}
            for key, path in [('oauth', '/oauth/token'), ('rest', '/benchmark'), ('graphql', '/benchmark/graphql')]:
                events = [e for e in upstream.events if e['path'] == path]
                checks[key] = len(events) >= (1 if key == 'oauth' else 3) and all(e['status'] == 200 for e in events)
            documents = []
            for i in range(3):
                try:
                    url = f'http://127.0.0.1:18088/v1/projects/{project_id}/databases/(default)/documents/quotes/{prefix}-{i}'
                    with urllib.request.urlopen(url, timeout=10) as response:
                        document = json.load(response)
                    documents.append(document)
                except Exception as error:
                    documents.append({'error': str(error)})
            checks['firestorePersisted'] = all(d.get('fields', {}).get('sku', {}).get('stringValue') == 'fixture'
                    and d.get('fields', {}).get('total', {}).get('integerValue') == '250' for d in documents)
            result = {'kind': label, 'checks': checks, 'passed': all(checks.values()), 'exitCode': code,
                    'upstreamEvents': upstream.events, 'documents': documents, 'performanceComparison': False,
                    'sdkJars': [{'name': p.name, 'sha256': sha(p)} for p in (PROJECT / 'target/native-libs').glob('*.jar')
                        if p.name.startswith(('firebase-admin-', 'commercetools-'))]}
            if native:
                result['executableSha256'] = sha(PROJECT / 'target/native/application')
            (evidence / 'result.json').write_text(json.dumps(result, indent=2))
            print(json.dumps({'evidence': str(evidence), 'checks': checks, 'exitCode': code}), flush=True)
            return 0 if result['passed'] else 1
    finally:
        upstream.shutdown()
        upstream.server_close()
        stop(emulator)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('action', choices=['prepare', 'probe', 'metadata'])
    parser.add_argument('--native', action='store_true')
    parser.add_argument('--trace', action='store_true')
    args = parser.parse_args()
    if args.native and args.trace:
        parser.error('Native executables cannot be instrumented with the JVM agent')
    sys.exit(prepare() if args.action == 'prepare' else metadata() if args.action == 'metadata' else probe(args.native, args.trace))
