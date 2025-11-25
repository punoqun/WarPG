package com.warpg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "player_quests")
public class PlayerQuest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;
    
    private boolean completed = false;
    private int progress = 0;
    
    public PlayerQuest() {}
    
    public PlayerQuest(Player player, Quest quest) {
        this.player = player;
        this.quest = quest;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public Quest getQuest() { return quest; }
    public void setQuest(Quest quest) { this.quest = quest; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
}
