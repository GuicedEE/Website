package com.guicedee.website.pages;

/** Browser-side presentation of the benchmark repository's published summary contracts. */
final class BenchmarkShowcase {
    private BenchmarkShowcase() {}

    static String fields() {
        return """
                showcaseIndex = 0;
                persistenceMode = 0;
                persistenceTitles = ['Launch to committed graph · seconds', 'Joined read, warm p50 · milliseconds', 'CRUD cycle, warm p50 · milliseconds'];
                persistenceUnits = ['s', 'ms', 'ms'];
                persistenceValues: number[][] = [[14.448355600001378,11.852085200000147],[2.58150000081514,2.5103000007220544],[87.94330000091577,83.79690000219853]];
                persistenceSource = "Snapshot · 2026-11-16 · 3 starts per framework · default JVM";
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
                benchmarkValues: (number[] | null)[] = [[654.6762000000399,681.3458999997692],[3.517444553000132,4.01462643900004],[68.56640625,55.302734375],[14.448355600001378,11.852085200000147]];
                benchmarkUnits = ["ms","s","MiB","s"];
                benchmarkSources = ["Snapshot · 2026-11-15 · 6 / 6 starts (GuicedEE / Quarkus)","Snapshot · 2026-11-15 · 3 clean builds per task","Snapshot · 2026-11-15 · 6 / 6 starts (GuicedEE / Quarkus)","Snapshot · 2026-11-16 · 3 starts per framework · default JVM"];
                benchmarkLabels = [['GuicedEE native', 'Quarkus native'], ['GuicedEE SPI', 'Quarkus fast-JAR'], ['GuicedEE native', 'Quarkus native'], ['GuicedEE', 'Quarkus']];
                benchmarkColumns = [{field: 'framework', label: 'Application', width: 128, minWidth: 128}, {field: 'value', label: 'Median', align: 'end'}];
                buildGridColumns = [{"field": "artifact", "label": "Artifact", "width": 230, "minWidth": 230, "sortable": false, "align": "start", formatter: (value: unknown, row: any) => this.artifactCell(value, row, 'Package')}, {"field": "compile", "label": "Compile (s)", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}, {"field": "compileRange", "label": "Min–max (s)", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}, {"field": "package", "label": "Package (s)", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}, {"field": "packageRange", "label": "Min–max (s)", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}, {"field": "baseline", "label": "Baseline", "width": 110, "minWidth": 110, "sortable": false, "align": "start"}, {"field": "difference", "label": "Difference", "width": 80, "minWidth": 80, "sortable": false, "align": "end"}];
                buildSnapshot = [{"variant":"guicedee","goal":"compile","samples":"3","median_seconds":"3.517444553000132","minimum_seconds":"3.4693496999998388","maximum_seconds":"3.9054237410000496","baseline":"quarkus-standard","difference_percent":"-12.384262734137351"},{"variant":"guicedee","goal":"package","samples":"3","median_seconds":"4.097233871999833","minimum_seconds":"3.915044496000064","maximum_seconds":"4.189725716000339","baseline":"quarkus-standard","difference_percent":"-39.704721519537586"},{"variant":"guicedee-scan","goal":"compile","samples":"3","median_seconds":"3.722911505999946","minimum_seconds":"3.3272784890000366","maximum_seconds":"3.8096864780000033","baseline":"quarkus-standard","difference_percent":"-7.26630328954726"},{"variant":"guicedee-scan","goal":"package","samples":"3","median_seconds":"3.8845480259997203","minimum_seconds":"3.76343395899994","maximum_seconds":"4.470477734999804","baseline":"quarkus-standard","difference_percent":"-42.834626405140384"},{"variant":"quarkus-standard","goal":"compile","samples":"3","median_seconds":"4.01462643900004","minimum_seconds":"3.8710077129999263","maximum_seconds":"4.46697576600036","baseline":"quarkus-standard","difference_percent":"0.0"},{"variant":"quarkus-standard","goal":"package","samples":"3","median_seconds":"6.795281446999979","minimum_seconds":"6.657370422999975","maximum_seconds":"6.891672325999934","baseline":"quarkus-standard","difference_percent":"0.0"},{"variant":"guicedee-jlink","goal":"package","samples":"3","median_seconds":"18.24622751700008","minimum_seconds":"18.139327116000004","maximum_seconds":"19.4553061900001","baseline":"quarkus-standard-native","difference_percent":"-74.26737501375922"},{"variant":"guicedee-scan-jlink","goal":"package","samples":"3","median_seconds":"17.877413955000065","minimum_seconds":"17.413830298999983","maximum_seconds":"18.44327150199979","baseline":"quarkus-standard-native","difference_percent":"-74.78751218030193"},{"variant":"guicedee-native","goal":"package","samples":"3","median_seconds":"67.85807544100044","minimum_seconds":"66.24609463600018","maximum_seconds":"86.94487847899973","baseline":"quarkus-standard-native","difference_percent":"-4.299866589716183"},{"variant":"quarkus-standard-native","goal":"package","samples":"3","median_seconds":"70.90698102799979","minimum_seconds":"68.84245245300008","maximum_seconds":"85.87814577400013","baseline":"quarkus-standard-native","difference_percent":"0.0"},{"variant":"micronaut","goal":"compile","samples":"3","median_seconds":"5.684916422000242","minimum_seconds":"5.119923835000009","maximum_seconds":"5.815816701000131","baseline":"quarkus-standard","difference_percent":"41.60511590254552"},{"variant":"micronaut","goal":"package","samples":"3","median_seconds":"5.85218265200001","minimum_seconds":"5.5883897690000595","maximum_seconds":"6.479035076999935","baseline":"quarkus-standard","difference_percent":"-13.87873044487854"},{"variant":"micronaut-native","goal":"package","samples":"3","median_seconds":"92.23726224699976","minimum_seconds":"84.90550196699996","maximum_seconds":"95.62608343200009","baseline":"quarkus-standard-native","difference_percent":"30.08206090536707"}];
                buildGridRows = this.makeBuildGrid(this.buildSnapshot);
                runtimeColumns = [{"field": "artifact", "label": "Artifact", "width": 200, "minWidth": 200, "sortable": false, "align": "start", formatter: (value: unknown, row: any) => this.artifactCell(value, row, 'Startup')}, {"field": "starts", "label": "Starts", "width": 40, "minWidth": 40, "sortable": false, "align": "end"}, {"field": "startup", "label": "Startup (s)", "width": 70, "minWidth": 70, "sortable": false, "align": "end"}, {"field": "range", "label": "Min–max (s)", "width": 100, "minWidth": 100, "sortable": false, "align": "end"}, {"field": "warm", "label": "p50 (ms)", "width": 65, "minWidth": 65, "sortable": false, "align": "end"}, {"field": "peak", "label": "Peak (MiB)", "width": 75, "minWidth": 75, "sortable": false, "align": "end"}, {"field": "baseline", "label": "Baseline", "width": 110, "minWidth": 110, "sortable": false, "align": "start"}, {"field": "difference", "label": "Startup Δ", "width": 75, "minWidth": 75, "sortable": false, "align": "end"}];
                runtimeProfile = 0;
                runtimeSources = ["Snapshot · 2026-11-15 · default JVM + native","Snapshot · 2026-11-15 · C1 JVM + native"];
                runtimeGrids = [[{"key":"jvm-defaultguicedee","artifact":"GuicedEE SPI / full JDK","starts":3,"startup":"22.367","range":"22.231–23.777","warm":"76.74","peak":"313.46","baseline":"Quarkus fast-JAR","difference":"-4.1%"},{"key":"jvm-defaultguicedee-scan","artifact":"GuicedEE scanner / full JDK","starts":3,"startup":"23.862","range":"22.853–24.538","warm":"82.87","peak":"309.30","baseline":"Quarkus fast-JAR","difference":"+2.3%"},{"key":"jvm-defaultquarkus-standard","artifact":"Quarkus upstream SDKs / fast-JAR","starts":3,"startup":"23.320","range":"21.960–23.388","warm":"38.33","peak":"278.00","baseline":"Quarkus fast-JAR","difference":"Baseline"},{"key":"jvm-defaultmicronaut","artifact":"Micronaut upstream SDKs / JVM","starts":3,"startup":"26.573","range":"26.089–26.938","warm":"75.39","peak":"305.62","baseline":"Quarkus fast-JAR","difference":"+13.9%"},{"key":"jvm-defaultguicedee-jlink","artifact":"GuicedEE SPI / application jlink","starts":3,"startup":"13.005","range":"12.027–13.165","warm":"76.32","peak":"231.16","baseline":"Quarkus fast-JAR","difference":"-44.2%"},{"key":"jvm-defaultguicedee-scan-jlink","artifact":"GuicedEE scanner / application jlink","starts":3,"startup":"13.195","range":"12.827–13.280","warm":"74.42","peak":"229.09","baseline":"Quarkus fast-JAR","difference":"-43.4%"},{"key":"native-standardguicedee-native","artifact":"GuicedEE SPI / native","starts":6,"startup":"0.655","range":"0.620–0.980","warm":"4.93","peak":"68.57","baseline":"Quarkus native","difference":"-3.9%"},{"key":"native-standardquarkus-standard-native","artifact":"Quarkus upstream SDKs / native","starts":6,"startup":"0.681","range":"0.661–0.725","warm":"5.19","peak":"55.30","baseline":"Quarkus native","difference":"Baseline"},{"key":"native-standardmicronaut-native","artifact":"Micronaut upstream SDKs / native","starts":6,"startup":"0.627","range":"0.607–0.665","warm":"5.25","peak":"85.26","baseline":"Quarkus native","difference":"-8.0%"}],[{"key":"jvm-c1guicedee","artifact":"GuicedEE SPI / full JDK","starts":3,"startup":"13.290","range":"13.051–13.315","warm":"11.70","peak":"261.80","baseline":"Quarkus fast-JAR","difference":"-6.3%"},{"key":"jvm-c1guicedee-scan","artifact":"GuicedEE scanner / full JDK","starts":3,"startup":"13.480","range":"12.923–13.852","warm":"10.42","peak":"264.52","baseline":"Quarkus fast-JAR","difference":"-4.9%"},{"key":"jvm-c1quarkus-standard","artifact":"Quarkus upstream SDKs / fast-JAR","starts":3,"startup":"14.177","range":"13.869–14.787","warm":"10.21","peak":"242.61","baseline":"Quarkus fast-JAR","difference":"Baseline"},{"key":"jvm-c1micronaut","artifact":"Micronaut upstream SDKs / JVM","starts":3,"startup":"15.658","range":"15.651–16.115","warm":"9.30","peak":"258.56","baseline":"Quarkus fast-JAR","difference":"+10.4%"},{"key":"jvm-c1guicedee-jlink","artifact":"GuicedEE SPI / application jlink","starts":3,"startup":"8.300","range":"7.263–8.401","warm":"10.42","peak":"190.82","baseline":"Quarkus fast-JAR","difference":"-41.5%"},{"key":"jvm-c1guicedee-scan-jlink","artifact":"GuicedEE scanner / application jlink","starts":3,"startup":"7.777","range":"7.504–7.928","warm":"9.60","peak":"194.20","baseline":"Quarkus fast-JAR","difference":"-45.1%"},{"key":"native-standardguicedee-native","artifact":"GuicedEE SPI / native","starts":6,"startup":"0.655","range":"0.620–0.980","warm":"4.93","peak":"68.57","baseline":"Quarkus native","difference":"-3.9%"},{"key":"native-standardquarkus-standard-native","artifact":"Quarkus upstream SDKs / native","starts":6,"startup":"0.681","range":"0.661–0.725","warm":"5.19","peak":"55.30","baseline":"Quarkus native","difference":"Baseline"},{"key":"native-standardmicronaut-native","artifact":"Micronaut upstream SDKs / native","starts":6,"startup":"0.627","range":"0.607–0.665","warm":"5.25","peak":"85.26","baseline":"Quarkus native","difference":"-8.0%"}]];
                buildMode = 0;
                buildTitles = ['Clean JVM compile · seconds', 'JVM packaging · seconds', 'Native build · seconds'];
                buildValues = [[3.517444553000132,4.01462643900004],[4.097233871999833,6.795281446999979],[67.85807544100044,70.90698102799979]];
                buildSource = "Snapshot · 2026-11-15 · 3 clean builds per task";
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
                        ['micronaut', 'Micronaut upstream SDKs / JVM'],
                        ['guicedee-jlink', 'GuicedEE SPI / application jlink'],
                        ['guicedee-scan-jlink', 'GuicedEE scanner / application jlink'],
                        ['guicedee-native', 'GuicedEE SPI / native'],
                        ['quarkus-standard-native', 'Quarkus upstream SDKs / native'],
                        ['micronaut-native', 'Micronaut upstream SDKs / native']
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
                        const jvm = ['guicedee', 'guicedee-scan', 'quarkus-standard', 'micronaut'].includes(variant);
                        if (jvm) validate(compiled!);
                        const baseline = jvm ? 'quarkus-standard' : 'quarkus-standard-native';
                        if (packaged['baseline'] !== baseline) throw new Error('Unexpected build baseline');
                        const delta = Number(packaged['difference_percent']);
                        if (!Number.isFinite(delta)) throw new Error('Invalid build difference');
                        return { key: JSON.stringify(packaged) + JSON.stringify(compiled), artifact,
                            compile: compiled ? format(compiled, 'median_seconds') : '—',
                            compileRange: compiled ? range(compiled) : '—',
                            package: format(packaged, 'median_seconds'), packageRange: range(packaged),
                            baseline: jvm ? 'Quarkus fast-JAR' : 'Quarkus native',
                            difference: variant === baseline ? 'Baseline' : `${delta > 0 ? '+' : ''}${delta.toFixed(1)}%`
                        };
                    });
                }
                makeRuntimeGrid(data: any, profile: string): any[] {
                    const groups = [
                        { name: profile, ids: ['guicedee', 'guicedee-scan', 'quarkus-standard', 'micronaut', 'guicedee-jlink', 'guicedee-scan-jlink'], baseline: 'quarkus-standard' },
                        { name: 'native-standard', ids: ['guicedee-native', 'quarkus-standard-native', 'micronaut-native'], baseline: 'quarkus-standard-native' }
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
                selectPersistenceMode(mode: number) {
                    this.persistenceMode = mode;
                    this.benchmarkUnits[3] = this.persistenceUnits[mode];
                    this.updateBenchmark(3, this.persistenceValues[mode], this.persistenceSource);
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
                                if (data.profile !== 'default' || !Number.isInteger(data.runs) || data.runs < 1) throw new Error('Unsupported persistence profile');
                                const g = data.variants.guicedee, q = data.variants.quarkus;
                                const values = [[g.launchToFirstTransactionMs.median / 1000, q.launchToFirstTransactionMs.median / 1000],
                                    [g.read.p50Ms, q.read.p50Ms], [g.cycle.p50Ms, q.cycle.p50Ms]];
                                if (!values.flat().every(value => typeof value === 'number' && Number.isFinite(value) && value > 0)) throw new Error('Invalid persistence measurements');
                                const source = `Published ${data.measuredAt.slice(0, 10)} · ${data.runs} starts per framework · default JVM`;
                                this.persistenceValues = values;
                                this.persistenceSource = source;
                                this.selectPersistenceMode(this.persistenceMode);
                            } catch { /* Keep the labelled snapshot of verified published measurements. */ }
                        })()
                    ]);
                }
                """;
    }
}
