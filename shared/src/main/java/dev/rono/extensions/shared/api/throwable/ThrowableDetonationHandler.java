package dev.rono.extensions.shared.api.throwable;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;

/**
 * Callback when a thrown projectile detonates.
 */
@FunctionalInterface
public interface ThrowableDetonationHandler {
    void detonate(IgnisWorld world, IgnisLocation impact);
}
