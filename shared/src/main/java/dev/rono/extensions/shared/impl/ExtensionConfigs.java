package dev.rono.extensions.shared.impl;

import dev.rono.extensions.shared.api.config.ExplosionConfig;
import dev.rono.extensions.shared.api.config.ThrowableItemConfig;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.model.ItemDefinition;

/**
 * Convenience entry points for typed extension configuration records.
 */
final class ExtensionConfigs {
    private ExtensionConfigs() {
    }

    static ExplosionConfig explosion(BlockDefinition definition) {
        return ExplosionConfig.from(definition);
    }

    static ThrowableItemConfig throwable(ItemDefinition definition) {
        return ThrowableItemConfig.from(definition);
    }
}
