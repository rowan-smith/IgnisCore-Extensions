package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.util.PlacedMetaSupport;

/**
 * Public API for variants helpers.
 */
public final class VariantsApi {
    public static final VariantsApi INSTANCE = new VariantsApi();

    private VariantsApi() {
    }

    public void cardinalSplit(IgnisWorld world, IgnisLocation center, float power, double offset) {
        ExplosionVariantsSupport.cardinalSplit(world, center, power, offset);
    }
    public void ricochetRay(IgnisWorld world, IgnisLocation start, float yaw, int bounces,
                                    double step, float power) {
        ExplosionVariantsSupport.ricochetRay(world, start, yaw, bounces, step, power);
    }
    public void mirrorBlast(IgnisWorld world, IgnisLocation center, float power, double mirrorY) {
        ExplosionVariantsSupport.mirrorBlast(world, center, power, mirrorY);
    }
    public void phaseBurst(IgnisWorld world, IgnisLocation center, float power, double radius) {
        ExplosionVariantsSupport.phaseBurst(world, center, power, radius);
    }
    public float resolveYaw(IgnisWorld world, IgnisLocation block, Object triggerContext,
                                    dev.rono.igniscore.api.strategy.IgnisStrategyContext context) {
        return ExplosionVariantsSupport.resolveYaw(world, block, triggerContext, context);
    }
}
