package dev.rono.igniscore.block.pipevalve;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockBreakEvent;
import dev.rono.igniscore.api.event.OnBlockBreakListener;

final class PipeValveOnBlockBreakListener implements OnBlockBreakListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        ExtensionShared.ticks().stop(event.block().location());
        ExtensionShared.remote().unregister(event.block().location());
        PipeValveSupport.OPEN.remove(ExtensionShared.remote().key(event.block().location()));
    }
}

