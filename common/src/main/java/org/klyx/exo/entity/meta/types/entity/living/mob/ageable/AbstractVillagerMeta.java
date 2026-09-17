package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class AbstractVillagerMeta extends AgeableMobMeta {

    private static final MetaAccessor<Integer> UNHAPPY_COUNTER = new MetaAccessor<>(18, MetaType.INT, 0);

    public AbstractVillagerMeta setUnhappyCounter(int counter) {
        set(UNHAPPY_COUNTER, counter);
        return this;
    }

    public int getUnhappyCounter() {
        return get(UNHAPPY_COUNTER);
    }
}
