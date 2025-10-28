package org.klyx.exo.entities.specific;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketInteraction extends AbstractEntity {
    public PacketInteraction() {
        super(EntityType.FIREWORK_ROCKET);
    }

    @Override
    protected void initDefaultMetadata() {
        super.initDefaultMetadata();

        entityMetadata.set(DataKeys.Interaction.WIDTH);
        entityMetadata.set(DataKeys.Interaction.HEIGHT);
        entityMetadata.set(DataKeys.Interaction.CAN_BE_INTERACTED_WITH);
    }

    @Override
    public void onSpawn() {}

    @Override
    public void onDespawn() {}

    @Override
    public void onViewerAdded(Player player) {}

    @Override
    public void onViewerRemoved(Player player) {}
}
