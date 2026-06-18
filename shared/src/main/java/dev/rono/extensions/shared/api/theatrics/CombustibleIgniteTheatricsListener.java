package dev.rono.extensions.shared.api.theatrics;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockActivateEvent;
import dev.rono.igniscore.api.event.OnBlockActivateListener;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.util.Locations;

/**
 * Standard fuse-lit feedback for combustible explosive blocks.
 */
public final class CombustibleIgniteTheatricsListener implements OnBlockActivateListener {
    private final IgnisStrategyContext context;

    public CombustibleIgniteTheatricsListener(IgnisStrategyContext context) {
        this.context = context;
    }

    @Override
    public void onBlockActivate(BlockActivateEvent event) {
        IgnisLocation center = Locations.toCenter(event.instance().getLocation());
        IgnisWorld world = context.extensions().resolveWorld(center);
        ExtensionShared.theatrics().igniteFlare(world, center);
    }
}
