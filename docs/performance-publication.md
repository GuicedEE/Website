# Published benchmark data

The homepage reads the current datasets from `GuicedEE/benchmarks` on `master` once
per visit. Its dated snapshots come from those same validated datasets and remain
visible when a request fails. Refresh them before rebuilding the website:

```shell
python tools/sync-benchmark-snapshots.py /path/to/benchmarks
python tools/sync-benchmark-snapshots.py /path/to/benchmarks --check
python tools/verify-benchmarks.py /path/to/benchmarks
python tools/verify-benchmarks.py /path/to/benchmarks --url https://guicedee.com/home
```

The current snapshot uses benchmark revision `c022ced`: SDK runtime/build results
from 15 November 2026 and persistence batch `20261116-112315` from 16 November 2026.
Runtime, memory, build and persistence charts and grids include GuicedEE, Quarkus
and Micronaut. Quarkus remains the baseline for the table percentages.

Persistence offers startup, joined-read and CRUD-cycle views. Startup includes the
first committed 15-entity graph; it is not HTTP-port readiness. CRUD cycles include
three write transactions and one read across four HTTP requests. All are default-JVM
results at 0.25 CPU / 500 MiB per application. Instrumented phase timings are excluded.

The generated Angular production build and public browser checks verify the chart
values, labels and source dates. Website deployment is independent of framework
artifact publication and leaves the website backend unchanged.

## Current deployment — 16 November 2026

Website source `acab33534de103136f4be737d1ba5f04d07ed729` is now live in revision
`guicedee-website--v260-20261116-acab335`, with benchmark snapshot `c022ced`.
Public checks verified the three-framework charts, build modes, complete grids
and current published datasets. All eight homepage slides use a stable-height
scroller. See the [complete deployment record](website-publication-2026-11-16.md)
for the image, builder/documentation changes and public verification.

## Earlier deployment — 16 November 2026

The production front end at [guicedee.com](https://guicedee.com/home) serves the
build from website commit `b63114d600519d5b303b3fcad5ee065f6772e032`, using benchmark
revision `3d97924`. Azure Container Apps revision `guicedee-website--perf-20261116`
is ready and receives 100% of traffic.

Image: `docker.io/gedmarc/guicedee-website@sha256:819ff571e023dd478a2fb04c0d458d2016f64f29c08d4e87b3cc4ec90368d4aa`.

Verification completed:

- Maven generation and Angular production build passed.
- All 153 live assets match the production build by SHA-256.
- `/`, `/home`, `/releases`, and `/modules` return the matching SPA index with
  `Cache-Control: no-store`.
- Public Chromium checks verified startup/read/CRUD chart values and units,
  published source dates, nine-row runtime/build grids, Micronaut rows, and normal
  page refresh, with no JavaScript errors.
- Local browser checks also passed with unavailable and invalid dataset responses.

The public persistence startup chart shows GuicedEE **14.45 s** and Quarkus
**11.85 s**. The underlying dataset includes Micronaut at **15.45 s**. These are
three-start medians under the quota above, not unrestricted-machine startup times.
The SDK figures retain their 15 November measurement date.

Local deployment evidence is retained in DevSuite under
`benchmarks/target/hibernate-startup-20261116/`; browser evidence is under the
isolated website worktree's `target/public-benchmark-verification/`. Generated
build outputs and screenshots are not committed to the repository.
