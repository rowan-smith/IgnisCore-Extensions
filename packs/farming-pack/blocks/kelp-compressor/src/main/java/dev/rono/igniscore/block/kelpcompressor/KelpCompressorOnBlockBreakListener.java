package dev.rono.igniscore.block.kelpcompressor;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockBreakEvent;
import dev.rono.igniscore.api.event.OnBlockBreakListener;
import dev.rono.igniscore.block.kelpcompressor.KelpCompressorSupport;

final class KelpCompressorOnBlockBreakListener implements OnBlockBreakListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        ExtensionShared.ticks().stop(event.block().location());
    }
}

