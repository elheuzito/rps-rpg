package com.example.inventory_service.aspect;

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
 * Combined Logging and Audit Aspect for Inventory Service
 * Demonstrates AOP for cross-cutting concerns
 */
@Aspect
@Component
public class InventoryServiceAspect {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceAspect.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Pointcut for all service methods
     */
    @Pointcut("execution(* com.example.inventory_service.service..*(..))")
    public void serviceMethods() {
    }

    /**
     * Pointcut for item creation
     */
    @Pointcut("execution(* com.example.inventory_service.service.InventoryService.createItem(..))")
    public void itemCreation() {
    }

    /**
     * Pointcut for bag creation
     */
    @Pointcut("execution(* com.example.inventory_service.service.InventoryService.createBag(..))")
    public void bagCreation() {
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
     * Audit item creation
     */
    @AfterReturning(pointcut = "itemCreation()", returning = "result")
    public void auditItemCreation(JoinPoint joinPoint, Object result) {
        String timestamp = LocalDateTime.now().format(formatter);
        Object[] args = joinPoint.getArgs();

        auditLogger.info("[{}] ITEM_CREATED | Arguments: {} | Result: {}",
                timestamp, Arrays.toString(args), result);
    }

    /**
     * Audit bag creation
     */
    @AfterReturning(pointcut = "bagCreation()", returning = "result")
    public void auditBagCreation(JoinPoint joinPoint, Object result) {
        String timestamp = LocalDateTime.now().format(formatter);
        Object[] args = joinPoint.getArgs();

        auditLogger.info("[{}] BAG_CREATED | Arguments: {} | Result: {}",
                timestamp, Arrays.toString(args), result);
    }

    /**
     * Validation for item/bag creation
     */
    @Before("(itemCreation() || bagCreation()) && args(name, weight)")
    public void validateItemCreation(String name, double weight) {
        if (name == null || name.trim().isEmpty()) {
            logger.error("Validation failed: Item name is null or empty");
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }

        if (weight < 0) {
            logger.error("Validation failed: Negative weight: {}", weight);
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        if (weight > 1000) {
            logger.error("Validation failed: Weight too large: {}", weight);
            throw new IllegalArgumentException("Weight cannot exceed 1000kg");
        }

        logger.debug("✓ Validation passed: Item '{}' with weight {} is valid", name, weight);
    }
}
