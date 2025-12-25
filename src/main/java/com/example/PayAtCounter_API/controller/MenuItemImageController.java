package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.dto.MenuItemImageDTO;
import com.example.PayAtCounter_API.service.MenuItemImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-item-images")
@RequiredArgsConstructor
public class MenuItemImageController {

    private final MenuItemImageService menuItemImageService;

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<MenuItemImageDTO>> getImagesByMenuItemId(@PathVariable String menuItemId) {
        List<MenuItemImageDTO> images = menuItemImageService.getImagesByMenuItemId(menuItemId);
        return ResponseEntity.ok(images);
    }

    @PostMapping
    public ResponseEntity<MenuItemImageDTO> createMenuItemImage(@RequestBody MenuItemImageDTO dto) {
        MenuItemImageDTO createdImage = menuItemImageService.createMenuItemImage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdImage);
    }

    @PatchMapping("/{imageId}")
    public ResponseEntity<MenuItemImageDTO> updateMenuItemImage(
            @PathVariable String imageId,
            @RequestBody MenuItemImageDTO dto) {
        MenuItemImageDTO updatedImage = menuItemImageService.updateMenuItemImage(imageId, dto);
        return ResponseEntity.ok(updatedImage);
    }
}

