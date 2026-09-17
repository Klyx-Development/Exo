package org.klyx.exo.entity.meta.types.entity.vehicle;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;

public abstract class VehicleEntityMeta extends EntityMeta {

    private static final MetaAccessor<Integer> SHAKING_POWER = new MetaAccessor<>(8, MetaType.INT, 0);
    private static final MetaAccessor<Integer> SHAKING_DIRECTION = new MetaAccessor<>(9, MetaType.INT, 1);
    private static final MetaAccessor<Float> SHAKING_MULTIPLIER = new MetaAccessor<>(10, MetaType.FLOAT, 0.0F);

    public VehicleEntityMeta setShakingPower(int power) {
        set(SHAKING_POWER, power);
        return this;
    }

    public int getShakingPower() {
        return get(SHAKING_POWER);
    }

    public VehicleEntityMeta setShakingDirection(int direction) {
        set(SHAKING_DIRECTION, direction);
        return this;
    }

    public int getShakingDirection() {
        return get(SHAKING_DIRECTION);
    }

    public VehicleEntityMeta setShakingMultiplier(float multiplier) {
        set(SHAKING_MULTIPLIER, multiplier);
        return this;
    }

    public float getShakingMultiplier() {
        return get(SHAKING_MULTIPLIER);
    }
}
