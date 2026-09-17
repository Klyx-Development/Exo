package org.klyx.exo.entity.meta.types.entity.living.mob.water;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class PufferfishMeta extends AbstractFishMeta {

    private static final MetaAccessor<Integer> PUFF_STATE = new MetaAccessor<>(17, MetaType.INT, 0);

    public PufferfishMeta setPuffState(int puffState) {
        set(PUFF_STATE, puffState);
        return this;
    }

    public int getPuffState() {
        return get(PUFF_STATE);
    }
}
