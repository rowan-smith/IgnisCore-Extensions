package dev.rono.igniscore.block.chunkloaderlite;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class ChunkLoaderLiteRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    ChunkLoaderLiteRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "chunk-loader-lite");
    }
}

