package com.warpg;

import com.warpg.model.CharacterClass;
import com.warpg.model.Monster;
import com.warpg.model.Player;
import com.warpg.model.Zone;
import com.warpg.model.ZoneType;
import com.warpg.service.CombatService;
import com.warpg.service.GameEngine;
import com.warpg.util.DatabaseManager;
import com.warpg.util.DataSeeder;

import java.util.Scanner;

/**
 * Main application entry point with console-based UI
 */
public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static GameEngine gameEngine;
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("     Welcome to WarPG v2.0!      ");
        System.out.println("=================================\n");
        
        // Initialize database
        DatabaseManager.initialize();
        
        // Seed initial data
        System.out.println("Initializing game data...");
        DataSeeder.seedInitialData();
        System.out.println();
        
        // Initialize game engine
        gameEngine = new GameEngine();
        
        // Main menu
        mainMenu();
        
        // Cleanup
        DatabaseManager.shutdown();
        scanner.close();
    }
    
    private static void mainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    loginPlayer();
                    break;
                case "2":
                    registerPlayer();
                    break;
                case "3":
                    System.out.println("Thanks for playing!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private static void loginPlayer() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        if (gameEngine.loginPlayer(username, password)) {
            System.out.println("\nWelcome back, " + 
                gameEngine.getCurrentPlayer().getCharacterName() + "!");
            gameLoop();
        } else {
            System.out.println("\nInvalid credentials. Please try again.");
        }
    }
    
    private static void registerPlayer() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Character Name: ");
        String charName = scanner.nextLine().trim();
        
        System.out.println("\nChoose a class:");
        for (int i = 0; i < CharacterClass.values().length; i++) {
            CharacterClass cc = CharacterClass.values()[i];
            System.out.println((i + 1) + ". " + cc.getDisplayName() + " - " + 
                cc.getDescription());
        }
        System.out.print("Choice: ");
        
        try {
            int classChoice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            CharacterClass selectedClass = CharacterClass.values()[classChoice];
            
            Player player = gameEngine.registerPlayer(username, password, charName, selectedClass);
            System.out.println("\nCharacter created successfully!");
            System.out.println("Welcome, " + player.getCharacterName() + "!");
            
            gameEngine.loginPlayer(username, password);
            gameLoop();
        } catch (Exception e) {
            System.out.println("Error creating character: " + e.getMessage());
        }
    }
    
    private static void gameLoop() {
        Player player = gameEngine.getCurrentPlayer();
        
        while (player.isAlive()) {
            System.out.println("\n=== GAME ===");
            displayPlayerInfo(player);
            System.out.println("\n1. Move North");
            System.out.println("2. Move South");
            System.out.println("3. Move East");
            System.out.println("4. Move West");
            System.out.println("5. View Stats");
            System.out.println("6. Save & Quit");
            System.out.print("Choose action: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    handleMove(GameEngine.Direction.NORTH);
                    break;
                case "2":
                    handleMove(GameEngine.Direction.SOUTH);
                    break;
                case "3":
                    handleMove(GameEngine.Direction.EAST);
                    break;
                case "4":
                    handleMove(GameEngine.Direction.WEST);
                    break;
                case "5":
                    displayDetailedStats(player);
                    break;
                case "6":
                    gameEngine.savePlayer();
                    System.out.println("Game saved!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        
        System.out.println("\n=== GAME OVER ===");
        System.out.println("You have fallen in battle...");
    }
    
    private static void handleMove(GameEngine.Direction direction) {
        GameEngine.MoveResult result = gameEngine.movePlayer(direction);
        System.out.println("\n" + result.getMessage());
        
        if (result.getEventType() == GameEngine.EventType.COMBAT) {
            combat(result.getMonster());
        }
    }
    
    private static void combat(Monster monster) {
        System.out.println("\n=== COMBAT ===");
        Player player = gameEngine.getCurrentPlayer();
        
        while (player.isAlive() && monster.isAlive()) {
            System.out.println("\nYour HP: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println(monster.getName() + " HP: " + monster.getHealth() + 
                "/" + monster.getMaxHealth());
            System.out.println("\n1. Attack");
            System.out.println("2. Flee");
            System.out.print("Action: ");
            
            String choice = scanner.nextLine().trim();
            
            if (choice.equals("2")) {
                if (gameEngine.attemptFlee(monster)) {
                    System.out.println("You successfully fled!");
                    return;
                } else {
                    System.out.println("Failed to flee!");
                }
            }
            
            // Player attacks
            CombatService.CombatRound playerRound = 
                gameEngine.executeCombatRound(monster, true);
            System.out.println(playerRound.getDescription());
            
            if (!monster.isAlive()) {
                System.out.println("\nVictory! You defeated the " + monster.getName() + "!");
                gameEngine.awardCombatRewards(monster);
                System.out.println("Gained " + monster.getExperienceReward() + " XP and " + 
                    monster.getGoldReward() + " gold!");
                
                if (player.getLevel() > 1) {
                    System.out.println("*** LEVEL UP! You are now level " + 
                        player.getLevel() + "! ***");
                }
                return;
            }
            
            // Monster attacks
            CombatService.CombatRound monsterRound = 
                gameEngine.executeCombatRound(monster, false);
            System.out.println(monsterRound.getDescription());
        }
    }
    
    private static void displayPlayerInfo(Player player) {
        System.out.println("\nCharacter: " + player.getCharacterName() + 
            " (Level " + player.getLevel() + " " + 
            player.getCharacterClass().getDisplayName() + ")");
        System.out.println("HP: " + player.getHealth() + "/" + player.getMaxHealth() + 
            " | MP: " + player.getMana() + "/" + player.getMaxMana());
        System.out.println("Gold: " + player.getGold() + " | XP: " + 
            player.getExperience() + "/" + player.getExperienceToNextLevel());
        
        // Display zone info with color
        Zone currentZone = gameEngine.getCurrentZone();
        if (currentZone != null) {
            String zoneColor = currentZone.getZoneType().getAnsiColor();
            String reset = ZoneType.getResetCode();
            System.out.println("Location: " + zoneColor + currentZone.getZoneType().getEmoji() + 
                " " + currentZone.getDisplayName() + reset + 
                " (" + player.getPosX() + ", " + player.getPosY() + ")");
            System.out.println("  " + zoneColor + currentZone.getZoneType().getDescription() + reset);
        } else {
            System.out.println("Position: Zone " + player.getZoneId() + 
                " (" + player.getPosX() + ", " + player.getPosY() + ")");
        }
    }
    
    private static void displayDetailedStats(Player player) {
        System.out.println("\n=== CHARACTER STATS ===");
        System.out.println("Name: " + player.getCharacterName());
        System.out.println("Class: " + player.getCharacterClass().getDisplayName());
        System.out.println("Level: " + player.getLevel());
        System.out.println("\n--- Attributes ---");
        System.out.println("Strength: " + player.getStrength());
        System.out.println("Dexterity: " + player.getDexterity());
        System.out.println("Intelligence: " + player.getIntelligence());
        System.out.println("Constitution: " + player.getConstitution());
        System.out.println("Wisdom: " + player.getWisdom());
        System.out.println("Charisma: " + player.getCharisma());
        System.out.println("Luck: " + player.getLuck());
        System.out.println("\n--- Combat Stats ---");
        System.out.println("Attack: " + player.getAttack());
        System.out.println("Defense: " + player.getDefense());
        System.out.println("Magic Power: " + player.getMagicPower());
        System.out.println("Magic Resist: " + player.getMagicResist());
    }
}
