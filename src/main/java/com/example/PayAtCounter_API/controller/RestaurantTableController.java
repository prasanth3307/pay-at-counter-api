package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.dto.RestaurantTableDTO;
import com.example.PayAtCounter_API.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant-tables")
@RequiredArgsConstructor
public class RestaurantTableController {

    private final RestaurantTableService restaurantTableService;

    @PostMapping("/create")
    public ResponseEntity<RestaurantTableDTO> createRestaurantTable(
            @RequestParam String vendorId,
            @RequestParam String storeId,
            @RequestBody RestaurantTableDTO tableDTO) {
        RestaurantTableDTO createdTable = restaurantTableService.createTable(vendorId, storeId, tableDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTable);
    }

    @PatchMapping("/update/{tableId}")
    public ResponseEntity<RestaurantTableDTO> updateRestaurantTable(
            @PathVariable String tableId,
            @RequestParam String vendorId,
            @RequestParam String storeId,
            @RequestBody RestaurantTableDTO tableDTO) {
        RestaurantTableDTO updated = restaurantTableService.updateTable(tableId, vendorId, storeId, tableDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/get-by-store/{storeId}")
    public ResponseEntity<List<RestaurantTableDTO>> getTablesByStoreAndVendor(
            @PathVariable String storeId,
            @RequestParam String vendorId) {
        List<RestaurantTableDTO> tables = restaurantTableService.getTablesByStoreAndVendor(storeId, vendorId);
        return ResponseEntity.ok(tables);
    }
}