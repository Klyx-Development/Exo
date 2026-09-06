package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class WitherSkullMeta extends EntityMeta {

    private static final MetaAccessor<Boolean> INVULNERABLE = new MetaAccessor<>(8, MetaType.BOOLEAN, false);

    public WitherSkullMeta setInvulnerable(boolean invulnerable) {
        set(INVULNERABLE, invulnerable);
        return this;
    }

    public boolean isInvulnerable() {
        return get(INVULNERABLE);
    }

}
