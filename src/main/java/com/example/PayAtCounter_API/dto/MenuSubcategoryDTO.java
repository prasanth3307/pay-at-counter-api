package com.example.PayAtCounter_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuSubcategoryDTO {
    private String subcategoryId;
    private String name;
    private String categoryId; // comes from MenuCategory entity
}
