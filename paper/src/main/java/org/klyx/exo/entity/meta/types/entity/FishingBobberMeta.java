package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class FishingBobberMeta extends EntityMeta {

    private static final MetaAccessor<Integer> HOOKED_ENTITY_ID = new MetaAccessor<>(8, EntityDataSerializers.INT, 0);
    private static final MetaAccessor<Boolean> CATCHABLE = new MetaAccessor<>(9, EntityDataSerializers.BOOLEAN, false);

    public FishingBobberMeta setHookedEntityId(int hookedEntityId) {
        set(HOOKED_ENTITY_ID, hookedEntityId);
        return this;
    }

    public int getHookedEntityId() {
        return get(HOOKED_ENTITY_ID);
    }

    public FishingBobberMeta setCatchable(boolean catchable) {
        set(CATCHABLE, catchable);
        return this;
    }

    public boolean isCatchable() {
        return get(CATCHABLE);
    }

}
