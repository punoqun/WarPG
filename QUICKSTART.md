# WarPG v2.0 - Quick Start Guide

## Installation

### Prerequisites
- **Java 17 or higher**: Check with `java -version`
- **Maven 3.6+**: Check with `mvn -version`

### Download and Build
```bash
git clone https://github.com/punoqun/WarPG.git
cd WarPG
mvn clean package
```

## Running the Game

### Method 1: Maven Exec
```bash
mvn exec:java -Dexec.mainClass="com.warpg.Main"
```

### Method 2: JAR File
```bash
java -jar target/warpg-2.0.0.jar
```

## First Time Setup

1. **Main Menu**: Choose "2" to Register
2. **Username**: Enter a unique username (e.g., `hero123`)
3. **Password**: Enter a password (securely hashed)
4. **Character Name**: Your character's display name (e.g., `Aragorn`)
5. **Class Selection**: Choose 1-5:
   - 1: **Warrior** - High health and strength
   - 2: **Mage** - High mana and intelligence
   - 3: **Rogue** - High dexterity and luck
   - 4: **Cleric** - Balanced healer
   - 5: **Ranger** - Archer with dexterity

## Playing the Game

### Main Game Loop

```
=== GAME ===
Character: Aragorn (Level 1 Warrior)
HP: 120/175 | MP: 50/105
Gold: 100 | XP: 0/150
Position: Zone 1 (25, 25)

1. Move North
2. Move South
3. Move East
4. Move West
5. View Stats
6. Save & Quit
Choose action:
```

### Movement
- **1-4**: Move in cardinal directions
- Walking into zone edge wraps to next zone
- Each move may trigger encounters

### Encounters

#### Empty Tile
```
Nothing here...
```
Continue exploring.

#### Treasure
```
You found 45 gold!
```
Automatically added to inventory.

#### Monster
```
A Goblin appears!

=== COMBAT ===
Your HP: 120/175
Goblin HP: 70/70

1. Attack
2. Flee
Action:
```

### Combat

#### Attacking
- Choose "1" to attack
- Damage based on your Attack vs their Defense
- Critical hits possible (based on Luck)
- Turn-based: You attack, then monster attacks

#### Fleeing
- Choose "2" to attempt escape
- Success chance based on Luck vs Monster Level
- Failed escape: Monster gets free attack

#### Victory
```
Victory! You defeated the Goblin!
Gained 25 XP and 10 gold!
```

### Leveling Up

When you gain enough XP:
```
*** LEVEL UP! You are now level 2! ***
```

Benefits:
- All stats increase by 1
- HP and MP fully restored
- New experience goal

### View Stats

Choose "5" in main menu:
```
=== CHARACTER STATS ===
Name: Aragorn
Class: Warrior
Level: 2

--- Attributes ---
Strength: 16
Dexterity: 11
Intelligence: 11
Constitution: 14
Wisdom: 11
Charisma: 11
Luck: 11

--- Combat Stats ---
Attack: 21
Defense: 17
Magic Power: 16
Magic Resist: 14
```

### Saving

Choose "6" to Save & Quit:
```
Game saved!
```

Your progress is automatically saved.

### Loading

On next run:
1. Main Menu: Choose "1" to Login
2. Enter your username
3. Enter your password
4. Resume where you left off!

## Character Classes Explained

### Warrior
- **Bonus**: +5 STR, +3 CON, +20 HP
- **Best for**: Melee combat, tanking damage
- **Play style**: Get close and hit hard

### Mage
- **Bonus**: +5 INT, +3 WIS, +30 MP
- **Best for**: Magic damage (future), ranged combat
- **Play style**: High magic power

### Rogue
- **Bonus**: +5 DEX, +3 LUCK
- **Best for**: Critical hits, fleeing
- **Play style**: Fast, lucky strikes

### Cleric
- **Bonus**: +5 WIS, +3 CON, +20 MP, +10 HP
- **Best for**: Balanced approach, healing (future)
- **Play style**: Versatile support

### Ranger
- **Bonus**: +4 DEX, +4 WIS
- **Best for**: Ranged combat, accuracy
- **Play style**: Precise attacks

## Tips and Tricks

### Combat
- **Check HP**: Monitor health during long fights
- **Flee if needed**: Low health? Run away!
- **Luck matters**: Higher luck = more crits and better flee chance

### Exploration
- **Explore thoroughly**: Don't rush to next zone
- **Collect gold**: You'll need it for future features
- **Level up**: Fight monsters to gain experience

### Character Building
- **Warriors**: Focus on strength-based equipment
- **Mages**: Look for intelligence bonuses
- **Rogues**: Maximize dexterity for critical hits

### Progression
- Level 1-3: Fight goblins
- Level 4-6: Challenge orcs
- Level 7-9: Battle trolls
- Level 10+: Face dragons

## Troubleshooting

### Game won't start
```bash
# Clear database and restart
rm -rf data/
mvn clean compile
mvn exec:java -Dexec.mainClass="com.warpg.Main"
```

### Forgot password
- Delete `data/` folder and start over
- OR use H2 console (advanced users)

### Build errors
```bash
# Clean and rebuild
mvn clean install
```

### Database locked
- Close all instances of the game
- Delete `data/warpg.lock` if exists

## Advanced Features

### Database Location
Game data stored in: `./data/warpg.mv.db`

Backup your save:
```bash
cp -r data/ backup/
```

Restore save:
```bash
cp -r backup/data/ ./
```

### Multiple Characters
Create multiple accounts with different usernames.
Each account has its own character.

### Statistics Tracking
All achievements and quests tracked automatically (visible in future updates).

## Game Mechanics

### Damage Formula
```
Hit Chance = 70% + (Attack - Defense) * 2% + Luck/2%
Critical Chance = 10% + Luck/2%
Critical Damage = Normal Damage * 2
```

### Experience Formula
```
XP to Next Level = Level * 100 + Level² * 50
```

### Stat Growth
Each level up: +1 to all base attributes

## Future Features (Planned)

- [ ] Item equipment system
- [ ] Inventory management
- [ ] Quest completion
- [ ] Achievement unlocks
- [ ] Consumable items (potions)
- [ ] Shops and trading
- [ ] More monster variety
- [ ] Boss battles
- [ ] Crafting system
- [ ] JavaFX GUI

## Getting Help

### Documentation
- `README.md` - Project overview and features
- `ARCHITECTURE.md` - Technical details
- `SECURITY.md` - Security information
- `ZONES.md` - Zone system guide

### Community
- GitHub Issues: Report bugs
- GitHub Discussions: Ask questions

### Development
```bash
# Run tests (when available)
mvn test

# Check code style
mvn checkstyle:check

# Generate docs
mvn javadoc:javadoc
```

## Quick Reference

### Commands
| Command | Action |
|---------|--------|
| 1 | Move North |
| 2 | Move South |
| 3 | Move East |
| 4 | Move West |
| 5 | View Stats |
| 6 | Save & Quit |

### Combat
| Command | Action |
|---------|--------|
| 1 | Attack |
| 2 | Flee |

### Stats
- **STR**: Increases attack damage
- **DEX**: Increases attack accuracy and defense
- **INT**: Increases magic power
- **CON**: Increases max HP and defense
- **WIS**: Increases magic resist and max MP
- **CHA**: Increases gold rewards
- **LUCK**: Increases critical chance and flee success

## Have Fun!

Explore the world, fight monsters, level up your character, and become a legend!

For more information, see the full documentation:
- Technical: `ARCHITECTURE.md`
- Security: `SECURITY.md`
- Zones: `ZONES.md`
