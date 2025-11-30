package com.example.combat_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Validation Aspect - Validates input parameters
 * Demonstrates AOP for input validation cross-cutting concern
 */
@Aspect
@Component
public class ValidationAspect {

    private static final Logger logger = LoggerFactory.getLogger(ValidationAspect.class);

    /**
     * Pointcut for fight operations
     */
    @Pointcut("execution(* com.example.combat_service.service.CombatService.fightRound(..)) && args(playerMove)")
    public void fightWithMove(String playerMove) {
    }

    /**
     * Pointcut for class selection
     */
    @Pointcut("execution(* com.example.combat_service.service.CombatService.setPlayerClass(..)) && args(characterClass)")
    public void classSelection(String characterClass) {
    }

    /**
     * Validate player move before fight
     */
    @Before("fightWithMove(playerMove)")
    public void validatePlayerMove(JoinPoint joinPoint, String playerMove) {
        if (playerMove == null || playerMove.trim().isEmpty()) {
            logger.error("Validation failed: Player move is null or empty");
            throw new IllegalArgumentException("Player move cannot be null or empty");
        }

        String[] validMoves = { "ROCK", "PAPER", "SCISSORS" };
        boolean isValid = false;
        for (String validMove : validMoves) {
            if (validMove.equals(playerMove.toUpperCase())) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            logger.error("Validation failed: Invalid move '{}'. Valid moves: ROCK, PAPER, SCISSORS", playerMove);
            throw new IllegalArgumentException(
                    "Invalid move: " + playerMove + ". Valid moves are: ROCK, PAPER, SCISSORS");
        }

        logger.debug("✓ Validation passed: Player move '{}' is valid", playerMove);
    }

    /**
     * Validate character class before setting
     */
    @Before("classSelection(characterClass)")
    public void validateCharacterClass(JoinPoint joinPoint, String characterClass) {
        if (characterClass == null || characterClass.trim().isEmpty()) {
            logger.error("Validation failed: Character class is null or empty");
            throw new IllegalArgumentException("Character class cannot be null or empty");
        }

        String[] validClasses = { "Warrior", "Mage" };
        boolean isValid = false;
        for (String validClass : validClasses) {
            if (validClass.equalsIgnoreCase(characterClass)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            logger.error("Validation failed: Invalid class '{}'. Valid classes: Warrior, Mage", characterClass);
            throw new IllegalArgumentException(
                    "Invalid character class: " + characterClass + ". Valid classes are: Warrior, Mage");
        }

        logger.debug("✓ Validation passed: Character class '{}' is valid", characterClass);
    }
}
