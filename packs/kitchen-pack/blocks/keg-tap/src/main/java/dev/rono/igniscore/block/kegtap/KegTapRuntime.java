package dev.rono.igniscore.block.kegtap;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class KegTapRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    KegTapRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "keg-tap");
    }
}

