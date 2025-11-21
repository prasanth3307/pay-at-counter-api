package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.RestaurantTableDTO;
import com.example.PayAtCounter_API.model.RestaurantTable;
import com.example.PayAtCounter_API.model.Store;
import com.example.PayAtCounter_API.repository.RestaurantTableRepository;
import com.example.PayAtCounter_API.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;
    private final StoreRepository storeRepository;

    public List<RestaurantTableDTO> getAllTables() {
        List<RestaurantTable> tables = restaurantTableRepository.findAll();

        return tables.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RestaurantTableDTO createTable(RestaurantTableDTO tableDTO) {
        if (restaurantTableRepository.findById(tableDTO.getTableId()).isPresent()) {
            throw new RuntimeException("Table ID already exists: " + tableDTO.getTableId());
        }

        Store store = storeRepository.findById(tableDTO.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + tableDTO.getStoreId()));

        RestaurantTable table = new RestaurantTable();
        table.setTableId(tableDTO.getTableId());
        table.setStore(store);
        table.setTableNumber(tableDTO.getTableNumber());
        table.setSeatingCapacity(tableDTO.getSeatingCapacity());
        table.setStatus(tableDTO.getStatus());
        table.setCreatedAt(Instant.now());

        RestaurantTable savedTable = restaurantTableRepository.save(table);
        return convertToDTO(savedTable);
    }

    private RestaurantTableDTO convertToDTO(RestaurantTable table) {
        return new RestaurantTableDTO(
                table.getTableId(),
                table.getStore().getStoreId(),
                table.getTableNumber(),
                table.getSeatingCapacity(),
                table.getStatus(),
                table.getCreatedAt()
        );
    }
    @Transactional
    public RestaurantTableDTO updateTable(String tableId, RestaurantTableDTO tableDTO) {
        RestaurantTable table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found with ID: " + tableId));

        // Only update fields that are provided (not null)
        if (tableDTO.getTableNumber() != null) {
            table.setTableNumber(tableDTO.getTableNumber());
        }
        if (tableDTO.getSeatingCapacity() != null) {
            table.setSeatingCapacity(tableDTO.getSeatingCapacity());
        }
        if (tableDTO.getStatus() != null) {
            table.setStatus(tableDTO.getStatus());
        }

        RestaurantTable updatedTable = restaurantTableRepository.save(table);
        return convertToDTO(updatedTable);
    }
    @Transactional
    public RestaurantTableDTO updateTableNumber(String tableId, Integer newTableNumber) {
        RestaurantTable table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setTableNumber(newTableNumber);

        RestaurantTable updatedTable = restaurantTableRepository.save(table);
        return convertToDTO(updatedTable);
    }
    @Transactional
    public RestaurantTableDTO updateSeatingCapacity(String tableId, Integer newCapacity) {
        RestaurantTable table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setSeatingCapacity(newCapacity);

        RestaurantTable updatedTable = restaurantTableRepository.save(table);
        return convertToDTO(updatedTable);
    }
    @Transactional
    public RestaurantTableDTO updateStatus(String tableId, String newStatus) {
        RestaurantTable table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setStatus(newStatus);

        RestaurantTable updatedTable = restaurantTableRepository.save(table);
        return convertToDTO(updatedTable);
    }
}

