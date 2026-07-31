package org.klyx.exo.entity.meta.types.entity.arrow;

import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;

public class ArrowMeta extends AbstractArrowMeta {

    private static final MetaAccessor<Integer> COLOR = new MetaAccessor<>(11, EntityDataSerializers.INT, -1);

    public ArrowMeta setColor(int color) {
        set(COLOR, color);
        return this;
    }

    public int getColor() {
        return get(COLOR);
    }

}
