package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class ItemFrameMeta extends EntityMeta {

    private static final MetaAccessor<Direction> DIRECTION = new MetaAccessor<>(8, EntityDataSerializers.DIRECTION, Direction.SOUTH);
    private static final MetaAccessor<ItemStack> ITEM_STACK = new MetaAccessor<>(9, EntityDataSerializers.ITEM_STACK, ItemStack.EMPTY);
    private static final MetaAccessor<Integer> ROTATION = new MetaAccessor<>(10, EntityDataSerializers.INT, 0);

    public ItemFrameMeta setDirection(Direction direction) {
        set(DIRECTION, direction);
        return this;
    }

    public Direction getDirection() {
        return get(DIRECTION);
    }

    public ItemFrameMeta setItem(ItemStack itemStack) {
        set(ITEM_STACK, itemStack);
        return this;
    }

    public ItemFrameMeta setItem(org.bukkit.inventory.ItemStack itemStack) {
        set(ITEM_STACK, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    public ItemStack getItem() {
        return get(ITEM_STACK);
    }

    public ItemFrameMeta setRotation(int rotation) {
        set(ROTATION, rotation);
        return this;
    }

    public int getRotation() {
        return get(ROTATION);
    }


}
