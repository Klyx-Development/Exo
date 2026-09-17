package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class CamelMeta extends AbstractHorseMeta {

    private static final MetaAccessor<Boolean> DASHING = new MetaAccessor<>(19, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Long> LAST_POSE_CHANGE_TICK = new MetaAccessor<>(20, MetaType.LONG, 0L);

    public CamelMeta setDashing(boolean dashing) {
        set(DASHING, dashing);
        return this;
    }

    public boolean isDashing() {
        return get(DASHING);
    }

    public CamelMeta setLastPoseChangeTick(long tick) {
        set(LAST_POSE_CHANGE_TICK, tick);
        return this;
    }

    public long getLastPoseChangeTick() {
        return get(LAST_POSE_CHANGE_TICK);
    }
}
