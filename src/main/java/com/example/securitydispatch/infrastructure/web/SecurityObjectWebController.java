package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.application.SecurityObjectService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Zone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/security-objects")
public class SecurityObjectWebController {
    private final ZoneService zoneService;
    private final SecurityObjectService securityObjectService;
    private final DriverService driverService;

    public SecurityObjectWebController(ZoneService zoneService, SecurityObjectService securityObjectService, DriverService driverService) {
        this.zoneService = zoneService;
        this.securityObjectService = securityObjectService;
        this.driverService = driverService;
    }
    @GetMapping
    public String listSecurityObjects(Model model){
        model.addAttribute("objects",securityObjectService.findAll());
        return "security-objects";
    }
}
