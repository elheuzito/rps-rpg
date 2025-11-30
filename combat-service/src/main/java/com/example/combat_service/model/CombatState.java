package com.example.combat_service.model;

import java.util.ArrayList;
import java.util.List;

public class CombatState {
    private String playerClass = "Warrior"; // Warrior or Mage
    private int playerHearts = 5; // Default for Warrior
    private int playerMaxHearts = 5;
    private int playerDamage = 1;

    private int enemyHearts = 3;
    private int enemyMaxHearts = 3;

    private int currentRound = 1;
    private int maxRounds = 3;
    private String status = "ONGOING"; // ONGOING, VICTORY, DEFEAT
    private List<String> logs = new ArrayList<>();

    public void initializeForClass(String characterClass) {
        this.playerClass = characterClass;
        if ("Mage".equalsIgnoreCase(characterClass)) {
            this.playerMaxHearts = 3;
            this.playerHearts = 3;
            this.playerDamage = 2;
        } else { // Warrior
            this.playerMaxHearts = 5;
            this.playerHearts = 5;
            this.playerDamage = 1;
        }
    }

    public void addLog(String log) {
        logs.add(0, log); // Add to top
        if (logs.size() > 50) {
            logs.remove(logs.size() - 1);
        }
    }

    // Getters and Setters
    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public int getPlayerMaxHearts() {
        return playerMaxHearts;
    }

    public void setPlayerMaxHearts(int playerMaxHearts) {
        this.playerMaxHearts = playerMaxHearts;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public void setPlayerDamage(int playerDamage) {
        this.playerDamage = playerDamage;
    }

    public int getEnemyMaxHearts() {
        return enemyMaxHearts;
    }

    public void setEnemyMaxHearts(int enemyMaxHearts) {
        this.enemyMaxHearts = enemyMaxHearts;
    }

    public int getPlayerHearts() {
        return playerHearts;
    }

    public void setPlayerHearts(int playerHearts) {
        this.playerHearts = playerHearts;
    }

    public int getEnemyHearts() {
        return enemyHearts;
    }

    public void setEnemyHearts(int enemyHearts) {
        this.enemyHearts = enemyHearts;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }
}
