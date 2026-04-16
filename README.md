# Universe Journey

`UniverseJourney` is a Fabric mod focused on high-power progression, built around two content lines: **Supreme** and **Universe**.

The mod includes custom equipment, dimensions, boss entities, and advanced gameplay mechanics, expanding the late-game experience with high-tier utility and combat tools.

---

## 🎮 For Users

### Core Features
* **Supreme Progression**: Focuses on utility with the `Supreme Mobile` and specialized blocks like the `Supreme Worktable` and `Reserver`.
* **Universe Progression**: The pinnacle of power, featuring the `Universe Star`, `Omni Blade`, and the comprehensive `Universe` armor set.
* **Custom Dimensions**: Explore the **Ore Continent** and the **Harvest Continent**, featuring unique biomes like `Melon Jungle` and `Pumpkin Gorge`.
* **Boss Encounters**: Challenge the `Universe Guardian` or the multi-phase `Skulk Behemoth`.

### Controls & Mechanics
* **Equipment Mode Toggle**: Press **`U`** (default) to switch modes for the Supreme Mobile, Universe Swords, and Console.
* **Universe Boots Dash**: While **sneaking**, double-tap movement keys (`W/A/S/D`) to perform a high-speed dash.
* **Remote Interaction**:
  * Use the `Universe Console` for block binding and remote menu access.
  * The `Supreme Mobile` can identify compatible blocks and open virtual GUIs remotely.

### Requirements
* **Minecraft**: `26.1`
* **Fabric Loader**: `>=0.19.1`
* **Fabric API**: `0.145.1+26.1`
* **Java**: `25`

---

## 🛠️ For Developers

> **[ !!! IMPORTANT !!! ]**: This project is currently under **active personal development**. To avoid version fragmentation and sync issues, please **do not fork** this project unless absolutely necessary.

### Technical Overview
* **Namespace**: `universejourney`
* **Entrypoints**:
  * Main: `roeyqian.universejourney.UniverseJourney`
  * Client: `roeyqian.universejourney.UniverseJourneyClient`
* **Custom Recipe Types**:
  * `universejourney:supreme_crafting`
  * `universejourney:universe_crafting`
  * `universejourney:universe_cooking`

### Build Instructions
The project uses Java 25. Ensure your environment is configured correctly before building.

```powershell
# Build the project
.\gradlew.bat build
```
Build artifacts can be found in `build/libs`.

---

## 📜 License
* **GPL-3.0-only**
* See the `LICENSE` file for full text.