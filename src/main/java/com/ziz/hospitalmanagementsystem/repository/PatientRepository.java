package com.ziz.hospitalmanagementsystem.repository;

import com.ziz.hospitalmanagementsystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

}
