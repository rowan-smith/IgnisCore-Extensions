#!/usr/bin/env python3
"""Sync extension display lore, behavior wiring, and gameplay tests across all packs."""

from __future__ import annotations

import argparse
import json
import re
import textwrap
from pathlib import Path

import yaml

ROOT = Path(__file__).resolve().parents[1]
PACKS = ROOT / "packs"
CATALOG_PATH = Path(__file__).with_name("extension-catalog.json")
DISPLAY_OVERRIDES_PATH = Path(__file__).with_name("display-overrides.json")
TEXTURE_FALLBACK_OVERRIDES_PATH = Path(__file__).with_name("texture-fallback-overrides.json")

MYSTERIOUS_IDS = {
    "glitch-tnt", "mirage-tnt", "hologram-tnt", "phantom-tnt", "mirror-world-tnt",
    "mimic-tnt", "rift-generator", "ouija-slab", "quantum-coin", "gravity-marble",
    "unlabeled-potion", "doppelganger-block", "fake-bedrock", "pause-tnt", "blink-tnt",
}

REMOTE_CHARGE_IDS = {"signal-charge", "remote-c4", "timed-charge", "scare-charge"}
BURIED_MINE_IDS = {
    "shrapnel-mine", "ember-mine", "claymore-mine", "bouncing-betty", "cascade-mine",
}
TRIPWIRE_IDS = {"tripwire-charge"}

JAVA_EFFECT_HINTS = [
    (r"acidCorrode", "corrodes stone and ore into air"),
    (r"freezeRadius|frost", "flash-freezes the area to ice"),
    (r"spawnSpider|entityPayload", "unleashes a hostile swarm"),
    (r"pullLootToward|blackHole", "drags entities inward before collapsing"),
    (r"launchUpward|trampoline", "launches everything skyward"),
    (r"directionalBlast|breach", "punches a shaped breach through walls"),
    (r"ringBlast|torus", "carves a ring-shaped cavity"),
    (r"tunnel|drill|bore", "bores a tunnel along your aim"),
    (r"poisonCloud|ENTITY_EFFECT", "blankets the area in toxic haze"),
    (r"lightning|strikeLightning", "calls chained lightning"),
    (r"wildfire|spreadFire", "plants spreading wildfire"),
    (r"tsunami|FALLING_WATER", "summons a surging wave"),
    (r"mudslide|mud", "collapses terrain into flowing mud"),
    (r"swapPlayers|swap", "swaps nearby players on detonation"),
    (r"stasis|freezeEntities", "locks entities in stasis"),
    (r"decoyExplosions|playDecoy", "fakes blasts while the real one lands elsewhere"),
    (r"cycleBlockPreviews|glitch", "reality stutters and flickers"),
    (r"ringBlockPreviews|hologram", "projects dancing decoy blocks"),
    (r"phantom|invisible|removeEntity", "vanishes before the hidden blast"),
    (r"seismic|gridBlast", "ripples the ground in rolling shockwaves"),
    (r"shrapnel|debris", "sprays deadly shrapnel"),
    (r"concussion|knockback", "hurls entities with massive knockback"),
    (r"depthCharge|underwater", "clears submerged blocks only"),
    (r"screenShake", "violently rattles nearby screens"),
    (r"inferno|FLAME", "erupts into spreading flame"),
    (r"echo|aftershock", "echoes with phantom aftershocks"),
    (r"wormhole|pull", "pulls matter inward before erupting"),
    (r"orbit|physics\(\)\.orbit", "spins victims into orbit"),
    (r"magnet|pullLoot", "yanks loot inward then scatters it"),
    (r"bridge|scaffold", "builds structure as it counts down"),
    (r"splitter|cardinal", "splits into multiple blasts"),
    (r"ricochet|bounce", "ricochets blasts along a path"),
    (r"phase|passThrough", "detonates through solid blocks"),
    (r"silent", "detonates with almost no sound"),
    (r"antiGravity|levitat", "lifts entities in anti-gravity"),
    (r"invisiwall|glass", "raises then shatters a hidden barrier"),
    (r"doppelganger|showBlockPreview", "mimics innocent stone until triggered"),
    (r"nuke|screenShake", "levels everything in a cataclysmic blast"),
    (r"spider", "unleashes a spider storm"),
    (r"buriedMine|proximity", "arms when creatures draw near"),
    (r"remote|detonator", "detonates remotely via detonator"),
    (r"throwable|throwProjectile", "flies on a timed fuse after thrown"),
    (r"flashbang|blind", "blinds and disorients without killing"),
    (r"smoke|SMOKE", "billows thick concealment smoke"),
    (r"sticky|latch", "sticks to the first surface hit"),
    (r"scan\.|bonemeal|crop", "nurtures or scans nearby crops"),
    (r"gui\.|openInventory|OPEN", "opens a storage or crafting GUI"),
    (r"link\(|LinkItem", "links to paired blocks for remote control"),
    (r"consumable|applyPotionEffect", "grants a potion effect when used"),
    (r"ticks\(\)\.start|tickPeriod", "runs a passive effect while placed"),
    (r"quarry|collect", "vacuums nearby drops into storage"),
    (r"xp|experience", "pulls experience orbs inward"),
    (r"ouija|Ouija", "whispers when enough souls gather"),
    (r"rift|Rift", "tears a rift in local space"),
    (r"quantum|coin", "exists in superposition until observed"),
    (r"gravity.*marble|GravityMarble", "rolls with impossible eagerness"),
]

MYSTERIOUS_LORE = {
    "glitch-tnt": ("&dGlitch TNT", ["&7It &dflickers&7 between things it cannot decide to be.", "&7Reality &dstutters&7 wherever it sits.", "&7Best not to look too closely."]),
    "mirage-tnt": ("&dMirage TNT", ["&7The blast you see is &dnever&7 where it lands.", "&7Heat, light, and cruel misdirection.", "&7Chase the illusion at your peril."]),
    "hologram-tnt": ("&dHologram TNT", ["&7Is it really there? Are &dany&7 of them?", "&7Copies dance while the real fuse burns.", "&7Seeing is no longer believing."]),
    "phantom-tnt": ("&dPhantom TNT", ["&7Now you see it. &dSoon&7 you won't.", "&7The blast seems to come from nowhere.", "&7You'll never spot where it went."]),
    "mirror-world-tnt": ("&dMirror World TNT", ["&7Whatever happens here also happens &dthere&7.", "&7A reflection across an unseen pane.", "&7Two worlds, one fate."]),
    "mimic-tnt": ("&dMimic TNT", ["&7One of them is real. The rest only &dpretend&7.", "&7Good luck guessing which is which.", "&7Deception scatters in every direction."]),
    "rift-generator": ("&dRift Generator", ["&7Something on the other side is &dpulling&7.", "&7The air bends inward toward the tear.", "&7Best not to be standing too close."]),
    "ouija-slab": ("&dOuija Slab", ["&7It only speaks when others &dgather&7 round.", "&7Stand at its corners and listen.", "&7The letters know more than they should."]),
    "quantum-coin": ("&dQuantum Coin", ["&7Heads or tails, until you &dlook&7.", "&7Both faces, neither face, every flip.", "&7Chance has never felt so uncertain."]),
    "gravity-marble": ("&dGravity Marble", ["&7It rolls a little too &deagerly&7.", "&7A glittering trail follows wherever it goes.", "&7Where it stops, nobody knows."]),
    "unlabeled-potion": ("&dUnlabeled Potion", ["&7No label, no hints, no promises.", "&7Drink it and find out what it &ddoes&7.", "&7Some bottles keep their secrets till the last sip."]),
    "doppelganger-block": ("&dDoppelgänger Block", ["&7It looks like an ordinary &fstone block&7.", "&7But it is most certainly &dnot&7.", "&7Trust nothing that wears another's face."]),
    "fake-bedrock": ("&dFake Bedrock", ["&7Unbreakable, unmovable, the bottom of the world.", "&7Or so it would have you &dbelieve&7.", "&7Some foundations are not what they seem."]),
    "pause-tnt": ("&dPause TNT", ["&7The countdown &dstops&7. And waits.", "&7Just when you relax, time resumes.", "&7Patience is its cruelest trick."]),
    "blink-tnt": ("&dBlink TNT", ["&7Here. There. &dEverywhere&7 in between.", "&7It teleports when you aren't &dwatching&7.", "&7Detonation is merely a &dsuggestion&7."]),
}


