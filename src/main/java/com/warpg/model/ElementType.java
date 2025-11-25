package com.warpg.model;

/**
 * Element types for monster weaknesses and resistances
 */
public enum ElementType {
    PHYSICAL("Physical"),
    FIRE("Fire"),
    ICE("Ice"),
    LIGHTNING("Lightning"),
    POISON("Poison"),
    HOLY("Holy"),
    DARK("Dark"),
    NATURE("Nature");
    
    private final String displayName;
    
    ElementType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
