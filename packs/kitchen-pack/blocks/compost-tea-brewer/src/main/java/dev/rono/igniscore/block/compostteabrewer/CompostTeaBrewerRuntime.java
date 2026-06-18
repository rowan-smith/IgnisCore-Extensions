package dev.rono.igniscore.block.compostteabrewer;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class CompostTeaBrewerRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    CompostTeaBrewerRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "compost-tea-brewer");
    }
}

