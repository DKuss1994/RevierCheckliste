package com.example.securitydispatch.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Checklist {
    private final long id;
    private final Shift shift;
    private final StandardConfiguration config;
    LocalDateTime generatedAt;
    private final List<ChecklistEntry> entries;
    private final List<Warning> warnings;

    public Checklist(long id,Shift shift,StandardConfiguration config,
                     LocalDateTime generatedAt,List<Warning> warnings,
                     List<ChecklistEntry> entries) {
        if(shift == null){
           throw new IllegalArgumentException("Checklist shift must not be null");
        }if(config == null){
           throw new IllegalArgumentException("Checklist configuration must not be null");
        }if(generatedAt == null){
           throw new IllegalArgumentException("Checklist generatedAt must not be null");
        }
        if(warnings==null){
            throw new IllegalArgumentException("Checklist warnings must not be null");
        }
        if(entries==null){
            throw new IllegalArgumentException("Checklist entries must not be null");
        }
        this.generatedAt = generatedAt;
        this.config = config;
        this.shift = shift;
        this.id = id;
        this.warnings = warnings;
        this.entries = entries;
    }

    public long getId() {
        return id;
    }

    public Shift getShift() {
        return shift;
    }

    public StandardConfiguration getConfiguration() {
        return config;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public List<Warning> getWarnings() {
            return warnings;
    }

    public List<ChecklistEntry> getEntries() {
        return entries;
    }
}
