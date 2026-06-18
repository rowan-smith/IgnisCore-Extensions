package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisInventory;
import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.strategy.ExtensionSupport;

/**
 * Public API for processing helpers.
 */
public final class ProcessingApi {
    public static final ProcessingApi INSTANCE = new ProcessingApi();

    private ProcessingApi() {
    }

    public boolean matches(IgnisItem item, String... materials) {
        return ProcessingGuiSupport.matches(item, materials);
    }
    public void consumeOne(IgnisInventory inventory, int slot) {
        ProcessingGuiSupport.consumeOne(inventory, slot);
    }
    public void setOutput(ExtensionSupport extensionSupport, IgnisInventory inventory, int slot, String material, int amount) {
        ProcessingGuiSupport.setOutput(extensionSupport, inventory, slot, material, amount);
    }
}
