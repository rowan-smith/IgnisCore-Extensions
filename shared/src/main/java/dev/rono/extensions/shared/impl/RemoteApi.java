package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.util.Locations;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * Public API for remote helpers.
 */
public final class RemoteApi {
    public static final RemoteApi INSTANCE = new RemoteApi();

    private RemoteApi() {
    }

    public String key(IgnisLocation location) {
        return LinkedBlockRegistry.key(location);
    }
    public void register(IgnisLocation location, BiConsumer<IgnisPlayer, String> remoteAction) {
        LinkedBlockRegistry.register(location, remoteAction);
    }
    public void unregister(IgnisLocation location) {
        LinkedBlockRegistry.unregister(location);
    }
    public boolean activate(IgnisLocation location, IgnisPlayer player, String action) {
        return LinkedBlockRegistry.activate(location, player, action);
    }
}
