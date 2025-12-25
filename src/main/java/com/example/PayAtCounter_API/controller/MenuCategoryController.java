package com.example.PayAtCounter_API.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.PayAtCounter_API.service.MenuCategoryService;
import com.example.PayAtCounter_API.dto.MenuCategoryDTO;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    public MenuCategoryController(MenuCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<MenuCategoryDTO>> getAllCategories() {
        List<MenuCategoryDTO> categories = menuCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<MenuCategoryDTO> createMenuCategory(@RequestBody MenuCategoryDTO menuCategoryDTO) {
        MenuCategoryDTO createdCategory = menuCategoryService.createMenuCategory(menuCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<MenuCategoryDTO> updateMenuCategoryName(
            @PathVariable String categoryId,
            @RequestBody MenuCategoryDTO menuCategoryDTO) {
        MenuCategoryDTO updatedCategory = menuCategoryService.updateMenuCategoryName(categoryId, menuCategoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }
}