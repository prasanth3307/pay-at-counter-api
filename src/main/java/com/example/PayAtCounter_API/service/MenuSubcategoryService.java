package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.MenuSubcategoryDTO;
import com.example.PayAtCounter_API.model.MenuSubcategory;
import com.example.PayAtCounter_API.repository.MenuSubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuSubcategoryService {

    @Autowired
    private MenuSubcategoryRepository menuSubcategoryRepository;

    public List<MenuSubcategoryDTO> getAllMenuSubcategories() {
        List<MenuSubcategory> subcategories = menuSubcategoryRepository.findAll();

        return subcategories.stream()
                .map(sub -> new MenuSubcategoryDTO(
                        sub.getSubcategoryId(),
                        sub.getName(),
                        sub.getCategory().getCategoryId()
                ))
                .collect(Collectors.toList());
    }
}
