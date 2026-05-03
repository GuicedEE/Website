package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportProvider;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportProvider;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.services.RouterOutlet;
import com.jwebmp.core.base.html.DivSimple;
import com.guicedee.website.App;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.toast.WaToastDataService;
import com.jwebmp.webawesome.components.tooltip.WaTooltip;
import com.jwebmp.webawesome.components.badge.WaBadge;
import com.jwebmp.webawesome.components.popover.WaPopover;
import com.jwebmp.webawesome.components.popover.WaPopoverPlacements;
import com.jwebmp.core.base.angular.components.NgIf;
import com.jwebmp.plugins.markdown.Markdown;
import com.jwebmp.webawesome.components.tree.WaTree;
import com.jwebmp.webawesome.components.tree.WaTreeItem;
import com.jwebmp.webawesome.components.page.WaPage;
import com.jwebmp.webawesome.components.waswitch.WaSwitch;
import com.jwebmp.webawesome.components.WaDiv;
import com.jwebmp.webawesome.tokens.WaBorderToken;
import com.jwebmp.webawesome.tokens.WaSpaceToken;
import com.jwebmp.webawesome.tokens.WaTypographyToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Top-level boot component for the GuicedEE website.
 * <p>
 * The WaPage is the outermost shell.  The banner holds a product navigation
 * bar inspired by the Web Awesome "Awesomeverse" pattern — a wa-cluster of
 * links with icons and tooltips for each DevSuite project.  Header, menu,
 * and navigation live in their normal page slots.  The main content area
 * holds the RouterOutlet for page routing.
 */
