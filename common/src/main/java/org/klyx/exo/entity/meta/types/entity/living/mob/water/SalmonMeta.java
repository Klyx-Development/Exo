package org.klyx.exo.entity.meta.types.entity.living.mob.water;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class SalmonMeta extends AbstractFishMeta {

    private static final MetaAccessor<Integer> SIZE = new MetaAccessor<>(17, MetaType.INT, 0);

    public SalmonMeta setSize(int size) {
        set(SIZE, size);
        return this;
    }

    public int getSize() {
        return get(SIZE);
    }
}
