package dev.rono.igniscore.block.prepcounter;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class PrepCounterRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    PrepCounterRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "prep-counter");
    }
}

