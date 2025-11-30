package com.example.combat_service.service;

import com.example.combat_service.model.CombatState;
import com.example.combat_service.service.command.AttackCommand;
import com.example.combat_service.service.command.CombatCommand;
import com.example.combat_service.service.observer.CombatObserver;
import com.example.combat_service.config.GameConfig;
import com.example.combat_service.service.strategy.CombatStrategy;
import com.example.combat_service.service.strategy.RandomStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CombatService {

    @Autowired
    private List<CombatObserver> observers;

    private CombatStrategy enemyStrategy = new RandomStrategy();
    private CombatState state = new CombatState();

    public CombatState fightRound(String playerMove) {
        if (!"ONGOING".equals(state.getStatus())) {
            return state;
        }

        String enemyMove = enemyStrategy.nextMove();

        // Log moves
        logAndNotify("Round " + state.getCurrentRound() + ": You chose " + playerMove + ", Enemy chose " + enemyMove);

        CombatCommand command = new AttackCommand(playerMove, enemyMove);
        String result = command.execute();

        if ("VICTORY".equals(result)) {
            int damage = state.getPlayerDamage();
            state.setEnemyHearts(state.getEnemyHearts() - damage);
            logAndNotify("Result: You WON this clash! Enemy takes " + damage + " damage.");
        } else if ("DEFEAT".equals(result)) {
            state.setPlayerHearts(state.getPlayerHearts() - 1);
            logAndNotify("Result: You LOST this clash! You take 1 damage.");
        } else {
            logAndNotify("Result: DRAW! No damage dealt.");
        }

        checkRoundStatus();
        return state;
    }

    private void checkRoundStatus() {
        // Check Player Death
        if (state.getPlayerHearts() <= 0) {
            state.setStatus("DEFEAT");
            logAndNotify("GAME OVER! You have been defeated.");
            return;
        }

        // Check Enemy Death (Round Win)
        if (state.getEnemyHearts() <= 0) {
            logAndNotify("Enemy Defeated! You regenerate 1 Heart.");
            state.setPlayerHearts(Math.min(state.getPlayerMaxHearts(), state.getPlayerHearts() + 1));

            if (state.getCurrentRound() < state.getMaxRounds()) {
                state.setCurrentRound(state.getCurrentRound() + 1);

                // Boss round (Round 3) has 5 hearts
                if (state.getCurrentRound() == 3) {
                    state.setEnemyHearts(5);
                    state.setEnemyMaxHearts(5);
                    logAndNotify("--- BOSS ROUND! The final opponent has 5 hearts! ---");
                } else {
                    state.setEnemyHearts(3);
                    state.setEnemyMaxHearts(3);
                    logAndNotify("--- ROUND " + state.getCurrentRound() + " START ---");
                }
            } else {
                state.setStatus("VICTORY");
                logAndNotify("CHAMPION! You defeated all opponents and won a CROW!");
            }
        }
    }

    public CombatState healPlayer() {
        if ("ONGOING".equals(state.getStatus()) && state.getPlayerHearts() < state.getPlayerMaxHearts()) {
            state.setPlayerHearts(state.getPlayerHearts() + 1);
            logAndNotify("Used Potion! Restored 1 Heart.");
        } else {
            logAndNotify("Cannot use potion right now.");
        }
        return state;
    }

    public CombatState getState() {
        return state;
    }

    public void resetGame() {
        state = new CombatState();
        logAndNotify("--- NEW GAME STARTED ---");
    }

    public void setPlayerClass(String characterClass) {
        state.initializeForClass(characterClass);
        logAndNotify("Class set to: " + characterClass + " (HP: " + state.getPlayerMaxHearts() + ", DMG: "
                + state.getPlayerDamage() + ")");
    }

    private void logAndNotify(String message) {
        state.addLog(message);
        for (CombatObserver observer : observers) {
            observer.onCombatEvent(message);
        }
    }

    public int getDifficulty() {
        return GameConfig.getInstance().getDifficultyLevel();
    }
}
