package dev.rono.igniscore.block.laststandcharge;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockTickEvent;
import dev.rono.igniscore.api.event.OnBlockTickListener;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;
import dev.rono.igniscore.api.util.Locations;

final class LastStandChargeOnBlockTickListener implements OnBlockTickListener {
    private final IgnisStrategyContext context;

    LastStandChargeOnBlockTickListener(IgnisStrategyContext context) {
        this.context = context;
    }

    @Override
    public void onBlockTick(BlockTickEvent event) {
        BlockDefinition def = event.instance().getDefinition();
        IgnisLocation loc = Locations.toCenter(event.instance().getLocation());
        IgnisWorld world = LastStandChargeSupport.worldAt(context, loc);
        int fuse = ExtensionShared.explosion().fuseTicks(event.instance(), 80);
        int elapsed = ExtensionShared.explosion().elapsedFuseTicks(event.instance(), 80);
        int interval = StrategySupport.customInt(def, "tickInterval", 5);
        if (elapsed % interval != 0) {
            return;
        }
        double radius = StrategySupport.customDouble(def, "stasisRadius", 5.0);
        if (event.instance().getTicksLeft() < StrategySupport.customInt(def, "lastStandTicks", 30)) {
            ExtensionShared.entities().freezeInRadius(world, loc, radius);
            ExtensionShared.theatrics().sparkle(world, loc, "TOTEM_OF_UNDYING", 6);
        }
    }
}

