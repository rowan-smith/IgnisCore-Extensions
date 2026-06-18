# IgnisCore Extensions

Bundled block and item extensions for [IgnisCore](https://github.com/rowan-smith/IgnisCore) — fuse explosives, utility blocks, link tools, consumables, throwables, and more.

**Documentation:** https://igniscore.rono.dev/developers

## Layout

```
shared/                    # Optional helpers — access via ExtensionShared only
  api/                     # Public config, GUI, and throwable types
  impl/                    # Package-private Support implementations + *Api facades
packs/
  explosions-pack/         # TNT, charges, mines, grenades, detonator
  linking-pack/            # Remote link tools and paired blocks
  farming-pack/            # Crops, livestock, irrigation
  kitchen-pack/            # Brewing, cooking, consumables
  crafting-pack/           # Processing, storage, repair
  exploration-pack/        # Scanning, wayfinding, mapping
  building-pack/           # Decoration, construction tools
  utility-pack/            # Trade, chunk loading, mob utilities
  novelty-pack/            # Ambient and novelty content
```

Each pack contains `blocks/` and/or `items/` subdirectories with extension modules.

## Build

The IgnisCore API is resolved from [JitPack](https://jitpack.io/#rowan-smith/IgnisCore) (`com.github.rowan-smith.IgnisCore:api:main`). Build extensions with:

```bash
mvn clean package
```

Each extension module produces `packs/<pack>/<blocks|items>/<name>/target/<name>.jar`. Pack builds embed the `shared` helper classes into every extension JAR so `ExtensionShared` is available at runtime.

## Integration with IgnisCore

IgnisCore consumes this repository as a git submodule at `extensions/` and bundles the built JARs into the bootstrap plugin. See the [IgnisCore README](https://github.com/rowan-smith/IgnisCore#build) for the full reactor build.
