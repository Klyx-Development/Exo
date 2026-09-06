package org.klyx.exo.minestom.dispatch;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.AttachEntityPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.common.LeashDispatcher;

import java.util.List;
import java.util.UUID;

public final class MinestomLeashDispatcher implements LeashDispatcher {

    private final PacketDispatcher<ServerPacket> packetDispatcher;

    public MinestomLeashDispatcher(PacketDispatcher<ServerPacket> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchLink(ExoEntity entity, int holderEntityId) {
        AttachEntityPacket packet = new AttachEntityPacket(entity.entityId(), holderEntityId);
        for (UUID viewer : entity.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }
}
