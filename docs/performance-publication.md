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

The current snapshot uses benchmark revision `3d97924`: SDK runtime/build results
from 15 November 2026 and persistence batch `20261116-093613` from 16 November 2026.
Runtime and build grids include GuicedEE, Quarkus and Micronaut. The headline charts
retain Quarkus as their primary comparison.

Persistence offers startup, joined-read and CRUD-cycle views. Startup includes the
first committed 15-entity graph; it is not HTTP-port readiness. CRUD cycles include
three write transactions and one read across four HTTP requests. All are default-JVM
results at 0.25 CPU / 500 MiB per application. Instrumented phase timings are excluded.

The generated Angular production build and public browser checks verify the chart
values, labels and source dates. Website deployment is independent of framework
artifact publication and leaves the website backend unchanged.
