package com.example.wastepickup.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.wastepickup.model.Role;
import com.example.wastepickup.model.User;
import com.example.wastepickup.model.WastePickup;
import com.example.wastepickup.repository.UserRepository;
import com.example.wastepickup.repository.WastePickupRepository;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private WastePickupRepository wastePickupRepository;

    @Autowired
    private UserRepository userRepository;

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    // Show Report Form
    @GetMapping("/reports")
    public String showReportForm() {
        return "admin-report";
    }

    // Generate Report
    @PostMapping("/generateReport")
    public String generateReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                 @RequestParam(value = "location", required = false) String location,
                                 Model model) {
        List<WastePickup> reportData;

        if (location != null && !location.isEmpty()) {
            reportData = wastePickupRepository.findByPickupDateTimeBetweenAndPickupLocation(start, end, location);
        } else {
            reportData = wastePickupRepository.findByPickupDateTimeBetween(start, end);
        }

        model.addAttribute("reportData", reportData);
        return "admin-report-result";
    }

    // Manage Users
    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<User> citizens = userRepository.findByRole(Role.ROLE_CITIZEN);
        List<User> collectors = userRepository.findByRole(Role.ROLE_COLLECTOR);
        List<User> recyclers = userRepository.findByRole(Role.ROLE_RECYCLING_STAFF);

        model.addAttribute("citizens", citizens);
        model.addAttribute("collectors", collectors);
        model.addAttribute("recyclers", recyclers);

        return "admin-users";
    }

    // Delete User
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "User removed successfully!");
        return "redirect:/admin/users";
    }
}
