package com.example.wastepickup.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wastepickup.model.WastePickup;

public interface WastePickupRepository extends JpaRepository<WastePickup, Long> {
    List<WastePickup> findByStatus(String status);
    List<WastePickup> findByPickupDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<WastePickup> findByPickupDateTimeBetweenAndPickupLocation(LocalDateTime start, LocalDateTime end, String pickupLocation);
    List<WastePickup> findByUserName(String userName);
    List<WastePickup> findByStatusIn(List<String> statuses);

}
