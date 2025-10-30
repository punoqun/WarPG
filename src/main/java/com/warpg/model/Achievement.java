package com.warpg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    private String description;
    private int rewardGold;
    
    public Achievement() {}
    
    public Achievement(String name, String description, int rewardGold) {
        this.name = name;
        this.description = description;
        this.rewardGold = rewardGold;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getRewardGold() { return rewardGold; }
    public void setRewardGold(int rewardGold) { this.rewardGold = rewardGold; }
}
