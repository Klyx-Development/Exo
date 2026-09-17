package org.klyx.exo.entity.meta.types.entity.living.mob.water;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class TropicalFishMeta extends AbstractFishMeta {

    private static final MetaAccessor<Integer> TYPE_VARIANT = new MetaAccessor<>(17, MetaType.INT, 0);

    public TropicalFishMeta setTypeVariant(int typeVariant) {
        set(TYPE_VARIANT, typeVariant);
        return this;
    }

    public int getTypeVariant() {
        return get(TYPE_VARIANT);
    }
}
