package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class WitherBossMeta extends CreatureMeta {

    private static final MetaAccessor<Integer> CENTER_HEAD_TARGET = new MetaAccessor<>(16, MetaType.INT, 0);
    private static final MetaAccessor<Integer> LEFT_HEAD_TARGET = new MetaAccessor<>(17, MetaType.INT, 0);
    private static final MetaAccessor<Integer> RIGHT_HEAD_TARGET = new MetaAccessor<>(18, MetaType.INT, 0);
    private static final MetaAccessor<Integer> INVULNERABLE_TIME = new MetaAccessor<>(19, MetaType.INT, 0);

    public WitherBossMeta setCenterHeadTarget(int entityId) {
        set(CENTER_HEAD_TARGET, entityId);
        return this;
    }

    public int getCenterHeadTarget() {
        return get(CENTER_HEAD_TARGET);
    }

    public WitherBossMeta setLeftHeadTarget(int entityId) {
        set(LEFT_HEAD_TARGET, entityId);
        return this;
    }

    public int getLeftHeadTarget() {
        return get(LEFT_HEAD_TARGET);
    }

    public WitherBossMeta setRightHeadTarget(int entityId) {
        set(RIGHT_HEAD_TARGET, entityId);
        return this;
    }

    public int getRightHeadTarget() {
        return get(RIGHT_HEAD_TARGET);
    }

    public WitherBossMeta setInvulnerableTime(int ticks) {
        set(INVULNERABLE_TIME, ticks);
        return this;
    }

    public int getInvulnerableTime() {
        return get(INVULNERABLE_TIME);
    }
}
