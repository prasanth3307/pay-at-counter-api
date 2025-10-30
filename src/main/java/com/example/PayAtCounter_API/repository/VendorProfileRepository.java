package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorProfileRepository extends JpaRepository<VendorProfile, String> {
}

