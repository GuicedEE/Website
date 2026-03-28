package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.services.RouterOutlet;
import com.jwebmp.core.base.html.Div;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.Size;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.button.WaDropDown;
import com.jwebmp.webawesome.components.button.WaDropdownItem;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.input.InputSize;
import com.jwebmp.webawesome.components.input.WaInput;
import com.jwebmp.webawesome.components.tabgroup.WaTab;
import com.jwebmp.webawesome.components.tabgroup.WaTabGroup;
import com.jwebmp.webawesome.components.tabgroup.WaTabPanel;
import com.jwebmp.webawesomepro.components.page.WaPage;

@NgComponent("guicedee-app")
@NgRoutable(path = "")
public class WebsiteBoot extends DivSimple<WebsiteBoot> implements INgComponent<WebsiteBoot>
{
    public WebsiteBoot()
    {
        addStyle("width:100%");
        addStyle("height:100%");

        addClass("wa-dark");

        //this has the menu bar menu and header
        WaPage<?> page = new WaPage<>();
        page.setNavOpen(true);

        var header = page.getHeader();
        WaDropDown<?> navDropdown = new WaDropDown<>();
        WaButton<?> dropdownButton = new WaButton<>();
        dropdownButton.add(new WaIcon<>("menu"));
        dropdownButton.addStyle("background: none; border: none; padding: 0; color: inherit;");
        dropdownButton.setSize(Size.Small);
        navDropdown.setButton(dropdownButton);
        navDropdown.addItem(new WaDropdownItem<>("Home"));
        navDropdown.addItem(new WaDropdownItem<>("Modules"));
        navDropdown.addItem(new WaDropdownItem<>("Documentation"));
        navDropdown.addItem(new WaDropdownItem<>("Support"));
        navDropdown.addItem(new WaDropdownItem<>("Community"));
        navDropdown.addItem(new WaDropdownItem<>("Github"));
        navDropdown.addItem(new WaDropdownItem<>("Contact"));
        navDropdown.addItem(new WaDropdownItem<>("About"));
        navDropdown.addItem(new WaDropdownItem<>("License"));
        navDropdown.addItem(new WaDropdownItem<>("Downloads"));
        navDropdown.addItem(new WaDropdownItem<>("FAQ"));
        navDropdown.addStyle("max-height: 250px; overflow-y: auto;");

        // Tabs
        WaTabGroup<?> tabs = new WaTabGroup<>()
                .addStyle("width: auto;");

        tabs.addTab(new WaTab<>().setPanel("guicedPanel").add(new WaIcon<>("home")).add("GuicedEE"),
                new WaTabPanel<>().setName("guicedPanel").addStyle("height:100%").add(new RouterOutlet()), true);

        tabs.addTab(new WaTab<>().setPanel("jwebPanel").add(new WaIcon<>("globe")).add("JWebMP"),
                new WaTabPanel<>().setName("jwebPanel").addStyle("height:100%").add("JWebMP Content"), false);

        tabs.addTab(new WaTab<>().setPanel("entityasssitPanel").add(new WaIcon<>("database")).add("EntityAssist"),
                new WaTabPanel<>().setName("entityasssitPanel").addStyle("height:100%").add("EntityAssist Content"), false);

        tabs.addTab(new WaTab<>().setPanel("activityMasterPanel").add(new WaIcon<>("tasks")).add("ActivityMaster"),
                new WaTabPanel<>().setName("activityMasterPanel").addStyle("height:100%").add("ActivityMaster Content"), false);

        header.add(navDropdown);
        header.add(tabs);

        header.setSplit();

        WaInput<?> search = new WaInput<>().setPlaceholder("Search...");
        search.setSize(InputSize.SMALL);
        search.addStyle("margin: 0 10px;");
        header.add(search);

        WaButton<?> themeToggle = new WaButton<>().add(new WaIcon<>("moon"));
        themeToggle.setSize(Size.Small);
        themeToggle.addStyle("background: none; border: none; padding: 0; color: inherit; margin-right: 15px;");
        header.add(themeToggle);

        add(page);

    }
}
