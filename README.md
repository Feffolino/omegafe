# Omega Flashlight FE Batteries

NeoForge 1.21.1 addon for [Omega Flashlight](https://www.curseforge.com/minecraft/mc-mods/omega-flashlight) that adds three rechargeable batteries (Small, Medium, Large) that charge with Forge Energy in any FE charger. Omega's original batteries stay single-use. See `SUMMARY.md` for the player-facing description and `CURSEFORGE.md` for the full page text.

## Building

1. Download `omegaflashlight-1.6.6-1.21.1.jar` and put it in `libs/`. It is a compile-only dependency and is not part of this repository or of the built jar.
2. Run `./gradlew build` (Gradle 9.2.1 needs a JDK 17+ to run; the Java 21 toolchain is downloaded automatically).
3. The jar is `build/libs/omegafe-1.0.0.jar`.

## Art

- `art/make_art.py` regenerates `icon.png`, `icon_1024.png`, `banner.png` and `logo.png` from the item textures.
- `art/make_textures.py` generates the old placeholder textures. Don't run it: it overwrites the current hand-drawn textures in `src/main/resources/assets/omegafe/textures/item/`.

## License

Code: MIT, see `LICENSE`. The battery textures are derived from Omega Flashlight's and are LGPL-3.0-only (notice in `LICENSE`).
