package dev.rono.igniscore.block.screenshakecharge;

import dev.rono.extensions.shared.ExtensionShared;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.model.RuntimeBlockInstance;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;
import dev.rono.igniscore.api.util.Locations;

final class ScreenShakeChargeBehavior {
    private final IgnisStrategyContext context;

    ScreenShakeChargeBehavior(IgnisStrategyContext context) {
        this.context = context;
    }

    void onTrigger(RuntimeBlockInstance instance, Object triggerContext) {
        BlockDefinition def = instance.getDefinition();
        IgnisLocation loc = Locations.toCenter(instance.getLocation());
        IgnisWorld world = worldAt(loc);
        double shakeRadius = StrategySupport.customDouble(def, "shakeRadius", 24.0);
        int duration = StrategySupport.customInt(def, "shakeDuration", 20);
        float power = (float) StrategySupport.customDouble(def, "realPower", 1.5);
        ExtensionShared.explosion().create(world, loc, power, false, false);
        ExtensionShared.blasts().violentScreenShake(context.effects(), context.protocol(),
                world, loc, shakeRadius, duration, context.scheduler());
    }

    private IgnisWorld worldAt(IgnisLocation location) {
        return context.extensions().resolveWorld(location);
    }
}
