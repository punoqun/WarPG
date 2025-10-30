package com.warpg.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Secure database manager using JPA/Hibernate
 * Replaces the insecure Connect class with proper connection pooling
 */
public class DatabaseManager {
    
    private static EntityManagerFactory emf;
    private static final String PERSISTENCE_UNIT_NAME = "WarPGPU";
    
    private DatabaseManager() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Initialize the database connection pool
     */
    public static void initialize() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
    }
    
    /**
     * Get an entity manager for database operations
     * @return EntityManager instance
     */
    public static EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            initialize();
        }
        return emf.createEntityManager();
    }
    
    /**
     * Close the entity manager factory
     */
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
    
    /**
     * Check if the database is initialized
     */
    public static boolean isInitialized() {
        return emf != null && emf.isOpen();
    }
}
