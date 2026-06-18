package dev.rono.igniscore.block.motionfloodlight;

import dev.rono.igniscore.api.event.BlockPlaceEvent;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.model.PlacedBlock;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.testsupport.ExtensionTestSupport;
import dev.rono.igniscore.testsupport.TestEventBus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BehaviorTest {
    @Test
    void placeActivatesStrategy() {
        TestEventBus.TestContext ctx = TestEventBus.createContext();
        BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                BehaviorTest.class, "motion-floodlight", 10001);
        Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), "motion-floodlight");

        assertDoesNotThrow(() -> ctx.eventBus().fireBlockPlace(
                new BlockPlaceEvent(
                        PlacedBlock.of(definition, new IgnisLocation("world", 1, 2, 3)),
                        null),
                "motion-floodlight"));
    }
}
