package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class LlamaMeta extends ChestedHorseMeta {

    private static final MetaAccessor<Integer> STRENGTH = new MetaAccessor<>(20, MetaType.INT, 0);
    private static final MetaAccessor<Integer> VARIANT = new MetaAccessor<>(21, MetaType.INT, 0);

    public LlamaMeta setStrength(int strength) {
        set(STRENGTH, strength);
        return this;
    }

    public int getStrength() {
        return get(STRENGTH);
    }

    public LlamaMeta setVariant(int variant) {
        set(VARIANT, variant);
        return this;
    }

    public int getVariant() {
        return get(VARIANT);
    }
}
