package org.klyx.exo.entity.meta.types.living.creatures;

import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.living.CreatureMeta;

public final class ZombieMeta extends CreatureMeta {

    private static final MetaAccessor<Boolean> BABY =
            new MetaAccessor<>(16, EntityDataSerializers.BOOLEAN, false);
    private static final MetaAccessor<Integer> SPECIAL_TYPE =
            new MetaAccessor<>(17, EntityDataSerializers.INT, 0); // unused
    private static final MetaAccessor<Boolean> DROWNED_CONVERSION =
            new MetaAccessor<>(18, EntityDataSerializers.BOOLEAN, false);

    public ZombieMeta setBaby(boolean baby) {
        set(BABY, baby);
        return this;
    }

    public boolean isBaby() {
        return get(BABY);
    }

    public ZombieMeta setConvertingInWater(boolean converting) {
        set(DROWNED_CONVERSION, converting);
        return this;
    }

    public boolean isConvertingInWater() {
        return get(DROWNED_CONVERSION);
    }
}