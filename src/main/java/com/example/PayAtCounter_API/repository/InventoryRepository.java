package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByMenuItem_MenuItemId(String menuItemId);
}

