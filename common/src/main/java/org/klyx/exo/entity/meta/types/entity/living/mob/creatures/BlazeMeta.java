package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class BlazeMeta extends CreatureMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(16, MetaType.BYTE, (byte) 0);

    public BlazeMeta setOnFire(boolean onFire) {
        setFlag(FLAGS, 0, onFire);
        return this;
    }

    public boolean isOnFire() {
        return getFlag(FLAGS, 0);
    }
}
