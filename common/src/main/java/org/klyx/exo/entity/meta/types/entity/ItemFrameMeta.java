package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.meta.Direction;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class ItemFrameMeta extends EntityMeta {

    private static final MetaAccessor<Direction> DIRECTION = new MetaAccessor<>(8, MetaType.DIRECTION, Direction.SOUTH);
    private static final MetaAccessor<ExoItemStack> ITEM_STACK = new MetaAccessor<>(9, MetaType.ITEM_STACK, ExoItemStack.EMPTY);
    private static final MetaAccessor<Integer> ROTATION = new MetaAccessor<>(10, MetaType.INT, 0);

    public ItemFrameMeta setDirection(Direction direction) {
        set(DIRECTION, direction);
        return this;
    }

    public Direction getDirection() {
        return get(DIRECTION);
    }

    public ItemFrameMeta setItem(ExoItemStack itemStack) {
        set(ITEM_STACK, itemStack);
        return this;
    }

    public ExoItemStack getItem() {
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
