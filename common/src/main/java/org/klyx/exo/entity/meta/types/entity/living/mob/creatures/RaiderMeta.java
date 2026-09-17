package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class RaiderMeta extends CreatureMeta {

    private static final MetaAccessor<Boolean> IS_CELEBRATING = new MetaAccessor<>(16, MetaType.BOOLEAN, false);

    public RaiderMeta setCelebrating(boolean celebrating) {
        set(IS_CELEBRATING, celebrating);
        return this;
    }

    public boolean isCelebrating() {
        return get(IS_CELEBRATING);
    }
}
