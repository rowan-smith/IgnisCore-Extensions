package dev.rono.igniscore.block.icecreamfreezer;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.util.Locations;
import net.kyori.adventure.text.Component;

final class IceCreamFreezerSupport {
    private IceCreamFreezerSupport() {
    }

    static final int SNOW_SLOT = 10;
    static final int BERRY_SLOT = 12;
    static final int BUCKET_SLOT = 14;
    static final int OUTPUT_SLOT = 16;

    static void tick(IceCreamFreezerRuntime runtime, BlockDefinition definition, IgnisLocation location) {

        var inventory = runtime.registry.inventoryAt(location);
        if (inventory == null) {
            return;
        }
        if (!ExtensionShared.processing().matches(inventory.getItem(SNOW_SLOT), "snow_block", "snow")
                || !ExtensionShared.processing().matches(inventory.getItem(BERRY_SLOT), "sweet_berries")
                || !ExtensionShared.processing().matches(inventory.getItem(BUCKET_SLOT), "bucket", "milk_bucket")) {
            return;
        }
        ExtensionShared.processing().consumeOne(inventory, SNOW_SLOT);
        ExtensionShared.processing().consumeOne(inventory, BERRY_SLOT);
        ExtensionShared.processing().consumeOne(inventory, BUCKET_SLOT);
        ExtensionShared.processing().setOutput(runtime.context.extensions(), inventory, OUTPUT_SLOT, "bowl", 1);
        IgnisWorld world = worldAt(runtime, location);
        IgnisLocation center = Locations.toCenter(location);
        ExtensionShared.theatrics().sparkle(world, center, "CLOUD", 8);
        world.playSound(center, "BLOCK_POWDER_SNOW_STEP", 0.7f, 1.3f);
    
    }

    static Component title(IceCreamFreezerRuntime runtime, BlockDefinition definition) {

        return definition.getTitle() == null ? Component.text("Ice Cream Freezer") : definition.getTitle();
    
    }

    static IgnisWorld worldAt(IceCreamFreezerRuntime runtime, IgnisLocation location) {

        return runtime.context.extensions().resolveWorld(location);
    
    }
}

