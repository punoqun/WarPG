package com.warpg.dao;

import com.warpg.util.DatabaseManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

/**
 * Generic DAO providing basic CRUD operations with proper transaction management
 * @param <T> Entity type
 * @param <ID> ID type
 */
public abstract class GenericDAO<T, ID> {
    
    private final Class<T> entityClass;
    
    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    /**
     * Save an entity (insert or update)
     */
    public T save(T entity) {
        EntityManager em = DatabaseManager.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            T result = em.merge(entity);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error saving entity", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Find entity by ID
     */
    public Optional<T> findById(ID id) {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            em.close();
        }
    }
    
    /**
     * Find all entities
     */
    public List<T> findAll() {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(queryString, entityClass).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Delete an entity
     */
    public void delete(T entity) {
        EntityManager em = DatabaseManager.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            T merged = em.merge(entity);
            em.remove(merged);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error deleting entity", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Delete entity by ID
     */
    public void deleteById(ID id) {
        EntityManager em = DatabaseManager.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error deleting entity by ID", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Count all entities
     */
    public long count() {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            String queryString = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(queryString, Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
