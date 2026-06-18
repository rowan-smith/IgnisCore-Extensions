package dev.rono.igniscore.block.coffeebrewer;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class CoffeeBrewerRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    CoffeeBrewerRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "coffee-brewer");
    }
}

