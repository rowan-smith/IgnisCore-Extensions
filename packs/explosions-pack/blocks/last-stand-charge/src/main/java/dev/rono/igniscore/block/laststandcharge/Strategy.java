package dev.rono.igniscore.block.laststandcharge;

import dev.rono.extensions.shared.api.theatrics.CombustiblePlaceTheatricsListener;
import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        context.eventBus().subscribe(new CombustiblePlaceTheatricsListener(context));
        context.eventBus().subscribe(new LastStandChargeOnBlockClickListener());
        context.eventBus().subscribe(new LastStandChargeOnBlockTickListener(context));
        context.eventBus().subscribe(new LastStandChargeOnBlockTriggerListener(context));
    }

}
