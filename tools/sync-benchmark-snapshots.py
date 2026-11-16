"""Refresh the Java-authored homepage snapshots from a validated benchmark checkout."""
import argparse
import csv
import json
from pathlib import Path
import re


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('benchmarks', type=Path)
    parser.add_argument('--check', action='store_true')
    args = parser.parse_args()
    docs = args.benchmarks / 'docs'
    runtime = json.loads((docs / 'current-performance.json').read_text(encoding='utf-8'))
    persistence = json.loads((docs / 'persistence-performance.json').read_text(encoding='utf-8'))
    with (docs / 'build-performance.csv').open(encoding='utf-8', newline='') as stream:
        builds = list(csv.DictReader(stream))
    if persistence['profile'] != 'default':
        raise ValueError('The homepage requires the default persistence profile')
    native = runtime['groups']['native-standard']['variants']
    native_rows = [native[name] for name in ['guicedee-native', 'quarkus-standard-native', 'micronaut-native']]
    persistence_rows = [persistence['variants'][name] for name in ['guicedee', 'quarkus', 'micronaut']]
    build_value = lambda variant, goal: float(next(row['median_seconds'] for row in builds
        if row['variant'] == variant and row['goal'] == goal))
    build_values = [[build_value(variant, goal) for variant in variants] for variants, goal in [
        (['guicedee', 'quarkus-standard', 'micronaut'], 'compile'),
        (['guicedee', 'quarkus-standard', 'micronaut'], 'package'),
        (['guicedee-native', 'quarkus-standard-native', 'micronaut-native'], 'package')]]
    persistence_values = [[row['launchToFirstTransactionMs']['median'] / 1000 for row in persistence_rows],
                          [row['read']['p50Ms'] for row in persistence_rows],
                          [row['cycle']['p50Ms'] for row in persistence_rows]]
    snapshot = f"Snapshot · {runtime['measuredOn']}"
    native_source = f"{snapshot} · {' / '.join(str(row['starts']) for row in native_rows)} starts (GuicedEE / Quarkus / Micronaut)"
    build_source = f"{snapshot} · {runtime['builds']['runs']} clean builds per task"
    persistence_source = (f"Snapshot · {persistence['measuredAt'][:10]} · "
                          f"{persistence['runs']} starts per framework · default JVM")
    grids = []
    for profile in ['jvm-default', 'jvm-c1']:
        grid = []
        for group, ids, reference in [
            (profile, ['guicedee', 'guicedee-scan', 'quarkus-standard', 'micronaut',
                       'guicedee-jlink', 'guicedee-scan-jlink'], 'quarkus-standard'),
            ('native-standard', ['guicedee-native', 'quarkus-standard-native', 'micronaut-native'],
             'quarkus-standard-native')]:
            variants = runtime['groups'][group]['variants']
            baseline = variants[reference]['launchToFirstBusinessMs']['median']
            for name in ids:
                row = variants[name]
                startup = row['launchToFirstBusinessMs']
                grid.append(dict(key=group + name, artifact=row['label'], starts=row['starts'],
                    startup=f"{startup['median']/1000:.3f}",
                    range=f"{startup['minimum']/1000:.3f}–{startup['maximum']/1000:.3f}",
                    warm=f"{row['warm']['p50Ms']:.2f}", peak=f"{row['peakMiB']['median']:.2f}",
                    baseline='Quarkus native' if group == 'native-standard' else 'Quarkus fast-JAR',
                    difference='Baseline' if name == reference else f"{(startup['median']/baseline-1)*100:+.1f}%"))
        grids.append(grid)
    fields = dict(persistenceValues=persistence_values, persistenceSource=persistence_source,
        benchmarkValues=[[row['launchToFirstBusinessMs']['median'] for row in native_rows],
                         build_values[0], [row['peakMiB']['median'] for row in native_rows], persistence_values[0]],
        benchmarkUnits=['ms', 's', 'MiB', 's'],
        benchmarkSources=[native_source, build_source, native_source, persistence_source],
        buildSnapshot=builds, buildValues=build_values, buildSource=build_source,
        runtimeGrids=grids, runtimeSources=[f'{snapshot} · default JVM + native', f'{snapshot} · C1 JVM + native'])
    source = Path(__file__).resolve().parents[1] / 'src/main/java/com/guicedee/website/pages/BenchmarkShowcase.java'
    old = source.read_text(encoding='utf-8')
    content = old
    for field, value in fields.items():
        pattern = rf'(?m)^(\s*{field}(?:\s*:[^=\n]+)?\s*=\s*)[^\n]*;$'
        encoded = json.dumps(value, ensure_ascii=False, separators=(',', ':'), allow_nan=False)
        content, count = re.subn(pattern, lambda match: match[1] + encoded + ';', content)
        if count != 1:
            raise ValueError(f'Expected one Java snapshot field: {field}')
    if args.check:
        if content != old:
            raise ValueError('Homepage benchmark snapshots are stale')
    else:
        source.write_text(content, encoding='utf-8', newline='\n')
    print('Verified snapshots' if args.check else 'Updated snapshots')


if __name__ == '__main__':
    main()
