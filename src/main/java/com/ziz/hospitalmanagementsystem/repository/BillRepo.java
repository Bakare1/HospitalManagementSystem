package com.ziz.hospitalmanagementsystem.repository;

import com.ziz.hospitalmanagementsystem.model.Bill;
import com.ziz.hospitalmanagementsystem.model.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
    List<Bill> findByStatus(BillStatus status);
    List<Bill> findByPatientId(Long patientId);
}
