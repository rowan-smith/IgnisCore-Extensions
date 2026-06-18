package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisTask;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.util.Locations;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Public API for ticks helpers.
 */
public final class TicksApi {
    public static final TicksApi INSTANCE = new TicksApi();

    private TicksApi() {
    }

    public void start(IgnisStrategyContext context, IgnisLocation location, long periodTicks, Runnable action) {
        PlacedTickSupport.start(context, location, periodTicks, action);
    }
    public void stop(IgnisLocation location) {
        PlacedTickSupport.stop(location);
    }
}
