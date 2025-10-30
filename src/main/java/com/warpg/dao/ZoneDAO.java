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
        // Progressive difficulty: easier zones first, harder zones later
        // Also creates geographic variety and logical progression
        
        if (zoneNumber <= 2) {
            return ZoneType.GRASSLAND; // Starting area
        } else if (zoneNumber <= 4) {
            return zoneNumber % 2 == 0 ? ZoneType.FOREST : ZoneType.GRASSLAND;
        } else if (zoneNumber <= 6) {
            return zoneNumber % 2 == 0 ? ZoneType.DESERT : ZoneType.SWAMP;
        } else if (zoneNumber <= 8) {
            return zoneNumber % 2 == 0 ? ZoneType.MOUNTAIN : ZoneType.CAVE;
        } else if (zoneNumber <= 10) {
            return zoneNumber % 2 == 0 ? ZoneType.TUNDRA : ZoneType.RUINS;
        } else if (zoneNumber <= 15) {
            // Mid-game variety
            int mod = zoneNumber % 5;
            switch (mod) {
                case 0: return ZoneType.VOLCANO;
                case 1: return ZoneType.FOREST;
                case 2: return ZoneType.MOUNTAIN;
                case 3: return ZoneType.SWAMP;
                case 4: return ZoneType.CAVE;
                default: return ZoneType.RUINS;
            }
        } else {
            // End-game: dangerous zones more common
            int mod = zoneNumber % 4;
            switch (mod) {
                case 0: return ZoneType.DUNGEON;
                case 1: return ZoneType.VOLCANO;
                case 2: return ZoneType.RUINS;
                case 3: return ZoneType.DUNGEON;
                default: return ZoneType.DUNGEON;
            }
        }
    }
}
