# Architecture Documentation - WarPG v2.0

## Overview
WarPG v2.0 follows a layered architecture pattern with clear separation of concerns, modern ORM, and security-first design.

## Architecture Diagram

```
┌─────────────────────────────────────────────────┐
│            Presentation Layer                   │
│  ┌──────────────────────────────────────────┐  │
│  │        Main (Console UI)                  │  │
│  │        (Future: JavaFX GUI)               │  │
│  └──────────────────────────────────────────┘  │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│             Service Layer                        │
│  ┌───────────────┐  ┌──────────────────────┐   │
│  │ GameEngine    │  │ PlayerService        │   │
│  │               │  │ CombatService        │   │
│  └───────────────┘  └──────────────────────┘   │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│              DAO Layer                          │
│  ┌───────────────┐  ┌──────────────────────┐   │
│  │ GenericDAO    │  │ PlayerDAO            │   │
│  │ (Base)        │  │ MonsterDAO           │   │
│  │               │  │ ItemDAO              │   │
│  └───────────────┘  └──────────────────────┘   │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│          Persistence Layer (JPA/Hibernate)      │
│  ┌──────────────────────────────────────────┐  │
│  │  EntityManager                            │  │
│  │  Transaction Management                   │  │
│  └──────────────────────────────────────────┘  │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│          Database (H2 Embedded)                 │
│  ┌──────────────────────────────────────────┐  │
│  │  players, items, monsters, quests, etc.   │  │
│  └──────────────────────────────────────────┘  │
└─────────────────────────────────────────────────┘
```

## Layer Responsibilities

### 1. Presentation Layer (`Main.java`)
**Responsibilities:**
- User interface (console input/output)
- Display game state
- Handle user commands
- Format output

**Dependencies:**
- Service Layer (GameEngine, PlayerService)

**Key Classes:**
- `Main`: Entry point and console UI controller

### 2. Service Layer (`service/`)
**Responsibilities:**
- Business logic
- Game rules enforcement
- Orchestrate DAO operations
- Coordinate complex operations

**Dependencies:**
- DAO Layer
- Model Layer

**Key Classes:**
- `GameEngine`: Main game controller, handles movement, encounters, zones
- `PlayerService`: Player registration, authentication, save/load
- `CombatService`: Combat mechanics, damage calculation, rewards

**Design Patterns:**
- Service Facade: Single entry point for related operations
- Strategy Pattern: Different combat strategies per class

### 3. DAO Layer (`dao/`)
**Responsibilities:**
- Database operations (CRUD)
- Query construction
- Transaction management
- Entity persistence

**Dependencies:**
- Model Layer
- DatabaseManager

**Key Classes:**
- `GenericDAO<T, ID>`: Base class with common CRUD operations
- `PlayerDAO`: Player-specific queries (find by username, etc.)
- `ItemDAO`: Item-specific queries (find by type, etc.)
- `MonsterDAO`: Monster-specific queries (find by level, random)

**Design Patterns:**
- DAO Pattern: Separate persistence logic from business logic
- Generic Programming: Reusable CRUD via generics
- Template Method: Common operations in base class

### 4. Model Layer (`model/`)
**Responsibilities:**
- Domain objects
- Entity definitions
- Validation rules
- Business rules (stat calculations)

**Dependencies:**
- None (pure domain model)

**Key Classes:**

#### Core Entities
- `Player`: Player character with stats, inventory, equipment
- `Monster`: Enemy creatures with stats and loot
- `Item`: Base class for all items (abstract)
  - `Weapon`: Weapons with attack bonuses
  - `Armor`: Armor with defense bonuses
  - `Consumable`: Potions and consumables

#### Supporting Entities
- `InventoryItem`: Links player to items (with quantity)
- `EquippedItem`: Tracks equipped items by slot
- `Quest`: Quest definitions
- `PlayerQuest`: Quest progress tracking
- `Achievement`: Achievement definitions
- `PlayerAchievement`: Achievement unlock tracking

#### Enumerations
- `CharacterClass`: Character classes
- `ItemType`, `ItemRarity`: Item classification
- `WeaponType`, `ArmorType`: Equipment types
- `EquipmentSlot`: Equipment slots
- `ConsumableType`: Consumable categories

**Design Patterns:**
- Entity Pattern: JPA entities
- Inheritance: Item hierarchy
- Enumeration: Type-safe constants

### 5. Utility Layer (`util/`)
**Responsibilities:**
- Cross-cutting concerns
- Helper functions
- Database initialization
- Security utilities

**Key Classes:**
- `DatabaseManager`: Entity manager factory singleton
- `PasswordUtil`: Secure password hashing
- `DataSeeder`: Initial game data population

## Data Flow Examples

### 1. Player Registration

```
User Input (Main)
    ↓
PlayerService.registerPlayer()
    ↓
PasswordUtil.hashPassword()
    ↓
new Player(...) [Model]
    ↓
PlayerDAO.save()
    ↓
EntityManager.persist()
    ↓
Database INSERT
```

### 2. Combat Round

```
User Input (Main)
    ↓
GameEngine.executeCombatRound()
    ↓
CombatService.executeRound()
    ↓
Calculate damage [Model.Player, Model.Monster]
    ↓
Player.takeDamage() / Monster.takeDamage()
    ↓
Return CombatRound result
    ↓
Display to user (Main)
```

### 3. Load Player

