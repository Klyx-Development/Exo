package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketExperienceOrb extends BaseEntity {
    public PacketExperienceOrb() {
        super(EntityType.EXPERIENCE_ORB);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.ExperienceOrb.EXPERIENCE_AMOUNT);
    }
}
