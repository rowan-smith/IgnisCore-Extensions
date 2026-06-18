package dev.rono.igniscore.block.fortunecookiemaker;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.CustomBlockAction;
import dev.rono.igniscore.api.event.BlockInteractEvent;
import dev.rono.igniscore.api.event.OnBlockInteractListener;

final class FortuneCookieMakerOnBlockInteractListener implements OnBlockInteractListener {
    private final FortuneCookieMakerRuntime runtime;

    FortuneCookieMakerOnBlockInteractListener(FortuneCookieMakerRuntime runtime) {
        this.runtime = runtime;
    }

    @Override
    public void onBlockInteract(BlockInteractEvent event) {
        if (event.action() != CustomBlockAction.OPEN) {
            return;
        }
        if (event.heldItem() != null && !event.heldItem().isAir() && ExtensionShared.processing().matches(event.heldItem(), "cookie")) {
            String fortune = FortuneCookieMakerSupport.FORTUNES[(int) (Math.random() * FortuneCookieMakerSupport.FORTUNES.length)];
            event.player().sendMessage("<gold>Fortune:</gold> <italic>" + fortune + "</italic>");
            ExtensionShared.theatrics().sparkle(FortuneCookieMakerSupport.worldAt(runtime, event.block().location()), event.player().getLocation(), "NOTE", 6);
        }
        runtime.registry.openBlock(event.player(), event.block().location());
    }
}

