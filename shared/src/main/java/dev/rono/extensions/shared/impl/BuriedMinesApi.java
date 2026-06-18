package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.IgnisCoreAPI;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisTask;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.util.Locations;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Public API for buriedmines helpers.
 */
public final class BuriedMinesApi {
    public static final BuriedMinesApi INSTANCE = new BuriedMinesApi();

    private BuriedMinesApi() {
    }

    public void arm(IgnisStrategyContext context,
                            IgnisLocation blockLocation,
                            String blockTypeId,
                            double triggerRadius) {
        BuriedMineSupport.arm(context, blockLocation, blockTypeId, triggerRadius);
    }
    public void disarm(IgnisLocation blockLocation) {
        BuriedMineSupport.disarm(blockLocation);
    }
}
