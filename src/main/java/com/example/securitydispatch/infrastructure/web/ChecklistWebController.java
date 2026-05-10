package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.ChecklistApplicationService;
import com.example.securitydispatch.application.PdfService;
import com.example.securitydispatch.application.ShiftService;
import com.example.securitydispatch.domain.Checklist;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checklists")
public class ChecklistWebController {

    private final ShiftService shiftService;
    private final ChecklistApplicationService checklistService;
    private final PdfService pdfService;

    public ChecklistWebController(ShiftService shiftService,
                                  ChecklistApplicationService checklistService,
                                  PdfService pdfService) {
        this.shiftService = shiftService;
        this.checklistService = checklistService;
        this.pdfService = pdfService;
    }

    @GetMapping("/generate")
    public String showGenerateForm(Model model) {
        model.addAttribute("shifts", shiftService.findAll());
        return "generate-checklist";
    }

    @PostMapping("/generate/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestParam Long shiftId) {
        Checklist checklist = checklistService.generateChecklist(shiftId);
        byte[] pdf = pdfService.generateChecklistPdf(checklist);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=checklist.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}