def load_display_overrides() -> dict[str, dict]:
    if not DISPLAY_OVERRIDES_PATH.exists():
        return {}
    return json.loads(DISPLAY_OVERRIDES_PATH.read_text(encoding="utf-8"))


def load_texture_fallback_overrides() -> dict[str, str]:
    if not TEXTURE_FALLBACK_OVERRIDES_PATH.exists():
        return {}
    return json.loads(TEXTURE_FALLBACK_OVERRIDES_PATH.read_text(encoding="utf-8"))


DISPLAY_OVERRIDES = load_display_overrides()
TEXTURE_FALLBACK_OVERRIDES = load_texture_fallback_overrides()

# Bare ids that resolve to another loaded extension before the igniscore catalog.
EXTENSION_TEXTURE_FALLBACK_IDS = frozenset({"grenade", "detonator"})

# Logical names that map to a different vanilla block id.
MINECRAFT_BLOCK_FALLBACK_ALIASES = {
    "grass": "grass_block",
    "hay": "hay_block",
    "redstone": "redstone_block",
    "tripwire": "tripwire_hook",
    "trapdoor": "oak_trapdoor",
}

MINECRAFT_BLOCK_FALLBACK_IDS = frozenset(
    {
        "anvil",
        "barrel",
        "beacon",
        "bedrock",
        "bell",
        "brewing_stand",
        "chest",
        "composter",
        "dispenser",
        "ender_chest",
        "farmland",
        "glass",
        "grindstone",
        "lantern",
        "lever",
        "lodestone",
        "obsidian",
        "smoker",
        "stone",
        "tnt",
    }
    | set(MINECRAFT_BLOCK_FALLBACK_ALIASES)
    | set(MINECRAFT_BLOCK_FALLBACK_ALIASES.values())
)

MINECRAFT_ITEM_FALLBACK_IDS = frozenset(
    {"book", "compass", "map", "potion", "shears", "spyglass"}
)


def format_texture_fallback(logical: str, *, kind: str) -> str:
    """Map inferred fallback keys to IgnisCore namespaces (minecraft / igniscore / extension id)."""
    if not logical or ":" in logical:
        return logical
    if logical in EXTENSION_TEXTURE_FALLBACK_IDS:
        return logical
    if logical in MINECRAFT_BLOCK_FALLBACK_ALIASES:
        return f"minecraft:{MINECRAFT_BLOCK_FALLBACK_ALIASES[logical]}"
    if kind == "blocks" and logical in MINECRAFT_BLOCK_FALLBACK_IDS:
        return f"minecraft:{logical}"
    if kind == "items" and logical in MINECRAFT_ITEM_FALLBACK_IDS:
        return f"minecraft:{logical}"
    return f"igniscore:{logical}"


def humanize(ext_id: str) -> str:
    name = ext_id.replace("-", " ").title()
    return name.replace("Tnt", "TNT").replace("C4", "C4").replace("Mri", "MRI").replace("Xp", "XP")


def strip_title_color(title: str) -> str:
    _, name = strip_title_decorations(title)
    return name


def strip_title_decorations(title: str) -> tuple[str, str]:
    color_match = re.match(r"^(&[0-9a-fk-or])", title, re.I)
    color = color_match.group(1) if color_match else ""
    name = title[len(color):] if color else title
    name = re.sub(r"^&l", "", name, flags=re.I)
    return color, name


def format_display_title(title: str, default_color: str = "&6") -> str:
    color, name = strip_title_decorations(title)
    if not name:
        name = title
    if not color:
        color = default_color
    return f"{color}&l{name}"


def ensure_non_italic_lore(line: str) -> str:
    line = re.sub(r"^(&r&r)+", "", line)
    return f"&r&r{line}"


def has_accent_color(line: str) -> bool:
    return any(m.lower() != "&7" for m in re.findall(r"&[0-9a-fk-or]", line, re.I))


def title_color(ext_id: str, rel: str) -> str:
    if ext_id in MYSTERIOUS_IDS:
        return "&d"
    pack = rel.split("/")[1] if rel.startswith("packs/") else ""
    kind = "items" if "/items/" in rel else "blocks"
    if pack == "explosions-pack":
        if ext_id == "nuke":
            return "&a"
        if ext_id == "detonator":
            return "&e"
        if ext_id in REMOTE_CHARGE_IDS:
            return "&6"
        if "mine" in ext_id:
            return "&8"
        if kind == "items" or "grenade" in ext_id:
            return "&c"
        return "&c"
    if pack == "kitchen-pack":
        return "&e" if kind == "items" else "&6"
    if pack == "farming-pack":
        return "&a"
    if pack == "exploration-pack":
        return "&b"
    if pack == "building-pack":
        return "&6"
    if pack == "crafting-pack":
        return "&e"
    if pack == "linking-pack":
        return "&9"
    if pack == "novelty-pack":
        return "&d"
    if pack == "utility-pack":
        return "&6"
    return "&6"


def themed_title(ext_id: str, rel: str, title: str | None = None) -> str:
    if title:
        color, name = strip_title_decorations(title)
        if not name:
            name = humanize(ext_id)
        if not color:
            color = title_color(ext_id, rel)
        return f"{color}&l{name}"
    return f"{title_color(ext_id, rel)}&l{humanize(ext_id)}"


def polish_lore_lines(lines: list[str]) -> list[str]:
    skip = {"&7See item lore for usage hints."}
    polished: list[str] = []
    for raw in lines:
        if raw in skip:
            continue
        line = raw.replace("flint & steel", "flint and steel").replace("&33-block", "&e3-block")
        if not line.startswith("&"):
            line = "&7" + line
        prefix = ""
        body = line
        color_prefix = re.match(r"^((?:&[0-9a-fk-or])*)", line, re.I)
        if color_prefix and color_prefix.group(1):
            prefix = color_prefix.group(1)
            body = line[len(prefix) :]
        if body and body[0].islower():
            body = body[0].upper() + body[1:]
        if not has_accent_color(prefix + body):
            body = re.sub(
                r"\b(Right-click|Left-click|Place|Open|Throw|Drink|Eat|Bury|Link|Toggle|Scan|Sprinkle|Ring|Vacuums)\b",
                r"&e\1&7",
                body,
                count=1,
                flags=re.I,
            )
            body = re.sub(
                r"\b(\d+(?:\.\d+)?)\s*(-block|-slot|-row|-radius|s\b)",
                r"&e\1&7\2",
                body,
            )
            body = re.sub(
                r"\bslot\s+(\d+)\b",
                r"&eslot &e\1&7",
                body,
                flags=re.I,
            )
            body = re.sub(
                r"\b(flint and steel|fire charge|detonator|gold ingot|glass bottle|water bucket|bone meal)\b",
                r"&f\1&7",
                body,
                count=1,
                flags=re.I,
            )
            body = re.sub(
                r"\b(Enchant|Amethyst|End-rod|Campfire|Witch|Composter|Honey|Grindstone|Glow-squid|"
                r"Redstone|Obsidian|Chronicle|Bell|Trapdoor|Passive|Remote|Spectate|Waypoint)\b",
                r"&b\1&7",
                body,
                count=1,
                flags=re.I,
            )
            line = prefix + body
        polished.append(ensure_non_italic_lore(line))
    if len(polished) < 2:
        polished.append(ensure_non_italic_lore("&7Place or interact to use."))
    return polished[:5]


