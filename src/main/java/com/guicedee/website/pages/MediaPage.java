package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.webawesome.components.WaStack;

@NgComponent("guicedee-media")
@NgRoutable(path = "media")
public class MediaPage extends WebsitePage<MediaPage>
{
    public MediaPage()
    {
        newMainSection().add("Collections of imagery, video, and release notes for marketing campaigns.");
    }
}

