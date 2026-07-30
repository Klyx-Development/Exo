package org.klyx.exo.entity.meta.types.living;

import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;

public abstract class MobMeta extends LivingEntityMeta {

    private static final MetaAccessor<Byte> MOB_FLAGS =
            new MetaAccessor<>(15, EntityDataSerializers.BYTE, (byte) 0);

    public MobMeta setNoAi(boolean noAi) {
        setFlag(MOB_FLAGS, 0, noAi);
        return this;
    }

    public boolean isNoAi() {
        return getFlag(MOB_FLAGS, 0);
    }

    public MobMeta setLeftHanded(boolean leftHanded) {
        setFlag(MOB_FLAGS, 1, leftHanded);
        return this;
    }

    public boolean isLeftHanded() {
        return getFlag(MOB_FLAGS, 1);
    }

    public MobMeta setAggressive(boolean aggressive) {
        setFlag(MOB_FLAGS, 2, aggressive);
        return this;
    }

    public boolean isAggressive() {
        return getFlag(MOB_FLAGS, 2);
    }
}