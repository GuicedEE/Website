package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;

/**
 * Compatibility route for bookmarked /home(aside:home) URLs.
 * WebsiteBoot clears this outlet; homepage section links now live under Home.
 */
@NgComponent("guicedee-home-aside")
@NgRoutable(path = "home", outlet = "aside")
public class HomeAsidePage extends DivSimple<HomeAsidePage> implements INgComponent<HomeAsidePage>
{
    public HomeAsidePage()
    {
        setTag("ng-container");
    }
}
