package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.input.InputSize;
import com.jwebmp.webawesome.components.input.WaInput;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.text.WaText;

import java.util.ArrayList;
import java.util.List;

/**
 * Aside component for the Modules page.
 * Renders a search filter and "On this page" anchor links in the named "aside" router-outlet.
 */
@NgComponent("guicedee-modules-aside")
@NgRoutable(path = "modules", outlet = "aside")
public class ModulesAsidePage extends DivSimple<ModulesAsidePage> implements INgComponent<ModulesAsidePage>
{
    public ModulesAsidePage()
    {
        setTag("aside");
        addClass("page-aside");
        addStyle("position:sticky");
        addStyle("top:var(--wa-spacing-large)");
        addStyle("padding:0 var(--wa-spacing-large) var(--wa-spacing-large) var(--wa-spacing-large)");
        addStyle("min-width:14rem");

        // Search input
        var search = new WaInput<>();
        search.setPlaceholder("Filter modules\u2026");
        search.setSize(InputSize.Small);
        search.setClearable(true);
        search.addAttribute("(wa-input)", "onFilterChange($event)");
        search.addAttribute("(wa-clear)", "onFilterChange($event)");

        var searchIcon = new WaIcon<>("magnifying-glass");
        searchIcon.addAttribute("slot", "start");
        search.add(searchIcon);
        search.addStyle("margin-bottom", "var(--wa-spacing-medium)");
        add(search);

        // Section heading
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

        list.add(asideLink("core-modules", "Core modules"));
        list.add(asideLink("web-rest", "Web & REST"));
        list.add(asideLink("data-persistence", "Data & persistence"));
        list.add(asideLink("observability", "Observability"));
        list.add(asideLink("integration", "Integration"));
        list.add(asideLink("migration-compatibility", "Migration"));

        add(list);
    }

    @Override
    public List<String> methods()
    {
        var m = new ArrayList<String>();
        m.add("""
                onFilterChange(event: any) {
                    const query = (event?.target?.value || '').toLowerCase().trim();
                    const cards = document.querySelectorAll('wa-card[appearance="outlined"]');
                    cards.forEach((card: any) => {
                        const text = card.textContent?.toLowerCase() || '';
                        if (!query || text.includes(query)) {
                            card.style.display = '';
                        } else {
                            card.style.display = 'none';
                        }
                    });
                }
                """);
        return m;
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
