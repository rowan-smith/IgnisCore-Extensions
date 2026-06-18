package dev.rono.igniscore.block.antigravityzone;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.extensions.shared.api.theatrics.CombustiblePlaceTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        AntiGravityZoneRuntime runtime = new AntiGravityZoneRuntime(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new CombustiblePlaceTheatricsListener(context));
        context.eventBus().subscribe(new CombustibleFuseTheatricsListener(context));
        context.eventBus().subscribe(new AntiGravityZoneOnBlockClickListener());
        context.eventBus().subscribe(new AntiGravityZoneOnBlockPlaceListener(runtime));
        context.eventBus().subscribe(new AntiGravityZoneOnBlockTriggerListener(runtime));
    }

}
