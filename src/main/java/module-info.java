import com.guicedee.website.WebsitePageConfigurator;
import com.jwebmp.core.base.angular.client.services.TypescriptIndexPageConfigurator;
import com.jwebmp.core.services.IPageConfigurator;

open module website {
    requires com.guicedee.client;
    requires com.guicedee.vertx;
    requires com.jwebmp.client;
    requires com.jwebmp.core;
    requires com.jwebmp.core.angular;
    requires com.jwebmp.core.base.angular.client;
    requires com.jwebmp.plugins.markdown;
    requires com.jwebmp.webawesome;
    requires com.jwebmp.webawesomepro;
    requires static lombok;
    requires org.apache.commons.text;
    requires com.jwebmp.plugins.fontawesome5pro;

    provides IPageConfigurator with WebsitePageConfigurator;
    provides TypescriptIndexPageConfigurator with WebsitePageConfigurator;

}