package dev.rono.igniscore.block.displaycase;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class DisplayCaseRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    DisplayCaseRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "display-case");
    }
}

