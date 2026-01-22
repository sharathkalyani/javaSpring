package com.example.wastepickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.wastepickup.model.User;
import com.example.wastepickup.repository.UserRepository;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String showProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            // Log and redirect if the user is not authenticated
            System.out.println("UserDetails is null. Redirecting to login.");
            return "redirect:/login";
        }

        String name = userDetails.getUsername(); // Gets email or username from logged-in user
        System.out.println("Logged-in username: " + name);

        User user = userRepository.findByUsername(name);
        if (user == null) {
            // Log and handle the case where the user is not found
            System.out.println("User not found in the database for username: " + name);
            return "error"; // Redirect to an error page or show a meaningful message
        }

        System.out.println("User found: " + user.getUsername());
        model.addAttribute("user", user);
        return "profile"; // Render the profile.html template
    }

    @GetMapping("/profile/dashboard")
    public String redirectToDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            // Redirect to login if the user is not authenticated
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            // Handle the case where the user is not found
            return "error"; // Redirect to an error page or show a meaningful message
        }

        // Redirect based on the user's role
        return switch (user.getRole()) {
            case ROLE_ADMIN -> "redirect:/admin/dashboard";
            case ROLE_COLLECTOR -> "redirect:/collector/dashboard";
            case ROLE_RECYCLING_STAFF -> "redirect:/recycling/dashboard";
            default -> "redirect:/dashboard"; 
        };
    }
}