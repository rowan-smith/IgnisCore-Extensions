package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;

/**
 * Particle and sound flourishes for utility extensions.
 */
final class TheatricsSupport {
    private TheatricsSupport() {
    }

    static void sparkle(IgnisWorld world, IgnisLocation center, String particle, int count) {
        world.spawnParticle(center, particle, count, 0.35, 0.25, 0.35, 0.02);
    }

    static void chime(IgnisWorld world, IgnisLocation center, float pitch) {
        world.playSound(center, "BLOCK_NOTE_BLOCK_CHIME", 0.9f, pitch);
    }

    static void pulseRing(IgnisWorld world, IgnisLocation center, double radius, String particle) {
        for (int i = 0; i < 360; i += 20) {
            double rad = Math.toRadians(i);
            IgnisLocation point = center.add(Math.cos(rad) * radius, 0.1, Math.sin(rad) * radius);
            world.spawnParticle(point, particle, 1, 0, 0, 0, 0);
        }
    }

    static void scanBeam(IgnisWorld world, IgnisLocation from, IgnisLocation toward, String particle) {
        double dx = toward.x() - from.x();
        double dy = toward.y() - from.y();
        double dz = toward.z() - from.z();
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (dist < 0.5) {
            return;
        }
        int steps = (int) Math.min(12, dist);
        for (int i = 1; i <= steps; i++) {
            double t = i / (double) steps;
            IgnisLocation point = from.add(dx * t, dy * t, dz * t);
            world.spawnParticle(point, particle, 2, 0.05, 0.05, 0.05, 0.01);
        }
    }

    static void igniteFlare(IgnisWorld world, IgnisLocation center) {
        world.spawnParticle(center, "FLAME", 16, 0.35, 0.35, 0.35, 0.04);
        world.spawnParticle(center, "SMOKE", 8, 0.2, 0.2, 0.2, 0.02);
        world.spawnParticle(center, "LAVA", 4, 0.15, 0.15, 0.15, 0.01);
        world.playSound(center, "ENTITY_BLAZE_SHOOT", 0.9f, 1.2f);
    }

    static void fusePulse(IgnisWorld world, IgnisLocation center, int ticksLeft, int fuseTicks) {
        if (fuseTicks <= 0 || ticksLeft <= 0) {
            return;
        }
        float urgency = 1.0f - (ticksLeft / (float) fuseTicks);
        int smoke = 2 + (int) (urgency * 6);
        world.spawnParticle(center, "SMOKE", smoke, 0.25, 0.15, 0.25, 0.01);
        if (ticksLeft <= fuseTicks / 4 || ticksLeft % 10 == 0) {
            world.spawnParticle(center, "FLAME", 2 + (int) (urgency * 4), 0.2, 0.1, 0.2, 0.02);
        }
        if (ticksLeft <= 20 && ticksLeft % 5 == 0) {
            world.playSound(center, "BLOCK_NOTE_BLOCK_PLING", 0.5f, 0.8f + urgency);
        }
    }

    static void detonationFlash(IgnisWorld world, IgnisLocation center, float power) {
        int particles = Math.min(40, 8 + (int) (power * 2));
        world.spawnParticle(center, "EXPLOSION", particles, 0.6, 0.4, 0.6, 0.02);
        world.spawnParticle(center, "CLOUD", particles / 2, 0.8, 0.5, 0.8, 0.01);
        world.playSound(center, "ENTITY_GENERIC_EXPLODE", Math.min(2.0f, 0.6f + power / 10f), 0.9f);
    }
}
