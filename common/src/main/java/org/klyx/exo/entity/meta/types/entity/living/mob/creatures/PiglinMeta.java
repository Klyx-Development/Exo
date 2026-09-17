package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class PiglinMeta extends AbstractPiglinMeta {

    private static final MetaAccessor<Boolean> BABY = new MetaAccessor<>(17, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> IS_CHARGING_CROSSBOW = new MetaAccessor<>(18, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> IS_DANCING = new MetaAccessor<>(19, MetaType.BOOLEAN, false);

    public PiglinMeta setBaby(boolean baby) {
        set(BABY, baby);
        return this;
    }

    public boolean isBaby() {
        return get(BABY);
    }

    public PiglinMeta setChargingCrossbow(boolean charging) {
        set(IS_CHARGING_CROSSBOW, charging);
        return this;
    }

    public boolean isChargingCrossbow() {
        return get(IS_CHARGING_CROSSBOW);
    }

    public PiglinMeta setDancing(boolean dancing) {
        set(IS_DANCING, dancing);
        return this;
    }

    public boolean isDancing() {
        return get(IS_DANCING);
    }
}
