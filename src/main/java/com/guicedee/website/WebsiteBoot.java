package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.services.RouterOutlet;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.button.WaDropDown;
import com.jwebmp.webawesome.components.button.WaDropdownItem;
import com.jwebmp.webawesome.components.divider.WaDivider;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.tabgroup.WaTab;
import com.jwebmp.webawesome.components.tabgroup.WaTabGroup;
import com.jwebmp.webawesome.components.tabgroup.WaTabPanel;
import com.jwebmp.webawesomepro.components.page.WaPage;

@NgComponent("guicedee-app")
@NgRoutable(path = "")
/**
 * Top Level = Tab Groups x 4, Only first has content
 * Tab Group Content is a WaPage with header, navigation, menu, main-header, main (as per docs)
 * The page has all the site details
 */
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

        var menu = page.getMenu();
        menu.add("Home");

        var navigation = page.getNavigation();
        navigation.add("GuicedEE Navigation Links");

        var header = page.getHeader();
        header.add("GuicedEE Website");

        var mainHeader = page.getMainHeader();
        var main = page.getMain();
        main.add(new RouterOutlet());


        // Tabs
        WaTabGroup<?> tabs = new WaTabGroup<>()
                .addStyle("width:100%")
                .addStyle("height:100%");

        tabs.addTab(new WaTab<>().setPanel("guicedPanel").add(new WaIcon("home")).add("GuicedEE"),
                new WaTabPanel<>().setName("guicedPanel").addStyle("height:100%").add(page), true);

        tabs.addTab(new WaTab<>().setPanel("jwebPanel").add(new WaIcon("globe")).add("JWebMP"),
                new WaTabPanel<>().setName("jwebPanel").addStyle("height:100%").add("JWebMP Content"), false);

        tabs.addTab(new WaTab<>().setPanel("entityasssitPanel").add(new WaIcon("database")).add("EntityAssist"),
                new WaTabPanel<>().setName("entityasssitPanel").addStyle("height:100%").add("EntityAssist Content"), false);

        tabs.addTab(new WaTab<>().setPanel("activityMasterPanel").add(new WaIcon("tasks")).add("ActivityMaster"),
                new WaTabPanel<>().setName("activityMasterPanel").addStyle("height:100%").add("ActivityMaster Content"), false);

        add(tabs);

    }
}
