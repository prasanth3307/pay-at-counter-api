package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.dto.MenuSubcategoryDTO;
import com.example.PayAtCounter_API.service.MenuSubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menusubcategories")
@CrossOrigin(origins = "*")
public class MenuSubcategoryController {

    @Autowired
    private MenuSubcategoryService menuSubcategoryService;

    @GetMapping("/fetchAll")
    public List<MenuSubcategoryDTO> fetchAllMenuSubcategories() {
        return menuSubcategoryService.getAllMenuSubcategories();
    }
}
