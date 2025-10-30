package com.warpg.dao;

import com.warpg.model.Zone;
import com.warpg.model.ZoneType;
import com.warpg.util.DatabaseManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.Optional;

public class ZoneDAO extends GenericDAO<Zone, Long> {
    
    public ZoneDAO() {
        super(Zone.class);
    }
    
    public Optional<Zone> findByZoneNumber(int zoneNumber) {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            Zone zone = em.createQuery(
                "SELECT z FROM Zone z WHERE z.zoneNumber = :zoneNumber", Zone.class)
                .setParameter("zoneNumber", zoneNumber)
                .getSingleResult();
            return Optional.of(zone);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    public Zone getOrCreateZone(int zoneNumber) {
        Optional<Zone> existing = findByZoneNumber(zoneNumber);
        if (existing.isPresent()) {
            return existing.get();
        }
        
        // Create a new zone with type based on zone number
        ZoneType type = determineZoneType(zoneNumber);
        int minLevel = Math.max(1, (zoneNumber - 1) * 2);
        int maxLevel = minLevel + 3;
        
        Zone zone = new Zone(zoneNumber, type, minLevel, maxLevel);
        return save(zone);
    }
    
    private ZoneType determineZoneType(int zoneNumber) {
        ZoneType[] types = ZoneType.values();
        // Cycle through zone types, with some logic
        int index = (zoneNumber - 1) % types.length;
        return types[index];
    }
}
