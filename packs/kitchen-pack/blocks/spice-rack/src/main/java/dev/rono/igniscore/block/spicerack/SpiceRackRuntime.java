package dev.rono.igniscore.block.spicerack;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class SpiceRackRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    SpiceRackRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "spice-rack");
    }
}

