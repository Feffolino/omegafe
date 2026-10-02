# Omega Flashlight FE Batteries

Forge 1.20.1 port of the addon for [Omega Flashlight](https://www.curseforge.com/minecraft/mc-mods/omega-flashlight) that adds three rechargeable batteries (Small, Medium, Large) that charge with Forge Energy in any FE charger. Omega's original batteries stay single-use. See `SUMMARY.md` for details and `CURSEFORGE.md` for the CurseForge page.

## Requirements
- Minecraft 1.20.1
- Forge 47.4.23+
- Omega Flashlight 0.7.6+ for Forge 1.20.1 (required dependency)

## Building

1. Ensure `omegaflashlight-0.7.6-1.20.1.jar` is placed in `libs/`. It is a compile-only and local runtime dependency; it is never bundled in the built jar.
2. Run `./gradlew build` (Gradle 8.10.2 uses the Java 17 toolchain downloaded automatically via Foojay).
3. The jar is produced at `build/libs/omegafe-1.0.0-1.20.1.jar`.

## Art

- `art/make_art.py` regenerates `icon.png`, `icon_1024.png`, `banner.png` and `logo.png` from the item textures.
- `art/make_textures.py` generates old placeholder textures: do NOT run it, to avoid overwriting hand-drawn textures.

## License

Code: MIT, see `LICENSE`. The battery textures are derived from Omega Flashlight by omegaru010 and are licensed under LGPL-3.0-only (see notice in `LICENSE`).
