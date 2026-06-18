package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.port.IgnisScheduler;
import dev.rono.igniscore.api.port.IgnisTask;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.service.IgnisRegionService;
import dev.rono.igniscore.api.strategy.StrategySupport;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Public API for transform helpers.
 */
public final class TransformApi {
    public static final TransformApi INSTANCE = new TransformApi();

    private TransformApi() {
    }

    public void transformSphere(IgnisWorld world,
                                        IgnisLocation center,
                                        int radius,
                                        Predicate<String> matcher,
                                        String replacement) {
        BlockTransformSupport.transformSphere(world, center, radius, matcher, replacement);
    }
    public void acidCorrode(IgnisWorld world, IgnisLocation center, int radius) {
        BlockTransformSupport.acidCorrode(world, center, radius);
    }
    public void frostTransform(IgnisWorld world, IgnisLocation center, int radius) {
        BlockTransformSupport.frostTransform(world, center, radius);
    }
    public void igniteFireRing(IgnisWorld world, IgnisLocation center, int radius) {
        BlockTransformSupport.igniteFireRing(world, center, radius);
    }
    public void plantWildfireSpiral(IgnisWorld world, IgnisLocation center, int arms, int length) {
        BlockTransformSupport.plantWildfireSpiral(world, center, arms, length);
    }
    public void mudslideFlow(IgnisWorld world,
                                     IgnisLocation center,
                                     int radius,
                                     int depth,
                                     IgnisScheduler scheduler,
                                     int batchDelay) {
        BlockTransformSupport.mudslideFlow(world, center, radius, depth, scheduler, batchDelay);
    }
    public void tsunamiWave(IgnisWorld world, IgnisLocation center, int radius, IgnisScheduler scheduler) {
        BlockTransformSupport.tsunamiWave(world, center, radius, scheduler);
    }
    public float breachingPower(BlockDefinition definition, float basePower) {
        return BlockTransformSupport.breachingPower(definition, basePower);
    }
    public boolean isBreachingWeak(String material) {
        return BlockTransformSupport.isBreachingWeak(material);
    }
    public boolean isBreachingStrong(String material) {
        return BlockTransformSupport.isBreachingStrong(material);
    }
    public void infernoPatch(IgnisWorld world, IgnisLocation center, int radius) {
        BlockTransformSupport.infernoPatch(world, center, radius);
    }
    public void spreadInferno(IgnisWorld world,
                                    IgnisLocation center,
                                    int radius,
                                    int durationTicks,
                                    IgnisScheduler scheduler) {
        BlockTransformSupport.spreadInferno(world, center, radius, durationTicks, scheduler);
    }
    public void poisonCloud(IgnisWorld world,
                                    IgnisLocation center,
                                    double radius,
                                    int durationTicks,
                                    IgnisScheduler scheduler) {
        BlockTransformSupport.poisonCloud(world, center, radius, durationTicks, scheduler);
    }
    public void blackHoleCollapse(IgnisRegionService region,
                                          IgnisWorld world,
                                          IgnisLocation center,
                                          int voidRadius,
                                          boolean bedrockShell,
                                          IgnisScheduler scheduler) {
        BlockTransformSupport.blackHoleCollapse(region, world, center, voidRadius, bedrockShell, scheduler);
    }
    public void breachingBlast(IgnisWorld world, IgnisLocation center, int radius) {
        BlockTransformSupport.breachingBlast(world, center, radius);
    }
    public int customInt(BlockDefinition definition, String key, int defaultValue) {
        return BlockTransformSupport.customInt(definition, key, defaultValue);
    }
}
