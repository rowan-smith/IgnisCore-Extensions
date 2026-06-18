package dev.rono.igniscore.block.silenttnt;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        SilentTntRuntime runtime = new SilentTntRuntime(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new SilentTntOnBlockClickListener());
        context.eventBus().subscribe(new SilentTntOnBlockTickListener(runtime));
        context.eventBus().subscribe(new SilentTntOnBlockTriggerListener(runtime));
    }

}
