package com.example.securitydispatch.infrastructure.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String redirectToDashboard() {
        return "redirect:/dashboard";
    }
}