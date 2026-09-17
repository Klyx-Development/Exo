package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class SulfurCubeMeta extends AbstractCubeMobMeta {

    private static final MetaAccessor<Integer> MAX_FUSE = new MetaAccessor<>(19, MetaType.INT, -1);
    private static final MetaAccessor<Boolean> FROM_BUCKET = new MetaAccessor<>(20, MetaType.BOOLEAN, false);

    public SulfurCubeMeta setMaxFuse(int maxFuse) {
        set(MAX_FUSE, maxFuse);
        return this;
    }

    public int getMaxFuse() {
        return get(MAX_FUSE);
    }

    public SulfurCubeMeta setFromBucket(boolean fromBucket) {
        set(FROM_BUCKET, fromBucket);
        return this;
    }

    public boolean isFromBucket() {
        return get(FROM_BUCKET);
    }
}
