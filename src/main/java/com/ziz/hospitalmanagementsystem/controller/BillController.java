package com.ziz.hospitalmanagementsystem.controller;

import com.ziz.hospitalmanagementsystem.model.Bill;
import com.ziz.hospitalmanagementsystem.model.BillStatus;
import com.ziz.hospitalmanagementsystem.repository.AppointmentRepository;
import com.ziz.hospitalmanagementsystem.util.BillStatusValidator; // if using utility class
import com.ziz.hospitalmanagementsystem.repository.BillRepo;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class BillController {

    @Autowired
    private BillRepo repo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Hidden
    @RequestMapping("/bill")
    public void redirectToSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }

    @GetMapping("/getAllBills")
    public List<Bill> getAllBills(){
        return repo.findAll();
    }

     // Add this at the top if not present

    @PostMapping("/addBill")
    public ResponseEntity<?> addBill(@RequestBody Bill bill) {
        // Check if appointment exists
        if (bill.getAppointment() == null || bill.getAppointment().getId() == null) {
            return ResponseEntity.badRequest().body("Error: Appointment is missing or has no ID.");
        }

        if (bill.getPatient() == null || bill.getPatient().getId() == null) {
            return ResponseEntity.badRequest().body("Error: Patient is missing or has no ID.");
        }

        Long appointmentId = bill.getAppointment().getId();
        Long patientId = bill.getPatient().getId();

        var appointmentOpt = appointmentRepo.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Appointment not found with ID: " + appointmentId);
        }

        var appointment = appointmentOpt.get();
        Long appointmentPatientId = appointment.getPatient().getId();

        // Validate that bill.patient matches appointment.patient
        if (!appointmentPatientId.equals(patientId)) {
            return ResponseEntity.status(400).body(
                    "Validation Error: Patient ID in bill (" + patientId +
                            ") does not match patient in appointment (" + appointmentPatientId + ")"
            );
        }
        // Check that status is present (since it's now an enum)
        if (bill.getStatus() == null) {
            return ResponseEntity.badRequest().body("Status is required and must be one of: PAID, PENDING, OVERDUE.");
        }

        // All good—save and return
        Bill savedBill = repo.save(bill);
        return ResponseEntity.status(201).body(savedBill);
    }

    @GetMapping("/getBillsByPatient/{id}")
    public List<Bill> getBillsByPatient(@PathVariable Long id) {
        return repo.findByPatientId(id);
    }

    @GetMapping("/getBillById/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/updateBill/{id}")
    public ResponseEntity<?> updateBill(@PathVariable Long id, @RequestBody Bill updatedBill) {
        if (updatedBill.getStatus() == null) {
            return ResponseEntity.badRequest().body("Status is required and must be one of: PAID, PENDING, OVERDUE.");
        }

        return repo.findById(id).map(bill -> {
            bill.setAmount(updatedBill.getAmount());
            bill.setStatus(updatedBill.getStatus());
            bill.setBillingDate(updatedBill.getBillingDate());
            return ResponseEntity.ok(repo.save(bill));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteBill/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getBillsByStatus/{status}")
    public List<Bill> getBillsByStatus(@PathVariable BillStatus status) {
        return repo.findByStatus(status);
    }
}
