package dev.rono.igniscore.block.lightningrodtnt;

import dev.rono.extensions.shared.api.theatrics.CombustibleFuseTheatricsListener;
import dev.rono.igniscore.api.event.BlockActivateEvent;
import dev.rono.igniscore.api.event.BlockPlaceEvent;
import dev.rono.igniscore.api.event.BlockTickEvent;
import dev.rono.igniscore.api.event.BlockTriggerEvent;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.model.PlacedBlock;
import dev.rono.igniscore.api.model.RuntimeBlockInstance;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.testsupport.BehaviorTestSupport;
import dev.rono.igniscore.testsupport.ExtensionTestSupport;
import dev.rono.igniscore.testsupport.TestEventBus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BehaviorTest {
    private static final String EXTENSION_ID = "lightning-rod-tnt";

    @Test
    void placedAnnouncesItself() {
        TestEventBus.TestContext ctx = TestEventBus.createContext();
        BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                BehaviorTest.class, EXTENSION_ID, 10001);
        Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), EXTENSION_ID);

        ctx.eventBus().fireBlockPlace(
                new BlockPlaceEvent(
                        PlacedBlock.of(definition, new IgnisLocation("world", 1, 2, 3)),
                        null),
                EXTENSION_ID);

        assertFalse(ctx.world().sounds().isEmpty() && ctx.world().particles().isEmpty());
    }

    @Test
    void igniteFlaresOnActivation() {
        TestEventBus.TestContext ctx = TestEventBus.createContext();
        BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                BehaviorTest.class, EXTENSION_ID, 10001);
        Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), EXTENSION_ID);
        RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);

        ctx.eventBus().fireBlockActivate(new BlockActivateEvent(instance), EXTENSION_ID);

        assertFalse(ctx.world().particles().isEmpty());
    }

    @Test
    void fuseCountdownPulses() {
        TestEventBus.TestContext ctx = TestEventBus.createContext();
        BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                BehaviorTest.class, EXTENSION_ID, 10001);
        Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), EXTENSION_ID);
        RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);
        instance.setTicksLeft(40);

        new CombustibleFuseTheatricsListener(ctx.context())
                .onBlockTick(new BlockTickEvent(instance));

        assertFalse(ctx.world().particles().isEmpty());
    }

    @Test
    void triggerDetonates() {
        TestEventBus.TestContext ctx = TestEventBus.createContext();
        BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                BehaviorTest.class, EXTENSION_ID, 10001);
        Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), EXTENSION_ID);
        RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);

        ctx.eventBus().fireBlockTrigger(new BlockTriggerEvent(instance, null), EXTENSION_ID);

        assertFalse(ctx.world().explosions().isEmpty());
    }
}
