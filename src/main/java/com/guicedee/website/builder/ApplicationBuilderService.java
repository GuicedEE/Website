package com.guicedee.website.builder;

import com.guicedee.website.catalog.ModuleCatalog;
import com.guicedee.website.catalog.ModuleEntry;
import com.guicedee.website.catalog.ServiceDefinition;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class ApplicationBuilderService
{
    private static final AtomicReference<ApplicationBuilderService> INSTANCE = new AtomicReference<>();

    private ApplicationBuilderService()
    {
    }

    public static ApplicationBuilderService getInstance()
    {
        INSTANCE.compareAndSet(null, new ApplicationBuilderService());
        return INSTANCE.get();
    }

    public void initialize()
    {
        // placeholder for builder warm-up logic, content generation, dependency checks
    }

    public List<ModuleEntry> getAvailableModules()
    {
        return ModuleCatalog.getModules();
    }

    public List<ServiceDefinition> getAvailableServices()
    {
        return ModuleCatalog.getServices();
    }
}
