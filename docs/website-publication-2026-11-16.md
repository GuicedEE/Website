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

Revision `guicedee-website--v260-20261116-0b7e65e` was verified healthy, ready and
serving 100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-0b7e65e`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:fae402d16d31a6e523615e9b070442b370c1e26cef7cf508363124afc3c0968a`.

The production build and search regression checks passed. Local and public
Chromium navigation checks passed across ten pages and 122 section links,
including release ordering, exclusive group expansion, keyboard controls,
direct links, search, reload/back, filters and legacy URLs. Desktop, tablet and
mobile drawer checks passed at 1440, 1024 and 390 pixels. All 298 public assets
match the production build. Evidence and screenshots are retained under
`target/website-nested-navigation/` in DevSuite.

## Explore the platform submenu

Source `78978748a08d7eef799af74d75d7de7c2788548b` nests Home's platform exploration
links beneath Explore the platform. Overview preserves the existing section
destination; 13 further links cover AI skills, foundations, modular design,
the module catalog, configuration, code examples, REST services, security,
Vert.x deployment, MicroProfile, JLink deployment, cloud-aware logging and
developer resources. Selecting a section opens the disclosure, scrolls below
the fixed header and focuses the destination. The same tree is used in the
desktop menu and mobile drawer, with the existing exclusive expansion rules.

Revision `guicedee-website--v260-20261116-7897874` was verified healthy, ready and
serving 100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-7897874`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:03db02c34149d6e674f52e38127dc4cb6cfa362611f4fe6dab61818c7c1551c3`.

The production build and search regression checks passed. Local and public
Chromium checks covered all 135 section links across ten pages, every Explore
destination, direct/repeat links, disclosure reopening, search, refresh/back,
exclusive expansion, filters, legacy URLs and mobile drawer navigation at
1440, 1024 and 390 pixels. All 298 public assets match the production build.
Evidence and screenshots are retained under `target/website-explore-navigation/`
in DevSuite.

## Benchmark highlights for 2.6.0

Source `447ca698576a78bd33917329558b7f76d9651202` adds four leading release
cards covering the public benchmark suite, developer experience, native runtime
and reactive persistence. The copy uses "Keeping pace with the best" and names
Quarkus and Micronaut as comparisons. The application jlink card now includes
the measured package times instead of the obsolete pending-measurement note.

Figures come from benchmark revision `f5770f264815ca06b121599d327768bf43e7bb45`,
verified against the public repository. The section links to that immutable
report and the repository, with measurement dates, runtime resource limits and
the separate clean-build timing boundary. It includes 655 ms native startup,
4.93 ms warm p50, 68.57 MiB median peak memory, 3.52 s compilation, 4.10 s JVM
packaging, 67.86 s native packaging, 18.25/17.88 s SPI/scanner jlink packaging,
and 2.41/80.31 ms persistence read/CRUD-cycle p50.

Revision `guicedee-website--v260-20261116-447ca69` was verified healthy, ready and
serving 100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-447ca69`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:cdb715dd34fb74563a4efd9ce27acb0a66ca8ff657d933f2728526683524d46d`.

The production build passed. Local and public Chromium checks verified all ten
figures against the benchmark JSON/CSV, nine responsive cards, source links,
the release anchor below the fixed header and no browser errors at 1440, 1024
and 390 pixels. Generated and public release Markdown include the same figures.
All 298 public assets match the production build. Evidence and screenshots are
retained under `target/website-release-benchmarks/` in DevSuite.

## Always dark mode and lighter header star

Source `bf789b04e7f2e496235e473c2572a5b5ab1030a0` renders `wa-dark` on both
the HTML root and body, with dark color-scheme metadata in the initial document.
The theme toggle and saved-theme override are removed. The shared app theme
remains dark for diagrams. The header star uses `--wa-color-brand-on-quiet`
(`#f88ab7`) with its secondary icon layer at full opacity.

