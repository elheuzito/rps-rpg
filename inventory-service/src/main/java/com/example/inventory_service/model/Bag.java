package com.example.inventory_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("bag")
@Data
@EqualsAndHashCode(callSuper = true)
public class Bag extends InventoryComponent {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<InventoryComponent> items = new ArrayList<>();

    public void addItem(InventoryComponent item) {
        items.add(item);
    }

    public void removeItem(InventoryComponent item) {
        items.remove(item);
    }

    @Override
    public double getTotalWeight() {
        double total = getWeight(); // Weight of the bag itself
        for (InventoryComponent item : items) {
            total += item.getTotalWeight();
        }
        return total;
    }
}
