# ⚔ RougelikeRPG

A turn-based terminal dungeon crawler built in Java using the [Lanterna](https://github.com/mabe02/lanterna) terminal UI library. Procedurally generated floors, multiple enemy types, class-based player stats, and a 10-floor dungeon with a boss waiting at the bottom.

Built as a portfolio project demonstrating OOP design, procedural generation, game loop architecture, and iterative development.

---

## 📸 Features

- **Procedural dungeon generation** via cellular automata — every floor is unique
- **10-floor dungeon** with floor-scaled enemy difficulty
- **3 playable classes** — Warrior, Rogue, Wizard — each with unique stats and skills
- **3 enemy types** — Rat, Bat, Goblin — with radius-based AI and per-enemy speed tiers
- **Town/overworld map** with Inn, Shop, Blacksmith, and Town Hall
- **Ladder system** — touch the ladder to descend to the next generated floor
- **Status bar HUD** showing HP, Gold, and current Floor
- **Full WASD movement** with wall collision using a next-position (nx, ny) system

---

## 🎮 Controls

| Key | Action |
|-----|--------|
| `W` | Move up |
| `S` | Move down |
| `A` | Move left |
| `D` | Move right |
| `Q` / `Esc` | Quit |

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven

### Steps

```bash
git clone https://github.com/YOUR_USERNAME/RougelikeRPG.git
cd RougelikeRPG
mvn compile
mvn exec:java -Dexec.mainClass="RougelikeRPG.Main"
```

> **Recommended:** Run in IntelliJ IDEA with **JetBrains Mono** font for correct Unicode symbol rendering.

---

## 🗺 Maps

### Dungeon (34×100)
Generated fresh each floor using **cellular automata**:
1. Fill grid randomly based on a fill chance
2. Run 7 iterations of neighbour-counting smoothing
3. Remove small disconnected regions (BFS flood fill)
4. Connect remaining regions with L-shaped tunnels
5. Place a ladder (`≡`) at a random floor tile

Walls are rendered with depth-based shading — walls closer to open floor appear brighter, giving a sense of depth without a full lighting system.

### Town/Overworld (100×34)
A hand-crafted town with:
- **Inn** (top-left) — rest and recovery
- **Town Hall** (top-right) — quests and lore
- **Smithy** (bottom-left) — weapons and armor
- **General Shop** (bottom-right) — consumables
- **Fountain** — decorative centrepiece
- **Cave entrance** leading to the dungeon

---

## 🧱 Architecture

```
Main
 └── Dungeon (implements MapLoop)
      ├── DungeonMap (implements GameMap)
      │    ├── Cellular automata generation
      │    ├── Enemy spawning + scaling
      │    └── Tile rendering
      ├── Player (extends Entity)
      │    ├── WASD movement
      │    └── Class stats (Warrior / Rogue / Wizard)
      └── Enemy (extends Entity)
           └── EnemyAi() — radius-based chase + speed tiers
```

### Key Classes

| Class | Responsibility |
|-------|---------------|
| `Entity` | Base class: hp, att, def, pos (px,py), next pos (nx,ny), random spawn |
| `Player` | Extends Entity — input, movement, class system, ladder interaction |
| `Enemy` | Extends Entity — `EnemyAi()`, speed, moveCounter, symbol, color |
| `DungeonMap` | 2D tile array, cellular automata generation, enemy generation |
| `Dungeon` | Game loop via `start(Player, Screen)` |
| `Town` | Hand-crafted overworld map with its own loop |
| `GameMap` | Interface — render, getTile, enemy management |
| `MapLoop` | Interface — `start(Player, Screen)` |
| `Tiles` | Enum — WALL, FLOOR, LADDER |

### Design Decisions
- **nx,ny next-position system** — collision is checked against the next position before committing the move, keeping movement logic clean
- **DungeonMap is data only** — no game loop logic lives inside it; the `Dungeon` class owns the loop
- **Entities rendered generically** — the renderer loops over `Entity` references; Player and Enemy are treated the same way
- **ArrayList\<Enemy\>** with `Iterator` for safe removal of dead enemies during the game loop

---

## ⚔ Playable Classes

| | Warrior | Rogue | Wizard |
|---|---|---|---|
| **Race** | Dwarf | Elf | Human |
| **Weapon** | Axe | Dagger | Staff |
| **HP** | 130 | 100 | 100 |
| **Attack** | 12 | 10 | 18 |
| **Defense** | 8 | 5 | 2 |
| **Resource** | 🔥 Wrath | 💨 Speed | 💧 Mana |
| **Defensive Skill** | Block | Dodge | Invisibility |
| **Buff Skill** | Wrath | Focus | Meditate |
| **Special** | Consumes Wrath | Consumes Speed | Consumes Mana |

---

## 👾 Enemies

| Symbol | Name | HP | Attack | Defense | Color |
|--------|------|----|--------|---------|-------|
| `ρ` | Rat | 20 | 5 | 0 | Grey |
| `ω` | Bat | 25 | 8 | 0 | Purple |
| `Φ` | Goblin | 50 | 12 | 3 | Green |

Enemy spawn counts scale per floor using multiplier arrays — Rats dominate early floors, Goblins take over in the deeper floors, and Floor 10 is reserved for a boss encounter.

**AI behaviour:**
- Within 15 tiles: enemy moves directly toward the player
- Outside range: enemy wanders randomly on valid floor tiles
- Each enemy has a `speed` field and `moveCounter` — faster enemies act more frequently per player turn

---

## 🗂 Project Structure

```
src/
└── main/java/RougelikeRPG/
    ├── Main.java          — Entry point
    ├── Entity.java        — Base entity class
    ├── Player.java        — Player logic + class system
    ├── Enemy.java         — Enemy AI
    ├── Dungeon.java       — Dungeon game loop
    ├── DungeonMap.java    — Map generation + rendering
    ├── Town.java          — Overworld map + loop
    ├── GameMap.java       — Map interface
    ├── MapLoop.java       — Loop interface
    └── Tiles.java         — Tile enum
```

---

## 🔮 Roadmap

- [x] Procedural dungeon generation (cellular automata)
- [x] Player movement + collision
- [x] Multi-floor dungeon with ladder transitions
- [x] Enemy spawning + radius-based AI
- [x] Floor-scaled enemy difficulty
- [x] Town/overworld map
- [x] Playable class system (stats)
- [ ] **Phase 6 — Combat system** ← current
- [ ] Phase 7 — Items & Inventory
- [ ] Phase 8 — Game State pattern (MainMenu, GameOver, Victory)
- [ ] Phase 9 — Polish (FOV, colors, title screen, high score)
- [ ] Phase 10 — Boss floor (Floor 10)

---

## 🛠 Tech Stack

- **Language:** Java
- **Terminal UI:** [Lanterna](https://github.com/mabe02/lanterna)
- **Build:** Maven
- **IDE:** IntelliJ IDEA
- **Font:** JetBrains Mono (required for correct Unicode rendering)

---

## 📄 License

MIT License — feel free to use, fork, and learn from this project.
