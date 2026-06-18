package dev.rono.igniscore.block.fortunecookiemaker;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.util.Locations;
import net.kyori.adventure.text.Component;

final class FortuneCookieMakerSupport {
    private FortuneCookieMakerSupport() {
    }

    static final int WHEAT_SLOT = 11;
    static final int PAPER_SLOT = 15;
    static final int OUTPUT_SLOT = 17;
    static final String[] FORTUNES = {
            "A surprise awaits behind the next door.",
            "Beware the creeper in plain sight.",
            "Your build will inspire the server.",
            "Share loot and luck will follow.",
            "Dig down — but not too far."
    };

    static void tick(FortuneCookieMakerRuntime runtime, BlockDefinition definition, IgnisLocation location) {

        var inventory = runtime.registry.inventoryAt(location);
        if (inventory == null) {
            return;
        }
        if (!ExtensionShared.processing().matches(inventory.getItem(WHEAT_SLOT), "wheat")
                || !ExtensionShared.processing().matches(inventory.getItem(PAPER_SLOT), "paper")) {
            return;
        }
        ExtensionShared.processing().consumeOne(inventory, WHEAT_SLOT);
        ExtensionShared.processing().consumeOne(inventory, PAPER_SLOT);
        ExtensionShared.processing().setOutput(runtime.context.extensions(), inventory, OUTPUT_SLOT, "cookie", 1);
        IgnisWorld world = worldAt(runtime, location);
        world.playSound(Locations.toCenter(location), "ENTITY_GENERIC_EAT", 0.5f, 1.3f);
    
    }

    static Component title(FortuneCookieMakerRuntime runtime, BlockDefinition definition) {

        return definition.getTitle() == null ? Component.text("Fortune Cookie Maker") : definition.getTitle();
    
    }

    static IgnisWorld worldAt(FortuneCookieMakerRuntime runtime, IgnisLocation location) {

        return runtime.context.extensions().resolveWorld(location);
    
    }
}

