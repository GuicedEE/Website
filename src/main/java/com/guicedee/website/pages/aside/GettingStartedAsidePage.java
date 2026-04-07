package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.text.WaText;

/**
 * Aside component for the Getting Started page.
 * Renders "On this page" anchor links in the named "aside" router-outlet.
 */
@NgComponent("guicedee-getting-started-aside")
@NgRoutable(path = "getting-started", outlet = "aside")
public class GettingStartedAsidePage extends DivSimple<GettingStartedAsidePage> implements INgComponent<GettingStartedAsidePage>
{
    public GettingStartedAsidePage()
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

        list.add(asideLink("prerequisites", "Prerequisites"));
        list.add(asideLink("step-1", "Step 1: Create the project"));
        list.add(asideLink("step-2", "Step 2: Module descriptor"));
        list.add(asideLink("step-3", "Step 3: Bootstrap"));
        list.add(asideLink("step-4", "Step 4: REST endpoint"));
        list.add(asideLink("step-5", "Step 5: Run it"));
        list.add(asideLink("what-just-happened", "What just happened?"));
        list.add(asideLink("what-s-next", "Next steps"));

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


