package com.warpg.service;

import com.warpg.dao.MonsterDAO;
import com.warpg.dao.ZoneDAO;
import com.warpg.model.*;
import java.util.Random;

/**
 * Main game engine coordinating game mechanics
 */
public class GameEngine {
    
    private final PlayerService playerService;
    private final CombatService combatService;
    private final MonsterDAO monsterDAO;
    private final ZoneDAO zoneDAO;
    private final Random random;
    
    private Player currentPlayer;
    private GameMap currentMap;
    private Zone currentZone;
    
    public GameEngine() {
        this.playerService = new PlayerService();
        this.combatService = new CombatService();
        this.monsterDAO = new MonsterDAO();
        this.zoneDAO = new ZoneDAO();
        this.random = new Random();
        this.currentMap = new GameMap();
        this.currentZone = zoneDAO.getOrCreateZone(1);
    }
    
    /**
     * Register a new player
     */
    public Player registerPlayer(String username, String password, String characterName, 
                                 CharacterClass characterClass) {
        return playerService.registerPlayer(username, password, characterName, characterClass);
    }
    
    /**
     * Login player
     */
    public boolean loginPlayer(String username, String password) {
        var playerOpt = playerService.authenticatePlayer(username, password);
        if (playerOpt.isPresent()) {
            currentPlayer = playerOpt.get();
            return true;
        }
        return false;
    }
    
    /**
     * Get current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Get current zone
     */
    public Zone getCurrentZone() {
        if (currentZone == null && currentPlayer != null) {
            currentZone = zoneDAO.getOrCreateZone(currentPlayer.getZoneId());
        }
        return currentZone;
    }
    
    /**
     * Save current player
     */
    public void savePlayer() {
        if (currentPlayer != null) {
            playerService.savePlayer(currentPlayer);
        }
    }
    
    /**
     * Move player in direction
     */
    public MoveResult movePlayer(Direction direction) {
        if (currentPlayer == null) {
            return null;
        }
        
        int newX = currentPlayer.getPosX();
        int newY = currentPlayer.getPosY();
        
        switch (direction) {
            case NORTH:
                newY--;
                break;
            case SOUTH:
                newY++;
                break;
            case EAST:
                newX++;
                break;
            case WEST:
                newX--;
                break;
        }
        
        // Check boundaries
        if (newX < 0 || newX >= GameMap.MAP_SIZE || newY < 0 || newY >= GameMap.MAP_SIZE) {
            // Wrap around to new zone
            return handleZoneTransition();
        }
        
        currentPlayer.setPosX(newX);
        currentPlayer.setPosY(newY);
        
        // Check for encounters
        return checkEncounter(newX, newY);
    }
    
    private MoveResult handleZoneTransition() {
        currentPlayer.setZoneId(currentPlayer.getZoneId() + 1);
        currentPlayer.setPosX(GameMap.MAP_SIZE / 2);
        currentPlayer.setPosY(GameMap.MAP_SIZE / 2);
        currentMap = new GameMap();
        currentZone = zoneDAO.getOrCreateZone(currentPlayer.getZoneId());
        
        MoveResult result = new MoveResult();
        result.setEventType(EventType.ZONE_CHANGE);
        result.setMessage("You've entered " + currentZone.getDisplayName() + "!\n" + 
                         currentZone.getZoneType().getDescription());
        return result;
    }
    
    private MoveResult checkEncounter(int x, int y) {
        int encounter = currentMap.getTile(x, y);
        MoveResult result = new MoveResult();
        
        switch (encounter) {
            case 0: // Empty
                result.setEventType(EventType.NONE);
                result.setMessage("Nothing here...");
                break;
            case 1: // Monster
                // Get zone-specific monster template
                Monster template = monsterDAO.getRandomMonsterForZone(
                    currentZone.getZoneType(), currentPlayer.getLevel());
                if (template != null) {
                    // Create a fresh instance for this encounter (not from database)
                    Monster monster = new Monster(template.getName(), template.getLevel());
                    monster.setDescription(template.getDescription());
                    monster.setPreferredZone(template.getPreferredZone());
                    monster.setWeakness(template.getWeakness());
                    monster.setResistance(template.getResistance());
                    monster.setExperienceReward(template.getExperienceReward());
                    monster.setGoldReward(template.getGoldReward());
                    monster.setSpecialDropRate(template.getSpecialDropRate());
                    
                    result.setEventType(EventType.COMBAT);
                    result.setMonster(monster);
                    
                    String encounterMsg = "A " + monster.getName() + " appears!";
                    if (monster.getWeakness() != null) {
                        encounterMsg += "\n[Weak to: " + monster.getWeakness().getDisplayName() + "]";
                    }
                    result.setMessage(encounterMsg);
                    currentMap.clearTile(x, y);
                }
                break;
            case 2: // Treasure
                result.setEventType(EventType.TREASURE);
                int gold = random.nextInt(20 * currentPlayer.getLevel()) + 10;
                currentPlayer.addGold(gold);
                result.setMessage("You found " + gold + " gold!");
                currentMap.clearTile(x, y);
                break;
        }
        
        return result;
    }
    
    /**
     * Execute combat round
     */
    public CombatService.CombatRound executeCombatRound(Monster monster, boolean playerAttacks) {
        return combatService.executeRound(currentPlayer, monster, playerAttacks);
    }
    
    /**
     * Award combat rewards
     */
    public void awardCombatRewards(Monster monster) {
        combatService.awardRewards(currentPlayer, monster);
    }
    
    /**
     * Attempt to flee from combat
     */
    public boolean attemptFlee(Monster monster) {
        return combatService.attemptFlee(currentPlayer, monster);
    }
    
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
    
    public enum EventType {
        NONE, COMBAT, TREASURE, ZONE_CHANGE
    }
    
    public static class MoveResult {
        private EventType eventType;
        private String message;
        private Monster monster;
        
        public EventType getEventType() { return eventType; }
        public void setEventType(EventType eventType) { this.eventType = eventType; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public Monster getMonster() { return monster; }
        public void setMonster(Monster monster) { this.monster = monster; }
    }
    
    /**
     * Simple game map
     */
    private static class GameMap {
        public static final int MAP_SIZE = 50;
        private final int[][] tiles = new int[MAP_SIZE][MAP_SIZE];
        private final Random random = new Random();
        
        public GameMap() {
            generateMap();
        }
        
        private void generateMap() {
            // Fill with empty tiles
            for (int x = 0; x < MAP_SIZE; x++) {
                for (int y = 0; y < MAP_SIZE; y++) {
                    tiles[x][y] = 0;
                }
            }
            
            // Add monsters (1)
            for (int i = 0; i < 80; i++) {
                int x = random.nextInt(MAP_SIZE);
                int y = random.nextInt(MAP_SIZE);
                if (tiles[x][y] == 0) {
                    tiles[x][y] = 1;
                }
            }
            
            // Add treasure (2)
            for (int i = 0; i < 40; i++) {
                int x = random.nextInt(MAP_SIZE);
                int y = random.nextInt(MAP_SIZE);
                if (tiles[x][y] == 0) {
                    tiles[x][y] = 2;
                }
            }
        }
        
        public int getTile(int x, int y) {
            if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
                return tiles[x][y];
            }
            return -1;
        }
        
        public void clearTile(int x, int y) {
            if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
                tiles[x][y] = 0;
            }
        }
    }
}
