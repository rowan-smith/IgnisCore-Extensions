package dev.rono.igniscore.block.sprinklerhead;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockBreakEvent;
import dev.rono.igniscore.api.event.OnBlockBreakListener;

final class SprinklerHeadOnBlockBreakListener implements OnBlockBreakListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        ExtensionShared.ticks().stop(event.block().location());
        ExtensionShared.remote().unregister(event.block().location());
        SprinklerHeadSupport.ARMED.remove(ExtensionShared.remote().key(event.block().location()));
    }
}

