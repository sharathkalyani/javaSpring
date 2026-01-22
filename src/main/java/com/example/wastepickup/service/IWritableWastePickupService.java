package com.example.wastepickup.service;
//import com.yourproject.model.WastePickup;
import com.example.wastepickup.model.WastePickup;

public interface IWritableWastePickupService {
    WastePickup saveWastePickup(WastePickup wastePickup);
    void deleteWastePickup(Long id);
}
