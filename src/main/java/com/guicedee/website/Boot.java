package com.guicedee.website;

import com.guicedee.client.IGuiceContext;
import com.guicedee.client.utils.LogUtils;
import com.guicedee.website.builder.ApplicationBuilderService;
import com.jwebmp.webawesome.components.WebAwesomePageConfigurator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class Boot
{
    
    private Boot()
    {
    }

    public static void main(String[] args)
    {
        System.setProperty("HTTP_ENABLED", "true");
        System.setProperty("HTTP_PORT", "8765");
        System.setProperty("jwebmp.process.angular.ts", "true");
        LogUtils.addHighlightedConsoleLogger();
		
        
				
        IGuiceContext.instance().inject();
        ApplicationBuilderService.getInstance().initialize();
        log.info("GuicedEE Website ready.");
    }
}

