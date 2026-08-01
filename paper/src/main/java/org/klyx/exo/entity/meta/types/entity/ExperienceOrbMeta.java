package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class ExperienceOrbMeta extends EntityMeta {

    private static final MetaAccessor<Integer> VALUE = new MetaAccessor<>(8, EntityDataSerializers.INT, 0);

    public ExperienceOrbMeta setValue(int value) {
        set(VALUE, value);
        return this;
    }

    public int getValue() {
        return get(VALUE);
    }

}
