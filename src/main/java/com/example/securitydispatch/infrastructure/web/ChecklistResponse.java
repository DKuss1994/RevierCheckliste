package com.example.securitydispatch.infrastructure.web;
import java.time.LocalDateTime;
import java.util.List;
public class ChecklistResponse {
    private long id;
    private LocalDateTime generatedAt;
    private Integer inspectionCount;
    private List<String> warnings;

    public ChecklistResponse(long id, LocalDateTime generatedAt, Integer inspectionCount, List<String> warnings) {
        this.id = id;
        this.generatedAt = generatedAt;
        this.inspectionCount = inspectionCount;
        this.warnings = warnings;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public Integer getInspectionCount() {
        return inspectionCount;
    }
}
