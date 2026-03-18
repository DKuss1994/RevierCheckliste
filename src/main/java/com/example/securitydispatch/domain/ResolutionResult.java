package com.example.securitydispatch.domain;

import java.util.List;
import java.util.Objects;

public class ResolutionResult {
    private final StandardConfiguration configuration;
    private final List<Warning> warnings;

    public ResolutionResult(StandardConfiguration configuration, List<Warning> waring) {
        this.configuration = Objects.requireNonNull(configuration,"Configuration must not be null");
        this.warnings = Objects.requireNonNull(waring,"Warning must not be null");
    }

    public StandardConfiguration getConfiguration() {
        return configuration;
    }

    public List<Warning> getWarnings() {
        return warnings;
    }
}
