package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.model.VendorProfile;
import com.example.PayAtCounter_API.repository.VendorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorProfileService {

    private final VendorProfileRepository vendorProfileRepository;

    public List<VendorProfile> getAllVendors() {
        return vendorProfileRepository.findAll();
    }
}

