package dev.rono.igniscore.block.chickencoopcache;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class ChickenCoopCacheRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    ChickenCoopCacheRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "chicken-coop-cache");
    }
}

