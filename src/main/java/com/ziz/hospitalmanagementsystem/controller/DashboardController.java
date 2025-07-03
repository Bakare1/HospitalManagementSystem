package com.ziz.hospitalmanagementsystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard/doctor")
    public String doctorDashboard(Model model, Authentication auth) {
        model.addAttribute("username", auth.getName());
        return "doctor-dashboard"; // refers to doctor-dashboard.html or .jsp
    }

}