def normalize_display_meta(ext_id: str, rel: str, meta: dict) -> dict:
    title = themed_title(ext_id, rel, meta.get("title"))
    description = polish_lore_lines(list(meta.get("description") or []))
    return {
        "title": title,
        "description": description,
        "mysterious": bool(meta.get("mysterious")),
    }


def parse_header_summary(config_text: str) -> str | None:
    for line in config_text.splitlines():
        line = line.strip()
        if line.startswith("# ") and " — " in line and not line.startswith("# =="):
            return line.split(" — ", 1)[1].strip()
    return None


def load_config(path: Path) -> tuple[dict, str]:
    text = path.read_text(encoding="utf-8")
    return yaml.safe_load(text), text


def java_sources(module: Path) -> str:
    java_dir = module / "src/main/java"
    if not java_dir.exists():
        return ""
    return "\n".join(p.read_text(encoding="utf-8", errors="ignore") for p in java_dir.rglob("*.java"))


def infer_effect(java: str, ext_id: str, config: dict) -> str:
    for pattern, effect in JAVA_EFFECT_HINTS:
        if re.search(pattern, java, re.I):
            return effect
    custom = config.get("custom_data") or {}
    if custom.get("realExplosion") is False:
        return "unleashes its payload instead of a normal blast"
    if custom.get("fire"):
        return "detonates in a fiery explosion"
    if "mine" in ext_id:
        return "detonates when something wanders too close"
    if ext_id in REMOTE_CHARGE_IDS:
        return "detonates when triggered by a linked detonator"
    return "detonates in a powerful blast"


def fuse_seconds(config: dict) -> float:
    fuse = (config.get("custom_data") or {}).get("fuse", 80)
    try:
        return max(0.5, int(fuse) / 20.0)
    except (TypeError, ValueError):
        return 4.0


def infer_block_texture_fallback(ext_id: str, pack: str, profile: str) -> str:
    if ext_id in {"fake-bedrock", "doppelganger-block"}:
        return "bedrock"
    if ext_id == "invisiwall":
        return "glass"
    if ext_id in BURIED_MINE_IDS:
        return "mine"
    if ext_id in REMOTE_CHARGE_IDS or ext_id == "last-stand-charge":
        return "redstone"
    if ext_id in TRIPWIRE_IDS:
        return "tripwire"
    if ext_id == "wildfire-seed":
        return "grass"
    if ext_id == "stasis-field":
        return "obsidian"
    if pack == "explosions-pack":
        return "tnt"
    if pack == "farming-pack":
        if "glass" in ext_id:
            return "glass"
        if "bell" in ext_id:
            return "bell"
        if "coop" in ext_id or "cache" in ext_id or "nursery" in ext_id:
            return "barrel"
        if "compost" in ext_id:
            return "composter"
        if "sprinkler" in ext_id or "tray" in ext_id or "irrigation" in ext_id:
            return "farmland"
        return "hay"
    if pack == "kitchen-pack":
        if "brew" in ext_id or "accelerator" in ext_id or "infuser" in ext_id:
            return "brewing_stand"
        if "smoker" in ext_id or "oven" in ext_id or "drying" in ext_id:
            return "smoker"
        if "freezer" in ext_id:
            return "barrel"
        return "barrel"
    if pack == "building-pack":
        if "lantern" in ext_id or "floodlight" in ext_id:
            return "lantern"
        if "display" in ext_id or "pedestal" in ext_id:
            return "glass"
        return "barrel"
    if pack == "crafting-pack":
        if "repair" in ext_id:
            return "anvil"
        if "recycler" in ext_id or "sieve" in ext_id:
            return "grindstone"
        return "chest"
    if pack == "exploration-pack":
        if "beacon" in ext_id or "obelisk" in ext_id:
            return "beacon"
        if "radar" in ext_id or "echo" in ext_id or "sniffer" in ext_id or "camera" in ext_id:
            return "lodestone"
        if "quarry" in ext_id or "xp-vacuum" in ext_id:
            return "ender_chest"
        return "beacon"
    if pack == "linking-pack":
        if "bell" in ext_id:
            return "bell"
        if "lamp" in ext_id or "socket" in ext_id:
            return "lamp"
        if "valve" in ext_id or "hatch" in ext_id:
            return "trapdoor"
        if "sprinkler" in ext_id:
            return "dispenser"
        return "lever"
    if pack == "utility-pack":
        if "trade" in ext_id:
            return "barrel"
        if "chunk" in ext_id or "grinder" in ext_id:
            return "beacon"
        if "weather" in ext_id:
            return "glass"
        return "bell"
    if pack == "novelty-pack":
        if ext_id == "rift-generator":
            return "obsidian"
        return "stone"
    return "stone"


def infer_item_texture_fallback(ext_id: str, pack: str, profile: str) -> str:
    if ext_id == "detonator":
        return "detonator"
    if profile == "throwable" and pack == "explosions-pack":
        if "smoke" in ext_id:
            return "smoke"
        if "flare" in ext_id or "decoy" in ext_id:
            return "flare"
        return "grenade"
    if pack == "kitchen-pack":
        if any(token in ext_id for token in ("lunch", "broth", "tea", "espresso", "swab", "salt", "tonic", "shot", "coat")):
            return "potion"
        if ext_id in {"ghost-peppermint", "chorus-bite"}:
            return "food"
        if ext_id == "luck-dust":
            return "dust"
        return "potion"
    if pack == "farming-pack":
        if "shears" in ext_id:
            return "shears"
        if "seed" in ext_id:
            return "seeds"
        if "mulch" in ext_id:
            return "fertilizer"
        return "tool"
    if pack == "building-pack":
        if ext_id == "smoke-can":
            return "smoke"
        return "tool"
    if pack == "exploration-pack":
        if "book" in ext_id or "chronicle" in ext_id:
            return "book"
        if "compass" in ext_id or "structure-compass" in ext_id:
            return "compass"
        if "goggles" in ext_id or "stethoscope" in ext_id:
            return "spyglass"
        if "badge" in ext_id:
            return "badge"
        if "atlas" in ext_id or "keyring" in ext_id or "chunk-grid" in ext_id:
            return "map"
        return "compass"
    if pack == "linking-pack":
        if "wrench" in ext_id:
            return "tool"
        return "remote"
    if pack == "novelty-pack":
        if ext_id == "quantum-coin":
            return "coin"
        if ext_id == "gravity-marble":
            return "marble"
        return "curio"
    return "tool"


