package org.klyx.exo.entity.viewer;

import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import org.bukkit.entity.Player;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.util.packet.impl.Packets;
import org.klyx.exo.util.packet.impl.listener.PacketListener;

import java.util.Collection;

public class ChunkLoadPacketListener implements PacketListener<ClientboundLevelChunkWithLightPacket> {

    public ChunkLoadPacketListener() {
        Packets.INSTANCE.registerListener(this);
    }

    @Override
    public ClientboundLevelChunkWithLightPacket processPacket(Player player, ClientboundLevelChunkWithLightPacket packet) {
        Collection<ExoEntity> entities = Exo.entityManager().getEntitiesInChunk(packet.getX(), packet.getZ());
        if (entities.isEmpty()) return packet;

        entities.forEach(entity -> entity.getViewerManager().handleLoad(player));
        return packet;
    }
}
