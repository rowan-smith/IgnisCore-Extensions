package dev.rono.igniscore.block.fortunecookiemaker;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class FortuneCookieMakerRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    FortuneCookieMakerRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "fortune-cookie-maker");
    }
}

