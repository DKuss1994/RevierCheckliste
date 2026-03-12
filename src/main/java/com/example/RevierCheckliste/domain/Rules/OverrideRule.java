package com.example.RevierCheckliste.domain.Rules;

import com.example.RevierCheckliste.domain.StandardConfiguration;

import java.time.LocalDate;

public class OverrideRule extends Rule{
    private final StandardConfiguration configuration;

    public OverrideRule(LocalDate startDay, LocalDate endDay, StandardConfiguration config) {
        super(startDay, endDay);
        this.configuration = config;
    }

    public StandardConfiguration getConfiguration() {
        return configuration;
    }
}
