package org.klyx.exo.entity.meta.types.entity.arrow;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class AbstractArrowMeta extends EntityMeta {

    private static final MetaAccessor<Byte> ARROW_FLAGS = new MetaAccessor<>(8, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Byte> PIERCING_LEVEL = new MetaAccessor<>(9, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Boolean> IN_GROUND = new MetaAccessor<>(10, MetaType.BOOLEAN, false);

    public AbstractArrowMeta setCritical(boolean critical) {
        setFlag(ARROW_FLAGS, 0, critical);
        return this;
    }

    public boolean isCritical() {
        return getFlag(ARROW_FLAGS, 0);
    }

    public AbstractArrowMeta setNoclip(boolean noclip) {
        setFlag(ARROW_FLAGS, 1, noclip);
        return this;
    }

    public AbstractArrowMeta setPiercing(boolean piercing) {
        setFlag(PIERCING_LEVEL, 1, piercing);
        return this;
    }

    public boolean isPiercing() {
        return getFlag(PIERCING_LEVEL, 1);
    }

    public AbstractArrowMeta setInGround(boolean inGround) {
        set(IN_GROUND, inGround);
        return this;
    }

    public boolean isInGround() {
        return get(IN_GROUND);
    }

}
