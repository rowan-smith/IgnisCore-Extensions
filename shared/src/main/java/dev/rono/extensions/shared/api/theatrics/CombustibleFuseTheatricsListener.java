package dev.rono.extensions.shared.api.theatrics;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockTickEvent;
import dev.rono.igniscore.api.event.OnBlockTickListener;
import dev.rono.igniscore.api.model.RuntimeBlockInstance;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.util.Locations;

/**
 * Escalating fuse countdown particles and sounds for combustible explosive blocks.
 */
public final class CombustibleFuseTheatricsListener implements OnBlockTickListener {
    private final IgnisStrategyContext context;

    public CombustibleFuseTheatricsListener(IgnisStrategyContext context) {
        this.context = context;
    }

    @Override
    public void onBlockTick(BlockTickEvent event) {
        RuntimeBlockInstance instance = event.instance();
        int ticksLeft = instance.getTicksLeft();
        if (ticksLeft <= 0) {
            return;
        }
        int fuseTicks = ExtensionShared.explosion().fuseTicks(instance, 80);
        if (ticksLeft >= fuseTicks) {
            return;
        }
        IgnisLocation center = Locations.toCenter(instance.getLocation());
        IgnisWorld world = context.extensions().resolveWorld(center);
        ExtensionShared.theatrics().fusePulse(world, center, ticksLeft, fuseTicks);
    }
}
