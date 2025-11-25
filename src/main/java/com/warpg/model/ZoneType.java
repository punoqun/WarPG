package com.warpg.model;

/**
 * Different zone types with unique characteristics
 */
public enum ZoneType {
    GRASSLAND("Grassland", "Rolling green hills with gentle winds", "🌾", "Green"),
    FOREST("Forest", "Dense woodland with towering trees", "🌲", "Dark Green"),
    DESERT("Desert", "Scorching sands under a blazing sun", "🏜️", "Yellow"),
    SWAMP("Swamp", "Murky wetlands with poisonous vapors", "🌿", "Dark Green"),
    MOUNTAIN("Mountain", "Rocky peaks with treacherous cliffs", "⛰️", "Gray"),
    TUNDRA("Tundra", "Frozen wasteland with biting winds", "❄️", "Cyan"),
    VOLCANO("Volcano", "Lava flows and scorching heat", "🌋", "Red"),
    CAVE("Cave", "Dark underground passages", "🕳️", "Black"),
    RUINS("Ruins", "Ancient structures from a lost civilization", "🏛️", "Gray"),
    DUNGEON("Dungeon", "Perilous depths filled with evil", "⚔️", "Purple");
    
    private final String displayName;
    private final String description;
    private final String emoji;
    private final String color;
    
    ZoneType(String displayName, String description, String emoji, String color) {
        this.displayName = displayName;
        this.description = description;
        this.emoji = emoji;
        this.color = color;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getEmoji() {
        return emoji;
    }
    
    public String getColor() {
        return color;
    }
    
    /**
     * Get ANSI color code for terminal display
     */
    public String getAnsiColor() {
        switch (this) {
            case GRASSLAND: return "\u001B[32m"; // Green
            case FOREST: return "\u001B[92m"; // Bright Green (distinct from swamp)
            case DESERT: return "\u001B[33m"; // Yellow
            case SWAMP: return "\u001B[32;2m"; // Dark Green
            case MOUNTAIN: return "\u001B[37m"; // White/Gray
            case TUNDRA: return "\u001B[36m"; // Cyan
            case VOLCANO: return "\u001B[31m"; // Red
            case CAVE: return "\u001B[30;1m"; // Bright Black
            case RUINS: return "\u001B[37;2m"; // Dark Gray
            case DUNGEON: return "\u001B[35m"; // Purple
            default: return "\u001B[0m"; // Reset
        }
    }
    
    public static String getResetCode() {
        return "\u001B[0m";
    }
}
