package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.ShiftService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.domain.Shift;
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


    public ShiftWebController(ShiftService shiftService,
                              ZoneService zoneService,
                              DriverService driverService) {
        this.shiftService = shiftService;
        this.zoneService = zoneService;
        this.driverService = driverService;
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
}
