package org.klyx.exo.paper.entity.data.object.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.entity.EntityType;
import org.klyx.exo.entity.data.object.AbstractObjectData;
import org.klyx.exo.paper.entity.PaperEntityTypes;

public class FallingBlockObjectData extends AbstractObjectData {
    protected FallingBlockObjectData(int blockStateId) {
        super(PaperEntityTypes.toExo(EntityType.FALLING_BLOCK), blockStateId);
    }

    public static FallingBlockObjectData of(BlockData blockData) {
        BlockState nmsState = ((CraftBlockData) blockData).getState();
        int stateId = Block.getId(nmsState);
        return new FallingBlockObjectData(stateId);
    }
}
