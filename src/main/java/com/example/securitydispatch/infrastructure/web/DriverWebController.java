package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Zone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")
public class DriverWebController {


    private final DriverService driverService;

    public DriverWebController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public String listDrivers(Model model) {
        model.addAttribute("drivers", driverService.findAll());
        return "drivers";
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
    @PostMapping("/{driverId}/zones/{zoneId}")
    public String assignZone(@PathVariable Long driverId, @PathVariable Long zoneId) {
        driverService.assignZone(driverId, zoneId);
        return "redirect:/drivers/" + driverId;
    }
}



