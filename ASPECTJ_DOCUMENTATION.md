# AspectJ Implementation - Cross-Cutting Concerns

This document describes the AspectJ (AOP) implementation across all three microservices.

## Overview

AspectJ has been integrated to handle cross-cutting concerns:
- **Logging** - Method entry/exit, execution time, and exceptions
- **Auditing** - Business operation tracking with timestamps
- **Validation** - Input parameter validation before method execution

## Architecture

### Combat Service

#### 1. LoggingAspect
**Location:** `combat-service/src/main/java/com/example/combat_service/aspect/LoggingAspect.java`

**Responsibilities:**
- Logs all service and controller method calls
- Tracks execution time for performance monitoring
- Captures method arguments and return values
- Logs exceptions with detailed error information

**Key Pointcuts:**
```java
@Pointcut("execution(* com.example.combat_service.service..*(..))")
public void serviceMethods() {}

@Pointcut("execution(* com.example.combat_service.controller..*(..))")
public void controllerMethods() {}
```

**Advice Types:**
- `@Around` - Wraps method execution for timing and logging
- `@AfterReturning` - Logs successful completions
- `@AfterThrowing` - Logs exceptions

#### 2. AuditAspect
**Location:** `combat-service/src/main/java/com/example/combat_service/aspect/AuditAspect.java`

**Responsibilities:**
- Creates audit trail for business operations
- Tracks combat actions, healing, game resets, and class selection
- Uses separate AUDIT logger for compliance

**Audit Events:**
- `COMBAT_ACTION` - Player moves and results
- `GAME_RESET` - New game started
- `HEAL_ACTION` - Potion usage
- `CLASS_SELECTED` - Character class selection

**Example Audit Log:**
```
[2025-11-30 11:30:45] COMBAT_ACTION | Player Move: [ROCK] | Result: CombatState{...}
[2025-11-30 11:31:12] HEAL_ACTION | Potion used | Result: CombatState{...}
```

#### 3. ValidationAspect
**Location:** `combat-service/src/main/java/com/example/combat_service/aspect/ValidationAspect.java`

**Responsibilities:**
- Validates player moves (ROCK, PAPER, SCISSORS)
- Validates character classes (Warrior, Mage)
- Throws `IllegalArgumentException` for invalid inputs

**Validation Rules:**
- Player moves must be one of: ROCK, PAPER, SCISSORS
- Character classes must be: Warrior or Mage
- Inputs cannot be null or empty

### Player Service

#### PlayerServiceAspect
**Location:** `player-service/src/main/java/com/example/player_service/aspect/PlayerServiceAspect.java`

**Responsibilities:**
- Combined logging, auditing, and validation
- Tracks player creation events
- Validates player names

**Validation Rules:**
- Player name cannot be null or empty
- Player name cannot exceed 50 characters

**Audit Events:**
- `PLAYER_CREATED` - New player registration

### Inventory Service

#### InventoryServiceAspect
**Location:** `inventory-service/src/main/java/com/example/inventory_service/aspect/InventoryServiceAspect.java`

**Responsibilities:**
- Combined logging, auditing, and validation
- Tracks item and bag creation
- Validates item properties

**Validation Rules:**
- Item name cannot be null or empty
- Weight cannot be negative
- Weight cannot exceed 1000kg

**Audit Events:**
- `ITEM_CREATED` - New item added
- `BAG_CREATED` - New bag created

## Benefits

### 1. Separation of Concerns
- Business logic remains clean and focused
- Cross-cutting concerns are centralized
- Easy to modify logging/auditing without touching business code

### 2. Maintainability
- Single location for logging configuration
- Consistent logging format across all services
- Easy to add new audit points

### 3. Performance Monitoring
- Automatic execution time tracking
- No manual timing code in business methods
- Easy to identify slow operations

### 4. Security & Compliance
- Complete audit trail of business operations
- Immutable audit logs with timestamps
- Easy to track who did what and when

### 5. Input Validation
- Centralized validation logic
- Consistent error messages
- Prevents invalid data from reaching business logic

## Usage Examples

### Logging Output
```
INFO  → Entering: CombatService.fightRound(..) with arguments: [ROCK]
INFO  ← Exiting: CombatService.fightRound(..) | Execution time: 15ms | Result: CombatState{...}
```

### Audit Output
```
INFO  [2025-11-30 11:30:45] COMBAT_ACTION | Player Move: [ROCK] | Result: CombatState{...}
INFO  [2025-11-30 11:31:00] PLAYER_CREATED | Arguments: [Hero, Warrior] | Result: Player{...}
```

### Validation Error
```
ERROR Validation failed: Invalid move 'STONE'. Valid moves: ROCK, PAPER, SCISSORS
IllegalArgumentException: Invalid move: STONE. Valid moves are: ROCK, PAPER, SCISSORS
```

## Configuration

AspectJ is enabled via Spring Boot AOP starter:

```gradle
implementation 'org.springframework.boot:spring-boot-starter-aop'
```

No additional configuration needed - aspects are auto-detected via `@Aspect` and `@Component` annotations.

## Testing

To see aspects in action:
1. Start all services
2. Create a player
3. Play combat rounds
4. Check console logs for:
   - Method entry/exit logs
   - Execution times
   - Audit trail entries
5. Try invalid inputs to see validation in action

## Future Enhancements

Potential additions:
- Performance metrics collection
- Security auditing (authentication/authorization)
- Transaction management
- Caching aspects
- Rate limiting
- Retry logic for failed operations
