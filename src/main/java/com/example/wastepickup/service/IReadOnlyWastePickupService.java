package com.example.wastepickup.service;

import java.util.List;
import java.util.Optional;

import com.example.wastepickup.model.WastePickup;

public interface IReadOnlyWastePickupService {
    List<WastePickup> getAllWastePickups();
    Optional<WastePickup> getWastePickupById(Long id);
    List<WastePickup> getWastePickupsByUserName(String username);
}
