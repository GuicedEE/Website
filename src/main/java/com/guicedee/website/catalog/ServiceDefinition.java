package com.guicedee.website.catalog;

import lombok.Getter;

@Getter
public final class ServiceDefinition
{
    private final String id;
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String description;
    private final String module;

    public ServiceDefinition(String id,
                             String groupId,
                             String artifactId,
                             String version,
                             String description,
                             String module)
    {
        this.id = id;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.description = description;
        this.module = module;
    }
}
