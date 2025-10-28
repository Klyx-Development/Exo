package org.klyx.exo.entities.specific;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketFireworkRocket extends AbstractEntity {
    public PacketFireworkRocket() {
        super(EntityType.FIREWORK_ROCKET);
    }

    @Override
    protected void initDefaultMetadata() {
        super.initDefaultMetadata();

        entityMetadata.set(DataKeys.FireworkRocket.ITEM_STACK);
        entityMetadata.set(DataKeys.FireworkRocket.LAUNCHER_ENTITY_ID);
        entityMetadata.set(DataKeys.FireworkRocket.SHOT_AT_ANGLE);
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
