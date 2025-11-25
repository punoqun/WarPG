package com.warpg.dao;

import com.warpg.model.Monster;
import com.warpg.util.DatabaseManager;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;

public class MonsterDAO extends GenericDAO<Monster, Long> {
    
    private final Random random = new Random();
    
    public MonsterDAO() {
        super(Monster.class);
    }
    
    public List<Monster> findByLevel(int minLevel, int maxLevel) {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            return em.createQuery(
                "SELECT m FROM Monster m WHERE m.level BETWEEN :minLevel AND :maxLevel", 
                Monster.class)
                .setParameter("minLevel", minLevel)
                .setParameter("maxLevel", maxLevel)
                .getResultList();
        } finally {
            em.close();
        }
    }
    
    public Monster getRandomMonster(int level) {
        List<Monster> monsters = findByLevel(Math.max(1, level - 1), level + 1);
        if (monsters.isEmpty()) {
            return null;
        }
        return monsters.get(random.nextInt(monsters.size()));
    }
    
    public List<Monster> findByZoneType(com.warpg.model.ZoneType zoneType, int level) {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            return em.createQuery(
                "SELECT m FROM Monster m WHERE m.preferredZone = :zoneType AND " +
                "m.level BETWEEN :minLevel AND :maxLevel", 
                Monster.class)
                .setParameter("zoneType", zoneType)
                .setParameter("minLevel", Math.max(1, level - 1))
                .setParameter("maxLevel", level + 2)
                .getResultList();
        } finally {
            em.close();
        }
    }
    
    public Monster getRandomMonsterForZone(com.warpg.model.ZoneType zoneType, int level) {
        List<Monster> zoneMonsters = findByZoneType(zoneType, level);
        
        // Fallback to general monsters if no zone-specific ones found
        if (zoneMonsters.isEmpty()) {
            return getRandomMonster(level);
        }
        
        return zoneMonsters.get(random.nextInt(zoneMonsters.size()));
    }
}
