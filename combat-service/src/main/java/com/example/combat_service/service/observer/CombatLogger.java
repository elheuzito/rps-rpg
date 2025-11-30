package com.example.combat_service.service.observer;

import org.springframework.stereotype.Component;

@Component
public class CombatLogger implements CombatObserver {
    @Override
    public void onCombatEvent(String event) {
        System.out.println("[COMBAT LOG]: " + event);
    }
}
