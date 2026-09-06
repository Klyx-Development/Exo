package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;

import java.util.OptionalInt;

public class FireworkRocketMeta extends EntityMeta {

    private static final MetaAccessor<ExoItemStack> ITEM_STACK = new MetaAccessor<>(8, MetaType.ITEM_STACK, ExoItemStack.EMPTY);
    private static final MetaAccessor<OptionalInt> ATTACHED_TO_TARGET = new MetaAccessor<>(9, MetaType.OPTIONAL_UNSIGNED_INT, OptionalInt.empty());
    private static final MetaAccessor<Boolean> SHOT_AT_ANGLE = new MetaAccessor<>(10, MetaType.BOOLEAN, false);

    public FireworkRocketMeta setItem(ExoItemStack itemStack) {
        set(ITEM_STACK, itemStack);
        return this;
    }

    public ExoItemStack getItem() {
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
