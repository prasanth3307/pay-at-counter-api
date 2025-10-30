package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.RestaurantTableDTO;
import com.example.PayAtCounter_API.model.RestaurantTable;
import com.example.PayAtCounter_API.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;

    public List<RestaurantTableDTO> getAllTables() {
        List<RestaurantTable> tables = restaurantTableRepository.findAll();
        
        return tables.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

