package com.example.wastepickup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wastepickup.model.WastePickup;
import com.example.wastepickup.repository.WastePickupRepository;

@Service
//public class WastePickupService {
    public class WastePickupService implements IWastePickupService{
    @Autowired
    private WastePickupRepository wastePickupRepository;

    // ✅ Get all waste pickup requests
    @Override
    public List<WastePickup> getAllWastePickups() {
        return wastePickupRepository.findAll();
    }

    // ✅ Get a waste pickup request by ID
    @Override
    public Optional<WastePickup> getWastePickupById(Long id) {
        return wastePickupRepository.findById(id);
    }

    // ✅ Save or update a waste pickup request
    @Override
    public WastePickup saveWastePickup(WastePickup wastePickup) {
        return wastePickupRepository.save(wastePickup);
    }

    // ✅ Delete a waste pickup request by ID
    @Override
    public void deleteWastePickup(Long id) {
        wastePickupRepository.deleteById(id);
    }

    @Override
    public List<WastePickup> getWastePickupsByUserName(String username) {
        // Fetch waste pickups for the given username from the repository
        return wastePickupRepository.findByUserName(username);
    }
}
