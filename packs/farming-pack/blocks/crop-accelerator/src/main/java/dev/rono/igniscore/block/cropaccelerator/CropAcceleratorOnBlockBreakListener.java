package dev.rono.igniscore.block.cropaccelerator;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockBreakEvent;
import dev.rono.igniscore.api.event.OnBlockBreakListener;
import dev.rono.igniscore.block.cropaccelerator.CropAcceleratorSupport;

final class CropAcceleratorOnBlockBreakListener implements OnBlockBreakListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        ExtensionShared.ticks().stop(event.block().location());
    }
}

