package dev.rono.igniscore.block.waypointmarker;

import dev.rono.igniscore.api.strategy.AbstractIgnisBlockStrategy;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.extensions.shared.api.theatrics.ExtensionPlaceTheatricsListener;

public class Strategy extends AbstractIgnisBlockStrategy {

    public Strategy(IgnisStrategyContext context) {
        super(context);
        context.eventBus().subscribe(new ExtensionPlaceTheatricsListener(context));
        context.eventBus().subscribe(new WaypointMarkerOnBlockClickListener());
        context.eventBus().subscribe(new WaypointMarkerOnBlockInteractListener(context));
    }

}
