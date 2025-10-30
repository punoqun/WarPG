package com.warpg.util;

import com.warpg.dao.*;
import com.warpg.model.*;

/**
 * Seeds initial game data into the database
 */
public class DataSeeder {
    
    public static void seedInitialData() {
        seedMonsters();
        seedItems();
        seedQuests();
        seedAchievements();
    }
    
    private static void seedMonsters() {
        MonsterDAO monsterDAO = new MonsterDAO();
        
        // Check if monsters already exist
        if (monsterDAO.count() > 0) {
            return;
        }
        
        // Create basic monsters
        Monster goblin = new Monster("Goblin", 1);
        goblin.setDescription("A small, green creature with sharp teeth");
        monsterDAO.save(goblin);
        
        Monster orc = new Monster("Orc", 3);
        orc.setDescription("A large, brutish warrior");
        monsterDAO.save(orc);
        
        Monster troll = new Monster("Troll", 5);
        troll.setDescription("A massive beast with regenerating health");
        monsterDAO.save(troll);
        
        Monster dragon = new Monster("Dragon", 10);
        dragon.setDescription("A legendary fire-breathing dragon");
        dragon.setGoldReward(500);
        dragon.setExperienceReward(1000);
        monsterDAO.save(dragon);
        
        System.out.println("Seeded " + monsterDAO.count() + " monsters");
    }
    
    private static void seedItems() {
        ItemDAO itemDAO = new ItemDAO();
        
        // Check if items already exist
        if (itemDAO.count() > 0) {
            return;
        }
        
        // Create weapons
        Weapon sword = new Weapon("Iron Sword", "A basic iron sword", 
            ItemRarity.COMMON, WeaponType.SWORD, 10, 5);
        itemDAO.save(sword);
        
        Weapon mace = new Weapon("Steel Mace", "A heavy steel mace", 
            ItemRarity.UNCOMMON, WeaponType.MACE, 15, 3);
        itemDAO.save(mace);
        
        Weapon bow = new Weapon("Hunter's Bow", "A well-crafted hunting bow", 
            ItemRarity.RARE, WeaponType.BOW, 20, 10);
        itemDAO.save(bow);
        
        // Create armor
        Armor helmet = new Armor("Leather Helmet", "Basic leather head protection", 
            ItemRarity.COMMON, ArmorType.LEATHER, EquipmentSlot.HEAD, 5);
        itemDAO.save(helmet);
        
        Armor chestplate = new Armor("Iron Chestplate", "Sturdy iron chest armor", 
            ItemRarity.UNCOMMON, ArmorType.CHAINMAIL, EquipmentSlot.CHEST, 15);
        itemDAO.save(chestplate);
        
        // Create consumables
        Consumable healthPotion = new Consumable("Health Potion", "Restores 50 HP", 
            ItemRarity.COMMON, ConsumableType.HEALTH_POTION, 50, 0);
        itemDAO.save(healthPotion);
        
        Consumable manaPotion = new Consumable("Mana Potion", "Restores 30 MP", 
            ItemRarity.COMMON, ConsumableType.MANA_POTION, 0, 30);
        itemDAO.save(manaPotion);
        
        System.out.println("Seeded " + itemDAO.count() + " items");
    }
    
    private static void seedQuests() {
        GenericDAO<Quest, Long> questDAO = new GenericDAO<Quest, Long>(Quest.class) {};
        
        if (questDAO.count() > 0) {
            return;
        }
        
        Quest quest1 = new Quest("Goblin Slayer", 
            "Defeat 10 goblins to protect the village", 250, 100);
        questDAO.save(quest1);
        
        Quest quest2 = new Quest("Treasure Hunter", 
            "Find 5 treasure chests", 300, 150);
        questDAO.save(quest2);
        
        System.out.println("Seeded " + questDAO.count() + " quests");
    }
    
    private static void seedAchievements() {
        GenericDAO<Achievement, Long> achievementDAO = 
            new GenericDAO<Achievement, Long>(Achievement.class) {};
        
        if (achievementDAO.count() > 0) {
            return;
        }
        
        Achievement firstBlood = new Achievement("First Blood", 
            "Defeat your first monster", 50);
        achievementDAO.save(firstBlood);
        
        Achievement levelUp = new Achievement("Leveling Up", 
            "Reach level 5", 100);
        achievementDAO.save(levelUp);
        
        Achievement wealthy = new Achievement("Wealthy Adventurer", 
            "Accumulate 1000 gold", 200);
        achievementDAO.save(wealthy);
        
        System.out.println("Seeded " + achievementDAO.count() + " achievements");
    }
}
