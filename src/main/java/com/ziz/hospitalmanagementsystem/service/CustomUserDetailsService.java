package com.ziz.hospitalmanagementsystem.service;

import com.ziz.hospitalmanagementsystem.model.User;
import com.ziz.hospitalmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("Loaded user: " + user.getUsername());
        System.out.println("Assigned role: " + user.getRole());
        // Ensure role format matches Spring Security expectations
        String cleanRole = user.getRole().startsWith("ROLE_")
                ? user.getRole().substring(5)
                : user.getRole();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(cleanRole)
                .build();

    }
}
