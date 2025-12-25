package com.example.PayAtCounter_API.service;

import com.example.PayAtCounter_API.dto.InventoryDTO;
import com.example.PayAtCounter_API.exception.ResourceNotFoundException;
import com.example.PayAtCounter_API.model.Inventory;
import com.example.PayAtCounter_API.model.MenuItem;
import com.example.PayAtCounter_API.repository.InventoryRepository;
import com.example.PayAtCounter_API.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MenuItemRepository menuItemRepository;

    public InventoryDTO getInventoryByMenuItemId(String menuItemId) {
        String sanitizedMenuItemId = menuItemId.replace("{", "").replace("}", "").trim();

        if (!menuItemRepository.existsById(sanitizedMenuItemId)) {
            throw new ResourceNotFoundException("MenuItem", "menuItemId", sanitizedMenuItemId);
        }

        Inventory inventory = inventoryRepository.findByMenuItem_MenuItemId(sanitizedMenuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "menuItemId", sanitizedMenuItemId));

        return convertToDTO(inventory);
    }

    @Transactional
    public InventoryDTO createInventory(InventoryDTO dto) {
        if (dto.getMenuItemId() == null || dto.getMenuItemId().trim().isEmpty()) {
            throw new RuntimeException("Menu Item ID is required");
        }

        String sanitizedMenuItemId = dto.getMenuItemId().replace("{", "").replace("}", "").trim();

        MenuItem menuItem = menuItemRepository.findById(sanitizedMenuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "menuItemId", sanitizedMenuItemId));

        // Check if inventory already exists for this menu item
        if (inventoryRepository.findByMenuItem_MenuItemId(sanitizedMenuItemId).isPresent()) {
            throw new RuntimeException("Inventory already exists for menu item: " + sanitizedMenuItemId);
        }

        Inventory inventory = new Inventory();
        inventory.setInventoryId(UUID.randomUUID().toString());
        inventory.setMenuItem(menuItem);
        inventory.setCurrentStock(dto.getCurrentStock() != null ? dto.getCurrentStock() : 0);
        inventory.setRestockThreshold(dto.getRestockThreshold() != null ? dto.getRestockThreshold() : 0);
        inventory.setLastUpdated(Instant.now());

        Inventory savedInventory = inventoryRepository.save(inventory);
        return convertToDTO(savedInventory);
    }

    @Transactional
    public InventoryDTO updateInventory(String inventoryId, InventoryDTO dto) {
        String sanitizedInventoryId = inventoryId.replace("{", "").replace("}", "").trim();

        Inventory inventory = inventoryRepository.findById(sanitizedInventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "inventoryId", sanitizedInventoryId));

        if (dto.getCurrentStock() != null) {
            inventory.setCurrentStock(dto.getCurrentStock());
        }
        if (dto.getRestockThreshold() != null) {
            inventory.setRestockThreshold(dto.getRestockThreshold());
        }
        
        inventory.setLastUpdated(Instant.now());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return convertToDTO(updatedInventory);
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getInventoryId(),
                inventory.getMenuItem().getMenuItemId(),
                inventory.getCurrentStock(),
                inventory.getRestockThreshold(),
                inventory.getLastUpdated()
        );
    }
}

