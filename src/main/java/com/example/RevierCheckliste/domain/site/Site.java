package com.example.RevierCheckliste.domain.site;

public class Site {

    private String name;
    private StandardConfiguration configuration;

    public Site(String name, StandardConfiguration configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    public StandardConfiguration getConfiguration() {
        return configuration;
    }
}