package com.example.player_service.model;

import java.util.Map;

public class Warrior implements CharacterClass {
    @Override
    public String getName() {
        return "Warrior";
    }

    @Override
    public Map<String, Integer> getBaseStats() {
        return Map.of(
                "strength", 10,
                "intelligence", 2,
                "dexterity", 5);
    }
}
