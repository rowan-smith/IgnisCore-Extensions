package dev.rono.igniscore.block.xpvacuum;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.model.PlacedBlock;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;
import dev.rono.igniscore.api.util.Locations;

final class XpVacuumSupport {
    private XpVacuumSupport() {
    }

    static void tick(IgnisStrategyContext context, PlacedBlock block) {
        IgnisWorld world = worldAt(context, block.location());
        IgnisLocation center = Locations.toCenter(block.location());
        double radius = StrategySupport.customDouble(block.definition(), "vacuumRadius", 6.0);
        double strength = StrategySupport.customDouble(block.definition(), "vacuumStrength", 0.35);
        ExtensionShared.entities().pullLoot(world, center, radius, strength);
        ExtensionShared.theatrics().sparkle(world, center, "ENCHANT", 4);
    }

    static IgnisWorld worldAt(IgnisStrategyContext context, IgnisLocation location) {
        return context.extensions().resolveWorld(location);
    }
}
