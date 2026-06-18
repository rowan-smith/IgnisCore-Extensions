package dev.rono.igniscore.block.bridgebuilder;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.extensions.shared.api.theatrics.CombustiblePlaceTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new CombustiblePlaceTheatricsListener(context));
        context.eventBus().subscribe(new CombustibleFuseTheatricsListener(context));
        context.eventBus().subscribe(new BridgeBuilderOnBlockClickListener());
        context.eventBus().subscribe(new BridgeBuilderOnBlockTickListener(context));
        context.eventBus().subscribe(new BridgeBuilderOnBlockTriggerListener(context));
    }

}
