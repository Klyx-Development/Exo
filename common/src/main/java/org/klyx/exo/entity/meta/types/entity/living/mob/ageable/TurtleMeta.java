package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class TurtleMeta extends AnimalMeta {

    private static final MetaAccessor<Boolean> HAS_EGG = new MetaAccessor<>(18, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> LAYING_EGG = new MetaAccessor<>(19, MetaType.BOOLEAN, false);

    public TurtleMeta setHasEgg(boolean hasEgg) {
        set(HAS_EGG, hasEgg);
        return this;
    }

    public boolean hasEgg() {
        return get(HAS_EGG);
    }

    public TurtleMeta setLayingEgg(boolean layingEgg) {
        set(LAYING_EGG, layingEgg);
        return this;
    }

    public boolean isLayingEgg() {
        return get(LAYING_EGG);
    }
}
