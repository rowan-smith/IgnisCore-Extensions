package dev.rono.igniscore.block.piglinbarterpost;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class PiglinBarterPostRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    PiglinBarterPostRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "piglin-barter-post");
    }
}

