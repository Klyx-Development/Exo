package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class ZoglinMeta extends CreatureMeta {

    private static final MetaAccessor<Boolean> BABY = new MetaAccessor<>(16, MetaType.BOOLEAN, false);

    public ZoglinMeta setBaby(boolean baby) {
        set(BABY, baby);
        return this;
    }

    public boolean isBaby() {
        return get(BABY);
    }
}
