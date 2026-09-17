package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class VexMeta extends CreatureMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(16, MetaType.BYTE, (byte) 0);

    public VexMeta setAttacking(boolean attacking) {
        setFlag(FLAGS, 0, attacking);
        return this;
    }

    public boolean isAttacking() {
        return getFlag(FLAGS, 0);
    }
}
