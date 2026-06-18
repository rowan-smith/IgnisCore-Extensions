package dev.rono.igniscore.block.paintmixer;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockBreakEvent;
import dev.rono.igniscore.api.event.OnBlockBreakListener;
import dev.rono.igniscore.block.paintmixer.PaintMixerSupport;

final class PaintMixerOnBlockBreakListener implements OnBlockBreakListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        ExtensionShared.ticks().stop(event.block().location());
    }
}

