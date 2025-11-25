package com.warpg.model;

import jakarta.persistence.*;

/**
 * Armor item with defense stats
 */
@Entity
@DiscriminatorValue("ARMOR")
public class Armor extends Item {
    
    @Enumerated(EnumType.STRING)
    private ArmorType armorType;
    
    @Enumerated(EnumType.STRING)
    private EquipmentSlot equipmentSlot;
    
    private int defenseBonus;
    private int magicResistBonus = 0;
    
    // Attribute bonuses
    private int constitutionBonus = 0;
    private int strengthBonus = 0;
    private int dexterityBonus = 0;
    
    public Armor() {
        super();
        setItemType(ItemType.ARMOR);
    }
    
    public Armor(String name, String description, ItemRarity rarity, ArmorType armorType,
                 EquipmentSlot slot, int defenseBonus) {
        super(name, description, rarity, ItemType.ARMOR, 0);
        this.armorType = armorType;
        this.equipmentSlot = slot;
        this.defenseBonus = defenseBonus;
        calculateValue();
    }
    
    private void calculateValue() {
        int baseValue = 10;
        baseValue += defenseBonus * 4;
        baseValue += magicResistBonus * 3;
        baseValue += constitutionBonus * 3;
        baseValue += strengthBonus * 3;
        baseValue += dexterityBonus * 3;
        setValue((int)(baseValue * getRarity().getStatMultiplier()));
    }
    
    // Getters and setters
    public ArmorType getArmorType() { return armorType; }
    public void setArmorType(ArmorType armorType) { this.armorType = armorType; }
    
    public EquipmentSlot getEquipmentSlot() { return equipmentSlot; }
    public void setEquipmentSlot(EquipmentSlot equipmentSlot) { 
        this.equipmentSlot = equipmentSlot; 
    }
    
    public int getDefenseBonus() { return defenseBonus; }
    public void setDefenseBonus(int defenseBonus) { 
        this.defenseBonus = defenseBonus;
        calculateValue();
    }
    
    public int getMagicResistBonus() { return magicResistBonus; }
    public void setMagicResistBonus(int magicResistBonus) { 
        this.magicResistBonus = magicResistBonus; 
    }
    
    public int getConstitutionBonus() { return constitutionBonus; }
    public void setConstitutionBonus(int constitutionBonus) { 
        this.constitutionBonus = constitutionBonus; 
    }
    
    public int getStrengthBonus() { return strengthBonus; }
    public void setStrengthBonus(int strengthBonus) { this.strengthBonus = strengthBonus; }
    
    public int getDexterityBonus() { return dexterityBonus; }
    public void setDexterityBonus(int dexterityBonus) { this.dexterityBonus = dexterityBonus; }
    
    @Override
    public String toString() {
        return super.toString() + " " + armorType.getDisplayName() + 
               " (+" + defenseBonus + " Defense)";
    }
}
