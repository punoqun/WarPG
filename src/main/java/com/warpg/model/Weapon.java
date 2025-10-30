package com.warpg.model;

import jakarta.persistence.*;

/**
 * Weapon item with attack stats and weapon type
 */
@Entity
@DiscriminatorValue("WEAPON")
public class Weapon extends Item {
    
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    
    private int attackBonus;
    private int criticalChance; // Percentage
    private int criticalMultiplier = 2; // Default 2x damage on crit
    
    // Attribute bonuses
    private int strengthBonus = 0;
    private int dexterityBonus = 0;
    private int intelligenceBonus = 0;
    
    public Weapon() {
        super();
        setItemType(ItemType.WEAPON);
    }
    
    public Weapon(String name, String description, ItemRarity rarity, WeaponType weaponType, 
                  int attackBonus, int criticalChance) {
        super(name, description, rarity, ItemType.WEAPON, 0);
        this.weaponType = weaponType;
        this.attackBonus = attackBonus;
        this.criticalChance = criticalChance;
        calculateValue();
    }
    
    private void calculateValue() {
        int baseValue = 10;
        baseValue += attackBonus * 5;
        baseValue += criticalChance * 2;
        baseValue += strengthBonus * 3;
        baseValue += dexterityBonus * 3;
        baseValue += intelligenceBonus * 3;
        setValue((int)(baseValue * getRarity().getStatMultiplier()));
    }
    
    // Getters and setters
    public WeaponType getWeaponType() { return weaponType; }
    public void setWeaponType(WeaponType weaponType) { this.weaponType = weaponType; }
    
    public int getAttackBonus() { return attackBonus; }
    public void setAttackBonus(int attackBonus) { 
        this.attackBonus = attackBonus;
        calculateValue();
    }
    
    public int getCriticalChance() { return criticalChance; }
    public void setCriticalChance(int criticalChance) { this.criticalChance = criticalChance; }
    
    public int getCriticalMultiplier() { return criticalMultiplier; }
    public void setCriticalMultiplier(int criticalMultiplier) { 
        this.criticalMultiplier = criticalMultiplier; 
    }
    
    public int getStrengthBonus() { return strengthBonus; }
    public void setStrengthBonus(int strengthBonus) { this.strengthBonus = strengthBonus; }
    
    public int getDexterityBonus() { return dexterityBonus; }
    public void setDexterityBonus(int dexterityBonus) { this.dexterityBonus = dexterityBonus; }
    
    public int getIntelligenceBonus() { return intelligenceBonus; }
    public void setIntelligenceBonus(int intelligenceBonus) { 
        this.intelligenceBonus = intelligenceBonus; 
    }
    
    @Override
    public String toString() {
        return super.toString() + " " + weaponType.getDisplayName() + 
               " (+" + attackBonus + " Attack)";
    }
}
