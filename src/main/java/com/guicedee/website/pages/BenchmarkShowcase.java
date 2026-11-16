package com.guicedee.website.pages;

/** Browser-side presentation of the benchmark repository's published summary contracts. */
final class BenchmarkShowcase {
    private BenchmarkShowcase() {}

    static String fields() {
        return """
                showcaseIndex = 0;
                cdiColumns = [
                    {field: 'runtime', label: 'Runtime', width: 140, sortable: false},
                    {field: 'discovery', label: 'Discovery', width: 110, sortable: false},
                    {field: 'checks', label: 'Checks', width: 70, sortable: false}
                ];
                cdiValidation = [
                    {key: 'windows-scan', runtime: 'Windows jlink', discovery: 'Scanner', checks: '16/16'},
                    {key: 'windows-spi', runtime: 'Windows jlink', discovery: 'SPI', checks: '16/16'},
                    {key: 'linux-scan', runtime: 'Linux jlink', discovery: 'Scanner', checks: '16/16'},
                    {key: 'linux-spi', runtime: 'Linux jlink', discovery: 'SPI', checks: '16/16'},
                    {key: 'linux-native', runtime: 'Linux native', discovery: 'SPI', checks: '16/16'}
                ];
                benchmarkValues: (number[] | null)[] = [[656.09155, 677.7095], [3.14255652, 3.65736], [68.474609375, 55.298828125], null];
                benchmarkUnits = ['ms', 's', 'MiB', 'ms'];
                benchmarkSources = ['Snapshot · 15 Nov 2026 · 14 starts per framework', 'Snapshot · 15 Nov 2026 · 3 clean builds per framework', 'Snapshot · 15 Nov 2026 · 14 starts per framework', 'Awaiting published persistence results'];
                benchmarkLabels = [['GuicedEE native', 'Quarkus native'], ['GuicedEE SPI', 'Quarkus fast-JAR'], ['GuicedEE native', 'Quarkus native'], ['GuicedEE', 'Quarkus']];
                benchmarkColumns = [{field: 'framework', label: 'Application', width: 128, minWidth: 128}, {field: 'value', label: 'Median', align: 'end'}];
                buildGridColumns = [{"field": "artifact", "label": "Artifact", "width": 230, "minWidth": 230, "sortable": false, "align": "start", formatter: (value: unknown, row: any) => this.artifactCell(value, row, 'Package')}, {"field": "compile", "label": "Compile (s)", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}, {"field": "compileRange", "label": "Min–max (s)", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}, {"field": "package", "label": "Package (s)", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}, {"field": "packageRange", "label": "Min–max (s)", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}, {"field": "baseline", "label": "Baseline", "width": 110, "minWidth": 110, "sortable": false, "align": "start"}, {"field": "difference", "label": "Difference", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}];
                buildSnapshot = [{"variant":"guicedee","goal":"compile","samples":"3","median_seconds":"3.142556519997015","minimum_seconds":"3.1269706189996214","maximum_seconds":"3.321157457001391","baseline":"quarkus-standard","difference_percent":"-14.075859517458866"},{"variant":"guicedee","goal":"package","samples":"3","median_seconds":"3.613382607996755","minimum_seconds":"3.540610115000163","maximum_seconds":"3.7905448519959464","baseline":"quarkus-standard","difference_percent":"-42.55978491099333"},{"variant":"guicedee-scan","goal":"compile","samples":"3","median_seconds":"3.0543127689961693","minimum_seconds":"3.0210860740044154","maximum_seconds":"3.2612780929994187","baseline":"quarkus-standard","difference_percent":"-16.48863026937847"},{"variant":"guicedee-scan","goal":"package","samples":"3","median_seconds":"3.678736948997539","minimum_seconds":"3.6266870079998625","maximum_seconds":"3.6925208379980177","baseline":"quarkus-standard","difference_percent":"-41.520878210170274"},{"variant":"quarkus-standard","goal":"compile","samples":"3","median_seconds":"3.6573616009991383","minimum_seconds":"3.5910385030001635","maximum_seconds":"3.812964363998617","baseline":"quarkus-standard","difference_percent":"0.0"},{"variant":"quarkus-standard","goal":"package","samples":"3","median_seconds":"6.290684326995688","minimum_seconds":"6.185008239001036","maximum_seconds":"6.316454676998546","baseline":"quarkus-standard","difference_percent":"0.0"},{"variant":"guicedee-jlink","goal":"package","samples":"3","median_seconds":"17.68009977899783","minimum_seconds":"17.41920514400408","maximum_seconds":"17.708078821997333","baseline":"quarkus-standard-native","difference_percent":"-73.35666330954163"},{"variant":"guicedee-scan-jlink","goal":"package","samples":"3","median_seconds":"17.342569385000388","minimum_seconds":"17.240374606997648","maximum_seconds":"17.35249384099734","baseline":"quarkus-standard-native","difference_percent":"-73.86531066125058"},{"variant":"guicedee-native","goal":"package","samples":"3","median_seconds":"65.04742507899937","minimum_seconds":"64.96809957900405","maximum_seconds":"80.24446508099936","baseline":"quarkus-standard-native","difference_percent":"-1.975641037621878"},{"variant":"quarkus-standard-native","goal":"package","samples":"3","median_seconds":"66.35842944299657","minimum_seconds":"66.07256183199934","maximum_seconds":"66.66610347099777","baseline":"quarkus-standard-native","difference_percent":"0.0"}];
                buildGridRows = this.makeBuildGrid(this.buildSnapshot);
                runtimeColumns = [{"field": "artifact", "label": "Artifact", "width": 200, "minWidth": 200, "sortable": false, "align": "start", formatter: (value: unknown, row: any) => this.artifactCell(value, row, 'Startup')}, {"field": "starts", "label": "Starts", "width": 40, "minWidth": 40, "sortable": false, "align": "end"}, {"field": "startup", "label": "Startup (s)", "width": 70, "minWidth": 70, "sortable": false, "align": "end"}, {"field": "range", "label": "Min–max (s)", "width": 100, "minWidth": 100, "sortable": false, "align": "end"}, {"field": "warm", "label": "p50 (ms)", "width": 65, "minWidth": 65, "sortable": false, "align": "end"}, {"field": "peak", "label": "Peak (MiB)", "width": 75, "minWidth": 75, "sortable": false, "align": "end"}, {"field": "baseline", "label": "Baseline", "width": 110, "minWidth": 110, "sortable": false, "align": "start"}, {"field": "difference", "label": "Startup Δ", "width": 75, "minWidth": 75, "sortable": false, "align": "end"}];
                runtimeProfile = 0;
                runtimeSources = ['Snapshot · 15 Nov 2026 · default JVM + native', 'Snapshot · 15 Nov 2026 · C1 JVM + native'];
                runtimeGrids = [[{"key":"jvm-defaultguicedee","artifact":"GuicedEE SPI / full JDK","starts":3,"startup":"22.928","range":"21.781–23.969","warm":"91.55","peak":"292.98","baseline":"Quarkus fast-JAR","difference":"-2.0%"},{"key":"jvm-defaultguicedee-scan","artifact":"GuicedEE scanner / full JDK","starts":3,"startup":"23.706","range":"22.859–25.965","warm":"93.71","peak":"301.05","baseline":"Quarkus fast-JAR","difference":"+1.4%"},{"key":"jvm-defaultquarkus-standard","artifact":"Quarkus upstream SDKs / fast-JAR","starts":3,"startup":"23.387","range":"22.469–24.641","warm":"90.58","peak":"279.12","baseline":"Quarkus fast-JAR","difference":"Baseline"},{"key":"jvm-defaultguicedee-jlink","artifact":"GuicedEE SPI / application jlink","starts":3,"startup":"12.966","range":"12.790–13.195","warm":"91.14","peak":"234.10","baseline":"Quarkus fast-JAR","difference":"-44.6%"},{"key":"jvm-defaultguicedee-scan-jlink","artifact":"GuicedEE scanner / application jlink","starts":3,"startup":"13.556","range":"13.153–13.668","warm":"91.29","peak":"225.54","baseline":"Quarkus fast-JAR","difference":"-42.0%"},{"key":"native-standardguicedee-native","artifact":"GuicedEE SPI / native","starts":14,"startup":"0.656","range":"0.614–0.950","warm":"5.21","peak":"68.47","baseline":"Quarkus native","difference":"-3.2%"},{"key":"native-standardquarkus-standard-native","artifact":"Quarkus upstream SDKs / native","starts":14,"startup":"0.678","range":"0.658–1.005","warm":"5.11","peak":"55.30","baseline":"Quarkus native","difference":"Baseline"}],[{"key":"jvm-c1guicedee","artifact":"GuicedEE SPI / full JDK","starts":3,"startup":"13.353","range":"13.166–13.414","warm":"41.30","peak":"258.95","baseline":"Quarkus fast-JAR","difference":"-6.7%"},{"key":"jvm-c1guicedee-scan","artifact":"GuicedEE scanner / full JDK","starts":3,"startup":"13.364","range":"13.334–13.559","warm":"65.39","peak":"262.56","baseline":"Quarkus fast-JAR","difference":"-6.6%"},{"key":"jvm-c1quarkus-standard","artifact":"Quarkus upstream SDKs / fast-JAR","starts":3,"startup":"14.311","range":"14.182–14.500","warm":"23.16","peak":"241.18","baseline":"Quarkus fast-JAR","difference":"Baseline"},{"key":"jvm-c1guicedee-jlink","artifact":"GuicedEE SPI / application jlink","starts":3,"startup":"7.567","range":"7.463–7.718","warm":"45.26","peak":"186.52","baseline":"Quarkus fast-JAR","difference":"-47.1%"},{"key":"jvm-c1guicedee-scan-jlink","artifact":"GuicedEE scanner / application jlink","starts":3,"startup":"7.874","range":"7.869–7.889","warm":"31.09","peak":"190.94","baseline":"Quarkus fast-JAR","difference":"-45.0%"},{"key":"native-standardguicedee-native","artifact":"GuicedEE SPI / native","starts":14,"startup":"0.656","range":"0.614–0.950","warm":"5.21","peak":"68.47","baseline":"Quarkus native","difference":"-3.2%"},{"key":"native-standardquarkus-standard-native","artifact":"Quarkus upstream SDKs / native","starts":14,"startup":"0.678","range":"0.658–1.005","warm":"5.11","peak":"55.30","baseline":"Quarkus native","difference":"Baseline"}]];
                buildMode = 0;
                buildTitles = ['Clean JVM compile · seconds', 'JVM packaging · seconds', 'Native build · seconds'];
                buildValues = [[3.14255652, 3.6573616], [3.61338261, 6.29068433], [65.04742508, 66.35842944]];
                buildSource = 'Snapshot · 15 Nov 2026 · 3 clean builds per task';
                benchmarkRows = this.benchmarkValues.map((values, i) => this.makeBenchmarkRows(values, i));
                benchmarkSummaries = this.benchmarkValues.map((values, i) => this.benchmarkSummary(values, i));
                benchmarkCharts = this.benchmarkValues.map((values, i) => this.benchmarkConfig(values, i));
                """;
    }

