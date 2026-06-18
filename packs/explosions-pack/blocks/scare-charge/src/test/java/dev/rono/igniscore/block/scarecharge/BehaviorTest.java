package dev.rono.igniscore.block.scarecharge;

import dev.rono.igniscore.api.config.BlockBehaviorConfig;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.testsupport.ExtensionTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class BehaviorTest {
    @Test
    void isNotCombustible() {
        BlockDefinition definition = ExtensionTestSupport.loadBlockDefinition(
                BehaviorTest.class, "scare-charge", 10001);
        assertFalse(BlockBehaviorConfig.from(definition.getBehaviorConfig()).combustible());
    }
}
