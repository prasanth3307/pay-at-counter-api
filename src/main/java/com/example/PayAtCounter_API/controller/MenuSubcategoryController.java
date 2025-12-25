package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.dto.MenuSubcategoryDTO;
import com.example.PayAtCounter_API.service.MenuSubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menusubcategories")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MenuSubcategoryController {

    private final MenuSubcategoryService menuSubcategoryService;

    @GetMapping("/fetchAll")
    public List<MenuSubcategoryDTO> fetchAllMenuSubcategories() {
        return menuSubcategoryService.getAllMenuSubcategories();
    }

    // Standard REST endpoint for fetching all
    @GetMapping
    public ResponseEntity<List<MenuSubcategoryDTO>> getAllSubcategories() {
        return ResponseEntity.ok(menuSubcategoryService.getAllMenuSubcategories());
    }

    @PostMapping
    public ResponseEntity<MenuSubcategoryDTO> createMenuSubcategory(@RequestBody MenuSubcategoryDTO dto) {
        MenuSubcategoryDTO created = menuSubcategoryService.createMenuSubcategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{subcategoryId}")
    public ResponseEntity<MenuSubcategoryDTO> updateMenuSubcategory(
            @PathVariable String subcategoryId,
            @RequestBody MenuSubcategoryDTO dto) {
        MenuSubcategoryDTO updated = menuSubcategoryService.updateMenuSubcategory(subcategoryId, dto);
        return ResponseEntity.ok(updated);
    }
}
