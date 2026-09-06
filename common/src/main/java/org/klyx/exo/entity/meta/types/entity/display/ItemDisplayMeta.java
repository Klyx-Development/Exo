package org.klyx.exo.entity.meta.types.entity.display;

import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.meta.ItemDisplayContext;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class ItemDisplayMeta extends DisplayMeta {

    private static final MetaAccessor<ExoItemStack> ITEM_STACK = new MetaAccessor<>(23, MetaType.ITEM_STACK, ExoItemStack.EMPTY);
    private static final MetaAccessor<Byte> DISPLAY_TYPE = new MetaAccessor<>(24, MetaType.BYTE, (byte) 0);

    public ItemDisplayMeta setItem(ExoItemStack itemStack) {
        set(ITEM_STACK, itemStack);
        return this;
    }

    public ExoItemStack getItem() {
        return get(ITEM_STACK);
    }

    public ItemDisplayMeta setDisplayType(ItemDisplayContext displayType) {
        setFlag(DISPLAY_TYPE, displayType.getId(), true);
        return this;
    }

    public ItemDisplayContext getDisplayType() {
        return ItemDisplayContext.byId(get(DISPLAY_TYPE));
    }

}
