package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class PolarBearMeta extends AnimalMeta {

    private static final MetaAccessor<Boolean> STANDING = new MetaAccessor<>(18, MetaType.BOOLEAN, false);

    public PolarBearMeta setStanding(boolean standing) {
        set(STANDING, standing);
        return this;
    }

    public boolean isStanding() {
        return get(STANDING);
    }
}
