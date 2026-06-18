        package dev.rono.igniscore.item.farmerstea;

        import dev.rono.igniscore.api.event.ItemClickEvent;
        import dev.rono.igniscore.api.model.ItemDefinition;
        import dev.rono.igniscore.api.port.IgnisInteraction;
        import dev.rono.igniscore.api.port.IgnisItem;
        import dev.rono.igniscore.api.port.IgnisLocation;
        import dev.rono.igniscore.api.port.IgnisPlayer;
        import dev.rono.igniscore.api.port.IgnisWorld;
        import dev.rono.igniscore.testsupport.ExtensionTestSupport;
        import dev.rono.igniscore.testsupport.TestEventBus;
        import org.junit.jupiter.api.Test;

        import java.util.UUID;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertFalse;
        import static org.junit.jupiter.api.Assertions.assertTrue;

        class BehaviorTest {
            @Test
            void useProducesExpectedEffect() {
                TestEventBus.TestContext ctx = TestEventBus.createContext();
                ItemDefinition definition = ExtensionTestSupport.loadItemDefinition(
                        BehaviorTest.class, "farmers-tea", 20001);
                Strategy strategy = TestEventBus.activate(() -> new Strategy(ctx.context()), "farmers-tea");
                TrackingPlayer player = new TrackingPlayer(ctx.world());
                TrackingItem item = new TrackingItem(1);

                ctx.eventBus().fireItemClick(
                        new ItemClickEvent(player, definition, item, IgnisInteraction.RIGHT_CLICK_AIR, null, "use"),
                        "farmers-tea");

                assertEquals(0, item.getAmount());
                assertFalse(player.messages().isEmpty());
                assertFalse(ctx.world().particles().isEmpty() || ctx.world().sounds().isEmpty());
            }

            private static final class TrackingItem implements IgnisItem {
    private int amount;
    private TrackingItem(int amount) { this.amount = amount; }
    @Override public int getAmount() { return amount; }
    @Override public void setAmount(int amount) { this.amount = amount; }
    @Override public String getMaterialKey() { return "paper"; }
    @Override public boolean isAir() { return amount <= 0; }
    @Override public Object nativeItem() { return this; }
}

            private static final class TrackingPlayer implements IgnisPlayer {
    private final IgnisWorld world;
    private final IgnisLocation eye = new IgnisLocation("world", 0, 1.6, 0);
    private final java.util.List<String> messages = new java.util.ArrayList<>();
    private final java.util.List<String> effects = new java.util.ArrayList<>();
    private boolean openedInventory;
    private TrackingPlayer(IgnisWorld world) { this.world = world; }
    java.util.List<String> messages() { return messages; }
    java.util.List<String> effects() { return effects; }
    boolean openedInventory() { return openedInventory; }
    @Override public UUID getUniqueId() { return UUID.randomUUID(); }
    @Override public String getName() { return "tester"; }
    @Override public IgnisLocation getLocation() { return eye; }
    @Override public IgnisLocation getEyeLocation() { return eye.withYawPitch(0f, 0f); }
    @Override public IgnisWorld getWorld() { return world; }
    @Override public void sendMessage(String miniMessage) { messages.add(miniMessage); }
    @Override public void openInventory(Object nativeInventory) { openedInventory = true; }
    @Override public void applyPotionEffect(String effectKey, int durationTicks, int amplifier) {
        effects.add(effectKey);
    }
}

        }
