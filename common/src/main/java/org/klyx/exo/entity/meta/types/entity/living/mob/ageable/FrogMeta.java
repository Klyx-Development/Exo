package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.util.Key;

import java.util.OptionalInt;

public final class FrogMeta extends AnimalMeta {

    private static final MetaAccessor<ExoVariant> VARIANT =
            new MetaAccessor<>(18, MetaType.FROG_VARIANT, new ExoVariant(Key.of("temperate")));
    private static final MetaAccessor<OptionalInt> TONGUE_TARGET =
            new MetaAccessor<>(19, MetaType.OPTIONAL_UNSIGNED_INT, OptionalInt.empty());

    public FrogMeta setVariant(ExoVariant variant) {
        set(VARIANT, variant);
        return this;
    }

    public ExoVariant getVariant() {
        return get(VARIANT);
    }

    public FrogMeta setTongueTarget(OptionalInt entityId) {
        set(TONGUE_TARGET, entityId);
        return this;
    }

    public OptionalInt getTongueTarget() {
        return get(TONGUE_TARGET);
    }
}
