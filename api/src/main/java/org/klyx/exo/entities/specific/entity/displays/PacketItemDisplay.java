package org.klyx.exo.entities.specific.entity.displays;

import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.klyx.exo.data.keys.DataKeys;

public class PacketItemDisplay extends AbstractPacketDisplay {
    public PacketItemDisplay() {
        super(EntityType.ITEM_DISPLAY);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.ItemDisplay.ITEM_STACK);
        setMetadata(DataKeys.ItemDisplay.DISPLAY_TYPE);
    }

    public void setItemStack(ItemStack stack) {
        setMetadata(DataKeys.ItemDisplay.ITEM_STACK, CraftItemStack.unwrap(stack));
    }
}
