package com.guicedee.website.catalog;

import lombok.Getter;

@Getter
public final class ModuleEntry
{
    private final String id;
    private final String name;
    private final String description;
    private final String bootClass;
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String readmePath;
    private final String rulesPath;

    public ModuleEntry(String id,
                       String name,
                       String description,
                       String bootClass,
                       String groupId,
                       String artifactId,
                       String version,
                       String readmePath,
                       String rulesPath)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bootClass = bootClass;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.readmePath = readmePath;
        this.rulesPath = rulesPath;
    }
}
