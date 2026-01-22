package com.example.wastepickup.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.wastepickup.model.WastePickup;
import com.example.wastepickup.service.WastePickupService;

@Controller
public class HomeController {

    @Autowired
    private WastePickupService wastePickupService;

    // 🔹 This is the new homepage (landing page)
    @GetMapping("/")
    public String home(Principal principal) {
        // If logged in, go to dashboard
        if (principal != null) {
            return "redirect:/dashboard";
        }
        // Otherwise, show homepage
        return "homepage"; // this maps to homepage.html
    }

    // 🔹 Dashboard after login
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<WastePickup> wastePickups = wastePickupService.getAllWastePickups();
        model.addAttribute("wastePickups", wastePickups);
        return "waste-pickup"; 
    }
}
