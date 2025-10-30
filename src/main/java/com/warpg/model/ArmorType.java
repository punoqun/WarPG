package com.warpg.model;

/**
 * Types of armor materials
 */
public enum ArmorType {
    CLOTH("Cloth", "Light armor for mages"),
    LEATHER("Leather", "Medium armor for rogues"),
    CHAINMAIL("Chainmail", "Heavy armor for warriors"),
    PLATE("Plate", "Heaviest armor with maximum protection");
    
    private final String displayName;
    private final String description;
    
    ArmorType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
}
