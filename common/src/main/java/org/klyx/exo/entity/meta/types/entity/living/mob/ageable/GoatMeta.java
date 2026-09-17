package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class GoatMeta extends AnimalMeta {

    private static final MetaAccessor<Boolean> IS_SCREAMING_GOAT = new MetaAccessor<>(18, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> HAS_LEFT_HORN = new MetaAccessor<>(19, MetaType.BOOLEAN, true);
    private static final MetaAccessor<Boolean> HAS_RIGHT_HORN = new MetaAccessor<>(20, MetaType.BOOLEAN, true);

    public GoatMeta setScreamingGoat(boolean screaming) {
        set(IS_SCREAMING_GOAT, screaming);
        return this;
    }

    public boolean isScreamingGoat() {
        return get(IS_SCREAMING_GOAT);
    }

    public GoatMeta setLeftHorn(boolean leftHorn) {
        set(HAS_LEFT_HORN, leftHorn);
        return this;
    }

    public boolean hasLeftHorn() {
        return get(HAS_LEFT_HORN);
    }

    public GoatMeta setRightHorn(boolean rightHorn) {
        set(HAS_RIGHT_HORN, rightHorn);
        return this;
    }

    public boolean hasRightHorn() {
        return get(HAS_RIGHT_HORN);
    }
}
