package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.port.IgnisScheduler;
import dev.rono.igniscore.api.port.IgnisTask;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.service.IgnisEffectService;
import dev.rono.igniscore.api.service.IgnisProtocolService;
import dev.rono.igniscore.api.service.IgnisRegionService;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.util.PlacedMetaSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

/**
 * Public API for blasts helpers.
 */
public final class BlastsApi {
    public static final BlastsApi INSTANCE = new BlastsApi();

    private BlastsApi() {
    }

    public void breakHollowSphere(IgnisRegionService region,
                                         IgnisWorld world,
                                         IgnisLocation center,
                                         int outerRadius,
                                         int shellThickness,
                                         boolean staggered,
                                         int batchSize,
                                         int batchDelayTicks,
                                         IgnisScheduler scheduler) {
        BlockBlastSupport.breakHollowSphere(region, world, center, outerRadius, shellThickness, staggered, batchSize, batchDelayTicks, scheduler);
    }
    public void breakTorus(IgnisRegionService region,
                                   IgnisWorld world,
                                   IgnisLocation center,
                                   int majorRadius,
                                   int minorRadius,
                                   boolean staggered,
                                   int batchSize,
                                   int batchDelayTicks,
                                   IgnisScheduler scheduler) {
        BlockBlastSupport.breakTorus(region, world, center, majorRadius, minorRadius, staggered, batchSize, batchDelayTicks, scheduler);
    }
    public void breakCylinderDown(IgnisRegionService region,
                                          IgnisWorld world,
                                          IgnisLocation center,
                                          int radius,
                                          int depth,
                                          boolean staggered,
                                          int batchSize,
                                          int batchDelayTicks,
                                          IgnisScheduler scheduler) {
        BlockBlastSupport.breakCylinderDown(region, world, center, radius, depth, staggered, batchSize, batchDelayTicks, scheduler);
    }
    public void breakUnderwater(IgnisRegionService region,
                                        IgnisWorld world,
                                        IgnisLocation center,
                                        int radius,
                                        boolean staggered,
                                        int batchSize,
                                        int batchDelayTicks,
                                        IgnisScheduler scheduler) {
        BlockBlastSupport.breakUnderwater(region, world, center, radius, staggered, batchSize, batchDelayTicks, scheduler);
    }
    public void breakWithPredicate(IgnisRegionService region,
                                           IgnisWorld world,
                                           IgnisLocation center,
                                           int radius,
                                           Predicate<IgnisLocation> predicate,
                                           boolean staggered,
                                           int batchSize,
                                           int batchDelayTicks,
                                           IgnisScheduler scheduler,
                                           String primaryParticle,
                                           String secondaryParticle) {
        BlockBlastSupport.breakWithPredicate(region, world, center, radius, predicate, staggered, batchSize, batchDelayTicks, scheduler, primaryParticle, secondaryParticle);
    }
    public float resolveYaw(IgnisStrategyContext context, Object triggerContext, IgnisLocation fallback) {
        return DirectionalBlastSupport.resolveYaw(context, triggerContext, fallback);
    }
    public void forwardBlast(IgnisWorld world,
                                     IgnisLocation origin,
                                     float yaw,
                                     int steps,
                                     double stepDistance,
                                     float power,
                                     boolean blockDamage) {
        DirectionalBlastSupport.forwardBlast(world, origin, yaw, steps, stepDistance, power, blockDamage);
    }
    public void coneBlast(IgnisWorld world,
                                IgnisLocation origin,
                                float yaw,
                                double range,
                                double coneAngle,
                                double knockbackStrength) {
        DirectionalBlastSupport.coneBlast(world, origin, yaw, range, coneAngle, knockbackStrength);
    }
    public void applyKnockback(IgnisWorld world,
                                       IgnisLocation center,
                                       double radius,
                                       double strength,
                                       boolean lift) {
        EntityBlastSupport.applyKnockback(world, center, radius, strength, lift);
    }
    public void screenShake(IgnisEffectService effects,
                                    IgnisProtocolService protocol,
                                    IgnisWorld world,
                                    IgnisLocation center,
                                    double radius,
                                    int durationTicks,
                                    int intervalTicks,
                                    float soundVolume,
                                    float soundPitch,
                                    IgnisScheduler scheduler) {
        EntityBlastSupport.screenShake(effects, protocol, world, center, radius, durationTicks, intervalTicks, soundVolume, soundPitch, scheduler);
    }
    public void violentScreenShake(IgnisEffectService effects,
                                           IgnisProtocolService protocol,
                                           IgnisWorld world,
                                           IgnisLocation center,
                                           double radius,
                                           int durationTicks,
                                           IgnisScheduler scheduler) {
        EntityBlastSupport.violentScreenShake(effects, protocol, world, center, radius, durationTicks, scheduler);
    }
    public void launchDebris(IgnisWorld world,
                                   IgnisLocation center,
                                   int scanRadius,
                                   int projectileCount,
                                   double minVelocity,
                                   double maxVelocity) {
        ShrapnelSupport.launchDebris(world, center, scanRadius, projectileCount, minVelocity, maxVelocity);
    }
}
