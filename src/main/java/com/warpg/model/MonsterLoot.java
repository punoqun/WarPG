package com.warpg.model;

import jakarta.persistence.*;

/**
 * Defines loot drops for monsters
 */
@Entity
@Table(name = "monster_loot")
public class MonsterLoot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id", nullable = false)
    private Monster monster;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    private int dropChance; // Out of 10000
    private int minQuantity = 1;
    private int maxQuantity = 1;
    
    public MonsterLoot() {}
    
    public MonsterLoot(Monster monster, Item item, int dropChance) {
        this.monster = monster;
        this.item = item;
        this.dropChance = dropChance;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Monster getMonster() { return monster; }
    public void setMonster(Monster monster) { this.monster = monster; }
    
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    
    public int getDropChance() { return dropChance; }
    public void setDropChance(int dropChance) { this.dropChance = dropChance; }
    
    public int getMinQuantity() { return minQuantity; }
    public void setMinQuantity(int minQuantity) { this.minQuantity = minQuantity; }
    
    public int getMaxQuantity() { return maxQuantity; }
    public void setMaxQuantity(int maxQuantity) { this.maxQuantity = maxQuantity; }
}
