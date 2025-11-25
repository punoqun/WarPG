package com.warpg.model;

/**
 * Item rarity levels affecting stats and drop rates
 */
public enum ItemRarity {
    COMMON("Common", 1.0),
    UNCOMMON("Uncommon", 1.5),
    RARE("Rare", 2.0),
    EPIC("Epic", 3.0),
    LEGENDARY("Legendary", 5.0),
    MYTHIC("Mythic", 10.0);
    
    private final String displayName;
    private final double statMultiplier;
    
    ItemRarity(String displayName, double statMultiplier) {
        this.displayName = displayName;
        this.statMultiplier = statMultiplier;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public double getStatMultiplier() {
        return statMultiplier;
    }
}
