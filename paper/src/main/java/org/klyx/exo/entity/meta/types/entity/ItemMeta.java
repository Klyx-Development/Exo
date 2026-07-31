package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class ItemMeta extends EntityMeta {

    private static final MetaAccessor<ItemStack> ITEM_STACK = new MetaAccessor<>(8, EntityDataSerializers.ITEM_STACK, ItemStack.EMPTY);

    public ItemMeta setItem(ItemStack itemStack) {
        set(ITEM_STACK, itemStack);
        return this;
    }

    public ItemMeta setItem(org.bukkit.inventory.ItemStack itemStack) {
        set(ITEM_STACK, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    public ItemStack getItem() {
        return get(ITEM_STACK);
    }

}
