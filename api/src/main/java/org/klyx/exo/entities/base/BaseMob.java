package org.klyx.exo.entities.base;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.entities.impl.AbstractMob;

public class BaseMob extends AbstractMob {
    public BaseMob(@NotNull EntityType entityType) {
        super(entityType);
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
