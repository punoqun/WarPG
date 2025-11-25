package com.warpg.service;

import com.warpg.dao.PlayerDAO;
import com.warpg.model.CharacterClass;
import com.warpg.model.Player;
import com.warpg.util.PasswordUtil;
import java.util.Optional;

/**
 * Service layer for player operations with secure authentication
 */
public class PlayerService {
    
    private final PlayerDAO playerDAO;
    
    public PlayerService() {
        this.playerDAO = new PlayerDAO();
    }
    
    /**
     * Register a new player with secure password hashing
     */
    public Player registerPlayer(String username, String password, String characterName, 
                                 CharacterClass characterClass) {
        // Check if username already exists
        if (playerDAO.usernameExists(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        // Hash password securely
        String passwordHash = PasswordUtil.hashPassword(password);
        
        // Create and save player
        Player player = new Player(username, passwordHash, characterName, characterClass);
        return playerDAO.save(player);
    }
    
    /**
     * Authenticate player with secure password verification
     */
    public Optional<Player> authenticatePlayer(String username, String password) {
        Optional<Player> playerOpt = playerDAO.findByUsername(username);
        
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            if (PasswordUtil.verifyPassword(password, player.getPasswordHash())) {
                return Optional.of(player);
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Save player progress
     */
    public Player savePlayer(Player player) {
        player.recalculateStats();
        return playerDAO.save(player);
    }
    
    /**
     * Load player by username
     */
    public Optional<Player> loadPlayer(String username) {
        return playerDAO.findByUsername(username);
    }
}
