package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.MenuItemImageDTO;
import com.example.PayAtCounter_API.exception.ResourceNotFoundException;
import com.example.PayAtCounter_API.model.MenuItem;
import com.example.PayAtCounter_API.model.MenuItemImage;
import com.example.PayAtCounter_API.repository.MenuItemImageRepository;
import com.example.PayAtCounter_API.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemImageService {

    private final MenuItemImageRepository menuItemImageRepository;
    private final MenuItemRepository menuItemRepository;

    public List<MenuItemImageDTO> getImagesByMenuItemId(String menuItemId) {
        String sanitizedMenuItemId = menuItemId.replace("{", "").replace("}", "").trim();

        if (!menuItemRepository.existsById(sanitizedMenuItemId)) {
            throw new ResourceNotFoundException("MenuItem", "menuItemId", sanitizedMenuItemId);
        }

        List<MenuItemImage> images = menuItemImageRepository.findByMenuItem_MenuItemId(sanitizedMenuItemId);
        return images.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuItemImageDTO createMenuItemImage(MenuItemImageDTO dto) {
        if (dto.getMenuItemId() == null || dto.getMenuItemId().trim().isEmpty()) {
            throw new RuntimeException("Menu Item ID is required");
        }
        if (dto.getImageUrl() == null || dto.getImageUrl().trim().isEmpty()) {
            throw new RuntimeException("Image URL is required");
        }

        String sanitizedMenuItemId = dto.getMenuItemId().replace("{", "").replace("}", "").trim();

        MenuItem menuItem = menuItemRepository.findById(sanitizedMenuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "menuItemId", sanitizedMenuItemId));

        MenuItemImage image = new MenuItemImage();
        image.setImageId(UUID.randomUUID().toString());
        image.setMenuItem(menuItem);
        image.setImageUrl(dto.getImageUrl().trim());
        image.setIsPrimary(dto.getIsPrimary() != null ? dto.getIsPrimary() : false);
        image.setUploadedAt(Instant.now());

        // Handle primary image logic: if this is primary, unset others
        if (image.getIsPrimary()) {
            unsetOtherPrimaryImages(sanitizedMenuItemId);
        }

        MenuItemImage savedImage = menuItemImageRepository.save(image);
        return convertToDTO(savedImage);
    }

    @Transactional
    public MenuItemImageDTO updateMenuItemImage(String imageId, MenuItemImageDTO dto) {
        String sanitizedImageId = imageId.replace("{", "").replace("}", "").trim();

        MenuItemImage existingImage = menuItemImageRepository.findById(sanitizedImageId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItemImage", "imageId", sanitizedImageId));

        if (dto.getImageUrl() != null && !dto.getImageUrl().trim().isEmpty()) {
            existingImage.setImageUrl(dto.getImageUrl().trim());
        }

        if (dto.getIsPrimary() != null) {
            existingImage.setIsPrimary(dto.getIsPrimary());
            // If setting to true, unset others for the same menu item
            if (dto.getIsPrimary()) {
                unsetOtherPrimaryImages(existingImage.getMenuItem().getMenuItemId(), sanitizedImageId);
            }
        }

        MenuItemImage updatedImage = menuItemImageRepository.save(existingImage);
        return convertToDTO(updatedImage);
    }

    private void unsetOtherPrimaryImages(String menuItemId) {
        unsetOtherPrimaryImages(menuItemId, null);
    }

    private void unsetOtherPrimaryImages(String menuItemId, String excludeImageId) {
        List<MenuItemImage> images = menuItemImageRepository.findByMenuItem_MenuItemId(menuItemId);
        for (MenuItemImage img : images) {
            if (excludeImageId == null || !img.getImageId().equals(excludeImageId)) {
                if (Boolean.TRUE.equals(img.getIsPrimary())) {
                    img.setIsPrimary(false);
                    menuItemImageRepository.save(img);
                }
            }
        }
    }

    private MenuItemImageDTO convertToDTO(MenuItemImage image) {
        return new MenuItemImageDTO(
                image.getImageId(),
                image.getMenuItem().getMenuItemId(),
                image.getImageUrl(),
                image.getIsPrimary(),
                image.getUploadedAt()
        );
    }
}

