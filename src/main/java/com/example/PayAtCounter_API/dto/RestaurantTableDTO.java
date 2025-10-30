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
public class RestaurantTableDTO {
    
    private String tableId;
    
    @JsonProperty("store_id")
    private String storeId;
    
    private Integer tableNumber;
    
    private Integer seatingCapacity;
    
    private String status;
    
    private Instant createdAt;
}

