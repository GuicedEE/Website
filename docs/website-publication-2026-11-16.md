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

Revision `guicedee-website--v260-20261116-2c64599` was verified healthy and ready
with 100% of traffic before the homepage follow-up below.
Image tag: `gedmarc/guicedee-website:2.6.0-20261116-2c64599`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:b164286d1e24ff6fb39910dcef5738888db5719fc9026091e5debe1a59ff2c59`.

Local and public browser checks passed all eight grids and 28 cards at 1440,
1024 and 390 pixels, including equal row heights, aligned notes, unique anchors
and no horizontal page overflow. All 298 live assets match the production build.
Evidence is retained under `target/website-release-history/` in DevSuite.

## Developer selector and native comparison

Source `4ed17e874feed77071244f32d04df8146b0c2471` shortens the homepage selector
to Developer and explicitly names GuicedEE, Quarkus and Micronaut in the
Native-Image caption. Micronaut was already included in the published chart and
table; the comparison data and its measurement date remain unchanged.

Revision `guicedee-website--v260-20261116-4ed17e8` was verified healthy and ready
with 100% of traffic before the navigation follow-up below.
Image tag: `gedmarc/guicedee-website:2.6.0-20261116-4ed17e8`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:df28ba06a83a34d1f90cc34fae6e39a80b64510b64951e70bfcc74c0ab2fa293`.

The production build and three local browser scenarios passed, including
unavailable and invalid benchmark responses. Public checks verified the label,
all three framework values in both native charts and their visible table rows,
build modes and complete grids. Native peak container memory is 68.57 MiB for
GuicedEE, 55.30 MiB for Quarkus and 85.26 MiB for Micronaut. All 298 live assets
match the production build. Evidence is retained under
`target/website-native-comparison/` in DevSuite.

## Homepage section navigation

Source `af6b645552fe8219190ac93050e5beba849b4fe5` moves Highlights & benchmarks,
How it works, IntelliJ plugin and Explore the platform under Home in the desktop
menu and mobile drawer. The homepage's right sidebar and reserved column are
removed. Documentation sidebars still activate normally. Section links support
keyboard navigation, refresh/back navigation and the mobile drawer; Explore the
platform opens its disclosure. Old `/home(aside:home)` bookmarks are normalized
while preserving query parameters and fragments.

Revision `guicedee-website--v260-20261116-af6b645` was verified healthy and ready
with 100% of traffic before the page-navigation follow-up below.
Image tag: `gedmarc/guicedee-website:2.6.0-20261116-af6b645`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:0077a0d727a5d05e6c69176d77722b0562f5881f7a19a07aa210eca5300ffd98`.

The production build passed. Local and public navigation checks covered 1440,
1024 and 390 pixels, all four destinations, legacy URLs and documentation aside
transitions. Search checks and all three local benchmark scenarios passed.
Benchmark verification uses revision `f5770f2`, matching the persistence snapshot
already committed in `f60400e`. All 298 public assets match the production build.
Evidence is retained under `target/website-home-navigation/` in DevSuite.

## Section navigation across all pages

Source `7f9b496dcb8b1251c58648b8304cf50bb58acfdd` moves all 106 section links
under their page in a shared desktop/mobile navigation tree. Opening another
branch collapses the previous branch. Route changes, search results and browser
history open only the destination page's branch. Active pages and sections are
marked, and the desktop menu scrolls independently for long lists.

The right sidebar is removed throughout the site. Modules, Services and
Environment Variables keep their filters within the page content; section links
clear a filter when needed to reveal their destination. Empty compatibility
routes preserve old named-outlet bookmarks without rendering sidebars. Long
configuration annotation names wrap on mobile.

Revision `guicedee-website--v260-20261116-7f9b496` was verified healthy, ready and
serving 100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-7f9b496`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:0259d64e6122fb06445665fff5092f011d840f88911f1c998c094366d7418c6d`.

The production build passed. Local and public Chromium checks covered all ten
pages, all 106 link destinations, exclusive branch expansion, filters, keyboard
navigation, repeated links, refresh/back, eight legacy URL forms and desktop,
tablet and mobile drawer interactions. Search checks, three benchmark scenarios
and 186 packaged HTTP/crawler checks passed. All 298 public assets match the
production build. Evidence is retained under `target/website-page-navigation/`
in DevSuite; the navigation check is `tools/verify-page-navigation.py`.

## Release versions and capability groups

Source `0b7e65e6740419b6ee1617f3da9e7660ee0a6756` adds 15 release-version links,
newest first, and groups the 23 Capabilities destinations into Core &
configuration, Web & security, Data & messaging, Cloud & deployment, and
Observability & resilience. The Firebase and Commercetools section is included.
The shared desktop/mobile tree allows one open page and one open capability
group. Direct links, search results, reload and browser history reveal the
matching group, including on the Web Awesome kit's initial component upgrade.

Revision `guicedee-website--v260-20261116-0b7e65e` is healthy, ready and serves
100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-0b7e65e`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:fae402d16d31a6e523615e9b070442b370c1e26cef7cf508363124afc3c0968a`.

The production build and search regression checks passed. Local and public
Chromium navigation checks passed across ten pages and 122 section links,
including release ordering, exclusive group expansion, keyboard controls,
direct links, search, reload/back, filters and legacy URLs. Desktop, tablet and
mobile drawer checks passed at 1440, 1024 and 390 pixels. All 298 public assets
match the production build. Evidence and screenshots are retained under
`target/website-nested-navigation/` in DevSuite.
