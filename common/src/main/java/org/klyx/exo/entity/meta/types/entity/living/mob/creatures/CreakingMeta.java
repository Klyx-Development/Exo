package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.ExoBlockPos;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.Optional;

public final class CreakingMeta extends CreatureMeta {

    private static final MetaAccessor<Boolean> CAN_MOVE = new MetaAccessor<>(16, MetaType.BOOLEAN, true);
    private static final MetaAccessor<Boolean> IS_ACTIVE = new MetaAccessor<>(17, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> IS_TEARING_DOWN = new MetaAccessor<>(18, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Optional<ExoBlockPos>> HOME_POS =
            new MetaAccessor<>(19, MetaType.OPTIONAL_BLOCK_POS, Optional.empty());

    public CreakingMeta setCanMove(boolean canMove) {
        set(CAN_MOVE, canMove);
        return this;
    }

    public boolean canMove() {
        return get(CAN_MOVE);
    }

    public CreakingMeta setActive(boolean active) {
        set(IS_ACTIVE, active);
        return this;
    }

    public boolean isActive() {
        return get(IS_ACTIVE);
    }

    public CreakingMeta setTearingDown(boolean tearingDown) {
        set(IS_TEARING_DOWN, tearingDown);
        return this;
    }

    public boolean isTearingDown() {
        return get(IS_TEARING_DOWN);
    }

    public CreakingMeta setHomePos(Optional<ExoBlockPos> pos) {
        set(HOME_POS, pos);
        return this;
    }

    public Optional<ExoBlockPos> getHomePos() {
        return get(HOME_POS);
    }
}
