package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class HappyGhastMeta extends AnimalMeta {

    private static final MetaAccessor<Boolean> IS_LEASH_HOLDER = new MetaAccessor<>(18, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> STAYS_STILL = new MetaAccessor<>(19, MetaType.BOOLEAN, false);

    public HappyGhastMeta setLeashHolder(boolean leashHolder) {
        set(IS_LEASH_HOLDER, leashHolder);
        return this;
    }

    public boolean isLeashHolder() {
        return get(IS_LEASH_HOLDER);
    }

    public HappyGhastMeta setStaysStill(boolean staysStill) {
        set(STAYS_STILL, staysStill);
        return this;
    }

    public boolean staysStill() {
        return get(STAYS_STILL);
    }
}
