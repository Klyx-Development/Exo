package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class BeeMeta extends AnimalMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(18, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Long> ANGER_END_TIME = new MetaAccessor<>(19, MetaType.LONG, -1L);

    public BeeMeta setRolling(boolean rolling) {
        setFlag(FLAGS, 1, rolling);
        return this;
    }

    public boolean isRolling() {
        return getFlag(FLAGS, 1);
    }

    public BeeMeta setHasStung(boolean stung) {
        setFlag(FLAGS, 2, stung);
        return this;
    }

    public boolean hasStung() {
        return getFlag(FLAGS, 2);
    }

    public BeeMeta setHasNectar(boolean nectar) {
        setFlag(FLAGS, 3, nectar);
        return this;
    }

    public boolean hasNectar() {
        return getFlag(FLAGS, 3);
    }

    public BeeMeta setAngerEndTime(long time) {
        set(ANGER_END_TIME, time);
        return this;
    }

    public long getAngerEndTime() {
        return get(ANGER_END_TIME);
    }
}
