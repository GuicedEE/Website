package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.services.RouterOutlet;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.tabgroup.WaTab;
import com.jwebmp.webawesome.components.tabgroup.WaTabGroup;
import com.jwebmp.webawesome.components.tabgroup.WaTabPanel;

@NgComponent("guicedee-app")
@NgRoutable(path = "")
public class WebsiteBoot extends DivSimple<WebsiteBoot> implements INgComponent<WebsiteBoot>
{
    public WebsiteBoot()
    {
        addStyle("width:100%");
        addStyle("height:100%");

        add(new WaTabGroup<>()
                .addStyle("height:100%")
                .addStyle("width:100%")
                .add(new WaTab<>().setPanel("guicedPanel").setName("GuicedEE").setText("GuicedEE").setActiveTabColor("#FF007F"))
                .add(new WaTabPanel<>().setName("guicedPanel").add(new RouterOutlet()))
                .add(new WaTab<>().setPanel("jwebmpPanel").setName("JWebMP").setText("JWebMP").setActiveTabColor("#FF007F"))
                .add(new WaTabPanel<>().setName("jwebmpPanel").add("This should be a new url redirect"))
                .add(new WaTab<>().setPanel("entityPanel").setName("Entity Assist").setText("Entity Assist").setActiveTabColor("#FF007F"))
                .add(new WaTabPanel<>().setName("entityPanel").add("This should be a new url redirect"))
                .add(new WaTab<>().setPanel("activityPanel").setName("Activity Master").setText("Activity Master").setActiveTabColor("#FF007F"))
                .add(new WaTabPanel<>().setName("activityPanel").add("This should be a new url redirect"))
        );
    }
}
