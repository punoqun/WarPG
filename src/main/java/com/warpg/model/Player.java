package com.warpg.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Player entity representing the player character with stats, inventory, and progression
 */
@Entity
@Table(name = "players")
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(nullable = false)
    private String passwordHash; // Stored as hash, never plain text
    
    @Column(nullable = false, length = 100)
    private String characterName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CharacterClass characterClass;
    
    private int level = 1;
    private int experience = 0;
    private int experienceToNextLevel = 100;
    
    // Base stats
    private int health = 100;
    private int maxHealth = 100;
    private int mana = 50;
    private int maxMana = 50;
    
    // Attributes
    private int strength = 10;
    private int dexterity = 10;
    private int intelligence = 10;
    private int constitution = 10;
    private int wisdom = 10;
    private int charisma = 10;
    private int luck = 10;
    
    // Derived stats
    private int attack = 10;
    private int defense = 10;
    private int magicPower = 10;
    private int magicResist = 10;
    
    private int gold = 100;
    
    // Location
    private int zoneId = 1;
    private int posX = 25;
    private int posY = 25;
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryItem> inventory = new ArrayList<>();
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquippedItem> equipment = new ArrayList<>();
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerQuest> quests = new ArrayList<>();
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerAchievement> achievements = new ArrayList<>();
    
    // Constructors
    public Player() {}
    
    public Player(String username, String passwordHash, String characterName, CharacterClass characterClass) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.characterName = characterName;
        this.characterClass = characterClass;
        applyClassBonuses();
    }
    
    private void applyClassBonuses() {
        switch (characterClass) {
            case WARRIOR:
                strength += 5;
                constitution += 3;
                maxHealth += 20;
                health += 20;
                break;
            case MAGE:
                intelligence += 5;
                wisdom += 3;
                maxMana += 30;
                mana += 30;
                break;
            case ROGUE:
                dexterity += 5;
                luck += 3;
                break;
            case CLERIC:
                wisdom += 5;
                constitution += 3;
                maxMana += 20;
                mana += 20;
                maxHealth += 10;
                health += 10;
                break;
            case RANGER:
                dexterity += 4;
                wisdom += 4;
                break;
        }
        recalculateStats();
    }
    
    public void recalculateStats() {
        // Recalculate derived stats from attributes
        attack = strength + (dexterity / 2);
        defense = constitution + (dexterity / 3);
        magicPower = intelligence + (wisdom / 2);
        magicResist = wisdom + (intelligence / 3);
        
        maxHealth = 100 + (constitution * 5) + (level * 10);
        maxMana = 50 + (intelligence * 3) + (wisdom * 2) + (level * 5);
        
        experienceToNextLevel = level * 100 + (level * level * 50);
    }
    
    public void gainExperience(int exp) {
        experience += exp;
        while (experience >= experienceToNextLevel) {
            levelUp();
        }
    }
    
    private void levelUp() {
        level++;
        experience -= experienceToNextLevel;
        
        // Stat increases
        strength++;
        dexterity++;
        intelligence++;
        constitution++;
        wisdom++;
        charisma++;
        
        recalculateStats();
        health = maxHealth;
        mana = maxMana;
    }
    
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health = Math.max(0, health - actualDamage);
    }
    
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }
    
    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public boolean canAfford(int cost) {
        return gold >= cost;
    }
    
    public void addGold(int amount) {
        gold += amount;
    }
    
    public void removeGold(int amount) {
        gold = Math.max(0, gold - amount);
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getCharacterName() { return characterName; }
    public void setCharacterName(String characterName) { this.characterName = characterName; }
    
    public CharacterClass getCharacterClass() { return characterClass; }
    public void setCharacterClass(CharacterClass characterClass) { this.characterClass = characterClass; }
    
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    
    public int getExperienceToNextLevel() { return experienceToNextLevel; }
    public void setExperienceToNextLevel(int experienceToNextLevel) { 
        this.experienceToNextLevel = experienceToNextLevel; 
    }
    
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    
    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }
    
    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = mana; }
    
    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }
    
    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }
    
    public int getDexterity() { return dexterity; }
    public void setDexterity(int dexterity) { this.dexterity = dexterity; }
    
    public int getIntelligence() { return intelligence; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
    
    public int getConstitution() { return constitution; }
    public void setConstitution(int constitution) { this.constitution = constitution; }
    
    public int getWisdom() { return wisdom; }
    public void setWisdom(int wisdom) { this.wisdom = wisdom; }
    
    public int getCharisma() { return charisma; }
    public void setCharisma(int charisma) { this.charisma = charisma; }
    
    public int getLuck() { return luck; }
    public void setLuck(int luck) { this.luck = luck; }
    
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    
    public int getMagicPower() { return magicPower; }
    public void setMagicPower(int magicPower) { this.magicPower = magicPower; }
    
    public int getMagicResist() { return magicResist; }
    public void setMagicResist(int magicResist) { this.magicResist = magicResist; }
    
    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }
    
    public int getZoneId() { return zoneId; }
    public void setZoneId(int zoneId) { this.zoneId = zoneId; }
    
    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }
    
    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }
    
    public List<InventoryItem> getInventory() { return inventory; }
    public void setInventory(List<InventoryItem> inventory) { this.inventory = inventory; }
    
    public List<EquippedItem> getEquipment() { return equipment; }
    public void setEquipment(List<EquippedItem> equipment) { this.equipment = equipment; }
    
    public List<PlayerQuest> getQuests() { return quests; }
    public void setQuests(List<PlayerQuest> quests) { this.quests = quests; }
    
    public List<PlayerAchievement> getAchievements() { return achievements; }
    public void setAchievements(List<PlayerAchievement> achievements) { 
        this.achievements = achievements; 
    }
}
