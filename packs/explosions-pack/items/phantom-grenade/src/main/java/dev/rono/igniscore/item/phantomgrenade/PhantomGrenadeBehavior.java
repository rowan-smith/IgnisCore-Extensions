package dev.rono.igniscore.item.phantomgrenade;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.ItemDefinition;
import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;

final class PhantomGrenadeBehavior {
    private final IgnisStrategyContext context;

    PhantomGrenadeBehavior(IgnisStrategyContext context) {
        this.context = context;
    }

    void onItemUse(IgnisPlayer player, ItemDefinition definition, IgnisItem item) {
        ExtensionShared.throwable().throwProjectile(context, player, definition, item, (world, impact) -> {
            float fakePower = (float) StrategySupport.customDouble(definition.getCustomData(), "fakePower", 6.0);
            int delay = StrategySupport.customInt(definition.getCustomData(), "realDelayTicks", 40);
            ExtensionShared.preview().forNearbyPlayers(world, impact, 32, p ->
                    ExtensionShared.preview().fakeExplosion(context.effects(), world, impact, fakePower, world.getPlayersNear(impact, 32)));
            world.spawnParticle(impact, "EXPLOSION", 2, 0.2, 0.2, 0.2, 0.01);
            context.scheduler().runLater(impact, () -> {
                world.playSound(impact, "ENTITY_GENERIC_EXPLODE", 1.0f, 1.0f);
                ExtensionShared.explosion().create(world, impact, definition, 3.5, false);
            }, delay);
        });
    }
}
