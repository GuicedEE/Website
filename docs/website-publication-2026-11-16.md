# Website publication — 16 November 2026

The updated frontend is live at [guicedee.com](https://guicedee.com/home).
Azure revision `guicedee-website--v260-20261116-acab335` is healthy, ready and
receives 100% of traffic.

- Website source: `acab33534de103136f4be737d1ba5f04d07ed729`, pushed to `master`.
- Image tag: `gedmarc/guicedee-website:2.6.0-20261116-acab335`.
- Deployed immutable image: `docker.io/gedmarc/guicedee-website@sha256:1180dfd86a8ba54b14a93b43e0958f994f4318a2a5613bbcca4033ac340cd061`.
- Benchmark snapshot: `c022ced33073fdf388cfdb3bdf2bf6258d7a0ee2`.

The builder places Runtime target beside Language in the responsive metadata
grid. Service Discovery and its five options are nested under Cloud SDKs.
The homepage uses vertical scrollers to keep all eight slides at a stable height,
includes the persistence example and simplified CDI excerpt, and shows Micronaut
alongside GuicedEE and Quarkus. The existing WebAssembly research preview retains
its experimental status and validation limits.

The site now publishes `llms.txt`, the complete reference, 127 component documents
with actual Maven/JPMS metadata and APIs, and Markdown versions of all ten main
documentation pages. robots.txt, sitemap.xml, canonical/alternate links, metadata,
short caching for mutable resources, HTTP-to-HTTPS redirects and proper 404s are
part of the deployed nginx/frontend configuration.

## Verification

- Maven generation, Angular production build and Docker packaging passed.
- The packaged image passed 186 HTTP checks covering crawler resources, every
  index link, canonical routes, cache policies, redirects, errors and metadata.
- Local browser checks passed all three benchmark-data scenarios, all eight
  carousel slides, search/reveal/navigation, and Kotlin Maven/Gradle ZIP contents.
- Language and Runtime target share a row at desktop/tablet widths; mobile has
  no horizontal page overflow after responsive layout settles.
- All 298 publicly served assets match the production build. The HTML comparison
  accounts for nginx's route-specific canonical/alternate/Open Graph injection.
- All ten sitemap routes return their matching canonical URL. HTTP redirects to
  HTTPS, missing pages/resources return 404, and public documentation GET/HEAD
  requests succeed.
- Live Chromium checks verified the builder layout, nested discovery options,
  selected dependencies in downloaded projects, responsive layout and benchmark
  values with no JavaScript errors.

Build inputs, image upload/deployment metadata, public asset hashes, screenshots
and browser evidence are retained under
`target/website-publish-20261116/` in the DevSuite workspace. Local check logs use
the `target/website-publish-*` prefix. The local preview remains available at
`http://localhost:4200/builder` while its preview container is running.

This publication updates the static frontend. It does not publish GuicedEE
framework artifacts to Maven Central or redeploy the separate website backend.
