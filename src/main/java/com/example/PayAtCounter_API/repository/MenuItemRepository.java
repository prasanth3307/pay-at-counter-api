package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, String> {
}

