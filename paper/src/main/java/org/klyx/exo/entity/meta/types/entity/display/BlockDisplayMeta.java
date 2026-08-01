package org.klyx.exo.entity.meta.types.entity.display;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;

public class BlockDisplayMeta extends DisplayMeta {

    private static final MetaAccessor<BlockState> BLOCK_STATE = new MetaAccessor<>(23, EntityDataSerializers.BLOCK_STATE, Blocks.AIR.defaultBlockState());

    public BlockDisplayMeta setBlock(BlockState blockState) {
        set(BLOCK_STATE, blockState);
        return this;
    }

    public BlockDisplayMeta setBlock(org.bukkit.block.BlockState blockState) {
        return setBlock(((CraftBlockState) blockState).getHandle());
    }

    public BlockDisplayMeta setBlock(BlockData blockData) {
        return setBlock(blockData.createBlockState());
    }

    public BlockState getBlockState() {
        return get(BLOCK_STATE);
    }

}
