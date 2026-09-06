package org.klyx.exo.entity.meta.types.entity.display;

import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.util.Key;

public class BlockDisplayMeta extends DisplayMeta {

    private static final MetaAccessor<ExoBlockState> BLOCK_STATE = new MetaAccessor<>(23, MetaType.BLOCK_STATE, new ExoBlockState(Key.of("air"), null));

    public BlockDisplayMeta setBlock(ExoBlockState blockState) {
        set(BLOCK_STATE, blockState);
        return this;
    }

    public ExoBlockState getBlockState() {
        return get(BLOCK_STATE);
    }

}
