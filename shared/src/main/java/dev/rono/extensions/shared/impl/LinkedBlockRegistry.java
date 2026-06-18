package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.util.Locations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * Remote activation registry for linked item → block pairs.
 */
final class LinkedBlockRegistry {
    private static final Map<String, BiConsumer<IgnisPlayer, String>> REMOTE = new ConcurrentHashMap<>();

    private LinkedBlockRegistry() {
    }

    static String key(IgnisLocation location) {
        IgnisLocation block = Locations.toBlock(location);
        return block.worldName() + ":" + (int) block.x() + ":" + (int) block.y() + ":" + (int) block.z();
    }

    static void register(IgnisLocation location, BiConsumer<IgnisPlayer, String> remoteAction) {
        REMOTE.put(key(location), remoteAction);
    }

    static void unregister(IgnisLocation location) {
        REMOTE.remove(key(location));
    }

    static boolean activate(IgnisLocation location, IgnisPlayer player, String action) {
        BiConsumer<IgnisPlayer, String> handler = REMOTE.get(key(location));
        if (handler == null) {
            return false;
        }
        handler.accept(player, action);
        return true;
    }
}
