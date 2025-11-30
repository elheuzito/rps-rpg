package com.example.player_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Combined Logging and Audit Aspect for Player Service
 * Demonstrates AOP for cross-cutting concerns
 */
@Aspect
@Component
public class PlayerServiceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceAspect.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Pointcut for all service methods
     */
    @Pointcut("execution(* com.example.player_service.service..*(..))")
    public void serviceMethods() {
    }

    /**
     * Pointcut for player creation
     */
    @Pointcut("execution(* com.example.player_service.service.PlayerService.createPlayer(..))")
    public void playerCreation() {
    }

    /**
     * Around advice - logs method execution
     */
    @Around("serviceMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("→ Entering: {} with arguments: {}", methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            logger.info("← Exiting: {} | Execution time: {}ms", methodName, executionTime);

            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("✗ Exception in: {} | Execution time: {}ms | Error: {}",
                    methodName, executionTime, e.getMessage());
            throw e;
        }
    }

    /**
     * Audit player creation
     */
    @AfterReturning(pointcut = "playerCreation()", returning = "result")
    public void auditPlayerCreation(JoinPoint joinPoint, Object result) {
        String timestamp = LocalDateTime.now().format(formatter);
        Object[] args = joinPoint.getArgs();

        auditLogger.info("[{}] PLAYER_CREATED | Arguments: {} | Result: {}",
                timestamp, Arrays.toString(args), result);
    }

    /**
     * Validation for player creation
     */
    @Before("playerCreation() && args(name, characterClass)")
    public void validatePlayerCreation(String name, String characterClass) {
        if (name == null || name.trim().isEmpty()) {
            logger.error("Validation failed: Player name is null or empty");
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }

        if (name.length() > 50) {
            logger.error("Validation failed: Player name too long: {}", name.length());
            throw new IllegalArgumentException("Player name cannot exceed 50 characters");
        }

        logger.debug("✓ Validation passed: Player name '{}' is valid", name);
    }
}
