package com.example.wastepickup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wastepickup.model.Role;
import com.example.wastepickup.model.User; // Assuming you have an enum for roles

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByRole(Role role);
}