@NgComponent("guicedee-app")
@NgRoutable(path = "")
@NgImportProvider("{provide: LOCALE_ID, useValue: 'en-ZA'}")
@NgBootImportProvider(value = "{ provide: LOCALE_ID, useValue: 'en-ZA' }")
@NgBootImportReference(value = "provideHttpClient", reference = "@angular/common/http")
@NgBootImportProvider("provideHttpClient()")
@NgBootImportReference(value = "LOCALE_ID", reference = "@angular/core")
@NgBootImportReference(value = "registerLocaleData", reference = "@angular/common")
@NgBootImportReference(value = "localeEnZa", reference = "@angular/common/locales/en-ZA", direct = true)
@NgImportReference(value = "localeEnZa", reference = "@angular/common/locales/en-ZA", direct = true, wrapValueInBraces = false)
@NgImportReference(value = "LOCALE_ID", reference = "@angular/core")
@NgImportReference(value = "registerLocaleData", reference = "@angular/common")
@NgImportReference(value = "signal", reference = "@angular/core")
@NgImportReference(value = "DOCUMENT", reference = "@angular/common")
@NgImportReference(value = "Router, NavigationStart, NavigationEnd", reference = "@angular/router")
@NgImportReference(value = "inject", reference = "@angular/core")
@NgImportReference(value = "filter", reference = "rxjs/operators")
@NgComponentReference(value = WaToastDataService.class)
@NgComponentReference(value = App.class)
public class WebsiteBoot extends DivSimple<WebsiteBoot> implements INgComponent<WebsiteBoot> {
    public WebsiteBoot() {
        setTag("ng-container");
        addStyle("width:100%");
        addStyle("height:100%");

        // ── WaPage is the top-level shell ──
        WaPage<?> page = new WaPage<>();
        page.addStyle("width:100%");
        page.addStyle("height:100%");

        // ── WaPage settings ──
        page.getMain().setPageSize(PageSize.ExtraSmall);

        // ── Banner: product navigation bar ──
        var banner = page.getHeader();

        DivSimple<?> navWrapper = new DivSimple<>();
        navWrapper.addClass("wrapper-nav-products");

        DivSimple<?> nav = new DivSimple<>();
        nav.setTag("nav");
        nav.addClass("nav-products");
        nav.addClass("nav-products-full");
        nav.addAttribute("aria-label", "DevSuite Products");

        // Primary product links
        DivSimple<?> primary = new DivSimple<>();
        primary.addClass("nav-products-primary");
        primary.addClass("wa-split");
        primary.addClass("wa-align-items-stretch");

        DivSimple<?> cluster = new DivSimple<>();
        cluster.addClass("wa-cluster");
        cluster.addClass("wa-align-items-stretch");
        cluster.addClass("wa-gap-0");

        // GuicedEE — active product
        WaButton<?> guicedeeBtn = new WaButton<>();
        guicedeeBtn.setAppearance(Appearance.Plain);
        guicedeeBtn.setVariant(Variant.Brand);
        guicedeeBtn.addAttribute("routerLink", "/home");
        guicedeeBtn.addClass("product");
        guicedeeBtn.addClass("product-guicedee");
        guicedeeBtn.addClass("product-active");
        guicedeeBtn.setID("product-guicedee");
        guicedeeBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        var guicedeeLogo = new WaIcon<>();
        guicedeeLogo.addClass("fak");
        guicedeeLogo.addClass("fa-guicedee-logo");
        guicedeeLogo.addClass("logo-icon");
        guicedeeLogo.addClass("logo-guicedee");
        guicedeeLogo.addAttribute("label", "GuicedEE");
        guicedeeBtn.add(guicedeeLogo);
        guicedeeBtn.setText("GuicedEE");
        guicedeeBtn.setRenderTextBeforeChildren(false);
        cluster.add(guicedeeBtn);

        // JWebMP
        WaButton<?> jwebmpBtn = new WaButton<>();
        jwebmpBtn.setAppearance(Appearance.Plain);
        jwebmpBtn.setVariant(Variant.Brand);
        jwebmpBtn.setAsLink("https://jwebmp.com", "jwebmp", null);
        jwebmpBtn.addClass("product");
        jwebmpBtn.addClass("product-jwebmp");
        jwebmpBtn.setID("product-jwebmp");
        jwebmpBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        var jwebmpLogo = new WaIcon<>();
        jwebmpLogo.addClass("fak");
        jwebmpLogo.addClass("fa-jwebmp-logo-green");
        jwebmpLogo.addClass("logo-icon");
        jwebmpLogo.addClass("logo-jwebmp");
        jwebmpLogo.addAttribute("label", "JWebMP");
        jwebmpBtn.add(jwebmpLogo);
        cluster.add(jwebmpBtn);
        WaTooltip<?> jwebmpTip = new WaTooltip<>();
        jwebmpTip.setForId("product-jwebmp");
        jwebmpTip.setText("JWebMP");
        cluster.add(jwebmpTip);

        // Entity Assist
        WaButton<?> entityBtn = new WaButton<>();
        entityBtn.setAppearance(Appearance.Plain);
        entityBtn.setVariant(Variant.Brand);
        entityBtn.setAsLink("https://entityassist.com", "entityassist", null);
        entityBtn.addClass("product");
        entityBtn.addClass("product-entity-assist");
        entityBtn.setID("product-entity-assist");
        entityBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        var entityLogo = new WaIcon<>();
        entityLogo.addClass("fak");
        entityLogo.addClass("fa-entityassist-logo");
        entityLogo.addClass("logo-icon");
        entityLogo.addClass("logo-entity-assist");
        entityLogo.addAttribute("label", "Entity Assist");
        entityBtn.add(entityLogo);
        cluster.add(entityBtn);
        WaTooltip<?> entityTip = new WaTooltip<>();
        entityTip.setForId("product-entity-assist");
        entityTip.setText("Entity Assist");
        cluster.add(entityTip);

        // Activity Master
        WaButton<?> activityBtn = new WaButton<>();
        activityBtn.setAppearance(Appearance.Plain);
        activityBtn.setVariant(Variant.Brand);
        activityBtn.setAsLink("https://activity-master.com/", "activitymaster", null);
        activityBtn.addClass("product");
        activityBtn.addClass("product-activity-master");
        activityBtn.setID("product-activity-master");
        activityBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        var activityLogo = new WaIcon<>();
        activityLogo.addClass("fak");
        activityLogo.addClass("fa-activitymaster-logo");
        activityLogo.addClass("logo-icon");
        activityLogo.addClass("logo-activity-master");
        activityLogo.addAttribute("label", "Activity Master");
        activityBtn.add(activityLogo);
        cluster.add(activityBtn);
        WaTooltip<?> activityTip = new WaTooltip<>();
        activityTip.setForId("product-activity-master");
        activityTip.setText("Activity Master");
        cluster.add(activityTip);

        // ── Version badge (Central release) ──
        WaBadge<?> versionBadge = new WaBadge<>();
        versionBadge.addClass("version-badge");
        versionBadge.setVariant(Variant.Brand);
        versionBadge.setPill(true);
        versionBadge.setFontSize(WaTypographyToken.FontSize2XS);
        versionBadge.setOnColour(Variant.Brand);
        versionBadge.setFillColour(Variant.Brand);
        versionBadge.addStyle("border", "2px solid var(--wa-color-brand-light)");
        versionBadge.addStyle("box-shadow", "0 0 6px color-mix(in srgb, var(--wa-color-brand-normal) 40%, transparent)");
        versionBadge.addStyle("cursor", "pointer");
        versionBadge.setText("2.0.1");
        versionBadge.setID("version-badge");
        cluster.add(versionBadge);

        // ── Snapshot badge popover with Maven/Gradle repository instructions ──
        WaPopover<?> snapshotPopover = new WaPopover<>();
        snapshotPopover.setForElement(versionBadge);
        snapshotPopover.setPlacement(WaPopoverPlacements.Bottom);
        snapshotPopover.setMaxWidth("32rem");
        snapshotPopover.setPopoverBorderColor(Variant.Brand);
        snapshotPopover.setPopoverBorderWidth(WaBorderToken.WidthS);
        snapshotPopover.setPopoverBorderRadius(WaBorderToken.RadiusL);
        snapshotPopover.setArrowColor(Variant.Brand);

        var popoverContent = new WaDiv<>();
        popoverContent.setPadding(WaSpaceToken.SpaceM);

        var popoverTitle = new WaDiv<>("strong");
        popoverTitle.setText("Snapshot Repository Setup");
        popoverTitle.addStyle("display", "block");
        popoverTitle.addStyle("margin-block-end", WaSpaceToken.SpaceS.var());
        popoverTitle.setFontSize(WaTypographyToken.FontSizeM);
        popoverContent.add(popoverTitle);

        var snapshotVersionLabel = new WaDiv<>();
        snapshotVersionLabel.addStyle("margin-block-end", WaSpaceToken.SpaceS.var());
        snapshotVersionLabel.setFontSize(WaTypographyToken.FontSizeS);
        snapshotVersionLabel.setText("Current snapshot: <code>2.0.1</code>");
        popoverContent.add(snapshotVersionLabel);

        var popoverDesc = new WaDiv<>("p");
        popoverDesc.addStyle("margin-block-end", WaSpaceToken.SpaceS.var());
        popoverDesc.setFontSize(WaTypographyToken.FontSizeS);
        popoverDesc.addStyle("color", "var(--wa-color-neutral-700)");
        popoverDesc.addAttribute("[innerText]", "app.useGradle() ? 'Add to your build.gradle:' : 'Add to your pom.xml:'");
        popoverContent.add(popoverDesc);

        var mavenMd = new Markdown<>("""
                ```xml
                <repository>
                    <id>guicedee-github</id>
                    <url>https://maven.pkg.github.com/GuicedEE</url>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
                ```""");
        mavenMd.setClipboard(true);
        mavenMd.addClass("aside-snippet-code");
        mavenMd.addClass("wa-body-xs");
        var mavenIf = new NgIf("!app.useGradle()");
        mavenIf.add(mavenMd);
        popoverContent.add(mavenIf);

        var gradleMd = new Markdown<>("""
                ```groovy
                repositories {
                    maven {
                        url = uri("https://maven.pkg.github.com/GuicedEE")
                        credentials {
                            username = project.findProperty("gpr.user")
                                ?: System.getenv("GITHUB_USER")
                            password = project.findProperty("gpr.token")
                                ?: System.getenv("GITHUB_TOKEN")
                        }
                    }
                }
                ```""");
        gradleMd.setClipboard(true);
        gradleMd.addClass("aside-snippet-code");
        gradleMd.addClass("wa-body-xs");
        var gradleIf = new NgIf("app.useGradle()");
        gradleIf.add(gradleMd);
        popoverContent.add(gradleIf);

        var authNote = new WaDiv<>("p");
        authNote.addStyle("margin-block-start", WaSpaceToken.SpaceS.var());
        authNote.setFontSize(WaTypographyToken.FontSize2XS);
        authNote.addStyle("color", "var(--wa-color-neutral-600)");
        authNote.setText("&#x1F511; GitHub Packages requires authentication — use a personal access token with <code>read:packages</code> scope.");
        popoverContent.add(authNote);

        snapshotPopover.add(popoverContent);
        cluster.add(snapshotPopover);


        primary.add(cluster);

        // Secondary links (GitHub, Blog)
        DivSimple<?> secondary = new DivSimple<>();
        secondary.addClass("nav-products-secondary");
        secondary.addClass("wa-cluster");
        secondary.addClass("wa-gap-2xs");

        // Maven / Gradle toggle switch
        var buildToolToggle = new WaDiv<>();
        buildToolToggle.addClass("wa-cluster");
        buildToolToggle.addClass("wa-gap-2xs");
        buildToolToggle.addClass("wa-align-items-center");
        buildToolToggle.setFontSize(WaTypographyToken.FontSizeXS);
        buildToolToggle.addStyle("color", "var(--wa-color-text-quiet)");

        var mavenLabel = new DivSimple<>();
        mavenLabel.setTag("span");
        mavenLabel.setText("Maven");
        buildToolToggle.add(mavenLabel);

        WaSwitch<?> buildToolSwitch = new WaSwitch<>();
        buildToolSwitch.setSize(com.jwebmp.webawesome.components.Size.Small);
        buildToolSwitch.setName("useGradle");
        //buildToolSwitch.bind("app.useGradle()");
        buildToolSwitch.addAttribute("(wa-change)", "onBuildToolChange($event)");
        buildToolToggle.add(buildToolSwitch);

        var gradleLabel = new DivSimple<>();
        gradleLabel.setTag("span");
        gradleLabel.setText("Gradle");
        buildToolToggle.add(gradleLabel);

        secondary.add(buildToolToggle);

        WaButton<?> githubBtn = new WaButton<>();
        githubBtn.setAppearance(Appearance.Plain);
        githubBtn.setVariant(Variant.Brand);
        githubBtn.setAsLink("https://github.com/GuicedEE/", "guicedee-github", null);
        githubBtn.addClass("pseudo-product");
        githubBtn.addClass("product-github");
        githubBtn.setID("product-github");
        githubBtn.add(new WaIcon<>("github").addAttribute("family", "brands")
                                            .addAttribute("label", "GitHub"));
        secondary.add(githubBtn);
        WaTooltip<?> githubTip = new WaTooltip<>();
        githubTip.setForId("product-github");
        githubTip.setText("GitHub");
        secondary.add(githubTip);

        WaButton<?> starBtn = new WaButton<>();
        starBtn.setAppearance(Appearance.Plain);
        starBtn.setVariant(Variant.Brand);
        starBtn.setAsLink("https://github.com/GuicedEE/GuicedInjection", "guicedee-github", null);
        starBtn.addClass("pseudo-product");
        starBtn.addClass("product-star");
        starBtn.setID("product-star");
        starBtn.add(new WaIcon<>("star").addAttribute("family", "sharp-duotone").addAttribute("label", "Star this Repository"));
        secondary.add(starBtn);
        WaTooltip<?> starTip = new WaTooltip<>();
        starTip.setForId("product-star");
        starTip.setText("Star this Repository");
        secondary.add(starTip);

        WaButton<?> docsBtn = new WaButton<>();
        docsBtn.setAppearance(Appearance.Plain);
        docsBtn.setVariant(Variant.Brand);
        docsBtn.setAsLink("https://github.com/GuicedEE/ai-rules", "guicedee-github", null);
        docsBtn.addClass("pseudo-product");
        docsBtn.addClass("product-docs");
        docsBtn.setID("product-docs");
        docsBtn.add(new WaIcon<>("brain-circuit").addAttribute("family", "sharp-duotone").addAttribute("label", "AI Skills Repository"));
        secondary.add(docsBtn);
        WaTooltip<?> docsTip = new WaTooltip<>();
        docsTip.setForId("product-docs");
        docsTip.setText("AI Skills Repository");
        secondary.add(docsTip);

        WaButton<?> patreonBtn = new WaButton<>();
        patreonBtn.setAppearance(Appearance.Plain);
        patreonBtn.setVariant(Variant.Brand);
        patreonBtn.setAsLink("https://www.patreon.com/GedMarc", "guicedee-patreon", null);
        patreonBtn.addClass("pseudo-product");
        patreonBtn.addClass("product-patreon");
        patreonBtn.setID("product-patreon");
        patreonBtn.add(new WaIcon<>("patreon").addAttribute("family", "brands")
                                              .addAttribute("label", "Patreon"));
        secondary.add(patreonBtn);
        WaTooltip<?> patreonTip = new WaTooltip<>();
        patreonTip.setForId("product-patreon");
        patreonTip.setText("Support me on Patreon");
        secondary.add(patreonTip);

        // Theme toggle (dark ↔ light)
        WaButton<?> themeBtn = new WaButton<>();
        themeBtn.setAppearance(Appearance.Plain);
        themeBtn.setVariant(Variant.Brand);
        themeBtn.addAttribute("(click)", "toggleDarkMode()");
        themeBtn.addClass("pseudo-product");
        themeBtn.addClass("product-theme");
        themeBtn.setID("product-theme");
        var themeIcon = new WaIcon<>();
        themeIcon.addAttribute("[name]", "darkMode() ? 'sun-bright' : 'moon'");
        themeIcon.addAttribute("family", "sharp-duotone");
        themeIcon.addAttribute("label", "Toggle Theme");
        themeBtn.add(themeIcon);
        secondary.add(themeBtn);
        WaTooltip<?> themeTip = new WaTooltip<>();
        themeTip.setForId("product-theme");
        themeTip.setText("Toggle Theme");
        secondary.add(themeTip);

        primary.add(secondary);
        nav.add(primary);
        navWrapper.add(nav);
        banner.add(navWrapper);

        // ── Menu: WaTree navigation with sub-items ──
        var menu = page.getMenu();
        WaTree<?> menuTree = new WaTree<>();
        menuTree.setIndentSize("2px");
        menuTree.setIndentGuideColor("var(--wa-color-neutral-300)");

        // Home
        menuTree.add(createRouterTreeItem("/home", "Home", "house"));

        // App Builder
        menuTree.add(createRouterTreeItem("/builder", "App Builder", "wand-magic-sparkles"));

        // Getting Started
        menuTree.add(createRouterTreeItem("/getting-started", "Getting Started", "rocket"));

        // End-to-End Guide
        menuTree.add(createRouterTreeItem("/guides/end-to-end", "End-to-End Guide", "route"));

        // Capabilities
        menuTree.add(createRouterTreeItem("/capabilities", "Capabilities", "star"));

        // Modules
        menuTree.add(createRouterTreeItem("/modules", "Modules", "cubes"));

        // Services
        menuTree.add(createRouterTreeItem("/services", "Services", "puzzle-piece"));

        // Environment Variables
        menuTree.add(createRouterTreeItem("/environment-variables", "Env Variables", "sliders"));

        // Releases
        menuTree.add(createRouterTreeItem("/releases", "Releases", "tag"));


        // Support (external)
        menuTree.add(createExternalTreeItem("https://www.patreon.com/GedMarc", "Support", "life-ring"));

        menu.add(menuTree);

        // ── Built-on attribution links below menu tree ──
        var builtOn = new WaDiv<>();
        builtOn.setPadding(WaSpaceToken.SpaceM);
        builtOn.addStyle("border-top", "1px solid var(--wa-color-neutral-200)");
        builtOn.addStyle("margin-top", "auto");
        var builtOnLabel = new DivSimple<>();
        builtOnLabel.setTag("span");
        builtOnLabel.setText("Built on");
        builtOnLabel.addClass("wa-body-2xs");
        builtOnLabel.addStyle("color", "var(--wa-color-text-quiet)");
        builtOnLabel.addStyle("display", "block");
        builtOnLabel.addStyle("margin-bottom", WaSpaceToken.SpaceXS.var());
        builtOn.add(builtOnLabel);
        var builtOnLinks = new DivSimple<>();
        builtOnLinks.addClass("wa-stack");
        builtOnLinks.addClass("wa-gap-2xs");
        builtOnLinks.addClass("built-on-links");

        Link<?> angularAwesomeLink = new Link<>();
        angularAwesomeLink.setTag("a");
        angularAwesomeLink.addAttribute("href", "https://www.npmjs.com/package/angular-awesome");
        angularAwesomeLink.addAttribute("target", "angular-awesome");
        angularAwesomeLink.add(new WaIcon<>("npm").addAttribute("family", "brands"));
        angularAwesomeLink.setText("Angular Awesome");
        angularAwesomeLink.setRenderTextBeforeChildren(false);
        angularAwesomeLink.addClass("wa-body-xs");
        angularAwesomeLink.addStyle("color", "var(--wa-color-brand-normal)");
        builtOnLinks.add(angularAwesomeLink);

        Link<?> webAwesomeLink = new Link<>();
        webAwesomeLink.setTag("a");
        webAwesomeLink.addAttribute("href", "https://www.webawesome.com");
        webAwesomeLink.addAttribute("target", "web-awesome");
        webAwesomeLink.add(new WaIcon<>("web-awesome").addAttribute("family", "sharp-duotone"));
        webAwesomeLink.setText("Web Awesome");
        webAwesomeLink.setRenderTextBeforeChildren(false);
        webAwesomeLink.addClass("wa-body-xs");
        webAwesomeLink.addStyle("color", "var(--wa-color-brand-normal)");
        builtOnLinks.add(webAwesomeLink);

        Link<?> jwebmpMenuLink = new Link<>();
        jwebmpMenuLink.setTag("a");
        jwebmpMenuLink.addAttribute("href", "https://jwebmp.com");
        jwebmpMenuLink.addAttribute("target", "jwebmp");
        var jwebmpBuiltIcon = new DivSimple<>();
        jwebmpBuiltIcon.setTag("i");
        jwebmpBuiltIcon.addClass("fak");
        jwebmpBuiltIcon.addClass("fa-jwebmp-logo-green");
        jwebmpBuiltIcon.addClass("built-on-logo");
        jwebmpMenuLink.add(jwebmpBuiltIcon);
        jwebmpMenuLink.setText("JWebMP");
        jwebmpMenuLink.setRenderTextBeforeChildren(false);
        jwebmpMenuLink.addClass("wa-body-xs");
        jwebmpMenuLink.addStyle("color", "var(--wa-color-brand-normal)");
        builtOnLinks.add(jwebmpMenuLink);

        builtOn.add(builtOnLinks);
        menu.add(builtOn);

        // ── Navigation Toggle (burger button, slot="navigation-toggle") ──
        var navToggle = page.getNavigationToggle();
        WaButton<?> burgerBtn = new WaButton<>();
        burgerBtn.setAppearance(Appearance.Plain);
        burgerBtn.setVariant(Variant.Neutral);
        burgerBtn.addAttribute("aria-label", "Toggle navigation menu");
        burgerBtn.add(new WaIcon<>("bars").addAttribute("family", "sharp-duotone"));
        navToggle.add(burgerBtn);

        // ── Navigation Toggle Icon (slot="navigation-toggle-icon") ──
        var navToggleIcon = page.getNavigationToggleIcon();
        navToggleIcon.add(new WaIcon<>("bars").addAttribute("family", "sharp-duotone"));

        // ── Navigation Header (branding inside the drawer, slot="navigation-header") ──
        var navHeader = page.getNavigationHeader();
        Link<?> drawerLogo = new Link<>();
        drawerLogo.setTag("a");
        drawerLogo.addAttribute("routerLink", "/home");
        drawerLogo.addAttribute("aria-label", "GuicedEE Home");
        drawerLogo.addClass("appearance-plain");
        var drawerLogoSpan = new DivSimple<>();
        drawerLogoSpan.setTag("i");
        drawerLogoSpan.addClass("fak");
        drawerLogoSpan.addClass("fa-guicedee-logo");
        drawerLogoSpan.addClass("logo-icon");
        drawerLogoSpan.addClass("logo-guicedee");
        drawerLogo.add(drawerLogoSpan);
        drawerLogo.setText("GuicedEE");
        drawerLogo.setRenderTextBeforeChildren(false);
        navHeader.add(drawerLogo);

        // ── Burger Menu Navigation (drawer contents, slot="navigation") ──
        var burgerMenuNavigation = page.getNavigation();
        WaTree<?> navTree = new WaTree<>();
        navTree.setIndentSize("2px");
        navTree.setIndentGuideColor("var(--wa-color-neutral-300)");

        navTree.add(createRouterTreeItem("/home", "Home", "house"));
        navTree.add(createRouterTreeItem("/builder", "App Builder", "wand-magic-sparkles"));
        navTree.add(createRouterTreeItem("/getting-started", "Getting Started", "rocket"));
        navTree.add(createRouterTreeItem("/guides/end-to-end", "End-to-End Guide", "route"));
        navTree.add(createRouterTreeItem("/capabilities", "Capabilities", "star"));
        navTree.add(createRouterTreeItem("/modules", "Modules", "cubes"));
        navTree.add(createRouterTreeItem("/services", "Services", "puzzle-piece"));
        navTree.add(createRouterTreeItem("/environment-variables", "En Variables", "sliders"));
        navTree.add(createRouterTreeItem("/releases", "Releases", "tag"));
        navTree.add(createExternalTreeItem("https://www.patreon.com/GedMarc", "Support", "life-ring"));
        burgerMenuNavigation.add(navTree);

        // ── Built-on attribution links below drawer tree ──
        var drawerBuiltOn = new WaDiv<>();
        drawerBuiltOn.setPadding(WaSpaceToken.SpaceM);
        drawerBuiltOn.addStyle("border-top", "1px solid var(--wa-color-neutral-200)");
        drawerBuiltOn.addStyle("margin-top", "auto");
        var drawerBuiltOnLabel = new DivSimple<>();
        drawerBuiltOnLabel.setTag("span");
        drawerBuiltOnLabel.setText("Built on");
        drawerBuiltOnLabel.addClass("wa-body-2xs");
        drawerBuiltOnLabel.addStyle("color", "var(--wa-color-text-quiet)");
        drawerBuiltOnLabel.addStyle("display", "block");
        drawerBuiltOnLabel.addStyle("margin-bottom", WaSpaceToken.SpaceXS.var());
        drawerBuiltOn.add(drawerBuiltOnLabel);
        var drawerBuiltOnLinks = new DivSimple<>();
        drawerBuiltOnLinks.addClass("wa-stack");
        drawerBuiltOnLinks.addClass("wa-gap-2xs");
        drawerBuiltOnLinks.addClass("built-on-links");

        Link<?> drawerAngularLink = new Link<>();
        drawerAngularLink.setTag("a");
        drawerAngularLink.addAttribute("href", "https://www.npmjs.com/package/angular-awesome");
        drawerAngularLink.addAttribute("target", "angular-awesome");
        drawerAngularLink.add(new WaIcon<>("npm").addAttribute("family", "brands"));
        drawerAngularLink.setText("Angular Awesome");
        drawerAngularLink.setRenderTextBeforeChildren(false);
        drawerAngularLink.addClass("wa-body-xs");
        drawerAngularLink.addStyle("color", "var(--wa-color-brand-normal)");
        drawerBuiltOnLinks.add(drawerAngularLink);

        Link<?> drawerWebAwesomeLink = new Link<>();
        drawerWebAwesomeLink.setTag("a");
        drawerWebAwesomeLink.addAttribute("href", "https://www.webawesome.com");
        drawerWebAwesomeLink.addAttribute("target", "web-awesome");
        drawerWebAwesomeLink.add(new WaIcon<>("web-awesome").addAttribute("family", "sharp-duotone"));
        drawerWebAwesomeLink.setText("Web Awesome");
        drawerWebAwesomeLink.setRenderTextBeforeChildren(false);
        drawerWebAwesomeLink.addClass("wa-body-xs");
        drawerWebAwesomeLink.addStyle("color", "var(--wa-color-brand-normal)");
        drawerBuiltOnLinks.add(drawerWebAwesomeLink);

        Link<?> drawerJwebmpLink = new Link<>();
        drawerJwebmpLink.setTag("a");
        drawerJwebmpLink.addAttribute("href", "https://jwebmp.com");
        drawerJwebmpLink.addAttribute("target", "jwebmp");
        var drawerJwebmpBuiltIcon = new DivSimple<>();
        drawerJwebmpBuiltIcon.setTag("i");
        drawerJwebmpBuiltIcon.addClass("fak");
        drawerJwebmpBuiltIcon.addClass("fa-jwebmp-logo-green");
        drawerJwebmpBuiltIcon.addClass("built-on-logo");
        drawerJwebmpLink.add(drawerJwebmpBuiltIcon);
        drawerJwebmpLink.setText("JWebMP");
        drawerJwebmpLink.setRenderTextBeforeChildren(false);
        drawerJwebmpLink.addClass("wa-body-xs");
        drawerJwebmpLink.addStyle("color", "var(--wa-color-brand-normal)");
        drawerBuiltOnLinks.add(drawerJwebmpLink);

        drawerBuiltOn.add(drawerBuiltOnLinks);
        burgerMenuNavigation.add(drawerBuiltOn);


        page.getMain().add(new RouterOutlet<>());
        page.getAside().add(new RouterOutlet<>("aside"));

        add(page);
    }

