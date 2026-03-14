package com.example.securitydispatch.domain;

import com.example.securitydispatch.domain.Rules.AdditionalRule;
import com.example.securitydispatch.domain.Rules.OverrideRule;

import java.util.ArrayList;
import java.util.List;

public class SecurityObject {


    private final long id;
    private final String name;
    private final Zone zone;
    private final Address address;
    private final StandardConfiguration config;
    private final List<OverrideRule> overrideRule = new ArrayList<>();
    private final List<AdditionalRule> additionalRules = new ArrayList<>();

    public SecurityObject(long id, String name, Zone zone, Address address, StandardConfiguration config) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Object name must not be blank");
        } if (zone == null ) {
            throw new IllegalArgumentException("Zone name must not be null");
        } if (address == null ) {
            throw new IllegalArgumentException("Address name must not be null");
        }if (config == null ) {
            throw new IllegalArgumentException("Configuration must not be null");
        }
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.address = address;
        this.config = config;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Zone getZone() {
        return zone;
    }

    public Address getAddress() {
        return address;
    }


    public StandardConfiguration getStandardConfiguration() {
        return config;
    }

    public void addOverrideRule(OverrideRule rule) {
        for (OverrideRule existing : overrideRule) {
            if(existing.getStartDay().isBefore(rule.getEndDay())&&
            existing.getEndDay().isAfter(rule.getStartDay())){
                throw new IllegalArgumentException("Override rules must not overlap");
            }
        }

        this.overrideRule.add(rule);

    }
    public void addAdditionalRule(AdditionalRule rule) {
        for (AdditionalRule existing : additionalRules) {
            if(existing.getStartDay().isBefore(rule.getEndDay())&&
            existing.getEndDay().isAfter(rule.getStartDay())){
                throw new IllegalArgumentException("Override rules must not overlap");
            }
        }

        this.additionalRules.add(rule);

    }
    public List <OverrideRule> getOverrideRules(){
        return this.overrideRule;
    }
    public List <AdditionalRule> getAdditionalRules(){
        return this.additionalRules;
    }
}
