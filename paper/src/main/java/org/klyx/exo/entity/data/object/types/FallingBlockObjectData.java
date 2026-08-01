package org.klyx.exo.entity.data.object.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.entity.EntityType;
import org.klyx.exo.entity.data.object.AbstractObjectData;

public class FallingBlockObjectData extends AbstractObjectData {
    protected FallingBlockObjectData(int blockStateId) {
        super(EntityType.FALLING_BLOCK, blockStateId);
    }

    public static FallingBlockObjectData of(BlockData blockData) {
        BlockState nmsState = ((CraftBlockData) blockData).getState();
        int stateId = Block.getId(nmsState);
        return new FallingBlockObjectData(stateId);
    }
}
