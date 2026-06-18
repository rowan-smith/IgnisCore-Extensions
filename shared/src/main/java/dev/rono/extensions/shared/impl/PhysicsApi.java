package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.port.IgnisScheduler;
import dev.rono.igniscore.api.port.IgnisTask;
import dev.rono.igniscore.api.port.IgnisWorld;
import java.util.HashMap;
import java.util.Map;

/**
 * Public API for physics helpers.
 */
public final class PhysicsApi {
    public static final PhysicsApi INSTANCE = new PhysicsApi();

    private PhysicsApi() {
    }

    public void pullToward(IgnisWorld world, IgnisLocation target, double radius, double strength) {
        EntityPhysicsSupport.pullToward(world, target, radius, strength);
    }
    public void pushFrom(IgnisWorld world, IgnisLocation source, double radius, double strength, boolean lift) {
        EntityPhysicsSupport.pushFrom(world, source, radius, strength, lift);
    }
    public void orbit(IgnisWorld world,
                              IgnisLocation center,
                              double radius,
                              double angularSpeed,
                              int elapsedTicks) {
        EntityPhysicsSupport.orbit(world, center, radius, angularSpeed, elapsedTicks);
    }
    public void spiralKnockback(IgnisWorld world, IgnisLocation center, double radius, double strength, int tick) {
        EntityPhysicsSupport.spiralKnockback(world, center, radius, strength, tick);
    }
    public void launchUpward(IgnisWorld world, IgnisLocation center, double radius, double velocity) {
        EntityPhysicsSupport.launchUpward(world, center, radius, velocity);
    }
    public void applyLevitation(IgnisWorld world, IgnisLocation center, double radius, double upward) {
        EntityPhysicsSupport.applyLevitation(world, center, radius, upward);
    }
    public void freezeEntities(IgnisWorld world, IgnisLocation center, double radius, int durationTicks) {
        EntityPhysicsSupport.freezeEntities(world, center, radius, durationTicks);
    }
    public void snapDamage(IgnisWorld world,
                                   Map<Object, IgnisLocation> anchors,
                                   IgnisLocation center,
                                   double damageRadius) {
        EntityPhysicsSupport.snapDamage(world, anchors, center, damageRadius);
    }
    public Map<Object, IgnisLocation> snapshotPositions(IgnisWorld world, IgnisLocation center, double radius) {
        return EntityPhysicsSupport.snapshotPositions(world, center, radius);
    }
    public boolean isLootEntity(Object entity) {
        return EntityPhysicsSupport.isLootEntity(entity);
    }
    public void pullLootToward(IgnisWorld world, IgnisLocation target, double radius, double strength) {
        EntityPhysicsSupport.pullLootToward(world, target, radius, strength);
    }
    public void scatterLoot(IgnisWorld world, IgnisLocation center, double radius, double strength) {
        EntityPhysicsSupport.scatterLoot(world, center, radius, strength);
    }
    public void applyFeatherfall(IgnisWorld world,
                                         IgnisLocation center,
                                         double radius,
                                         int durationTicks,
                                         IgnisScheduler scheduler) {
        EntityPhysicsSupport.applyFeatherfall(world, center, radius, durationTicks, scheduler);
    }
    public void coneKnockback(IgnisWorld world,
                                      IgnisLocation origin,
                                      float yawDegrees,
                                      double coneAngleDegrees,
                                      double range,
                                      double strength) {
        EntityPhysicsSupport.coneKnockback(world, origin, yawDegrees, coneAngleDegrees, range, strength);
    }
}
