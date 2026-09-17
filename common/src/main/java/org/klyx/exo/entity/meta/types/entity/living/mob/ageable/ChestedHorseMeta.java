package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class ChestedHorseMeta extends AbstractHorseMeta {

    private static final MetaAccessor<Boolean> CHEST = new MetaAccessor<>(19, MetaType.BOOLEAN, false);

    public ChestedHorseMeta setChest(boolean chest) {
        set(CHEST, chest);
        return this;
    }

    public boolean hasChest() {
        return get(CHEST);
    }
}
