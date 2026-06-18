package dev.rono.igniscore.block.claymoremine;

import dev.rono.igniscore.api.event.BlockPlaceEvent;
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

class BehaviorTest {
    @Test
    void triggerProducesBlast() {
        TestEventBus.TestContext ctx = TestEventBus.createContext();
        BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                BehaviorTest.class, "claymore-mine", 10001);
        Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), "claymore-mine");
        RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);

        ctx.eventBus().fireBlockTrigger(new BlockTriggerEvent(instance, null), "claymore-mine");

        assertFalse(ctx.world().explosions().isEmpty());
    }
}
