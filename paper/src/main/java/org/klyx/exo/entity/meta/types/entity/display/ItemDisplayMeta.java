package org.klyx.exo.entity.meta.types.entity.display;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.klyx.exo.entity.meta.impl.MetaAccessor;

public class ItemDisplayMeta extends DisplayMeta {

    private static final MetaAccessor<ItemStack> ITEM_STACK = new MetaAccessor<>(23, EntityDataSerializers.ITEM_STACK, ItemStack.EMPTY);
    private static final MetaAccessor<Byte> DISPLAY_TYPE = new MetaAccessor<>(24, EntityDataSerializers.BYTE, (byte) 0);

    public ItemDisplayMeta setItem(ItemStack itemStack) {
        set(ITEM_STACK, itemStack);
        return this;
    }

    public ItemDisplayMeta setItem(org.bukkit.inventory.ItemStack itemStack) {
        set(ITEM_STACK, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    public ItemStack getItem() {
        return get(ITEM_STACK);
    }

    public ItemDisplayMeta setDisplayType(ItemDisplayContext displayType) {
        setFlag(DISPLAY_TYPE, displayType.getId(), true);
        return this;
    }

    public ItemDisplayContext getDisplayType() {
        return ItemDisplayContext.BY_ID.apply(get(DISPLAY_TYPE));
    }

}
