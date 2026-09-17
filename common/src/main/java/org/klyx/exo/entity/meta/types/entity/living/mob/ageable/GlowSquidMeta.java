package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class GlowSquidMeta extends AgeableWaterCreatureMeta {

    private static final MetaAccessor<Integer> DARK_TICKS_REMAINING = new MetaAccessor<>(18, MetaType.INT, 0);

    public GlowSquidMeta setDarkTicksRemaining(int ticks) {
        set(DARK_TICKS_REMAINING, ticks);
        return this;
    }

    public int getDarkTicksRemaining() {
        return get(DARK_TICKS_REMAINING);
    }
}
