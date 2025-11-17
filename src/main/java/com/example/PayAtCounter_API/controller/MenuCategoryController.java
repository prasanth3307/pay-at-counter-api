package com.example.PayAtCounter_API.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}