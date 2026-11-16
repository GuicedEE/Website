package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;

/** Compatibility route for old aside URLs; WebsiteBoot removes the named outlet. */
@NgComponent("guicedee-services-aside")
@NgRoutable(path = "services", outlet = "aside")
public class ServicesAsidePage extends DivSimple<ServicesAsidePage> implements INgComponent<ServicesAsidePage> {
    public ServicesAsidePage() { setTag("ng-container"); }
}
