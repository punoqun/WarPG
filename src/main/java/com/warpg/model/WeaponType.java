package com.warpg.model;

/**
 * Different weapon types with different characteristics
 */
public enum WeaponType {
    SWORD("Sword", "Balanced weapon for versatile combat"),
    DAGGER("Dagger", "Fast, light weapon with high critical chance"),
    MACE("Mace", "Heavy crushing weapon"),
    AXE("Axe", "Powerful cleaving weapon"),
    SPEAR("Spear", "Long reach piercing weapon"),
    BOW("Bow", "Ranged weapon requiring dexterity"),
    CROSSBOW("Crossbow", "Powerful ranged weapon"),
    STAFF("Staff", "Magical weapon amplifying spell power"),
    WAND("Wand", "Quick magical weapon for spellcasters"),
    HAMMER("Hammer", "Devastating blunt weapon");
    
    private final String displayName;
    private final String description;
    
    WeaponType(String displayName, String description) {
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
