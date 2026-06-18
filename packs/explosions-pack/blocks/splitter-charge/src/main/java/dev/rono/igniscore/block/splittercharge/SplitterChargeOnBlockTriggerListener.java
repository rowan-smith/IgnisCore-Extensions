package dev.rono.igniscore.block.splittercharge;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockTriggerEvent;
import dev.rono.igniscore.api.event.OnBlockTriggerListener;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;
import dev.rono.igniscore.api.util.Locations;

final class SplitterChargeOnBlockTriggerListener implements OnBlockTriggerListener {
    private final IgnisStrategyContext context;

    SplitterChargeOnBlockTriggerListener(IgnisStrategyContext context) {
        this.context = context;
    }

    @Override
    public void onBlockTrigger(BlockTriggerEvent event) {
        BlockDefinition def = event.instance().getDefinition();
        IgnisLocation loc = Locations.toCenter(event.instance().getLocation());
        IgnisWorld world = SplitterChargeSupport.worldAt(context, loc);
        float power = ExtensionShared.explosion().resolvePower(def, 4.0);
        double offset = StrategySupport.customDouble(def, "splitOffset", 2.5);
        ExtensionShared.variants().cardinalSplit(world, loc, power, offset);
    }
}

