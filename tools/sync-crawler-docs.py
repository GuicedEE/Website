#!/usr/bin/env python3
"""Refresh committed, offline-buildable documentation from the DevSuite reactor.

Run from any directory. --check detects source/snapshot drift without writing.
Only Maven release profiles are enumerated; tests, examples and private build
outputs are never copied. Java APIs are parsed by the JDK compiler, not regex.
"""
import argparse
import base64
import configparser
import hashlib
import json
import re
import subprocess
import tempfile
import xml.etree.ElementTree as ET
from pathlib import Path
from urllib.parse import urljoin

SITE = Path(__file__).resolve().parents[1]
ROOT = SITE.parents[1]
PUBLIC = SITE / 'src/main/resources/src/assets'
BASE = 'https://guicedee.com'
PROFILES = ('guicedee-boms', 'services', 'guicedee', 'guicedee-native')


def pom(path):
    root = ET.parse(path).getroot()
    for node in root.iter():
        node.tag = node.tag.split('}')[-1]
    return root


def modules():
    profiles = {p.findtext('id'): p for p in pom(ROOT / 'pom.xml').findall('profiles/profile')}
    paths = []
    def add(path):
        path = path.resolve()
        if 'examples' in path.parts or path in paths:
            return
        paths.append(path)
        for query in ('modules/module', 'subprojects/subproject'):
            for child in pom(path / 'pom.xml').findall(query):
                add(path / child.text)
    for name in PROFILES:
        for query in ('modules/module', 'subprojects/subproject'):
            for child in profiles[name].findall(query):
                add(ROOT / child.text)
    return paths


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--check', action='store_true')
    args = parser.parse_args()
    config = configparser.ConfigParser()
    config.read(ROOT / '.gitmodules')
    repos = sorted([(ROOT / config[s]['path'], config[s]['url'].removesuffix('.git'), config[s].get('branch', 'master')) for s in config.sections()], key=lambda x: -len(x[0].parts))
    def source_url(file):
        for directory, url, branch in repos:
            if file.is_relative_to(directory):
                return f'{url}/blob/{branch}/{file.relative_to(directory).as_posix()}'
        return 'https://github.com/GuicedEE'

    inputs = {}
    def read(path):
        data = path.read_bytes()
        text = data.decode('utf-16' if data.startswith((b'\xff\xfe', b'\xfe\xff')) else 'utf-8-sig')
        inputs[path.relative_to(ROOT).as_posix()] = hashlib.sha256(data).hexdigest()
        return text

    read(ROOT / 'pom.xml')
    read(ROOT / '.gitmodules')
    paths = modules()
    java_files = sorted(f for path in paths for f in (path / 'src/main/java').rglob('*.java') if f.name not in ('module-info.java', 'package-info.java'))
    apis = {}
    if java_files:
        with tempfile.TemporaryDirectory(prefix='guicedee-docs-') as tmp:
            manifest = Path(tmp) / 'sources.txt'
            manifest.write_text('\n'.join(str(f) for f in java_files), encoding='utf-8')
            result = subprocess.run(['java', str(SITE / 'tools/ExtractApi.java'), str(manifest)], text=True, encoding='utf-8', capture_output=True, check=True)
            for line in result.stdout.splitlines():
                filename, body = (base64.b64decode(part).decode('utf-8') for part in line.split('\t'))
                apis[Path(filename)] = body
    output = {}
    inventory = []
    sections = {'Application modules and Kotlin': [], 'Modular library services': [], 'BOMs and build parents': []}
    all_docs = []
    overview = read(SITE / 'docs/ai-overview.md')
    output['reference/overview.md'] = overview
    for path in paths:
        read(path / 'pom.xml')
        model = pom(path / 'pom.xml')
        group = model.findtext('groupId') or model.findtext('parent/groupId')
        artifact = model.findtext('artifactId')
        version = model.findtext('version') or model.findtext('parent/version')
        packaging = model.findtext('packaging') or 'jar'
        section = 'Modular library services' if 'services' in path.relative_to(ROOT).parts else 'BOMs and build parents' if packaging == 'pom' else 'Application modules and Kotlin'
        slug = f'{group}.{artifact}'
        doc_path = f'reference/components/{slug}.md'
        description = ' '.join((model.findtext('description') or model.findtext('name') or artifact).split())
        descriptor = path / 'src/main/java/module-info.java'
        if not descriptor.exists():
            descriptor = path / 'src/moditect/module-info.java'
        descriptor_text = read(descriptor) if descriptor.exists() else ''
        if not descriptor_text:
            descriptor_text = model.findtext('.//moduleInfo') or ''
        match = re.search(r'\b(?:open\s+)?module\s+([\w.]+)\s*\{', descriptor_text)
        module_name = match[1] if match else None
        row = {'groupId': group, 'artifactId': artifact, 'version': version, 'packaging': packaging, 'jpmsModule': module_name, 'source': source_url(path / 'pom.xml'), 'document': BASE + '/' + doc_path}
        inventory.append(row)
        sections[section].append(f'- [{group}:{artifact}]({row["document"]}): {description}')
        doc = f'# {group}:{artifact}\n\n{description}\n\n- Version line: `{version}` (verify publication separately).\n- Packaging: `{packaging}`.\n- JPMS module: ' + (f'`{module_name}`.' if module_name else 'not declared in this source POM/directory; inspect the build configuration.') + f'\n- [Source POM]({row["source"]}).\n- [Reference guide]({BASE}/reference/overview.md).\n\n'
        deps = model.findall('dependencies/dependency')
        if deps:
            doc += '## Direct dependency declarations\n\nVersions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.\n\n'
            for dep in deps:
                doc += f'- `{dep.findtext("groupId")}:{dep.findtext("artifactId")}`; scope `{dep.findtext("scope") or "compile"}`'
                if dep.findtext('optional') == 'true': doc += '; optional'
                doc += '.\n'
            doc += '\n'
        if descriptor_text:
            doc += '## JPMS descriptor\n\n```java\n' + descriptor_text.strip() + '\n```\n\n'
        readme = path / 'README.md'
        if not readme.exists() and path.parent in paths:
            readme = path.parent / 'README.md'
        if readme.exists():
            contents = read(readme)
            # Resolve repository-relative documentation links; retain absolute URLs/fragments.
            def resolve_link(m):
                label, target = m.groups()
                if not re.match(r'^(?:[a-z][a-z0-9+.-]*:|#|/)', target, re.I):
                    target = urljoin(source_url(readme), target)
                return f'[{label}]({target})'
            contents = re.sub(r'\[([^\]\n]*)\]\(([^\s)]+)\)', resolve_link, contents)
            doc += f'## Module guide\n\nSource: [{readme.name}]({source_url(readme)}). Historical version examples retain their original context.\n\n' + contents.strip() + '\n\n'
        else:
            doc += '## Usage\n\n' + ('Import this POM in dependency management, or use it as a parent only when its documentation specifies that role. It provides build/version alignment rather than runtime functions.' if packaging == 'pom' else 'This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.') + '\n\n'
        module_apis = []
        for file in sorted((path / 'src/main/java').rglob('*.java')):
            read(file)
            body = apis.get(file, '')
            if body.strip():
                module_apis.append(f'## {file.stem}\n\n[Java source]({source_url(file)})\n' + body)
        for file in sorted((path / 'src/main/kotlin').rglob('*.kt')):
            contents = read(file)
            # Small handwritten adapters: keep full source so receivers, defaults,
            # suspend boundaries and extension-function implementations stay intact.
            module_apis.append(f'## Kotlin source: {file.name}\n\n[Source]({source_url(file)})\n\n```kotlin\n{contents.strip()}\n```\n')
        if module_apis:
            doc += '## Declared API reference\n\nJava public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.\n\n' + '\n'.join(module_apis)
        output[doc_path] = doc
        all_docs.append(doc)
    index = '# GuicedEE\n\n> Guice-first, JPMS modular Java and Kotlin services on Vert.x, with reactive persistence, HTTP APIs, messaging, cloud SDKs, MicroProfile integrations, jlink and Native-Image support.\n\nThis index covers the 2.6.0 source line. Verify artifact publication before choosing a dependency version. Each component document supplies actual Maven coordinates, direct dependencies, JPMS metadata, available usage guides and declared APIs. Library wrappers expose upstream APIs.\n\n## Start here\n\n'
    index += f'- [Developer reference]({BASE}/reference/overview.md): architecture, bootstrap, integration choices, Kotlin, packaging and API usage.\n- [Complete reference]({BASE}/llms-full.txt): all component documents in one Markdown file.\n- [Machine-readable inventory]({BASE}/reference/inventory.json): all {len(inventory)} reactor components, coordinates, modules and source provenance.\n'
    index += '\n## Website documentation\n\n'
    for route, title in [('home','Overview and benchmarks'),('getting-started','Getting started'),('guides/end-to-end','End-to-end guide'),('capabilities','Capabilities and annotations'),('modules','Module catalog'),('services','Library services'),('environment-variables','Environment variables'),('cloud','Cloud deployment'),('builder','Application builder'),('releases','Release notes')]:
        index += f'- [{title}]({BASE}/{route}.md): text from the website documentation.\n'
    for title, links in sections.items():
        index += '\n## ' + title + '\n\n' + '\n'.join(sorted(links)) + '\n'
    index += '\n## Tools and related projects\n\n- [IntelliJ plugin](https://github.com/GuicedEE/GuicedEEIntelliJPlugin): project generation, Java/Kotlin templates and build configuration.\n- [MCP server](https://github.com/GuicedEE/mcp): GuicedEE integration with Model Context Protocol tooling; see its own version and setup requirements.\n- [Benchmarks](https://github.com/GuicedEE/benchmarks): reproducible methodology, runtime, build, CDI and persistence results.\n- [GuicedEE organization](https://github.com/GuicedEE): source repositories and issue trackers.\n- [JWebMP](https://jwebmp.com): Java-authored web interfaces and Angular generation.\n- [EntityAssist](https://entityassist.com): related persistence entity and query utilities.\n'
    output['llms.txt'] = index
    output['llms-full.txt'] = '# GuicedEE complete reference\n\n' + overview + '\n\n' + '\n\n---\n\n'.join(all_docs)
    for file in (SITE / 'tools/sync-crawler-docs.py', SITE / 'tools/ExtractApi.java'):
        read(file)
    output['reference/inventory.json'] = json.dumps({'schemaVersion': 1, 'sourceLine': '2.6.0', 'components': inventory, 'inputs': dict(sorted(inputs.items()))}, indent=2, ensure_ascii=False) + '\n'
    changed = []
    for name, contents in output.items():
        file = PUBLIC / name
        contents = contents.replace('\r\n', '\n').replace('\r', '\n')
        contents = '\n'.join(line.expandtabs(4).rstrip() for line in contents.splitlines()).rstrip() + '\n'
        if not file.exists() or file.read_bytes() != contents.encode('utf-8'):
            changed.append(name)
            if not args.check:
                file.parent.mkdir(parents=True, exist_ok=True)
                file.write_text(contents, encoding='utf-8', newline='\n')
    # Delete only stale component documents previously owned by this generator.
    stale = [p for p in (PUBLIC / 'reference/components').glob('*.md') if p.relative_to(PUBLIC).as_posix() not in output]
    changed += [p.relative_to(PUBLIC).as_posix() for p in stale]
    if not args.check:
        for file in stale: file.unlink()
    print(f'{len(inventory)} components; {len(java_files)} Java sources; {len(output)} documents; {len(changed)} changes')
    if args.check and changed:
        raise SystemExit('Documentation drift: ' + ', '.join(changed[:12]))


if __name__ == '__main__':
    main()
