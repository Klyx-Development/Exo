package org.klyx.exo.entities.impl.components;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import org.bukkit.entity.Player;
import org.klyx.exo.data.entity.EntityState;
import org.klyx.exo.entities.impl.AbstractEntity;
import org.klyx.exo.entities.impl.AbstractLivingEntity;
import org.klyx.exo.utils.PacketUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ViewerRegistry {
    private final AbstractEntity entity;
    private final Set<Player> viewers = ConcurrentHashMap.newKeySet();

    public ViewerRegistry(AbstractEntity entity) {
        this.entity = entity;
    }

    public void addViewer(Player player) {
        if (entity.getState() != EntityState.ALIVE || !viewers.add(player)) return;

        try {
            List<Packet<?>> packetList = new ArrayList<>();
            packetList.add(PacketUtil.createSpawnPacket(entity));
            packetList.add(entity.getEntityMetadata().createPacket());

            if (entity instanceof AbstractLivingEntity livingEntity) {
                ClientboundSetEquipmentPacket packet = livingEntity.getEquipment().createPacket();
                if (packet != null) {
                    packetList.add(packet);
                }

                packetList.add(livingEntity.getEntityAttributes().createPacket());
            }

            PacketUtil.sendBundledPackets(player, packetList.toArray(new Packet<?>[0]));
            entity.onViewerAdded(player);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong trying to add a viewer to entity with id: " + entity.getEntityId() + ", " + e.getMessage());
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
