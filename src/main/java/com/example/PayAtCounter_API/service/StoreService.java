package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.model.Store;
import com.example.PayAtCounter_API.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
}

