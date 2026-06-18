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

    /** Burst of flame and smoke when a combustible charge is lit. */
    public void igniteFlare(IgnisWorld world, IgnisLocation center) {
        TheatricsSupport.igniteFlare(world, center);
    }

    /** Escalating fuse countdown particles; call from onBlockTick handlers. */
    public void fusePulse(IgnisWorld world, IgnisLocation center, int ticksLeft, int fuseTicks) {
        TheatricsSupport.fusePulse(world, center, ticksLeft, fuseTicks);
    }

    /** Pre-blast particle and sound flourish scaled to explosion power. */
    public void detonationFlash(IgnisWorld world, IgnisLocation center, float power) {
        TheatricsSupport.detonationFlash(world, center, power);
    }
}
