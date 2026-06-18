package dev.rono.igniscore.block.lostandfoundbin;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class LostAndFoundBinRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;
    int sweepCounter;

    LostAndFoundBinRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "lost-and-found-bin");
    }
}

