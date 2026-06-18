package dev.rono.igniscore.block.keyedhatch;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockBreakEvent;
import dev.rono.igniscore.api.event.OnBlockBreakListener;

final class KeyedHatchOnBlockBreakListener implements OnBlockBreakListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        ExtensionShared.remote().unregister(event.block().location());
        KeyedHatchSupport.OPEN.remove(ExtensionShared.remote().key(event.block().location()));
    }
}

