package com.warpg.model;

/**
 * Character classes with different playstyles and bonuses
 */
public enum CharacterClass {
    WARRIOR("Warrior", "Master of melee combat with high strength and health"),
    MAGE("Mage", "Wielder of arcane magic with high intelligence and mana"),
    ROGUE("Rogue", "Agile fighter with high dexterity and luck"),
    CLERIC("Cleric", "Holy healer with balanced stats and support abilities"),
    RANGER("Ranger", "Skilled archer with high dexterity and wisdom");
    
    private final String displayName;
    private final String description;
    
    CharacterClass(String displayName, String description) {
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
