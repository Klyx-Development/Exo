package org.klyx.exo.entities.specific.entity.displays;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.entity.EntityType;
import org.klyx.exo.data.keys.DataKeys;

public class PacketBlockDisplay extends AbstractPacketDisplay {
    public PacketBlockDisplay() {
        super(EntityType.BLOCK_DISPLAY);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.BlockDisplay.BLOCK_STATE);
    }

    public void setBlock(BlockData blockData) {
        setMetadata(DataKeys.BlockDisplay.BLOCK_STATE, ((CraftBlockData) blockData).getState());
    }
}
