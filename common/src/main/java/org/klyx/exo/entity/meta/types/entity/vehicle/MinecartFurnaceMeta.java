package org.klyx.exo.entity.meta.types.entity.vehicle;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class MinecartFurnaceMeta extends AbstractMinecartMeta {

    private static final MetaAccessor<Boolean> HAS_FUEL = new MetaAccessor<>(13, MetaType.BOOLEAN, false);

    public MinecartFurnaceMeta setFuel(boolean fuel) {
        set(HAS_FUEL, fuel);
        return this;
    }

    public boolean hasFuel() {
        return get(HAS_FUEL);
    }
}
