package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketAreaEffectCloud extends BaseEntity {
    public PacketAreaEffectCloud() {
        super(EntityType.AREA_EFFECT_CLOUD);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.AreaEffectCloud.RADIUS);
        setMetadata(DataKeys.AreaEffectCloud.IGNORE_RADIUS);
        setMetadata(DataKeys.AreaEffectCloud.PARTICLE);
    }
}
