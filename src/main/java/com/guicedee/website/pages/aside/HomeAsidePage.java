package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.text.WaText;

/**
 * Aside component for the Home page.
 * Renders "On this page" anchor links in the named "aside" router-outlet.
 */
@NgComponent("guicedee-home-aside")
@NgRoutable(path = "home", outlet = "aside")
public class HomeAsidePage extends DivSimple<HomeAsidePage> implements INgComponent<HomeAsidePage>
{
    public HomeAsidePage()
    {
        setTag("aside");
        addClass("page-aside");
        addStyle("position:sticky");
        addStyle("top:var(--wa-spacing-large)");
        addStyle("padding:0 var(--wa-spacing-large) var(--wa-spacing-large) var(--wa-spacing-large)");
        addStyle("min-width:14rem");

        var heading = new WaText<>();
        heading.setTag("div");
        heading.setWaCaption("s");
        heading.setWaFontWeight("semibold");
        heading.addClass("hero-eyebrow");
        heading.setText("On this page");
        add(heading);

        var list = new DivSimple<>();
        list.setTag("ul");
        list.addStyle("list-style:none");
        list.addStyle("padding:0");
        list.addStyle("margin:var(--wa-spacing-small) 0 0 0");
        list.addStyle("display:flex");
        list.addStyle("flex-direction:column");
        list.addStyle("gap:var(--wa-spacing-x-small)");

        list.add(asideLink("hero", "Overview"));
        list.add(asideLink("how-it-works", "How it works"));
        list.add(asideLink("module-catalog", "Module catalog"));
        list.add(asideLink("zero-file-based-config-philosophy", "Zero config"));
        list.add(asideLink("see-the-code", "Code examples"));
        list.add(asideLink("rest-services", "REST services"));
        list.add(asideLink("security", "Security"));
        list.add(asideLink("vert-x-deployment", "Vert.x deployment"));
        list.add(asideLink("microprofile-native", "MicroProfile"));
        list.add(asideLink("foundations", "Foundations"));
        list.add(asideLink("modular-by-design", "Modular build"));
        list.add(asideLink("ship-as-jlink-artifacts", "JLink & Docker"));
        list.add(asideLink("cloud-aware-logging", "Logging"));
        list.add(asideLink("for-developers", "For developers"));

        add(list);
    }

    private DivSimple<?> asideLink(String anchorId, String label)
    {
        var li = new DivSimple<>();
        li.setTag("li");

        var link = new Link<>();
        link.setTag("a");
        link.addAttribute("href", "javascript:void(0)");
        link.addAttribute("onclick",
                "document.getElementById('" + anchorId + "').scrollIntoView({behavior:'smooth'})");
        link.setText(label);
        li.add(link);

        return li;
    }
}

