package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class GuardianMeta extends CreatureMeta {

    private static final MetaAccessor<Boolean> IS_RETRACTING_SPIKES = new MetaAccessor<>(16, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Integer> TARGET_EID = new MetaAccessor<>(17, MetaType.INT, 0);

    public GuardianMeta setRetractingSpikes(boolean retracting) {
        set(IS_RETRACTING_SPIKES, retracting);
        return this;
    }

    public boolean isRetractingSpikes() {
        return get(IS_RETRACTING_SPIKES);
    }

    public GuardianMeta setTargetEntityId(int entityId) {
        set(TARGET_EID, entityId);
        return this;
    }

    public int getTargetEntityId() {
        return get(TARGET_EID);
    }
}
