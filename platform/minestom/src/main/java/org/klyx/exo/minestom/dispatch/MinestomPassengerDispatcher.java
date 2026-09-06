package org.klyx.exo.minestom.dispatch;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.SetPassengersPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.common.PassengerDispatcher;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class MinestomPassengerDispatcher implements PassengerDispatcher {

    private final PacketDispatcher<ServerPacket> packetDispatcher;

    public MinestomPassengerDispatcher(PacketDispatcher<ServerPacket> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchPassengers(ExoEntity viewerSource, int vehicleEntityId, Collection<Integer> passengerIds) {
        SetPassengersPacket packet = new SetPassengersPacket(vehicleEntityId, List.copyOf(passengerIds));
        for (UUID viewer : viewerSource.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }
}