Revision `guicedee-website--v260-20261116-bf789b0` was verified healthy, ready and
serving 100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-bf789b0`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:768731530f7107c0bd17ea1df1b2790af01e4a6ef6a7374fa6791e71120e606a`.

The production build passed. Local and public browser checks verified the
initial HTML, dark document/body color schemes under light and dark system
settings, a saved light preference, reloads, route navigation, the star's
rendered color and opacity, and no theme toggle or browser errors. Desktop and
mobile layouts were checked at 1440 and 390 pixels. All 298 public assets match
the production build. Evidence and screenshots are retained under
`target/website-dark-mode/` in DevSuite.

## Carousel scrolling and section navigation response

Source `8c1b26653595e7f7933c2d096eefff15b45a4431` lets vertical scrolling pass
from carousel content to the page at its top and bottom, and when the slide has
no internal overflow. Horizontal carousel scrolling remains available.

Section links previously included fixed waits of 350 ms and 400 ms. They now
wait for rendering, the measured header height, relevant disclosure animations,
carousel accessibility state and mobile drawer closure only when required.
Duplicate fragment reveals are removed. This preserves header clearance and
focus while making ordinary section links respond without the 750 ms pause.

Revision `guicedee-website--v260-20261116-8c1b266` was verified healthy, ready and serving
100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-8c1b266`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:0cb66d33b31d0ff65b9c86bdeffef4398376fecb34f5797c7b8acf5a068e82e8`.

The production build and search regression checks passed. Local and public
Chromium checks verified wheel scrolling through non-overflowing slides, inner
scrolling, page scrolling at both boundaries, horizontal carousel movement and
touch-swipe scrolling. Three public section selections took 28.0–28.9 ms from
click to destination focus in this check. These are browser observations, not a
performance guarantee. Local and public navigation checks passed for ten pages
and all 135 section links, including search, disclosures, direct/repeat links,
history, filters, legacy URLs and mobile drawer focus at 1440, 1024 and 390 pixels.
All 298 public assets match the production build. Evidence is retained under
`target/website-scroll-fix/` in DevSuite; the added regression check is
`tools/verify-scroll-behavior.py`.

## Modular JAR developer comparisons

Source `b9e8f807c5d4085c6fcecbd820973af2b59c4e0e` shows both GuicedEE modular
JAR variants in the Compile and JVM package charts: SPI discovery and scanner
discovery. The chart and build grid explicitly label their `java -m` launch mode.
The existing benchmark Dockerfile runs both on the full JDK with `--module-path`
and `-m`. Native and application jlink retain their distinct packaging categories.

Clean compilation medians are 3.52 s (SPI) and 3.72 s (scanner); complete JVM
package medians are 4.10 s and 3.88 s. Values come from the published build CSV,
including the unavailable/invalid-data fallback snapshots. The snapshot matches
benchmark revision `db61c9af550449db854d53803b0bcde2df0a0778`, including the prior
persistence snapshot refresh in website commit `059ea0f`.

Two-line chart labels and a wider mobile carousel keep all four build series
readable. The carousel retains a stable viewport and page scroll chaining.

Revision `guicedee-website--v260-20261116-b9e8f80` was verified healthy, ready and serving
100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-b9e8f80`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:abd679b3207cefb7504d72fdb994e24be7a01f474fb72ee1f214a4c6f51e7598`.

The production build passed. Browser checks passed for published, unavailable
and invalid datasets, exact chart values and grid agreement, switching from
native back to compile, all four visible labels at 1440 and 390 pixels, stable
slide heights and no horizontal page overflow. Existing wheel, touch and
section-navigation checks passed. Public chart checks passed, and all 298 public
assets match the verified production build. Evidence and screenshots are retained
under `target/website-compile-module-mode/` in DevSuite.

## App Builder option columns