    private static WaTreeItem<?> createRouterTreeItem(String path, String text, String icon)
    {
        if (!path.startsWith("/"))
        {
            path = "/" + path;
        }

        WaTreeItem<?> item = new WaTreeItem<>();
        Link<?> link = new Link<>("#");
        item.add(link);
        link.addAttribute("routerLink", path);
        link.setRenderTextBeforeChildren(false);
        if (icon != null)
        {
            WaIcon<?> waIcon = new WaIcon<>(icon).addClass("wa-gap-1").addStyle("color", "var(--wa-color-brand-on-normal)");
            waIcon.setFamily("sharp-duotone");
            link.add(waIcon);
        }
        link.setText("&nbsp;"+ text);
        return item;
    }

    private static WaTreeItem<?> createExternalTreeItem(String url, String text, String icon)
    {
        WaTreeItem<?> item = new WaTreeItem<>();
        Link<?> link = new Link<>("#");
        item.add(link);
        link.addAttribute("href", url);
        link.addAttribute("target", "guicedee-external");
        link.setRenderTextBeforeChildren(false);
        if (icon != null)
        {
            WaIcon<?> waIcon = new WaIcon<>(icon).addClass("wa-gap-1").addStyle("color", "var(--wa-color-brand-on-normal)");
            waIcon.setFamily("sharp-duotone");
            link.add(waIcon);
        }
        link.setText("&nbsp;" + text);
        return item;
    }

