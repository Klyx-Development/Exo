package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.entity.living.mob.PathfinderMobMeta;

public abstract class AgeableMobMeta extends PathfinderMobMeta {

    private static final MetaAccessor<Boolean> BABY = new MetaAccessor<>(16, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> AGE_LOCKED = new MetaAccessor<>(17, MetaType.BOOLEAN, false);

    public AgeableMobMeta setBaby(boolean baby) {
        set(BABY, baby);
        return this;
    }

    public boolean isBaby() {
        return get(BABY);
    }

    public AgeableMobMeta setAgeLocked(boolean ageLocked) {
        set(AGE_LOCKED, ageLocked);
        return this;
    }

    public boolean isAgeLocked() {
        return get(AGE_LOCKED);
    }
}
