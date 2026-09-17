package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class WardenMeta extends CreatureMeta {

    private static final MetaAccessor<Integer> ANGER_LEVEL = new MetaAccessor<>(16, MetaType.INT, 0);

    public WardenMeta setAngerLevel(int angerLevel) {
        set(ANGER_LEVEL, angerLevel);
        return this;
    }

    public int getAngerLevel() {
        return get(ANGER_LEVEL);
    }
}
