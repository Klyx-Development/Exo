package org.klyx.exo.entities;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BaseEntity extends AbstractEntity {
    public BaseEntity(@NotNull EntityType entityType) {
        super(entityType);
    }

    @Override
    public void onViewerAdded(Player player) {

    }

    @Override
    public void onViewerRemoved(Player player) {

    }
}
