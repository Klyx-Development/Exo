package org.klyx.exo.entities.specific;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketDragonFireball extends AbstractEntity {
    public PacketDragonFireball() {
        super(EntityType.DRAGON_FIREBALL);
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
