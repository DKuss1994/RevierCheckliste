package com.example.securitydispatch.application;


import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.infrastructure.persistence.*;
import com.example.securitydispatch.infrastructure.web.ChecklistRequest;
import com.example.securitydispatch.infrastructure.web.ChecklistResponse;
import org.springframework.stereotype.Service;
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
        SecurityObject securityObject = loadSecurityObject(checklistRequest.getSecurityObjectId());
        Checklist checklist = checklistGenerationService.generate(shift,securityObject);
        saveChecklist(checklist);
        return toResponse(checklist);
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

    private void saveChecklist(Checklist checklist) {
        checklistRepository.save(ChecklistMapper.toEntity(checklist));
    }

    private SecurityObject loadSecurityObject(Long id) {
     return securityObjectRepository.findById(id).
                map(SecurityObjectMapper::toDomain)
                .orElseThrow(()-> new IllegalArgumentException("SecurityObject not found: "
                        + id));
    }

    private Shift loadShift(Long id) {
        return   shiftRepository.findById(id)
                .map(ShiftMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Shift not found: "+ id));

    }

    public Checklist generateChecklist(ChecklistRequest checklistRequest) {
        Shift shift = loadShift(checklistRequest.getShiftId());
        SecurityObject securityObject = loadSecurityObject(checklistRequest.getSecurityObjectId());
        Checklist checklist = checklistGenerationService.generate(shift,securityObject);
        saveChecklist(checklist);
        return checklist;
    }
}
