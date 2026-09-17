package org.klyx.exo.entity.meta.types.entity.living.mob.water;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class AbstractFishMeta extends WaterAnimalMeta {

    private static final MetaAccessor<Boolean> FROM_BUCKET = new MetaAccessor<>(16, MetaType.BOOLEAN, false);

    public AbstractFishMeta setFromBucket(boolean fromBucket) {
        set(FROM_BUCKET, fromBucket);
        return this;
    }

    public boolean isFromBucket() {
        return get(FROM_BUCKET);
    }
}
