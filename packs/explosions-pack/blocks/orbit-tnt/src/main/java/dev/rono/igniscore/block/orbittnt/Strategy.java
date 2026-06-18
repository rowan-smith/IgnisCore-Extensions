package dev.rono.igniscore.block.orbittnt;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        OrbitTntRuntime runtime = new OrbitTntRuntime(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new CombustibleFuseTheatricsListener(context));
        context.eventBus().subscribe(new OrbitTntOnBlockClickListener());
        context.eventBus().subscribe(new OrbitTntOnBlockTickListener(runtime));
        context.eventBus().subscribe(new OrbitTntOnBlockTriggerListener(runtime));
    }

}
