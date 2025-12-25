package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.dto.InventoryDTO;
import com.example.PayAtCounter_API.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<InventoryDTO> getInventoryByMenuItemId(@PathVariable String menuItemId) {
        InventoryDTO inventory = inventoryService.getInventoryByMenuItemId(menuItemId);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO dto) {
        InventoryDTO createdInventory = inventoryService.createInventory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
    }

    @PatchMapping("/{inventoryId}")
    public ResponseEntity<InventoryDTO> updateInventory(
            @PathVariable String inventoryId,
            @RequestBody InventoryDTO dto) {
        InventoryDTO updatedInventory = inventoryService.updateInventory(inventoryId, dto);
        return ResponseEntity.ok(updatedInventory);
    }
}

