package com.example.combat_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Logging Aspect - Logs method entry, exit, and exceptions
 * Demonstrates AOP for cross-cutting logging concerns
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut for all service layer methods
     */
    @Pointcut("execution(* com.example.combat_service.service..*(..))")
    public void serviceMethods() {
    }

    /**
     * Pointcut for all controller methods
     */
    @Pointcut("execution(* com.example.combat_service.controller..*(..))")
    public void controllerMethods() {
    }

    /**
     * Around advice - logs method execution time and parameters
     */
    @Around("serviceMethods() || controllerMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("→ Entering: {} with arguments: {}", methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            logger.info("← Exiting: {} | Execution time: {}ms | Result: {}",
                    methodName, executionTime, result);

            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("✗ Exception in: {} | Execution time: {}ms | Error: {}",
                    methodName, executionTime, e.getMessage());
            throw e;
        }
    }

    /**
     * After returning advice - logs successful method completion
     */
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.debug("✓ Method {} completed successfully", joinPoint.getSignature().getName());
    }

    /**
     * After throwing advice - logs exceptions
     */
    @AfterThrowing(pointcut = "serviceMethods() || controllerMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("✗ Exception in method: {} | Error: {} | Message: {}",
                joinPoint.getSignature().getName(),
                error.getClass().getSimpleName(),
                error.getMessage());
    }
}
