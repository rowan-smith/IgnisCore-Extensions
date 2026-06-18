package dev.rono.extensions.shared.api.theatrics;

import dev.rono.igniscore.api.event.BlockTickEvent;
import dev.rono.igniscore.api.model.BlockDefinition;
import dev.rono.igniscore.api.model.RuntimeBlockInstance;
import dev.rono.igniscore.testsupport.BehaviorTestSupport;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CombustibleFuseTheatricsListenerTest {
    @Test
    void fusePulseWhenCountingDown() {
        BehaviorTestSupport.TestContext ctx = BehaviorTestSupport.createContext();
        BlockDefinition definition = blockDefinition(Map.of("fuse", 80));
        RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);
        instance.setTicksLeft(40);

        new CombustibleFuseTheatricsListener(ctx.context())
                .onBlockTick(new BlockTickEvent(instance));

        assertFalse(ctx.world().particles().isEmpty());
    }

    @Test
    void silentBeforeFuseStarts() {
        BehaviorTestSupport.TestContext ctx = BehaviorTestSupport.createContext();
        BlockDefinition definition = blockDefinition(Map.of("fuse", 80));
        RuntimeBlockInstance instance = BehaviorTestSupport.blockInstance(definition);
        instance.setTicksLeft(80);

        new CombustibleFuseTheatricsListener(ctx.context())
                .onBlockTick(new BlockTickEvent(instance));

        assertTrue(ctx.world().particles().isEmpty());
    }

    private static BlockDefinition blockDefinition(Map<String, Object> customData) {
        return new BlockDefinition(
                "test-fuse",
                "paper",
                "carrot_on_a_stick",
                Component.text("Test"),
                List.of(),
                true,
                true,
                "top.png",
                "side.png",
                "bottom.png",
                customData,
                Map.of(),
                Map.of(),
                Map.of(),
                10001,
                false,
                false,
                false
        );
    }
}
