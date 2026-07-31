package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class FallingBlockMeta extends EntityMeta {

    private static final MetaAccessor<BlockPos> SPAWN_POSITION = new MetaAccessor<>(8, EntityDataSerializers.BLOCK_POS, BlockPos.ZERO);

    public FallingBlockMeta setSpawnPosition(BlockPos pos) {
        set(SPAWN_POSITION, pos);
        return this;
    }

    public BlockPos getSpawnPosition() {
        return get(SPAWN_POSITION);
    }

}
