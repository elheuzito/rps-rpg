package com.example.inventory_service.service;

import com.example.inventory_service.model.Bag;
import com.example.inventory_service.model.InventoryComponent;
import com.example.inventory_service.model.Item;
import com.example.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryComponent createItem(String name, double weight) {
        Item item = new Item();
        item.setName(name);
        item.setWeight(weight);
        return inventoryRepository.save(item);
    }

    public InventoryComponent createBag(String name, double weight) {
        Bag bag = new Bag();
        bag.setName(name);
        bag.setWeight(weight);
        return inventoryRepository.save(bag);
    }

    public InventoryComponent addItemToBag(Long bagId, Long itemId) {
        InventoryComponent bagComponent = inventoryRepository.findById(bagId)
                .orElseThrow(() -> new RuntimeException("Bag not found"));

        if (!(bagComponent instanceof Bag)) {
            throw new RuntimeException("Component is not a bag");
        }

        InventoryComponent itemComponent = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Bag bag = (Bag) bagComponent;
        bag.addItem(itemComponent);
        return inventoryRepository.save(bag);
    }

    public InventoryComponent getComponent(Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Component not found"));
    }

    public List<InventoryComponent> getAllComponents() {
        return inventoryRepository.findAll();
    }
}
