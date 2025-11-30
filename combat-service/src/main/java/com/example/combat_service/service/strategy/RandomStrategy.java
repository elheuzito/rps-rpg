package com.example.combat_service.service.strategy;

import java.util.Random;

public class RandomStrategy implements CombatStrategy {
    private final Random random = new Random();
    private final String[] MOVES = { "ROCK", "PAPER", "SCISSORS" };

    @Override
    public String nextMove() {
        return MOVES[random.nextInt(MOVES.length)];
    }
}
