package com.example.wastepickup.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WastePickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wasteType;
    private String pickupLocation;
    private LocalDateTime pickupDateTime;
    private String status;
    private String userName;

    // ✅ Newly added fields
    private String category;     // e.g., "Recyclable", "Non-Recyclable"
    private String processedBy;  // Name/ID of recycling center staff who processed the waste

    // ✅ Default constructor
    public WastePickup() {
    }

    // ✅ Constructor with parameters
    public WastePickup(String wasteType, String pickupLocation, LocalDateTime pickupDateTime, String status, String userName, String category, String processedBy) {
        this.wasteType = wasteType;
        this.pickupLocation = pickupLocation;
        this.pickupDateTime = pickupDateTime;
        this.status = status;
        this.userName = userName;
        this.category = category;
        this.processedBy = processedBy;
        updateStatusAutomatically();
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
        updateStatusAutomatically();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    // ✅ Automatic status update when scheduled time passes
    public void updateStatusAutomatically() {
        if (this.pickupDateTime != null && LocalDateTime.now().isAfter(this.pickupDateTime)) {
            this.status = "Completed";
        }
    }
}
