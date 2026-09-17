package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class BoggedMeta extends AbstractSkeletonMeta {

    private static final MetaAccessor<Boolean> SHEARED = new MetaAccessor<>(16, MetaType.BOOLEAN, false);

    public BoggedMeta setSheared(boolean sheared) {
        set(SHEARED, sheared);
        return this;
    }

    public boolean isSheared() {
        return get(SHEARED);
    }
}
