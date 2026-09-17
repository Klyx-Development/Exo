package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.util.Key;

public final class ZombieNautilusMeta extends AbstractNautilusMeta {

    private static final MetaAccessor<ExoVariant> VARIANT =
            new MetaAccessor<>(21, MetaType.ZOMBIE_NAUTILUS_VARIANT, new ExoVariant(Key.of("temperate")));

    public ZombieNautilusMeta setVariant(ExoVariant variant) {
        set(VARIANT, variant);
        return this;
    }

    public ExoVariant getVariant() {
        return get(VARIANT);
    }
}
