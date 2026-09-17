package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class AbstractCubeMobMeta extends AgeableMobMeta {

    private static final MetaAccessor<Integer> SIZE = new MetaAccessor<>(18, MetaType.INT, 1);

    public AbstractCubeMobMeta setSize(int size) {
        set(SIZE, size);
        return this;
    }

    public int getSize() {
        return get(SIZE);
    }
}
