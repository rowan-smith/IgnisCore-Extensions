package dev.rono.igniscore.block.socketlamp;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.event.BlockBreakEvent;
import dev.rono.igniscore.api.event.OnBlockBreakListener;

final class SocketLampOnBlockBreakListener implements OnBlockBreakListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        ExtensionShared.ticks().stop(event.block().location());
        ExtensionShared.remote().unregister(event.block().location());
        SocketLampSupport.LIGHT_LEVEL.remove(ExtensionShared.remote().key(event.block().location()));
    }
}

