package org.klyx.exo.entities.specific.itemFrame;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.impl.AbstractEntity;

public class AbstractItemFrame extends AbstractEntity {
    public AbstractItemFrame(@NotNull EntityType entityType) {
        super(entityType);
    }

    @Override
    protected void initDefaultMetadata() {
        super.initDefaultMetadata();

        entityMetadata.set(DataKeys.ItemFrame.DIRECTION);
        entityMetadata.set(DataKeys.ItemFrame.ITEM_STACK);
        entityMetadata.set(DataKeys.ItemFrame.ROTATION);
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
