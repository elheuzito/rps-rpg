package com.example.inventory_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("item")
@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends InventoryComponent {

    @Override
    public double getTotalWeight() {
        return getWeight();
    }
}
