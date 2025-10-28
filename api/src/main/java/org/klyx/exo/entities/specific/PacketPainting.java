package org.klyx.exo.entities.specific;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketPainting extends AbstractEntity {
    public PacketPainting() {
        super(EntityType.AREA_EFFECT_CLOUD);
    }

    @Override
    protected void initDefaultMetadata() {
        super.initDefaultMetadata();

        entityMetadata.set(DataKeys.Painting.PAINTING_VARIANT);
        entityMetadata.set(DataKeys.Painting.DIRECTION);
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
