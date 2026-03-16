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
                .add(new WaTabPanel<>().setName("GuicedEE").add(new RouterOutlet()))
                .add(new WaTab<>().setPanel("jwebmpPanel").setName("JWebMP").setText("JWebMP").setActiveTabColor("#FF007F"))
                .add(new WaTabPanel<>().setName("JWebMP").add("This should be a new url redirect"))
                .add(new WaTab<>().setPanel("entityPanel").setName("Entity Assist").setText("Entity Assist").setActiveTabColor("#FF007F"))
                .add(new WaTabPanel<>().setName("Entity Assist").add("This should be a new url redirect"))
                .add(new WaTab<>().setPanel("activityPanel").setName("Activity Master").setText("Activity Master").setActiveTabColor("#FF007F"))
                .add(new WaTabPanel<>().setName("Activity Master").add("This should be a new url redirect"))
        );
    }
}
//what port? 4200
//perfect looks like its working

//okkk, this brings in a new avenue for the angular plugin, removing the use of the boot run for client only oooo


//for now just keep this running in ng serve, your updates will filter through, i'll sort out the angular plugin

//Alright




//The test classes are the best placed to see how to use components (y)
//im assuming because test classes are a safer enviroment? Yeah - and because thats how we confirm the component works as well
//so we test each component in a test class? - Yes
// It also shows us to configure the java components properly --- I think
//is there a seperate folder that is used for testing? (all maven has src/main/java - and src/test/java )
//so what do I need to do right now or am i still in the learning phase? You can do the ->


//So like tab icons, colours names, sizing, and then the url links for jwebmp, entity assist, activity master
// Tabs are -> GuicedEE  -> JWebMP -> Entity Assist -> Activity Master
//We are only doing the GuicedEE site now

//Then the left hand (nagivation-menu) per item instead of whatever it is now (don't worry about splitting content here. this is the scaffolding phase)
//And the burger menu when the site is small (wa-page has special handling for this)

//Ok so for now just edit the tabs and add a navigation meny on the left hand side - yes, although i think it is called navigation-menu in wa-page, or similar

