package com.warpg.model;

import jakarta.persistence.*;

/**
 * Links a player to an item in their inventory with quantity
 */
@Entity
@Table(name = "inventory_items")
public class InventoryItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    private int quantity = 1;
    
    public InventoryItem() {}
    
    public InventoryItem(Player player, Item item, int quantity) {
        this.player = player;
        this.item = item;
        this.quantity = quantity;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
