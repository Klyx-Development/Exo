package org.klyx.exo.entity.meta.types.entity.living.mob.golem;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class SnowGolemMeta extends AbstractGolemMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(16, MetaType.BYTE, (byte) 0x10);

    public SnowGolemMeta setPumpkinHat(boolean pumpkinHat) {
        setFlag(FLAGS, 4, pumpkinHat);
        return this;
    }

    public boolean hasPumpkinHat() {
        return getFlag(FLAGS, 4);
    }
}
