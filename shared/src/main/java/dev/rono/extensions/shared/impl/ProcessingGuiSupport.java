package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisInventory;
import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.strategy.ExtensionSupport;

/**
 * Small helpers for processing-block GUIs.
 */
final class ProcessingGuiSupport {
    private ProcessingGuiSupport() {
    }

    static boolean matches(IgnisItem item, String... materials) {
        if (item == null || item.isAir()) {
            return false;
        }
        String key = item.getMaterialKey().toLowerCase();
        for (String material : materials) {
            if (key.contains(material.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    static void consumeOne(IgnisInventory inventory, int slot) {
        IgnisItem item = inventory.getItem(slot);
        if (item == null || item.isAir()) {
            return;
        }
        item.setAmount(item.getAmount() - 1);
        inventory.setItem(slot, item.getAmount() > 0 ? item : null);
    }

    static void setOutput(ExtensionSupport extensionSupport, IgnisInventory inventory, int slot, String material, int amount) {
        inventory.setItem(slot, extensionSupport.createItem(material, amount));
    }
}
