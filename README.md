# ☻ Terminal Roguelike Dungeon Crawler

A dungeon crawler that runs entirely in the terminal. Built in Java using the Lanterna library for rendering.
Procedurally generated floors, turn-based combat, multiple enemy types, and a class system with unique skills.

---

## Features

- **Procedural generation** — Cellular automata dungeon generation for organic, cave-like floors
- **10-floor dungeon** — Scales in difficulty, with Floor 10 reserved for a boss encounter
- **Turn-based gameplay** — The game only updates on player input; no real-time pressure
- **Three playable classes** — Warrior, Rogue, and Wizard, each with unique stats and a resource-based skill system
- **Enemy AI** — Radius-based activation; enemies pursue the player within 10 tiles
- **Speed tiers** — Different enemy types move at different rates, not every enemy moves every turn
- **Town / overworld** — A town map with an Inn, Shop, Blacksmith, Town Hall, cave entrance, and forest entrance
- **Status HUD** — Live display of HP, floor, attack, and defense

---

## Controls

| Key | Action |
|-----|--------|
| `W A S D` or Arrow Keys | Move / Bump attack |
| `I` | Open inventory *(coming soon)* |
| `U` | Use / equip item *(coming soon)* |
| `Q` | Quit |

---

## Classes

| | ⚔ Warrior | 🗡 Rogue | 🔮 Wizard |
|---|---|---|---|
| Race | Dwarf | Elf | Human |
| HP | High | Balanced | Balanced |
| Defense | High | Balanced | Low |
| Attack | Balanced | Balanced (×2 hit) | Highest |
| Resource | 🔥 Wrath | 💨 Speed | 💧 Mana |

Each class gains its resource passively each turn. Skills are activated mid-combat:

- **Defensive skill** — Avoids or reduces incoming damage, grants resource
- **Buff skill** — Temporary stat boost, grants resource
- **Special skill** — Consumes all accumulated resource; more resource = stronger effect

### Warrior
| Skill | Effect |
|-------|--------|
| 🛡 Block | Greatly reduce incoming damage + gain Wrath |
| 😡 Wrath | Double Wrath gain for 2 turns + Weaken enemy |
| 🔥 Special | Consumes Wrath — more = stronger burst damage |

### Rogue
| Skill | Effect |
|-------|--------|
| 💨 Dodge | Avoid attack completely + gain Speed + inflict Bleeding (2 turns) |
| 🎯 Focus | Gain more Speed for 2 turns + hit harder |
| ⚡ Special | Consumes Speed — more = stronger multi-hit effect |

### Wizard
| Skill | Effect |
|-------|--------|
| 🌫 Invisibility | Avoid attack completely + gain Mana |
| 🧘 Meditate | Recover HP + gain more Mana for 2 turns |
| 💧 Special | Consumes Mana — more = stronger spell / AoE |

---

## Enemies

| Symbol | Name | Color | Notes |
|--------|------|-------|-------|
| `ω` | Bat | Purple | Fast, low HP |
| `Φ` | Goblin | Green | Balanced stats |
| `ρ` | Rat | Grey | Weak, early floors only |

Enemies spawn in greater numbers and variety on deeper floors. Each enemy type has its own speed tier — not every enemy moves every turn.

---

## Tech Stack

- **Language:** Java (OOP)
- **Terminal UI:** [Lanterna](https://github.com/mabe02/lanterna) (via Maven)
- **IDE:** IntelliJ IDEA
- **Font:** JetBrains Mono (required for correct Unicode symbol rendering)
- **Build:** Maven

---

## How to Run

### Requirements
- Java 17+
- Maven

### Steps

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
cd YOUR_REPO_NAME
mvn compile
mvn exec:java -Dexec.mainClass="com.yourpackage.Main"
```

> **Note:** Run in a real terminal, not the IDE's built-in console, for correct rendering.  
> On Windows, **Windows Terminal** works best. On Linux/macOS any standard terminal works.

---

## Architecture

```
Entity (base)
├── Player     — input, movement, class & resource system
└── Enemy      — aiTurn(), speed tiers, symbol & color

DungeonMap     — holds tile data only (cellular automata generation)
Dungeon        — owns the game loop via start(Player, Screen)
CombatSystem   — resolves attack(attacker, defender)
Renderer       — draws map + entities via Lanterna

GameState (interface)
├── MainMenuState
├── OverworldState   — town map, NPC buildings
├── DungeonState     — dungeon loop, enemies, combat
├── GameOverState
└── VictoryState
```

The **Game State pattern** keeps each area self-contained. Each state runs its own loop and returns the next state on transition — `Main` just calls `run()` in a loop.

---

## Development Roadmap

| Phase | Focus | Status |
|-------|-------|--------|
| Setup | Project, Maven, Lanterna, GitHub | ✅ Done |
| Phase 1 | Static map rendering | ✅ Done |
| Phase 2 | Player movement + HUD | ✅ Done |
| Phase 3 | Procedural generation (cellular automata) | ✅ Done |
| Phase 5 | Enemies + AI | ✅ Done |
| Phase 6 | Combat system | 🔨 In Progress |
| Phase 7 | Items + Inventory | 📋 Upcoming |
| Phase 9 | Polish + title/game over screens | 📋 Later |
| Phase 10 | GitHub + portfolio presentation | 📋 Later |

---

## License

MIT — free to use, fork, and learn from.
