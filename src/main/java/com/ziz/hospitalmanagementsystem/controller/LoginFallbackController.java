package com.ziz.hospitalmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class LoginFallbackController {
    @Hidden
    @RequestMapping("/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}