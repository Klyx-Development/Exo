package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketFallingBlock extends BaseEntity {
    public PacketFallingBlock() {
        super(EntityType.FALLING_BLOCK);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.FallingBlock.POSITION);
    }
}