    @Override
    public List<String> host() {
        return List.of("""
                {
                    '[style.width]': '"100%"',
                    '[style.height]': '"100%"',
                 }
                """);
    }

    @Override
    public List<String> providers() {
        var p = INgComponent.super.providers();
        return p;
    }

    @Override
    public List<String> fields() {
        var f = new ArrayList<>(INgComponent.super.fields());
        f.add("private router: Router = inject(Router);");
        f.add("private _asideNavigating = false;");
        f.add("private document = inject(DOCUMENT);");
        f.add("darkMode = signal(true);");
        f.add("""
                private asideRoutes: Record<string, string> = {
                    'home': 'home',
                    'getting-started': 'getting-started',
                    'capabilities': 'capabilities',
                    'guides/end-to-end': 'guides/end-to-end',
                    'modules': 'modules',
                    'services': 'services',
                    'environment-variables': 'environment-variables'
                };""");
        return f;
    }

    @Override
    public List<String> methods() {
        var m = new ArrayList<>(INgComponent.super.methods());
        m.add("""
                toggleDarkMode() {
                    const isDark = !this.darkMode();
                    this.darkMode.set(isDark);
                    this.document.body.classList.toggle('wa-dark', isDark);
                    localStorage.setItem('guicedee-theme', isDark ? 'dark' : 'light');
                }""");

        m.add("""
                onBuildToolChange(event: any) {
                    const value = event.target.checked;
                    this.app.useGradle.set(value);
                    localStorage.setItem('guicedee-build-tool', value ? 'gradle' : 'maven');
                }""");
        return m;
    }

