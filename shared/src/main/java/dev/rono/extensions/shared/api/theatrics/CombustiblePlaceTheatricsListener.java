package dev.rono.extensions.shared.api.theatrics;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockPlaceEvent;
import dev.rono.igniscore.api.event.OnBlockPlaceListener;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.util.Locations;

/**
 * Standard placement feedback for combustible explosive blocks.
 */
public final class CombustiblePlaceTheatricsListener implements OnBlockPlaceListener {
    private final IgnisStrategyContext context;

    public CombustiblePlaceTheatricsListener(IgnisStrategyContext context) {
        this.context = context;
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        IgnisLocation center = Locations.toCenter(event.block().location());
        IgnisWorld world = context.extensions().resolveWorld(center);
        ExtensionShared.theatrics().sparkle(world, center, "SMOKE", 4);
        world.playSound(center, "BLOCK_TNT_PLACE", 0.8f, 1.0f);
    }
}
