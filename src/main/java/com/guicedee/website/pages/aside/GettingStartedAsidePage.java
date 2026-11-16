package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;

/** Compatibility route for old aside URLs; WebsiteBoot removes the named outlet. */
@NgComponent("guicedee-getting-started-aside")
@NgRoutable(path = "getting-started", outlet = "aside")
public class GettingStartedAsidePage extends DivSimple<GettingStartedAsidePage> implements INgComponent<GettingStartedAsidePage> {
    public GettingStartedAsidePage() { setTag("ng-container"); }
}
