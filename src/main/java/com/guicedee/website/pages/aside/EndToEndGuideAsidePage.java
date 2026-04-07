package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.text.WaText;

/**
 * Aside component for the End-to-End Guide page.
 * Renders "On this page" anchor links in the named "aside" router-outlet.
 */
@NgComponent("guicedee-end-to-end-aside")
@NgRoutable(path = "guides/end-to-end", outlet = "aside")
public class EndToEndGuideAsidePage extends DivSimple<EndToEndGuideAsidePage> implements INgComponent<EndToEndGuideAsidePage>
{
    public EndToEndGuideAsidePage()
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

        list.add(asideLink("project-setup", "Project setup"));
        list.add(asideLink("module-descriptor", "Module descriptor"));
        list.add(asideLink("guice-module-bootstrap", "Guice module & bootstrap"));
        list.add(asideLink("rest-endpoints", "REST endpoints"));
        list.add(asideLink("security", "Security"));
        list.add(asideLink("cors", "CORS"));
        list.add(asideLink("rest-client", "REST client"));
        list.add(asideLink("health-checks", "Health checks"));
        list.add(asideLink("configuration", "Configuration"));
        list.add(asideLink("persistence", "Persistence"));
        list.add(asideLink("verticle-isolation", "Verticle isolation"));
        list.add(asideLink("cloud-aware-logging", "Cloud logging"));
        list.add(asideLink("jlink-deployment", "JLink deployment"));
        list.add(asideLink("lifecycle-environment", "Lifecycle & env vars"));
        list.add(asideLink("module-deep-dives", "Module deep-dives"));

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


