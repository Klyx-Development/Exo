package org.klyx.exo.entity.meta.types.entity.vehicle;

import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.Optional;

public class AbstractMinecartMeta extends VehicleEntityMeta {

    private static final MetaAccessor<Optional<ExoBlockState>> CUSTOM_DISPLAY_BLOCK =
            new MetaAccessor<>(11, MetaType.OPTIONAL_BLOCK_STATE, Optional.empty());
    private static final MetaAccessor<Integer> DISPLAY_OFFSET = new MetaAccessor<>(12, MetaType.INT, 6);

    public AbstractMinecartMeta setCustomDisplayBlock(Optional<ExoBlockState> block) {
        set(CUSTOM_DISPLAY_BLOCK, block);
        return this;
    }

    public Optional<ExoBlockState> getCustomDisplayBlock() {
        return get(CUSTOM_DISPLAY_BLOCK);
    }

    public AbstractMinecartMeta setDisplayOffset(int offset) {
        set(DISPLAY_OFFSET, offset);
        return this;
    }

    public int getDisplayOffset() {
        return get(DISPLAY_OFFSET);
    }
}
