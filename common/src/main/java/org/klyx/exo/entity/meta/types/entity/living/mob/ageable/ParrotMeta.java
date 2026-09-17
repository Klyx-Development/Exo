package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class ParrotMeta extends TameableAnimalMeta {

    private static final MetaAccessor<Integer> VARIANT = new MetaAccessor<>(20, MetaType.INT, 0);

    public ParrotMeta setVariant(int variant) {
        set(VARIANT, variant);
        return this;
    }

    public int getVariant() {
        return get(VARIANT);
    }
}
