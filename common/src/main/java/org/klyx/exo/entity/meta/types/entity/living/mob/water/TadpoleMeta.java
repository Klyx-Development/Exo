package org.klyx.exo.entity.meta.types.entity.living.mob.water;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class TadpoleMeta extends AbstractFishMeta {

    private static final MetaAccessor<Boolean> AGE_LOCKED = new MetaAccessor<>(17, MetaType.BOOLEAN, false);

    public TadpoleMeta setAgeLocked(boolean ageLocked) {
        set(AGE_LOCKED, ageLocked);
        return this;
    }

    public boolean isAgeLocked() {
        return get(AGE_LOCKED);
    }
}
