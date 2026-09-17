package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class ZombieMeta extends CreatureMeta {

    private static final MetaAccessor<Boolean> BABY =
            new MetaAccessor<>(16, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Integer> SPECIAL_TYPE =
            new MetaAccessor<>(17, MetaType.INT, 0); // unused
    private static final MetaAccessor<Boolean> DROWNED_CONVERSION =
            new MetaAccessor<>(18, MetaType.BOOLEAN, false);

    public ZombieMeta setBaby(boolean baby) {
        set(BABY, baby);
        return this;
    }

    public boolean isBaby() {
        return get(BABY);
    }

    public ZombieMeta setConvertingInWater(boolean converting) {
        set(DROWNED_CONVERSION, converting);
        return this;
    }

    public boolean isConvertingInWater() {
        return get(DROWNED_CONVERSION);
    }
}
