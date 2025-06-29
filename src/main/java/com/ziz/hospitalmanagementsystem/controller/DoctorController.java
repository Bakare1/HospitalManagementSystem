package com.ziz.hospitalmanagementsystem.controller;

import com.ziz.hospitalmanagementsystem.model.Doctor;
import com.ziz.hospitalmanagementsystem.repository.doctorRepo;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class DoctorController {

    @Autowired
    private doctorRepo repo;

    @Hidden
    @RequestMapping("/doctor")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }

    @GetMapping("/getDoctors")
    public List<Doctor> getDoctors(){
        return repo.findAll();
    }

    @GetMapping("/available")
    public List<Doctor> getAvailableDoctors(){
        return repo.findByAvailableTrue();
    }

    @PostMapping("/addDoctor")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor addDoctor(@RequestBody Doctor doctor){
        return repo.save(doctor);
    }

    @GetMapping("/getDoctorById/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/updateDoctor/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor updatedDoctor) {
        return repo.findById(id).map(doctor -> {
            doctor.setFullName(updatedDoctor.getFullName());
            doctor.setSpecialization(updatedDoctor.getSpecialization());
            doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setOfficeNumber(updatedDoctor.getOfficeNumber());
            doctor.setAvailable(updatedDoctor.isAvailable());
            return ResponseEntity.ok(repo.save(doctor));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
