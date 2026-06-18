package dev.rono.igniscore.block.screenshakecharge;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        ScreenShakeChargeRuntime runtime = new ScreenShakeChargeRuntime(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new ScreenShakeChargeOnBlockClickListener());
        context.eventBus().subscribe(new ScreenShakeChargeOnBlockTriggerListener(runtime));
    }

}
