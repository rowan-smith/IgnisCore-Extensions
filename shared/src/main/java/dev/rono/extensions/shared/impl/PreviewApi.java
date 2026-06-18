package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.port.IgnisScheduler;
import dev.rono.igniscore.api.port.IgnisTask;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.service.IgnisEffectService;
import dev.rono.igniscore.api.service.IgnisHologramService;
import dev.rono.igniscore.api.service.IgnisProtocolService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Public API for preview helpers.
 */
public final class PreviewApi {
    public static final PreviewApi INSTANCE = new PreviewApi();

    private PreviewApi() {
    }

    public void playDecoyExplosions(IgnisEffectService effects,
                                            IgnisWorld world,
                                            IgnisLocation realCenter,
                                            int decoyCount,
                                            double spread,
                                            float power) {
        PreviewTrickSupport.playDecoyExplosions(effects, world, realCenter, decoyCount, spread, power);
    }

    public void fakeExplosion(IgnisEffectService effects,
                              IgnisWorld world,
                              IgnisLocation location,
                              float power,
                              java.util.Collection<IgnisPlayer> players) {
        VisualEffectSupport.safeFakeExplosion(effects, world, location, power, players);
    }
    public void scareExplosion(IgnisEffectService effects,
                                       IgnisProtocolService protocol,
                                       IgnisWorld world,
                                       IgnisLocation center,
                                       double radius,
                                       float fakePower) {
        PreviewTrickSupport.scareExplosion(effects, protocol, world, center, radius, fakePower);
    }
    public void silentDetonation(IgnisWorld world,
                                         IgnisLocation center,
                                         float realPower,
                                         boolean blockDamage) {
        PreviewTrickSupport.silentDetonation(world, center, realPower, blockDamage);
    }
    public void ringBlockPreviews(IgnisEffectService effects,
                                          IgnisPlayer viewer,
                                          IgnisLocation center,
                                          int count,
                                          double radius,
                                          String materialKey) {
        PreviewTrickSupport.ringBlockPreviews(effects, viewer, center, count, radius, materialKey);
    }
    public void cycleBlockPreviews(IgnisEffectService effects,
                                           List<IgnisPlayer> viewers,
                                           IgnisLocation center,
                                           int radius,
                                           String[] materials,
                                           int tick) {
        PreviewTrickSupport.cycleBlockPreviews(effects, viewers, center, radius, materials, tick);
    }
    public Object spawnCountdownHologram(IgnisHologramService holograms,
                                                 IgnisLocation location,
                                                 int seconds) {
        return PreviewTrickSupport.spawnCountdownHologram(holograms, location, seconds);
    }
    public void updateCountdownHologram(IgnisHologramService holograms,
                                                Object handle,
                                                int secondsRemaining) {
        PreviewTrickSupport.updateCountdownHologram(holograms, handle, secondsRemaining);
    }
    public void deleteHologram(IgnisHologramService holograms, Object handle) {
        PreviewTrickSupport.deleteHologram(holograms, handle);
    }
    public void delayedFakeBursts(IgnisEffectService effects,
                                          IgnisWorld world,
                                          IgnisLocation center,
                                          int bursts,
                                          int delayTicks,
                                          IgnisScheduler scheduler) {
        PreviewTrickSupport.delayedFakeBursts(effects, world, center, bursts, delayTicks, scheduler);
    }
    public void forNearbyPlayers(IgnisWorld world, IgnisLocation center, double radius, Consumer<IgnisPlayer> action) {
        PreviewTrickSupport.forNearbyPlayers(world, center, radius, action);
    }
    public List<IgnisLocation> circleOffsets(int count, double radius) {
        return PreviewTrickSupport.circleOffsets(count, radius);
    }
}
