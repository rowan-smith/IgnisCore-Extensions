package dev.rono.igniscore.block.picnicbasket;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.extensions.shared.api.gui.BlockStorageRegistry;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;

final class PicnicBasketRuntime {
    final IgnisStrategyContext context;
    final BlockStorageRegistry registry;

    PicnicBasketRuntime(IgnisStrategyContext context) {
        this.context = context;
                this.registry = ExtensionShared.gui().blockStorage(context, "picnic-basket");
    }
}

