package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class AbstractHorseMeta extends AnimalMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(18, MetaType.BYTE, (byte) 0);

    public AbstractHorseMeta setTame(boolean tame) {
        setFlag(FLAGS, 1, tame);
        return this;
    }

    public boolean isTame() {
        return getFlag(FLAGS, 1);
    }

    public AbstractHorseMeta setBred(boolean bred) {
        setFlag(FLAGS, 3, bred);
        return this;
    }

    public boolean hasBred() {
        return getFlag(FLAGS, 3);
    }

    public AbstractHorseMeta setEating(boolean eating) {
        setFlag(FLAGS, 4, eating);
        return this;
    }

    public boolean isEating() {
        return getFlag(FLAGS, 4);
    }

    public AbstractHorseMeta setRearing(boolean rearing) {
        setFlag(FLAGS, 5, rearing);
        return this;
    }

    public boolean isRearing() {
        return getFlag(FLAGS, 5);
    }

    public AbstractHorseMeta setMouthOpen(boolean open) {
        setFlag(FLAGS, 6, open);
        return this;
    }

    public boolean isMouthOpen() {
        return getFlag(FLAGS, 6);
    }
}
