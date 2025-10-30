# WarPG v2.0 - Complete Rewrite

A modern, secure text-based RPG built with Java 17, Maven, JPA/Hibernate, and security best practices.

## 🚀 Quick Start

```bash
git clone https://github.com/punoqun/WarPG.git
cd WarPG
mvn clean package
mvn exec:java -Dexec.mainClass="com.warpg.Main"
```

See [QUICKSTART.md](QUICKSTART.md) for detailed instructions.

## ✨ What's New in v2.0

### 🔒 Security (Critical)
- ✅ **Zero SQL Injection Vulnerabilities** - All queries use JPA with parameterized statements
- ✅ **Secure Password Storage** - SHA-256 hashing with salt (no plain text)
- ✅ **No Exposed Credentials** - Embedded H2 database (no hardcoded remote credentials)
- ✅ **CodeQL Verified** - 0 security alerts

### 🏗️ Architecture
- ✅ **Modern Build System** - Maven for dependency management
- ✅ **Layered Architecture** - Clean separation (Model, DAO, Service, UI)
- ✅ **ORM Layer** - Hibernate/JPA for maintainable database access
- ✅ **Proper Schema Design** - No table inheritance anti-patterns

### 🎮 New Game Features
- **5 Character Classes**: Warrior, Mage, Rogue, Cleric, Ranger
- **Enhanced Combat System**: Critical hits, flee mechanics, better balance
- **Complete Attribute System**: 7 base attributes + 4 derived stats
- **Item Rarity System**: 6 levels (Common to Mythic)
- **10 Weapon Types**: Sword, Dagger, Mace, Axe, Spear, Bow, Crossbow, Staff, Wand, Hammer
- **4 Armor Types**: Cloth, Leather, Chainmail, Plate
- **Equipment Slots**: Head, Chest, Hands, Legs, Feet, Weapons, Accessories
- **Leveling System**: Automatic stat growth and progression
- **Zone System**: Multi-zone exploration with wrap-around
- **Quest Framework**: Expandable quest system
- **Achievement System**: Track player accomplishments

## 📚 Documentation

- **[QUICKSTART.md](QUICKSTART.md)** - Get started in 5 minutes
- **[README_NEW.md](README_NEW.md)** - Detailed feature list and comparison
- **[ARCHITECTURE.md](ARCHITECTURE.md)** - Technical architecture and design
- **[SECURITY.md](SECURITY.md)** - Security improvements and analysis
- **[MIGRATION_GUIDE.md](MIGRATION_GUIDE.md)** - Migrating from v1.x

## 🎯 Key Improvements

| Feature | Old Version | v2.0 |
|---------|-------------|------|
| SQL Injection | ❌ Vulnerable | ✅ Secure |
| Password Storage | ❌ Plain text | ✅ Hashed |
| Database | ❌ Remote w/ exposed creds | ✅ Local embedded |
| Build System | ❌ Manual | ✅ Maven |
| Architecture | ❌ Monolithic | ✅ Layered |
| Character Classes | ❌ None | ✅ 5 classes |
| Combat | ❌ Basic | ✅ Advanced |
| Items | ❌ Simple tiers | ✅ Full system |

## 🛠️ Technology Stack

- **Java 17** - Modern Java with latest features
- **Maven 3.9** - Build automation and dependency management
- **Hibernate 6.3** - ORM for secure database access
- **H2 Database** - Embedded, lightweight, secure
- **SLF4J** - Logging framework
- **JUnit 5** - Testing framework (ready)

## 🎮 Gameplay Example

```
=================================
     Welcome to WarPG v2.0!      
=================================

=== MAIN MENU ===
1. Login
2. Register
3. Exit
Choose an option: 2

Username: hero
Password: ****
Character Name: Aragorn

Choose a class:
1. Warrior - Master of melee combat with high strength and health
2. Mage - Wielder of arcane magic with high intelligence and mana
3. Rogue - Agile fighter with high dexterity and luck
4. Cleric - Holy healer with balanced stats and support abilities
5. Ranger - Skilled archer with high dexterity and wisdom
Choice: 1

Character created successfully!
Welcome, Aragorn!

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
```

## 🔐 Security Highlights

**Before (v1.x):**
```java
// VULNERABLE - SQL Injection possible
PreparedStatement stmt = con.prepareStatement(
    "SELECT * FROM hero WHERE id='" + id + "'"
);
```

**After (v2.0):**
```java
// SECURE - Parameterized query via JPA
Player player = em.createQuery(
    "SELECT p FROM Player p WHERE p.username = :username", Player.class)
    .setParameter("username", username)
    .getSingleResult();
```

## 📊 Project Status

✅ **Fully Functional** - Compiles, runs, tested
✅ **Security Verified** - CodeQL scan passed (0 alerts)  
✅ **Documented** - Comprehensive documentation
✅ **Maintainable** - Clean architecture, modern practices

## 🚧 Future Roadmap

- [ ] JavaFX GUI interface
- [ ] Full inventory and equipment system
- [ ] Quest completion mechanics
- [ ] Crafting system
- [ ] More monsters and items
- [ ] Boss battles
- [ ] Multiplayer support (client-server)

## 👥 Contributing

This is an educational project demonstrating secure coding practices. Feel free to:
1. Report issues
2. Suggest features
3. Submit pull requests
4. Use as learning material

## 📝 License

Educational/Personal Project - See repository for details

## 🙏 Credits

Complete rewrite by GitHub Copilot Workspace for security and feature enhancement.

Original concept by punoqun.

---

**Old Version Note:** The original insecure code is preserved in `src/*.java` for reference but is no longer compiled or used. All functionality has been replaced with the new secure implementation in `src/main/java/com/warpg/`.

**Start Playing:** See [QUICKSTART.md](QUICKSTART.md) to begin your adventure!
