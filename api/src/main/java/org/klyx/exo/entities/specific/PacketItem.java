package org.klyx.exo.entities.specific;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketItem extends AbstractEntity {
    public PacketItem() {
        super(EntityType.ITEM);
    }

    @Override
    protected void initDefaultMetadata() {
        super.initDefaultMetadata();

        entityMetadata.set(DataKeys.Item.ITEM_STACK);
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
