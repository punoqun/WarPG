package com.warpg.model;

import jakarta.persistence.*;

/**
 * Tracks equipped items by slot
 */
@Entity
@Table(name = "equipped_items")
public class EquippedItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentSlot slot;
    
    public EquippedItem() {}
    
    public EquippedItem(Player player, Item item, EquipmentSlot slot) {
        this.player = player;
        this.item = item;
        this.slot = slot;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    
    public EquipmentSlot getSlot() { return slot; }
    public void setSlot(EquipmentSlot slot) { this.slot = slot; }
}