def infer_texture_fallback(ext_id: str, rel: str, kind: str, config: dict, java: str) -> str:
    if ext_id in TEXTURE_FALLBACK_OVERRIDES:
        return format_texture_fallback(TEXTURE_FALLBACK_OVERRIDES[ext_id], kind=kind)
    pack = rel.split("/")[1] if rel.startswith("packs/") else ""
    profile = detect_profile(ext_id, kind, config, java)
    if kind == "items":
        logical = infer_item_texture_fallback(ext_id, pack, profile)
    else:
        logical = infer_block_texture_fallback(ext_id, pack, profile)
    return format_texture_fallback(logical, kind=kind)


def apply_texture_fallback(config: dict, fallback: str) -> None:
    textures = dict(config.get("textures") or {})
    textures["fallback"] = fallback
    ordered: dict[str, object] = {}
    for key in ("icon", "top", "side", "bottom", "side-1", "side-2", "side-3", "side-4"):
        if key in textures:
            ordered[key] = textures[key]
    for key, value in textures.items():
        if key not in ordered:
            ordered[key] = value
    config["textures"] = ordered


def detect_profile(ext_id: str, kind: str, config: dict, java: str) -> str:
    behavior = config.get("behavior") or {}
    if kind == "items":
        if behavior.get("right_click_air") == "throw" or behavior.get("right_click_block") == "throw":
            return "throwable"
        if ext_id == "detonator":
            return "detonator"
        if "linkBlockType" in (config.get("custom_data") or {}):
            return "link_item"
        return "item_use"
    if behavior.get("combustible") is False:
        if ext_id in TRIPWIRE_IDS:
            return "tripwire_charge"
        if ext_id in REMOTE_CHARGE_IDS:
            return "remote_charge"
        if ext_id in BURIED_MINE_IDS or "mine" in ext_id:
            return "buried_mine"
        if behavior.get("right_click_block") == "open" or "gui." in java.lower():
            return "gui_block"
        if "ticks().start" in java or "tickPeriod" in str(config.get("custom_data") or {}):
            return "placed_tick"
        return "placed_passive"
    if ext_id in REMOTE_CHARGE_IDS:
        return "remote_charge"
    if ext_id in TRIPWIRE_IDS:
        return "tripwire_charge"
    if ext_id in BURIED_MINE_IDS or ("mine" in ext_id and behavior.get("combustible") is False):
        return "buried_mine"
    if behavior.get("combustible") or (config.get("custom_data") or {}).get("fuse"):
        if "OnBlockTriggerListener" in java or "onTrigger" in java:
            return "combustible_explosive"
    if behavior.get("right_click_block") == "open" or "gui." in java.lower():
        return "gui_block"
    if "ticks().start" in java or "tickPeriod" in str(config.get("custom_data") or {}):
        return "placed_tick"
    if "OnBlockTriggerListener" in java:
        return "combustible_explosive"
    return "placed_passive"


def build_display(ext_id: str, rel: str, config: dict, config_text: str, java: str) -> dict:
    if ext_id in MYSTERIOUS_LORE:
        title, lines = MYSTERIOUS_LORE[ext_id]
        return normalize_display_meta(ext_id, rel, {"title": title, "description": lines, "mysterious": True})

    if ext_id in DISPLAY_OVERRIDES:
        return normalize_display_meta(ext_id, rel, DISPLAY_OVERRIDES[ext_id])

    profile = detect_profile(ext_id, "blocks" if "/blocks/" in rel else "items", config, java)
    header = parse_header_summary(config_text)
    name = humanize(ext_id)
    lines: list[str] = []

    if profile == "combustible_explosive":
        fuse = fuse_seconds(config)
        effect = infer_effect(java, ext_id, config)
        lines = [
            "&7Place and ignite with &fflint and steel&7 or &6fire charge&7.",
            f"&7Fuses for &e{fuse:.1f}s&7, then {effect}.",
        ]
        if header:
            lines.insert(0, f"&7{header.rstrip('.')}.")
    elif profile == "buried_mine":
        effect = infer_effect(java, ext_id, config)
        lines = [
            "&7Bury it to arm a &8proximity mine&7.",
            f"&7{effect.capitalize()}.",
        ]
        if header:
            lines[0] = f"&7{header.rstrip('.')}."
    elif profile == "tripwire_charge":
        lines = [
            "&7Place two charges to string a &etripwire&7 between them.",
            "&7Both ends &cdetonate&7 when something crosses the line.",
        ]
    elif profile == "remote_charge":
        lines = [
            "&7Place it, then link a &edetonator&7.",
            "&7Cannot be lit with flint and steel.",
            "&7Right-click the detonator to blow it remotely.",
        ]
    elif profile == "throwable":
        fuse = fuse_seconds(config)
        effect = infer_effect(java, ext_id, config)
        lines = [
            "&7Right-click to &ethrow&7.",
            f"&7Detonates after &e{fuse:.1f}s&7; {effect}.",
        ]
    elif profile == "detonator":
        lines = [
            "&7Left-click a &esignal charge&7 to link it.",
            "&7Right-click to detonate all linked charges.",
            "&7Holds up to &e16&7 paired charges.",
        ]
    elif profile == "link_item":
        target = (config.get("custom_data") or {}).get("linkBlockType", "a linked block")
        lines = [
            f"&7Right-click a &e{str(target).replace('-', ' ')}&7 to link.",
            "&7Use again to control it remotely.",
        ]
    elif profile == "gui_block":
        lines = [
            "&7Right-click to &eopen&7.",
        ]
        if header:
            lines.insert(0, f"&7{header.rstrip('.')}.")
        else:
            lines.append(f"&7Interact with the {name.lower()}.")
    elif profile == "item_use":
        if header:
            lines = [f"&7{header.rstrip('.')}."]
        else:
            lines = [f"&7Right-click to use the {name.lower()}."]
        lines.append("&7Consumable or tool — check effects after use.")
    elif profile == "placed_tick":
        if header:
            lines = [f"&7{header.rstrip('.')}."]
        else:
            lines = [f"&7Place to activate the {name.lower()}."]
        lines.append("&7Runs passively while placed nearby.")
    else:
        if header:
            lines = [f"&7{header.rstrip('.')}."]
        else:
            existing = (config.get("display") or {}).get("description") or []
            if existing and existing[0] != "&7Flare explosive":
                lines = list(existing)
            else:
                lines = [f"&7Place or use the {name.lower()}."]

    return normalize_display_meta(
        ext_id,
        rel,
        {"title": themed_title(ext_id, rel), "description": lines[:5], "mysterious": False},
    )


def format_display_yaml(meta: dict) -> str:
    out = ["display:", f"  title: \"{meta['title']}\"" , "  description:"]
    for line in meta["description"]:
        out.append(f"    - \"{line}\"")
    return "\n".join(out)


def replace_display_section(config_text: str, meta: dict) -> str:
    display_yaml = format_display_yaml(meta)
    pattern = re.compile(r"^display:\n(?:  .*\n)+", re.M)
    if pattern.search(config_text):
        return pattern.sub(display_yaml + "\n", config_text, count=1)
    # Insert after id block
    id_match = re.search(r"^(id: .+\n)\n", config_text, re.M)
    if id_match:
        pos = id_match.end()
        return config_text[:pos] + display_yaml + "\n\n" + config_text[pos:]
    return display_yaml + "\n\n" + config_text