Source `bf98bac2d827999c22868abf144f0ed375ac867c` lays out the ten module groups
in two columns when the builder has enough space. The preview stays alongside
on large screens and moves below on smaller screens; phones use one option
column. Group cards and wrapping labels keep the expanded options readable.
One Web Awesome selection tree retains all module selections, including the
Service Discovery group nested under Cloud SDKs.

Revision `guicedee-website--v260-20261116-bf98bac` was verified healthy, ready and serving
100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-bf98bac`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:451e2be104ffa8bde75fae56ed67621d7ea4ce8efd25832faea76a91fb5c1e16`.

The production build passed. Local and public browser checks expanded every
group at 1440, 1024 and 390 pixels, verified the column count and preview position,
and found no horizontal page overflow or JavaScript errors. Selections from
both columns and the nested group appeared in the downloaded Maven ZIP. The
existing Kotlin builder checks passed for Maven and Gradle downloads, including
native starters with Firebase and Commercetools. This layout change did not rerun
compilation of the generated projects. All 298 public assets match the production
build. Evidence and screenshots are retained under
`target/website-builder-columns/` in DevSuite.

## Compact environment reference tables

Source `35cfd61db713a2b0337e60ee92224ace5d26d77c` keeps the environment reference
tables as semantic HTML and adds a Wrap text switch beside each table. Rows are
compact by default, long values use ellipses with full-text hover titles, and
wrapping can be enabled independently for each table. Predictable column widths
give Application / status a defined share of the contract tables. Narrow screens
scroll within the table region, including via keyboard.

The 28 Telemetry rows are 34.25 px high in compact mode at the checked widths;
most were 128.95 px before the change at 1440 px. All original content across
44 tables and 703 rows, including headers, was compared before publication and
preserved. Complete text stays in the DOM for search and accessibility.

Revision `guicedee-website--v260-20261116-35cfd61` was verified healthy, ready and serving
100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-35cfd61`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:3981e94bd6f85b74da4bf914f2fe9dcabf8bb19fb918946b1ee7ec0288a8aad6`.

The production build and existing site-search checks passed. Local and public
browser checks at 1440, 1024 and 390 pixels verified compact and wrapped row
heights, independent switches, keyboard activation, full-text titles, header
fit, mobile table scrolling and no horizontal page overflow or JavaScript errors.
All 298 public assets match the verified production build. Evidence and screenshots
are retained under `target/website-environment-tables/` in DevSuite.

## Representation formats in Data & persistence

Source `b16dcc3314890034eb5e616b45cc54714ce63ce1` replaces the generic
Representations card with individual JSON, Excel, XML and CSV entries beside
Persistence. JSON, Excel and XML use their actual
`com.guicedee.modules.representations` coordinates. CSV is provided by
`com.guicedee.modules.services:commons-csv` and uses the upstream Apache Commons
CSV API. The obsolete `com.guicedee:representations` coordinate is removed from
this section.

Descriptions were checked against the local interfaces, POMs and Commons CSV
JAR. Excel documents the implemented XLSX worksheet-to-object reading helpers;
it does not claim that the default `toExcel()` stub exports workbooks. Each new
card opens its packaged component reference and links to its actual source
repository. The section remains a responsive card grid with module filtering.

Revision `guicedee-website--v260-20261116-b16dcc3` is healthy, ready and serves
100% of traffic. Image tag: `gedmarc/guicedee-website:2.6.0-20261116-b16dcc3`.
Deployed image:
`docker.io/gedmarc/guicedee-website@sha256:5c9fbabcaf06fe4b0d0e13f2887cc5323012c4b311cbb8c2446d2a6064336971`.

The production build and existing site-search checks passed. Local and public
browser checks at 1440, 1024 and 390 pixels verified all five cards, copyable
coordinates, CSV filtering and no horizontal page overflow or JavaScript errors.
All four new documentation dialogs returned their matching reference documents
and source-repository links. Generated module Markdown includes the four formats.
All 298 public assets match the production build. Evidence and screenshots are
retained under `target/website-representation-formats/` in DevSuite.
