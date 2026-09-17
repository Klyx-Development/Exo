package org.klyx.exo.entity.meta.types.entity.living.mob;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class AllayMeta extends MobMeta {

    private static final MetaAccessor<Boolean> IS_DANCING = new MetaAccessor<>(16, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> CAN_DUPLICATE = new MetaAccessor<>(17, MetaType.BOOLEAN, true);

    public AllayMeta setDancing(boolean dancing) {
        set(IS_DANCING, dancing);
        return this;
    }

    public boolean isDancing() {
        return get(IS_DANCING);
    }

    public AllayMeta setCanDuplicate(boolean canDuplicate) {
        set(CAN_DUPLICATE, canDuplicate);
        return this;
    }

    public boolean canDuplicate() {
        return get(CAN_DUPLICATE);
    }
}
