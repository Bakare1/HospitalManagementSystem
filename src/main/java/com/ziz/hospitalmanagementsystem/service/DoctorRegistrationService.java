package com.ziz.hospitalmanagementsystem.service;

import com.ziz.hospitalmanagementsystem.dto.DoctorRegistrationDTO;
import com.ziz.hospitalmanagementsystem.model.Doctor;
import com.ziz.hospitalmanagementsystem.model.User;
import com.ziz.hospitalmanagementsystem.repository.doctorRepo;
import com.ziz.hospitalmanagementsystem.repository.UserRepository;
import com.ziz.hospitalmanagementsystem.repository.doctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DoctorRegistrationService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private doctorRepo doctorRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(DoctorRegistrationDTO dto) {
        // 1. Create and save User
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("DOCTOR");
        User savedUser = userRepo.save(user);

        // 2. Create and save Doctor profile linked to User
        Doctor doctor = new Doctor();
        doctor.setFullName(dto.getFullName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setUser(savedUser);
        doctorRepo.save(doctor);
    }
}