package com.warpg.model;

/**
 * Types of consumable items
 */
public enum ConsumableType {
    HEALTH_POTION("Health Potion"),
    MANA_POTION("Mana Potion"),
    BUFF_POTION("Buff Potion"),
    FOOD("Food");
    
    private final String displayName;
    
    ConsumableType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
