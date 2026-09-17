package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.ArmadilloState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class ArmadilloMeta extends AnimalMeta {

    private static final MetaAccessor<ArmadilloState> STATE = new MetaAccessor<>(18, MetaType.ARMADILLO_STATE, ArmadilloState.IDLE);

    public ArmadilloMeta setState(ArmadilloState state) {
        set(STATE, state);
        return this;
    }

    public ArmadilloState getState() {
        return get(STATE);
    }
}
