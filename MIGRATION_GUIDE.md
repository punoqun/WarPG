# Migration Guide: WarPG v1.x to v2.0

## Overview
WarPG v2.0 is a complete rewrite with incompatible changes. This guide helps you understand the differences and migrate if needed.

## Breaking Changes

### Database
- **Old:** Remote PostgreSQL with exposed credentials
- **New:** Local embedded H2 database
- **Impact:** Old save data is **not compatible**. You must start fresh.

### Architecture
- **Old:** Monolithic with raw SQL
- **New:** Layered architecture with ORM
- **Impact:** All code has changed. Custom modifications won't work.

### API Changes
- **Old:** Direct database access in every class
- **New:** Service layer with DAOs
- **Impact:** If you extended the code, you'll need to rewrite extensions.

## What Stayed the Same

✅ Core game concept (text RPG with zones and combat)
✅ Movement system (NSEW navigation)
✅ Zone wrap-around mechanics
✅ Item drop system
✅ Monster encounters

## What Changed

### Character System
**Old:**
- Single character type
- 11 basic attributes
- No character classes

**New:**
- 5 character classes (Warrior, Mage, Rogue, Cleric, Ranger)
- 7 core attributes + 4 derived stats
- Class-based bonuses
- Automatic stat growth on level-up

### Combat System
**Old:**
```java
// Simple hit/miss
if (roll > 80 - (attack - defense)) {
    damage = strength;
}
```

**New:**
```java
// Advanced with critical hits, flee mechanics
int hitChance = 70 + ((attack - defense) * 2) + (luck / 2);
boolean isCrit = random.nextInt(100) < (10 + luck / 2);
if (isCrit) damage *= 2;
```

### Item System
**Old:**
- Basic items with tier system (1-5)
- Table inheritance (PostgreSQL specific)

**New:**
- 6 rarity levels (Common to Mythic)
- 10 weapon types
- 4 armor types
- Equipment slots
- Item modifiers and bonuses

### Authentication
**Old:**
```java
// Plain text password
String password = "mypassword";
```

**New:**
```java
// Hashed with salt
String passwordHash = PasswordUtil.hashPassword("mypassword");
// Returns: "saltBase64:hashBase64"
```

## Building and Running

### Old Method
```bash
javac -cp "lib/postgresql-42.2.4.jar" src/*.java
java -cp "lib/postgresql-42.2.4.jar:src" Main
```

### New Method
```bash
mvn clean package
mvn exec:java -Dexec.mainClass="com.warpg.Main"
```

Or run the JAR:
```bash
java -jar target/warpg-2.0.0.jar
```

## File Structure

### Old
```
src/
  *.java (all files in root)
lib/
  postgresql-42.2.4.jar
```

### New
```
src/main/java/com/warpg/
  model/      # Entities
  dao/        # Data access
  service/    # Business logic
  util/       # Utilities
  Main.java
src/main/resources/
  META-INF/persistence.xml
pom.xml       # Maven config
```

## Data Migration

### Save Data
❌ **Not Supported**

The database schema is completely different. You cannot migrate save data from v1.x to v2.0.

### Starting Fresh
1. Remove old database connection info
2. Run new version
3. Register new account
4. Create new character

## Custom Modifications

If you modified the old code:

### Database Changes
**Old Way:**
```java
PreparedStatement stmt = con.prepareStatement(
    "INSERT INTO hero VALUES ('" + id + "', ...)"
);
```

**New Way:**
```java
Player player = new Player(username, passwordHash, name, characterClass);
playerDAO.save(player);
```

### Adding New Items
**Old Way:**
```sql
INSERT INTO weapon VALUES (1, 100, 3, 50, 'Level 5', 25);
```

**New Way:**
```java
Weapon sword = new Weapon("Excalibur", "Legendary sword", 
    ItemRarity.LEGENDARY, WeaponType.SWORD, 50, 10);
itemDAO.save(sword);
```

### Adding New Monsters
**Old Way:**
```sql
INSERT INTO mob VALUES (100, 10, 15, 8, 'Dragon', 20);
```

**New Way:**
```java
Monster dragon = new Monster("Dragon", 10);
dragon.setDescription("A fearsome dragon");
monsterDAO.save(dragon);
```

## Extending the New Version

### Adding a New Character Class

1. Edit `CharacterClass.java`:
```java
PALADIN("Paladin", "Holy warrior with divine powers")
```

2. Edit `Player.java` `applyClassBonuses()`:
```java
case PALADIN:
    strength += 4;
    wisdom += 4;
    constitution += 2;
    break;
```

### Adding a New Weapon Type

1. Edit `WeaponType.java`:
```java
KATANA("Katana", "Fast blade from the east")
```

2. Create weapons of this type:
```java
Weapon katana = new Weapon("Steel Katana", "Sharp blade", 
    ItemRarity.RARE, WeaponType.KATANA, 25, 15);
```

### Adding Custom Stats

1. Add field to `Player.java`:
```java
private int customStat = 0;
```

2. Add getters/setters

3. Update `recalculateStats()` if derived

4. Rebuild database (delete `data/` folder)

## Testing Your Migration

1. ✅ Game compiles
2. ✅ Can register new account
3. ✅ Can create character
4. ✅ Can move around
5. ✅ Can fight monsters
6. ✅ Can save and reload

## Getting Help

### Old Code Issues
The old code is preserved in `src/` directory for reference only. It is not compiled or used.

### New Code Issues
Check:
1. Maven dependencies installed (`mvn dependency:resolve`)
2. Java 17+ installed (`java -version`)
3. Database directory writable (`ls -la data/`)

### Common Issues

**Problem:** Game won't start
**Solution:** Delete `data/` directory and restart

**Problem:** Can't compile
**Solution:** Run `mvn clean install`

**Problem:** Character stats wrong
**Solution:** Check class bonuses in `Player.java`

## Rollback

If you need to run the old version:

1. Checkout old commit: `git checkout <old-commit>`
2. Set up PostgreSQL database
3. Compile old code: `javac -cp lib/postgresql-42.2.4.jar src/*.java`
4. Run: `java -cp "lib/postgresql-42.2.4.jar:src" Main`

**Warning:** Old version has security vulnerabilities. Use at your own risk.

## Future Compatibility

v2.0 uses standard JPA, so future versions should support database migration tools like Flyway or Liquibase.

Save data format is stable and will be migrated in future versions.

## Summary

| Aspect | v1.x | v2.0 | Compatible? |
|--------|------|------|-------------|
| Database | PostgreSQL | H2 | ❌ No |
| Save Data | Remote DB | Local DB | ❌ No |
| Code | Raw SQL | JPA/Hibernate | ❌ No |
| Security | Vulnerable | Secure | N/A |
| Features | Basic | Enhanced | ✅ Superset |
| Performance | Network | Local | ✅ Better |

**Recommendation:** Start fresh with v2.0. The enhanced features and security make it worth the restart.
