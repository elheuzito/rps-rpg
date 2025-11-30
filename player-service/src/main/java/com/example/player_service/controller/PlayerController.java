package com.example.player_service.controller;

import com.example.player_service.model.Player;
import com.example.player_service.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping
    public Player createPlayer(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String classType = payload.get("classType");
        return playerService.createPlayer(name, classType);
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }
}
