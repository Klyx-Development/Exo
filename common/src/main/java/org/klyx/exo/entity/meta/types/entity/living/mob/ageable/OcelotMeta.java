package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class OcelotMeta extends AnimalMeta {

    private static final MetaAccessor<Boolean> TRUSTING = new MetaAccessor<>(18, MetaType.BOOLEAN, false);

    public OcelotMeta setTrusting(boolean trusting) {
        set(TRUSTING, trusting);
        return this;
    }

    public boolean isTrusting() {
        return get(TRUSTING);
    }
}
