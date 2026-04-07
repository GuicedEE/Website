package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportProvider;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportProvider;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.services.RouterOutlet;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.toast.WaToastDataService;
import com.jwebmp.webawesome.components.tooltip.WaTooltip;
import com.jwebmp.webawesome.components.tree.WaTree;
import com.jwebmp.webawesome.components.tree.WaTreeItem;
import com.jwebmp.webawesome.components.page.WaPage;

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
@NgBootImportReference(value = "localeEnZa", reference = "@angular/common/locales/en-ZA", direct = true)
@NgImportReference(value = "localeEnZa", reference = "@angular/common/locales/en-ZA", direct = true, wrapValueInBraces = false)
@NgImportReference(value = "signal", reference = "@angular/core")
@NgImportReference(value = "DOCUMENT", reference = "@angular/common")
@NgImportReference(value = "Router, NavigationStart, NavigationEnd", reference = "@angular/router")
@NgImportReference(value = "filter", reference = "rxjs/operators")
@NgComponentReference(WaToastDataService.class)
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
        Link<?> guicedeeLink = new Link<>();
        guicedeeLink.setTag("a");
        guicedeeLink.addAttribute("routerLink", "/home");
        guicedeeLink.addClass("product");
        guicedeeLink.addClass("product-guicedee");
        guicedeeLink.addClass("product-active");
        guicedeeLink.addClass("appearance-plain");
        guicedeeLink.addAttribute("aria-label", "GuicedEE");
        var guicedeeLogo = new DivSimple<>();
        guicedeeLogo.setTag("i");
        guicedeeLogo.addClass("fak");
        guicedeeLogo.addClass("fa-guicedee-logo");
        guicedeeLogo.addClass("logo-icon");
        guicedeeLogo.addClass("logo-guicedee");
        guicedeeLink.add(guicedeeLogo);
        guicedeeLink.setText("GuicedEE");
        guicedeeLink.setRenderTextBeforeChildren(false);
        cluster.add(guicedeeLink);

        // JWebMP
        Link<?> jwebmpLink = new Link<>();
        jwebmpLink.setTag("a");
        jwebmpLink.addAttribute("href", "https://jwebmp.com");
        jwebmpLink.addAttribute("target", "_blank");
        jwebmpLink.addClass("product");
        jwebmpLink.addClass("product-jwebmp");
        jwebmpLink.addClass("appearance-plain");
        jwebmpLink.setID("product-jwebmp");
        var jwebmpLogo = new DivSimple<>();
        jwebmpLogo.setTag("i");
        jwebmpLogo.addClass("fak");
        jwebmpLogo.addClass("fa-jwebmp-logo-green");
        jwebmpLogo.addClass("logo-icon");
        jwebmpLogo.addClass("logo-jwebmp");
        jwebmpLink.add(jwebmpLogo);
        cluster.add(jwebmpLink);
        WaTooltip<?> jwebmpTip = new WaTooltip<>();
        jwebmpTip.setForId("product-jwebmp");
        jwebmpTip.setText("JWebMP");
        cluster.add(jwebmpTip);

        // Entity Assist
        Link<?> entityLink = new Link<>();
        entityLink.setTag("a");
        entityLink.addAttribute("href", "https://entityassist.com");
        entityLink.addAttribute("target", "_blank");
        entityLink.addClass("product");
        entityLink.addClass("product-entity-assist");
        entityLink.addClass("appearance-plain");
        entityLink.setID("product-entity-assist");
        entityLink.add(new WaIcon<>("database").addClass("logo-icon").addClass("logo-entity-assist"));
        cluster.add(entityLink);
        WaTooltip<?> entityTip = new WaTooltip<>();
        entityTip.setForId("product-entity-assist");
        entityTip.setText("Entity Assist");
        cluster.add(entityTip);

        // Activity Master
        Link<?> activityLink = new Link<>();
        activityLink.setTag("a");
        activityLink.addAttribute("href", "https://github.com/Activity-Master/");
        activityLink.addAttribute("target", "_blank");
        activityLink.addClass("product");
        activityLink.addClass("product-activity-master");
        activityLink.addClass("appearance-plain");
        activityLink.setID("product-activity-master");
        activityLink.add(new WaIcon<>("tasks").addClass("logo-icon").addClass("logo-activity-master"));
        cluster.add(activityLink);
        WaTooltip<?> activityTip = new WaTooltip<>();
        activityTip.setForId("product-activity-master");
        activityTip.setText("Activity Master");
        cluster.add(activityTip);

        primary.add(cluster);

        // Secondary links (GitHub, Blog)
        DivSimple<?> secondary = new DivSimple<>();
        secondary.addClass("nav-products-secondary");
        secondary.addClass("wa-cluster");
        secondary.addClass("wa-gap-2xs");

        WaButton<?> githubBtn = new WaButton<>();
        githubBtn.setAppearance(Appearance.Plain);
        githubBtn.setVariant(Variant.Brand);
        githubBtn.setAsLink("https://github.com/GuicedEE/", "_blank", null);
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
        starBtn.setAsLink("https://github.com/GuicedEE/GuicedInjection", "_blank", null);
        starBtn.addClass("pseudo-product");
        starBtn.addClass("product-star");
        starBtn.setID("product-star");
        starBtn.add(new WaIcon<>("star").addAttribute("label", "Star this Repository"));
        secondary.add(starBtn);
        WaTooltip<?> starTip = new WaTooltip<>();
        starTip.setForId("product-star");
        starTip.setText("Star this Repository");
        secondary.add(starTip);

        WaButton<?> docsBtn = new WaButton<>();
        docsBtn.setAppearance(Appearance.Plain);
        docsBtn.setVariant(Variant.Brand);
        docsBtn.setAsLink("https://github.com/GuicedEE/ai-rules", "_blank", null);
        docsBtn.addClass("pseudo-product");
        docsBtn.addClass("product-docs");
        docsBtn.setID("product-docs");
        docsBtn.add(new WaIcon<>("brain-circuit").addAttribute("label", "AI Skills Repository"));
        secondary.add(docsBtn);
        WaTooltip<?> docsTip = new WaTooltip<>();
        docsTip.setForId("product-docs");
        docsTip.setText("AI Skills Repository");
        secondary.add(docsTip);

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

        // Releases
        menuTree.add(createRouterTreeItem("/releases", "Releases", "tag"));

        // App Builder
        menuTree.add(createRouterTreeItem("/builder", "App Builder", "wand-magic-sparkles"));

        // Media
        menuTree.add(createRouterTreeItem("/media", "Media", "photo-film"));


        menu.add(menuTree);

        // ── Navigation Toggle (burger button, slot="navigation-toggle") ──
        var navToggle = page.getNavigationToggle();
        WaButton<?> burgerBtn = new WaButton<>();
        burgerBtn.setAppearance(Appearance.Plain);
        burgerBtn.setVariant(Variant.Neutral);
        burgerBtn.addAttribute("aria-label", "Toggle navigation menu");
        burgerBtn.add(new WaIcon<>("bars"));
        navToggle.add(burgerBtn);

        // ── Navigation Toggle Icon (slot="navigation-toggle-icon") ──
        var navToggleIcon = page.getNavigationToggleIcon();
        navToggleIcon.add(new WaIcon<>("bars"));

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
        navHeader.add(drawerLogo);

        // ── Burger Menu Navigation (drawer contents, slot="navigation") ──
        var burgerMenuNavigation = page.getNavigation();
        WaTree<?> navTree = new WaTree<>();
        navTree.setIndentSize("2px");
        navTree.setIndentGuideColor("var(--wa-color-neutral-300)");

        navTree.add(createRouterTreeItem("/home", "Home", "house"));
        navTree.add(createRouterTreeItem("/getting-started", "Getting Started", "rocket"));
        navTree.add(createRouterTreeItem("/guides/end-to-end", "End-to-End Guide", "route"));
        navTree.add(createRouterTreeItem("/capabilities", "Capabilities", "star"));
        navTree.add(createRouterTreeItem("/modules", "Modules", "cubes"));
        navTree.add(createRouterTreeItem("/services", "Services", "puzzle-piece"));
        navTree.add(createRouterTreeItem("/releases", "Releases", "tag"));
        navTree.add(createRouterTreeItem("/builder", "App Builder", "wand-magic-sparkles"));
        navTree.add(createRouterTreeItem("/media", "Media", "photo-film"));
        burgerMenuNavigation.add(navTree);

        // ── Navigation Footer (external links inside the drawer, slot="navigation-footer") ──
        var navFooter = page.getNavigationFooter();
        Link<?> navGithubLink = new Link<>();
        navGithubLink.setTag("a");
        navGithubLink.addAttribute("href", "https://github.com/GuicedEE/");
        navGithubLink.addAttribute("target", "_blank");
        navGithubLink.add(new WaIcon<>("github").addAttribute("family", "brands"));
        navGithubLink.setText("GitHub");
        navFooter.add(navGithubLink);

        Link<?> navJwebmpLink = new Link<>();
        navJwebmpLink.setTag("a");
        navJwebmpLink.addAttribute("href", "https://jwebmp.com");
        navJwebmpLink.addAttribute("target", "_blank");
        navJwebmpLink.add(new WaIcon<>("globe"));
        navJwebmpLink.setText("JWebMP");
        navFooter.add(navJwebmpLink);

        page.getMain().add(new RouterOutlet());
        page.getAside().add(new RouterOutlet("aside"));

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
            WaIcon<?> waIcon = new WaIcon<>(icon).addClass("wa-gap-1").addStyle("color: var(--wa-color-brand-on-normal)");
            link.add(waIcon);
        }
        link.setText("&nbsp;"+ text);
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
        p.add("provideLocaleData(localeEnZa, 'en-ZA'");
        return p;
    }

    @Override
    public List<String> fields() {
        var f = new ArrayList<>(INgComponent.super.fields());
        f.add("private router: Router = inject(Router);");
        f.add("private _asideNavigating = false;");
        f.add("private document = inject(DOCUMENT);");
        f.add("darkMode = signal(true);");
        f.add("useGradle = false;");
        f.add("""
                private asideRoutes: Record<string, string> = {
                    'getting-started': 'getting-started',
                    'capabilities': 'capabilities',
                    'guides/end-to-end': 'guides/end-to-end',
                    'modules': 'modules'
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
                onBuildToolChange(value: boolean) {
                    this.useGradle = value;
                    localStorage.setItem('guicedee-build-tool', value ? 'gradle' : 'maven');
                    window.dispatchEvent(new CustomEvent('guicedee-build-tool-change', { detail: value }));
                }""");
        return m;
    }

    @Override
    public List<String> onInit() {
        var init = new ArrayList<>(INgComponent.super.onInit());
        init.add("""
                const savedTheme = localStorage.getItem('guicedee-theme');
                const prefersDark = savedTheme ? savedTheme === 'dark' : true;
                this.darkMode.set(prefersDark);
                this.document.body.classList.toggle('wa-dark', prefersDark);""");
        init.add("""
                const savedBuildTool = localStorage.getItem('guicedee-build-tool');
                if (savedBuildTool) {
                    this.useGradle = savedBuildTool === 'gradle';
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
