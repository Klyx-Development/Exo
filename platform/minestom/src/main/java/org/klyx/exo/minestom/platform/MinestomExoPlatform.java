package org.klyx.exo.minestom.platform;

import net.minestom.server.MinecraftServer;
import net.minestom.server.network.packet.server.ServerPacket;
import org.klyx.exo.common.SpectateDispatcher;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.minestom.dispatch.MinestomAttributeDispatcher;
import org.klyx.exo.minestom.dispatch.MinestomEquipmentDispatcher;
import org.klyx.exo.minestom.dispatch.MinestomLeashDispatcher;
import org.klyx.exo.minestom.dispatch.MinestomMetadataDispatcher;
import org.klyx.exo.minestom.dispatch.MinestomMovementDispatcher;
import org.klyx.exo.minestom.dispatch.MinestomPacketSender;
import org.klyx.exo.minestom.dispatch.MinestomPassengerDispatcher;
import org.klyx.exo.minestom.dispatch.MinestomSpectateDispatcher;
import org.klyx.exo.minestom.dispatch.MinestomViewerDispatch;
import org.klyx.exo.minestom.dispatch.MinestomViewerVisibilityQuery;
import org.klyx.exo.minestom.schedule.MinestomTickScheduler;
import org.klyx.exo.common.AttributeDispatcher;
import org.klyx.exo.common.EquipmentDispatcher;
import org.klyx.exo.common.ExoPlatform;
import org.klyx.exo.common.LeashDispatcher;
import org.klyx.exo.common.MetadataDispatcher;
import org.klyx.exo.common.MovementDispatcher;
import org.klyx.exo.common.PassengerDispatcher;
import org.klyx.exo.common.TickScheduler;
import org.klyx.exo.common.ViewerDispatch;
import org.klyx.exo.common.ViewerVisibilityQuery;
import org.slf4j.Logger;

public final class MinestomExoPlatform implements ExoPlatform {

    private final MinestomTickScheduler tickScheduler = new MinestomTickScheduler();
    private final PacketDispatcher<ServerPacket> packetDispatcher = new PacketDispatcher<>(new MinestomPacketSender(), tickScheduler);
    private final MinestomViewerVisibilityQuery viewerVisibilityQuery = new MinestomViewerVisibilityQuery();
    private final MinestomViewerDispatch viewerDispatch = new MinestomViewerDispatch(packetDispatcher);
    private final MinestomMovementDispatcher movementDispatcher = new MinestomMovementDispatcher(packetDispatcher);
    private final MinestomMetadataDispatcher metadataDispatcher = new MinestomMetadataDispatcher(packetDispatcher);
    private final MinestomAttributeDispatcher attributeDispatcher = new MinestomAttributeDispatcher(packetDispatcher);
    private final MinestomEquipmentDispatcher equipmentDispatcher = new MinestomEquipmentDispatcher(packetDispatcher);
    private final MinestomLeashDispatcher leashDispatcher = new MinestomLeashDispatcher(packetDispatcher);
    private final MinestomPassengerDispatcher passengerDispatcher = new MinestomPassengerDispatcher(packetDispatcher);
    private final MinestomSpectateDispatcher spectateDispatcher = new MinestomSpectateDispatcher(packetDispatcher);

    public void start() {
        packetDispatcher.start();
    }

    public void stop() {
        packetDispatcher.stop();
    }

    @Override
    public Logger logger() {
        return MinecraftServer.LOGGER;
    }

    @Override
    public ViewerVisibilityQuery viewerVisibilityQuery() {
        return viewerVisibilityQuery;
    }

    @Override
    public ViewerDispatch viewerDispatch() {
        return viewerDispatch;
    }

    @Override
    public MovementDispatcher movementDispatcher() {
        return movementDispatcher;
    }

    @Override
    public MetadataDispatcher metadataDispatcher() {
        return metadataDispatcher;
    }

    @Override
    public AttributeDispatcher attributeDispatcher() {
        return attributeDispatcher;
    }

    @Override
    public TickScheduler tickScheduler() {
        return tickScheduler;
    }

    @Override
    public EquipmentDispatcher equipmentDispatcher() {
        return equipmentDispatcher;
    }

    @Override
    public LeashDispatcher leashDispatcher() {
        return leashDispatcher;
    }

    @Override
    public PassengerDispatcher passengerDispatcher() {
        return passengerDispatcher;
    }

    @Override
    public SpectateDispatcher spectateDispatcher() {
        return spectateDispatcher;
    }
}
