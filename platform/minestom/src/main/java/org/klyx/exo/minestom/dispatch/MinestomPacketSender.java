package org.klyx.exo.minestom.dispatch;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.BundlePacket;
import org.klyx.exo.entity.dispatch.PacketSender;

import java.util.List;
import java.util.UUID;

public final class MinestomPacketSender implements PacketSender<ServerPacket> {

    @Override
    public void sendBatch(UUID viewerUUID, List<ServerPacket> orderedPackets) {
        Player player = MinecraftServer.getConnectionManager().getOnlinePlayerByUuid(viewerUUID);
        if (player == null) return;

        if (orderedPackets.size() > 1) {
            player.sendPacket(new BundlePacket());
            for (ServerPacket packet : orderedPackets) {
                player.sendPacket(packet);
            }
            player.sendPacket(new BundlePacket());
        } else {
            for (ServerPacket packet : orderedPackets) {
                player.sendPacket(packet);
            }
        }
    }

    @Override
    public void onFlushError(UUID viewerUUID, Exception e) {
        MinecraftServer.LOGGER.error("Failed to flush packets for {}", viewerUUID, e);
    }
}
