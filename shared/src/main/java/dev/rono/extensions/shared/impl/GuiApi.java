package dev.rono.extensions.shared.impl;

import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.extensions.shared.api.gui.SecureTradeRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

/**
 * Public API for block GUI registries.
 */
public final class GuiApi {
    public static final GuiApi INSTANCE = new GuiApi();

    private GuiApi() {
    }

    public BlockStorageRegistry blockStorage(IgnisStrategyContext context, String namespace) {
        return new BlockStorageRegistry(context, namespace);
    }

    public SecureTradeRegistry secureTrade(IgnisStrategyContext context) {
        return new SecureTradeRegistry(context);
    }
}
