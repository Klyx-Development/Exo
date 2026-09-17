package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class DolphinMeta extends AgeableWaterCreatureMeta {

    private static final MetaAccessor<Boolean> GOT_FISH = new MetaAccessor<>(18, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Integer> MOISTNESS_LEVEL = new MetaAccessor<>(19, MetaType.INT, 2400);

    public DolphinMeta setGotFish(boolean gotFish) {
        set(GOT_FISH, gotFish);
        return this;
    }

    public boolean gotFish() {
        return get(GOT_FISH);
    }

    public DolphinMeta setMoistnessLevel(int level) {
        set(MOISTNESS_LEVEL, level);
        return this;
    }

    public int getMoistnessLevel() {
        return get(MOISTNESS_LEVEL);
    }
}
