package com.warpg.model;

import jakarta.persistence.*;

/**
 * Zone entity representing different areas in the game world
 */
@Entity
@Table(name = "zones")
public class Zone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private int zoneNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ZoneType zoneType;
    
    @Column(nullable = false)
    private int minLevel;
    
    @Column(nullable = false)
    private int maxLevel;
    
    @Column(length = 100)
    private String customName; // Optional custom name for the zone
    
    public Zone() {}
    
    public Zone(int zoneNumber, ZoneType zoneType, int minLevel, int maxLevel) {
        this.zoneNumber = zoneNumber;
        this.zoneType = zoneType;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public int getZoneNumber() { return zoneNumber; }
    public void setZoneNumber(int zoneNumber) { this.zoneNumber = zoneNumber; }
    
    public ZoneType getZoneType() { return zoneType; }
    public void setZoneType(ZoneType zoneType) { this.zoneType = zoneType; }
    
    public int getMinLevel() { return minLevel; }
    public void setMinLevel(int minLevel) { this.minLevel = minLevel; }
    
    public int getMaxLevel() { return maxLevel; }
    public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel; }
    
    public String getCustomName() { return customName; }
    public void setCustomName(String customName) { this.customName = customName; }
    
    public String getDisplayName() {
        if (customName != null && !customName.isEmpty()) {
            return customName;
        }
        return zoneType.getDisplayName() + " (Zone " + zoneNumber + ")";
    }
    
    @Override
    public String toString() {
        return getDisplayName();
    }
}
