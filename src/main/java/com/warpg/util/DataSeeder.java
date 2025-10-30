package com.warpg.util;

import com.warpg.dao.*;
import com.warpg.model.*;
import com.warpg.model.ZoneType;
import com.warpg.model.ElementType;

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
        
        // GRASSLAND monsters
        Monster goblin = new Monster("Goblin", 1);
        goblin.setDescription("A small, green creature with sharp teeth");
        goblin.setPreferredZone(ZoneType.GRASSLAND);
        goblin.setWeakness(ElementType.FIRE);
        goblin.setResistance(ElementType.NATURE);
        goblin.setSpecialDropRate(10);
        monsterDAO.save(goblin);
        
        Monster wolf = new Monster("Wolf", 2);
        wolf.setDescription("A fierce grey wolf with sharp fangs");
        wolf.setPreferredZone(ZoneType.GRASSLAND);
        wolf.setWeakness(ElementType.PHYSICAL);
        wolf.setResistance(ElementType.ICE);
        monsterDAO.save(wolf);
        
        // FOREST monsters
        Monster treant = new Monster("Treant", 3);
        treant.setDescription("An ancient tree come to life");
        treant.setPreferredZone(ZoneType.FOREST);
        treant.setWeakness(ElementType.FIRE);
        treant.setResistance(ElementType.NATURE);
        treant.setSpecialDropRate(15);
        monsterDAO.save(treant);
        
        Monster darkElf = new Monster("Dark Elf", 4);
        darkElf.setDescription("A sinister forest dweller with dark magic");
        darkElf.setPreferredZone(ZoneType.FOREST);
        darkElf.setWeakness(ElementType.HOLY);
        darkElf.setResistance(ElementType.DARK);
        monsterDAO.save(darkElf);
        
        // DESERT monsters
        Monster scorpion = new Monster("Giant Scorpion", 3);
        scorpion.setDescription("A massive scorpion with venomous stinger");
        scorpion.setPreferredZone(ZoneType.DESERT);
        scorpion.setWeakness(ElementType.ICE);
        scorpion.setResistance(ElementType.FIRE);
        scorpion.setSpecialDropRate(20);
        monsterDAO.save(scorpion);
        
        Monster sandWorm = new Monster("Sand Worm", 5);
        sandWorm.setDescription("A colossal worm that burrows through the sand");
        sandWorm.setPreferredZone(ZoneType.DESERT);
        sandWorm.setWeakness(ElementType.ICE);
        sandWorm.setResistance(ElementType.PHYSICAL);
        monsterDAO.save(sandWorm);
        
        // SWAMP monsters
        Monster slime = new Monster("Toxic Slime", 2);
        slime.setDescription("A bubbling mass of poisonous goo");
        slime.setPreferredZone(ZoneType.SWAMP);
        slime.setWeakness(ElementType.FIRE);
        slime.setResistance(ElementType.POISON);
        monsterDAO.save(slime);
        
        Monster swampTroll = new Monster("Swamp Troll", 5);
        swampTroll.setDescription("A massive troll covered in moss and slime");
        swampTroll.setPreferredZone(ZoneType.SWAMP);
        swampTroll.setWeakness(ElementType.FIRE);
        swampTroll.setResistance(ElementType.POISON);
        swampTroll.setSpecialDropRate(25);
        monsterDAO.save(swampTroll);
        
        // MOUNTAIN monsters
        Monster griffin = new Monster("Griffin", 6);
        griffin.setDescription("A majestic creature with eagle wings and lion body");
        griffin.setPreferredZone(ZoneType.MOUNTAIN);
        griffin.setWeakness(ElementType.LIGHTNING);
        griffin.setResistance(ElementType.PHYSICAL);
        griffin.setSpecialDropRate(30);
        monsterDAO.save(griffin);
        
        Monster stoneGolem = new Monster("Stone Golem", 7);
        stoneGolem.setDescription("An animated construct of solid rock");
        stoneGolem.setPreferredZone(ZoneType.MOUNTAIN);
        stoneGolem.setWeakness(ElementType.LIGHTNING);
        stoneGolem.setResistance(ElementType.PHYSICAL);
        monsterDAO.save(stoneGolem);
        
        // TUNDRA monsters
        Monster iceElemental = new Monster("Ice Elemental", 6);
        iceElemental.setDescription("A being of pure frozen energy");
        iceElemental.setPreferredZone(ZoneType.TUNDRA);
        iceElemental.setWeakness(ElementType.FIRE);
        iceElemental.setResistance(ElementType.ICE);
        monsterDAO.save(iceElemental);
        
        Monster frostGiant = new Monster("Frost Giant", 8);
        frostGiant.setDescription("A towering giant from the frozen wastes");
        frostGiant.setPreferredZone(ZoneType.TUNDRA);
        frostGiant.setWeakness(ElementType.FIRE);
        frostGiant.setResistance(ElementType.ICE);
        frostGiant.setSpecialDropRate(35);
        monsterDAO.save(frostGiant);
        
        // VOLCANO monsters
        Monster magmaElemental = new Monster("Magma Elemental", 7);
        magmaElemental.setDescription("A being of pure molten lava");
        magmaElemental.setPreferredZone(ZoneType.VOLCANO);
        magmaElemental.setWeakness(ElementType.ICE);
        magmaElemental.setResistance(ElementType.FIRE);
        monsterDAO.save(magmaElemental);
        
        Monster fireDrake = new Monster("Fire Drake", 9);
        fireDrake.setDescription("A lesser dragon wreathed in flames");
        fireDrake.setPreferredZone(ZoneType.VOLCANO);
        fireDrake.setWeakness(ElementType.ICE);
        fireDrake.setResistance(ElementType.FIRE);
        fireDrake.setSpecialDropRate(40);
        monsterDAO.save(fireDrake);
        
        // CAVE monsters
        Monster bat = new Monster("Giant Bat", 3);
        bat.setDescription("A massive bat with razor-sharp fangs");
        bat.setPreferredZone(ZoneType.CAVE);
        bat.setWeakness(ElementType.HOLY);
        bat.setResistance(ElementType.DARK);
        monsterDAO.save(bat);
        
        Monster spider = new Monster("Cave Spider", 4);
        spider.setDescription("A huge spider with venomous fangs");
        spider.setPreferredZone(ZoneType.CAVE);
        spider.setWeakness(ElementType.FIRE);
        spider.setResistance(ElementType.POISON);
        monsterDAO.save(spider);
        
        // RUINS monsters
        Monster skeleton = new Monster("Skeleton Warrior", 5);
        skeleton.setDescription("An undead warrior from ages past");
        skeleton.setPreferredZone(ZoneType.RUINS);
        skeleton.setWeakness(ElementType.HOLY);
        skeleton.setResistance(ElementType.PHYSICAL);
        monsterDAO.save(skeleton);
        
        Monster wraith = new Monster("Wraith", 7);
        wraith.setDescription("A ghostly specter of malevolent energy");
        wraith.setPreferredZone(ZoneType.RUINS);
        wraith.setWeakness(ElementType.HOLY);
        wraith.setResistance(ElementType.DARK);
        wraith.setSpecialDropRate(30);
        monsterDAO.save(wraith);
        
        // DUNGEON monsters (high level)
        Monster demon = new Monster("Demon", 9);
        demon.setDescription("A creature from the depths of the abyss");
        demon.setPreferredZone(ZoneType.DUNGEON);
        demon.setWeakness(ElementType.HOLY);
        demon.setResistance(ElementType.DARK);
        demon.setSpecialDropRate(45);
        monsterDAO.save(demon);
        
        Monster dragon = new Monster("Ancient Dragon", 10);
        dragon.setDescription("A legendary wyrm of immense power");
        dragon.setPreferredZone(ZoneType.DUNGEON);
        dragon.setWeakness(ElementType.LIGHTNING);
        dragon.setResistance(ElementType.FIRE);
        dragon.setGoldReward(500);
        dragon.setExperienceReward(1000);
        dragon.setSpecialDropRate(50);
        monsterDAO.save(dragon);
        
        System.out.println("Seeded " + monsterDAO.count() + " monsters across all zones");
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
