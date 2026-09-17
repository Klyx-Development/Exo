package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class AbstractPiglinMeta extends CreatureMeta {

    private static final MetaAccessor<Boolean> IMMUNE_TO_ZOMBIFICATION = new MetaAccessor<>(16, MetaType.BOOLEAN, false);

    public AbstractPiglinMeta setImmuneToZombification(boolean immune) {
        set(IMMUNE_TO_ZOMBIFICATION, immune);
        return this;
    }

    public boolean isImmuneToZombification() {
        return get(IMMUNE_TO_ZOMBIFICATION);
    }
}
