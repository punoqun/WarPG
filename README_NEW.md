# WarPG v2.0 - Complete Rewrite

A modern, secure text-based RPG built with Java, JPA/Hibernate, and best practices.

## What's New

### Security Improvements
- **No SQL Injection**: All database queries use JPA with parameterized statements
- **Secure Password Storage**: Passwords hashed with SHA-256 and salt
- **No Hardcoded Credentials**: Database uses embedded H2 instead of remote PostgreSQL with exposed credentials
- **Proper Transaction Management**: ACID compliance with JPA transactions

### Architecture Improvements
- **Modern Build System**: Maven for dependency management
- **ORM Layer**: Hibernate/JPA for secure, maintainable database access
- **Service Layer**: Proper separation of concerns (DAO, Service, Controller layers)
- **Entity-Relationship Design**: Proper database schema without table inheritance anti-pattern

### New Game Features
- **Character Classes**: Warrior, Mage, Rogue, Cleric, Ranger with unique bonuses
- **Enhanced Combat**: Critical hits, better damage calculation, flee option
- **Leveling System**: Automatic stat increases, experience tracking
- **Attribute System**: Strength, Dexterity, Intelligence, Constitution, Wisdom, Charisma, Luck
- **Derived Stats**: Attack, Defense, Magic Power, Magic Resist calculated from attributes
- **Item Rarity System**: Common, Uncommon, Rare, Epic, Legendary, Mythic
- **Multiple Weapon Types**: Sword, Dagger, Mace, Axe, Spear, Bow, Crossbow, Staff, Wand, Hammer
- **Armor Types**: Cloth, Leather, Chainmail, Plate
- **Equipment Slots**: Head, Chest, Hands, Legs, Feet, Weapons, Accessories (Neck, Rings)
- **Consumables**: Health potions, mana potions
- **Monster Variety**: Multiple monsters with level scaling
- **Zone System**: Travel between zones with wrap-around mechanics
- **Quest System**: Framework for quests (expandable)
- **Achievement System**: Track player accomplishments
- **Loot System**: Monsters drop items and gold

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build
```bash
mvn clean package
```

### Run
```bash
mvn exec:java -Dexec.mainClass="com.warpg.Main"
```

Or run directly with Java:
```bash
java -cp target/warpg-2.0.0.jar com.warpg.Main
```

### Testing
```bash
mvn test
```

## Project Structure

```
src/main/java/com/warpg/
├── model/          # JPA entities (Player, Item, Monster, etc.)
├── dao/            # Data Access Objects with secure queries
├── service/        # Business logic (PlayerService, CombatService, GameEngine)
├── controller/     # UI controllers (future JavaFX)
├── util/           # Utilities (DatabaseManager, PasswordUtil)
└── Main.java       # Application entry point

src/main/resources/
└── META-INF/
    └── persistence.xml  # JPA configuration
```

## Database

The game uses an embedded H2 database stored in `./data/warpg.mv.db`. This file is automatically created on first run and contains all game data, player progress, items, and monsters.

## Security Features

1. **SQL Injection Prevention**: All queries use JPA Query Language with parameters
2. **Password Hashing**: Passwords never stored in plain text
3. **No Credential Exposure**: No hardcoded database passwords in source
4. **Connection Pooling**: Proper resource management with HikariCP
5. **Input Validation**: Service layer validates all user input

## Comparison with Old Version

| Feature | Old Version | New Version |
|---------|-------------|-------------|
| Database Access | Raw SQL with string concatenation | JPA with parameterized queries |
| Password Storage | Plain text | SHA-256 with salt |
| Database Location | Remote PostgreSQL with exposed credentials | Local embedded H2 |
| Build System | None (manual compilation) | Maven |
| Architecture | Monolithic with mixed concerns | Layered (Model, DAO, Service) |
| Character Classes | None | 5 classes with unique abilities |
| Combat System | Basic random hits | Advanced with crits, stats, flee |
| Item System | Basic types | Multiple types with rarity |
| Leveling | Hardcoded | Dynamic with stat growth |

## Future Enhancements

- JavaFX graphical interface
- More weapon and armor types
- Crafting system
- Multiplayer support
- Quest chains
- Dungeon instances
- Trading system
- Guild system
- Pet system
- More spells and abilities

## Contributing

This is a complete rewrite focusing on security, architecture, and gameplay. The old source code is preserved in the `src/` directory (old Java files) for reference but is no longer used.

## License

Educational/Personal Project
