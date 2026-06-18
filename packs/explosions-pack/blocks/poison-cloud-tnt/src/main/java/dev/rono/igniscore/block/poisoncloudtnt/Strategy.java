package dev.rono.igniscore.block.poisoncloudtnt;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        PoisonCloudTntRuntime runtime = new PoisonCloudTntRuntime(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new CombustibleFuseTheatricsListener(context));
        context.eventBus().subscribe(new PoisonCloudTntOnBlockClickListener());
        context.eventBus().subscribe(new PoisonCloudTntOnBlockTickListener(runtime));
        context.eventBus().subscribe(new PoisonCloudTntOnBlockTriggerListener(runtime));
    }

}
