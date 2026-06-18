package dev.rono.igniscore.block.moodlantern;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockPlaceEvent;
import dev.rono.igniscore.api.event.OnBlockPlaceListener;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;
import dev.rono.igniscore.api.util.Locations;

final class MoodLanternOnBlockPlaceListener implements OnBlockPlaceListener {
    private final IgnisStrategyContext context;

    MoodLanternOnBlockPlaceListener(IgnisStrategyContext context) {
        this.context = context;
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        ExtensionShared.ticks().start(context, event.block().location(), StrategySupport.customInt(event.block().definition(), "tickPeriod", 25),
                () -> MoodLanternSupport.tick(context, event.block().definition(), event.block().location()));
        IgnisLocation center = Locations.toCenter(event.block().location());
        MoodLanternSupport.worldAt(context, center).playSound(center, "BLOCK_AMETHYST_BLOCK_CHIME", 0.9f, 1.0f);
    }
}

