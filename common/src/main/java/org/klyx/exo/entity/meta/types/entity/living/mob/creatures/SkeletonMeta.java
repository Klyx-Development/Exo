package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class SkeletonMeta extends AbstractSkeletonMeta {

    private static final MetaAccessor<Boolean> STRAY_CONVERSION = new MetaAccessor<>(16, MetaType.BOOLEAN, false);

    public SkeletonMeta setConvertingToStray(boolean converting) {
        set(STRAY_CONVERSION, converting);
        return this;
    }

    public boolean isConvertingToStray() {
        return get(STRAY_CONVERSION);
    }
}