def fix_behavior(ext_id: str, kind: str, config: dict, java: str) -> dict:
    behavior = dict(config.get("behavior") or {})
    custom = dict(config.get("custom_data") or {})
    profile = detect_profile(ext_id, kind, config, java)

    if profile == "combustible_explosive":
        behavior["combustible"] = True
        behavior.setdefault("left_click_block", "break")
        behavior.setdefault("right_click_block", "ignite")
        behavior.setdefault("ignition_materials", ["FLINT_AND_STEEL", "FIRE_CHARGE"])
        behavior.setdefault("sounds", {})
        if "place" not in behavior["sounds"]:
            behavior["sounds"]["place"] = "BLOCK_TNT_PLACE"
        if "ignite" not in behavior["sounds"]:
            behavior["sounds"]["ignite"] = "ITEM_FLINTANDSTEEL_USE"
        if "fuse" not in custom:
            custom["fuse"] = 80
        if "power" not in custom and "radius" not in custom:
            custom["power"] = 4.0
        custom.setdefault("blockDamage", True)
    elif profile in {"remote_charge", "buried_mine"}:
        behavior["combustible"] = False
    elif profile == "throwable":
        behavior["right_click_air"] = "throw"
        behavior["right_click_block"] = "throw"
        custom.setdefault("throw_velocity", 1.4)
        custom.setdefault("fuse_ticks", 40)
        custom.setdefault("power", 4.0)
    elif profile == "item_use":
        behavior["right_click_air"] = "use"
        behavior["right_click_block"] = "use"

    config["behavior"] = behavior
    if custom:
        config["custom_data"] = custom
    return config


def package_parts(module: Path) -> tuple[str, str, str]:
    rel = module.relative_to(PACKS)
    kind = rel.parts[1]  # blocks | items
    ext_id = module.name
    pack = rel.parts[0]
    return ext_id, kind, pack


def java_package(ext_id: str, kind: str) -> str:
    singular = "block" if kind == "blocks" else "item"
    pkg = ext_id.replace("-", "")
    return f"dev.rono.igniscore.{singular}.{pkg}"


def strategy_class(ext_id: str, kind: str) -> str:
    return f"{java_package(ext_id, kind)}.Strategy"


TRACKING_ITEM_CLASS = textwrap.dedent("""\
    private static final class TrackingItem implements IgnisItem {
        private int amount;
        private TrackingItem(int amount) { this.amount = amount; }
        @Override public int getAmount() { return amount; }
        @Override public void setAmount(int amount) { this.amount = amount; }
        @Override public String getMaterialKey() { return "paper"; }
        @Override public boolean isAir() { return amount <= 0; }
        @Override public Object nativeItem() { return this; }
    }
""")


def tracking_player_class() -> str:
    return textwrap.dedent("""\
        private static final class TrackingPlayer implements IgnisPlayer {
            private final IgnisWorld world;
            private final IgnisLocation eye = new IgnisLocation("world", 0, 1.6, 0);
            private final java.util.List<String> messages = new java.util.ArrayList<>();
            private final java.util.List<String> effects = new java.util.ArrayList<>();
            private boolean openedInventory;
            private TrackingPlayer(IgnisWorld world) { this.world = world; }
            java.util.List<String> messages() { return messages; }
            java.util.List<String> effects() { return effects; }
            boolean openedInventory() { return openedInventory; }
            @Override public UUID getUniqueId() { return UUID.randomUUID(); }
            @Override public String getName() { return "tester"; }
            @Override public IgnisLocation getLocation() { return eye; }
            @Override public IgnisLocation getEyeLocation() { return eye.withYawPitch(0f, 0f); }
            @Override public IgnisWorld getWorld() { return world; }
            @Override public void sendMessage(String miniMessage) { messages.add(miniMessage); }
            @Override public void openInventory(Object nativeInventory) { openedInventory = true; }
            @Override public void applyPotionEffect(String effectKey, int durationTicks, int amplifier) {
                effects.add(effectKey);
            }
        }
    """)


def case_block_for_item(java: str, ext_id: str) -> str:
    match = re.search(rf'case\s+"{re.escape(ext_id)}"\s*->\s*\{{([^}}]*)\}}', java, re.S)
    return match.group(1) if match else java


def item_use_assertion_lines(ext_id: str, java: str) -> list[str]:
    block = case_block_for_item(java, ext_id)
    lines: list[str] = []
    if re.search(r"consumeOne|setAmount\([^)]*-\s*1\)", block):
        lines.append("assertEquals(0, item.getAmount());")
    elif re.search(r"consumeOne|setAmount\([^)]*-\s*1\)", java):
        lines.append("assertEquals(0, item.getAmount());")
    if "applyPotionEffect" in block:
        lines.append("assertFalse(player.effects().isEmpty());")
    if "sendMessage" in block or "sendMessage" in java:
        lines.append("assertFalse(player.messages().isEmpty());")
    if "openInventory" in block:
        lines.append("assertTrue(player.openedInventory());")
    if re.search(r"spawnProjectile", block):
        lines.append("assertEquals(0, item.getAmount());")
    if re.search(r"spawnParticle|playSound|pulseRing|sparkle|bonemeal", block):
        lines.append("assertFalse(ctx.world().particles().isEmpty() || ctx.world().sounds().isEmpty());")
    if not lines:
        lines.append(
            "assertFalse(player.messages().isEmpty() && player.effects().isEmpty() "
            "&& ctx.world().sounds().isEmpty() && ctx.world().particles().isEmpty());"
        )
    return lines


def placed_block_assertion_lines(java: str, config: dict) -> list[str]:
    if re.search(r"OnBlockPlaceListener[\s\S]{0,800}(chime|sparkle|playSound|spawnParticle)", java):
        return ["assertFalse(ctx.world().particles().isEmpty() && ctx.world().sounds().isEmpty());"]
    if (config.get("behavior") or {}).get("sounds", {}).get("place"):
        return ["assertFalse(ctx.world().sounds().isEmpty());"]
    return ["assertFalse(ctx.world().sounds().isEmpty() && ctx.world().particles().isEmpty());"]


def is_weak_test(path: Path) -> bool:
    if not path.exists():
        return True
    text = path.read_text(encoding="utf-8")
    if "assertDoesNotThrow" in text:
        return True
    if "definitionLoads" in text:
        return True
    if "useDoesNotThrow" in text:
        return True
    return False


def behavior_test_path(module: Path, kind: str) -> Path:
    ext_id, _, _ = package_parts(module)
    singular = "block" if kind == "blocks" else "item"
    pkg = ext_id.replace("-", "")
    return module / f"src/test/java/dev/rono/igniscore/{singular}/{pkg}/BehaviorTest.java"


