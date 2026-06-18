package dev.rono.igniscore.block.securetradetable;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.SecureTradeRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class SecureTradeTableRuntime {
    final IgnisStrategyContext context;
    final SecureTradeRegistry registry;

    SecureTradeTableRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().secureTrade(context);
    }
}

