package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.Checklist;
import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.domain.StandardConfiguration;

public class ChecklistMapper {
    public static ChecklistEntity toEntity(Checklist checklist) {
        ShiftEntity shift = ShiftMapper.toEntity(checklist.getShift());
        StandardConfiguration config = checklist.getConfig();
        StandardConfigurationEmbeddable standardConfiguration = new StandardConfigurationEmbeddable(
                config.getInspectionCount().orElse(null),
                config.getInspectionDays().orElse(null),
                config.getOpeningTime().orElse(null),
                config.getOpeningDays().orElse(null),
                config.getClosingTime().orElse(null),
                config.getClosingDays().orElse(null),
                config.getInspectionWindowStart().orElse(null),
                config.getInspectionWindowEnd().orElse(null),
                config.getNotes().orElse(null));

        return new ChecklistEntity(checklist.getId(),shift,standardConfiguration,
                checklist.getGeneratedAt(),checklist.getWarnings());
    }

    public static Checklist toDomain(ChecklistEntity entity) {
        Shift shift = ShiftMapper.toDomain(entity.getShift());
        StandardConfigurationEmbeddable config = entity.getConfiguration();
        StandardConfiguration standardConfiguration = new StandardConfiguration.Builder()
                .inspectionCount(config.getInspectionCount())
                .inspectionDays(config.getInspectionDays())
                .openingTime(config.getOpeningTime())
                .openingDays(config.getOpeningDays())
                .closingTime(config.getClosingTime())
                .closingDays(config.getClosingDays())
                .inspectionWindowStart(config.getInspectionWindowStart())
                .inspectionWindowEnd(config.getInspectionWindowEnd())
                .notes(config.getNotes())
                .build();
        return new Checklist(entity.getId(),shift,standardConfiguration,entity.getGeneratedAt(),entity.getWarnings());
    }
}
