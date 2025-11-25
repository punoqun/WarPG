package com.warpg.dao;

import com.warpg.model.Item;
import com.warpg.model.ItemType;
import com.warpg.util.DatabaseManager;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ItemDAO extends GenericDAO<Item, Long> {
    
    public ItemDAO() {
        super(Item.class);
    }
    
    public List<Item> findByType(ItemType type) {
        EntityManager em = DatabaseManager.getEntityManager();
        try {
            return em.createQuery(
                "SELECT i FROM Item i WHERE i.itemType = :type", Item.class)
                .setParameter("type", type)
                .getResultList();
        } finally {
            em.close();
        }
    }
}