```
User Input (Main)
    ↓
GameEngine.loginPlayer()
    ↓
PlayerService.authenticatePlayer()
    ↓
PlayerDAO.findByUsername()
    ↓
EntityManager.createQuery()
    ↓
Database SELECT
    ↓
PasswordUtil.verifyPassword()
    ↓
Return Player entity
```

## Design Principles Applied

### SOLID Principles

#### Single Responsibility (S)
- Each class has one reason to change
- DAOs only handle persistence
- Services only handle business logic
- Models only represent domain

#### Open/Closed (O)
- GenericDAO is open for extension (inheritance)
- Closed for modification
- New entity types extend Item without changing Item

#### Liskov Substitution (L)
- All Item subclasses can substitute Item
- All DAOs can substitute GenericDAO

#### Interface Segregation (I)
- Small, focused interfaces
- DAOs expose only needed methods
- Services have specific purposes

#### Dependency Inversion (D)
- High-level modules (Services) don't depend on low-level (DAOs)
- Both depend on abstractions (JPA interfaces)

### Other Principles

#### DRY (Don't Repeat Yourself)
- GenericDAO eliminates duplicate CRUD code
- Utility classes for common operations

#### KISS (Keep It Simple)
- Straightforward layering
- Clear responsibilities
- No over-engineering

#### YAGNI (You Aren't Gonna Need It)
- Only implemented needed features
- No speculative generality

## Security Architecture

### Defense in Depth

```
Layer 1: Input Validation (Services)
    ↓
Layer 2: Type Safety (JPA Entities)
    ↓
Layer 3: Parameterized Queries (JPA)
    ↓
Layer 4: Database Constraints (H2)
```

### Security Features

1. **Authentication**
   - Password hashing (SHA-256 + salt)
   - Constant-time comparison
   - No plain text storage

2. **Data Access**
   - All queries parameterized
   - No string concatenation
   - JPA handles escaping

3. **Data Validation**
   - JPA constraints (`@Column(nullable=false)`)
   - Service layer validation
   - Type safety

4. **Error Handling**
   - Try-catch with rollback
   - No sensitive data in errors
   - Proper resource cleanup

## Performance Considerations

### Database
- Connection pooling (20 connections)
- Lazy loading for collections
- Eager loading for frequently accessed data
- Indexes on foreign keys (automatic)

### Memory
- Embedded H2 (low memory footprint)
- Entity caching (Hibernate L1 cache)
- Proper resource disposal

### Scalability
Current: Single-user local game
Future: Could scale to client-server with:
- Connection pooling adjustments
- Second-level cache
- Read replicas
- Sharding by player ID

## Testing Strategy

### Unit Tests (Future)
- Test each layer independently
- Mock dependencies
- Test edge cases

### Integration Tests (Future)
- Test layer interactions
- Test database operations
- Test transaction rollback

### Current Testing
- Manual testing via console
- CodeQL security scanning
- Maven compilation validation

## Extension Points

### Adding New Features

1. **New Entity Type**
   - Create entity in `model/`
   - Add to `persistence.xml`
   - Create DAO if needed
   - Add service methods

2. **New Game Mechanic**
   - Add service class in `service/`
   - Update GameEngine
   - Add to Main menu

3. **New UI**
   - Create controller in `controller/`
   - Implement using services
   - No DAO access directly

## Technology Stack

| Layer | Technology |
|-------|------------|
| Build | Maven 3.9 |
| Language | Java 17 |
| ORM | Hibernate 6.3 |
| Database | H2 2.2 |
| Logging | SLF4J 2.0 |
| Testing | JUnit 5 |

## Configuration

### Database (`persistence.xml`)
```xml
<property name="hibernate.hbm2ddl.auto" value="create"/>
```
- `create`: Drop and recreate (development)
- `update`: Update schema (production)
- `validate`: Only validate (production)

### Connection Pool
```xml
<property name="hibernate.c3p0.min_size" value="5"/>
<property name="hibernate.c3p0.max_size" value="20"/>
```

## Deployment

### Development
```bash
mvn clean compile
mvn exec:java
```

### Production
```bash
mvn clean package
java -jar target/warpg-2.0.0.jar
```

### Distribution
- Include JAR and README
- Database created automatically
- No external dependencies needed

## Future Architecture Evolution

### Phase 1 (Current)
- Console UI
- Local database
- Single player

### Phase 2 (Planned)
- JavaFX GUI
- Enhanced features
- Save slots

### Phase 3 (Future)
- Client-Server architecture
- MySQL/PostgreSQL (with proper config)
- RESTful API
- Multiplayer

### Phase 4 (Vision)
- Web interface
- Real-time multiplayer
- Cloud database
- Microservices

## Maintenance

### Adding Data
Edit `DataSeeder.java`:
```java
Monster newMonster = new Monster("NewMonster", 5);
monsterDAO.save(newMonster);
```

### Schema Changes
1. Modify entity classes
2. Delete `data/` folder (or use migrations in future)
3. Restart application

### Backup
```bash
cp -r data/ data_backup_$(date +%Y%m%d)/
```

## Conclusion

WarPG v2.0 architecture provides:
- ✅ Clear separation of concerns
- ✅ Security by design
- ✅ Maintainability
- ✅ Extensibility
- ✅ Testability
- ✅ Performance

The layered architecture makes it easy to understand, modify, and extend the game while maintaining security and code quality.
