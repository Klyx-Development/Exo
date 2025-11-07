package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketLightningBolt extends BaseEntity {
    public PacketLightningBolt() {
        super(EntityType.LIGHTNING_BOLT);
    }
}
