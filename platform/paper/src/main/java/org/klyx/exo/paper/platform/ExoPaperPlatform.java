package org.klyx.exo.paper.platform;

import net.minecraft.network.protocol.Packet;
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.common.AttributeDispatcher;
import org.klyx.exo.common.EquipmentDispatcher;
import org.klyx.exo.common.ExoPlatform;
import org.klyx.exo.common.LeashDispatcher;
import org.klyx.exo.common.LightweightEntityDispatcher;
import org.klyx.exo.common.MetadataDispatcher;
import org.klyx.exo.common.MovementDispatcher;
import org.klyx.exo.common.PassengerDispatcher;
import org.klyx.exo.common.SpectateDispatcher;
import org.klyx.exo.common.TickScheduler;
import org.klyx.exo.common.ViewerDispatch;
import org.klyx.exo.common.ViewerVisibilityQuery;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.paper.dispatch.PaperAttributeDispatcher;
import org.klyx.exo.paper.dispatch.PaperEquipmentDispatcher;
import org.klyx.exo.paper.dispatch.PaperLeashDispatcher;
import org.klyx.exo.paper.dispatch.PaperLightweightEntityDispatcher;
import org.klyx.exo.paper.dispatch.PaperMetadataDispatcher;
import org.klyx.exo.paper.dispatch.PaperMovementDispatcher;
import org.klyx.exo.paper.dispatch.PaperPacketSender;
import org.klyx.exo.paper.dispatch.PaperPassengerDispatcher;
import org.klyx.exo.paper.dispatch.PaperSpectateDispatcher;
import org.klyx.exo.paper.dispatch.PaperViewerDispatch;
import org.klyx.exo.paper.dispatch.PaperViewerVisibilityQuery;
import org.klyx.exo.paper.schedule.PaperTickScheduler;
import org.slf4j.Logger;

public final class ExoPaperPlatform implements ExoPlatform {

    private final JavaPlugin plugin;
    private final Logger logger;
    private final PaperTickScheduler tickScheduler;
    private final PacketDispatcher<Packet<?>> packetDispatcher;
    private final PaperViewerVisibilityQuery viewerVisibilityQuery = new PaperViewerVisibilityQuery();
    private final PaperViewerDispatch viewerDispatch;
    private final PaperMovementDispatcher movementDispatcher;
    private final PaperMetadataDispatcher metadataDispatcher;
    private final PaperAttributeDispatcher attributeDispatcher;
    private final PaperEquipmentDispatcher equipmentDispatcher;
    private final PaperLeashDispatcher leashDispatcher;
    private final PaperPassengerDispatcher passengerDispatcher;
    private final PaperSpectateDispatcher spectateDispatcher;
    private final PaperLightweightEntityDispatcher lightweightEntityDispatcher = new PaperLightweightEntityDispatcher();

    public ExoPaperPlatform(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getSLF4JLogger();
        this.tickScheduler = new PaperTickScheduler(plugin);
        this.packetDispatcher = new PacketDispatcher<>(new PaperPacketSender(), tickScheduler);
        this.viewerDispatch = new PaperViewerDispatch(packetDispatcher);
        this.movementDispatcher = new PaperMovementDispatcher(packetDispatcher);
        this.metadataDispatcher = new PaperMetadataDispatcher(packetDispatcher);
        this.attributeDispatcher = new PaperAttributeDispatcher(packetDispatcher);
        this.equipmentDispatcher = new PaperEquipmentDispatcher(packetDispatcher);
        this.leashDispatcher = new PaperLeashDispatcher(packetDispatcher);
        this.passengerDispatcher = new PaperPassengerDispatcher(packetDispatcher);
        this.spectateDispatcher = new PaperSpectateDispatcher(packetDispatcher);
    }

    public void start() {
        packetDispatcher.start();
    }

    public void stop() {
        packetDispatcher.stop();
    }

    public JavaPlugin plugin() {
        return plugin;
    }

    @Override
    public Logger logger() {
        return logger;
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

    @Override
    public LightweightEntityDispatcher lightweightEntityDispatcher() {
        return lightweightEntityDispatcher;
    }
}
