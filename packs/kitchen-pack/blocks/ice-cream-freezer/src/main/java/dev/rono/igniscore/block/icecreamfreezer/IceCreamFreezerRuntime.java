package dev.rono.igniscore.block.icecreamfreezer;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class IceCreamFreezerRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    IceCreamFreezerRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "ice-cream-freezer");
    }
}

