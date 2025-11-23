package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.dto.RestaurantTableDTO;
import com.example.PayAtCounter_API.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class RestaurantTableController {

    private final RestaurantTableService restaurantTableService;

    @PostMapping
    public ResponseEntity<RestaurantTableDTO> createTable(@RequestBody RestaurantTableDTO tableDTO) {
        RestaurantTableDTO createdTable = restaurantTableService.createTable(tableDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTable);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTableDTO>> getAllTables() {
        List<RestaurantTableDTO> tables = restaurantTableService.getAllTables();
        return ResponseEntity.ok(tables);
    }

    @PutMapping("/{tableId}")
    public ResponseEntity<RestaurantTableDTO> updateTable(
            @PathVariable String tableId,
            @RequestBody RestaurantTableDTO tableDTO) {
        RestaurantTableDTO updated = restaurantTableService.updateTable(tableId, tableDTO);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{tableId}/table-number")
    public ResponseEntity<RestaurantTableDTO> updateTableNumber(
            @PathVariable String tableId,
            @RequestBody Map<String, Integer> body) {
        RestaurantTableDTO updated = restaurantTableService.updateTableNumber(
                tableId, body.get("tableNumber"));
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{tableId}/seating-capacity")
    public ResponseEntity<RestaurantTableDTO> updateSeatingCapacity(
            @PathVariable String tableId,
            @RequestBody Map<String, Integer> body) {
        RestaurantTableDTO updated = restaurantTableService.updateSeatingCapacity(
                tableId, body.get("seatingCapacity"));
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{tableId}/status")
    public ResponseEntity<RestaurantTableDTO> updateStatus(
            @PathVariable String tableId,
            @RequestBody Map<String, String> body) {
        RestaurantTableDTO updated = restaurantTableService.updateStatus(
                tableId, body.get("status"));
        return ResponseEntity.ok(updated);
    }
}