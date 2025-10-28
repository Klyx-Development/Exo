package org.klyx.exo.entities.specific;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketAreaEffectCloud extends AbstractEntity {
    public PacketAreaEffectCloud() {
        super(EntityType.AREA_EFFECT_CLOUD);
    }

    @Override
    protected void initDefaultMetadata() {
        super.initDefaultMetadata();

        entityMetadata.set(DataKeys.AreaEffectCloud.RADIUS);
        entityMetadata.set(DataKeys.AreaEffectCloud.IGNORE_RADIUS);
        entityMetadata.set(DataKeys.AreaEffectCloud.PARTICLE);
    }

    @Override
    public void onSpawn() {

    }

    @Override
    public void onDespawn() {

    }

    @Override
    public void onViewerAdded(Player player) {

    }

    @Override
    public void onViewerRemoved(Player player) {

    }
}
