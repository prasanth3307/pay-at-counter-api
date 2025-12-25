package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.MenuItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemImageRepository extends JpaRepository<MenuItemImage, String> {
    List<MenuItemImage> findByMenuItem_MenuItemId(String menuItemId);
}

