package dev.rono.igniscore.block.compostteabrewer;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.util.Locations;
import net.kyori.adventure.text.Component;

final class CompostTeaBrewerSupport {
    private CompostTeaBrewerSupport() {
    }

    static final int BONE_SLOT = 11;
    static final int BOTTLE_SLOT = 15;
    static final int OUTPUT_SLOT = 17;

    static void tick(CompostTeaBrewerRuntime runtime, BlockDefinition definition, IgnisLocation location) {

        var inventory = runtime.registry.inventoryAt(location);
        if (inventory == null) {
            return;
        }
        if (!ExtensionShared.processing().matches(inventory.getItem(BONE_SLOT), "bone_meal")
                || !ExtensionShared.processing().matches(inventory.getItem(BOTTLE_SLOT), "glass_bottle", "potion")) {
            return;
        }
        ExtensionShared.processing().consumeOne(inventory, BONE_SLOT);
        ExtensionShared.processing().consumeOne(inventory, BOTTLE_SLOT);
        ExtensionShared.processing().setOutput(runtime.context.extensions(), inventory, OUTPUT_SLOT, "splash_potion", 1);
        IgnisWorld world = worldAt(runtime, location);
        IgnisLocation center = Locations.toCenter(location);
        ExtensionShared.theatrics().sparkle(world, center, "HAPPY_VILLAGER", 6);
        world.playSound(center, "ITEM_BOTTLE_FILL", 0.7f, 1.0f);
    
    }

    static Component title(CompostTeaBrewerRuntime runtime, BlockDefinition definition) {

        return definition.getTitle() == null ? Component.text("Compost Tea Brewer") : definition.getTitle();
    
    }

    static IgnisWorld worldAt(CompostTeaBrewerRuntime runtime, IgnisLocation location) {

        return runtime.context.extensions().resolveWorld(location);
    
    }
}

