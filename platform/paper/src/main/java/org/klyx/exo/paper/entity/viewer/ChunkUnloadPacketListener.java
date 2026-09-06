package org.klyx.exo.paper.entity.viewer;

import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import org.bukkit.entity.Player;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.paper.player.ExoPaperPlayer;
import org.klyx.exo.paper.packet.impl.Packets;
import org.klyx.exo.paper.packet.impl.listener.PacketListener;

import java.util.Collection;

public class ChunkUnloadPacketListener implements PacketListener<ClientboundForgetLevelChunkPacket> {

    public ChunkUnloadPacketListener() {
        Packets.INSTANCE.registerListener(this);
    }

    @Override
    public ClientboundForgetLevelChunkPacket processPacket(Player player, ClientboundForgetLevelChunkPacket packet) {
        Collection<ExoEntity> entities = Exo.entityManager().getEntitiesInChunk(packet.pos().x(), packet.pos().z());
        if (entities.isEmpty()) return packet;

        ExoPaperPlayer exoPlayer = ExoPaperPlayer.of(player);
        entities.forEach(entity -> entity.getViewerManager().handleUnload(exoPlayer));
        return packet;
    }
}
