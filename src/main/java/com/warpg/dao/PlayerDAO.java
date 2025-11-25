package com.warpg.dao;

import com.warpg.model.Player;
import com.warpg.util.DatabaseManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.Optional;

/**
 * DAO for Player entity with secure queries using parameterized statements
 */
public class PlayerDAO extends GenericDAO<Player, Long> {
    
    public PlayerDAO() {
        super(Player.class);
    }
    
    /**
     * Find player by username (secure, no SQL injection risk)
     */
    public Optional<Player> findByUsername(String username) {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            Player player = em.createQuery(
                "SELECT p FROM Player p WHERE p.username = :username", Player.class)
                .setParameter("username", username)
                .getSingleResult();
            return Optional.of(player);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(p) FROM Player p WHERE p.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
