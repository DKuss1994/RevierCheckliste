package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Zone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/zones")
public class ZoneWebController {
    private final ZoneService zoneService;
    public ZoneWebController(ZoneService zoneService){
        this.zoneService = zoneService;
    }
    @GetMapping
    public String listZones(Model model){
        model.addAttribute("zones",zoneService.findAll());
        return "zones";
    }
    @GetMapping("/new")
    public String showCreateFrom(){

        return "zone-form";
    }
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Zone zone = zoneService.findById(id); // erwartet, dass ZoneService diese Methode hat
        model.addAttribute("zone", zone);
        return "zone-form";
    }
    @PostMapping
    public String createZone(Model model, @RequestParam String name){
        zoneService.create(name);
        return "redirect:/zones";
    }
    @PostMapping("/{id}")
    public String updateZone(@PathVariable Long id, @RequestParam String name) {
        zoneService.update(id, name);
        return "redirect:/zones";
    }
}

