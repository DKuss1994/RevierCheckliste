package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.domain.Rules.OverrideRule;

import java.time.LocalDateTime;

public class ChecklistGenerationService {
    public Checklist generate(Shift shift, SecurityObject securityObject) {
        StandardConfiguration config = resolveConfiguration(shift, securityObject);
        return new Checklist(1L, shift, config, LocalDateTime.now());
    }

    private StandardConfiguration resolveConfiguration(Shift shift, SecurityObject securityObject) {
        for (OverrideRule rule : securityObject.getOverrideRules()) {
            if (rule.isActive(shift.getDeploymentDate())) {
                return rule.getConfiguration();
            }

        }
        return securityObject.getStandardConfiguration();
    }
}