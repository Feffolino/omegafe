# CurseForge upload checklist

Files in this folder: `summary.txt` (project summary), `description.md` (project description, paste in the
Markdown editor).

## 1. Before uploading
- [ ] Icon in the mod: `src/main/resources/logo.png`.
- [ ] Rebuild: `./gradlew build` -> `build/libs/omegafe-1.0.0-1.20.1.jar`.
- [ ] Banner image: upload `curseforge/icon/banner.png` to the project's Images gallery and replace `BANNER_URL` in `description.md`.

## 2. Create the project (Minecraft > Mods)
| Field | Value |
|---|---|
| Name | Omega Flashlight FE Batteries |
| Slug | `omega-flashlight-fe-batteries` |
| Summary | contents of `summary.txt` |
| Description | contents of `description.md` (Markdown) |
| Avatar | `curseforge/icon/icon.png` (400x400) |
| Main category | Addons |
| Other categories | Technology, Energy, Utility & QoL |
| License | MIT |
| Source URL | https://github.com/Feffolino/omegafe |
| Issues URL | https://github.com/Feffolino/omegafe/issues |

## 3. Upload the file
| Field | Value |
|---|---|
| File | `build/libs/omegafe-1.0.0-1.20.1.jar` |
| Display name | Omega Flashlight FE Batteries 1.0.0 (1.20.1 Forge) |
| Release type | Release |
| Game version | 1.20.1 |
| Mod loader | Forge |
| Environment | Client and Server (required on both) |
| Java | Java 17 |
| Changelog | Port to Forge 1.20.1 (Forge 47.4.23+). |

## 4. Relations (file upload page, "Related projects")
| Project | Type |
|---|---|
| Omega Flashlight | Required dependency |

## 5. After approval
- [ ] Tag the release on GitHub: `git tag v1.0.0-1.20.1 && git push origin v1.0.0-1.20.1`.