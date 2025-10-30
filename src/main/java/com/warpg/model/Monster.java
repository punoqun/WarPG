package com.warpg.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Monster entity with stats and loot tables
 */
@Entity
@Table(name = "monsters")
public class Monster {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    private int level;
    private int health;
    private int maxHealth;
    private int attack;
    private int defense;
    private int magicPower;
    private int magicResist;
    
    private int experienceReward;
    private int goldReward;
    
    private int spawnChance = 100; // Out of 10000
    
    // Loot table removed temporarily to avoid circular reference issues
    // @OneToMany(mappedBy = "monster", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<MonsterLoot> lootTable = new ArrayList<>();
    
    public Monster() {}
    
    public Monster(String name, int level) {
        this.name = name;
        this.level = level;
        calculateStats();
    }
    
    private void calculateStats() {
        maxHealth = 50 + (level * 20);
        health = maxHealth;
        attack = 5 + (level * 3);
        defense = 5 + (level * 2);
        magicPower = 5 + (level * 2);
        magicResist = 5 + (level * 2);
        experienceReward = level * 25;
        goldReward = level * 10;
    }
    
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health = Math.max(0, health - actualDamage);
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public void resetHealth() {
        health = maxHealth;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getLevel() { return level; }
    public void setLevel(int level) { 
        this.level = level;
        calculateStats();
    }
    
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    
    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }
    
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    
    public int getMagicPower() { return magicPower; }
    public void setMagicPower(int magicPower) { this.magicPower = magicPower; }
    
    public int getMagicResist() { return magicResist; }
    public void setMagicResist(int magicResist) { this.magicResist = magicResist; }
    
    public int getExperienceReward() { return experienceReward; }
    public void setExperienceReward(int experienceReward) { 
        this.experienceReward = experienceReward; 
    }
    
    public int getGoldReward() { return goldReward; }
    public void setGoldReward(int goldReward) { this.goldReward = goldReward; }
    
    public int getSpawnChance() { return spawnChance; }
    public void setSpawnChance(int spawnChance) { this.spawnChance = spawnChance; }
    
    // public List<MonsterLoot> getLootTable() { return lootTable; }
    // public void setLootTable(List<MonsterLoot> lootTable) { this.lootTable = lootTable; }
}
