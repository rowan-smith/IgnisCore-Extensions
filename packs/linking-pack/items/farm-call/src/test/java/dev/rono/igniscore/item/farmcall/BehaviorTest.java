package dev.rono.igniscore.item.farmcall;

import dev.rono.igniscore.api.model.ItemDefinition;
import dev.rono.igniscore.testsupport.ExtensionTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BehaviorTest {
    @Test
    void definitionLoads() {
        ItemDefinition definition = ExtensionTestSupport.loadItemDefinition(
                BehaviorTest.class, "farm-call", 20001);
        assertNotNull(definition);
    }
}
