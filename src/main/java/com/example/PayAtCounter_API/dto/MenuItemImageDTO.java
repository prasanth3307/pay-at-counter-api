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
public class MenuItemImageDTO {
    
    private String imageId;
    
    @JsonProperty("menu_item_id")
    private String menuItemId;
    
    private String imageUrl;
    
    @JsonProperty("is_primary")
    private Boolean isPrimary;
    
    private Instant uploadedAt;
}

