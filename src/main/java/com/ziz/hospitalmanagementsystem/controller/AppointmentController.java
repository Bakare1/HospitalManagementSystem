package com.ziz.hospitalmanagementsystem.controller;

import com.ziz.hospitalmanagementsystem.model.Appointment;
import com.ziz.hospitalmanagementsystem.model.AppointmentStatus;
import com.ziz.hospitalmanagementsystem.repository.AppointmentRepository;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Hidden
    @RequestMapping("/appointments")
    public void redirectToSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }

    @GetMapping("/getAppointments")
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @PostMapping("/bookAppointment")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        if (appointment.getPatient() == null || appointment.getDoctor() == null) {
            return ResponseEntity.badRequest().body("Patient and Doctor are required.");
        }

        if (appointment.getStatus() == null) {
            return ResponseEntity.badRequest().body("Status must be one of: SCHEDULED, COMPLETED, or CANCELLED.");
        }

        Appointment saved = appointmentRepo.save(appointment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/appointments/doctor/{id}")
    public List<Appointment> getByDoctor(@PathVariable Long id) {
        return appointmentRepo.findByDoctorId(id);
    }

    @GetMapping("/appointments/patient/{id}")
    public List<Appointment> getByPatient(@PathVariable Long id) {
        return appointmentRepo.findByPatientId(id);
    }

    // ✅ Filter appointments by status (Enum-safe!)
    @GetMapping("/appointments/status/{status}")
    public List<Appointment> getByStatus(@PathVariable AppointmentStatus status) {
        return appointmentRepo.findByStatus(status);
    }

}