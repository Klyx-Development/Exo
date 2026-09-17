package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.util.Key;

public final class CowMeta extends AbstractCowMeta {

    private static final MetaAccessor<ExoVariant> VARIANT =
            new MetaAccessor<>(18, MetaType.COW_VARIANT, new ExoVariant(Key.of("temperate")));
    private static final MetaAccessor<ExoVariant> SOUND_VARIANT =
            new MetaAccessor<>(19, MetaType.COW_SOUND_VARIANT, new ExoVariant(Key.of("classic")));

    public CowMeta setVariant(ExoVariant variant) {
        set(VARIANT, variant);
        return this;
    }

    public ExoVariant getVariant() {
        return get(VARIANT);
    }

    public CowMeta setSoundVariant(ExoVariant variant) {
        set(SOUND_VARIANT, variant);
        return this;
    }

    public ExoVariant getSoundVariant() {
        return get(SOUND_VARIANT);
    }
}
