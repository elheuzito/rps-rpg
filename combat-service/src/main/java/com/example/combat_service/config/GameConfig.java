package com.example.combat_service.config;

public class GameConfig {
    private static GameConfig instance;
    private int difficultyLevel;

    private GameConfig() {
        this.difficultyLevel = 1;
    }

    public static synchronized GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
