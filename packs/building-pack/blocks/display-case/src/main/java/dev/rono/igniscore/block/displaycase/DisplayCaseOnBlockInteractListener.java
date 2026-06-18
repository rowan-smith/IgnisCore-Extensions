package dev.rono.igniscore.block.displaycase;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.CustomBlockAction;
import dev.rono.igniscore.api.event.BlockInteractEvent;
import dev.rono.igniscore.api.event.OnBlockInteractListener;
import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.util.Locations;

final class DisplayCaseOnBlockInteractListener implements OnBlockInteractListener {
    private final DisplayCaseRuntime runtime;

    DisplayCaseOnBlockInteractListener(DisplayCaseRuntime runtime) {
        this.runtime = runtime;
    }

    @Override
    public void onBlockInteract(BlockInteractEvent event) {
        if (event.action() != CustomBlockAction.OPEN) {
            return;
        }
        runtime.registry.openBlock(event.player(), event.block().location());
        var inventory = runtime.registry.inventoryAt(event.block().location());
        if (inventory == null) {
            return;
        }
        IgnisItem display = inventory.getItem(DisplayCaseSupport.DISPLAY_SLOT);
        if (display != null && !display.isAir()) {
            event.player().sendMessage("<gray>Museum exhibit: <white>" + display.getAmount() + "x "
                    + display.getMaterialKey() + "</white></gray>");
            IgnisWorld world = DisplayCaseSupport.worldAt(runtime, event.block().location());
            ExtensionShared.theatrics().sparkle(world, Locations.toCenter(event.block().location()), "END_ROD", 6);
        }
    }
}

