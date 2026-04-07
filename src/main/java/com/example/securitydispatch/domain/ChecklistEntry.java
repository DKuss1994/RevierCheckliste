package com.example.securitydispatch.domain;

public class ChecklistEntry {
    private final SecurityObject securityObject;
    private final StandardConfiguration resolvedConfiguration;
    public ChecklistEntry(SecurityObject securityObject, StandardConfiguration config) {
        if (securityObject == null ) {
            throw new IllegalArgumentException("ChecklistEntry security object must not be null");
        }
        if (config == null ) {
            throw new IllegalArgumentException("ChecklistEntry standard configuration must not be null");
        }
        this.securityObject = securityObject;
        this.resolvedConfiguration = config;

    }

    public SecurityObject getSecurityObject() {
        return securityObject;
    }

    public StandardConfiguration getResolvedConfiguration() {
        return resolvedConfiguration;
    }

}
