package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

import java.util.OptionalInt;

public class FireworkRocketMeta extends EntityMeta {

    private static final MetaAccessor<ItemStack> ITEM_STACK = new MetaAccessor<>(8, EntityDataSerializers.ITEM_STACK, ItemStack.EMPTY);
    private static final MetaAccessor<OptionalInt> ATTACHED_TO_TARGET = new MetaAccessor<>(9, EntityDataSerializers.OPTIONAL_UNSIGNED_INT, OptionalInt.empty());
    private static final MetaAccessor<Boolean> SHOT_AT_ANGLE = new MetaAccessor<>(10, EntityDataSerializers.BOOLEAN, false);

    public FireworkRocketMeta setItem(ItemStack itemStack) {
        set(ITEM_STACK, itemStack);
        return this;
    }

    public FireworkRocketMeta setItem(org.bukkit.inventory.ItemStack itemStack) {
        set(ITEM_STACK, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    public ItemStack getItem() {
        return get(ITEM_STACK);
    }

    public FireworkRocketMeta setAttachedToTarget(int target) {
        set(ATTACHED_TO_TARGET, OptionalInt.of(target));
        return this;
    }

    public OptionalInt getAttachedToTarget() {
        return get(ATTACHED_TO_TARGET);
    }

    public FireworkRocketMeta setShotAtAngle(boolean shotAtAngle) {
        set(SHOT_AT_ANGLE, shotAtAngle);
        return this;
    }

    public boolean isShotAtAngle() {
        return get(SHOT_AT_ANGLE);
    }

}
