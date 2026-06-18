package dev.rono.igniscore.block.fortunecookiemaker;

import dev.rono.igniscore.api.CustomBlockAction;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.strategy.PlacedClickSupport;
import dev.rono.igniscore.api.port.IgnisInteraction;
import dev.rono.igniscore.testsupport.ExtensionTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BehaviorTest {
    @Test
    void rightClickOpensGui() {
        BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                BehaviorTest.class, "fortune-cookie-maker", 10001);
        assertEquals(CustomBlockAction.OPEN,
                PlacedClickSupport.resolve(definition, CustomBlockAction.BREAK, CustomBlockAction.OPEN,
                        IgnisInteraction.RIGHT_CLICK_BLOCK, "AIR"));
    }
}
