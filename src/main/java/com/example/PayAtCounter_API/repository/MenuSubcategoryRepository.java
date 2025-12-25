package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.MenuSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuSubcategoryRepository extends JpaRepository<MenuSubcategory, String> {
    Optional<MenuSubcategory> findByCategory_CategoryIdAndNameIgnoreCase(String categoryId, String name);
}
