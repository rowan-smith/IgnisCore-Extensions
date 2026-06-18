package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.service.IgnisEffectService;
import dev.rono.igniscore.api.service.IgnisProtocolService;

import java.util.Collection;

final class VisualEffectSupport {
    private VisualEffectSupport() {
    }

    static void safeFakeExplosion(IgnisEffectService effects,
                                  IgnisWorld world,
                                  IgnisLocation location,
                                  float power,
                                  Collection<IgnisPlayer> players) {
        try {
            effects.playFakeExplosion(location, power, players);
        } catch (RuntimeException ignored) {
            fallbackExplosion(world, location);
        }
    }

    static void safeProtocolExplosion(IgnisProtocolService protocol,
                                      IgnisWorld world,
                                      IgnisLocation location,
                                      float power,
                                      Collection<IgnisPlayer> players) {
        if (protocol == null || !protocol.isEnabled()) {
            return;
        }
        try {
            protocol.sendFakeExplosion(location, power, players);
        } catch (RuntimeException ignored) {
            fallbackExplosion(world, location);
        }
    }

    static void fallbackExplosion(IgnisWorld world, IgnisLocation location) {
        world.spawnParticle(location, "EXPLOSION_EMITTER", 1, 0.0, 0.0, 0.0, 0.0);
        world.playSound(location, "ENTITY_GENERIC_EXPLODE", 1.0f, 0.8f);
    }
}
