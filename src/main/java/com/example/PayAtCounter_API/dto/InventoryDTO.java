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
public class InventoryDTO {

    private String inventoryId;

    @JsonProperty("menu_item_id")
    private String menuItemId;

    private Integer currentStock;

    private Integer restockThreshold;

    private Instant lastUpdated;
}

