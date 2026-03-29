package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.ChecklistGenerationService;
import com.example.securitydispatch.domain.Checklist;
import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.domain.Warning;
import com.example.securitydispatch.infrastructure.persistence.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checklists")
public class ChecklistController {

    private final ChecklistGenerationService checklistGenerationService;
    private final ShiftRepository shiftRepository;
    private final SecurityObjectRepository securityObjectRepository;
    private final ChecklistRepository checklistRepository;

    public ChecklistController(ChecklistGenerationService checklistGenerationService,
                               ShiftRepository shiftRepository,
                               SecurityObjectRepository securityObjectRepository,
                               ChecklistRepository checklistRepository) {
        this.checklistGenerationService = checklistGenerationService;
        this.shiftRepository = shiftRepository;
        this.securityObjectRepository = securityObjectRepository;
        this.checklistRepository = checklistRepository;
    }

    @PostMapping("/generate")
    public ResponseEntity<ChecklistResponse> generate(
            @RequestBody ChecklistRequest request) {

        Shift shift = shiftRepository.findById(request.getShiftId())
                .map(ShiftMapper::toDomain)
                .orElseThrow (()-> new RuntimeException("Shift not found: " + request.getShiftId()));

        SecurityObject securityObject = securityObjectRepository.findById(request.getSecurityObjectId())
                .map(SecurityObjectMapper::toDomain)
                .orElseThrow(()-> new RuntimeException("Security object not found: " + request.getSecurityObjectId()));

        Checklist checklist = checklistGenerationService.generate(shift, securityObject);
        checklistRepository.save(ChecklistMapper.toEntity(checklist));

        ChecklistResponse response = new ChecklistResponse(
                checklist.getId(),
                checklist.getGeneratedAt(),
                checklist.getConfiguration().getInspectionCount().orElse(null),
                checklist.getWarnings().stream()
                        .map(Warning::getMessage)
                        .toList());

        return ResponseEntity.ok(response);
    }
}