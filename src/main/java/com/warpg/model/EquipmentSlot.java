package com.warpg.model;

/**
 * Equipment slots for armor and accessories
 */
public enum EquipmentSlot {
    HEAD("Head"),
    CHEST("Chest"),
    HANDS("Hands"),
    LEGS("Legs"),
    FEET("Feet"),
    MAIN_HAND("Main Hand"),
    OFF_HAND("Off Hand"),
    NECK("Neck"),
    RING_1("Ring 1"),
    RING_2("Ring 2");
    
    private final String displayName;
    
    EquipmentSlot(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
