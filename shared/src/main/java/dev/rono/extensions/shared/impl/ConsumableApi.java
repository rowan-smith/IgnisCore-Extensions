package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.service.IgnisNbtService;

/**
 * Public API for consumable helpers.
 */
public final class ConsumableApi {
    public static final ConsumableApi INSTANCE = new ConsumableApi();

    private ConsumableApi() {
    }

    public boolean isOnCooldown(IgnisNbtService nbt, IgnisItem item, String key, long cooldownTicks) {
        return ConsumableSupport.isOnCooldown(nbt, item, key, cooldownTicks);
    }
    public void markUsed(IgnisNbtService nbt, IgnisItem item, String key) {
        ConsumableSupport.markUsed(nbt, item, key);
    }
    public void consumeOne(IgnisItem item) {
        ConsumableSupport.consumeOne(item);
    }
    public void notifyCooldown(IgnisPlayer player, long remainingMs) {
        ConsumableSupport.notifyCooldown(player, remainingMs);
    }
}
