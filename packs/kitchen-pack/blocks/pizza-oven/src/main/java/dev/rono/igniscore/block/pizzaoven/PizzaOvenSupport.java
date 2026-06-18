package dev.rono.igniscore.block.pizzaoven;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.util.Locations;
import net.kyori.adventure.text.Component;

final class PizzaOvenSupport {
    private PizzaOvenSupport() {
    }

    static final int BREAD_SLOT = 10;
    static final int TOMATO_SLOT = 12;
    static final int CHEESE_SLOT = 14;
    static final int OUTPUT_SLOT = 16;

    static void tick(PizzaOvenRuntime runtime, BlockDefinition definition, IgnisLocation location) {

        var inventory = runtime.registry.inventoryAt(location);
        if (inventory == null) {
            return;
        }
        if (!ExtensionShared.processing().matches(inventory.getItem(BREAD_SLOT), "bread")
                || !ExtensionShared.processing().matches(inventory.getItem(TOMATO_SLOT), "beetroot")
                || !ExtensionShared.processing().matches(inventory.getItem(CHEESE_SLOT), "milk_bucket")) {
            return;
        }
        ExtensionShared.processing().consumeOne(inventory, BREAD_SLOT);
        ExtensionShared.processing().consumeOne(inventory, TOMATO_SLOT);
        ExtensionShared.processing().consumeOne(inventory, CHEESE_SLOT);
        ExtensionShared.processing().setOutput(runtime.context.extensions(), inventory, OUTPUT_SLOT, "cake", 1);
        IgnisWorld world = worldAt(runtime, location);
        IgnisLocation center = Locations.toCenter(location);
        world.spawnParticle(center, "FLAME", 6, 0.3, 0.2, 0.3, 0.02);
        world.playSound(center, "BLOCK_SMOKER_SMOKE", 0.6f, 1.0f);
        ExtensionShared.theatrics().sparkle(world, center, "LAVA", 4);
    
    }

    static Component title(PizzaOvenRuntime runtime, BlockDefinition definition) {

        return definition.getTitle() == null ? Component.text("Pizza Oven") : definition.getTitle();
    
    }

    static IgnisWorld worldAt(PizzaOvenRuntime runtime, IgnisLocation location) {

        return runtime.context.extensions().resolveWorld(location);
    
    }
}

