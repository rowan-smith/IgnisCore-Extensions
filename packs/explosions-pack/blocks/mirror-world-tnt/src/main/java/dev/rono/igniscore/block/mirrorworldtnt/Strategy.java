package dev.rono.igniscore.block.mirrorworldtnt;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;
import dev.rono.extensions.shared.api.theatrics.CombustibleIgniteTheatricsListener;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        context.eventBus().subscribe(new CombustibleIgniteTheatricsListener(context));
        context.eventBus().subscribe(new CombustibleFuseTheatricsListener(context));
        context.eventBus().subscribe(new MirrorWorldTntOnBlockClickListener());
        context.eventBus().subscribe(new MirrorWorldTntOnBlockTickListener(context));
        context.eventBus().subscribe(new MirrorWorldTntOnBlockTriggerListener(context));
    }

}
