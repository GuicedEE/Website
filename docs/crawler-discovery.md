# Crawler and AI documentation

The site publishes `/llms.txt` as a focused Markdown index, `/llms-full.txt` as
the complete reference, `/reference/overview.md` as a developer guide, and
individual documents under `/reference/components/`. The inventory covers all
127 components in the current GuicedEE Maven release profiles, including 111
JARs with verified JPMS names. It includes library services, application modules,
Kotlin adapters, representations, native-image support, BOMs and build parents.
Tooling and related projects are linked separately with their own version scope.

Each component document includes actual coordinates, direct dependency
declarations, its JPMS descriptor, available README instructions and an index of
Java API declarations. Kotlin adapters include their source. SDK wrappers expose
upstream APIs; the reference identifies the upstream dependencies and does not
invent wrapper methods. Java inherited and generated members are not expanded.

The website build additionally exports all ten documentation routes as Markdown
(`/modules.md`, `/services.md`, `/getting-started.md`, etc.) from their Java
component trees and appends them to the full reference. This includes annotation,
configuration and environment-variable documentation. Interactive charts remain
linked to their authoritative benchmark repository instead of embedding a second
set of potentially stale measurements. The HTML application still uses Angular;
the Markdown resources require no JavaScript.

## Ownership and refresh

`tools/sync-crawler-docs.py` enumerates the `guicedee-boms`, `services`, `guicedee`
and `guicedee-native` profiles in DevSuite's root POM, recursively expanding child
modules. Examples, tests and build outputs are excluded. `ExtractApi.java` uses
the JDK compiler parser without resolving dependencies or running application
code. The inventory records source-file SHA-256 hashes. Committed resource
snapshots allow a website checkout to build without fetching sibling repositories
or contacting GitHub at build/runtime.

From a full DevSuite checkout with JDK 25 and Python:

```powershell
python GuicedEE/website/tools/sync-crawler-docs.py
python GuicedEE/website/tools/sync-crawler-docs.py --check
.\mvnw.cmd -B -ntp -f GuicedEE/website/pom.xml install '-Djwebmp.angular.docker=false' '-Dmaven.javadoc.skip=true' '-Dcentral.publishing.skip=true' '-Dmaven.deploy.skip=true'
python GuicedEE/website/tools/verify-site-discovery.py
```

For the final container build, enable `-Djwebmp.angular.docker=true` on the Maven
command. This also refreshes the generated Dockerfile/nginx configuration; a
Docker-disabled build only refreshes frontend output. Verify the packaged image
with `python GuicedEE/website/tools/verify-site-discovery.py --image gedmarc/guicedee-website:2.6.0`.
Building the image locally does not push it or update Azure.

Commit the regenerated resources with documentation changes. `--check` exits
unsuccessfully on source/snapshot drift. `SiteDiscovery` generates the route
Markdown and sitemap during the normal Angular generation step; edit its Java
sources, not files under `target`. `docs/ai-overview.md` is the curated overview.
`src/main/nginx/site.conf` is selected by the Angular Maven plugin's
`nginxConfigFile` setting and copied into Docker builds. Its generated-file marker
allows the plugin to refresh the copied configuration on subsequent builds.

The verifier starts a disposable nginx container bound only to localhost, checks
every local index link, sitemap routes, GET/HEAD, MIME types, cache policies,
canonical links, forwarding/host redirects and 404s, then checks metadata during
browser navigation. It requires Docker and Python Playwright with Chromium.

## Crawl behavior

- `robots.txt` allows public pages and their rendering assets and advertises the
  canonical sitemap. No special crawler exclusions are imposed.
- The sitemap contains the ten primary HTTPS routes, without query strings,
  fragments, named aside outlets, or fabricated modification dates.
- nginx supplies route-specific canonical and Markdown alternate links before
  JavaScript runs. Angular updates them, the title and Open Graph URL on client
  navigation. Unknown URLs receive HTTP 404 rather than a successful app shell.
- Descriptions, language, Open Graph metadata, Twitter summary metadata, WebSite
  structured data, favicon and a valid browser-mode web manifest are included.
- Named aside routes and query-string refreshes remain valid. Canonical URLs
  exclude those UI details. `www` redirects to the HTTPS apex domain.
- Requests forwarded by Azure as HTTP redirect to HTTPS. The internal HTTP hop
  after TLS termination does not cause a redirect loop.
- Documentation, robots, sitemap, manifest and unversioned assets use five-minute
  revalidation. Only content-hashed JS/CSS bundles are cached as immutable. HTML
  remains `no-store`. Missing static resources return 404 with no long cache.

The layout follows the [llms.txt proposal](https://llmstxt.org/), including
`describedby` and Markdown alternate links. It is documentation discovery, not a
promise that every AI service will fetch it. Robots rules manage crawling and do
not guarantee indexing or provide access control; see
[Google's robots documentation](https://developers.google.com/search/docs/crawling-indexing/robots/intro).
Forwarded protocol handling follows
[Azure Container Apps ingress headers](https://learn.microsoft.com/en-us/azure/container-apps/ingress-overview).

## Live audit: 16 November 2026, before these changes

The live host returned 404 for `robots.txt`, `sitemap.xml`, `llms.txt` and
`llms-full.txt`. The HTML lacked a description and canonical URL. Unknown routes
and `manifest.webmanifest` returned the HTML app shell with status 200. The SVG
favicon worked; `/favicon.ico` returned 404. The apex HTTP URL served content
without redirecting; Azure ingress reported `allowInsecure: true`. HTTPS worked
and `www` already redirected to the apex domain.

These corrections are in the website source and verified production build.
They have not been deployed as part of this documentation task. Publishing must
be followed by public GET/HEAD and browser checks; a successful local build is
not evidence that the live site's crawler resources have changed. No Maven
Central publication or Azure configuration mutation is performed by these tools.
