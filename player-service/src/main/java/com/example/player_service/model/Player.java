package com.example.player_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String characterClass;
    private int strength;
    private int intelligence;
    private int dexterity;
    private int hp;
    private int maxHp;

    // Private constructor for Builder
    private Player(Builder builder) {
        this.name = builder.name;
        this.characterClass = builder.characterClass;
        this.strength = builder.strength;
        this.intelligence = builder.intelligence;
        this.dexterity = builder.dexterity;
        this.maxHp = builder.maxHp;
        this.hp = builder.maxHp;
    }

    public static class Builder {
        private String name;
        private String characterClass;
        private int strength;
        private int intelligence;
        private int dexterity;
        private int maxHp = 100; // Default

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCharacterClass(CharacterClass charClass) {
            this.characterClass = charClass.getName();
            var stats = charClass.getBaseStats();
            this.strength = stats.getOrDefault("strength", 5);
            this.intelligence = stats.getOrDefault("intelligence", 5);
            this.dexterity = stats.getOrDefault("dexterity", 5);
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
