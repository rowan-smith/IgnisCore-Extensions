package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.model.ItemDefinition;
import dev.rono.igniscore.api.model.RuntimeBlockInstance;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.StrategySupport;
import java.util.Map;

/**
 * Public API for explosion helpers.
 */
public final class ExplosionApi {
    public static final ExplosionApi INSTANCE = new ExplosionApi();

    private ExplosionApi() {
    }

    public int fuse(BlockDefinition definition, int defaultFuse) {
        return ExplosionSupport.fuse(definition, defaultFuse);
    }
    public double radius(BlockDefinition definition, double defaultRadius) {
        return ExplosionSupport.radius(definition, defaultRadius);
    }
    public float resolvePower(BlockDefinition definition, double defaultPower) {
        return ExplosionSupport.resolvePower(definition, defaultPower);
    }
    public float resolvePower(ItemDefinition definition, double defaultPower) {
        return ExplosionSupport.resolvePower(definition, defaultPower);
    }
    public float resolvePower(Map<String, Object> customData, double defaultPower) {
        return ExplosionSupport.resolvePower(customData, defaultPower);
    }
    public void create(IgnisWorld world, IgnisLocation location, BlockDefinition definition,
                                       double defaultPower, boolean defaultFire) {
        ExplosionSupport.createExplosion(world, location, definition, defaultPower, defaultFire);
    }
    public void create(IgnisWorld world, IgnisLocation location, ItemDefinition definition,
                                       double defaultPower, boolean defaultFire) {
        ExplosionSupport.createExplosion(world, location, definition, defaultPower, defaultFire);
    }
    public void create(IgnisWorld world, IgnisLocation location, Map<String, Object> customData,
                                       double defaultPower, boolean defaultFire) {
        ExplosionSupport.createExplosion(world, location, customData, defaultPower, defaultFire);
    }
    public void create(IgnisWorld world, IgnisLocation location, float power, boolean fire, boolean blockDamage) {
        ExplosionSupport.createExplosion(world, location, power, fire, blockDamage);
    }
    public int fuseTicks(RuntimeBlockInstance instance, int defaultFuse) {
        return ExplosionSupport.fuseTicks(instance, defaultFuse);
    }
    public int elapsedFuseTicks(RuntimeBlockInstance instance, int defaultFuse) {
        return ExplosionSupport.elapsedFuseTicks(instance, defaultFuse);
    }
}
