package dev.rono.igniscore.block.wormholetnt;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new WormholeOnBlockClickListener());
        context.eventBus().subscribe(new WormholeOnBlockTickListener(context));
        context.eventBus().subscribe(new WormholeOnBlockTriggerListener(context));
    }

}