    static String methods() {
        return """
                artifactCell(value: unknown, row: any, metric: string): HTMLElement {
                    const cell = document.createElement('span');
                    cell.className = 'artifact-percentage';
                    cell.style.cssText = 'display:block;position:relative;isolation:isolate;min-width:0;padding:.35rem .4rem;border-radius:.3rem;overflow:hidden;';
                    const baseline = row.difference === 'Baseline';
                    const delta = Number.parseFloat(row.difference);
                    const magnitude = Number.isFinite(delta) ? Math.abs(delta) : 0;
                    cell.title = baseline ? `${String(value)} — ${metric} baseline` :
                        `${String(value)} — ${metric} difference ${row.difference} vs ${row.baseline}`;
                    const bar = document.createElement('wa-progress-bar');
                    bar.setAttribute('value', String(Math.min(100, magnitude)));
                    bar.setAttribute('aria-hidden', 'true');
                    bar.setAttribute('data-difference', row.difference);
                    bar.style.cssText = 'display:block;position:absolute;inset:0;z-index:-1;pointer-events:none;--track-height:100%;--track-color:transparent;';
                    bar.style.setProperty('--indicator-color', delta > 0 ? '#a78bfa40' : '#10b98140');
                    if (baseline) cell.style.background = 'var(--wa-color-neutral-fill-quiet)';
                    const label = document.createElement('span');
                    label.textContent = String(value);
                    label.style.cssText = 'position:relative;line-height:1.2;';
                    cell.append(bar, label);
                    return cell;
                }
                makeBenchmarkRows(values: number[] | null, index: number): any[] {
                    return values ? values.map((value, i) => ({ key: `${index}:${this.benchmarkLabels[index][i]}:${value}`, framework: this.benchmarkLabels[index][i], value: `${value.toFixed(2)} ${this.benchmarkUnits[index]}` })) : [];
                }
                makeBuildGrid(rows: Record<string, string>[]): any[] {
                    const variants = [
                        ['guicedee', 'GuicedEE SPI / full JDK'],
                        ['guicedee-scan', 'GuicedEE scanner / full JDK'],
                        ['quarkus-standard', 'Quarkus upstream SDKs / fast-JAR'],
                        ['guicedee-jlink', 'GuicedEE SPI / application jlink'],
                        ['guicedee-scan-jlink', 'GuicedEE scanner / application jlink'],
                        ['guicedee-native', 'GuicedEE SPI / native'],
                        ['quarkus-standard-native', 'Quarkus upstream SDKs / native']
                    ];
                    const validate = (row: Record<string, string>) => {
                        if (!row) throw new Error('Missing build row');
                        const values = ['minimum_seconds', 'median_seconds', 'maximum_seconds'].map(key => Number(row[key]));
                        if (!values.every(value => Number.isFinite(value) && value > 0) || values[0] > values[1] || values[1] > values[2]) throw new Error('Invalid build range');
                    };
                    const format = (row: Record<string, string>, key: string) => Number(row[key]).toFixed(2);
                    const range = (row: Record<string, string>) => `${format(row, 'minimum_seconds')}–${format(row, 'maximum_seconds')}`;
                    return variants.map(([variant, artifact], i) => {
                        const packaged = rows.find(row => row['variant'] === variant && row['goal'] === 'package')!;
                        const compiled = rows.find(row => row['variant'] === variant && row['goal'] === 'compile');
                        validate(packaged);
                        if (i < 3) validate(compiled!);
                        const baseline = i < 3 ? 'quarkus-standard' : 'quarkus-standard-native';
                        if (packaged['baseline'] !== baseline) throw new Error('Unexpected build baseline');
                        const delta = Number(packaged['difference_percent']);
                        if (!Number.isFinite(delta)) throw new Error('Invalid build difference');
                        return { key: JSON.stringify(packaged) + JSON.stringify(compiled), artifact,
                            compile: compiled ? format(compiled, 'median_seconds') : '—',
                            compileRange: compiled ? range(compiled) : '—',
                            package: format(packaged, 'median_seconds'), packageRange: range(packaged),
                            baseline: i < 3 ? 'Quarkus fast-JAR' : 'Quarkus native',
                            difference: variant === baseline ? 'Baseline' : `${delta > 0 ? '+' : ''}${delta.toFixed(1)}%`
                        };
                    });
                }
                makeRuntimeGrid(data: any, profile: string): any[] {
                    const groups = [
                        { name: profile, ids: ['guicedee', 'guicedee-scan', 'quarkus-standard', 'guicedee-jlink', 'guicedee-scan-jlink'], baseline: 'quarkus-standard' },
                        { name: 'native-standard', ids: ['guicedee-native', 'quarkus-standard-native'], baseline: 'quarkus-standard-native' }
                    ];
                    return groups.flatMap(group => {
                        const variants = data.groups[group.name].variants;
                        const baseline = variants[group.baseline].launchToFirstBusinessMs.median;
                        if (!Number.isFinite(baseline) || baseline <= 0) throw new Error('Invalid runtime baseline');
                        return group.ids.map(id => {
                            const v = variants[id], startup = v.launchToFirstBusinessMs;
                            const values = [startup.minimum, startup.median, startup.maximum, v.warm.p50Ms, v.peakMiB.median];
                            if (!values.every(value => typeof value === 'number' && Number.isFinite(value) && value > 0) ||
                                startup.minimum > startup.median || startup.median > startup.maximum ||
                                !Number.isInteger(v.starts) || v.starts < 1 || typeof v.label !== 'string') throw new Error('Invalid runtime row');
                            const delta = (startup.median / baseline - 1) * 100;
                            return { key: group.name + id + JSON.stringify(values) + ':' + delta, artifact: v.label, starts: v.starts,
                                startup: (startup.median / 1000).toFixed(3),
                                range: `${(startup.minimum / 1000).toFixed(3)}–${(startup.maximum / 1000).toFixed(3)}`,
                                warm: v.warm.p50Ms.toFixed(2), peak: v.peakMiB.median.toFixed(2),
                                baseline: group.name === 'native-standard' ? 'Quarkus native' : 'Quarkus fast-JAR',
                                difference: id === group.baseline ? 'Baseline' : `${delta > 0 ? '+' : ''}${delta.toFixed(1)}%`
                            };
                        });
                    });
                }
                selectBuildMode(mode: number) {
                    this.buildMode = mode;
                    this.benchmarkLabels[1] = [mode === 2 ? 'GuicedEE native' : 'GuicedEE SPI', mode === 2 ? 'Quarkus native' : 'Quarkus fast-JAR'];
                    this.updateBenchmark(1, this.buildValues[mode], this.buildSource);
                }
                benchmarkSummary(values: number[] | null, index: number): string {
                    if (!values) return 'The next chapter is taking shape. Published PostgreSQL results will appear here.';
                    return `GuicedEE ${values[0].toFixed(2)} ${this.benchmarkUnits[index]} · Quarkus ${values[1].toFixed(2)} ${this.benchmarkUnits[index]}`;
                }
                benchmarkConfig(values: number[] | null, index: number): any {
                    return { type: 'bar', data: { labels: this.benchmarkLabels[index], datasets: [{
                        label: this.benchmarkUnits[index], data: values || [],
                        backgroundColor: ['#10b981', '#818cf8'], borderRadius: 8, maxBarThickness: 42
                    }] }, options: { indexAxis: 'y', responsive: true, maintainAspectRatio: false,
                        animation: false, plugins: { legend: { display: false } },
                        scales: { x: { beginAtZero: true, ticks: { color: '#64748b' }, grid: { color: '#94a3b822' } },
                                  y: { ticks: { color: '#64748b' }, grid: { display: false } } }
                    } };
                }
                updateBenchmark(index: number, values: number[], source: string) {
                    if (values.length !== 2 || !values.every(value => typeof value === 'number' && Number.isFinite(value) && value > 0)) {
                        throw new Error('Invalid benchmark measurements');
                    }
                    this.benchmarkValues[index] = values;
                    this.benchmarkRows[index] = this.makeBenchmarkRows(values, index);
                    this.benchmarkCharts[index] = this.benchmarkConfig(values, index);
                    this.benchmarkSummaries[index] = this.benchmarkSummary(values, index);
                    this.benchmarkSources[index] = source;
                }
                async loadBenchmarks() {
                    // Fetch once per page visit; independent datasets fail independently.
                    const base = 'https://raw.githubusercontent.com/GuicedEE/benchmarks/master/docs/';
                    const read = async (file: string) => {
                        const response = await fetch(base + file, { signal: AbortSignal.timeout(8000) });
                        if (!response.ok) throw new Error('Benchmark source unavailable');
                        return response;
                    };
                    await Promise.allSettled([
                        (async () => {
                            try {
                                const data = await (await read('current-performance.json')).json();
                                const grids = [this.makeRuntimeGrid(data, 'jvm-default'), this.makeRuntimeGrid(data, 'jvm-c1')];
                                this.runtimeGrids = grids;
                                this.runtimeSources = [`Published ${data.measuredOn} · default JVM + native`, `Published ${data.measuredOn} · C1 JVM + native`];
                                const variants = data.groups['native-standard'].variants;
                                const g = variants['guicedee-native'], q = variants['quarkus-standard-native'];
                                const source = `Published ${data.measuredOn} · ${g.starts} / ${q.starts} starts (GuicedEE / Quarkus)`;
                                this.updateBenchmark(0, [g.launchToFirstBusinessMs.median, q.launchToFirstBusinessMs.median], source);
                                this.updateBenchmark(2, [g.peakMiB.median, q.peakMiB.median], source);

                            } catch { /* Keep the labelled snapshot, never replace a failure with zero. */ }
                        })(),
                        (async () => {
                            try {
                                const csv = await (await read('build-performance.csv')).text();
                                const lines = csv.trim().split(/\\r?\\n/).map(line => line.split(','));
                                const header = lines.shift()!;
                                const rows = lines.map(row => Object.fromEntries(header.map((key, i) => [key, row[i]])));
                                const row = (variant: string, goal: string) => rows.find(item => item['variant'] === variant && item['goal'] === goal)!;
                                const gridRows = this.makeBuildGrid(rows);
                                const measured = [['guicedee', 'compile'], ['quarkus-standard', 'compile'], ['guicedee', 'package'], ['quarkus-standard', 'package'], ['guicedee-native', 'package'], ['quarkus-standard-native', 'package']].map(([variant, goal]) => row(variant, goal));
                                if (measured[4]['baseline'] !== 'quarkus-standard-native') throw new Error('Unsupported native build baseline');
                                const numbers = measured.map(item => Number(item['median_seconds']));
                                if (!numbers.every(value => Number.isFinite(value) && value > 0)) throw new Error('Invalid build measurements');
                                this.buildGridRows = gridRows;
                                this.buildValues = [[numbers[0], numbers[1]], [numbers[2], numbers[3]], [numbers[4], numbers[5]]];
                                this.buildSource = `Published build dataset · ${[...new Set(measured.map(item => item['samples']))].join('–')} clean builds per task`;
                                this.selectBuildMode(this.buildMode);
                            } catch { /* Keep the dated snapshot. */ }
                        })(),
                        (async () => {
                            try {
                                const data = await (await read('persistence-performance.json')).json();
                                if (data.profile !== 'default') throw new Error('Unsupported persistence profile');
                                this.updateBenchmark(3, [data.variants.guicedee.cycle.p50Ms, data.variants.quarkus.cycle.p50Ms],
                                    `Published ${data.measuredAt.slice(0, 10)} · ${data.runs} starts per framework · default JVM`);
                            } catch { /* Persistence stays explicitly pending until its public dataset is available. */ }
                        })()
                    ]);
                }
                """;
    }
}
