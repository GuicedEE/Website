package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;

/** Compatibility route for old aside URLs; WebsiteBoot removes the named outlet. */
@NgComponent("guicedee-capabilities-aside")
@NgRoutable(path = "capabilities", outlet = "aside")
public class CapabilitiesAsidePage extends DivSimple<CapabilitiesAsidePage> implements INgComponent<CapabilitiesAsidePage> {
    public CapabilitiesAsidePage() { setTag("ng-container"); }
}
