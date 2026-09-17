package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class AxolotlMeta extends AnimalMeta {

    private static final MetaAccessor<Integer> VARIANT = new MetaAccessor<>(18, MetaType.INT, 0);
    private static final MetaAccessor<Boolean> PLAYING_DEAD = new MetaAccessor<>(19, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> FROM_BUCKET = new MetaAccessor<>(20, MetaType.BOOLEAN, false);

    public AxolotlMeta setVariant(int variant) {
        set(VARIANT, variant);
        return this;
    }

    public int getVariant() {
        return get(VARIANT);
    }

    public AxolotlMeta setPlayingDead(boolean playingDead) {
        set(PLAYING_DEAD, playingDead);
        return this;
    }

    public boolean isPlayingDead() {
        return get(PLAYING_DEAD);
    }

    public AxolotlMeta setFromBucket(boolean fromBucket) {
        set(FROM_BUCKET, fromBucket);
        return this;
    }

    public boolean isFromBucket() {
        return get(FROM_BUCKET);
    }
}
