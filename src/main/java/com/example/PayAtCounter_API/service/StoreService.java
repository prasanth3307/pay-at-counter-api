package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.StoreDTO;
import com.example.PayAtCounter_API.model.Store;
import com.example.PayAtCounter_API.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public List<StoreDTO> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        
        return stores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private StoreDTO convertToDTO(Store store) {
        return new StoreDTO(
                store.getStoreId(),
                store.getVendor().getVendorId(),
                store.getStoreName(),
                store.getStoreAddress(),
                store.getStorePhone(),
                store.getLatitude(),
                store.getLongitude(),
                store.getCreatedAt(),
                store.getUpdatedAt()
        );
    }
}

