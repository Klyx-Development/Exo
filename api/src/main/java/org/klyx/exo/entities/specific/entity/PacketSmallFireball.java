package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;

public class PacketSmallFireball extends BaseEntity {
    public PacketSmallFireball() {
        super(EntityType.SMALL_FIREBALL);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.Fireball.ITEM_STACK);
    }
}
