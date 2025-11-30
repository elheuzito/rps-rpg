package com.example.player_service.model;

import java.util.Map;

public class Mage implements CharacterClass {
    @Override
    public String getName() {
        return "Mage";
    }

    @Override
    public Map<String, Integer> getBaseStats() {
        return Map.of(
                "strength", 2,
                "intelligence", 10,
                "dexterity", 5);
    }
}