    @Override
    public List<String> onInit() {
        var init = new ArrayList<>(INgComponent.super.onInit());
        init.add("registerLocaleData(localeEnZa, 'en-ZA')");
        init.add("""
                const savedTheme = localStorage.getItem('guicedee-theme');
                const prefersDark = savedTheme ? savedTheme === 'dark' : true;
                this.darkMode.set(prefersDark);
                this.document.body.classList.toggle('wa-dark', prefersDark);""");
        init.add("""
                const savedBuildTool = localStorage.getItem('guicedee-build-tool');
                if (savedBuildTool) {
                    this.app.useGradle.set(savedBuildTool === 'gradle');
                }""");
        init.add("""
                this.router.events.pipe(filter(e => e instanceof NavigationEnd)).subscribe((e: any) => {
                    if (this._asideNavigating) return;
                    const navEnd = e as NavigationEnd;
                    const parsedUrl = this.router.parseUrl(navEnd.urlAfterRedirects);
                    const primarySegments = parsedUrl.root.children['primary']?.segments || [];
                    const primaryPath = primarySegments.map((s: any) => s.path).join('/');
                    const asidePath = this.asideRoutes[primaryPath];
                    const currentAside = parsedUrl.root.children['aside'];
                    const currentAsidePath = currentAside?.segments?.map((s: any) => s.path).join('/') || null;
                    
                    if (asidePath && currentAsidePath !== asidePath) {
                        this._asideNavigating = true;
                        const asideSegments = asidePath.split('/');
                        const tree = this.router.createUrlTree([{outlets: {aside: asideSegments}}], {relativeTo: null as any});
                        tree.root.children['primary'] = parsedUrl.root.children['primary'];
                        tree.queryParams = parsedUrl.queryParams;
                        tree.fragment = parsedUrl.fragment;
                        this.router.navigateByUrl(tree, {replaceUrl: true})
                            .then(() => this._asideNavigating = false)
                            .catch(() => this._asideNavigating = false);
                    } else if (!asidePath && currentAside) {
                        this._asideNavigating = true;
                        delete parsedUrl.root.children['aside'];
                        this.router.navigateByUrl(parsedUrl, {replaceUrl: true})
                            .then(() => this._asideNavigating = false)
                            .catch(() => this._asideNavigating = false);
                    }
                });""");
        return init;
    }

}
