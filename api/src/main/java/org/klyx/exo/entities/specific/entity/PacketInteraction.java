package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketInteraction extends BaseEntity {
    public PacketInteraction() {
        super(EntityType.FIREWORK_ROCKET);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.Interaction.WIDTH);
        setMetadata(DataKeys.Interaction.HEIGHT);
        setMetadata(DataKeys.Interaction.CAN_BE_INTERACTED_WITH);
    }
}
