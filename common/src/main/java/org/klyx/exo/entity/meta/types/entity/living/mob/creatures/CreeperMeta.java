package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class CreeperMeta extends CreatureMeta {

    private static final MetaAccessor<Integer> SWELL_DIR = new MetaAccessor<>(16, MetaType.INT, -1);
    private static final MetaAccessor<Boolean> IS_POWERED = new MetaAccessor<>(17, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> IS_IGNITED = new MetaAccessor<>(18, MetaType.BOOLEAN, false);

    public CreeperMeta setSwellDir(int swellDir) {
        set(SWELL_DIR, swellDir);
        return this;
    }

    public int getSwellDir() {
        return get(SWELL_DIR);
    }

    public CreeperMeta setPowered(boolean powered) {
        set(IS_POWERED, powered);
        return this;
    }

    public boolean isPowered() {
        return get(IS_POWERED);
    }

    public CreeperMeta setIgnited(boolean ignited) {
        set(IS_IGNITED, ignited);
        return this;
    }

    public boolean isIgnited() {
        return get(IS_IGNITED);
    }
}
