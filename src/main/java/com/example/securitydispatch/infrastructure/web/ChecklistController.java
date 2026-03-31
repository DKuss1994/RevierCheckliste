package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.ChecklistApplicationService;
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

    private final ChecklistApplicationService checklistApplicationService;

    public ChecklistController(ChecklistApplicationService checklistApplicationService) {
        this.checklistApplicationService = checklistApplicationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ChecklistResponse> generate(@RequestBody ChecklistRequest request) {
        return ResponseEntity.ok(checklistApplicationService.generate(request));
    }
}