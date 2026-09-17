package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class GhastMeta extends CreatureMeta {

    private static final MetaAccessor<Boolean> IS_ATTACKING = new MetaAccessor<>(16, MetaType.BOOLEAN, false);

    public GhastMeta setAttacking(boolean attacking) {
        set(IS_ATTACKING, attacking);
        return this;
    }

    public boolean isAttacking() {
        return get(IS_ATTACKING);
    }
}
