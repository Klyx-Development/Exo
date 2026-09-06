package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class EyeOfEnderMeta extends EntityMeta {

    private static final MetaAccessor<ExoItemStack> ITEM_STACK = new MetaAccessor<>(8, MetaType.ITEM_STACK, ExoItemStack.EMPTY);

    public EyeOfEnderMeta setItem(ExoItemStack itemStack) {
        set(ITEM_STACK, itemStack);
        return this;
    }

    public ExoItemStack getItem() {
        return get(ITEM_STACK);
    }

}
