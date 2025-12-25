package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.exception.ResourceNotFoundException;
import com.example.PayAtCounter_API.model.MenuCategory;
import com.example.PayAtCounter_API.model.Store;
import com.example.PayAtCounter_API.dto.MenuCategoryDTO;
import com.example.PayAtCounter_API.repository.MenuCategoryRepository;
import com.example.PayAtCounter_API.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final StoreRepository storeRepository;

    public MenuCategoryService(MenuCategoryRepository menuCategoryRepository, StoreRepository storeRepository) {
        this.menuCategoryRepository = menuCategoryRepository;
        this.storeRepository = storeRepository;
    }

    public List<MenuCategoryDTO> getAllCategories() {
        return menuCategoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuCategoryDTO createMenuCategory(MenuCategoryDTO menuCategoryDTO) {
        // Validate store exists
        Store store = storeRepository.findById(menuCategoryDTO.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + menuCategoryDTO.getStoreId()));

        // Validate category name is provided
        if (menuCategoryDTO.getCategoryName() == null || menuCategoryDTO.getCategoryName().trim().isEmpty()) {
            throw new RuntimeException("Category name is required");
        }

        // Create new menu category
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setCategoryId(UUID.randomUUID().toString());
        menuCategory.setStore(store);
        menuCategory.setName(menuCategoryDTO.getCategoryName().trim());

        MenuCategory savedCategory = menuCategoryRepository.save(menuCategory);
        return convertToDTO(savedCategory);
    }

    @Transactional
    public MenuCategoryDTO updateMenuCategoryName(String categoryId, MenuCategoryDTO menuCategoryDTO) {
        // Sanitize categoryId - remove any braces that might be accidentally included
        String sanitizedCategoryId = categoryId != null ? categoryId.replace("{", "").replace("}", "").trim() : null;
        
        if (sanitizedCategoryId == null || sanitizedCategoryId.isEmpty()) {
            throw new RuntimeException("Category ID is required");
        }
        
        // Find existing category by ID
        MenuCategory existingCategory = menuCategoryRepository.findById(sanitizedCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", "categoryId", sanitizedCategoryId));

        // Validate category name is provided
        if (menuCategoryDTO.getCategoryName() == null || menuCategoryDTO.getCategoryName().trim().isEmpty()) {
            throw new RuntimeException("Category name is required");
        }

        String newCategoryName = menuCategoryDTO.getCategoryName().trim();

        // Check if another category with same name already exists for this store (excluding current category)
        Optional<MenuCategory> duplicateCategory = menuCategoryRepository.findByStore_StoreIdAndNameIgnoreCase(
                existingCategory.getStore().getStoreId(), 
                newCategoryName);
        
        if (duplicateCategory.isPresent() && !duplicateCategory.get().getCategoryId().equals(sanitizedCategoryId)) {
            throw new RuntimeException("Category with name '" + newCategoryName + "' already exists for this store");
        }

        // Update only the category name
        existingCategory.setName(newCategoryName);

        // Save and return the updated category
        MenuCategory updatedCategory = menuCategoryRepository.save(existingCategory);
        return convertToDTO(updatedCategory);
    }

    private MenuCategoryDTO convertToDTO(MenuCategory menuCategory) {
        MenuCategoryDTO dto = new MenuCategoryDTO();
        dto.setCategoryId(menuCategory.getCategoryId());
        dto.setCategoryName(menuCategory.getName());
        dto.setStoreId(menuCategory.getStore() != null ? menuCategory.getStore().getStoreId() : null);
        return dto;
    }
}