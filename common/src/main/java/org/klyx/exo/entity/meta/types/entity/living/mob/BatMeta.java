package org.klyx.exo.entity.meta.types.entity.living.mob;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class BatMeta extends AmbientCreatureMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(16, MetaType.BYTE, (byte) 0);

    public BatMeta setHanging(boolean hanging) {
        setFlag(FLAGS, 0, hanging);
        return this;
    }

    public boolean isHanging() {
        return getFlag(FLAGS, 0);
    }
}
