package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class SpiderMeta extends CreatureMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(16, MetaType.BYTE, (byte) 0);

    public SpiderMeta setClimbing(boolean climbing) {
        setFlag(FLAGS, 0, climbing);
        return this;
    }

    public boolean isClimbing() {
        return getFlag(FLAGS, 0);
    }
}
