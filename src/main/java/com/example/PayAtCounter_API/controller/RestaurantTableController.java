package com.example.PayAtCounter_API.controller;

import com.example.PayAtCounter_API.dto.RestaurantTableDTO;
import com.example.PayAtCounter_API.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class RestaurantTableController {

    private final RestaurantTableService restaurantTableService;

    @GetMapping
    public ResponseEntity<List<RestaurantTableDTO>> getAllTables() {
        List<RestaurantTableDTO> tables = restaurantTableService.getAllTables();
        return ResponseEntity.ok(tables);
    }
}

