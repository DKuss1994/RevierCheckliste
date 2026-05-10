package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.ChecklistApplicationService;
import com.example.securitydispatch.application.ChecklistGenerationService;
import com.example.securitydispatch.application.PdfService;
import com.example.securitydispatch.domain.Checklist;
import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.domain.Warning;
import com.example.securitydispatch.infrastructure.persistence.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checklists")
public class ChecklistController {

    private final PdfService pdfService;

    private final ChecklistApplicationService checklistApplicationService;

    public ChecklistController(PdfService pdfService, ChecklistApplicationService checklistApplicationService) {
        this.pdfService = pdfService;
        this.checklistApplicationService = checklistApplicationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ChecklistResponse> generate(@RequestBody ChecklistRequest request) {
        return ResponseEntity.ok(checklistApplicationService.generate(request));
    }
    @PostMapping("/generate/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Long shiftId) {
        Checklist checklist = checklistApplicationService.generateChecklist(shiftId);
        byte[] pdf = pdfService.generateChecklistPdf(checklist);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=checklist.pdf")
                .body(pdf);
    }
}