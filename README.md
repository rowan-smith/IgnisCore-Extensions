# IgnisCore Extensions

Bundled block and item extensions for [IgnisCore](https://github.com/rowan-smith/IgnisCore) — fuse explosives, utility blocks, link tools, consumables, throwables, and more.

**Documentation:** https://igniscore.rono.dev/developers

## Layout

```
shared/   # Optional helpers (ExtensionShared facade)
blocks/   # Block extension modules
items/    # Item extension modules
```

## Build

The IgnisCore API is resolved from [JitPack](https://jitpack.io/#rowan-smith/IgnisCore) (`com.github.rowan-smith.IgnisCore:api:main`). Build extensions with:

```bash
mvn clean package
```

Each extension module produces `blocks/<name>/target/<name>.jar` or `items/<name>/target/<name>.jar`.

## Integration with IgnisCore

IgnisCore consumes this repository as a git submodule at `extensions/` and bundles the built JARs into the bootstrap plugin. See the [IgnisCore README](https://github.com/rowan-smith/IgnisCore#build) for the full reactor build.
