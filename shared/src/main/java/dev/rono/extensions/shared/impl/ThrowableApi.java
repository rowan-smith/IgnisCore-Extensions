package dev.rono.extensions.shared.impl;

import dev.rono.extensions.shared.api.throwable.ThrowableDetonationHandler;
import dev.rono.igniscore.api.model.ItemDefinition;
import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

/**
 * Public API for throwable item explosives.
 */
public final class ThrowableApi {
    public static final ThrowableApi INSTANCE = new ThrowableApi();

    private ThrowableApi() {
    }

    public void throwProjectile(IgnisStrategyContext context,
                                IgnisPlayer player,
                                ItemDefinition definition,
                                IgnisItem item,
                                ThrowableDetonationHandler onDetonate) {
        ThrowableSupport.throwProjectile(context, player, definition, item, onDetonate);
    }
}
