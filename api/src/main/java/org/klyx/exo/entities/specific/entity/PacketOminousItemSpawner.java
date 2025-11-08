package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;

public class PacketOminousItemSpawner extends BaseEntity {
    public PacketOminousItemSpawner() {
        super(EntityType.OMINOUS_ITEM_SPAWNER);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.OminousItemSpawner.ITEM_STACK);
    }
}
