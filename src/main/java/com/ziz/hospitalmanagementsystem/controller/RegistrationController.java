package com.ziz.hospitalmanagementsystem.controller;

import com.ziz.hospitalmanagementsystem.dto.DoctorRegistrationDTO;
import com.ziz.hospitalmanagementsystem.service.DoctorRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private DoctorRegistrationService regService;

    @GetMapping("/register/doctor")
    public String showDoctorForm(Model model) {
        model.addAttribute("doctor", new DoctorRegistrationDTO());
        return "doctor-register";
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(
            @ModelAttribute("doctor") @Valid DoctorRegistrationDTO dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "doctor-register";
        }
        regService.register(dto);
        return "redirect:/login?registered";
    }
}