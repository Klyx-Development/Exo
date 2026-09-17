package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class PillagerMeta extends RaiderMeta {

    private static final MetaAccessor<Boolean> IS_CHARGING_CROSSBOW = new MetaAccessor<>(17, MetaType.BOOLEAN, false);

    public PillagerMeta setChargingCrossbow(boolean charging) {
        set(IS_CHARGING_CROSSBOW, charging);
        return this;
    }

    public boolean isChargingCrossbow() {
        return get(IS_CHARGING_CROSSBOW);
    }
}
