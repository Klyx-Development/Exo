package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class AbstractNautilusMeta extends TameableAnimalMeta {

    private static final MetaAccessor<Boolean> DASH = new MetaAccessor<>(20, MetaType.BOOLEAN, false);

    public AbstractNautilusMeta setDash(boolean dash) {
        set(DASH, dash);
        return this;
    }

    public boolean isDash() {
        return get(DASH);
    }
}
