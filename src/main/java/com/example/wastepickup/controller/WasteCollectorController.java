package com.example.wastepickup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.wastepickup.model.WastePickup;
import com.example.wastepickup.repository.WastePickupRepository;

@Controller
@RequestMapping("/collector")
@PreAuthorize("hasRole('COLLECTOR')")
public class WasteCollectorController {

    @Autowired
    private WastePickupRepository pickupRepository;

    @GetMapping("/dashboard")
    public String collectorDashboard(Model model) {
        List<WastePickup> pickups = pickupRepository.findByStatusIn(List.of("Requested","Collected"));
        model.addAttribute("pickups", pickups);
        return "collector_pickups";
    }

    @PostMapping("/updateStatus/{id}")
    public String updatePickupStatus(@PathVariable Long id) {
        WastePickup pickup = pickupRepository.findById(id).orElse(null);
        if (pickup != null) {
            pickup.setStatus("Collected");
            pickupRepository.save(pickup);
        }
        return "redirect:/collector/dashboard";
    }
}
