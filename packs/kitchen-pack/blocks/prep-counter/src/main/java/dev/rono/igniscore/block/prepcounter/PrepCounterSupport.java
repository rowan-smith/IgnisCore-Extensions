package dev.rono.igniscore.block.prepcounter;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.util.Locations;
import net.kyori.adventure.text.Component;

final class PrepCounterSupport {
    private PrepCounterSupport() {
    }

    static final int[] INPUT_SLOTS = {10, 11, 12};
    static final int OUTPUT_SLOT = 16;

    static void tick(PrepCounterRuntime runtime, BlockDefinition definition, IgnisLocation location) {

        var inventory = runtime.registry.inventoryAt(location);
        if (inventory == null) {
            return;
        }
        int foods = 0;
        for (int slot : INPUT_SLOTS) {
            IgnisItem item = inventory.getItem(slot);
            if (ExtensionShared.processing().matches(item, "bread", "cooked", "apple", "carrot", "potato", "beef", "pork", "chicken", "fish")) {
                foods++;
            }
        }
        if (foods < 3) {
            return;
        }
        for (int slot : INPUT_SLOTS) {
            ExtensionShared.processing().consumeOne(inventory, slot);
        }
        ExtensionShared.processing().setOutput(runtime.context.extensions(), inventory, OUTPUT_SLOT, "golden_carrot", 1);
        IgnisWorld world = worldAt(runtime, location);
        IgnisLocation center = Locations.toCenter(location);
        ExtensionShared.theatrics().sparkle(world, center, "HAPPY_VILLAGER", 10);
        world.playSound(center, "ENTITY_PLAYER_BURP", 0.5f, 1.2f);
    
    }

    static Component title(PrepCounterRuntime runtime, BlockDefinition definition) {

        return definition.getTitle() == null ? Component.text("Prep Counter") : definition.getTitle();
    
    }

    static IgnisWorld worldAt(PrepCounterRuntime runtime, IgnisLocation location) {

        return runtime.context.extensions().resolveWorld(location);
    
    }
}

