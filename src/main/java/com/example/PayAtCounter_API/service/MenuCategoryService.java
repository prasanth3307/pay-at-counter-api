package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.model.MenuCategory;
import com.example.PayAtCounter_API.dto.MenuCategoryDTO;
import com.example.PayAtCounter_API.repository.MenuCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

    public MenuCategoryService(MenuCategoryRepository menuCategoryRepository) {
        this.menuCategoryRepository = menuCategoryRepository;
    }

    public List<MenuCategoryDTO> getAllCategories() {
        return menuCategoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MenuCategoryDTO convertToDTO(MenuCategory menuCategory) {
        MenuCategoryDTO dto = new MenuCategoryDTO();
        dto.setCategoryId(menuCategory.getCategoryId());
        dto.setCategoryName(menuCategory.getName());
        dto.setStoreId(menuCategory.getStore() != null ? menuCategory.getStore().getStoreId() : null);
        return dto;
    }
}