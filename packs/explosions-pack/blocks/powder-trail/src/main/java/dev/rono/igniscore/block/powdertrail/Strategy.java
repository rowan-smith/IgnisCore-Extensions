package dev.rono.igniscore.block.powdertrail;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new PowderTrailOnBlockClickListener());
        context.eventBus().subscribe(new PowderTrailOnBlockTickListener(context));
        context.eventBus().subscribe(new PowderTrailOnBlockTriggerListener(context));
    }

}
