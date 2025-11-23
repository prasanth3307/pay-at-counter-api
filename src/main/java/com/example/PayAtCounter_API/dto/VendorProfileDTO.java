package com.example.PayAtCounter_API.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorProfileDTO {

    private String vendorId;

    private String vendorName;

    private String email;

    private String phoneNo;

    private String status;

    private Instant createdAt;

    private Instant updatedAt;
}