package dev.rono.igniscore.block.magnettnt;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        MagnetTntRuntime runtime = new MagnetTntRuntime(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new CombustibleFuseTheatricsListener(context));
        context.eventBus().subscribe(new MagnetTntOnBlockClickListener());
        context.eventBus().subscribe(new MagnetTntOnBlockTickListener(runtime));
        context.eventBus().subscribe(new MagnetTntOnBlockTriggerListener(runtime));
    }

}
