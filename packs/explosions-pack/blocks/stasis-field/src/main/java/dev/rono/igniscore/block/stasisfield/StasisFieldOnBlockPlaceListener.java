package dev.rono.igniscore.block.stasisfield;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockPlaceEvent;
import dev.rono.igniscore.api.event.OnBlockPlaceListener;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;
import dev.rono.igniscore.api.util.Locations;

final class StasisFieldOnBlockPlaceListener implements OnBlockPlaceListener {
    private final IgnisStrategyContext context;

    StasisFieldOnBlockPlaceListener(IgnisStrategyContext context) {
        this.context = context;
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        long period = StrategySupport.customInt(event.block().definition(), "tickPeriod", 20);
        ExtensionShared.ticks().start(context, event.block().location(), period, () -> StasisFieldSupport.tick(context, event.block().definition(), event.block().location()));
        IgnisLocation center = Locations.toCenter(event.block().location());
        ExtensionShared.theatrics().chime(StasisFieldSupport.worldAt(context, center), center, 1.0f);
    }
}

