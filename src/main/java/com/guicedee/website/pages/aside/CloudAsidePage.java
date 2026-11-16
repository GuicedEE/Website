package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;

/** Compatibility route for old aside URLs; WebsiteBoot removes the named outlet. */
@NgComponent("guicedee-cloud-aside")
@NgRoutable(path = "cloud", outlet = "aside")
public class CloudAsidePage extends DivSimple<CloudAsidePage> implements INgComponent<CloudAsidePage> {
    public CloudAsidePage() { setTag("ng-container"); }
}
