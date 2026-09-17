package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class HorseMeta extends AbstractHorseMeta {

    private static final MetaAccessor<Integer> VARIANT = new MetaAccessor<>(19, MetaType.INT, 0);

    public HorseMeta setVariant(int variant) {
        set(VARIANT, variant);
        return this;
    }

    public int getVariant() {
        return get(VARIANT);
    }
}
