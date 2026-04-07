package com.example.securitydispatch.domain;

public class ChecklistEntry {
    private final SecurityObject securityObject;
    private final StandardConfiguration config;
    public ChecklistEntry(SecurityObject securityObject, StandardConfiguration config) {
        this.securityObject = securityObject;
        this.config = config;

    }

    public SecurityObject getSecurityObject() {
        return securityObject;
    }

    public StandardConfiguration getConfig() {
        return config;
    }
    public StandardConfiguration getResolvedConfiguration(){
        return config;
    }
}
