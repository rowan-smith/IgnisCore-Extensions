package dev.rono.igniscore.block.cropmri;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockPlaceEvent;
import dev.rono.igniscore.api.event.OnBlockPlaceListener;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;
import dev.rono.igniscore.api.util.Locations;

final class CropMriOnBlockPlaceListener implements OnBlockPlaceListener {
    private final IgnisStrategyContext context;

    CropMriOnBlockPlaceListener(IgnisStrategyContext context) {
        this.context = context;
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        long period = StrategySupport.customInt(event.block().definition(), "tickPeriod", 20);
        ExtensionShared.ticks().start(context, event.block().location(), period, () -> CropMriSupport.tick(context, event.block().definition(), event.block().location()));
        IgnisLocation center = Locations.toCenter(event.block().location());
        ExtensionShared.theatrics().chime(CropMriSupport.worldAt(context, center), center, 1.0f);
    }
}