def render_behavior_test(ext_id: str, kind: str, profile: str, java: str = "", config: dict | None = None) -> str:
    config = config or {}
    pkg = java_package(ext_id, kind)
    manifest = "block-extension.yml" if kind == "blocks" else "item-extension.yml"
    cmd = 10001 if kind == "blocks" else 20001
    loader = "loadBlockDefinition" if kind == "blocks" else "loadItemDefinition"

    if profile == "combustible_explosive":
        return textwrap.dedent(f"""\
            package {pkg};

            import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;
            import dev.rono.igniscore.api.event.BlockActivateEvent;
            import dev.rono.igniscore.api.event.BlockPlaceEvent;
            import dev.rono.igniscore.api.event.BlockTickEvent;
            import dev.rono.igniscore.api.event.BlockTriggerEvent;
            import dev.rono.igniscore.api.model.BlockDefinition;
            import dev.rono.igniscore.api.model.PlacedBlock;
            import dev.rono.igniscore.api.model.RuntimeBlockInstance;
            import dev.rono.igniscore.api.port.IgnisLocation;
            import dev.rono.igniscore.testsupport.BehaviorTestSupport;
            import dev.rono.igniscore.testsupport.ExtensionTestSupport;
            import dev.rono.igniscore.testsupport.TestEventBus;
            import org.junit.jupiter.api.Test;

            import static org.junit.jupiter.api.Assertions.assertFalse;
            import static org.junit.jupiter.api.Assertions.assertTrue;

            class BehaviorTest {{
                private static final String EXTENSION_ID = "{ext_id}";

                @Test
                void placedAnnouncesItself() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, EXTENSION_ID, {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), EXTENSION_ID);

                    ctx.eventBus().fireBlockPlace(
                            new BlockPlaceEvent(
                                    PlacedBlock.of(definition, new IgnisLocation("world", 1, 2, 3)),
                                    null),
                            EXTENSION_ID);

                    assertFalse(ctx.world().sounds().isEmpty() && ctx.world().particles().isEmpty());
                }}

                @Test
                void igniteFlaresOnActivation() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, EXTENSION_ID, {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), EXTENSION_ID);
                    RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);

                    ctx.eventBus().fireBlockActivate(new BlockActivateEvent(instance), EXTENSION_ID);

                    assertFalse(ctx.world().particles().isEmpty());
                }}

                @Test
                void fuseCountdownPulses() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, EXTENSION_ID, {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), EXTENSION_ID);
                    RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);
                    instance.setTicksLeft(40);

                    new CombustibleFuseTheatricsListener(ctx.context())
                            .onBlockTick(new BlockTickEvent(instance));

                    assertFalse(ctx.world().particles().isEmpty());
                }}

                @Test
                void triggerDetonates() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, EXTENSION_ID, {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), EXTENSION_ID);
                    RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);

                    ctx.eventBus().fireBlockTrigger(new BlockTriggerEvent(instance, null), EXTENSION_ID);

                    assertFalse(ctx.world().explosions().isEmpty());
                }}
            }}
            """)

    if profile == "buried_mine":
        return textwrap.dedent(f"""\
            package {pkg};

            import dev.rono.igniscore.api.event.BlockPlaceEvent;
            import dev.rono.igniscore.api.event.BlockTriggerEvent;
            import dev.rono.igniscore.api.model.BlockDefinition;
            import dev.rono.igniscore.api.model.PlacedBlock;
            import dev.rono.igniscore.api.model.RuntimeBlockInstance;
            import dev.rono.igniscore.api.port.IgnisLocation;
            import dev.rono.igniscore.testsupport.BehaviorTestSupport;
            import dev.rono.igniscore.testsupport.ExtensionTestSupport;
            import dev.rono.igniscore.testsupport.TestEventBus;
            import org.junit.jupiter.api.Test;

            import static org.junit.jupiter.api.Assertions.assertFalse;

            class BehaviorTest {{
                @Test
                void triggerProducesBlast() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, "{ext_id}", {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), "{ext_id}");
                    RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);

                    ctx.eventBus().fireBlockTrigger(new BlockTriggerEvent(instance, null), "{ext_id}");

                    assertFalse(ctx.world().explosions().isEmpty());
                }}
            }}
            """)

    if profile == "tripwire_charge":
        return textwrap.dedent(f"""\
            package {pkg};

            import dev.rono.igniscore.api.event.BlockTriggerEvent;
            import dev.rono.igniscore.api.model.BlockDefinition;
            import dev.rono.igniscore.api.model.RuntimeBlockInstance;
            import dev.rono.igniscore.testsupport.BehaviorTestSupport;
            import dev.rono.igniscore.testsupport.ExtensionTestSupport;
            import dev.rono.igniscore.testsupport.TestEventBus;
            import org.junit.jupiter.api.Test;

            import static org.junit.jupiter.api.Assertions.assertFalse;

            class BehaviorTest {{
                @Test
                void triggerDetonatesLinkedCharge() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, "{ext_id}", {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), "{ext_id}");
                    RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);

                    ctx.eventBus().fireBlockTrigger(new BlockTriggerEvent(instance, null), "{ext_id}");

                    assertFalse(ctx.world().explosions().isEmpty());
                }}
            }}
            """)

    if profile == "remote_charge":
        return textwrap.dedent(f"""\
            package {pkg};

            import dev.rono.igniscore.api.config.BlockBehaviorConfig;
            import dev.rono.igniscore.api.model.BlockDefinition;
            import dev.rono.igniscore.testsupport.ExtensionTestSupport;
            import org.junit.jupiter.api.Test;

            import static org.junit.jupiter.api.Assertions.assertFalse;

            class BehaviorTest {{
                @Test
                void isNotCombustible() {{
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, "{ext_id}", {cmd});
                    assertFalse(BlockBehaviorConfig.from(definition.getBehaviorConfig()).combustible());
                }}
            }}
            """)

    if profile == "throwable":
        return textwrap.dedent(f"""\
            package {pkg};

            import dev.rono.igniscore.api.event.ItemClickEvent;
            import dev.rono.igniscore.api.model.ItemDefinition;
            import dev.rono.igniscore.api.port.IgnisInteraction;
            import dev.rono.igniscore.api.port.IgnisItem;
            import dev.rono.igniscore.api.port.IgnisLocation;
            import dev.rono.igniscore.api.port.IgnisPlayer;
            import dev.rono.igniscore.api.port.IgnisWorld;
            import dev.rono.igniscore.testsupport.ExtensionTestSupport;
            import dev.rono.igniscore.testsupport.TestEventBus;
            import org.junit.jupiter.api.Test;

            import java.util.UUID;

            import static org.junit.jupiter.api.Assertions.assertEquals;

            class BehaviorTest {{
                @Test
                void throwConsumesStack() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    ItemDefinition definition = ExtensionTestSupport.{loader}(
                            BehaviorTest.class, "{ext_id}", {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), "{ext_id}");
                    TestPlayer player = new TestPlayer(ctx.world());
                    TestItem item = new TestItem(1);

                    ctx.eventBus().fireItemClick(
                            new ItemClickEvent(player, definition, item, IgnisInteraction.RIGHT_CLICK_AIR, null, "throw"),
                            "{ext_id}");

                    assertEquals(0, item.getAmount());
                }}

                private static final class TestItem implements IgnisItem {{
                    private int amount;
                    private TestItem(int amount) {{ this.amount = amount; }}
                    @Override public int getAmount() {{ return amount; }}
                    @Override public void setAmount(int amount) {{ this.amount = amount; }}
                    @Override public String getMaterialKey() {{ return "snowball"; }}
                    @Override public boolean isAir() {{ return amount <= 0; }}
                    @Override public Object nativeItem() {{ return this; }}
                }}

                private static final class TestPlayer implements IgnisPlayer {{
                    private final IgnisWorld world;
                    private final IgnisLocation eye = new IgnisLocation("world", 0, 1.6, 0);
                    private TestPlayer(IgnisWorld world) {{ this.world = world; }}
                    @Override public UUID getUniqueId() {{ return UUID.randomUUID(); }}
                    @Override public String getName() {{ return "tester"; }}
                    @Override public IgnisLocation getLocation() {{ return eye; }}
                    @Override public IgnisLocation getEyeLocation() {{ return eye.withYawPitch(0f, 0f); }}
                    @Override public IgnisWorld getWorld() {{ return world; }}
                    @Override public void sendMessage(String miniMessage) {{}}
                    @Override public void openInventory(Object nativeInventory) {{}}
                    @Override public void applyPotionEffect(String effectKey, int durationTicks, int amplifier) {{}}
                }}
            }}
            """)

    if profile == "gui_block":
        return textwrap.dedent(f"""\
            package {pkg};

            import dev.rono.igniscore.api.CustomBlockAction;
            import dev.rono.igniscore.api.model.BlockDefinition;
            import dev.rono.igniscore.api.strategy.PlacedClickSupport;
            import dev.rono.igniscore.api.port.IgnisInteraction;
            import dev.rono.igniscore.testsupport.ExtensionTestSupport;
            import org.junit.jupiter.api.Test;

            import static org.junit.jupiter.api.Assertions.assertEquals;

            class BehaviorTest {{
                @Test
                void rightClickOpensGui() {{
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, "{ext_id}", {cmd});
                    assertEquals(CustomBlockAction.OPEN,
                            PlacedClickSupport.resolve(definition, CustomBlockAction.BREAK, CustomBlockAction.OPEN,
                                    IgnisInteraction.RIGHT_CLICK_BLOCK, "AIR"));
                }}
            }}
            """)

    if profile == "item_use":
        assertions = "\n                    ".join(item_use_assertion_lines(ext_id, java))
        player_class = textwrap.indent(tracking_player_class(), "    ")
        item_class = textwrap.indent(TRACKING_ITEM_CLASS, "    ")
        return textwrap.dedent(f"""\
            package {pkg};

            import dev.rono.igniscore.api.event.ItemClickEvent;
            import dev.rono.igniscore.api.model.ItemDefinition;
            import dev.rono.igniscore.api.port.IgnisInteraction;
            import dev.rono.igniscore.api.port.IgnisItem;
            import dev.rono.igniscore.api.port.IgnisLocation;
            import dev.rono.igniscore.api.port.IgnisPlayer;
            import dev.rono.igniscore.api.port.IgnisWorld;
            import dev.rono.igniscore.testsupport.ExtensionTestSupport;
            import dev.rono.igniscore.testsupport.TestEventBus;
            import org.junit.jupiter.api.Test;

            import java.util.UUID;

            import static org.junit.jupiter.api.Assertions.assertEquals;
            import static org.junit.jupiter.api.Assertions.assertFalse;
            import static org.junit.jupiter.api.Assertions.assertTrue;

            class BehaviorTest {{
                @Test
                void useProducesExpectedEffect() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    ItemDefinition definition = ExtensionTestSupport.{loader}(
                            BehaviorTest.class, "{ext_id}", {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), "{ext_id}");
                    TrackingPlayer player = new TrackingPlayer(ctx.world());
                    TrackingItem item = new TrackingItem(1);

                    ctx.eventBus().fireItemClick(
                            new ItemClickEvent(player, definition, item, IgnisInteraction.RIGHT_CLICK_AIR, null, "use"),
                            "{ext_id}");

                    {assertions}
                }}

            {item_class}
            {player_class}
            }}
            """)

    # placed_tick / placed_passive / mysterious / detonator / link_item
    if kind == "blocks":
        place_assertions = "\n                ".join(placed_block_assertion_lines(java, config))
        return textwrap.dedent(f"""\
            package {pkg};

            import dev.rono.igniscore.api.event.BlockPlaceEvent;
            import dev.rono.igniscore.api.model.BlockDefinition;
            import dev.rono.igniscore.api.model.PlacedBlock;
            import dev.rono.igniscore.api.port.IgnisLocation;
            import dev.rono.igniscore.testsupport.ExtensionTestSupport;
            import dev.rono.igniscore.testsupport.TestEventBus;
            import org.junit.jupiter.api.Test;

            import static org.junit.jupiter.api.Assertions.assertFalse;

            class BehaviorTest {{
                @Test
                void placeActivatesStrategy() {{
                    TestEventBus.TestContext ctx = TestEventBus.createContext();
                    BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                            BehaviorTest.class, "{ext_id}", {cmd});
                    Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), "{ext_id}");

                    ctx.eventBus().fireBlockPlace(
                            new BlockPlaceEvent(
                                    PlacedBlock.of(definition, new IgnisLocation("world", 1, 2, 3)),
                                    null),
                            "{ext_id}");

                    {place_assertions}
                }}
            }}
            """)
    return textwrap.dedent(f"""\
        package {pkg};

        import dev.rono.igniscore.api.model.ItemDefinition;
        import dev.rono.igniscore.testsupport.ExtensionTestSupport;
        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.assertNotNull;

        class BehaviorTest {{
            @Test
            void definitionLoads() {{
                ItemDefinition definition = ExtensionTestSupport.{loader}(
                        BehaviorTest.class, "{ext_id}", {cmd});
                assertNotNull(definition);
            }}
        }}
        """)


