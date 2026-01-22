package com.example.wastepickup.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.wastepickup.model.WastePickup;
import com.example.wastepickup.service.IReadOnlyWastePickupService;
import com.example.wastepickup.service.IWritableWastePickupService;
import com.example.wastepickup.service.WastePickupService;
//import com.example.wastepickup.service.WastePickupService;

@Controller
@RequestMapping("/wastepickup")
public class WastePickupController {
    @Autowired
    private WastePickupService wastePickupService;
    @Autowired
private IReadOnlyWastePickupService readOnlyService;

@Autowired
private IWritableWastePickupService writableService;


    // ✅ Display Waste Pickup Dashboard
    @GetMapping("/")
    public String viewWastePickups(Model model, Principal principal) {
        // Get the logged-in user's username
        String username = principal.getName();

        // Fetch waste pickup requests for the logged-in user
        List<WastePickup> userWastePickups = readOnlyService.getWastePickupsByUserName(username);

        // Add the filtered requests to the model
        model.addAttribute("wastePickups", userWastePickups);

        return "waste-pickup"; // Ensure "waste-pickup.html" is set up properly
    }

    // ✅ Create a new waste pickup request
    @PostMapping("/create")
    public String createWastePickup(@ModelAttribute WastePickup wastePickup) {
        //wastePickupService.saveWastePickup(wastePickup);
        wastePickup.setStatus("Requested");
        writableService.saveWastePickup(wastePickup);
        return "redirect:/wastepickup/"; // Redirect to updated dashboard
    }

    // ✅ Delete a waste pickup request
    @GetMapping("/delete/{id}")
    public String deleteWastePickup(@PathVariable Long id, Principal principal) {
        // Get the logged-in user's username
        String username = principal.getName();

        // Fetch the waste pickup request by ID
        Optional<WastePickup> wastePickup = readOnlyService.getWastePickupById(id);

        // Check if the request exists and belongs to the logged-in user
        if (wastePickup.isPresent() && wastePickup.get().getUserName().equals(username)) {
            writableService.deleteWastePickup(id);
        }

        // Redirect to the dashboard
        return "redirect:/wastepickup/";
    }

    // ✅ Load Edit Page with Waste Pickup Data
    @GetMapping("/edit/{id}")
    public String editWastePickup(@PathVariable Long id, Model model) {
       // Optional<WastePickup> wastePickup = wastePickupService.getWastePickupById(id);
        Optional<WastePickup> wastePickup = readOnlyService.getWastePickupById(id);
        if (wastePickup.isPresent()) {
            model.addAttribute("wastePickup", wastePickup.get());
            return "edit";
        } else {
            return "redirect:/wastepickup/";
        }
    }

    // ✅ Handle Form Submission to Update Waste Pickup Request
   /*  @PostMapping("/update")
    public String updateWastePickup(@ModelAttribute WastePickup wastePickup) {
        Optional<WastePickup> existingRequest = wastePickupService.getWastePickupById(wastePickup.getId());
        if (existingRequest.isPresent()) {
            wastePickupService.saveWastePickup(wastePickup);
        }
        return "redirect:/wastepickup/";
    }*/
    @PostMapping("/update")
    public String updateWastePickup(@ModelAttribute WastePickup wastePickup, Principal principal) {
        // Get the logged-in user's username
        String username = principal.getName();

        // Fetch the existing waste pickup request
        Optional<WastePickup> existingRequest = readOnlyService.getWastePickupById(wastePickup.getId());

        // Check if the request exists and belongs to the logged-in user
        if (existingRequest.isPresent() && existingRequest.get().getUserName().equals(username)) {
            writableService.saveWastePickup(wastePickup);
        }

        // Redirect to the dashboard
        return "redirect:/wastepickup/";
    }
}
