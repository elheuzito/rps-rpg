package com.example.player_service.service.factory;

import com.example.player_service.model.CharacterClass;
import com.example.player_service.model.Mage;
import com.example.player_service.model.Warrior;
import org.springframework.stereotype.Component;

@Component
public class CharacterClassFactory {
    public CharacterClass createCharacterClass(String type) {
        if (type.equalsIgnoreCase("warrior")) {
            return new Warrior();
        } else if (type.equalsIgnoreCase("mage")) {
            return new Mage();
        } else {
            throw new IllegalArgumentException("Unknown character class: " + type);
        }
    }
}