def remove_wrong_ignite_theatrics(module: Path, config: dict) -> str | None:
    behavior = config.get("behavior") or {}
    if behavior.get("combustible") is True:
        return None
    strategies = list((module / "src/main/java").rglob("Strategy.java"))
    if not strategies:
        return None
    path = strategies[0]
    text = path.read_text(encoding="utf-8")
    if "CombustibleIgniteTheatricsListener" not in text:
        return None
    text = re.sub(
        r"\n?import dev\.rono\.extensions\.shared\.api\.theatrics\.CombustibleIgniteTheatricsListener;\n",
        "\n",
        text,
    )
    text = re.sub(
        r"\n?        context\.eventBus\(\)\.subscribe\(new CombustibleIgniteTheatricsListener\(context\)\);\n",
        "\n",
        text,
    )
    path.write_text(text, encoding="utf-8")
    return str(path)


def add_fuse_theatrics(module: Path, java: str, config: dict) -> str | None:
    behavior = config.get("behavior") or {}
    if behavior.get("combustible") is not True:
        return None
    if "CombustibleFuseTheatricsListener" in java:
        return None
    strategies = list((module / "src/main/java").rglob("Strategy.java"))
    if not strategies:
        return None
    path = strategies[0]
    text = path.read_text(encoding="utf-8")
    if "CombustibleFuseTheatricsListener" in text:
        return None
    if "OnBlockActivateListener" in text and "CombustibleIgniteTheatricsListener" not in text:
        return None
    if "context.eventBus().subscribe" not in text:
        return None
    if "import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;" not in text:
        text = text.replace(
            "import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;",
            "import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;\n"
            "import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;",
        )
        if "CombustibleFuseTheatricsListener" not in text:
            text = text.replace(
                "import dev.rono.igniscore.api.strategy.IgnisStrategyContext;",
                "import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;\n"
                "import dev.rono.igniscore.api.strategy.IgnisStrategyContext;",
            )
    insert = '        context.eventBus().subscribe(new CombustibleFuseTheatricsListener(context));\n'
    ignite = '        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));\n'
    if ignite in text and insert not in text:
        text = text.replace(ignite, ignite + insert, 1)
    elif insert.strip() not in text:
        needle = "context.eventBus().subscribe("
        text = text.replace(needle, insert + needle, 1)
    path.write_text(text, encoding="utf-8")
    return str(path)


