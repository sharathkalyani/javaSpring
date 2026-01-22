/*package com.example.wastepickup.service;

import com.example.wastepickup.model.WastePickup;

import java.util.List;
import java.util.Optional;

//public interface IWastePickupService {
   

    public interface IWastePickupService extends IReadOnlyWastePickupService, IWritableWastePickupService {
        // Nothing new, just a combination for controllers or future modules
     
    List<WastePickup> getAllWastePickups();
    Optional<WastePickup> getWastePickupById(Long id);
    WastePickup saveWastePickup(WastePickup wastePickup);
    void deleteWastePickup(Long id);
}
*/
package com.example.wastepickup.service;

public interface IWastePickupService extends IReadOnlyWastePickupService, IWritableWastePickupService {
    // No need to redefine methods here.
    // This interface simply combines read & write operations.
}
