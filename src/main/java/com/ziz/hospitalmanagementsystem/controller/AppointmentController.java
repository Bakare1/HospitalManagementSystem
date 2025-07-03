package com.ziz.hospitalmanagementsystem.controller;

import com.ziz.hospitalmanagementsystem.dto.AppointmentRequestDTO;
import com.ziz.hospitalmanagementsystem.model.*;
import com.ziz.hospitalmanagementsystem.repository.AppointmentRepository;
import com.ziz.hospitalmanagementsystem.repository.PatientRepository;
import com.ziz.hospitalmanagementsystem.repository.UserRepository;
import com.ziz.hospitalmanagementsystem.repository.doctorRepo;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private doctorRepo docRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private UserRepository userRepository;

    @Hidden
    @RequestMapping("/appointments")
    public void redirectToSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAppointments")
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @PostMapping("/bookAppointment")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDTO dto) {
        if (dto.getPatientId() == null || dto.getDoctorId() == null) {
            return ResponseEntity.badRequest().body("Patient ID and Doctor ID are required.");
        }

        Patient patient = patientRepo.findById(dto.getPatientId()).orElse(null);
        Doctor doctor = docRepo.findById(dto.getDoctorId()).orElse(null);

        if (patient == null || doctor == null) {
            return ResponseEntity.badRequest().body("Invalid patient or doctor ID.");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setReason(dto.getReason());
        appointment.setStatus(AppointmentStatus.valueOf(dto.getStatus()));
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment saved = appointmentRepo.save(appointment);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/appointments/doctor/{id}")
    public List<Appointment> getByDoctor(@PathVariable Long id) {
        return appointmentRepo.findByDoctorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/appointments/patient/{id}")
    public List<Appointment> getByPatient(@PathVariable Long id) {
        return appointmentRepo.findByPatientId(id);
    }

    //Filter appointments by status (Enum-safe!)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/appointments/status/{status}")
    public List<Appointment> getByStatus(@PathVariable AppointmentStatus status) {
        return appointmentRepo.findByStatus(status);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/appointments/my")
    public ResponseEntity<?> getMyAppointments(Authentication authentication) {
        String username = authentication.getName(); // logged-in username

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Doctor doctor = docRepo.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Doctor profile not linked to this user"));

        List<Appointment> appointments = appointmentRepo.findByDoctorId(doctor.getId());

        return ResponseEntity.ok(appointments);
    }

}