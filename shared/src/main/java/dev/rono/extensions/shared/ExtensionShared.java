package dev.rono.extensions.shared;

import dev.rono.extensions.shared.impl.BlastsApi;
import dev.rono.extensions.shared.impl.BuriedMinesApi;
import dev.rono.extensions.shared.impl.ConfigApi;
import dev.rono.extensions.shared.impl.ConsumableApi;
import dev.rono.extensions.shared.impl.EntitiesApi;
import dev.rono.extensions.shared.impl.ExplosionApi;
import dev.rono.extensions.shared.impl.GuiApi;
import dev.rono.extensions.shared.impl.LinkApi;
import dev.rono.extensions.shared.impl.PhysicsApi;
import dev.rono.extensions.shared.impl.PreviewApi;
import dev.rono.extensions.shared.impl.ProcessingApi;
import dev.rono.extensions.shared.impl.RemoteApi;
import dev.rono.extensions.shared.impl.ScanApi;
import dev.rono.extensions.shared.impl.TheatricsApi;
import dev.rono.extensions.shared.impl.ThrowableApi;
import dev.rono.extensions.shared.impl.TicksApi;
import dev.rono.extensions.shared.impl.TransformApi;
import dev.rono.extensions.shared.impl.VariantsApi;

/**
 * Entry point for optional extension helper APIs shipped with IgnisCore.
 *
 * <p>All shared helpers are accessed through the grouped accessors on this class
 * (for example {@code ExtensionShared.explosion()}, {@code ExtensionShared.theatrics()}).
 * Do not import implementation classes from {@code dev.rono.extensions.shared.impl}.</p>
 *
 * <p>Depend on {@code dev.rono.extensions:shared} with {@code provided} scope and do not
 * shade this module into extension JARs — helpers are supplied at runtime by the bootstrap
 * plugin.</p>
 *
 * @see dev.rono.igniscore.api.strategy.IgnisStrategies
 */
public final class ExtensionShared {
    private ExtensionShared() {
    }

    /** Explosion tuning and detonation helpers. */
    public static ExplosionApi explosion() {
        return ExplosionApi.INSTANCE;
    }

    /** Typed {@code custom_data} views for common extension kinds. */
    public static ConfigApi config() {
        return ConfigApi.INSTANCE;
    }

    /** Particles, sounds, and scan visuals. */
    public static TheatricsApi theatrics() {
        return TheatricsApi.INSTANCE;
    }

    /** Repeating tasks for placed blocks. */
    public static TicksApi ticks() {
        return TicksApi.INSTANCE;
    }

    /** World scanning helpers (ores, crops, copper, moss). */
    public static ScanApi scan() {
        return ScanApi.INSTANCE;
    }

    /** Item ↔ block NBT linking. */
    public static LinkApi link() {
        return LinkApi.INSTANCE;
    }

    /** Remote activation registry for linked blocks. */
    public static RemoteApi remote() {
        return RemoteApi.INSTANCE;
    }

    /** Consumable cooldown and consume-one helpers. */
    public static ConsumableApi consumable() {
        return ConsumableApi.INSTANCE;
    }

    /** Entity queries and radius effects. */
    public static EntitiesApi entities() {
        return EntitiesApi.INSTANCE;
    }

    /** Processing GUI inventory helpers. */
    public static ProcessingApi processing() {
        return ProcessingApi.INSTANCE;
    }

    /** Specialized explosive blast patterns. */
    public static VariantsApi variants() {
        return VariantsApi.INSTANCE;
    }

    /** Shaped region blasts, directional bursts, entity knockback, shrapnel. */
    public static BlastsApi blasts() {
        return BlastsApi.INSTANCE;
    }

    /** Buried mine proximity arming. */
    public static BuriedMinesApi buriedMines() {
        return BuriedMinesApi.INSTANCE;
    }

    /** Block mutation helpers (elemental transforms, breaching). */
    public static TransformApi transform() {
        return TransformApi.INSTANCE;
    }

    /** Entity motion and physics effects. */
    public static PhysicsApi physics() {
        return PhysicsApi.INSTANCE;
    }

    /** Fake explosions, previews, hologram countdowns. */
    public static PreviewApi preview() {
        return PreviewApi.INSTANCE;
    }

    /** Snowball-style throwable item explosives. */
    public static ThrowableApi throwable() {
        return ThrowableApi.INSTANCE;
    }

    /** Block GUI registries (storage, trade). */
    public static GuiApi gui() {
        return GuiApi.INSTANCE;
    }
}
