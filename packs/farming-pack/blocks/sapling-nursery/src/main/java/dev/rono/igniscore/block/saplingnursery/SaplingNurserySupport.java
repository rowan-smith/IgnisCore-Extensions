package dev.rono.igniscore.block.saplingnursery;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.util.Locations;
import net.kyori.adventure.text.Component;

final class SaplingNurserySupport {
    private SaplingNurserySupport() {
    }

    static void tick(SaplingNurseryRuntime runtime, BlockDefinition definition, IgnisLocation location) {

        var inventory = runtime.registry.inventoryAt(location);
        if (inventory == null) {
            return;
        }
        IgnisWorld world = worldAt(runtime, location);
        IgnisLocation block = Locations.toBlock(location);
        for (int i = 0; i < inventory.getSize(); i++) {
            IgnisItem sapling = inventory.getItem(i);
            if (!ExtensionShared.processing().matches(sapling, "sapling", "propagule", "azalea")) {
                continue;
            }
            IgnisLocation[] offsets = {block.add(1, 0, 0), block.add(-1, 0, 0), block.add(0, 0, 1), block.add(0, 0, -1)};
            for (IgnisLocation soil : offsets) {
                String below = world.getBlockMaterialKey(soil).toLowerCase();
                String above = world.getBlockMaterialKey(soil.add(0, 1, 0)).toLowerCase();
                if ((below.contains("dirt") || below.contains("grass")) && above.contains("air")) {
                    world.setBlockMaterialKey(soil.add(0, 1, 0), sapling.getMaterialKey());
                    ExtensionShared.processing().consumeOne(inventory, i);
                    world.spawnParticle(soil.add(0.5, 1.5, 0.5), "HAPPY_VILLAGER", 4, 0.2, 0.2, 0.2, 0.01);
                    world.playSound(soil, "BLOCK_GRASS_PLACE", 0.7f, 1.0f);
                    return;
                }
            }
        }
    
    }

    static Component title(SaplingNurseryRuntime runtime, BlockDefinition definition) {

        return definition.getTitle() == null ? Component.text("Sapling Nursery") : definition.getTitle();
    
    }

    static IgnisWorld worldAt(SaplingNurseryRuntime runtime, IgnisLocation location) {

        return runtime.context.extensions().resolveWorld(location);
    
    }
}

