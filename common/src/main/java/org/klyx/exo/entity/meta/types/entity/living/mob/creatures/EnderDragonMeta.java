package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class EnderDragonMeta extends CreatureMeta {

    private static final MetaAccessor<Integer> PHASE = new MetaAccessor<>(16, MetaType.INT, 10);

    public EnderDragonMeta setPhase(int phase) {
        set(PHASE, phase);
        return this;
    }

    public int getPhase() {
        return get(PHASE);
    }
}
