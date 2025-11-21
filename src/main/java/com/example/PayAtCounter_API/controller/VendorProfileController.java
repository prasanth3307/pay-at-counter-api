package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.dto.VendorProfileDTO;
import com.example.PayAtCounter_API.service.VendorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor/create")
@RequiredArgsConstructor
public class VendorProfileController {

    private final VendorProfileService vendorProfileService;

    // Get all vendors
    @GetMapping
    public ResponseEntity<List<VendorProfileDTO>> getAllVendors() {
        List<VendorProfileDTO> vendors = vendorProfileService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    // US1: Get vendor by ID
    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorProfileDTO> getVendorById(@PathVariable String vendorId) {
        VendorProfileDTO vendor = vendorProfileService.getVendorById(vendorId);
        return ResponseEntity.ok(vendor);
    }

    // US2: Create vendor profile
    @PostMapping
    public ResponseEntity<VendorProfileDTO> createVendor(@RequestBody VendorProfileDTO vendorProfileDTO) {
        VendorProfileDTO createdVendor = vendorProfileService.createVendor(vendorProfileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVendor);
    }

    // US3: Update vendor details (Name, Email, Phone No) - Supports partial updates
    @PatchMapping("/{vendorId}")
    public ResponseEntity<VendorProfileDTO> updateVendor(
            @PathVariable String vendorId,
            @RequestBody VendorProfileDTO vendorProfileDTO) {
        VendorProfileDTO updatedVendor = vendorProfileService.updateVendor(vendorId, vendorProfileDTO);
        return ResponseEntity.ok(updatedVendor);
    }
}