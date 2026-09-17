package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class SheepMeta extends AnimalMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(18, MetaType.BYTE, (byte) 0);

    public SheepMeta setColor(byte color) {
        byte current = get(FLAGS);
        set(FLAGS, (byte) ((current & ~0x0F) | (color & 0x0F)));
        return this;
    }

    public byte getColor() {
        return (byte) (get(FLAGS) & 0x0F);
    }

    public SheepMeta setSheared(boolean sheared) {
        setFlag(FLAGS, 4, sheared);
        return this;
    }

    public boolean isSheared() {
        return getFlag(FLAGS, 4);
    }
}
