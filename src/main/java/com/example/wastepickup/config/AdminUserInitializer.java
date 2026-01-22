package com.example.wastepickup.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.wastepickup.model.Role;
import com.example.wastepickup.model.User;
import com.example.wastepickup.repository.UserRepository;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        if (userRepository.findByUsername("admin") == null) {
            // Create admin user
            
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Set a secure password
            admin.setRole(Role.ROLE_ADMIN); // Assuming you have an enum for roles

            userRepository.save(admin);
            System.out.println("Admin user created: admin@example.com / admin123");
        }
    }
}