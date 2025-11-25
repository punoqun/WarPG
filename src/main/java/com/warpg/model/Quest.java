package com.warpg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quests")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    private int experienceReward;
    private int goldReward;
    
    public Quest() {}
    
    public Quest(String name, String description, int expReward, int goldReward) {
        this.name = name;
        this.description = description;
        this.experienceReward = expReward;
        this.goldReward = goldReward;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getExperienceReward() { return experienceReward; }
    public void setExperienceReward(int experienceReward) { 
        this.experienceReward = experienceReward; 
    }
    public int getGoldReward() { return goldReward; }
    public void setGoldReward(int goldReward) { this.goldReward = goldReward; }
}
