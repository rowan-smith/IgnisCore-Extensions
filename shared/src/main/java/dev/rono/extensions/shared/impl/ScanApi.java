package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import java.util.Locale;
import java.util.Set;

/**
 * Public API for scan helpers.
 */
public final class ScanApi {
    public static final ScanApi INSTANCE = new ScanApi();

    private ScanApi() {
    }

    public IgnisLocation findNearestMatching(IgnisWorld world, IgnisLocation center, int radius, Set<String> targets) {
        return BlockScanSupport.findNearestMatching(world, center, radius, targets);
    }
    public IgnisLocation findNearestOre(IgnisWorld world, IgnisLocation center, int radius) {
        return BlockScanSupport.findNearestOre(world, center, radius);
    }
    public int countCrops(IgnisWorld world, IgnisLocation center, int radius) {
        return BlockScanSupport.countCrops(world, center, radius);
    }
    public void bonemealRadius(IgnisWorld world, IgnisLocation center, int radius) {
        BlockScanSupport.bonemealRadius(world, center, radius);
    }
    public void mossifyNearWater(IgnisWorld world, IgnisLocation center, int radius) {
        BlockScanSupport.mossifyNearWater(world, center, radius);
    }
    public void deoxidizeCopper(IgnisWorld world, IgnisLocation center, int radius) {
        BlockScanSupport.deoxidizeCopper(world, center, radius);
    }
}
