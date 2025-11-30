package com.example.player_service.service;

import com.example.player_service.service.factory.CharacterClassFactory;
import com.example.player_service.model.CharacterClass;
import com.example.player_service.model.Player;
import com.example.player_service.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CharacterClassFactory characterClassFactory;

    public Player createPlayer(String name, String classType) {
        CharacterClass charClass = characterClassFactory.createCharacterClass(classType);

        Player player = new Player.Builder()
                .setName(name)
                .setCharacterClass(charClass)
                .build();

        return playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
