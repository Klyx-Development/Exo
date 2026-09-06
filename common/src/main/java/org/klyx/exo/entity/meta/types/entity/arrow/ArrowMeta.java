package org.klyx.exo.entity.meta.types.entity.arrow;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class ArrowMeta extends AbstractArrowMeta {

    private static final MetaAccessor<Integer> COLOR = new MetaAccessor<>(11, MetaType.INT, -1);

    public ArrowMeta setColor(int color) {
        set(COLOR, color);
        return this;
    }

    public int getColor() {
        return get(COLOR);
    }

}
