package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.MenuSubcategoryDTO;
import com.example.PayAtCounter_API.exception.ResourceNotFoundException;
import com.example.PayAtCounter_API.model.MenuCategory;
import com.example.PayAtCounter_API.model.MenuSubcategory;
import com.example.PayAtCounter_API.repository.MenuCategoryRepository;
import com.example.PayAtCounter_API.repository.MenuSubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuSubcategoryService {

    private final MenuSubcategoryRepository menuSubcategoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    public List<MenuSubcategoryDTO> getAllMenuSubcategories() {
        List<MenuSubcategory> subcategories = menuSubcategoryRepository.findAll();
        return subcategories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuSubcategoryDTO createMenuSubcategory(MenuSubcategoryDTO dto) {
        // Validate inputs
        if (dto.getCategoryId() == null || dto.getCategoryId().trim().isEmpty()) {
            throw new RuntimeException("Category ID is required");
        }
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Subcategory name is required");
        }

        String sanitizedCategoryId = dto.getCategoryId().replace("{", "").replace("}", "").trim();

        // Check if category exists
        MenuCategory category = menuCategoryRepository.findById(sanitizedCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", "categoryId", sanitizedCategoryId));

        // Check for duplicate name in the same category
        Optional<MenuSubcategory> duplicate = menuSubcategoryRepository
                .findByCategory_CategoryIdAndNameIgnoreCase(sanitizedCategoryId, dto.getName().trim());
        
        if (duplicate.isPresent()) {
            throw new RuntimeException("Subcategory with name '" + dto.getName() + "' already exists in this category");
        }

        // Create
        MenuSubcategory subcategory = new MenuSubcategory();
        subcategory.setSubcategoryId(UUID.randomUUID().toString());
        subcategory.setName(dto.getName().trim());
        subcategory.setCategory(category);

        return convertToDTO(menuSubcategoryRepository.save(subcategory));
    }

    @Transactional
    public MenuSubcategoryDTO updateMenuSubcategory(String subcategoryId, MenuSubcategoryDTO dto) {
        String sanitizedId = subcategoryId.replace("{", "").replace("}", "").trim();
        
        MenuSubcategory subcategory = menuSubcategoryRepository.findById(sanitizedId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuSubcategory", "subcategoryId", sanitizedId));

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Subcategory name is required");
        }

        String newName = dto.getName().trim();

        // Check duplicate name (excluding self)
        Optional<MenuSubcategory> duplicate = menuSubcategoryRepository
                .findByCategory_CategoryIdAndNameIgnoreCase(subcategory.getCategory().getCategoryId(), newName);

        if (duplicate.isPresent() && !duplicate.get().getSubcategoryId().equals(sanitizedId)) {
            throw new RuntimeException("Subcategory with name '" + newName + "' already exists in this category");
        }

        subcategory.setName(newName);
        
        // If we wanted to allow moving subcategories to different categories, we would handle that here too,
        // but the prompt asked "UpdateSubCategorie ... But, I only need to Update CategoryName Accordingly" 
        // (actually the prompt for MenuCategory said that, for this one it just said "UpdateSubCategorie").
        // I'll stick to updating the name as that's the primary editable field. 
        // If categoryId is passed and different, we could update it, but typically that's a move operation.
        // Given the previous task, I'll focus on the name, but checking the DTO if categoryId is present might be good.
        // However, "Repo standards" usually implies consistency. The previous task specifically requested ONLY name update.
        // I will stick to name update to be safe, unless specifically asked to move.

        return convertToDTO(menuSubcategoryRepository.save(subcategory));
    }

    private MenuSubcategoryDTO convertToDTO(MenuSubcategory subcategory) {
        return new MenuSubcategoryDTO(
                subcategory.getSubcategoryId(),
                subcategory.getName(),
                subcategory.getCategory().getCategoryId()
        );
    }
}
