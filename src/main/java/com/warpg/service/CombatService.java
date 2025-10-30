package com.warpg.service;

import com.warpg.model.*;
import java.util.Random;

/**
 * Enhanced combat system with better mechanics
 */
public class CombatService {
    
    private final Random random = new Random();
    
    public enum CombatResult {
        PLAYER_VICTORY,
        MONSTER_VICTORY,
        PLAYER_FLED
    }
    
    /**
     * Execute one round of combat
     */
    public CombatRound executeRound(Player player, Monster monster, boolean playerAttacks) {
        CombatRound round = new CombatRound();
        
        if (playerAttacks) {
            int damage = calculateDamage(player.getAttack(), monster.getDefense(), player.getLuck());
            boolean isCrit = random.nextInt(100) < (10 + player.getLuck() / 2);
            
            if (isCrit) {
                damage *= 2;
                round.setCritical(true);
            }
            
            monster.takeDamage(damage);
            round.setDamage(damage);
            round.setAttacker("Player");
            round.setHit(damage > 0);
        } else {
            int damage = calculateDamage(monster.getAttack(), player.getDefense(), 5);
            player.takeDamage(damage);
            round.setDamage(damage);
            round.setAttacker("Monster");
            round.setHit(damage > 0);
        }
        
        return round;
    }
    
    private int calculateDamage(int attack, int defense, int luck) {
        // Base hit chance: 70% + (attack - defense) * 2%
        int hitChance = 70 + ((attack - defense) * 2) + (luck / 2);
        hitChance = Math.min(95, Math.max(10, hitChance)); // Clamp between 10% and 95%
        
        if (random.nextInt(100) < hitChance) {
            // Hit! Calculate damage with variance
            int baseDamage = Math.max(1, attack - defense / 2);
            int variance = random.nextInt(Math.max(1, baseDamage / 4));
            return baseDamage + variance;
        }
        
        return 0; // Miss
    }
    
    /**
     * Attempt to flee from combat
     */
    public boolean attemptFlee(Player player, Monster monster) {
        int fleeChance = 50 + player.getLuck() - monster.getLevel() * 2;
        fleeChance = Math.min(90, Math.max(10, fleeChance));
        return random.nextInt(100) < fleeChance;
    }
    
    /**
     * Award experience and gold for defeating a monster
     */
    public void awardRewards(Player player, Monster monster) {
        int exp = monster.getExperienceReward();
        int gold = monster.getGoldReward();
        
        // Bonus for luck
        exp += exp * player.getLuck() / 100;
        gold += gold * player.getCharisma() / 100;
        
        player.gainExperience(exp);
        player.addGold(gold);
    }
    
    /**
     * Combat round result
     */
    public static class CombatRound {
        private String attacker;
        private int damage;
        private boolean hit;
        private boolean critical;
        
        public String getAttacker() { return attacker; }
        public void setAttacker(String attacker) { this.attacker = attacker; }
        
        public int getDamage() { return damage; }
        public void setDamage(int damage) { this.damage = damage; }
        
        public boolean isHit() { return hit; }
        public void setHit(boolean hit) { this.hit = hit; }
        
        public boolean isCritical() { return critical; }
        public void setCritical(boolean critical) { this.critical = critical; }
        
        public String getDescription() {
            if (!hit) {
                return attacker + " missed!";
            }
            if (critical) {
                return attacker + " landed a CRITICAL HIT for " + damage + " damage!";
            }
            return attacker + " dealt " + damage + " damage.";
        }
    }
}
