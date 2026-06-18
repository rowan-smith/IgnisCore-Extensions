package dev.rono.igniscore.block.barnbell;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockBreakEvent;
import dev.rono.igniscore.api.event.OnBlockBreakListener;

final class BarnBellOnBlockBreakListener implements OnBlockBreakListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        ExtensionShared.remote().unregister(event.block().location());
        BarnBellSupport.COOLDOWN.remove(ExtensionShared.remote().key(event.block().location()));
    }
}

