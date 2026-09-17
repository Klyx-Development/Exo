package org.klyx.exo.entity.meta.types.entity.living.mob.golem;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class IronGolemMeta extends AbstractGolemMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(16, MetaType.BYTE, (byte) 0);

    public IronGolemMeta setPlayerCreated(boolean playerCreated) {
        setFlag(FLAGS, 0, playerCreated);
        return this;
    }

    public boolean isPlayerCreated() {
        return getFlag(FLAGS, 0);
    }
}
