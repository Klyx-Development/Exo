package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.util.Key;

public final class PigMeta extends AnimalMeta {

    private static final MetaAccessor<Integer> BOOST_TIME = new MetaAccessor<>(18, MetaType.INT, 0);
    private static final MetaAccessor<ExoVariant> VARIANT =
            new MetaAccessor<>(19, MetaType.PIG_VARIANT, new ExoVariant(Key.of("temperate")));
    private static final MetaAccessor<ExoVariant> SOUND_VARIANT =
            new MetaAccessor<>(20, MetaType.PIG_SOUND_VARIANT, new ExoVariant(Key.of("classic")));

    public PigMeta setBoostTime(int ticks) {
        set(BOOST_TIME, ticks);
        return this;
    }

    public int getBoostTime() {
        return get(BOOST_TIME);
    }

    public PigMeta setVariant(ExoVariant variant) {
        set(VARIANT, variant);
        return this;
    }

    public ExoVariant getVariant() {
        return get(VARIANT);
    }

    public PigMeta setSoundVariant(ExoVariant variant) {
        set(SOUND_VARIANT, variant);
        return this;
    }

    public ExoVariant getSoundVariant() {
        return get(SOUND_VARIANT);
    }
}
