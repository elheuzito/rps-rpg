package com.example.combat_service.controller;

import com.example.combat_service.model.CombatState;
import com.example.combat_service.service.CombatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/combat")
@CrossOrigin(origins = "*")
public class CombatController {

    @Autowired
    private CombatService combatService;

    @PostMapping("/fight")
    public CombatState fight(@RequestBody Map<String, String> payload) {
        String playerMove = payload.get("move");
        return combatService.fightRound(playerMove);
    }

    @PostMapping("/heal")
    public CombatState heal() {
        return combatService.healPlayer();
    }

    @PostMapping("/reset")
    public CombatState reset() {
        combatService.resetGame();
        return combatService.getState();
    }

    @PostMapping("/set-class")
    public CombatState setClass(@RequestBody Map<String, String> payload) {
        String characterClass = payload.get("class");
        combatService.setPlayerClass(characterClass);
        return combatService.getState();
    }

    @GetMapping("/state")
    public CombatState getState() {
        return combatService.getState();
    }

    @GetMapping("/difficulty")
    public int getDifficulty() {
        return combatService.getDifficulty();
    }
}
