package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.model.VendorProfile;
import com.example.PayAtCounter_API.service.VendorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorProfileController {

    private final VendorProfileService vendorProfileService;

    @GetMapping
    public ResponseEntity<List<VendorProfile>> getAllVendors() {
        List<VendorProfile> vendors = vendorProfileService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }
}

