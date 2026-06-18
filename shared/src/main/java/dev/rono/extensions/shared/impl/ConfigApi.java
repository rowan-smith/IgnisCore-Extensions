package dev.rono.extensions.shared.impl;

import dev.rono.extensions.shared.api.config.ExplosionConfig;
import dev.rono.extensions.shared.impl.ExtensionConfigs;
import dev.rono.extensions.shared.api.config.ThrowableItemConfig;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.model.ItemDefinition;

/**
 * Public API for typed extension configuration.
 */
public final class ConfigApi {
    public static final ConfigApi INSTANCE = new ConfigApi();

    private ConfigApi() {
    }

    public ExplosionConfig explosion(BlockDefinition definition) {
        return ExtensionConfigs.explosion(definition);
    }

    public ThrowableItemConfig throwable(ItemDefinition definition) {
        return ExtensionConfigs.throwable(definition);
    }
}
