package com.ziz.hospitalmanagementsystem.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Total amount charged", example = "35000")
    private double amount;

    @Enumerated(EnumType.STRING)
    private BillStatus status;// e.g. "PAID", "PENDING", "OVERDUE"


    @Schema(description = "Date the bill was created", example = "2025-06-30")
    private LocalDate billingDate;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    public Bill() {
    }

    public Bill(Long id, double amount, BillStatus status, LocalDate billingDate, Appointment appointment, Patient patient) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.billingDate = billingDate;
        this.appointment = appointment;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", amount=" + amount +
                ", status=" + status +
                ", billingDate=" + billingDate +
                ", appointment=" + appointment +
                ", patient=" + patient +
                '}';
    }
}
