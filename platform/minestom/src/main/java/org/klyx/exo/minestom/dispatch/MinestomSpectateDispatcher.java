package org.klyx.exo.minestom.dispatch;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.CameraPacket;
import org.klyx.exo.common.SpectateDispatcher;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.player.ExoPlayer;

import java.util.List;

public class MinestomSpectateDispatcher implements SpectateDispatcher {

    private final PacketDispatcher<ServerPacket> packetDispatcher;

    public MinestomSpectateDispatcher(PacketDispatcher<ServerPacket> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchSpectate(ExoPlayer spectator, int entityIdToSpectate) {
        CameraPacket cameraPacket = new CameraPacket(entityIdToSpectate);
        packetDispatcher.schedule(spectator.uuid(), PacketCategory.DEFAULT, List.of(cameraPacket));
    }
}
