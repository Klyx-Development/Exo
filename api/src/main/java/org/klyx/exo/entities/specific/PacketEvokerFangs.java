package org.klyx.exo.entities.specific;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketEvokerFangs extends AbstractEntity {
    public PacketEvokerFangs() {
        super(EntityType.EVOKER_FANGS);
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
