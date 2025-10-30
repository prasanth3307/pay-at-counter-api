package com.example.PayAtCounter_API.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    
    private String storeId;
    
    @JsonProperty("vendor_id")
    private String vendorId;
    
    private String storeName;
    
    private String storeAddress;
    
    private String storePhone;
    
    private BigDecimal latitude;
    
    private BigDecimal longitude;
    
    private Instant createdAt;
    
    private Instant updatedAt;
}

