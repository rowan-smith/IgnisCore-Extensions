package dev.rono.igniscore.block.pocketdimensioncache;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class PocketDimensionCacheRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    PocketDimensionCacheRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "pocket-dimension-cache");
    }
}

