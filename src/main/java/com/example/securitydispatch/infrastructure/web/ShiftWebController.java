package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.*;
import com.example.securitydispatch.domain.Checklist;
import com.example.securitydispatch.domain.Shift;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
        @RequestMapping("/shifts")
public class ShiftWebController {
    private final ShiftService shiftService;
    private final ZoneService zoneService;
    private final DriverService driverService;
    private final ChecklistApplicationService checklistService;
    private final PdfService pdfService = new PdfService();


    public ShiftWebController(ShiftService shiftService,
                              ZoneService zoneService,
                              DriverService driverService, ChecklistGenerationService checklistGenerationService, ChecklistApplicationService checklistService) {
        this.shiftService = shiftService;
        this.zoneService = zoneService;
        this.driverService = driverService;
        this.checklistService = checklistService;

    }
    @GetMapping
    public String listShifts(Model model) {
        model.addAttribute("shifts", shiftService.findAll());
        return "shifts";
    }
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("zones",zoneService.findAll());
        model.addAttribute("drivers",driverService.findAll());
        return "shift-form";
    }
    @PostMapping
    public String createShift(@RequestParam long driverId,
                              @RequestParam long zoneId,
                              @RequestParam String deploymentDate,
                              @RequestParam String startTime,
                              @RequestParam String endTime){
        LocalDate date = LocalDate.parse(deploymentDate);
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        shiftService.create(driverId,zoneId,date,start,end);
        return "redirect:/shifts";
    }
    @PostMapping("/{id}/delete")
    public String deleteShift(@PathVariable long id){
        shiftService.delete(id);
        return "redirect:/shifts";
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
