package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.SnifferState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class SnifferMeta extends AnimalMeta {

    private static final MetaAccessor<SnifferState> STATE = new MetaAccessor<>(18, MetaType.SNIFFER_STATE, SnifferState.IDLING);
    private static final MetaAccessor<Integer> DROP_SEED_AT_TICK = new MetaAccessor<>(19, MetaType.INT, 0);

    public SnifferMeta setState(SnifferState state) {
        set(STATE, state);
        return this;
    }

    public SnifferState getState() {
        return get(STATE);
    }

    public SnifferMeta setDropSeedAtTick(int tick) {
        set(DROP_SEED_AT_TICK, tick);
        return this;
    }

    public int getDropSeedAtTick() {
        return get(DROP_SEED_AT_TICK);
    }
}
