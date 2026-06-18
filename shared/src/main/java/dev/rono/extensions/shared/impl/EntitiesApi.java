package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.port.IgnisWorld;
import java.util.List;

/**
 * Public API for entities helpers.
 */
public final class EntitiesApi {
    public static final EntitiesApi INSTANCE = new EntitiesApi();

    private EntitiesApi() {
    }

    public boolean isHostile(Object entity) {
        return EntityUtilSupport.isHostile(entity);
    }
    public boolean isPassive(Object entity) {
        return EntityUtilSupport.isPassive(entity);
    }
    public boolean isLootEntity(Object entity) {
        return EntityUtilSupport.isLootEntity(entity);
    }
    public void pullLoot(IgnisWorld world, IgnisLocation target, double radius, double strength) {
        EntityUtilSupport.pullLoot(world, target, radius, strength);
    }
    public void freezeInRadius(IgnisWorld world, IgnisLocation center, double radius) {
        EntityUtilSupport.freezeInRadius(world, center, radius);
    }
    public void teleportRandomHorizontal(IgnisWorld world, IgnisLocation center, double radius, double distance) {
        EntityUtilSupport.teleportRandomHorizontal(world, center, radius, distance);
    }
    public int countHostiles(IgnisWorld world, IgnisLocation center, double radius) {
        return EntityUtilSupport.countHostiles(world, center, radius);
    }
    public int countPassives(IgnisWorld world, IgnisLocation center, double radius) {
        return EntityUtilSupport.countPassives(world, center, radius);
    }
    public void herdPassives(IgnisWorld world, IgnisLocation target, double radius) {
        EntityUtilSupport.herdPassives(world, target, radius);
    }
    public void swapNearestPlayers(IgnisWorld world, IgnisLocation center, double radius) {
        EntityUtilSupport.swapNearestPlayers(world, center, radius);
    }
}
