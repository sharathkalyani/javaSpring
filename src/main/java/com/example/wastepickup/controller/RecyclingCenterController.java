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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.wastepickup.model.WastePickup;
import com.example.wastepickup.repository.WastePickupRepository;

@Controller
@RequestMapping("/recycle")
@PreAuthorize("hasRole('RECYCLER')")
public class RecyclingCenterController {

    @Autowired
    private WastePickupRepository pickupRepository;

    // Show dashboard with pickups ready for categorization or processing
    @GetMapping("/dashboard")
    public String recyclerDashboard(Model model) {
        List<WastePickup> pickups = pickupRepository.findByStatusIn(List.of(
                "Collected", 
                "Processed and Sent to Recycling", 
                "Sent to Disposal", 
                "Processing", 
                "Recycled"
        ));
        model.addAttribute("collectedPickups", pickups);
        return "recycling_center";
    }

    // Initial processing and categorization
    @PostMapping("/process/{id}")
    public String processWaste(
            @PathVariable Long id,
            @RequestParam("category") String category,
            @RequestParam("processedBy") String processedBy
    ) {
        WastePickup pickup = pickupRepository.findById(id).orElse(null);
        if (pickup != null && pickup.getCategory() == null) {
            pickup.setCategory(category);
            pickup.setProcessedBy(processedBy);

            if (category.equalsIgnoreCase("Recyclable")) {
                pickup.setStatus("Processed and Sent to Recycling"); // Set to Processing until marked as Recycled
            } else {
                pickup.setStatus("Sent to Disposal");
            }

            pickupRepository.save(pickup);
        }
        return "redirect:/recycle/dashboard";
    }

    // Mark as recycled after processing
    @PostMapping("/markRecycled/{id}")
    public String markAsRecycled(@PathVariable Long id) {
        WastePickup pickup = pickupRepository.findById(id).orElse(null);
        if (pickup != null 
            && "Recyclable".equalsIgnoreCase(pickup.getCategory()) 
            && "Processed and Sent to Recycling".equalsIgnoreCase(pickup.getStatus())) {
            pickup.setStatus("Recycled");
            pickupRepository.save(pickup);
        }
        return "redirect:/recycle/dashboard";
    }
}
