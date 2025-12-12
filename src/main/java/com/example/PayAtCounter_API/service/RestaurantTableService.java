package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.RestaurantTableDTO;
import com.example.PayAtCounter_API.model.RestaurantTable;
import com.example.PayAtCounter_API.model.Store;
import com.example.PayAtCounter_API.model.VendorProfile;
import com.example.PayAtCounter_API.repository.RestaurantTableRepository;
import com.example.PayAtCounter_API.repository.StoreRepository;
import com.example.PayAtCounter_API.repository.VendorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;
    private final StoreRepository storeRepository;
    private final VendorProfileRepository vendorProfileRepository;

    public List<RestaurantTableDTO> getTablesByStoreAndVendor(String storeId, String vendorId) {
        VendorProfile vendor = vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + vendorId));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + storeId));

        if (!store.getVendor().getVendorId().equals(vendorId)) {
            throw new RuntimeException("Store " + storeId + " does not belong to vendor " + vendorId);
        }

        List<RestaurantTable> tables = restaurantTableRepository.findByStore_StoreId(storeId);
        return tables.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RestaurantTableDTO createTable(String vendorId, String storeId, RestaurantTableDTO tableDTO) {
        VendorProfile vendor = vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + vendorId));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + storeId));

        if (!store.getVendor().getVendorId().equals(vendorId)) {
            throw new RuntimeException("Store " + storeId + " does not belong to vendor " + vendorId);
        }

        if (tableDTO.getTableNumber() == null) {
            throw new RuntimeException("Table number is required");
        }
        if (tableDTO.getSeatingCapacity() == null) {
            throw new RuntimeException("Seating capacity is required");
        }
        if (tableDTO.getStatus() == null || tableDTO.getStatus().isEmpty()) {
            throw new RuntimeException("Status is required");
        }

        String generatedTableId = generateTableId();

        RestaurantTable table = new RestaurantTable();
        table.setTableId(generatedTableId);
        table.setStore(store);
        table.setTableNumber(tableDTO.getTableNumber());
        table.setSeatingCapacity(tableDTO.getSeatingCapacity());
        table.setStatus(tableDTO.getStatus());
        table.setCreatedAt(Instant.now());

        RestaurantTable savedTable = restaurantTableRepository.save(table);
        return convertToDTO(savedTable);
    }

    @Transactional
    public RestaurantTableDTO updateTable(String tableId, String vendorId, String storeId, RestaurantTableDTO tableDTO) {
        VendorProfile vendor = vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + vendorId));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + storeId));

        if (!store.getVendor().getVendorId().equals(vendorId)) {
            throw new RuntimeException("Store " + storeId + " does not belong to vendor " + vendorId);
        }

        RestaurantTable table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Restaurant table not found with ID: " + tableId));

        if (!table.getStore().getStoreId().equals(storeId)) {
            throw new RuntimeException("Table " + tableId + " does not belong to store " + storeId);
        }

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

    private String generateTableId() {
        List<RestaurantTable> allTables = restaurantTableRepository.findAll();

        int maxNumber = 0;
        for (RestaurantTable table : allTables) {
            String tableId = table.getTableId();
            if (tableId.startsWith("T")) {
                try {
                    int number = Integer.parseInt(tableId.substring(1));
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    // Skip non-numeric IDs
                }
            }
        }

        return String.format("T%03d", maxNumber + 1);
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
}