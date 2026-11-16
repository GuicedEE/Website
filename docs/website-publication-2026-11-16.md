# Website publication — 16 November 2026

The updated frontend is live at [guicedee.com](https://guicedee.com/home).
The initial publication used Azure revision
`guicedee-website--v260-20261116-acab335`, verified healthy and ready with 100%
of traffic before the release-card follow-up recorded below.

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

## Release-card layout follow-up

Source `05a506347a9fd8e562f3dd54c51f027853f1f7ff` replaces the 2.6.0 highlights'
vertical stack with a responsive grid. The five cards use matching row heights
and aligned notes. Public browser checks passed at 1440, 1024 and 390 pixels:
three, two and one column respectively, without horizontal page overflow.

Revision `guicedee-website--v260-20261116-05a5063` was verified healthy and ready
with 100% of traffic before the history-layout follow-up below.
Image tag: `gedmarc/guicedee-website:2.6.0-20261116-05a5063`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:f439099483a9d6d5d733d657262e4d255313e6286ec512b24bb24364c4eff01d`.

The production build passed and all 298 live assets match this build. Evidence,
layout measurements and screenshots are retained in DevSuite under
`target/website-release-cards/`.

## Release history through 2.4.0

Source `2c64599537eaa946025af880feb187bc90e30ea0` applies the same shared grid to
2.5.1, 2.5.0, 2.4.2, 2.4.1 and all three 2.4.0 sections. Unique anchors restore
the ClassGraph and clustering sections previously omitted during generation due
to duplicate IDs. The ClassGraph guide link remains below its cards.

Revision `guicedee-website--v260-20261116-2c64599` is healthy, ready and serves
100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-2c64599`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:b164286d1e24ff6fb39910dcef5738888db5719fc9026091e5debe1a59ff2c59`.

Local and public browser checks passed all eight grids and 28 cards at 1440,
1024 and 390 pixels, including equal row heights, aligned notes, unique anchors
and no horizontal page overflow. All 298 live assets match the production build.
Evidence is retained under `target/website-release-history/` in DevSuite.
