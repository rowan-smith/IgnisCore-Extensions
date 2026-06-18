package dev.rono.igniscore.block.irrigationsprinkler;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class IrrigationSprinklerRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    IrrigationSprinklerRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "irrigation-sprinkler");
    }
}

