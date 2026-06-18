package dev.rono.igniscore.item.gateclicker;

import dev.rono.igniscore.api.model.ItemDefinition;
import dev.rono.igniscore.testsupport.ExtensionTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BehaviorTest {
    @Test
    void definitionLoads() {
        ItemDefinition definition = ExtensionTestSupport.loadItemDefinition(
                BehaviorTest.class, "gate-clicker", 20001);
        assertNotNull(definition);
    }
}
