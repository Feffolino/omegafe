# Omega Flashlight FE Batteries — CurseForge page

| Field | Value |
|---|---|
| Project name | Omega Flashlight FE Batteries |
| Summary (max 50 chars) | Rechargeable FE batteries for Omega Flashlight |
| Project avatar | `art/icon.png` (400x400) |
| Description header | `art/banner.png` (1600x900), upload to the project's Images gallery and link it at the top of the description |
| Categories | Addons, Technology / Energy, Utility & QoL |
| Game version / Loader | 1.20.1 / Forge |
| License | MIT (battery textures LGPL-3.0, see Description) |
| Relations | Omega Flashlight: Required Dependency |
| File | `build/libs/omegafe-1.0.0-1.20.1.jar`, release type Release, changelog "Initial release for Forge 1.20.1." |

Alternative summaries (all under 50 chars):
- Omega Flashlight batteries you can charge with FE
- Add FE-rechargeable batteries to Omega Flashlight

---

## Description (paste into the CurseForge editor)

![Omega Flashlight FE Batteries](BANNER_URL)

An addon for **[Omega Flashlight](https://www.curseforge.com/minecraft/mc-mods/omega-flashlight)** that adds three **Rechargeable Batteries**: Small, Medium and Large.

They go in the flashlight like the original batteries: same battery slots, same drain, same charge bar under the item, and the same battery HUD in the bottom-left corner. They also charge with **Forge Energy (FE)** in **any block that charges FE items**:

- Mekanism Energy Cube (charge slot)
- Mekanism Chargepad
- Any other mod's charger, battery box or machine slot that charges FE items

Omega Flashlight's original batteries are not changed. They stay **single-use**.

### Crafting
Each rechargeable battery is crafted from the original battery of the same size. It comes out **empty**, so charge it before use.

| Battery | Recipe (shaped) |
|---|---|
| Rechargeable Small | Small Battery in the center, 2 Redstone left/right, 2 Copper Ingots top/bottom |
| Rechargeable Medium | Medium Battery in the center, 2 Redstone left/right, 2 Gold Ingots top/bottom |
| Rechargeable Large | Large Battery in the center, 2 Redstone Blocks left/right, 2 Diamonds top/bottom |

### Config (`config/omegafe-common.toml`)
- `fePerCharge`: FE per charge point, default **2** (1 point = 1 tick of light).
- `capacity.small / medium / large`: capacity in charge points, defaults **6000 / 18000 / 54000** (configured for Rat Lab). Changes apply live or upon reload.

### Commands (operators)
- `/give @p omegafe:rechargeable_small_battery{omegafe_charge:3000}` gives a battery with 3000 charge points.
- `/give @p omegafe:rechargeable_small_battery{omegafe_charge_percent:100}` gives a fully charged battery. Works with every Omega battery.

### Details
- Batteries **only receive** energy. They can't be drained to power other machines.
- They can't be repaired or combined in an anvil or crafting grid.
- Works in singleplayer and on dedicated servers. Install it on both client and server, with the same config.

### Requirements
- Minecraft 1.20.1, Forge 47.4.23+
- Omega Flashlight 0.7.6 or later (**required**)

This addon contains no Omega Flashlight code. Get Omega Flashlight from its own page.

The rechargeable battery textures are derived from Omega Flashlight's battery textures by omegaru010, licensed under LGPL-3.0. Those textures remain under LGPL-3.0; all other parts of this addon are MIT.

### Modpacks
Free to use in any modpack. Made for the Rat Lab modpack.

### License
Code: MIT. Battery textures: LGPL-3.0 (derived from Omega Flashlight).
