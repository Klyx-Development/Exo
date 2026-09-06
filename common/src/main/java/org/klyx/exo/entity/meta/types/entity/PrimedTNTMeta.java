package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;
import org.klyx.exo.util.Key;

public class PrimedTNTMeta extends EntityMeta {

    private static final MetaAccessor<Integer> FUSE_TIME = new MetaAccessor<>(8, MetaType.INT, 80);
    private static final MetaAccessor<ExoBlockState> BLOCK_STATE = new MetaAccessor<>(9, MetaType.BLOCK_STATE, new ExoBlockState(Key.of("tnt"), null));

    public PrimedTNTMeta setFuseTime(int fuseTime) {
        set(FUSE_TIME, fuseTime);
        return this;
    }

    public int getFuseTime() {
        return get(FUSE_TIME);
    }

    public PrimedTNTMeta setBlockState(ExoBlockState blockState) {
        set(BLOCK_STATE, blockState);
        return this;
    }

    public ExoBlockState getBlockState() {
        return get(BLOCK_STATE);
    }

}
