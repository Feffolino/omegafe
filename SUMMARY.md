# Omega Flashlight FE Batteries (Forge 1.20.1)

**Summary:** Rechargeable FE batteries for Omega Flashlight on Forge 1.20.1.

Omega Flashlight FE Batteries adds three rechargeable batteries to Omega Flashlight: Small, Medium and Large. Drop one into your flashlight and it works like an original battery, with the same slots, charge bar and bottom-left HUD. When it runs low, put it in any FE charger, such as a Mekanism Energy Cube or a Chargepad.

You craft each one from an original battery of the same size, plus redstone and copper, gold or diamonds. It comes out empty, so charge it before your first trip. Omega's own batteries still get used up and can't be recharged.

Capacity and FE cost per charge point are configurable in the common config file (`config/omegafe-common.toml`), with defaults of 6000 / 18000 / 54000 ticks of light (configured for the Rat Lab modpack). Requires the original Omega Flashlight mod, version 0.7.6 or later for Forge 1.20.1. Free to use in any modpack. MIT license; the battery textures are derived from Omega Flashlight's and stay under LGPL-3.0.

## Stato di verifica

### Verificato
- [x] Struttura progetto Forge 1.20.1 con ModDevGradle `legacyforge` e toolchain Java 17 via Foojay.
- [x] Decompilazione e analisi API `com.omega.flashlight.item.BatteryItem` (0.7.6): la carica corrisponde alla durability (`maxDamage - damageValue`), gli slot accettano `instanceof BatteryItem`.
- [x] Registrazione 3 batterie estendendo `BatteryItem` con `initCapabilities` per capability FE (`ForgeCapabilities.ENERGY`).
- [x] Config `COMMON` (`OmegaFEConfig`) con capacità dinamiche a runtime (6000/18000/54000) e conversione FE (2 FE per tick).
- [x] Ricette con output vuoto (`EmptyBatteryRecipe` custom `RecipeSerializer`).
- [x] Tooltip client con rimozione della durability vanilla in favore della carica FE.
- [x] Tag NBT one-shot (`omegafe_charge`, `omegafe_charge_percent`) gestiti al tick del giocatore.
- [x] Assets (texture, modelli, lang en_us / it_it, logo).

### Da testare nel modpack
- [ ] Test in-game con Mekanism Energy Cube / Chargepad nel modpack Rat Lab.
- [ ] Interazione diretta nell'HUD e nello slot della torcia in ambiente di gioco completo.
