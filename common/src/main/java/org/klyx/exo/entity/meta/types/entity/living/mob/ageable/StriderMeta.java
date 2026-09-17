package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class StriderMeta extends AnimalMeta {

    private static final MetaAccessor<Integer> BOOST_TIME = new MetaAccessor<>(18, MetaType.INT, 0);
    private static final MetaAccessor<Boolean> SUFFOCATING = new MetaAccessor<>(19, MetaType.BOOLEAN, false);

    public StriderMeta setBoostTime(int ticks) {
        set(BOOST_TIME, ticks);
        return this;
    }

    public int getBoostTime() {
        return get(BOOST_TIME);
    }

    public StriderMeta setSuffocating(boolean suffocating) {
        set(SUFFOCATING, suffocating);
        return this;
    }

    public boolean isSuffocating() {
        return get(SUFFOCATING);
    }
}
