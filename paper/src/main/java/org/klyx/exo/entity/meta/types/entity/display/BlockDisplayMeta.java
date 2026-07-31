package org.klyx.exo.entity.meta.types.entity.display;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;

public class BlockDisplayMeta extends DisplayMeta {

    private static final MetaAccessor<BlockState> BLOCK_STATE = new MetaAccessor<>(23, EntityDataSerializers.BLOCK_STATE, Blocks.AIR.defaultBlockState());

    public BlockDisplayMeta setBlockState(BlockState blockState) {
        set(BLOCK_STATE, blockState);
        return this;
    }

    public BlockState getBlockState() {
        return get(BLOCK_STATE);
    }

}
