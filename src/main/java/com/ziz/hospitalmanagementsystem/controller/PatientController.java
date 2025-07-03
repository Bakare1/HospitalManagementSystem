package com.ziz.hospitalmanagementsystem.controller;

import com.ziz.hospitalmanagementsystem.model.Patient;
import com.ziz.hospitalmanagementsystem.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientRepository repo;

    @Hidden
    @RequestMapping("/patients")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }

    @GetMapping("/getPatients")
    public List<Patient> getPatients(){
        return repo.findAll();
    }

    @PostMapping("/addPatients")
    public Patient addPatients(@RequestBody Patient patient){
        return repo.save(patient);
    }
}
