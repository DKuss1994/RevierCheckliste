package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Zone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverWebController {


    private final DriverService driverService;
    private final ZoneService zoneService;

    public DriverWebController(DriverService driverService, ZoneService zoneService) {
        this.driverService = driverService;
        this.zoneService = zoneService;
    }

    @GetMapping
    public String listDrivers(Model model) {
        model.addAttribute("drivers", driverService.findAll());
        return "drivers";
    }
    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Driver driver = driverService.findById(id);
        List<Zone> assignedZones = driverService.findAssignedZones(id);
        List<Zone> allZones = zoneService.findAll();
        List<Zone> availableZones = allZones.stream()
                .filter(z -> !assignedZones.contains(z))
                .toList();
        model.addAttribute("driver", driver);
        model.addAttribute("assignedZones", assignedZones);
        model.addAttribute("availableZones", availableZones);
        return "driver-detail";
    }

    @GetMapping("/new")
    public String showCreateFrom() {

        return "driver-form";
    }
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Driver driver = driverService.findById(id);
        model.addAttribute("driver", driver);
        return "driver-form";
    }

    @PostMapping
    public String createDriver(Model model, @RequestParam String firstName, @RequestParam String lastName) {
        driverService.create(firstName,lastName);
        return "redirect:/drivers";
    }
    @PostMapping("/{id}")
    public String updateDriver(@PathVariable Long id
            , @RequestParam String firstName
            ,@RequestParam String lastName) {
        driverService.update(id, firstName,lastName);
        return "redirect:/drivers";
    }
    @PostMapping("/{driverId}/zones")
    public String assignZone(@PathVariable Long driverId, @RequestParam Long zoneId) {
        driverService.assignZone(driverId, zoneId);
        return "redirect:/drivers/" + driverId;
    }
}



