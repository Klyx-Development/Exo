package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class InteractionMeta extends EntityMeta {

    private static final MetaAccessor<Float> WIDTH = new MetaAccessor<>(8, MetaType.FLOAT, 1.0F);
    private static final MetaAccessor<Float> HEIGHT = new MetaAccessor<>(9, MetaType.FLOAT, 1.0F);
    private static final MetaAccessor<Boolean> CAN_BE_ATTACKED = new MetaAccessor<>(10, MetaType.BOOLEAN, false);

    public InteractionMeta setWidth(float width) {
        set(WIDTH, width);
        return this;
    }

    public float getWidth() {
        return get(WIDTH);
    }

    public InteractionMeta setHeight(float height) {
        set(HEIGHT, height);
        return this;
    }

    public float getHeight() {
        return get(HEIGHT);
    }

    public InteractionMeta setCanBeAttacked(boolean canBeAttacked) {
        set(CAN_BE_ATTACKED, canBeAttacked);
        return this;
    }

    public boolean canBeAttacked() {
        return get(CAN_BE_ATTACKED);
    }

}
