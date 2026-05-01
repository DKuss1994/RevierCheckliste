package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.application.ZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping
    public String createZone(Model model, @RequestParam String firstName, @RequestParam String lastName) {
        driverService.create(firstName,lastName);
        return "redirect:/drivers";
    }
}



