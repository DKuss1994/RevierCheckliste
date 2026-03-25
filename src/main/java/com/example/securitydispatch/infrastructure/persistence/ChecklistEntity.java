package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.Warning;
import jakarta.persistence.*;
import com.example.securitydispatch.domain.Warning;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "checklists")
public class ChecklistEntity {
    @Id
    private long id;

    @OneToOne
    @JoinColumn(name = "shift_id")
    private ShiftEntity shift;

    @Embedded
    private StandardConfigurationEmbeddable configuration;

    private LocalDateTime generatedAt;
    private String warnings;

    protected ChecklistEntity() {
    }

    public ChecklistEntity(long id, ShiftEntity shift, StandardConfigurationEmbeddable configuration,
                           LocalDateTime generatedAt, List<Warning> warnings) {
        this.id = id;
        this.shift = shift;
        this.configuration = configuration;
        this.generatedAt = generatedAt;
        this.warnings = warningsToString(warnings);
    }

    private String warningsToString(List<Warning> warnings) {
        if (warnings == null || warnings.isEmpty()) return null;
        return warnings.stream()
                .map(Warning::getMessage)
                .collect(Collectors.joining(","));
    }

    private List<Warning> stringToWarnings(String warnings) {
        if (warnings == null || warnings.isEmpty()) return null;
        return Arrays.stream(warnings.split(","))
                .map(Warning::new)
                .collect(Collectors.toList());
    }
    public List<Warning> getWarnings(){
        return stringToWarnings(warnings);
    }


    public long getId() {
        return id;
    }

    public ShiftEntity getShift() {
        return shift;
    }

    public StandardConfigurationEmbeddable getConfiguration() {
        return configuration;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}
