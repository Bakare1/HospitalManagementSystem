package com.ziz.hospitalmanagementsystem.repository;

import com.ziz.hospitalmanagementsystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByAppointmentDate(LocalDate date);

    List<Appointment> findByStatus(String status);
}
