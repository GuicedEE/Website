package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.text.WaText;

/**
 * Aside component for the Capabilities page.
 * Renders "On this page" anchor links in the named "aside" router-outlet.
 */
@NgComponent("guicedee-capabilities-aside")
@NgRoutable(path = "capabilities", outlet = "aside")
public class CapabilitiesAsidePage extends DivSimple<CapabilitiesAsidePage> implements INgComponent<CapabilitiesAsidePage>
{
    public CapabilitiesAsidePage()
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

        list.add(asideLink("architecture", "Architecture"));
        list.add(asideLink("injection-lifecycle", "Injection & Lifecycle"));
        list.add(asideLink("http-server", "HTTP Server"));
        list.add(asideLink("rest-jax-rs", "REST (JAX-RS)"));
        list.add(asideLink("rest-client", "REST Client"));
        list.add(asideLink("security", "Security"));
        list.add(asideLink("verticles-deployment", "Verticles"));
        list.add(asideLink("persistence", "Persistence"));
        list.add(asideLink("websockets", "WebSockets"));
        list.add(asideLink("messaging", "Messaging"));
        list.add(asideLink("observability", "Observability"));
        list.add(asideLink("logging", "Logging"));
        list.add(asideLink("configuration", "Configuration"));
        list.add(asideLink("fault-tolerance", "Fault Tolerance"));
        list.add(asideLink("jlink-deployment", "JLink & Deployment"));
        list.add(asideLink("full-module-catalog", "Full catalog"));

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


