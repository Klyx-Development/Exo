package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class HoglinMeta extends AnimalMeta {

    private static final MetaAccessor<Boolean> IMMUNE_TO_ZOMBIFICATION = new MetaAccessor<>(18, MetaType.BOOLEAN, false);

    public HoglinMeta setImmuneToZombification(boolean immune) {
        set(IMMUNE_TO_ZOMBIFICATION, immune);
        return this;
    }

    public boolean isImmuneToZombification() {
        return get(IMMUNE_TO_ZOMBIFICATION);
    }
}
