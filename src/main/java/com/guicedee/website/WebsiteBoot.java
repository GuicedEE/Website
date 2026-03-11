package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.services.RouterOutlet;
import com.jwebmp.core.base.html.DivSimple;

@NgComponent("guicedee-app")
@NgRoutable(path = "")
public class WebsiteBoot extends DivSimple<WebsiteBoot> implements INgComponent<WebsiteBoot>
{
    public WebsiteBoot()
    {
        add(new RouterOutlet());
    }
}
