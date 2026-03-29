package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ChecklistGenerationService {

    private final ConfigurationResolver resolver = new ConfigurationResolver();

    public Checklist generate(Shift shift, SecurityObject securityObject) {
        ResolutionResult result = resolver.resolve(
                shift.getDeploymentDate(), securityObject);

        return new Checklist(1L, shift, result.getConfiguration(), LocalDateTime.now(),result.getWarnings());
    }
}