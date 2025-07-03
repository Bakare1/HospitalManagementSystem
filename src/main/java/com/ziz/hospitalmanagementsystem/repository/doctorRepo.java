package com.ziz.hospitalmanagementsystem.repository;

import com.ziz.hospitalmanagementsystem.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface doctorRepo extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecialization(String specialization);

    List<Doctor> findByAvailableTrue();

    Optional<Doctor> findByUser_Id(Long userId);

    Optional<Doctor> findByEmail(String email);
}