def add_ignite_theatrics(module: Path, java: str, config: dict) -> str | None:
    behavior = config.get("behavior") or {}
    if behavior.get("combustible") is not True:
        return None
    if "CombustibleIgniteTheatricsListener" in java:
        return None
    strategy = module / "src/main/java" / Path(*java_package(*package_parts(module)[1:]).split(".")) / "Strategy.java"
    # find Strategy.java
    strategies = list((module / "src/main/java").rglob("Strategy.java"))
    if not strategies:
        return None
    path = strategies[0]
    text = path.read_text(encoding="utf-8")
    if "CombustibleIgniteTheatricsListener" in text:
        return None
    if "OnBlockActivateListener" in text:
        return None
    if "context.eventBus().subscribe" not in text:
        return None
    if "import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;" not in text:
        text = text.replace(
            "import dev.rono.igniscore.api.strategy.IgnisStrategyContext;",
            "import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;\n"
            "import dev.rono.igniscore.api.strategy.IgnisStrategyContext;",
        )
    needle = "context.eventBus().subscribe("
    insert = '        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));\n'
    if insert.strip() in text:
        return None
    text = text.replace(needle, insert + needle, 1)
    path.write_text(text, encoding="utf-8")
    return str(path)


def iter_modules():
    for pack in sorted(PACKS.iterdir()):
        if not pack.is_dir():
            continue
        for category in ("blocks", "items"):
            cat = pack / category
            if not cat.is_dir():
                continue
            for module in sorted(cat.iterdir()):
                config = module / "src/main/resources/config.yml"
                if config.exists():
                    yield module, category, config


def cmd_catalog(_args):
    global DISPLAY_OVERRIDES
    DISPLAY_OVERRIDES = load_display_overrides()
    catalog = {}
    for module, kind, config_path in iter_modules():
        ext_id = module.name
        rel = str(module.relative_to(ROOT))
        config, config_text = load_config(config_path)
        java = java_sources(module)
        catalog[ext_id] = {
            **build_display(ext_id, rel, config, config_text, java),
            "profile": detect_profile(ext_id, kind, config, java),
            "texture_fallback": infer_texture_fallback(ext_id, rel, kind, config, java),
            "path": rel,
        }
    CATALOG_PATH.write_text(json.dumps(catalog, indent=2), encoding="utf-8")
    print(f"Wrote {len(catalog)} entries to {CATALOG_PATH}")


def behavior_needs_patch(before: dict, after: dict) -> bool:
    return before.get("behavior") != after.get("behavior") or before.get("custom_data") != after.get("custom_data")


def patch_config_dict(config_path: Path, merged: dict, meta: dict) -> None:
    text = config_path.read_text(encoding="utf-8")
    header = ""
    if text.startswith("#") and "\nid:" in text:
        header = text[: text.index("\nid:") + 1]
    merged = dict(merged)
    merged["display"] = {"title": meta["title"], "description": meta["description"]}
    body = yaml.dump(merged, default_flow_style=False, sort_keys=False, allow_unicode=True)
    config_path.write_text(header + body, encoding="utf-8")


def patch_texture_fallback(config_path: Path, fallback: str) -> bool:
    text = config_path.read_text(encoding="utf-8")
    if re.search(r"^  fallback: ", text, re.M):
        new_text, count = re.subn(r"^  fallback: .*$", f"  fallback: {fallback}", text, count=1, flags=re.M)
        if count:
            config_path.write_text(new_text, encoding="utf-8")
            return True
        return False

    pattern = re.compile(r"^(textures:\n(?:  (?!fallback:).+\n)+)", re.M)
    match = pattern.search(text)
    if not match:
        return False
    block = match.group(1)
    new_block = block + f"  fallback: {fallback}\n"
    config_path.write_text(text.replace(block, new_block, 1), encoding="utf-8")
    return True


def cmd_apply(args):
    global DISPLAY_OVERRIDES, TEXTURE_FALLBACK_OVERRIDES
    DISPLAY_OVERRIDES = load_display_overrides()
    TEXTURE_FALLBACK_OVERRIDES = load_texture_fallback_overrides()
    catalog = json.loads(CATALOG_PATH.read_text(encoding="utf-8")) if CATALOG_PATH.exists() else {}
    updated_displays = 0
    updated_behavior = 0
    updated_tests = 0
    updated_texture_fallbacks = 0
    wired_theatrics = 0
    wired_fuse = 0

    for module, kind, config_path in iter_modules():
        ext_id = module.name
        rel = str(module.relative_to(ROOT))
        config, config_text = load_config(config_path)
        java = java_sources(module)

        meta = {k: v for k, v in (catalog.get(ext_id) or build_display(ext_id, rel, config, config_text, java)).items() if k not in {"profile", "texture_fallback"}}
        profile = (catalog.get(ext_id) or {}).get("profile") or detect_profile(ext_id, kind, config, java)

        if args.texture_fallbacks:
            fallback = (catalog.get(ext_id) or {}).get("texture_fallback") or infer_texture_fallback(
                ext_id, rel, kind, config, java)
            if patch_texture_fallback(config_path, fallback):
                updated_texture_fallbacks += 1
            config, config_text = load_config(config_path)

        if args.displays:
            meta = normalize_display_meta(ext_id, rel, meta)
            new_text = replace_display_section(config_text, meta)
            config_path.write_text(new_text, encoding="utf-8")
            updated_displays += 1
            config, config_text = load_config(config_path)

        if args.behavior:
            remove_wrong_ignite_theatrics(module, config)
            fixed = fix_behavior(ext_id, kind, config, java)
            if behavior_needs_patch(config, fixed):
                patch_config_dict(config_path, fixed, meta)
                updated_behavior += 1
                config = fixed

        if args.theatrics and kind == "blocks":
            remove_wrong_ignite_theatrics(module, config)
            refreshed_java = java_sources(module)
            if add_ignite_theatrics(module, refreshed_java, config):
                wired_theatrics += 1
            refreshed_java = java_sources(module)
            if add_fuse_theatrics(module, refreshed_java, config):
                wired_fuse += 1

        if args.tests or getattr(args, "upgrade_tests", False):
            test_path = behavior_test_path(module, kind)
            test_path.parent.mkdir(parents=True, exist_ok=True)
            content = render_behavior_test(ext_id, kind, profile, java, config)
            should_write = not test_path.exists() or args.force_tests or (
                getattr(args, "upgrade_tests", False) and is_weak_test(test_path)
            )
            if should_write:
                test_path.write_text(content, encoding="utf-8")
                updated_tests += 1

    print(
        f"Updated displays: {updated_displays}, behavior configs: {updated_behavior}, "
        f"texture fallbacks: {updated_texture_fallbacks}, tests: {updated_tests}, "
        f"ignite theatrics: {wired_theatrics}, fuse theatrics: {wired_fuse}"
    )


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest="command", required=True)

    p_cat = sub.add_parser("catalog", help="Build extension-catalog.json")
    p_cat.set_defaults(func=cmd_catalog)

    p_apply = sub.add_parser("apply", help="Apply catalog updates")
    p_apply.add_argument("--displays", action="store_true")
    p_apply.add_argument("--behavior", action="store_true")
    p_apply.add_argument("--tests", action="store_true")
    p_apply.add_argument("--theatrics", action="store_true")
    p_apply.add_argument("--texture-fallbacks", action="store_true")
    p_apply.add_argument("--force-tests", action="store_true")
    p_apply.add_argument("--upgrade-tests", action="store_true", help="Overwrite weak assertDoesNotThrow tests")
    p_apply.set_defaults(func=cmd_apply)

    args = parser.parse_args()
    if args.command == "apply" and not any([
        args.displays, args.behavior, args.tests, args.theatrics,
        getattr(args, "upgrade_tests", False), args.texture_fallbacks,
    ]):
        args.displays = args.behavior = args.tests = args.theatrics = True
    args.func(args)


if __name__ == "__main__":
    main()
