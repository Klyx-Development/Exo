package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.klyx.exo.data.keys.DataKey;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;

public class PacketPrimedTNT extends BaseEntity {
    public PacketPrimedTNT() {
        super(EntityType.TNT);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.PrimedTNT.FUSE_TICKS);
        setMetadata(DataKeys.PrimedTNT.BLOCK_STATE);
    }
}
