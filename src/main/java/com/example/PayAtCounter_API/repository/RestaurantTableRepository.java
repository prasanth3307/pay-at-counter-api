package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, String> {

    List<RestaurantTable> findByStore_StoreId(String storeId);
}