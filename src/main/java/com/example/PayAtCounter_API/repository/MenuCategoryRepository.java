package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, String> {
    
    List<MenuCategory> findByStore_StoreId(String storeId);
    
    Optional<MenuCategory> findByStore_StoreIdAndNameIgnoreCase(String storeId, String name);
}