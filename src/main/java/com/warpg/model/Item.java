package com.warpg.model;

import jakarta.persistence.*;

/**
 * Base item entity with proper JPA mapping
 */
@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemRarity rarity;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType itemType;
    
    private int value; // Gold value
    private int levelRequirement = 1;
    private int dropChance = 100; // Out of 10000
    
    private boolean tradeable = true;
    private boolean questItem = false;
    
    public Item() {}
    
    public Item(String name, String description, ItemRarity rarity, ItemType itemType, int value) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.itemType = itemType;
        this.value = value;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public ItemRarity getRarity() { return rarity; }
    public void setRarity(ItemRarity rarity) { this.rarity = rarity; }
    
    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }
    
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    
    public int getLevelRequirement() { return levelRequirement; }
    public void setLevelRequirement(int levelRequirement) { 
        this.levelRequirement = levelRequirement; 
    }
    
    public int getDropChance() { return dropChance; }
    public void setDropChance(int dropChance) { this.dropChance = dropChance; }
    
    public boolean isTradeable() { return tradeable; }
    public void setTradeable(boolean tradeable) { this.tradeable = tradeable; }
    
    public boolean isQuestItem() { return questItem; }
    public void setQuestItem(boolean questItem) { this.questItem = questItem; }
    
    @Override
    public String toString() {
        return rarity.getDisplayName() + " " + name;
    }
}
