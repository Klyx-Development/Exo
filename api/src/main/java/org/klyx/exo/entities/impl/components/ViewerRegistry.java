package org.klyx.exo.entities.impl.components;

import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import org.bukkit.entity.Player;
import org.klyx.exo.data.entity.EntityState;
import org.klyx.exo.entities.impl.AbstractEntity;
import org.klyx.exo.utils.PacketUtil;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ViewerRegistry {
    private final AbstractEntity entity;
    private final Set<Player> viewers = ConcurrentHashMap.newKeySet();

    public ViewerRegistry(AbstractEntity entity) {
        this.entity = entity;
    }

    public void addViewer(Player player) {
        if (entity.getState() != EntityState.ALIVE) return;

        if (viewers.add(player)) {
            PacketUtil.sendBundledPackets(
                    player,
                    PacketUtil.createSpawnPacket(entity),
                    entity.entityMetadata.createPacket()
            );
            entity.onViewerAdded(player);
        }
    }

    public void removeViewer(Player player) {
        if (viewers.remove(player)) {
            PacketUtil.sendPacket(player, new ClientboundRemoveEntitiesPacket(entity.getEntityId()));
            entity.onViewerRemoved(player);
        }
    }

    public void clearAll() {
        new ArrayList<>(viewers).forEach(this::removeViewer);
        viewers.clear();
    }

    public Set<Player> getViewers() {
        return Set.copyOf(viewers);
    }

    public boolean hasViewer(Player player) {
        return viewers.contains(player);
    }
}
