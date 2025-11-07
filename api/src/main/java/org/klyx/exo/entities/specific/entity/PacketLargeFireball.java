package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketLargeFireball extends BaseEntity {
    public PacketLargeFireball() {
        super(EntityType.FIREBALL);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.LargeFireball.ITEM_STACK);
    }
}
