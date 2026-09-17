package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class WitchMeta extends RaiderMeta {

    private static final MetaAccessor<Boolean> USING_ITEM = new MetaAccessor<>(17, MetaType.BOOLEAN, false);

    public WitchMeta setUsingItem(boolean usingItem) {
        set(USING_ITEM, usingItem);
        return this;
    }

    public boolean isUsingItem() {
        return get(USING_ITEM);
    }
}
