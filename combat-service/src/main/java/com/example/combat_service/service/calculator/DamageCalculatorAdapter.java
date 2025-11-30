package com.example.combat_service.service.calculator;

import org.springframework.stereotype.Component;

@Component
public class DamageCalculatorAdapter {
    private final LegacyDamageCalculator legacyCalculator = new LegacyDamageCalculator();

    public int calculateDamage(int baseDamage) {
        // Adapting the interface: We only have baseDamage, so we assume weaponPower is
        // 0 or part of base
        return legacyCalculator.calculate(baseDamage, 5); // Adding a constant 5 as "weapon power"
    }
}
