package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class PrimedTNTMeta extends EntityMeta {

    private static final MetaAccessor<Integer> FUSE_TIME = new MetaAccessor<>(8, EntityDataSerializers.INT, 80);
    private static final MetaAccessor<BlockState> BLOCK_STATE = new MetaAccessor<>(9, EntityDataSerializers.BLOCK_STATE, Blocks.TNT.defaultBlockState());

    public PrimedTNTMeta setFuseTime(int fuseTime) {
        set(FUSE_TIME, fuseTime);
        return this;
    }

    public int getFuseTime() {
        return get(FUSE_TIME);
    }

    public PrimedTNTMeta setBlockState(BlockState blockState) {
        set(BLOCK_STATE, blockState);
        return this;
    }

    public PrimedTNTMeta setBlockState(org.bukkit.block.BlockState blockState) {
        setBlockState(((CraftBlockState) blockState).getHandle());
        return this;
    }

    public BlockState getBlockState() {
        return get(BLOCK_STATE);
    }

}
