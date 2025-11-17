package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.MenuSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuSubcategoryRepository extends JpaRepository<MenuSubcategory, String> {
}
