package org.klyx.exo.entity.meta.types.entity.vehicle;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class AbstractBoatMeta extends VehicleEntityMeta {

    private static final MetaAccessor<Boolean> LEFT_PADDLE_TURNING = new MetaAccessor<>(11, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> RIGHT_PADDLE_TURNING = new MetaAccessor<>(12, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Integer> SPLASH_TIMER = new MetaAccessor<>(13, MetaType.INT, 0);

    public AbstractBoatMeta setLeftPaddleTurning(boolean turning) {
        set(LEFT_PADDLE_TURNING, turning);
        return this;
    }

    public boolean isLeftPaddleTurning() {
        return get(LEFT_PADDLE_TURNING);
    }

    public AbstractBoatMeta setRightPaddleTurning(boolean turning) {
        set(RIGHT_PADDLE_TURNING, turning);
        return this;
    }

    public boolean isRightPaddleTurning() {
        return get(RIGHT_PADDLE_TURNING);
    }

    public AbstractBoatMeta setSplashTimer(int timer) {
        set(SPLASH_TIMER, timer);
        return this;
    }

    public int getSplashTimer() {
        return get(SPLASH_TIMER);
    }
}
