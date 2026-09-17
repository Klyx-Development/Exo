package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class PhantomMeta extends CreatureMeta {

    private static final MetaAccessor<Integer> SIZE = new MetaAccessor<>(16, MetaType.INT, 0);

    public PhantomMeta setSize(int size) {
        set(SIZE, size);
        return this;
    }

    public int getSize() {
        return get(SIZE);
    }
}
