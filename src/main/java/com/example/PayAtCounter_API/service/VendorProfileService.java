package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.VendorProfileDTO;
import com.example.PayAtCounter_API.model.VendorProfile;
import com.example.PayAtCounter_API.repository.VendorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorProfileService {

    private final VendorProfileRepository vendorProfileRepository;

    // Get all vendors
    public List<VendorProfileDTO> getAllVendors() {
        List<VendorProfile> vendors = vendorProfileRepository.findAll();

        return vendors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // US1: Get vendor by ID
    public VendorProfileDTO getVendorById(String vendorId) {
        VendorProfile vendor = vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));

        return convertToDTO(vendor);
    }

    // US2: Create vendor profile
    public VendorProfileDTO createVendor(VendorProfileDTO vendorProfileDTO) {
        VendorProfile vendor = new VendorProfile();

        // Auto-generate vendorId
        vendor.setVendorId(UUID.randomUUID().toString());

        // Set vendor data from DTO (only name, email, phoneNo)
        vendor.setVendorName(vendorProfileDTO.getVendorName());
        vendor.setEmail(vendorProfileDTO.getEmail());
        vendor.setPhoneNo(vendorProfileDTO.getPhoneNo());

        // Set default status
        vendor.setStatus("ACTIVE");

        // Set timestamps
        vendor.setCreatedAt(Instant.now());
        vendor.setUpdatedAt(Instant.now());

        VendorProfile savedVendor = vendorProfileRepository.save(vendor);
        return convertToDTO(savedVendor);
    }

    // US3: Update vendor details - Supports partial updates (Name, Email, Phone No)
    public VendorProfileDTO updateVendor(String vendorId, VendorProfileDTO vendorProfileDTO) {
        // Find existing vendor by ID
        VendorProfile existingVendor = vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));

        // Update all fields using single helper function
        updateVendorFields(existingVendor, vendorProfileDTO);

        // Save and return the updated vendor
        VendorProfile updatedVendor = vendorProfileRepository.save(existingVendor);
        return convertToDTO(updatedVendor);
    }

    // Single helper function to update all vendor fields
    private void updateVendorFields(VendorProfile existingVendor, VendorProfileDTO vendorProfileDTO) {
        // Update vendor name if provided
        if (vendorProfileDTO.getVendorName() != null) {
            existingVendor.setVendorName(vendorProfileDTO.getVendorName());
        }

        // Update email if provided
        if (vendorProfileDTO.getEmail() != null) {
            existingVendor.setEmail(vendorProfileDTO.getEmail());
        }

        // Update phone number if provided
        if (vendorProfileDTO.getPhoneNo() != null) {
            existingVendor.setPhoneNo(vendorProfileDTO.getPhoneNo());
        }

        // Always update the updatedAt timestamp
        existingVendor.setUpdatedAt(Instant.now());
    }

    // Helper method: Convert VendorProfile entity to VendorProfileDTO
    private VendorProfileDTO convertToDTO(VendorProfile vendor) {
        return new VendorProfileDTO(
                vendor.getVendorId(),
                vendor.getVendorName(),
                vendor.getEmail(),
                vendor.getPhoneNo(),
                vendor.getStatus(),
                vendor.getCreatedAt(),
                vendor.getUpdatedAt()
        );
    }
}