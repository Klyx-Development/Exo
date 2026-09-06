package org.klyx.exo.paper.dispatch;

import net.minecraft.network.protocol.Packet;
import org.klyx.exo.entity.dispatch.PacketSender;
import org.klyx.exo.paper.ExoPaper;
import org.klyx.exo.paper.packet.impl.Packets;

import java.util.List;
import java.util.UUID;

public final class PaperPacketSender implements PacketSender<Packet<?>> {

    @Override
    public void sendBatch(UUID viewerUUID, List<Packet<?>> orderedPackets) {
        Packets.INSTANCE.sendPackets(viewerUUID, orderedPackets.toArray(Packet[]::new));
    }

    @Override
    public void onFlushError(UUID viewerUUID, Exception e) {
        ExoPaper.logger().error("Failed to flush packets for {}", viewerUUID, e);
    }
}
