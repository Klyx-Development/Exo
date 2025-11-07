package org.klyx.exo.entities.specific.entity.itemFrame;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;

public class AbstractItemFrame extends BaseEntity {
    public AbstractItemFrame(@NotNull EntityType entityType) {
        super(entityType);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.ItemFrame.DIRECTION);
        setMetadata(DataKeys.ItemFrame.ITEM_STACK);
        setMetadata(DataKeys.ItemFrame.ROTATION);
    }

}
