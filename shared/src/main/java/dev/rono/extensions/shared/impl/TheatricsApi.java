package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;

/**
 * Public API for theatrics helpers.
 */
public final class TheatricsApi {
    public static final TheatricsApi INSTANCE = new TheatricsApi();

    private TheatricsApi() {
    }

    public void sparkle(IgnisWorld world, IgnisLocation center, String particle, int count) {
        TheatricsSupport.sparkle(world, center, particle, count);
    }
    public void chime(IgnisWorld world, IgnisLocation center, float pitch) {
        TheatricsSupport.chime(world, center, pitch);
    }
    public void pulseRing(IgnisWorld world, IgnisLocation center, double radius, String particle) {
        TheatricsSupport.pulseRing(world, center, radius, particle);
    }
    public void scanBeam(IgnisWorld world, IgnisLocation from, IgnisLocation toward, String particle) {
        TheatricsSupport.scanBeam(world, from, toward, particle);
    }
}
