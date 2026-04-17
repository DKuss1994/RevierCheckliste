package com.example.securitydispatch.application;


import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.infrastructure.persistence.*;
import com.example.securitydispatch.infrastructure.web.ChecklistRequest;
import com.example.securitydispatch.infrastructure.web.ChecklistResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChecklistApplicationService {

    private final ShiftRepository shiftRepository;
    private final SecurityObjectRepository securityObjectRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistGenerationService checklistGenerationService;

    public ChecklistApplicationService(ShiftRepository shiftRepository, SecurityObjectRepository securityObjectRepository, ChecklistRepository checklistRepository, ChecklistGenerationService checklistGenerationService) {
        this.shiftRepository = shiftRepository;
        this.securityObjectRepository = securityObjectRepository;
        this.checklistRepository = checklistRepository;
        this.checklistGenerationService = checklistGenerationService;
    }

    public ChecklistResponse generate(ChecklistRequest checklistRequest) {
        Shift shift = loadShift(checklistRequest.getShiftId());
        List<SecurityObject> securityObjects = loadListOfSecurityObjectByZoneId(shift.getZone().getId());
        Checklist checklist = checklistGenerationService.generate(shift, securityObjects);
        Checklist savedChecklist = saveAndReturnWithId(checklist);
        return toResponse(savedChecklist);
    }

    private static ChecklistResponse toResponse(Checklist checklist) {
        return new ChecklistResponse(
                checklist.getId(),
                checklist.getGeneratedAt(),
                checklist.getConfiguration().getInspectionCount().orElse(null),
                checklist.getWarnings().stream()
                        .map(Warning::getMessage)
                        .toList());
    }

    private Checklist saveAndReturnWithId(Checklist checklist) {
        ShiftEntity shiftEntity = shiftRepository.findById(
                        checklist.getShift().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Shift not found: " + checklist.getShift().getId()));
        // ChecklistEntity direkt mit der gespeicherten ShiftEntity erstellen
        StandardConfiguration config = checklist.getConfiguration();
        StandardConfigurationEmbeddable configEmbeddable = new StandardConfigurationEmbeddable(
                config.getInspectionCount().orElse(null),
                config.getInspectionDays().orElse(null),
                config.getOpeningTime().orElse(null),
                config.getOpeningDays().orElse(null),
                config.getClosingTime().orElse(null),
                config.getClosingDays().orElse(null),
                config.getInspectionWindowStart().orElse(null),
                config.getInspectionWindowEnd().orElse(null),
                config.getNotes().orElse(null));
        ChecklistEntity entity = new ChecklistEntity(
                shiftEntity,
                configEmbeddable,
                checklist.getGeneratedAt(),
                checklist.getWarnings());

        ChecklistEntity saved = checklistRepository.save(entity);

        return new Checklist(
                saved.getId(),
                checklist.getShift(),
                checklist.getConfiguration(),
                checklist.getGeneratedAt(),
                checklist.getWarnings(),
                checklist.getEntries());
    }


    private List<SecurityObject> loadListOfSecurityObjectByZoneId(Long zoneId) {
        return securityObjectRepository.findByZoneId(zoneId)
                .stream().map(SecurityObjectMapper::toDomain)
                .toList();
    }

    private SecurityObject loadSecurityObject(Long id) {
        return securityObjectRepository.findById(id).
                map(SecurityObjectMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("SecurityObject not found: "
                        + id));
    }

    private Shift loadShift(Long id) {
        return shiftRepository.findById(id)
                .map(ShiftMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Shift not found: " + id));

    }

    public Checklist generateChecklist(ChecklistRequest checklistRequest) {
        Shift shift = loadShift(checklistRequest.getShiftId());
        List<SecurityObject> securityObjects = loadListOfSecurityObjectByZoneId(shift.getZone().getId());
        Checklist checklist = checklistGenerationService.generate(shift, securityObjects);
        return saveAndReturnWithId(checklist);
    }
}
