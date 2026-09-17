package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class MushroomCowMeta extends AbstractCowMeta {

    private static final MetaAccessor<Integer> TYPE = new MetaAccessor<>(18, MetaType.INT, 0);

    public MushroomCowMeta setType(int type) {
        set(TYPE, type);
        return this;
    }

    public int getType() {
        return get(TYPE);
    }
}
