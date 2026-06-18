package dev.rono.extensions.shared.impl;

import dev.rono.igniscore.api.model.ItemDefinition;
import dev.rono.igniscore.api.port.IgnisBlock;
import dev.rono.igniscore.api.port.IgnisItem;
import dev.rono.igniscore.api.port.IgnisLocation;
import dev.rono.igniscore.api.port.IgnisPlayer;
import dev.rono.igniscore.api.port.IgnisWorld;
import dev.rono.igniscore.api.service.IgnisNbtService;
import dev.rono.igniscore.api.strategy.IgnisStrategyContext;
import dev.rono.igniscore.api.strategy.StrategySupport;
import dev.rono.igniscore.api.util.Locations;

/**
 * Public API for link helpers.
 */
public final class LinkApi {
    public static final LinkApi INSTANCE = new LinkApi();

    private LinkApi() {
    }

    public void itemOnUse(IgnisStrategyContext context,
                             IgnisPlayer player,
                             ItemDefinition definition,
                             IgnisItem item,
                             IgnisBlock clickedBlock,
                             String expectedBlockType,
                             String remoteAction,
                             double linkRange) {
        LinkItemSupport.onUse(context, player, definition, item, clickedBlock, expectedBlockType, remoteAction, linkRange);
    }
    public void store(IgnisNbtService nbt, IgnisItem item, String blockTypeId, IgnisLocation location) {
        BlockLinkSupport.link(nbt, item, blockTypeId, location);
    }
    public boolean hasLink(IgnisNbtService nbt, IgnisItem item) {
        return BlockLinkSupport.hasLink(nbt, item);
    }
    public IgnisLocation readLocation(IgnisNbtService nbt, IgnisItem item) {
        return BlockLinkSupport.readLocation(nbt, item);
    }
    public String readType(IgnisNbtService nbt, IgnisItem item) {
        return BlockLinkSupport.readType(nbt, item);
    }
    public boolean withinRange(IgnisPlayer player, IgnisLocation target, double maxRange) {
        return BlockLinkSupport.withinRange(player, target, maxRange);
    }
    public void clear(IgnisNbtService nbt, IgnisItem item) {
        BlockLinkSupport.clear(nbt, item);
    }
}
