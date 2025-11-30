package com.example.combat_service.service.command;

public class AttackCommand implements CombatCommand {
    private final String playerMove;
    private final String enemyMove;

    public AttackCommand(String playerMove, String enemyMove) {
        this.playerMove = playerMove;
        this.enemyMove = enemyMove;
    }

    @Override
    public String execute() {
        if (playerMove.equals(enemyMove)) {
            return "DRAW";
        }
        if ((playerMove.equals("ROCK") && enemyMove.equals("SCISSORS")) ||
                (playerMove.equals("PAPER") && enemyMove.equals("ROCK")) ||
                (playerMove.equals("SCISSORS") && enemyMove.equals("PAPER"))) {
            return "VICTORY";
        }
        return "DEFEAT";
    }
}
