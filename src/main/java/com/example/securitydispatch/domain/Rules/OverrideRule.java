package com.example.securitydispatch.domain.Rules;

import com.example.securitydispatch.domain.StandardConfiguration;

import java.time.LocalDate;
import java.util.Objects;

public class OverrideRule extends Rule{
    private final StandardConfiguration configuration;

    public OverrideRule(LocalDate startDay, LocalDate endDay, StandardConfiguration config) {
        super(startDay, endDay);
        this.configuration = Objects.requireNonNull(config,"OverrideRule configuration must not be null");
    }

    public StandardConfiguration getConfiguration() {
        return configuration;
    }
}
