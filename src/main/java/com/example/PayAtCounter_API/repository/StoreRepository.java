package com.example.PayAtCounter_API.repository;

import com.example.PayAtCounter_API.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
}

