package com.warpg.model;

import jakarta.persistence.*;

/**
 * Consumable item (potions, food, etc.)
 */
@Entity
@DiscriminatorValue("CONSUMABLE")
public class Consumable extends Item {
    
    @Enumerated(EnumType.STRING)
    private ConsumableType consumableType;
    
    private int healthRestore = 0;
    private int manaRestore = 0;
    private int duration = 0; // For buffs, in seconds
    
    public Consumable() {
        super();
        setItemType(ItemType.CONSUMABLE);
    }
    
    public Consumable(String name, String description, ItemRarity rarity, 
                      ConsumableType type, int healthRestore, int manaRestore) {
        super(name, description, rarity, ItemType.CONSUMABLE, 0);
        this.consumableType = type;
        this.healthRestore = healthRestore;
        this.manaRestore = manaRestore;
        calculateValue();
    }
    
    private void calculateValue() {
        int baseValue = 5;
        baseValue += healthRestore / 2;
        baseValue += manaRestore / 2;
        setValue((int)(baseValue * getRarity().getStatMultiplier()));
    }
    
    // Getters and setters
    public ConsumableType getConsumableType() { return consumableType; }
    public void setConsumableType(ConsumableType consumableType) { 
        this.consumableType = consumableType; 
    }
    
    public int getHealthRestore() { return healthRestore; }
    public void setHealthRestore(int healthRestore) { this.healthRestore = healthRestore; }
    
    public int getManaRestore() { return manaRestore; }
    public void setManaRestore(int manaRestore) { this.manaRestore = manaRestore; }
    
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
}
