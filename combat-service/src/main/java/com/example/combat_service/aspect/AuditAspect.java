package com.example.combat_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Audit Aspect - Tracks important business operations
 * Demonstrates AOP for audit trail logging
 */
@Aspect
@Component
public class AuditAspect {

    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Pointcut for combat-related operations
     */
    @Pointcut("execution(* com.example.combat_service.service.CombatService.fightRound(..))")
    public void fightOperations() {
    }

    /**
     * Pointcut for game state changes
     */
    @Pointcut("execution(* com.example.combat_service.service.CombatService.resetGame(..))")
    public void gameStateChanges() {
    }

    /**
     * Pointcut for healing operations
     */
    @Pointcut("execution(* com.example.combat_service.service.CombatService.healPlayer(..))")
    public void healOperations() {
    }

    /**
     * Pointcut for class selection
     */
    @Pointcut("execution(* com.example.combat_service.service.CombatService.setPlayerClass(..))")
    public void classSelection() {
    }

    /**
     * Audit fight operations
     */
    @AfterReturning(pointcut = "fightOperations()", returning = "result")
    public void auditFight(JoinPoint joinPoint, Object result) {
        String timestamp = LocalDateTime.now().format(formatter);
        Object[] args = joinPoint.getArgs();

        auditLogger.info("[{}] COMBAT_ACTION | Player Move: {} | Result: {}",
                timestamp, Arrays.toString(args), result);
    }

    /**
     * Audit game resets
     */
    @AfterReturning("gameStateChanges()")
    public void auditGameReset(JoinPoint joinPoint) {
        String timestamp = LocalDateTime.now().format(formatter);
        auditLogger.info("[{}] GAME_RESET | New game started", timestamp);
    }

    /**
     * Audit healing operations
     */
    @AfterReturning(pointcut = "healOperations()", returning = "result")
    public void auditHeal(JoinPoint joinPoint, Object result) {
        String timestamp = LocalDateTime.now().format(formatter);
        auditLogger.info("[{}] HEAL_ACTION | Potion used | Result: {}", timestamp, result);
    }

    /**
     * Audit class selection
     */
    @AfterReturning("classSelection()")
    public void auditClassSelection(JoinPoint joinPoint) {
        String timestamp = LocalDateTime.now().format(formatter);
        Object[] args = joinPoint.getArgs();
        auditLogger.info("[{}] CLASS_SELECTED | Class: {}", timestamp, Arrays.toString(args));
    }
}
