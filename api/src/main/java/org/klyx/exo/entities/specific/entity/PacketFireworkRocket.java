package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketFireworkRocket extends BaseEntity {
    public PacketFireworkRocket() {
        super(EntityType.FIREWORK_ROCKET);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.FireworkRocket.ITEM_STACK);
        setMetadata(DataKeys.FireworkRocket.LAUNCHER_ENTITY_ID);
        setMetadata(DataKeys.FireworkRocket.SHOT_AT_ANGLE);
    }
}
