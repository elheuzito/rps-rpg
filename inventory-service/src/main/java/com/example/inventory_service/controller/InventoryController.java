package com.example.inventory_service.controller;

import com.example.inventory_service.model.InventoryComponent;
import com.example.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/items")
    public InventoryComponent createItem(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        double weight = Double.parseDouble(payload.get("weight").toString());
        return inventoryService.createItem(name, weight);
    }

    @PostMapping("/bags")
    public InventoryComponent createBag(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        double weight = Double.parseDouble(payload.get("weight").toString());
        return inventoryService.createBag(name, weight);
    }

    @PostMapping("/bags/{bagId}/add/{itemId}")
    public InventoryComponent addItemToBag(@PathVariable Long bagId, @PathVariable Long itemId) {
        return inventoryService.addItemToBag(bagId, itemId);
    }

    @GetMapping("/{id}")
    public InventoryComponent getComponent(@PathVariable Long id) {
        return inventoryService.getComponent(id);
    }

    @GetMapping
    public List<InventoryComponent> getAllComponents() {
        return inventoryService.getAllComponents();
    }
}
